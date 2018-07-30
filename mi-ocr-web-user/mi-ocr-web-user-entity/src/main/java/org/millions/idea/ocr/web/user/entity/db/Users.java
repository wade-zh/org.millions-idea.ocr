/***
 * @pName mi-ocr-web-user
 * @name UserEntity
 * @user HongWei
 * @date 2018/6/22
 * @desc This is user table
 */
package org.millions.idea.ocr.web.user.entity.db;

import java.sql.Timestamp;

public class Users {
    private Integer uid;
    private String userName;
    private String password;
    private String email;
    private Timestamp registTime;
    private Timestamp lastActiveTime;
    private String lastLoginIp;
    private String lastLoginArea;

    public Users(Integer uid, String userName, String password, String email, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp, String lastLoginArea) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.registTime = registTime;
        this.lastActiveTime = lastActiveTime;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginArea = lastLoginArea;
    }

    public Users() {

    }

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
}
