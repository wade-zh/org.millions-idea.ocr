using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz
{
    public interface IMessageService
    {
        void OnMessage(EventingBasicConsumer consumer, IModel sendModel, string message, Action<bool> call);
    }
}
