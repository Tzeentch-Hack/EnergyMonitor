using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace ElectricityEmulator
{
    internal class Gateway
    {
        static HttpClient httpClient = new HttpClient();

        private string API_URL = "http://16.171.238.180:8080";

        public async Task PostUpdate(List<Consumer> consumers)
        {
            using HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, API_URL);
            await httpClient.SendAsync(request);
        }
    }
}
