using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mi_ocr_worker_win_app_entity;
using FastVerCode;
using RestSharp;
using Newtonsoft.Json;

namespace mi_ocr_worker_win_app_biz.Impl
{
    public class LzRemoteDiscernServiceImpl : BaseDiscernServiceImpl, ILzRemoteDiscernService
    {
        public override void OnNority(Captcha captcha, byte[] binary, Action<string> call)
        {
            if (!captcha.Channel.ToUpper().StartsWith("J")) {
                call(null);
                return;
            }

            #region upload
            IRestResponse response = upload(captcha, binary);
            if (response == null || response.StatusCode != System.Net.HttpStatusCode.OK || !response.Content.Contains("true"))
            {
                call(null);
                return;
            }
            LzCaptcha lzCaptcha = JsonConvert.DeserializeObject<LzCaptcha>(response.Content);
            Console.WriteLine(response.Content);
            #endregion

            SharedResult sr = new SharedResult()
            {
                Ticket = captcha.Ticket,
                Id = lzCaptcha.data.id.ToString(),
                Result = lzCaptcha.data.val
            };
            call(JsonConvert.SerializeObject(sr));
        }

        private IRestResponse upload(Captcha captcha, byte[] binary) {
            var client = new RestClient("http://v1-http-api.jsdama.com/api.php?mod=php&act=upload");
            var request = new RestRequest(Method.POST);
            request.AddParameter("user_name", RemoteUser.LianZhong);
            request.AddParameter("user_pw", Constant.RemotePassword);
            request.AddFileBytes("upload", binary, "captcha.jpg", "image/jpeg");
            request.AddParameter("yzmtype_mark", captcha.Channel.Substring(captcha.Channel.Length - 4, 4));
            request.AddParameter("yzm_minlen", "4");
            request.AddParameter("yzm_maxlen", "4");
            request.AddParameter("zztool_token", RemoteUser.LianZhong);
            return client.Execute(request);
        }
    }
}
