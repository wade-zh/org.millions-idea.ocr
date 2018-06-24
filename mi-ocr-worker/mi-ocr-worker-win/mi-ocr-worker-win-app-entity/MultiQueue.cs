using System.Configuration;

namespace mi_ocr_worker_win_app_entity
{
    public class MultiQueue {
        public static string Captcha => ConfigurationManager.AppSettings["Queue"].Split(',')[0];
        public static string Exchange => ConfigurationManager.AppSettings["Exchange"];
        public static string Report => ConfigurationManager.AppSettings["Queue"].Split(',')[1];
        public static string Wallet => ConfigurationManager.AppSettings["Queue"].Split(',')[2];
    }
}
