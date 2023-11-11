using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ElectricityEmulator
{
    internal class Utils
    {
        public static string GenerateId()
        {
            return Guid.NewGuid().ToString("N");
        }
    }
}
