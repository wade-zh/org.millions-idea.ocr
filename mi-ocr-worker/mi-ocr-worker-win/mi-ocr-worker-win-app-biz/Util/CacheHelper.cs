using StackExchange.Redis;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace mi_ocr_worker_win_app_biz
{
    class CacheHelper
    {
        /// <summary>
        /// 缓存
        /// </summary>
        public static class Cache
        {
            private static object cacheLocker = new object();//缓存锁对象
            private static ICache cache = null;//缓存接口

            static Cache()
            {
                Load();
            }

            /// <summary>
            /// 加载缓存
            /// </summary>
            /// <exception cref=""></exception>
            public static void Load()
            {
                try
                {
                    cache = new RedisCacheImpl();
                    if (cache == null)
                    {
                        Console.WriteLine("Get redis connection faild!");
                    }
                }
                catch (Exception ex)
                {
                    //Log.Error(ex.Message);
                }
            }

            public static ICache GetCache()
            {
                return cache;
            }


            /// <summary>
            /// 缓存过期时间
            /// </summary>
            public static int TimeOut
            {
                get
                {
                    return cache.TimeOut;
                }
                set
                {
                    lock (cacheLocker)
                    {
                        cache.TimeOut = value;
                    }
                }
            }

            /// <summary>
            /// 获得指定键的缓存值
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <returns>缓存值</returns>
            public static object Get(string key)
            {
                if (string.IsNullOrWhiteSpace(key))
                    return null;
                return cache.Get(key);
            }

            /// <summary>
            /// 获得指定键的缓存值
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <returns>缓存值</returns>
            public static T Get<T>(string key)
            {
                return cache.Get<T>(key);
            }

            /// <summary>
            /// 将指定键的对象添加到缓存中
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <param name="data">缓存值</param>
            public static void Insert(string key, object data)
            {
                if (string.IsNullOrWhiteSpace(key) || data == null)
                    return;
                //lock (cacheLocker)
                {
                    cache.Insert(key, data);
                }
            }
            /// <summary>
            /// 将指定键的对象添加到缓存中
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <param name="data">缓存值</param>
            public static void Insert<T>(string key, T data)
            {
                if (string.IsNullOrWhiteSpace(key) || data == null)
                    return;
                //lock (cacheLocker)
                {
                    cache.Insert<T>(key, data);
                }
            }
            /// <summary>
            /// 将指定键的对象添加到缓存中，并指定过期时间
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <param name="data">缓存值</param>
            /// <param name="cacheTime">缓存过期时间(分钟)</param>
            public static void Insert(string key, object data, int cacheTime)
            {
                if (!string.IsNullOrWhiteSpace(key) && data != null)
                {
                    //lock (cacheLocker)
                    {
                        cache.Insert(key, data, cacheTime);
                    }
                }
            }

            /// <summary>
            /// 将指定键的对象添加到缓存中，并指定过期时间
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <param name="data">缓存值</param>
            /// <param name="cacheTime">缓存过期时间(分钟)</param>
            public static void Insert<T>(string key, T data, int cacheTime)
            {
                if (!string.IsNullOrWhiteSpace(key) && data != null)
                {
                    //lock (cacheLocker)
                    {
                        cache.Insert<T>(key, data, cacheTime);
                    }
                }
            }

            /// <summary>
            /// 将指定键的对象添加到缓存中，并指定过期时间
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <param name="data">缓存值</param>
            /// <param name="cacheTime">缓存过期时间</param>
            public static void Insert(string key, object data, DateTime cacheTime)
            {
                if (!string.IsNullOrWhiteSpace(key) && data != null)
                {
                    //lock (cacheLocker)
                    {
                        cache.Insert(key, data, cacheTime);
                    }
                }
            }

            /// <summary>
            /// 将指定键的对象添加到缓存中，并指定过期时间
            /// </summary>
            /// <param name="key">缓存键</param>
            /// <param name="data">缓存值</param>
            /// <param name="cacheTime">缓存过期时间</param>
            public static void Insert<T>(string key, T data, DateTime cacheTime)
            {
                if (!string.IsNullOrWhiteSpace(key) && data != null)
                {
                    //lock (cacheLocker)
                    {
                        cache.Insert<T>(key, data, cacheTime);
                    }
                }
            }

            /// <summary>
            /// 从缓存中移除指定键的缓存值
            /// </summary>
            /// <param name="key">缓存键</param>
            public static void Remove(string key)
            {
                if (string.IsNullOrWhiteSpace(key))
                    return;
                lock (cacheLocker)
                {
                    cache.Remove(key);
                }
            }

            /// <summary>
            /// 判断key是否存在
            /// </summary>
            public static bool Exists(string key)
            {
                return cache.Exists(key);
            }


            public static bool Set(string key, string data, DateTime cacheTime)
            {
                try
                {
                    if (!string.IsNullOrWhiteSpace(key) && data != null)
                    {
                        lock (cacheLocker)
                        {
                            cache.Set(key, data, cacheTime);
                        }
                    }
                }
                catch (Exception)
                {
                    return false;
                }
                return true;
            }


        }
    }
}
