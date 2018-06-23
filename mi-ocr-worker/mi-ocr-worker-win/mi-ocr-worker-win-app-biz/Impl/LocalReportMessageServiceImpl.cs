using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class LocalReportMessageServiceImpl : BaseReportMessageServiceImpl, ILocalReportMessageService
    {

        public override void OnNority(Captcha captcha, Action<bool> call)
        {
            if (!captcha.Channel.ToUpper().StartsWith("T"))
            {
                call(false);
                return;
            }
            call(true);
        }
    }
}
