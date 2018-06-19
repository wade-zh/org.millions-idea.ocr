using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_entity
{
    public class LzCaptcha
    {
        public bool result { get; set; }

        public LzData data { get; set; }
    }

    public class LzData
    {
        public long id { get; set; }

        public string val { get; set; }
    }
}
