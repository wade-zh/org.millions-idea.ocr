/***
 * @pName mi-ocr-web-order
 * @name OrderDetailEntity
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.entity.agent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderDetailEntity {

    public interface OrderSampleViewEntity {}

    private Integer autoId;
    private String recordId;
    private String recordNo;
    private Integer fromUid;
    private Integer toUid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp tradeDate;
    private Integer tradeType;
    private BigDecimal tradeAmount;
    private String remark;
    private String captchaId;
    private String channelId;
    private Integer ack;
    private Integer toDays;
    private Integer isAvailable;

    public OrderDetailEntity(Integer autoId, String recordId, String recordNo, Integer fromUid, Integer toUid, Timestamp tradeDate, Integer tradeType, BigDecimal tradeAmount, String remark, String captchaId, String channelId, Integer ack, Integer toDays, Integer isAvailable) {
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
        this.toDays = toDays;
        this.isAvailable = isAvailable;
    }

    public OrderDetailEntity() {

    }

    @JsonView(OrderSampleViewEntity.class)
    public Integer getIsAvailable() {

        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

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

    @JsonView(OrderSampleViewEntity.class)
    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    @JsonView(OrderSampleViewEntity.class)
    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    @JsonView(OrderSampleViewEntity.class)
    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    @JsonView(OrderSampleViewEntity.class)
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

    @JsonView(OrderSampleViewEntity.class)
    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    @JsonView(OrderSampleViewEntity.class)
    public Integer getToDays() {
        return toDays;
    }

    public void setToDays(Integer toDays) {
        this.toDays = toDays;
    }
}
