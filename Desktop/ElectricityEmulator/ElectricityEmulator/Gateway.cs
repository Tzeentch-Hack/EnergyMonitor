using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json.Serialization.Metadata;
using System.Threading.Tasks;

namespace ElectricityEmulator
{
    internal class Gateway
    {
        static HttpClient httpClient = new HttpClient();

        private string API_URL = "http://16.171.238.180:8080";

        public async Task PostUpdate(List<Consumer> consumers)
        {
            using HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, API_URL + "/device_statistics");
            //List
            
            //var content = new JsonContent()
            //await httpClient.SendAsync(request);
        }

        private struct ConsumerData
        {
            string username;
            string consumer_id;
            string enabled;
            string device_name;
            string started_time;
            string working_time;
            string watt_consumption;
            string sum_consumption;
            string consumption_summary;
        }
    }
}
