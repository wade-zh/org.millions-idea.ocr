/***
 * @pName mi-ocr-web-user
 * @name BackCategoryService
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.millions.idea.ocr.web.user.biz.IBackCategoryService;
import org.millions.idea.ocr.web.user.entity.db.Backcategorys;
import org.millions.idea.ocr.web.user.repository.mapper.IBackcategoryMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackCategoryServiceImpl implements IBackCategoryService {
    private final IBackcategoryMapperRepository backcategoryMapperRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public BackCategoryServiceImpl(IBackcategoryMapperRepository backcategoryMapperRepository, RedisTemplate redisTemplate) {
        this.backcategoryMapperRepository = backcategoryMapperRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Query all categorys
     *
     * @return
     */
    @Override
    public List<Backcategorys> queryAll() {
        return backcategoryMapperRepository.selectAll();
    }

    /**
     * Write categorys in cache
     */
    @Override
    public void initCache() {
        backcategoryMapperRepository.selectAll().forEach(b -> {
            redisTemplate.opsForValue().set(b.getCode(), b.getReduce().toString());
        });
    }
}
