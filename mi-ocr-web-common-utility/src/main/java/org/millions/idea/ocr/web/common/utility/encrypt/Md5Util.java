/***
 * @pName mi-ocr-web-common-utility
 * @name Md5Util
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.common.utility.encrypt;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Md5Util {
    public static String getMd5(String key){
        return Hashing.md5().newHasher().putString(key, Charsets.UTF_8).hash().toString();
    }
}
