using mi_ocr_worker_win_app_entity;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Configuration;
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

        public static void StartupMessageReceive(string queue, Action<string, Action<bool>> action)
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
            Connection = factory.CreateConnection();

            BindQueue(queue, action);
        }

        private static void BindQueue(string queue, Action<string, Action<bool>> action)
        {
            model = Connection.CreateModel();
            model.ExchangeDeclare(exchange: MultiQueue.Exchange, type: "topic",durable: true, autoDelete: false, arguments: null);
            model.QueueDeclare(queue: queue,
                                     durable: true,
                                     exclusive: false,
                                     autoDelete: false,
                                     arguments: null);
            model.QueueBind(queue, MultiQueue.Exchange, queue);

            var consumer = new EventingBasicConsumer(model);
            consumer.Received += (model, ea) =>
            {
                var _body = ea.Body;
                var _message = Encoding.UTF8.GetString(_body);
                action(_message, (res) => {
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

    class MultiQueue {
        public static string Captcha => ConfigurationManager.AppSettings["Queue"].Split(',')[0];
        public static string Exchange => ConfigurationManager.AppSettings["Exchange"];
        public static string Report => ConfigurationManager.AppSettings["Queue"].Split(',')[1];
    }
}
