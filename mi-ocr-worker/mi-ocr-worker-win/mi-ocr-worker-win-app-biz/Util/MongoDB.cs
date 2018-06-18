using MongoDB.Driver;

namespace mi_ocr_worker_win_app_biz.Util
{
    public class MongoDB {

        private static string connStr = ConfigUtil.GetValue(SysConstant._MongoDBConnection);

        private static string dbName = ConfigUtil.GetValue(SysConstant._MongoDB);

        private static IMongoDatabase db = null;

        private static readonly object lockHelper = new object();

        private MongoDB()
        {
        }

        /// <summary>
        /// 创建DB
        /// </summary>
        /// <returns></returns>
        public static IMongoDatabase CreateDB()
        {
            if (db == null)
            {
                lock (lockHelper)
                {
                    if (db == null)
                    {
                        var client = new MongoClient(connStr);
                        db = client.GetDatabase(dbName);
                    }
                }
            }
            return db;
        }
    }
}
