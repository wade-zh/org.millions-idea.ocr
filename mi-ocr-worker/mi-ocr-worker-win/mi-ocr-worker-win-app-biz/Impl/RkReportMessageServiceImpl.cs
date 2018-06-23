using mi_ocr_worker_win_app_biz.Util;
using mi_ocr_worker_win_app_entity;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class RkReportMessageServiceImpl : BaseReportMessageServiceImpl, IRkReportMessageService
    {
        public override void OnNority(Captcha captcha, Action<bool> call)
        {
            if (!captcha.Channel.ToUpper().StartsWith("R"))
            {
                call(false);
                return;
            }
            ReportError(captcha.Ticket);
            call(true);
        }


        /// <summary>
        /// TODO 遗留代码
        /// </summary>
        /// <param name="message"></param>
        private async void ReportError(string message) {
            var client = new RestClient("http://api.ruokuai.com/reporterror.json");
            var request = new RestRequest(Method.POST);
            request.AddParameter("username", RemoteUser.RuoKuai);
            request.AddParameter("password", Constant.RemotePassword);
            request.AddParameter("softid", RemoteUser.RuoKuaiSoftId);
            request.AddParameter("softkey", RemoteUser.RuoKuaiSoftKey);
            request.AddParameter("id", message);
            IRestResponse response = client.Execute(request);
            if (response == null || response.StatusCode != System.Net.HttpStatusCode.OK) return;
            Console.WriteLine($"Upload error reports to ruokuai: { System.Web.HttpUtility.UrlDecode(message, System.Text.Encoding.GetEncoding("UTF-8"))}");
        }
    }
}
