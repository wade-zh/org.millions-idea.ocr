/***
 * @pName mi-ocr-web-app
 * @name UserEntity
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.entity.agent;

import java.sql.Timestamp;

public class UserEntity {
    private Integer uid;
    private String userName;
    private String password;
    private Timestamp registTime;
    private Timestamp lastActiveTime;
    private String lastLoginIp;
    private String lastLoginArea;

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", registTime=" + registTime +
                ", lastActiveTime=" + lastActiveTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastLoginArea='" + lastLoginArea + '\'' +
                '}';
    }

    public UserEntity() {
    }

    public UserEntity(Integer uid, String userName, String password, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp, String lastLoginArea) {

        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.registTime = registTime;
        this.lastActiveTime = lastActiveTime;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginArea = lastLoginArea;
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
