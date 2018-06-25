/***
 * @pName mi-ocr-web-captcha
 * @name Users
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class Users {
    private Integer uid;
    private String userName;
    private String password;
    private Integer issueId;
    private String issueResult;
    private String contact;
    private Timestamp registTime;
    private Timestamp lastActiveTime;
    private String lastLoginIp;

    @Override
    public String toString() {
        return "Users{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", issueId=" + issueId +
                ", issueResult='" + issueResult + '\'' +
                ", contact='" + contact + '\'' +
                ", registTime=" + registTime +
                ", lastActiveTime=" + lastActiveTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                '}';
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

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getIssueResult() {
        return issueResult;
    }

    public void setIssueResult(String issueResult) {
        this.issueResult = issueResult;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public Users() {

    }

    public Users(Integer uid, String userName, String password, Integer issueId, String issueResult, String contact, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp) {

        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.issueId = issueId;
        this.issueResult = issueResult;
        this.contact = contact;
        this.registTime = registTime;
        this.lastActiveTime = lastActiveTime;
        this.lastLoginIp = lastLoginIp;
    }
}
