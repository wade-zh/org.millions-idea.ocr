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


    ORDER_CREATE_TRANSACTION_RECORD("生成交易流水失败"),
    ORDER_CREATE_MONEY_CHANGE_PLUS_LOG("生成入账账单失败"),
    ORDER_CREATE_MONEY_CHANGE_MINUS_LOG("生成支出账单失败"),
    ORDER_SELECT_WALLET_INFO("查询钱包信息失败"),
    ORDER_REDUCE_WALLET_BALANCE("扣减余额失败"),
    ORDER_ADD_WALLET_BALANCE("充值失败"),
    ORDER_WALLET_BALANCE_ZERO("余额不足");

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
