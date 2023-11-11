namespace ElectricityEmulator
{
    partial class MainForm
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            DataGrid = new DataGridView();
            IDColumn = new DataGridViewTextBoxColumn();
            NameColumn = new DataGridViewTextBoxColumn();
            EnergyColumn = new DataGridViewTextBoxColumn();
            TimeStartedColumn = new DataGridViewTextBoxColumn();
            TimeWorkingColumn = new DataGridViewTextBoxColumn();
            panel1 = new Panel();
            panel2 = new Panel();
            IntervalLabel = new Label();
            IntervalTextBox = new TextBox();
            StopEmulation = new Button();
            StartEmulation = new Button();
            AddDeviceButton = new Button();
            NameTextBox = new TextBox();
            NameLabel = new Label();
            wattLabel = new Label();
            WtTextBox = new TextBox();
            consoleText = new TextBox();
            ((System.ComponentModel.ISupportInitialize)DataGrid).BeginInit();
            panel1.SuspendLayout();
            panel2.SuspendLayout();
            SuspendLayout();
            // 
            // DataGrid
            // 
            DataGrid.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            DataGrid.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            DataGrid.Columns.AddRange(new DataGridViewColumn[] { IDColumn, NameColumn, EnergyColumn, TimeStartedColumn, TimeWorkingColumn });
            DataGrid.Dock = DockStyle.Fill;
            DataGrid.Location = new Point(0, 0);
            DataGrid.Name = "DataGrid";
            DataGrid.RowHeadersWidth = 51;
            DataGrid.RowTemplate.Height = 29;
            DataGrid.Size = new Size(1290, 338);
            DataGrid.TabIndex = 0;
            DataGrid.KeyDown += DataGrid_KeyDown;
            // 
            // IDColumn
            // 
            IDColumn.HeaderText = "ID";
            IDColumn.MinimumWidth = 6;
            IDColumn.Name = "IDColumn";
            // 
            // NameColumn
            // 
            NameColumn.HeaderText = "Имя";
            NameColumn.MinimumWidth = 6;
            NameColumn.Name = "NameColumn";
            // 
            // EnergyColumn
            // 
            EnergyColumn.HeaderText = "Мощность";
            EnergyColumn.MinimumWidth = 6;
            EnergyColumn.Name = "EnergyColumn";
            // 
            // TimeStartedColumn
            // 
            TimeStartedColumn.HeaderText = "Время начала работы";
            TimeStartedColumn.MinimumWidth = 6;
            TimeStartedColumn.Name = "TimeStartedColumn";
            // 
            // TimeWorkingColumn
            // 
            TimeWorkingColumn.HeaderText = "Время работы";
            TimeWorkingColumn.MinimumWidth = 6;
            TimeWorkingColumn.Name = "TimeWorkingColumn";
            // 
            // panel1
            // 
            panel1.Controls.Add(DataGrid);
            panel1.Location = new Point(12, 12);
            panel1.Name = "panel1";
            panel1.Size = new Size(1290, 338);
            panel1.TabIndex = 1;
            // 
            // panel2
            // 
            panel2.Controls.Add(IntervalLabel);
            panel2.Controls.Add(IntervalTextBox);
            panel2.Controls.Add(StopEmulation);
            panel2.Controls.Add(StartEmulation);
            panel2.Controls.Add(AddDeviceButton);
            panel2.Controls.Add(NameTextBox);
            panel2.Controls.Add(NameLabel);
            panel2.Controls.Add(wattLabel);
            panel2.Controls.Add(WtTextBox);
            panel2.Location = new Point(12, 356);
            panel2.Name = "panel2";
            panel2.Size = new Size(1290, 81);
            panel2.TabIndex = 2;
            // 
            // IntervalLabel
            // 
            IntervalLabel.AutoSize = true;
            IntervalLabel.Font = new Font("Segoe UI", 18F, FontStyle.Bold, GraphicsUnit.Point);
            IntervalLabel.Location = new Point(715, -3);
            IntervalLabel.Name = "IntervalLabel";
            IntervalLabel.Size = new Size(160, 41);
            IntervalLabel.TabIndex = 9;
            IntervalLabel.Text = "Интервал";
            // 
            // IntervalTextBox
            // 
            IntervalTextBox.Location = new Point(715, 47);
            IntervalTextBox.Name = "IntervalTextBox";
            IntervalTextBox.Size = new Size(212, 27);
            IntervalTextBox.TabIndex = 8;
            // 
            // StopEmulation
            // 
            StopEmulation.Font = new Font("Segoe UI Semibold", 12F, FontStyle.Bold, GraphicsUnit.Point);
            StopEmulation.Location = new Point(1113, 3);
            StopEmulation.Name = "StopEmulation";
            StopEmulation.Size = new Size(174, 71);
            StopEmulation.TabIndex = 7;
            StopEmulation.Text = "Остановить эмуляцию";
            StopEmulation.UseVisualStyleBackColor = true;
            StopEmulation.Click += StopEmulation_Click;
            // 
            // StartEmulation
            // 
            StartEmulation.Font = new Font("Segoe UI Semibold", 12F, FontStyle.Bold, GraphicsUnit.Point);
            StartEmulation.Location = new Point(933, 3);
            StartEmulation.Name = "StartEmulation";
            StartEmulation.Size = new Size(174, 71);
            StartEmulation.TabIndex = 6;
            StartEmulation.Text = "Начать эмуляцию";
            StartEmulation.UseVisualStyleBackColor = true;
            StartEmulation.Click += StartEmulation_Click;
            // 
            // AddDeviceButton
            // 
            AddDeviceButton.Font = new Font("Segoe UI Semibold", 12F, FontStyle.Bold, GraphicsUnit.Point);
            AddDeviceButton.Location = new Point(352, 7);
            AddDeviceButton.Name = "AddDeviceButton";
            AddDeviceButton.Size = new Size(174, 71);
            AddDeviceButton.TabIndex = 4;
            AddDeviceButton.Text = "Добавить устройство";
            AddDeviceButton.UseVisualStyleBackColor = true;
            AddDeviceButton.Click += AddDeviceButton_Click;
            // 
            // NameTextBox
            // 
            NameTextBox.Location = new Point(134, 49);
            NameTextBox.Name = "NameTextBox";
            NameTextBox.Size = new Size(212, 27);
            NameTextBox.TabIndex = 3;
            // 
            // NameLabel
            // 
            NameLabel.AutoSize = true;
            NameLabel.Font = new Font("Segoe UI", 18F, FontStyle.Bold, GraphicsUnit.Point);
            NameLabel.Location = new Point(134, 5);
            NameLabel.Name = "NameLabel";
            NameLabel.Size = new Size(82, 41);
            NameLabel.TabIndex = 2;
            NameLabel.Text = "Имя";
            // 
            // wattLabel
            // 
            wattLabel.AutoSize = true;
            wattLabel.Font = new Font("Segoe UI", 18F, FontStyle.Bold, GraphicsUnit.Point);
            wattLabel.Location = new Point(3, 7);
            wattLabel.Name = "wattLabel";
            wattLabel.Size = new Size(51, 41);
            wattLabel.TabIndex = 1;
            wattLabel.Text = "Вт";
            // 
            // WtTextBox
            // 
            WtTextBox.Location = new Point(3, 49);
            WtTextBox.Name = "WtTextBox";
            WtTextBox.Size = new Size(125, 27);
            WtTextBox.TabIndex = 0;
            // 
            // consoleText
            // 
            consoleText.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            consoleText.Location = new Point(0, 455);
            consoleText.Multiline = true;
            consoleText.Name = "consoleText";
            consoleText.Size = new Size(1314, 145);
            consoleText.TabIndex = 3;
            // 
            // MainForm
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1314, 600);
            Controls.Add(consoleText);
            Controls.Add(panel2);
            Controls.Add(panel1);
            Name = "MainForm";
            Text = "Устройства потребления энергии";
            ((System.ComponentModel.ISupportInitialize)DataGrid).EndInit();
            panel1.ResumeLayout(false);
            panel2.ResumeLayout(false);
            panel2.PerformLayout();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private DataGridView DataGrid;
        private Panel panel1;
        private Panel panel2;
        private Label wattLabel;
        private TextBox WtTextBox;
        private TextBox NameTextBox;
        private Label NameLabel;
        private Button AddDeviceButton;
        private Button StartEmulation;
        private Button StopEmulation;
        private DataGridViewTextBoxColumn IDColumn;
        private DataGridViewTextBoxColumn NameColumn;
        private DataGridViewTextBoxColumn EnergyColumn;
        private DataGridViewTextBoxColumn TimeStartedColumn;
        private DataGridViewTextBoxColumn TimeWorkingColumn;
        private Label IntervalLabel;
        private TextBox IntervalTextBox;
        private TextBox consoleText;
    }
}