/***
 * @pName security
 * @name UserController
 * @user HongWei
 * @date 2018/7/15
 * @desc
 */
package com.example.security;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private List<User> userList = Arrays.asList(
            new User(1,"A",10, new Date()),
            new User(2,"B",11, new Date()),
            new User(3,"C",12, new Date()));


    @GetMapping("/user")
    @JsonView(User.UserSampleView.class)
    public List<User> query(User user, @PageableDefault(page = 1, size = 20, sort = "username,desc") Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        return userList;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable Integer id){
        return userList.stream().filter(u -> u.getId().equals(id)).findFirst().get();
    }


    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors){
        if (errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError)error;
                System.out.println(fieldError.getField() + "::" + error.getDefaultMessage());
            });
        }
        System.out.println(user.getId());
        System.out.println(user.getAddDate());
        user.setName("test");
        return user;
    }
}
