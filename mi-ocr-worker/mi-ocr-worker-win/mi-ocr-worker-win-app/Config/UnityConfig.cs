using mi_ocr_worker_win_app_biz;
using mi_ocr_worker_win_app_entity;
using Microsoft.Practices.Unity;
using Microsoft.Practices.Unity.Configuration;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app.Config
{
    public static class UnityConfig
    {
        public static UnityContainer Container { get; set; }

        public static void Configure()
        {
            Container = new UnityContainer();
            Container.RegisterType<IReceiveMessageService, ReceiveMessageServiceImpl>();
        }

    }
}
