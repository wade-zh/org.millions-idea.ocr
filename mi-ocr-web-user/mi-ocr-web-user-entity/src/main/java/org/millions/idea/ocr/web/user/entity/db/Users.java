/***
 * @pName mi-ocr-web-user
 * @name Users
 * @user HongWei
 * @date 2018/6/22
 * @desc This is user table
 */
package org.millions.idea.ocr.web.user.entity.db;

public class Users {
    private Integer uid;
    private String userName;
    private String password;
    private Integer issueId;
    private String issueResult;
    private String qq;

    @Override
    public String toString() {
        return "Users{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", issueId=" + issueId +
                ", issueResult='" + issueResult + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }

    public Users() {
    }

    public Users(Integer uid, String userName, String password, Integer issueId, String issueResult, String qq) {

        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.issueId = issueId;
        this.issueResult = issueResult;
        this.qq = qq;
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
