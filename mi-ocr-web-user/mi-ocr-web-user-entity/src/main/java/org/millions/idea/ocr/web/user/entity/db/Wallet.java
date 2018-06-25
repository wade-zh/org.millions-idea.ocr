/***
 * @pName mi-ocr-web-user
 * @name Wallet
 * @user HongWei
 * @date 2018/6/22
 * @desc This is wallet table
 */
package org.millions.idea.ocr.web.user.entity.db;

public class Wallet {
    private Integer wid;
    private Integer uid;
    private Integer balance;
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

    public Wallet(Integer wid, Integer uid, Integer balance, Integer version) {
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Wallet() {

    }

}
