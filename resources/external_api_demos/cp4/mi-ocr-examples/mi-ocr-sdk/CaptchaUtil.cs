using Newtonsoft.Json;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace org.millions.idea.ocr
{
    public class CaptchaUtil
    {
        private const string PLATFROM_URL = "http://193.112.151.148:20001";

        /// <summary>
        /// 登录
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        /// <returns>返回token</returns>
        public static string Login(string username, string password, out string key) {
            key = null;
            var client = new RestClient(PLATFROM_URL + "/user-api/login");
            var request = new RestRequest(Method.POST);
            request.AddHeader("Cache-Control", "no-cache");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("undefined", $"uname={username}&pwd={password}", ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
            HttpResp httpResp = JsonConvert.DeserializeObject<HttpResp>(System.Text.Encoding.UTF8.GetString(response.RawBytes));
            if (httpResp == null) {
                return JsonConvert.SerializeObject(new HttpResp() {
                    error = -1,
                    msg = System.Text.Encoding.UTF8.GetString(System.Text.Encoding.UTF8.GetBytes(response.ErrorMessage))
                });
            }
            if (httpResp.error == 0) key = httpResp.msg;
            return System.Text.Encoding.UTF8.GetString(response.RawBytes);
        }

        public class HttpResp {
            public int error { get; set; }
            public string msg { get; set; }
        }
    }
}
