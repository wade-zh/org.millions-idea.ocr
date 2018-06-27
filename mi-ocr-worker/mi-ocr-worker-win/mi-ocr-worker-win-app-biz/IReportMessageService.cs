using mi_ocr_worker_win_app_entity;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz
{
    public interface IReportMessageService 
    {
        void OnMessage(EventingBasicConsumer consumer, IModel sendModel, string message, Action<bool> call);
    }
}
