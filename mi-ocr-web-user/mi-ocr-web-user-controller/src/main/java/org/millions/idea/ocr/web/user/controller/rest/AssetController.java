/***
 * @pName mi-ocr-web-user-parent
 * @name AssetController
 * @user HongWei
 * @date 2018/6/20
 * @desc
 */
package org.millions.idea.ocr.web.user.controller.rest;

import org.millions.idea.ocr.web.user.biz.IUserService;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssetController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/get")
    public List<Users> get(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/get/{id}")
    public Users get(@PathVariable Integer id){
        return userService.getUser(id);
    }
}
