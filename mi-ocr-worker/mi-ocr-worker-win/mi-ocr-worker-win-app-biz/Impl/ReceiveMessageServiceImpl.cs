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
            if (captcha == null || captcha.Binary == null) return false;
            byte[] binary = Convert.FromBase64String(captcha.Binary);
            if (binary.Length == 0) return false;
            string code = Caffe.GetCaptcha(binary);
            if (code.Length == 0) code = "null";
            CacheHelper.Cache.Set(captcha.Ticket, code, DateTime.Now.AddSeconds(30));
            MongoDBHelper<Samples> mongoDBHelper = new MongoDBHelper<Samples>();
            Samples entity = new Samples()
            { 
                captchaId = captcha.Ticket,
                code = code,
                image = captcha.Binary,
                md5 = Md5Util.GetMD5Hash(binary)
            };
            Console.WriteLine($"Insert samples state is:{mongoDBHelper.Insert(entity) != null}");
            return true;
        }
    }
}
