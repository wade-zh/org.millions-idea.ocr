using mi_ocr_worker_win_app_entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace c_observer_demo
{
    public class MessageSourceManager
    {
        private List<ICaptchaDiscernService> observers = new List<ICaptchaDiscernService>();


        public void AddObserver(ICaptchaDiscernService observer) {
            observers.Add(observer);
        }


        public void NotifyAll(Captcha captcha) {
            foreach (var item in observers)
            {
                if (item != null)
                {
                    item.Discern(captcha);
                }
            }
        }
    }
}
