using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace c_read_any_xml_config
{
    public class GlobalConfig
    {
        public static RabbitMQ RabbitMQ { get; set; }
        public static Redis Redis { get; set; }
    }

    public class RabbitMQ
    {
        public string HostName { get; set; }
        public int Port { get; set; }
        public string QueueName { get; set; }
        public string VirtualHost { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }

        public override string ToString()
        {
            return "RabbitMQ{" +
                     "hostName='" + HostName + '\'' +
                     ", port=" + Port +
                     ", queueName='" + QueueName + '\'' +
                     ", virtualHost='" + VirtualHost + '\'' +
                     ", userName='" + UserName + '\'' +
                     ", password='" + Password + '\'' +
                     '}';
        }
    }

    public class Redis
    {
        public string HostName { get; set; }
        public int Port { get; set; }
        public string Password { get; set; }

        public override string ToString()
        {
            return "Redis{" +
                    "hostName='" + HostName + '\'' +
                    ", port=" + Port +
                    ", password='" + Password + '\'' +
                    '}';
        }
    }
}
