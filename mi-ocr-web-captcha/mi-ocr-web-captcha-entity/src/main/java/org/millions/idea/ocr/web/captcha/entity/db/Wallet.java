/***
 * @pName mi-ocr-web-captcha
 * @name Wallet
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.db;

public class Wallet {
    private Integer wid;
    private Integer uid;
    private Double balance;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "wid=" + wid +
                ", uid=" + uid +
                ", balance=" + balance +
                ", version=" + version +
                '}';
    }

    public Wallet(Integer wid, Integer uid, Double balance, Integer version) {
        this.wid = wid;
        this.uid = uid;
        this.balance = balance;
        this.version = version;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Wallet() {

    }

}
