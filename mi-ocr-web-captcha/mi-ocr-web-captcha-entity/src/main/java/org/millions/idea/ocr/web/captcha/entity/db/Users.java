/***
 * @pName mi-ocr-web-captcha
 * @name Users
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.db;

import java.sql.Timestamp;

public class Users {
    private Integer uid;
    private String userName;
    private String password;
    private Integer issueId;
    private String issueResult;
    private String qq;
    private Timestamp registTime;
    private Timestamp lastActiveTime;
    private String lastLoginIp;
    private Integer levelId;

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

    public Users(Integer uid, String userName, String password, Integer issueId, String issueResult, String qq, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp, Integer levelId) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.issueId = issueId;
        this.issueResult = issueResult;
        this.qq = qq;
        this.registTime = registTime;
        this.lastActiveTime = lastActiveTime;
        this.lastLoginIp = lastLoginIp;
        this.levelId = levelId;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}