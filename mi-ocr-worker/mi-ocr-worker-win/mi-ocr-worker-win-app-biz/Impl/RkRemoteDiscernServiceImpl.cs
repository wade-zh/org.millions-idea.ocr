using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;
using Newtonsoft.Json;
using RestSharp;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class RkRemoteDiscernServiceImpl : BaseDiscernServiceImpl, IRkRemoteDiscernService
    {
        public override void OnNority(Captcha captcha, byte[] binary, Action<string> call)
        {
            if (!captcha.Channel.ToUpper().StartsWith("R"))
            {
                call(null);
                return;
            }

            #region upload
            IRestResponse response = upload(captcha, binary);
            if (response == null || response.StatusCode != System.Net.HttpStatusCode.OK || !response.Content.Contains("Result"))
            {
                call(null);
                return;
            }
            RkCaptcha rkCaptcha = JsonConvert.DeserializeObject<RkCaptcha>(response.Content);
            Console.WriteLine(response.Content);
            #endregion

            SharedResult sr = new SharedResult()
            {
                Ticket = captcha.Ticket,
                Id = rkCaptcha.Id,
                Result = rkCaptcha.Result
            };
            call(JsonConvert.SerializeObject(sr));
        }

        private IRestResponse upload(Captcha captcha, byte[] binary)
        {
            var client = new RestClient("http://api.ruokuai.com/create.json");
            var request = new RestRequest(Method.POST);
            request.AddParameter("username", RemoteUser.RuoKuai);
            request.AddParameter("password", Constant.RemotePassword);
            request.AddParameter("typeid", captcha.Channel.Substring(captcha.Channel.Length - 4, 4));
            request.AddParameter("softid", RemoteUser.RuoKuaiSoftId);
            request.AddParameter("softkey", RemoteUser.RuoKuaiSoftKey);
            request.AddFileBytes("image", binary, "captcha.jpg", "image/jpeg");
            return client.Execute(request);
        }
    }
}
