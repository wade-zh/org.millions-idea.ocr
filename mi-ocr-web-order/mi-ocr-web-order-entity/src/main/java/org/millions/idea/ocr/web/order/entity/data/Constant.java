/***
 * @pName mi-ocr-web-finance
 * @name Constant
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.data;

public class Constant {
    static {
        sharedAccountID = 1;
        sharedTradeNo = "A873796782";
    }
    private final static Integer sharedAccountID;
    private final static String sharedTradeNo;

    public static Integer getSharedAccountID() {
        return sharedAccountID;
    }

    public static String getSharedTradeNo() {
        return sharedTradeNo;
    }
}
