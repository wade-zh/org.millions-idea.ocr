using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;

namespace c_observer_demo
{
    public class LzRemoteDiscernServiceImpl : BaseDiscernService
    {
        public override string OnNority(Captcha captcha)
        {
            if (!captcha.Channel.StartsWith("J")) return null;
            return "lianzhong response";
        }
    }
}
