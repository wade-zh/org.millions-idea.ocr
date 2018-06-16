namespace CnnDemo
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.btnClassing = new System.Windows.Forms.Button();
            this.lbCaptcha = new System.Windows.Forms.Label();
            this.btnSetNet = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(133, 77);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(130, 53);
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            // 
            // btnClassing
            // 
            this.btnClassing.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnClassing.Location = new System.Drawing.Point(315, 249);
            this.btnClassing.Name = "btnClassing";
            this.btnClassing.Size = new System.Drawing.Size(75, 23);
            this.btnClassing.TabIndex = 1;
            this.btnClassing.Text = "图像分类";
            this.btnClassing.UseVisualStyleBackColor = true;
            this.btnClassing.Click += new System.EventHandler(this.btnClassing_Click);
            // 
            // lbCaptcha
            // 
            this.lbCaptcha.AutoSize = true;
            this.lbCaptcha.Font = new System.Drawing.Font("微软雅黑", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lbCaptcha.Location = new System.Drawing.Point(160, 142);
            this.lbCaptcha.Name = "lbCaptcha";
            this.lbCaptcha.Size = new System.Drawing.Size(52, 28);
            this.lbCaptcha.TabIndex = 3;
            this.lbCaptcha.Text = "****";
            // 
            // btnSetNet
            // 
            this.btnSetNet.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnSetNet.Location = new System.Drawing.Point(234, 249);
            this.btnSetNet.Name = "btnSetNet";
            this.btnSetNet.Size = new System.Drawing.Size(75, 23);
            this.btnSetNet.TabIndex = 4;
            this.btnSetNet.Text = "设置网络";
            this.btnSetNet.UseVisualStyleBackColor = true;
            this.btnSetNet.Click += new System.EventHandler(this.btnSetNet_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(402, 284);
            this.Controls.Add(this.btnSetNet);
            this.Controls.Add(this.lbCaptcha);
            this.Controls.Add(this.btnClassing);
            this.Controls.Add(this.pictureBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "CaffeInvokeDemo";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button btnClassing;
        private System.Windows.Forms.Label lbCaptcha;
        private System.Windows.Forms.Button btnSetNet;
    }
}

