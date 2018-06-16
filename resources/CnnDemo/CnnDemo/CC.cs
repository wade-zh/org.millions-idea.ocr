using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace CnnDemo
{
    class CC
    {

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

        [DllImport("classification_dll.dll", EntryPoint = "forwardByTaskPool", CallingConvention = CallingConvention.StdCall)]
        public static extern int ForwardByTaskPool(int poolHandle, byte[] image, int imageLen, string printBlobName);

        /*Caffe_API int __stdcall getBlobLength(BlobData* feature);*/
        [DllImport("classification_dll.dll", EntryPoint = "getBlobLength", CallingConvention = CallingConvention.StdCall)]
        public static extern int GetBlobLength(int feature);

        /*Caffe_API void __stdcall cpyBlobData(void* buffer, BlobData* feature);*/
        [DllImport("classification_dll.dll", EntryPoint = "cpyBlobData", CallingConvention = CallingConvention.StdCall)]
        public static extern int CpyBlobData(float[] buffer, int feature);

        /*Caffe_API void  __stdcall releaseBlobData(BlobData* ptr);*/
        [DllImport("classification_dll.dll", EntryPoint = "releaseBlobData", CallingConvention = CallingConvention.StdCall)]
        public static extern int ReleaseBlobData(int ptr);
    }
}
