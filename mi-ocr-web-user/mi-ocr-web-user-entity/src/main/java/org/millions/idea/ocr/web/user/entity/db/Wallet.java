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
    private Double balance;

    @Override
    public String toString() {
        return "Wallet{" +
                "wid=" + wid +
                ", uid=" + uid +
                ", balance=" + balance +
                '}';
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

    public Wallet(Integer wid, Integer uid, Double balance) {

        this.wid = wid;
        this.uid = uid;
        this.balance = balance;
    }
}
