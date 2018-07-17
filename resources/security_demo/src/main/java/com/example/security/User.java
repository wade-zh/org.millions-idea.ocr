/***
 * @pName security
 * @name User
 * @user HongWei
 * @date 2018/7/15
 * @desc
 */
package com.example.security;

import com.example.security.validate.MyConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {

    public interface UserSampleView {
    }

    public interface UserDetailView extends UserSampleView {
    }

    public User(Integer id, String name, Integer age, Date addDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.addDate = addDate;
    }

    private Integer id;

    @NotBlank
    @ApiModelProperty("用户名称")
    private String name;

    @MyConstraint(message = "这是一个测试")
    @ApiModelProperty("用户年龄")
    private Integer age;

    @NotNull(message = "时间不能为空")
    private Date addDate;

    @JsonView(UserDetailView.class)
    public Date getAddDate() {
        return addDate;
    }

    @JsonView(UserSampleView.class)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonView(UserDetailView.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(UserDetailView.class)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User() {

    }

}
