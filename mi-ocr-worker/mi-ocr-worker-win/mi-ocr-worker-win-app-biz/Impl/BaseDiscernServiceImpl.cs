using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using mi_ocr_worker_win_app_utility;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public abstract class BaseDiscernServiceImpl : ICaptchaDiscernService
    {
        public string Discern(Captcha captcha)
        {
            byte[] binary = Convert.FromBase64String(captcha.Binary);
            if (binary.Length == 0) return null;
            string code = OnNority(captcha, binary);
            if (code == null) return null;
            PublishRedisMessage(captcha, binary, code);
            PublishMongoMessage(captcha, binary, code);
            return code;
        }

        public abstract string OnNority(Captcha captcha, byte[] binary);

        private async void PublishMongoMessage(Captcha captcha, byte[] binary, string code)
        {
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

        private async void PublishRedisMessage(Captcha captcha, byte[] binary, string code)
        {
            CacheHelper.Cache.Set(captcha.Ticket, code, DateTime.Now.AddSeconds(30));
        }
    }
}
