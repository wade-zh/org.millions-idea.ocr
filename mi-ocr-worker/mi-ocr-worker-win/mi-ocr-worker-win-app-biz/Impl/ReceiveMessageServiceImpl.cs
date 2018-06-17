using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz
{
    public class ReceiveMessageServiceImpl : IReceiveMessageService
    {
        public void OnMessage(string message)
        {
            Console.WriteLine("消息到达" + message);
        }
    }
}
