using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CnnDemo
{
    public partial class Form1 : Form
    {
        private int taskPool = 0;

        public Form1()
        {
            InitializeComponent();
        }

        private void btnSetNet_Click(object sender, EventArgs e)
        {
            byte[] prototxt = Util.GetFileStream("./deploy.prototxt");
            byte[] model = Util.GetFileStream("./_iter_80000.caffemodel");
            taskPool = CC.CreateTaskPoolByData(prototxt, prototxt.Length, model, model.Length, 1F, "", 0, 0F, 0, 5);
        }

        private void btnClassing_Click(object sender, EventArgs e)
        {
            string[] mapFile = Util.LoadStringFromFile("./label-map.txt").Trim().Split("\r\n".ToArray());
            ArrayList map = new ArrayList();
            for (int i = 0; i < mapFile.Length; i++)
            {
                if (mapFile[i].Length > 0)
                {
                    map.Add(mapFile[i]);
                }
            }
            string time_step = Util.GetMiddleString(Util.LoadStringFromFile("./deploy.prototxt"), "time_step:", "\r\n");
            string alphabet_size = Util.GetMiddleString(Util.LoadStringFromFile("./deploy.prototxt"), "num_output:", "\r\n");

            // Get the prediction result handle
            byte[] image = Util.GetFileStream("./AAEN_7ADF3BAC15C51B49A1323CD7FE69D091.JPG");
            int poolHandle = CC.ForwardByTaskPool(taskPool, image, image.Length, "premuted_fc");


            // Get the tensor handle
            float[] permute_fc = new float[CC.GetBlobLength(poolHandle)];
            

            // Copy the tensor data
            CC.CpyBlobData(permute_fc, poolHandle);
            string code = string.Empty;

            if (permute_fc.Length > 0)
            {
                int step = int.Parse(time_step);
                // TODO The number here is 37
                //int alphabet = int.Parse(alphabet_size);
                int alphabet = 37;
                int o = 0;
                float acc = 0F;
                int emptyLabel = alphabet - 1;
                int prev = emptyLabel;
                for (int i = 1; i < step; i++)
                {
                    o = Util.Argmax(permute_fc, (i - 1) * alphabet + 1, i * alphabet, ref acc);
                    if (o != emptyLabel && prev!=o) code += map[o + 1];
                    prev = o;
                }
                code = code.Replace("_","").Trim();
            }

            CC.ReleaseBlobData(poolHandle);

            MessageBox.Show(code);
        }

        
    }

    public class Util
    {

    public static int Argmax(float[] arr, int begin, int end, ref float acc)
    {
        acc = -9999;
        int mxInd = 0; 
        for (int i = begin; i < end; i++)
        {
            if (acc < arr[i])
            {
                mxInd = i;
                acc = arr[i];
            }
        }
        return mxInd - begin;
    }

    public static byte[] GetFileStream(string path) {
            FileStream fs = new FileStream(path, FileMode.Open);
            long size = fs.Length;
            byte[] array = new byte[size];
            fs.Read(array, 0, array.Length);
            fs.Close();
            return array;
        }


        public static string LoadStringFromFile(string fileName)
        {
            string content = string.Empty;

            StreamReader sr = null;
            try
            {
                sr = new StreamReader(fileName, System.Text.Encoding.UTF8);
                content = sr.ReadToEnd();
            }
            catch (Exception ex)
            {
                throw ex;
            }

            if (sr != null)
                sr.Close();

            return content;
        }



        public static string GetMiddleString(string SumString, string LeftString, string RightString)
        {
            if (string.IsNullOrEmpty(SumString)) return "";
            if (string.IsNullOrEmpty(LeftString)) return "";
            if (string.IsNullOrEmpty(RightString)) return "";

            int LeftIndex = SumString.IndexOf(LeftString);
            if (LeftIndex == -1) return "";
            LeftIndex = LeftIndex + LeftString.Length;
            int RightIndex = SumString.IndexOf(RightString, LeftIndex);
            if (RightIndex == -1) return "";
            return SumString.Substring(LeftIndex, RightIndex - LeftIndex);
        }

    }
}
