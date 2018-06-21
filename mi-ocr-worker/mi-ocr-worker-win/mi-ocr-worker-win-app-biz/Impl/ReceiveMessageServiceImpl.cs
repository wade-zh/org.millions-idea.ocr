using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_biz.Impl;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using mi_ocr_worker_win_app_utility;
using Newtonsoft.Json;

namespace mi_ocr_worker_win_app_biz
{
    public class ReceiveMessageServiceImpl : IReceiveMessageService
    {
        public void OnMessage(string message, Action<bool> call)
        { 
            Captcha captcha = JsonConvert.DeserializeObject<Captcha>(message);
            // Checking rule
            if (captcha == null || captcha.Binary == null || !ChannelUtil.IsDefined(captcha.Channel)) {
                call(false);
                return;
            }

            // Resolve direction
            MessageSourceManager.Instance.NotifyAllDiscern(captcha, (code)=> {
                if (code == null) {
                    call(false);
                    return;
                }
                call(true);
            });
        }
         

    }
}
