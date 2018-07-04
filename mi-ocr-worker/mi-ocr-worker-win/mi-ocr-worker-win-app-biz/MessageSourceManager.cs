using mi_ocr_worker_win_app_entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz
{
    public class MessageSourceManager
    {
        public static MessageSourceManager Instance { get; set; }

        private List<ICaptchaDiscernService> DiscernObservers = new List<ICaptchaDiscernService>();
        private List<IReportErrorMessageService> ReportObservers = new List<IReportErrorMessageService>();

        public void AddObserver(ICaptchaDiscernService observer)
        {
            DiscernObservers.Add(observer);
        }
        public void AddObserver(IReportErrorMessageService observer)
        {
            ReportObservers.Add(observer);
        }

        public void NotifyAllDiscern(Captcha captcha, Action<string> call)
        {
            foreach (var item in DiscernObservers)
            {
                bool isOk = false;
                if (item != null)
                {
                    item.Discern(captcha, (code) => {
                        isOk = true;
                        call(code);
                    });
                    break;
                }
            }
        }

        public void NotifyAllReport(Captcha captcha, Action<bool> call)
        {
            foreach (var item in ReportObservers)
            {
                if (item != null)
                {
                    item.Report(captcha, (flag) => {
                        call(flag);
                    });
                }
            }
        }
    }
}
