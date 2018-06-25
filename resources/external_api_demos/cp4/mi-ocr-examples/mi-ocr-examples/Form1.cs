using Newtonsoft.Json;
using org.millions.idea.ocr;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace mi_ocr_examples
{
    public partial class Form1 : Form
    {
        private string USER_KEY = string.Empty;

        public Form1()
        {
            InitializeComponent();
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            string resp = CaptchaUtil.Login(this.txtUname.Text, this.txtPwd.Text, out USER_KEY);
            if (USER_KEY == null) {
                CaptchaUtil.HttpResp httpResp = JsonConvert.DeserializeObject<CaptchaUtil.HttpResp>(resp);
                MessageBox.Show(httpResp.msg);
            }
        }
    }
}
