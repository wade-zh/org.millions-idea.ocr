using mi_ocr_worker_win_app_biz;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app.Config
{
    class QueueConfig
    {
        public static IConnection Connection { get; set; }
        public static IModel model { get; set; }

        public static void StartupMessageReceive(string queue, Action<EventingBasicConsumer, string, Action<bool>> action)
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

                Connection = factory.CreateConnection();

                BindQueue(queue, action);
            }
            catch (Exception e)
            {

                Console.WriteLine("StartupMessageReceive exception:" + e.ToString());
            }
        }

        private static void BindQueue(string queue, Action<EventingBasicConsumer, string, Action<bool>> action)
        {
            try
            {
                model = Connection.CreateModel();
                model.ExchangeDeclare(exchange: MultiQueue.Exchange, type: "topic", durable: true, autoDelete: false, arguments: null);
                IDictionary<string, object> arguments = new Dictionary<string, object>();
                arguments.Add("x-dead-letter-exchange", MultiQueue.Exchange);
                arguments.Add("x-message-ttl", 30000);
                model.QueueDeclare(queue: queue,
                                         durable: true,
                                         exclusive: false,
                                         autoDelete: false,
                                         arguments: null);
                model.QueueBind(queue, MultiQueue.Exchange, queue);
                var consumer = new EventingBasicConsumer(model);
                consumer.Received += (model, ea) =>
                {
                    try
                    {
                        Console.WriteLine("Received message");

                        var _body = ea.Body;
                        var _message = Encoding.UTF8.GetString(_body);
                        action(((EventingBasicConsumer)model), _message, (res) =>
                        {
                            try
                            {
                                if (res)
                                {
                                    ((EventingBasicConsumer)model).Model.BasicAck(deliveryTag: ea.DeliveryTag, multiple: false);
                                }
                            }
                            catch (Exception e)
                            {
                                Console.WriteLine("EventingBasicConsumer exception:" + e.Message);
                            }
                        });

                        /*ThreadPool.QueueUserWorkItem(new WaitCallback(Run), new ThreadPoolParams()
                         {
                             BasicDeliverEventArgs = ea,
                             Action = action,
                             Model = model
                         });*/
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("Received exception:" + e.ToString());
                    }

                };
                model.BasicConsume(queue: queue, autoAck: false, consumer: consumer);
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
            model.Close();
            Connection.Close();
        }


        class ThreadPoolParams {
            public RabbitMQ.Client.Events.BasicDeliverEventArgs BasicDeliverEventArgs { get; set; }
            public Action<EventingBasicConsumer, string, Action<bool>> Action { get; set; }
            public object Model { get; set; }
        }
    }
}
