using mi_ocr_worker_win_app.Config;
using mi_ocr_worker_win_app_biz;
using mi_ocr_worker_win_app_biz.Impl;
using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using Microsoft.Practices.Unity;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using static System.Net.Mime.MediaTypeNames;

namespace mi_ocr_worker_win_app
{

    class Program
    {
        public static IReceiveMessageService ReceiveMessageService { get; set; }
        public static IReportMessageService ReportMessageService { get; set; }
        public static IRkReportMessageService RkReportMessageService { get; set; }
        public static ILzReportMessageService LzReportMessageService { get; set; }
        public static ILocalReportMessageService LocalReportMessageService { get; set; }
        public static ILocalDiscernService LocalDiscernService { get; set; }
        public static ILzRemoteDiscernService LzRemoteDiscernService { get; set; }
        public static IRkRemoteDiscernService RkRemoteDiscernService { get; set; }


        static void Main(string[] args)
        {
            Console.WriteLine($"Startup state is {Caffe.InitCaptcha("./deploy.prototxt", ConfigurationManager.AppSettings["Caffemodel"], "./label-map.txt", -1, 32)}");

            // Bind unity container
            UnityConfig.Configure();
            FillUnityContainer();

            // Bind message source
            MessageSourceConfig.Configure(LocalDiscernService, LzRemoteDiscernService, RkRemoteDiscernService);
            MessageSourceConfig.Configure(LocalReportMessageService, LzReportMessageService, RkReportMessageService);

            // Bind receive messsages callback method
            QueueConfig.StartupMessageReceive(MultiQueue.Captcha, ReceiveMessageService.OnMessage);

            // Bind report error messsages callback method
            QueueConfig.StartupMessageReceive(MultiQueue.Report, ReportMessageService.OnMessage);


            #region print some messages
            Console.WriteLine("Please enter \"exit\" to exit the system safely.");
            var cmdLine = string.Empty;
            do
            {
                cmdLine = Console.ReadLine();
            } while (cmdLine != "exit");
            QueueConfig.Close();
            #endregion
        }

        private static void FillUnityContainer()
        {
            ReceiveMessageService = UnityConfig.Container.Resolve<ReceiveMessageServiceImpl>();
            ReportMessageService = UnityConfig.Container.Resolve<ReportMessageServiceImpl>();
            LocalDiscernService = UnityConfig.Container.Resolve<LocalDiscernServiceImpl>();
            LzRemoteDiscernService = UnityConfig.Container.Resolve<LzRemoteDiscernServiceImpl>();
            RkRemoteDiscernService = UnityConfig.Container.Resolve<RkRemoteDiscernServiceImpl>();
            LocalReportMessageService = UnityConfig.Container.Resolve<LocalReportMessageServiceImpl>();
            LzReportMessageService = UnityConfig.Container.Resolve<LzReportMessageServiceImpl>();
            RkReportMessageService = UnityConfig.Container.Resolve<RkReportMessageServiceImpl>();
        }
    }
}
