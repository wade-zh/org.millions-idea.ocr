using mi_ocr_worker_win_app.Config;
using RabbitMQ.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz.Util
{
    public class RabbitTemplate
    {
        public static ConnectionFactory connectionFactory { get; set; }

        public static void convertAndSend(string queue, string message) {
            using (var conn = connectionFactory.CreateConnection())
            {
                using (var channel = conn.CreateModel())
                {
                }
            }
        }
    }
}
