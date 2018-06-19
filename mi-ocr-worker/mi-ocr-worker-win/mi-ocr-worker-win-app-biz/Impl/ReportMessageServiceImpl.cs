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
    public class ReportMessageServiceImpl : IReportMessageService
    {
        public void OnMessage(string message, Action<bool> call)
        {
            if (message.Length != 36)
            {
                // report remote system
                ReportErrorToLianZhong(message);
            }
            DeleteErrorSampleAsync(message);
            call(true);
        }

        private async void DeleteErrorSampleAsync(string message)
        {
            MongoDBHelper<Samples> mongoDBHelper = new MongoDBHelper<Samples>();
            Samples sample = mongoDBHelper.GetEntity(s => s.captchaId == message);
            if (sample == null) return;
            sample.isError = true;
            mongoDBHelper.Update(sample, s => s.captchaId == message);
        }

        /// <summary>
        /// TODO 遗留代码
        /// </summary>
        /// <param name="message"></param>
        private async void ReportErrorToLianZhong(string message) {
            var client = new RestClient("http://v1-http-api.jsdama.com/api.php?mod=php&act=error");
            var request = new RestRequest(Method.POST);
            request.AddParameter("user_name", Constant.JUserName);
            request.AddParameter("user_pw", Constant.JPassword);
            request.AddParameter("yzm", message);
            IRestResponse response = client.Execute(request);
            if (response == null || response.StatusCode != System.Net.HttpStatusCode.OK) return;
            Console.WriteLine($"Upload error reports to lianzhong: { System.Web.HttpUtility.UrlDecode(message, System.Text.Encoding.GetEncoding("UTF-8"))}");
        }
    }
}
