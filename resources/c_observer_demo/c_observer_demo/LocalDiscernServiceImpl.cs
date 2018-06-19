using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;

namespace c_observer_demo
{
    public class LocalDiscernServiceImpl : BaseDiscernService
    {
        public override string OnNority(Captcha captcha)
        {
            if (!captcha.Channel.StartsWith("T")) return null;
            return "local response";
        }
    }
}
