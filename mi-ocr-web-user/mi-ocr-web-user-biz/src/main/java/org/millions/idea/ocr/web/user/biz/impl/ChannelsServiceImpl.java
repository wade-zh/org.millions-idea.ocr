/***
 * @pName mi-ocr-web-user
 * @name BackCategoryService
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.millions.idea.ocr.web.user.biz.IChannelsService;
import org.millions.idea.ocr.web.user.entity.db.Channels;
import org.millions.idea.ocr.web.user.repository.mapper.IChannelsMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelsServiceImpl implements IChannelsService {
    private final IChannelsMapperRepository channelsMapperRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public ChannelsServiceImpl(IChannelsMapperRepository channelsMapperRepository, RedisTemplate redisTemplate) {
        this.channelsMapperRepository = channelsMapperRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Query all categorys
     *
     * @return
     */
    @Override
    public List<Channels> queryAll() {
        return channelsMapperRepository.selectAll();
    }

    /**
     * Write categorys in cache
     */
    @Override
    public void initCache() {
        channelsMapperRepository.selectAll().forEach(b -> {
            redisTemplate.opsForValue().set(b.getCode(), b.getUnitAmount().toString());
        });
    }
}
