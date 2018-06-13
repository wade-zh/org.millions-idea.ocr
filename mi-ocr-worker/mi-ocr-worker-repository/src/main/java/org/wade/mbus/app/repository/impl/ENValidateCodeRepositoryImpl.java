package org.wade.mbus.app.repository.impl;

import org.springframework.stereotype.Repository;
import org.wade.mbus.app.repository.IValidateCodeRepository;

@Repository("enValidateCodeRepositoryImpl")
public class ENValidateCodeRepositoryImpl implements IValidateCodeRepository {
    @Override
    public String getImageText(byte[] data) {
        /*BASE64Encoder encoder = new BASE64Encoder();
        return IENExternal.Library.GetImageText(encoder.encode(data));*/
        return null;
    }
}
