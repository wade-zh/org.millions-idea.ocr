using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_biz.Impl;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using mi_ocr_worker_win_app_entity.common;
using mi_ocr_worker_win_app_utility;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace mi_ocr_worker_win_app_biz
{
    public class ReceiveMessageServiceImpl : IReceiveMessageService
    {
        private IWalletService walletService { get; set; }

        public void OnMessage(EventingBasicConsumer consumer, IModel sendModel, string message, Action<bool> call)
        {
            Captcha captcha = JsonConvert.DeserializeObject<Captcha>(message);
            // Checking rule
            if (captcha == null || captcha.Binary == null || !ChannelUtil.IsDefined(captcha.Channel))
            {
                call(false);
                return;
            }

            // Resolve direction
            MessageSourceManager.Instance.NotifyAllDiscern(captcha, (code) =>
            {
                if (code == null)
                {
                    call(false);
                    return;
                }

                // 发送扣费请求到队列中
                var msg = JsonConvert.SerializeObject(new WalletReq()
                {
                    channel = captcha.Channel,
                    token = captcha.Token
                });
                var body = Encoding.UTF8.GetBytes(msg);
                var properties = sendModel.CreateBasicProperties();
                properties.Persistent = true;// 确保消息是可靠持久的
                sendModel.BasicPublish(MultiQueue.Exchange, MultiQueue.Wallet, null, body); 
                Console.WriteLine($"Send deduction request: {msg}");

                call(true);
            });
        }
         

    }
}
