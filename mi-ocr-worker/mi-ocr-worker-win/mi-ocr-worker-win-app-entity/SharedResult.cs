using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_entity
{
    public class SharedResult
    {
        /// <summary>
        /// 自有令牌标志字段
        /// </summary>
        public string Ticket { get; set; }

        /// <summary>
        /// 对接外部系统返回的标志字段
        /// </summary>
        public string Id { get; set; }

        /// <summary>
        /// 识别结果
        /// </summary>
        public string Result { get; set; }

        
    }
}
