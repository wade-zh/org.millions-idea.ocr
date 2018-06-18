using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace c_read_any_xml_config
{
    class Program
    {
        static void Main(string[] args)
        {
            ConfigLoader.Configura(ConfigurationManager.AppSettings["configUrl"]);
            Console.WriteLine(GlobalConfig.RabbitMQ);
            Console.WriteLine(GlobalConfig.Redis);
            Console.ReadKey();
        }
    }
}
