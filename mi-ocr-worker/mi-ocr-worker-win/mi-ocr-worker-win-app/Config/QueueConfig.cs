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
        public static IModel Model { get; set; }

        public static void StartupMessageReceive(string queue, Func<string, bool> action)
        {
            ConnectionFactory factory = new ConnectionFactory
            {
                UserName = Constant.Username,
                Password = Constant.Password,
                VirtualHost = Constant.VirtualHost,
                HostName = Constant.HostName,
                Port = Constant.Port
            };

            Connection = factory.CreateConnection();

            BindQueue(queue, action);
        }

        private static void BindQueue(string queue, Func<string, bool> action)
        {
            Model = Connection.CreateModel();
            Model.QueueDeclare(queue: queue,
                                     durable: false,
                                     exclusive: false,
                                     autoDelete: false,
                                     arguments: null);

            var consumer = new EventingBasicConsumer(Model);
            consumer.Received += (model, ea) =>
            {
                var _body = ea.Body;
                var _message = Encoding.UTF8.GetString(_body);
                if (action(_message))
                {
                    Model.BasicAck(deliveryTag: ea.DeliveryTag, multiple: false);
                }
            };
            Model.BasicConsume(queue: queue, autoAck: false, consumer: consumer);
        }

        public static void Close() {
            Model.Close();
            Connection.Close();
        }
    }

    class MultiQueue {
        public static string Captcha => ConfigurationManager.AppSettings["Queue"].Split(',')[0];
        public static string Report => ConfigurationManager.AppSettings["Queue"].Split(',')[1];
    }
}
