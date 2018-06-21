using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_entity
{
    public class Constant
    {
        public static string Username { get; set; } = ConfigurationManager.AppSettings["Username"];

        public static string Password { get; set; } = ConfigurationManager.AppSettings["Password"];

        public static string VirtualHost { get; set; } = ConfigurationManager.AppSettings["VirtualHost"];

        public static string HostName { get; set; } = ConfigurationManager.AppSettings["HostName"];

        public static int Port { get; set; } = int.Parse(ConfigurationManager.AppSettings["Port"]);
         

        public static string RemotePassword { get; set; } = ConfigurationManager.AppSettings["RemotePassword"];
    }
}
