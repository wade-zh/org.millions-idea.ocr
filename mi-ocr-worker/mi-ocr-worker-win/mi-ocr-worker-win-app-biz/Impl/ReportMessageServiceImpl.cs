using mi_ocr_worker_win_app_entity;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class ReportMessageServiceImpl : IReportMessageService
    {
        public void OnMessage(EventingBasicConsumer consumer,IModel sendModel, string message, Action<bool> call)
        {
            Captcha captcha = JsonConvert.DeserializeObject<Captcha>(message);
            // Checking rule
            if (captcha == null || captcha.Ticket == null || !ChannelUtil.IsDefined(captcha.Channel))
            {
                call(false);
                return;
            }

            // Resolve direction
            MessageSourceManager.Instance.NotifyAllReport(captcha, (code) => {
                try
                {
                    if (!code)
                    {
                        call(false);
                        return;
                    }
                    call(true);
                }
                catch (Exception e)
                {
                    Console.WriteLine($"NotifyAllReport exception:" + e.Message);
                }
            });
        }

        
    }
}
