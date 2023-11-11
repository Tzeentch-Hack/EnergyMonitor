using Microsoft.VisualBasic;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ElectricityEmulator
{
    internal class ElectricityEmulatorCore
    { 
        public event Action<List<Consumer>>? OnTick;

        private List<Consumer> consumers = new List<Consumer>();

        private System.Windows.Forms.Timer timer;
        
        public int Interval { get; set; }

        public ElectricityEmulatorCore(int interval)
        {
            this.Interval = interval;
            timer = new System.Windows.Forms.Timer();
            timer.Interval = Interval;
            timer.Tick += Emulate;
        }

        private void Emulate(object? sender, EventArgs e)
        {
            foreach (var consumer in consumers)
            {
                if (consumer.Enabled)
                {
                    TimeSpan delta = DateTime.Now - consumer.TimeStarted;
                    consumer.TimeWorking = delta;
                    OnTick?.Invoke(consumers);
                    // Send message to server
                    using HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, "https://www.google.com");
                }
            }
        }

        public void AddConsumer(Consumer consumer)
        {
            consumers.Add(consumer);
        }

        public void DeleteConsumer(string consumerID)
        {
            foreach (var consumer in consumers)
            {
                if (consumer.ID == consumerID)
                {
                    //consumers.Remove(consumer);
                    consumer.Enabled = false;
                    return;
                }
            }
        }

        public void SetInterval(int interval)
        {
            this.Interval = interval;
        }

        public void StartEmulation()
        {
            timer.Start();
        }

        public void StopEmulation()
        {
            timer.Stop();
        }
    }
}
