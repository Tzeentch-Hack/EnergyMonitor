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
using ElectricityEmulator.Models;

namespace ElectricityEmulator.Gateway
{
    internal class StatisticsGateway
    {
        static HttpClient httpClient = new HttpClient();

        private string API_URL = "http://16.171.238.180:8080";
        //private string API_URL = "http://127.0.0.1:8080";

        public async Task PostUpdate(ConsumerList data)
        {
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
    }
}
