/***
 * @pName mi-ocr-web-user
 * @name InformationServiceImpl
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.millions.idea.ocr.web.user.biz.IInformationService;
import org.millions.idea.ocr.web.user.repository.mapper.IInformationMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl implements IInformationService {
    private final IInformationMapperRepository informationMapperRepository;

    @Autowired
    public InformationServiceImpl(IInformationMapperRepository informationMapperRepository) {
        this.informationMapperRepository = informationMapperRepository;
    }
}
