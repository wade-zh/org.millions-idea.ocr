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
    public class LzReportMessageServiceImpl : BaseReportMessageServiceImpl, ILzReportMessageService
    {

        public override void OnNority(Captcha captcha, Action<bool> call)
        {
            if (!captcha.Channel.ToUpper().StartsWith("J"))
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
            var client = new RestClient("http://v1-http-api.jsdama.com/api.php?mod=php&act=error");
            var request = new RestRequest(Method.POST);
            request.AddParameter("user_name", RemoteUser.LianZhong);
            request.AddParameter("user_pw", Constant.RemotePassword);
            request.AddParameter("yzm", message);
            IRestResponse response = client.Execute(request);
            if (response == null || response.StatusCode != System.Net.HttpStatusCode.OK) return;
            Console.WriteLine($"Upload error reports to lianzhong: { System.Web.HttpUtility.UrlDecode(message, System.Text.Encoding.GetEncoding("UTF-8"))}");
        }
    }
}
