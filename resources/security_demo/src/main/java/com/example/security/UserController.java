/***
 * @pName security
 * @name UserController
 * @user HongWei
 * @date 2018/7/15
 * @desc
 */
package com.example.security;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    private List<User> userList = Arrays.asList(
            new User(1,"A",10),
            new User(2,"B",11),
            new User(3,"C",12));


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> query(User user, @PageableDefault(page = 1, size = 20, sort = "username,desc") Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        return userList;
    }
}
