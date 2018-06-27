using mi_ocr_worker_win_app_biz;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app.Config
{
    class QueueConfig
    {
        public static IList<IConnection> Connections { get; set; } = new List<IConnection>();
        public static IModel receiveModel { get; set; }
        public static IModel sendModel { get; set; }

        public static void StartupMessageReceive(string queue, Action<EventingBasicConsumer, IModel, string, Action<bool>> action)
        {
            try
            {
                ConnectionFactory factory = new ConnectionFactory
                {
                    UserName = Constant.Username,
                    Password = Constant.Password,
                    VirtualHost = Constant.VirtualHost,
                    HostName = Constant.HostName,
                    Port = Constant.Port,
                    AutomaticRecoveryEnabled = true,
                    NetworkRecoveryInterval = TimeSpan.FromSeconds(10)
                };
                RabbitTemplate.connectionFactory = factory;

                /**
                 *  为了防止消息堵塞，经过排查，负责接收消息的连接和负责发送消息的连接需要隔离开来
                 *  索引0位置是接收消息，索引1的位置是发送消息
                 *
                 */
                Connections.Add(factory.CreateConnection());
                Connections.Add(factory.CreateConnection());

                BindQueue(queue, action);
            }
            catch (Exception e)
            {

                Console.WriteLine("StartupMessageReceive exception:" + e.ToString());
            }
        }

        private static void BindQueue(string queue, Action<EventingBasicConsumer, IModel, string, Action<bool>> action)
        {
            try
            {
                receiveModel = Connections.First().CreateModel();
                sendModel = Connections.First().CreateModel();

                receiveModel.ExchangeDeclare(exchange: MultiQueue.Exchange, type: "topic", durable: true, autoDelete: false, arguments: null);
                receiveModel.BasicQos(0,1, false);
                receiveModel.QueueDeclare(queue: queue,
                                         durable: true,
                                         exclusive: false,
                                         autoDelete: false,
                                         arguments: null);
                receiveModel.QueueBind(queue, MultiQueue.Exchange, queue);
                var consumer = new EventingBasicConsumer(receiveModel);
                consumer.Received += (model, ea) =>
                {
                    try
                    {
                        Console.WriteLine("Received message");

                        var _body = ea.Body;
                        var _message = Encoding.UTF8.GetString(_body);

                        Stopwatch stopwatch = new Stopwatch();
                        stopwatch.Start();
                        action(((EventingBasicConsumer)model), sendModel, _message, (res) =>
                        {
                            try
                            {
                                IModel cm = ((EventingBasicConsumer)model).Model;
                                if (res) 
                                {
                                    // 正常消息，确认消息
                                    cm.BasicAck(deliveryTag: ea.DeliveryTag, multiple: false);
                                    Console.WriteLine($"{ea.DeliveryTag} message is BasicAck");
                                }
                                else
                                {
                                    // 消费异常，消息重新发回队列
                                    //cm.BasicNack(ea.DeliveryTag, false, true);
                                    //Console.WriteLine("Cureent message is BasicNack");
                                }
                            }
                            catch (Exception e)
                            {
                               Console.WriteLine("EventingBasicConsumer exception:" + e.Message);
                            }
                            stopwatch.Stop();
                            Console.WriteLine($"{ea.DeliveryTag} Stopwatch is {stopwatch.Elapsed.TotalSeconds}/s");

                        });
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("Received exception:" + e.ToString());
                    }

                };
                receiveModel.BasicConsume(queue: queue, autoAck: false, consumer: consumer);
            }
            catch (Exception e)
            {

                Console.WriteLine("BindQueue exception:" + e.ToString());
            }
        }


        private static void Run(object state) {

            ThreadPoolParams threadPoolParams = (ThreadPoolParams)state;
            var _body = threadPoolParams.BasicDeliverEventArgs.Body;
            var _message = Encoding.UTF8.GetString(_body);
            threadPoolParams.Action(((EventingBasicConsumer)threadPoolParams.Model), _message, (res) =>
            {
                try
                {
                    if (res)
                    {
                        Console.WriteLine("消息已确认");
                        ((EventingBasicConsumer)threadPoolParams.Model).Model.BasicAck(deliveryTag: threadPoolParams.BasicDeliverEventArgs.DeliveryTag, multiple: false);
                    }
                    else
                    {
                        Console.WriteLine("消息重新归队");
                        ((EventingBasicConsumer)threadPoolParams.Model).Model.BasicReject(deliveryTag: threadPoolParams.BasicDeliverEventArgs.DeliveryTag, true);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("EventingBasicConsumer exception:" + e.Message);
                }
            });
        }


        public static void Close() {
            receiveModel.Close();
            sendModel.Close();
            foreach (var item in Connections)
            {
                item.Close();
            }
        }


        class ThreadPoolParams {
            public RabbitMQ.Client.Events.BasicDeliverEventArgs BasicDeliverEventArgs { get; set; }
            public Action<EventingBasicConsumer, string, Action<bool>> Action { get; set; }
            public object Model { get; set; }
        }
    }
}
