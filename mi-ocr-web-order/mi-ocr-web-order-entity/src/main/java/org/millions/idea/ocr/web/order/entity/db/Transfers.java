/***
 * @pName mi-ocr-web-order
 * @name Transfers
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.db;

import java.sql.Timestamp;

public class Transfers {
    private String tid;
    private Integer type;
    private Integer sendUid;
    private Integer recvUid;
    private Double amount;
    private String desc;

    public Transfers() {
    }

    public String getTid() {

        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSendUid() {
        return sendUid;
    }

    public void setSendUid(Integer sendUid) {
        this.sendUid = sendUid;
    }

    public Integer getRecvUid() {
        return recvUid;
    }

    public void setRecvUid(Integer recvUid) {
        this.recvUid = recvUid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Transfers(String tid, Integer type, Integer sendUid, Integer recvUid, Double amount, String desc, Timestamp date) {

        this.tid = tid;
        this.type = type;
        this.sendUid = sendUid;
        this.recvUid = recvUid;
        this.amount = amount;
        this.desc = desc;
        this.date = date;
    }

    private Timestamp date;
}
