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
            this.btnInitLmdb = new System.Windows.Forms.Button();
            this.btnTrain = new System.Windows.Forms.Button();
            this.btnContinueTrain = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // btnInitLmdb
            // 
            this.btnInitLmdb.Location = new System.Drawing.Point(12, 81);
            this.btnInitLmdb.Name = "btnInitLmdb";
            this.btnInitLmdb.Size = new System.Drawing.Size(108, 23);
            this.btnInitLmdb.TabIndex = 1;
            this.btnInitLmdb.Text = "生成LMDB数据库";
            this.btnInitLmdb.UseVisualStyleBackColor = true;
            this.btnInitLmdb.Click += new System.EventHandler(this.btnInitLmdb_Click);
            // 
            // btnTrain
            // 
            this.btnTrain.Location = new System.Drawing.Point(139, 81);
            this.btnTrain.Name = "btnTrain";
            this.btnTrain.Size = new System.Drawing.Size(108, 23);
            this.btnTrain.TabIndex = 2;
            this.btnTrain.Text = "开始训练";
            this.btnTrain.UseVisualStyleBackColor = true;
            this.btnTrain.Click += new System.EventHandler(this.btnTrain_Click);
            // 
            // btnContinueTrain
            // 
            this.btnContinueTrain.Location = new System.Drawing.Point(268, 81);
            this.btnContinueTrain.Name = "btnContinueTrain";
            this.btnContinueTrain.Size = new System.Drawing.Size(108, 23);
            this.btnContinueTrain.TabIndex = 3;
            this.btnContinueTrain.Text = "继续训练";
            this.btnContinueTrain.UseVisualStyleBackColor = true;
            this.btnContinueTrain.Click += new System.EventHandler(this.btnContinueTrain_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(401, 84);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(41, 12);
            this.label1.TabIndex = 4;
            this.label1.Text = "label1";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(448, 81);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(100, 21);
            this.textBox1.TabIndex = 5;
            // 
            // textBox2
            // 
            this.textBox2.Location = new System.Drawing.Point(12, 13);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(181, 21);
            this.textBox2.TabIndex = 6;
            this.textBox2.Text = "_iter_4500.caffemodel";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(570, 177);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnContinueTrain);
            this.Controls.Add(this.btnTrain);
            this.Controls.Add(this.btnInitLmdb);
            this.Name = "Form1";
            this.Text = "Form1";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button btnInitLmdb;
        private System.Windows.Forms.Button btnTrain;
        private System.Windows.Forms.Button btnContinueTrain;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.TextBox textBox2;
    }
}

