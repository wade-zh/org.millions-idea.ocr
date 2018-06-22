using org.millions.idea.caffe;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.ExceptionServices;
using System.Security;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace caffe_train_callback
{
    public partial class Form1 : Form
    {

        private int step = 500;
        private int interation = 500;
        private bool exit = false;

        public Form1()
        {
            InitializeComponent();
        }

        private void btnInitLmdb_Click(object sender, EventArgs e)
        {
            Thread t = new Thread(new ThreadStart(ConvertImageSet));
            t.Start();
        }
        public unsafe void ConvertImageSet()
        {
            Caffe.ConvertImageset("./ label-train.txt train_lmdb --shuffle=true --gray=true --resize_width=160 --resize_height=60", new Caffe.ConvertImageSetEventCallback(ConvertImageSetCallback));
            Caffe.ConvertImageset("./ label-val.txt val_lmdb --shuffle=true --gray=true --resize_width=160 --resize_height=60", new Caffe.ConvertImageSetEventCallback(ConvertImageSetCallback));
        }

        const int event_readlabel = 1;
        const int event_shuffle = 2;
        const int event_initdb = 3;
        const int event_put_one = 4;
        const int event_err_one = 5;
        const int event_finish = 6;
        public unsafe int ConvertImageSetCallback(int eventFlag, int param1, float param2, void* param3)
        {
            switch (eventFlag)
            {
                case event_readlabel:
                    Log($"读了标签文件啦，总共有{param1}个");
                    break;

                case event_shuffle:
                    Log("做了随机打乱了哟~");
                    break;

                case event_initdb:
                    Log($"开始初始化lmdb啦，宽度是{param1}，高度是{(int)param2}，目录路径是：{ new string((sbyte*)param3)}");
                    break;

                case event_put_one:
                    Log($"处理了一个，共处理了{param1}个，当前处理到了第{(int)param2}个");
                    break;

                case event_err_one:
                    Log($"错误了一个，已经处理了{param1}个，当前处理到了第{(int)param2}个，错误的文件路径：{new string((sbyte*)param3)}");
                    break;

                case event_finish:
                    Log($"转换完毕啦，总共处理了{param1}个文件");
                    break;
            }
            return 0;
        }


        private void Log(string log, params object[] param) {
            if (exit) return;
            this.Invoke((EventHandler)delegate {
                listBox1.Items.Add(log);
            });
        }

        private unsafe void btnTrain_Click(object sender, EventArgs e)
        {
            Caffe.TraindEventCallback func = new Caffe.TraindEventCallback(trainCallback);
            Caffe.setTraindEventCallback(func);
            new Thread(new ThreadStart(() => {
                Caffe.train_network("train --solver=solver.prototxt");
            })).Start();
        } 

        public struct TrainValInfo
        {
            public int iterNum;
            public int numOutput;
            public float[] values;
            public string[] outputNames;
            public override string ToString()
            {
                string ot = "iterNum: " + iterNum + ", numOutput: " + numOutput + ", values: [";
                for (int i = 0; i < numOutput; ++i)
                    ot += values[i] + (i == numOutput - 1 ? "" : ", ");
                ot += "], outputNames: [";

                for (int i = 0; i < numOutput; ++i)
                    ot += outputNames[i] + (i == numOutput - 1 ? "" : ", ");
                ot += "]";
                return ot;
            }
        }

        public unsafe TrainValInfo getInfo(void* param)
        {
            TrainValInfo info = new TrainValInfo();
            sbyte* p = (sbyte*)param;
            info.iterNum = *(int*)p; p += 4;
            info.numOutput = *(int*)p; p += 4;

            float* values = *(float**)p; p += sizeof(void*);
            if (info.numOutput > 0)
            {
                info.values = new float[info.numOutput];
                for (int i = 0; i < info.numOutput; ++i)
                    info.values[i] = values[i];

                sbyte** names = *(sbyte***)(p);
                info.outputNames = new string[info.numOutput];
                for (int i = 0; i < info.numOutput; ++i)
                    info.outputNames[i] = new string(names[i]);
            }
            return info;
        }


        public unsafe int trainCallback(int eventFlag, int param1, float param2, void* param3)
        {
            /*
             * #训练事件_初始化网络 1
             * #训练事件_初始化网络完成 2
             * #训练事件_完成一次迭代 3
             * #训练事件_测试开始 6
             * #训练事件_测试完毕 7
             * #训练事件_快照完毕 5
             * #训练事件_训练完成 4
             * #控制信号_无操作 0
             * #控制信号_停止训练 2
             * #控制信号_快照 1
             * 
            */

            switch (eventFlag)
            {
                case 1:
                    Log("初始化网络");
                    break;
                case 2:
                    Log("初始化网络完成");
                    break;
                case 3:
                    if (exit) return 2;
                    if (param1 == interation)
                    {
                        interation += step;
                        Log($"完成一次迭代，次数：{param1}, 损失：{param2}");
                        return 1;
                    }
                    break;
                case 6:
                    Log("测试开始");
                    break;
                case 7:
                    TrainValInfo info = getInfo(param3);
                    if (info.values[0] > 0.99)
                    {
                        Log($"高精度出现，第{param1}次迭代，accuracy：{info.values[0]}，ctc_loss = {info.values[1]} (* 1 = {info.values[1]} loss)");
                        return 1;
                    }
                    if (info.values[0] == 1F)
                    {
                        Log($"超高精度出现，第{param1}次迭代，accuracy：{info.values[0]}，ctc_loss = {info.values[1]} (* 1 = {info.values[1]} loss)");
                        return 1;
                    }
                    Log($"测试完毕，accuracy：{info.values[0]}，ctc_loss = {info.values[1]} (* 1 = {info.values[1]} loss)");
                    break;
                case 5:
                    Log("快照完毕");
                    break;
                case 4:
                    Log("训练完成");
                    break;
            }
            return 0;
        }

        private unsafe void btnContinueTrain_Click(object sender, EventArgs e)
        {
            Caffe.TraindEventCallback func = new Caffe.TraindEventCallback(trainCallback);
            Caffe.setTraindEventCallback(func);
            new Thread(new ThreadStart(()=> {
                Caffe.train_network($"train --solver=solver.prototxt --weights=models/" + this.textBox2.Text);
            })).Start();
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            exit = true;
        }
    }
}
