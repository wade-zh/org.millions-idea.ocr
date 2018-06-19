using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;

namespace c_observer_demo
{
    public abstract class BaseDiscernService : ICaptchaDiscernService
    {
        public string Discern(Captcha captcha)
        {
            string code = OnNority(captcha);
            Console.WriteLine(code);
            if (code == null) return null;
            PublishRedisMessage();
            PublishMongoMessage();
            return code;
        }

        public abstract string OnNority(Captcha captcha);

        private async void PublishMongoMessage() {
            Console.WriteLine("推送消息到MongoDB服务器");
        }


        private async void PublishRedisMessage()
        {
            Console.WriteLine("推送消息到MongoDB服务器");
        }
    }
}
