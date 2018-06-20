/***
 * @pName mi-ocr-web
 * @name EnumUtil
 * @user HongWei
 * @date 2018/6/19
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.util;

import org.millions.idea.ocr.web.captcha.entity.types.ChannelType;

public class EnumUtil {
    public static boolean isExist(String value){
        try{
            ChannelType channelType = ChannelType.valueOf(value);
            return channelType.toString().toUpperCase().equals(value.trim().toUpperCase());
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
