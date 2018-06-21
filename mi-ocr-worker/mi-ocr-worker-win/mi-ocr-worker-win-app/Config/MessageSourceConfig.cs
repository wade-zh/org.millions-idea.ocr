using mi_ocr_worker_win_app_biz;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app.Config
{
    public class MessageSourceConfig
    {
        public static void Configure(params ICaptchaDiscernService[] list) {
            MessageSourceManager.Instance = new MessageSourceManager();
            foreach (var item in list)
            {
                MessageSourceManager.Instance.AddObserver(item);
            }
        }

        public static void Configure(params IReportErrorMessageService[] list)
        {
            foreach (var item in list)
            {
                MessageSourceManager.Instance.AddObserver(item);
            }
        }
    }
}
