using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using mi_ocr_worker_win_app_utility;
using Newtonsoft.Json;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public abstract class BaseDiscernServiceImpl : ICaptchaDiscernService
    {

        public void Discern(Captcha captcha, Action<string> call) {
            byte[] binary = Convert.FromBase64String(captcha.Binary);
            if (binary.Length == 0)
            {
                call(null);
                return;
            }
            OnNority(captcha, binary, (code) => {
                try
                {
                    if (code == null)
                    {
                        call(null);
                        return;
                    }
                    call(code);
                    PublishRedisMessage(captcha, binary, code);
                }
                catch (Exception e)
                {
                    Console.WriteLine("OnNority exception:" + e.Message);
                }
                //PublishMongoMessage(captcha, binary, code);
            });
        }

        public abstract void OnNority(Captcha captcha, byte[] binary, Action<string> call);


        private async void PublishMongoMessage(Captcha captcha, byte[] binary, string code)
        {

            await Task.Run(()=> {
                try
                {
                    string captchaId = captcha.Ticket;
                    if (code.Contains("Ticket") && code.Contains("Id") && code.Contains("Result"))
                    {
                        string nCode = code;
                        captchaId = JsonConvert.DeserializeObject<SharedResult>(nCode).Id;
                        code = JsonConvert.DeserializeObject<SharedResult>(nCode).Result;
                    }
                    MongoDBHelper<Samples> mongoDBHelper = new MongoDBHelper<Samples>();
                    Samples entity = new Samples()
                    {
                        channel = captcha.Channel,
                        ticket = captcha.Ticket,
                        captchaId = captchaId,
                        code = code.ToUpper(),
                        image = captcha.Binary,
                        md5 = Md5Util.GetMD5Hash(binary),
                        isError = false,
                        createTime = DateTime.Now.AddHours(8)
                    };
                    mongoDBHelper.Insert(entity);
                }
                catch (Exception e)
                {
                    if (e.Message.Contains("duplicate key error"))
                    {
                        Console.WriteLine("Ignore current captcha, duplicate key error: " + code);
                        return;
                    }
                    else
                    {
                        Console.WriteLine("MongoDB exception: " + e.Message);
                    }
                }
            });
        }

        private async void PublishRedisMessage(Captcha captcha, byte[] binary, string code)
        {
            try
            {
                CacheHelper.Cache.Set(captcha.Ticket, code, DateTime.Now.AddSeconds(30));
            }
            catch (Exception e)
            {
                Console.WriteLine("PublishRedisMessage exception:" + e.Message);
            }
        }

    }
}
