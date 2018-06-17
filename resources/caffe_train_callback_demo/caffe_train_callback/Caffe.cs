/***
 * @pName caffe_task_pool_demo
 * @name CC
 * @user wadezh
 * @date 2018/6/16
 * @desc
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace org.millions.idea.caffe
{
    class Caffe
    {

        public static int taskPool { get; set; } = 0;
        public static string prototxt { get; set; }
        public static ArrayList map { get; set; }
        public static int timeStep { get; set; }
        public static int alphabetSize { get; set; }

        /*Caffe_API TaskPool* __stdcall createTaskPoolByData(

        const void* prototxt_data,

        int prototxt_data_length,

        const void* caffemodel_data,

        int caffemodel_data_length,

        float scale_raw = 1,

        const char* mean_file = 0,

        int num_means = 0,

        float* means = 0,

        int gpu_id = -1,

        int batch_size = 3);*/

        [DllImport("classification_dll.dll", EntryPoint = "createTaskPoolByData", CallingConvention = CallingConvention.StdCall)] 
        public static extern int CreateTaskPoolByData(byte[] prototxt_data,
        int prototxt_data_length,
        byte[] caffemodel_data,
        int caffemodel_data_length,
        float scale_raw = 1,
        string mean_file = "",
        int num_means = 0,
        float means = 0,
        int gpu_id = -1,
        int cach_size = 1);


        /*Caffe_API BlobData* __stdcall forwardByTaskPool(TaskPool* pool, const void* img, int len, const char* blob_name);*/

        [DllImport("classification.dll", EntryPoint = "forwardByTaskPool", CallingConvention = CallingConvention.StdCall)]
        public static extern int ForwardByTaskPool(int poolHandle, byte[] image, int imageLen, string printBlobName);

        /*Caffe_API int __stdcall getBlobLength(BlobData* feature);*/
        [DllImport("classification.dll", EntryPoint = "getBlobLength", CallingConvention = CallingConvention.StdCall)]
        public static extern int GetBlobLength(int feature);

        /*Caffe_API void __stdcall cpyBlobData(void* buffer, BlobData* feature);*/
        [DllImport("classification.dll", EntryPoint = "cpyBlobData", CallingConvention = CallingConvention.StdCall)]
        public static extern int CpyBlobData(float[] buffer, int feature);

        /*Caffe_API void  __stdcall releaseBlobData(BlobData* ptr);*/
        [DllImport("classification.dll", EntryPoint = "releaseBlobData", CallingConvention = CallingConvention.StdCall)]
        public static extern int ReleaseBlobData(int ptr);

        private static int Argmax(float[] arr, int begin, int end, ref float acc)
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


        public static bool InitCaptcha(string prototxtPath, string modelPath, string mapPath, int gpuId, int batchSize) {
            byte[] deploy = Util.GetFileStream(prototxtPath);
            byte[] model = Util.GetFileStream(modelPath);
            Caffe.taskPool = Caffe.CreateTaskPoolByData(deploy, deploy.Length, model, model.Length, 1F, "", 0, 0F, gpuId, batchSize);
            Caffe.prototxt = System.Text.Encoding.Default.GetString(deploy);
            string[] mapFile = Util.LoadStringFromFile(mapPath).Trim().Split("/r/n".ToArray());
            Caffe.map = new ArrayList();
            for (int i = 0; i < mapFile.Length; i++)
            {
                if (mapFile[i].Length > 0)
                {
                    Caffe.map.Add(mapFile[i]);
                }
            }
            string time_step = Util.GetMiddleString(Caffe.prototxt, "time_step:", "/r/n");
            string layer = Util.GetMiddleString(Caffe.prototxt, "inner_product_param {", "{");
            string alphabet_size = Util.GetMiddleString(layer, "num_output:", "/r/n");
            Caffe.timeStep = int.Parse(time_step);
            Caffe.alphabetSize = int.Parse(alphabet_size);
            return Caffe.taskPool != 0;
        }


        public static string GetCaptcha(byte[] image) {
            // Get the prediction result handle
            int poolHandle = Caffe.ForwardByTaskPool(taskPool, image, image.Length, "premuted_fc");

            // Get the tensor handle
            float[] permute_fc = new float[Caffe.GetBlobLength(poolHandle)];

            // Copy the tensor data
            CpyBlobData(permute_fc, poolHandle);
            string code = string.Empty;

            if (permute_fc.Length > 0)
            {
                int o = 0;
                float acc = 0F;
                int emptyLabel = alphabetSize - 1;
                int prev = emptyLabel;
                for (int i = 1; i < timeStep; i++)
                {
                    o = Argmax(permute_fc, (i - 1) * alphabetSize + 1, i * alphabetSize, ref acc);
                    if (o != emptyLabel && prev != o) code += map[o + 1];
                    prev = o;
                }
                code = code.Replace("_", "").Trim();
            }

            ReleaseBlobData(poolHandle);
            return code;
        }

        protected class Util
        {



            public static byte[] GetFileStream(string path)
            {
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



        [UnmanagedFunctionPointer(CallingConvention.StdCall)]
        public unsafe delegate int ConvertImageSetEventCallback(int eventFlag, int param1, float param2, void* param3);

        [DllImport(@"D:\caffe_train_components\bin-cpu\classification_dll.dll", EntryPoint = "convert_imageset", CallingConvention = CallingConvention.StdCall)]
        public static extern int ConvertImageset(string param, ConvertImageSetEventCallback callback);



        [UnmanagedFunctionPointer(CallingConvention.StdCall)]
        public unsafe delegate int TraindEventCallback(int eventFlag, int param1, float param2, void* param3);


        [DllImport(@"D:\caffe_train_components\bin-gpu-cuda8.0\classification_dll.dll", EntryPoint = "setTraindEventCallback", CallingConvention = CallingConvention.StdCall)]
        public static extern void setTraindEventCallback(TraindEventCallback callback);

        [DllImport(@"D:\caffe_train_components\bin-gpu-cuda8.0\classification_dll.dll", EntryPoint = "train_network", CallingConvention = CallingConvention.StdCall)]
        public static extern int train_network(string param);
    }

}
