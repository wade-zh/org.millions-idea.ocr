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
        public static void StartupMessageReceive(Action<string> action) {
            ConnectionFactory factory = new ConnectionFactory();

            factory.UserName = Constant.Username;
            factory.Password = Constant.Password;
            factory.VirtualHost = Constant.VirtualHost;
            factory.HostName = Constant.HostName;
            factory.Port = Constant.Port;

            using (IConnection conn = factory.CreateConnection())
            {
                using (IModel channel = conn.CreateModel())
                {
                    channel.QueueDeclare(queue: Constant.QueueName,
                                     durable: false,
                                     exclusive: false,
                                     autoDelete: false,
                                     arguments: null);

                    var consumer = new EventingBasicConsumer(channel);
                    consumer.Received += (model, ea) =>
                    {
                        var _body = ea.Body;
                        var _message = Encoding.UTF8.GetString(_body);
                        action(_message);
                        channel.BasicAck(deliveryTag: ea.DeliveryTag, multiple: false);
                    };

                    channel.BasicConsume(queue: Constant.QueueName, autoAck: false, consumer: consumer); 
                }
            }
        }
    }
}
