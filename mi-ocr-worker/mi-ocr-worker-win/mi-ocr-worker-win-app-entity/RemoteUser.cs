using System.Collections;
using System.Configuration;

namespace mi_ocr_worker_win_app_entity
{
    public class RemoteUser {
        public static string[] RemoteUsername { get; set; } = ConfigurationManager.AppSettings["RemoteUsername"].Split(',');
        public static string[] RemoteToken { get {
                string token = ConfigurationManager.AppSettings["RemoteToken"];
                if (token.Contains("|"))
                {
                    string [] arr = token.Split('|');
                    ArrayList arrayList = new ArrayList();
                    foreach (var item in arr)
                    {
                        arrayList.AddRange(item.Split(','));
                    }
                    return (string[])arrayList.ToArray();
                }
                else
                {
                    return token.Split(',');
               }
            } }

        public static string LianZhong { get; set; } = RemoteUsername[0];
        public static string RuoKuai { get; set; } = RemoteUsername[1];
        public static string RuoKuaiSoftId { get; set; } = RemoteToken[0];
        public static string RuoKuaiSoftKey { get; set; } = RemoteToken[1];
    }
}
