/***
 * @pName mi-ocr-common-utility
 * @name DateUtil
 * @user HongWei
 * @date 2018/6/23
 * @desc
 */
package org.millions.idea.ocr.web.common.utility.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Timestamp convert(Timestamp date){
        return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }
}
