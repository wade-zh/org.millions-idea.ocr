using System;
using System.Collections.Generic;
using System.Diagnostics;
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
                    if (code == null || code.Length == 0)
                    {
                        call(null);
                        return;
                    }
                    call(code);
                    PublishRedisMessage(captcha, binary, code);
                    PublishMongoMessage(captcha, binary, code);
                }
                catch (Exception e)
                {
                    Console.WriteLine("OnNority exception:" + e.Message);
                }
            });
        }

        public abstract void OnNority(Captcha captcha, byte[] binary, Action<string> call);


        private void PublishMongoMessage(Captcha captcha, byte[] binary, string code)
        {
            Task.Run(()=> {
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
                    else if (e.Message.Contains("System.Net.Sockets.SocketException"))
                    {

                        Console.WriteLine("MongoDB connect timeout");

                    }
                    else
                    {
                        Console.WriteLine("MongoDB exception: " + e.Message);
                    }
                }
            });
        }

        private void PublishRedisMessage(Captcha captcha, byte[] binary, string code)
        {
            Task.Run(() =>
            {
                try
                {
                    Stopwatch stopwatch = new Stopwatch();
                    stopwatch.Start();
                    long start = DateTime.Now.Ticks;
                    bool retry = false;
                    while (true)
                    {
                        try
                        {
                            long end = DateTime.Now.Ticks;
                            if ((end - start) >= 120 * 10000000)
                            {
                                Console.WriteLine("连接缓存服务器超时");
                            }
                            if (CacheHelper.Cache.Set(captcha.Ticket, code, DateTime.Now.AddSeconds(30)))
                            {
                                if (retry)
                                {
                                    Console.WriteLine("重新连接缓存服务器成功");
                                    retry = false;
                                }
                                break;
                            }
                            retry = true;
                            Console.WriteLine("重新连接缓存服务器……");
                            Thread.Sleep(1000);
                        }
                        catch (Exception)
                        {
                            continue;
                        }
                    }
                    stopwatch.Stop();
                    Console.WriteLine("Publish redis success!" + stopwatch.Elapsed.Milliseconds + "ms");
                }
                catch (Exception e)
                {
                    Console.WriteLine("PublishRedisMessage exception:" + e.Message);
                }
            });
        }

    }
}
