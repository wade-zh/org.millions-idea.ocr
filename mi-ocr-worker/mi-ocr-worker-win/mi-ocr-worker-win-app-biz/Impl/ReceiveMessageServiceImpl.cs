using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using mi_ocr_worker_win_app_utility;
using Newtonsoft.Json;

namespace mi_ocr_worker_win_app_biz
{
    public class ReceiveMessageServiceImpl : IReceiveMessageService
    {
        public bool OnMessage(string message)
        { 
            Captcha captcha = JsonConvert.DeserializeObject<Captcha>(message);
            // Checking rule
            if (captcha == null || captcha.Binary == null || !IsDefined(captcha.Channel)) return false;

            // Resolve direction
            string code = MessageSourceManager.Instance.NotifyAll(captcha);
            if (code == null) return false;
            return true;
        }

        private bool IsDefined(string value) {
            if (ChannelType.T0003604.Equals(value))
            {
                return true;
            }
            else if (ChannelType.J0003604.Equals(value))
            {
                return true;
            }
            return false;
        }

    }
}
