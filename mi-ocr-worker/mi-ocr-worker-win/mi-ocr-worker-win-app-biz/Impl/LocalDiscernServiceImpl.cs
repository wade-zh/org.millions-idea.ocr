using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class LocalDiscernServiceImpl : BaseDiscernServiceImpl, ILocalDiscernService
    {
        public override void OnNority(Captcha captcha, byte[] binary, Action<string> call)
        {
            if (!captcha.Channel.ToUpper().StartsWith("T"))
            {
                call(null);
                return;
            }
            string code = Caffe.GetCaptcha(binary);
            if (code.Length == 0) code = null;
            call(code);
        }
    }
}
