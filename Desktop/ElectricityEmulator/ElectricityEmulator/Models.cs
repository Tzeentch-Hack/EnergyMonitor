using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ElectricityEmulator.Models
{
    public struct ConsumerList
    {
        public List<ConsumerData> data;
    }

    public struct ConsumerData
    {
        public string username;
        public string consumer_id;
        public string enabled;
        public string device_name;
        public string started_time;
        public string working_time;
        public string watt_consumption;
        public string sum_consumption;
        public string consumption_summary;
    }
}
