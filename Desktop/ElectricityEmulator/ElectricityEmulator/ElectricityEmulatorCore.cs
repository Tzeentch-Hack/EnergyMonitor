using ElectricityEmulator.Models;
using Microsoft.VisualBasic;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using ElectricityEmulator.Gateway;


namespace ElectricityEmulator.Core
{
    internal class ElectricityEmulatorCore
    {
        private StatisticsGateway gateway;

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
            gateway = new StatisticsGateway();
        }

        private async void Emulate(object? sender, EventArgs e)
        {
            List<ConsumerData> sendingList = new List<ConsumerData>();
            foreach (var consumer in consumers)
            {
                if (consumer.Enabled)
                {
                    TimeSpan delta = DateTime.Now - consumer.TimeStarted;
                    consumer.TimeWorking = delta;
                    double power = double.Parse(consumer.Power);
                    double randomValue = GenerateRandomNormal(0.05 * power, 0.05 * power);
                    power += randomValue;
                    ConsumerData consumerData = new ConsumerData()
                    {
                        username = "SherAlex",
                        consumer_id = consumer.ID,
                        enabled = consumer.Enabled.ToString(),
                        device_name = consumer.DeviceName,
                        started_time = consumer.TimeStarted.ToString(),
                        working_time = consumer.TimeWorking.TotalMilliseconds.ToString(),
                        watt_consumption = power.ToString(),
                        sum_consumption = "0",
                        consumption_summary = "0"
                    };
                }
            }
            OnTick?.Invoke(consumers);

            // Send message to server
            ConsumerList data = new ConsumerList()
            {
                data = sendingList
            };
            await gateway.PostUpdate(data);
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
        private double GenerateRandomNormal(double mean, double stdDev)
        {
            Random rand = new Random();
            double u1 = 1.0 - rand.NextDouble();
            double u2 = 1.0 - rand.NextDouble();
            double randStdNormal = Math.Sqrt(-2.0 * Math.Log(u1)) * Math.Sin(2.0 * Math.PI * u2);

            double randNormal = mean + stdDev * randStdNormal;

            return randNormal;
        }
    }
}
