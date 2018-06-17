/***
 * @pName caffe_task_pool_demo
 * @name Form1
 * @user wadezh
 * @date 2018/6/16
 * @desc
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace caffe_task_pool_demo
{
    public partial class Form1 : Form
    {
       

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            if (!CC.InitCaptcha("./deploy.prototxt", "./_iter_4000.caffemodel", "./label-map.txt", -1, 1))
            {
                MessageBox.Show("Load faild!");
            }
        }

        private void btnClassing_Click(object sender, EventArgs e)
        {
            for (int i = 0; i < 8; i++)
            {
                Task.Run(()=>
                {
                    Stopwatch st = new Stopwatch();
                    string code = string.Empty;
                    lock (this)
                    {
                        MemoryStream ms = new MemoryStream();
                        this.Invoke((EventHandler)delegate {
                            pictureBox1.Image.Save(ms, System.Drawing.Imaging.ImageFormat.Jpeg);
                        });
                        byte[] image = ms.ToArray();


                        st = new Stopwatch();
                        st.Start();
                        code = CC.GetCaptcha(image);
                        st.Stop();

                    }
                    
                    this.Invoke((EventHandler)delegate {
                        listBox1.Items.Add($"result: {code} , when: {st.ElapsedMilliseconds.ToString()}ms");
                    });
                });
            }
        }


    }

    
}
