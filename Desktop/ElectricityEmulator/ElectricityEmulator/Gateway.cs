using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Json;
using System.Reflection.Metadata;
using System.Security.Policy;
using System.Text;
using System.Text.Json.Nodes;
using System.Text.Json.Serialization;
using System.Text.Json.Serialization.Metadata;
using System.Threading.Tasks;

namespace ElectricityEmulator
{
    internal class Gateway
    {
        static HttpClient httpClient = new HttpClient();

        private string API_URL = "http://16.171.238.180:8080";
        //private string API_URL = "http://127.0.0.1:8080";

        public async Task PostUpdate(List<Consumer> consumers)
        {
            List<ConsumerData> sendingList = new List<ConsumerData>();
            foreach (Consumer consumer in consumers)
            {
                ConsumerData consumerData = new ConsumerData()
                {
                    username = "SherAlex",
                    consumer_id = consumer.ID,
                    enabled = consumer.Enabled.ToString(),
                    device_name = consumer.DeviceName,
                    started_time = consumer.TimeStarted.ToString(),
                    working_time = consumer.TimeWorking.ToString(),
                    watt_consumption = consumer.Power,
                    sum_consumption = "195",
                    consumption_summary = "0"
                };
                sendingList.Add(consumerData);
            }
            
            ConsumerList data = new ConsumerList()
            {
                data = sendingList
            };
            
            var content = new StringContent(JsonConvert.SerializeObject(data), Encoding.UTF8, "application/json");

            try
            {
                HttpResponseMessage response = await httpClient.PostAsync(API_URL + $"/device_statistics?interval={1}", content);
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        private struct ConsumerList
        {
            public List<ConsumerData> data;
        }

        private struct ConsumerData
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

        private class TestModel
        {
            public int test_data;
        }
    }
}
