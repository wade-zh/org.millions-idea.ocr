/***
 * @pName j_read_any_xml_config_demo
 * @name GlobalConfig
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package com.example.demo;

public class GlobalConfig {
    private static RabbitMQ rabbitMQ;
    public static RabbitMQ getRabbitMQ() {
        return rabbitMQ;
    }
    public static void setRabbitMQ(RabbitMQ rabbitMQ) {
        GlobalConfig.rabbitMQ = rabbitMQ;
    }

    private static Redis redis;
    public static Redis getRedis() {
        return redis;
    }
    public static void setRedis(Redis redis) {
        GlobalConfig.redis = redis;
    }

    public static class RabbitMQ{
        private String hostName;
        private Integer port;
        private String queueName;

        public RabbitMQ() {
        }

        public RabbitMQ(String hostName, Integer port, String queueName, String virtualHost, String userName, String password) {

            this.hostName = hostName;
            this.port = port;
            this.queueName = queueName;
            this.virtualHost = virtualHost;
            this.userName = userName;
            this.password = password;
        }

        public String getHostName() {

            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getQueueName() {
            return queueName;
        }

        public void setQueueName(String queueName) {
            this.queueName = queueName;
        }

        public String getVirtualHost() {
            return virtualHost;
        }

        public void setVirtualHost(String virtualHost) {
            this.virtualHost = virtualHost;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private String virtualHost;
        private String userName;
        private String password;

        @Override
        public String toString() {
            return "RabbitMQ{" +
                    "hostName='" + hostName + '\'' +
                    ", port=" + port +
                    ", queueName='" + queueName + '\'' +
                    ", virtualHost='" + virtualHost + '\'' +
                    ", userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    public static class Redis{
        private String hostName;
        private Integer port;
        private String password;

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Redis() {

        }

        public Redis(String hostName, Integer port, String password) {

            this.hostName = hostName;
            this.port = port;
            this.password = password;
        }

        @Override
        public String toString() {
            return "Redis{" +
                    "hostName='" + hostName + '\'' +
                    ", port=" + port +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
