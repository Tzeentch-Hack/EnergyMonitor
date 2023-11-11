using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ElectricityEmulator
{
    internal class Consumer
    {
        public string ID { get; private set; }
        public string DeviceName { get; private set; }
        public string Power { get; private set; }
        public DateTime TimeStarted { get; private set; }
        public TimeSpan TimeWorking { get; set; }
        public bool Enabled { get; set; }

        public Consumer(string iD, string deviceName, string power, DateTime timeStarted, 
            TimeSpan timeWorking, bool enabled)
        {
            this.ID = iD;
            this.DeviceName = deviceName;
            this.Power = power;
            this.TimeStarted = timeStarted;
            this.TimeWorking = timeWorking;
            this.Enabled = enabled;
        }

        public string[] ToDataGridRow()
        {
            return new string[] { ID, DeviceName, Power, TimeStarted.ToString(), TimeWorking.ToString() };
        }

        public override string ToString()
        {
            return $"ID: {ID}, Power: {Power}, TimeStarted: {TimeStarted.ToString()}, WorkingTime: {TimeWorking.ToString()}";
        }
    }
}
