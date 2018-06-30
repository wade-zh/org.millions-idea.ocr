/***
 * @pName mi-ocr-web-finance
 * @name Exceptions
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.enums.transfer;

public enum Exceptions {
    SYSTEM_TRANSFER_ERROR("SYSTEM_TRANSFER_ERROR"),
    USER_TRANSFER_ERROR("USER_TRANSFER_ERROR"),
    USER_UPDATE_INFORMATION_ERROR("USER_UPDATE_INFORMATION_ERROR"),
    ORDER_CREATE_TRANSACTION_RECORD("ORDER_CREATE_TRANSACTION_RECORD"),
    ORDER_CREATE_MONEY_CHANGE_LOG("ORDER_CREATE_MONEY_CHANGE_LOG");

    private String error;

    Exceptions(String error) {
        this.error = error;
    }

    public String getError() {

        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
