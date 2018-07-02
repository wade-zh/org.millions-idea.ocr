/***
 * @pName mi-ocr-web-captcha
 * @name PayParam
 * @user HongWei
 * @date 2018/7/2
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.agent.order;


public class PayParam {
    private Integer uid;
    private Double unitPrice;
    private Integer type;
    private String remark;
    private Integer fromUid;
    private Exceptions exceptions;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Exceptions getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exceptions exceptions) {
        this.exceptions = exceptions;
    }

    public PayParam() {

    }

    public PayParam(Integer uid, Double unitPrice, Integer type, String remark, Integer fromUid, Exceptions exceptions) {

        this.uid = uid;
        this.unitPrice = unitPrice;
        this.type = type;
        this.remark = remark;
        this.fromUid = fromUid;
        this.exceptions = exceptions;
    }
}
