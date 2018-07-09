/***
 * @pName mi-ocr-web-order
 * @name MoneyChangeLogEntity
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.db;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MoneyChangeLogEntity {
    private Integer autoId;
    private String logId;
    private String recordId;
    private Integer fromUid;
    private Integer tradeType;
    private BigDecimal tradeAmount;
    private BigDecimal accountBalance;
    private String remark;
    private Timestamp addDate;

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
    }

    public MoneyChangeLogEntity() {

    }

    public MoneyChangeLogEntity(Integer autoId, String logId, String recordId, Integer fromUid, Integer tradeType, BigDecimal tradeAmount, BigDecimal accountBalance, String remark, Timestamp addDate) {

        this.autoId = autoId;
        this.logId = logId;
        this.recordId = recordId;
        this.fromUid = fromUid;
        this.tradeType = tradeType;
        this.tradeAmount = tradeAmount;
        this.accountBalance = accountBalance;
        this.remark = remark;
        this.addDate = addDate;
    }
}
