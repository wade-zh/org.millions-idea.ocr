namespace caffe_train_callback
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.listBox1 = new System.Windows.Forms.ListBox();
            this.btnInitLmdb = new System.Windows.Forms.Button();
            this.btnTrain = new System.Windows.Forms.Button();
            this.btnContinueTrain = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // listBox1
            // 
            this.listBox1.FormattingEnabled = true;
            this.listBox1.ItemHeight = 12;
            this.listBox1.Location = new System.Drawing.Point(12, 71);
            this.listBox1.Name = "listBox1";
            this.listBox1.Size = new System.Drawing.Size(776, 364);
            this.listBox1.TabIndex = 0;
            // 
            // btnInitLmdb
            // 
            this.btnInitLmdb.Location = new System.Drawing.Point(199, 28);
            this.btnInitLmdb.Name = "btnInitLmdb";
            this.btnInitLmdb.Size = new System.Drawing.Size(108, 23);
            this.btnInitLmdb.TabIndex = 1;
            this.btnInitLmdb.Text = "生成LMDB数据库";
            this.btnInitLmdb.UseVisualStyleBackColor = true;
            this.btnInitLmdb.Click += new System.EventHandler(this.btnInitLmdb_Click);
            // 
            // btnTrain
            // 
            this.btnTrain.Location = new System.Drawing.Point(326, 28);
            this.btnTrain.Name = "btnTrain";
            this.btnTrain.Size = new System.Drawing.Size(108, 23);
            this.btnTrain.TabIndex = 2;
            this.btnTrain.Text = "开始训练";
            this.btnTrain.UseVisualStyleBackColor = true;
            this.btnTrain.Click += new System.EventHandler(this.btnTrain_Click);
            // 
            // btnContinueTrain
            // 
            this.btnContinueTrain.Location = new System.Drawing.Point(455, 28);
            this.btnContinueTrain.Name = "btnContinueTrain";
            this.btnContinueTrain.Size = new System.Drawing.Size(108, 23);
            this.btnContinueTrain.TabIndex = 3;
            this.btnContinueTrain.Text = "继续训练";
            this.btnContinueTrain.UseVisualStyleBackColor = true;
            this.btnContinueTrain.Click += new System.EventHandler(this.btnContinueTrain_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.btnContinueTrain);
            this.Controls.Add(this.btnTrain);
            this.Controls.Add(this.btnInitLmdb);
            this.Controls.Add(this.listBox1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListBox listBox1;
        private System.Windows.Forms.Button btnInitLmdb;
        private System.Windows.Forms.Button btnTrain;
        private System.Windows.Forms.Button btnContinueTrain;
    }
}

