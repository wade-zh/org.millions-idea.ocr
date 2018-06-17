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
        public static IModel Model { get; set; }

        public static void StartupMessageReceive(Func<string,bool> action) {
            ConnectionFactory factory = new ConnectionFactory();

            factory.UserName = Constant.Username;
            factory.Password = Constant.Password;
            factory.VirtualHost = Constant.VirtualHost;
            factory.HostName = Constant.HostName;
            factory.Port = Constant.Port;

            Connection = factory.CreateConnection();
            Model = Connection.CreateModel();

            Model.QueueDeclare(queue: Constant.QueueName,
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

            Model.BasicConsume(queue: Constant.QueueName, autoAck: false, consumer: consumer);
        }


        public static void Close() {
            Model.Close();
            Connection.Close();
        }
    }
}
