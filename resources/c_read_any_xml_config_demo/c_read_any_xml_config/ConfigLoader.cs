using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace c_read_any_xml_config
{
    class ConfigLoader
    {

        public static void Configura(string path) {
            ReadRemoteXml(path);
        }

        private static void ReadRemoteXml(string path)
        {
            string sURL = path;
            using (XmlReader read = XmlReader.Create(sURL))
            {
                while (read.Read())
                {
                    if (read.Name.ToLower().Equals("rabbitmq"))
                    {
                        GlobalConfig.RabbitMQ = new RabbitMQ() {
                            HostName = read.GetAttribute("hostName"),
                            Port = int.Parse(read.GetAttribute("port")),
                            QueueName = read.GetAttribute("queueName"),
                            VirtualHost = read.GetAttribute("virtualHost"),
                            UserName = read.GetAttribute("userName"),
                            Password = read.GetAttribute("password"),
                        };


                        GlobalConfig.Redis = new Redis()
                        {
                            HostName = read.GetAttribute("hostName"),
                            Port = int.Parse(read.GetAttribute("port")),
                            Password = read.GetAttribute("password"),
                        };
                    }
                }
            }
        }
    }
}
