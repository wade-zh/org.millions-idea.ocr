/***
 * @pName mi-ocr-web-user
 * @name UserDetailEntity
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.user.entity.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserDetailEntity {
    private Integer uid;
    private String userName;
    private String password;
    private String email;
    private Timestamp registTime;
    private Timestamp lastActiveTime;
    private String lastLoginIp;
    private String lastLoginArea;


    private BigDecimal balance;
    private Timestamp editDate;
    private Integer state;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Timestamp registTime) {
        this.registTime = registTime;
    }

    public Timestamp getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Timestamp lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginArea() {
        return lastLoginArea;
    }

    public void setLastLoginArea(String lastLoginArea) {
        this.lastLoginArea = lastLoginArea;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getEditDate() {
        return editDate;
    }

    public void setEditDate(Timestamp editDate) {
        this.editDate = editDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public UserDetailEntity() {

    }

    public UserDetailEntity(Integer uid, String userName, String password, String email, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp, String lastLoginArea, BigDecimal balance, Timestamp editDate, Integer state) {

        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.registTime = registTime;
        this.lastActiveTime = lastActiveTime;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginArea = lastLoginArea;
        this.balance = balance;
        this.editDate = editDate;
        this.state = state;
    }
}
