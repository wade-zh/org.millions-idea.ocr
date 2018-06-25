/***
 * @pName mi-ocr-web-user
 * @name Backcategorys
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.entity.db;

public class Backcategorys {
    private Integer cid;
    private String code;
    private String cname;
    private Integer reduce;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getReduce() {
        return reduce;
    }

    public void setReduce(Integer reduce) {
        this.reduce = reduce;
    }

    public Backcategorys() {

    }

    public Backcategorys(Integer cid, String code, String cname, Integer reduce) {

        this.cid = cid;
        this.code = code;
        this.cname = cname;
        this.reduce = reduce;
    }
}
