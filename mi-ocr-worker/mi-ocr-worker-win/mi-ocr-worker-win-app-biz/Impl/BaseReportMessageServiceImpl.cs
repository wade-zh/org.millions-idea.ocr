using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public abstract class BaseReportMessageServiceImpl : IReportErrorMessageService
    {
         
        public void Report(Captcha captcha, Action<bool> call)
        {
            OnNority(captcha, (ret) => {
                if (!ret)
                {
                    call(false);
                    return;
                }
                //DeleteErrorSampleAsync(captcha);
            });
        }

        public abstract void OnNority(Captcha captcha, Action<bool> call);


        private async void DeleteErrorSampleAsync(Captcha captcha)
        {
            await Task.Run(()=> {
                try
                {
                    MongoDBHelper<Samples> mongoDBHelper = new MongoDBHelper<Samples>();
                    Samples sample = mongoDBHelper.GetEntity(s => s.captchaId == captcha.Ticket && s.channel == captcha.Channel);
                    if (sample == null) return;
                    sample.isError = true;
                    mongoDBHelper.Update(sample, s => s.captchaId == captcha.Ticket && s.channel == captcha.Channel);
                }
                catch (Exception e)
                {
                    Console.WriteLine($"DeleteErrorSampleAsync exception:" + e.Message);
                }
            });
        }
    }
}
