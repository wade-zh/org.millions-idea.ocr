/***
 * @pName mi-ocr-web-user
 * @name Backcategorys
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.entity.db;

import java.math.BigDecimal;

public class Channels {
    private Integer cid;
    private String code;
    private BigDecimal unitAmount;
    private String desc;

    public Channels() {
    }

    public Channels(Integer cid, String code, BigDecimal unitAmount, String desc) {

        this.cid = cid;
        this.code = code;
        this.unitAmount = unitAmount;
        this.desc = desc;
    }

    public Integer getCid() {

        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
