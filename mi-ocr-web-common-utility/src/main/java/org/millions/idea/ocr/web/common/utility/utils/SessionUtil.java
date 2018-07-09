/***
 * @pName mi-ocr-web-common-utility
 * @name SessionUtil
 * @user HongWei
 * @date 2018/7/2
 * @desc
 */
package org.millions.idea.ocr.web.common.utility.utils;

import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

public class SessionUtil {

    /**
     * 通过token从缓存中取出用户信息
     * @param token
     * @return
     */
    public static String getUserInfo(RedisTemplate redis, String token){
        Object cache = redis.opsForValue().get(token);
        if(cache == null) return null;
        return String.valueOf(cache);
    }
}
