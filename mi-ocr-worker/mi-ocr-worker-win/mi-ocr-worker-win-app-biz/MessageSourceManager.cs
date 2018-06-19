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

        private List<ICaptchaDiscernService> observers = new List<ICaptchaDiscernService>();

        public void AddObserver(ICaptchaDiscernService observer)
        {
            observers.Add(observer);
        }


        public string NotifyAll(Captcha captcha)
        {
            string res = null;
            foreach (var item in observers)
            {
                if (item != null)
                {
                    res = item.Discern(captcha);
                    if (res != null) break;
                }
            }
            return res;
        }
    }
}
