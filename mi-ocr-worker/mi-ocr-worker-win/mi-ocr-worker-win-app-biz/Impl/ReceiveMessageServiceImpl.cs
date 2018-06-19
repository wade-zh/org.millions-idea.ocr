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
            if (captcha == null || captcha.Binary == null || IsDefined(captcha.Channel)) return false;
            
            // Resolve direction

            byte[] binary = Convert.FromBase64String(captcha.Binary);
            if (binary.Length == 0) return false;
            string code = Caffe.GetCaptcha(binary);
            if (code.Length == 0) code = "null";
            PublishMessageAsync(captcha, binary, code);
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

        private async void PublishMessageAsync(Captcha captcha, byte[] binary, string code) {
            CacheHelper.Cache.Set(captcha.Ticket, code, DateTime.Now.AddSeconds(30));
            MongoDBHelper<Samples> mongoDBHelper = new MongoDBHelper<Samples>();
            Samples entity = new Samples()
            {
                channel = captcha.Channel,
                captchaId = captcha.Ticket,
                code = code,
                image = captcha.Binary,
                md5 = Md5Util.GetMD5Hash(binary),
                isError = false,
                createTime = DateTime.Now.AddHours(8)
            };
            Console.WriteLine($"Insert samples state is:{mongoDBHelper.Insert(entity) != null}");
        }
    }
}
