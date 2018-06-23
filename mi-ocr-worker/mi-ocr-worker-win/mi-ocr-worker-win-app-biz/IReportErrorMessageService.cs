using mi_ocr_worker_win_app_entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz
{
    public interface IReportErrorMessageService
    {
        void Report(Captcha captcha, Action<bool> call);
    }
}
