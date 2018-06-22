/***
 * @pName mi-ocr-web-user
 * @name UserServiceImpl
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.millions.idea.ocr.web.user.biz.IUserService;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.millions.idea.ocr.web.user.repository.mapper.IUserMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserMapperRepository userMapperRepository;

    @Autowired
    public UserServiceImpl(IUserMapperRepository userMapperRepository) {
        this.userMapperRepository = userMapperRepository;
    }

    /**
     * Get all user information
     *
     * @return
     */
    @Override
    public List<Users> getUsers() {
        return userMapperRepository.queryList();
    }

    /**
     * Get user information by uid
     *
     * @param uid
     * @return
     */
    @Override
    public Users getUser(Integer uid) {
        return userMapperRepository.query(uid);
    }
}
