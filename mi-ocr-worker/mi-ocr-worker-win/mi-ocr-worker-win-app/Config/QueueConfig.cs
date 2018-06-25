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
            ConnectionFactory factory = new ConnectionFactory
            {
                UserName = Constant.Username,
                Password = Constant.Password,
                VirtualHost = Constant.VirtualHost,
                HostName = Constant.HostName,
                Port = Constant.Port,
                SocketReadTimeout = 60 * 1000,
                SocketWriteTimeout = 60 *1000,
                RequestedConnectionTimeout = 60 *1000
                
            };

            RabbitTemplate.connectionFactory = factory;

            Connection = factory.CreateConnection();

            BindQueue(queue, action);
        }

        private static void BindQueue(string queue, Action<EventingBasicConsumer, string, Action<bool>> action)
        {
            model = Connection.CreateModel();
            model.ExchangeDeclare(exchange: MultiQueue.Exchange, type: "topic",durable: true, autoDelete: false, arguments: null);
            IDictionary<string, object> arguments = new Dictionary<string, object>();
            arguments.Add("x-dead-letter-exchange", MultiQueue.Exchange);
            arguments.Add("x-message-ttl", 30 * 1000);
            model.QueueDeclare(queue: queue,
                                     durable: true,
                                     exclusive: false,
                                     autoDelete: false,
                                     arguments: arguments);
            model.QueueBind(queue, MultiQueue.Exchange, queue);

            var consumer = new EventingBasicConsumer(model);
            consumer.Received += (model, ea) =>
            {
                var _body = ea.Body;
                var _message = Encoding.UTF8.GetString(_body);
                action(((EventingBasicConsumer)model),_message, (res) => {
                    if (res) {
                        ((EventingBasicConsumer)model).Model.BasicAck(deliveryTag: ea.DeliveryTag, multiple: false);
                    }
                }); 
            };
            model.BasicConsume(queue: queue, autoAck: false, consumer: consumer);
        }

        

        public static void Close() {
            model.Close();
            Connection.Close();
        }
    }
}
