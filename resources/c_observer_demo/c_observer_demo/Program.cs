using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;

namespace c_observer_demo
{
    class Program
    {
        static void Main(string[] args)
        {
            MessageSourceManager messageSourceManager = new MessageSourceManager();
            messageSourceManager.AddObserver(new LocalDiscernServiceImpl());
            messageSourceManager.AddObserver(new LzRemoteDiscernServiceImpl());
            messageSourceManager.NotifyAll(new Captcha()
            {
                Ticket = "2cbe26c4-7be0-4cf7-8f3e-6207fd04b2b5",
                Channel = "T0003604",
                Binary = "#"
            });
            messageSourceManager.NotifyAll(new Captcha()
            {
                Ticket = "2cbe26c4-7be0-4cf7-8f3e-6207fd04b2b5",
                Channel = "J0003604",
                Binary = "#"
            });
            Console.ReadLine();
        }
    }
}
