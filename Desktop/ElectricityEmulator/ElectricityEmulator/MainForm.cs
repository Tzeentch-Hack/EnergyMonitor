using System.Windows.Forms;

namespace ElectricityEmulator
{
    public partial class MainForm : Form
    {
        private ElectricityEmulatorCore electricityEmulatorCore;

        public MainForm()
        {
            InitializeComponent();

            electricityEmulatorCore = new ElectricityEmulatorCore(1000);
            IntervalTextBox.Text = "1000";
            electricityEmulatorCore.OnTick += UpdateData;
        }

        private void AddDeviceButton_Click(object sender, EventArgs e)
        {
            string newDeviceId = Utils.GenerateId();
            string newDeviceName = NameTextBox.Text.Trim();
            if (newDeviceName.Length < 3)
            {
                MessageBox.Show("Длина имени устройства должна быть больше 2 символов", "Ошибка!");
                return;
            }
            string newDevicePower = WtTextBox.Text.Trim();
            if (newDevicePower.Length == 0)
            {
                MessageBox.Show("Не указана мощность устройства", "Ошибка!");
            }
            float power;
            bool isNumeric = float.TryParse(newDevicePower, out power);
            if (!isNumeric)
            {
                MessageBox.Show("Указано некорректное числовое значение", "Ошибка!");
            }

            Consumer newDevice = new Consumer(newDeviceId, newDeviceName, newDevicePower, DateTime.Now,
                new TimeSpan(0), enabled: true);
            electricityEmulatorCore.AddConsumer(newDevice);
            AddConsumerToDataGrid(newDevice);
        }

        private void AddConsumerToDataGrid(Consumer consumer)
        {
            DataGrid.Rows.Add(consumer.ToDataGridRow());
        }

        private void StartEmulation_Click(object sender, EventArgs e)
        {
            int interval;
            bool isParsed = int.TryParse(IntervalTextBox.Text.Trim(), out interval);
            if (!isParsed)
            {
                MessageBox.Show("Неккоретный интервал", "Ошибка!");
            }
            electricityEmulatorCore.Interval = interval;
            electricityEmulatorCore.StartEmulation();
        }

        private void StopEmulation_Click(object sender, EventArgs e)
        {
            electricityEmulatorCore.StopEmulation();
        }

        private void UpdateData(List<Consumer> consumers)
        {
            foreach (var consumer in consumers)
            {
                TypeInConsole(consumer.ToString());
            }
        }

        private void DataGrid_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete)
            {
                if (DataGrid.SelectedRows.Count > 0)
                {
                    DataGrid.Rows.RemoveAt(DataGrid.SelectedRows[0].Index);
                }
                else if (DataGrid.SelectedCells.Count > 0)
                {
                    DataGrid.Rows.RemoveAt(DataGrid.SelectedCells[0].RowIndex);
                }
            }
        }

        private void TypeInConsole(string value)
        {
            consoleText.Text += value + '\n';
        }
    }
}