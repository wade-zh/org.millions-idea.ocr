/***
 * @pName mi-ocr-web-order
 * @name TransactionRecordEntity
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.db;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionRecordEntity {
    private Integer autoId;
    private String recordId;
    private String recordNo;
    private Integer fromUid;
    private Integer toUid;
    private Timestamp tradeDate;
    private Integer tradeType;
    private BigDecimal tradeAmount;
    private String remark;
    private String captchaId;
    private String channelId;
    private Integer ack;
    private Integer isAvailable;

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public TransactionRecordEntity() {

    }

    public TransactionRecordEntity(Integer autoId, String recordId, String recordNo, Integer fromUid, Integer toUid, Timestamp tradeDate, Integer tradeType, BigDecimal tradeAmount, String remark, String captchaId, String channelId, Integer ack, Integer isAvailable) {

        this.autoId = autoId;
        this.recordId = recordId;
        this.recordNo = recordNo;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.tradeDate = tradeDate;
        this.tradeType = tradeType;
        this.tradeAmount = tradeAmount;
        this.remark = remark;
        this.captchaId = captchaId;
        this.channelId = channelId;
        this.ack = ack;
        this.isAvailable = isAvailable;
    }
}
