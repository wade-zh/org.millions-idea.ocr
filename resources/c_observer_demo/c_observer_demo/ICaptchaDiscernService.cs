using mi_ocr_worker_win_app_entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace c_observer_demo
{
    public interface ICaptchaDiscernService
    {
        string Discern(Captcha captcha);
    }
}
