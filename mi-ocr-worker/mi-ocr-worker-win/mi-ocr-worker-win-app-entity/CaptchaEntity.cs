using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_entity
{
    public class Samples : BaseEntity
    {
        public string captchaId { get; set; }
        public string code { get; set; }
        public string image { get; set; }

        public string md5 { get; set; }
    }
}
