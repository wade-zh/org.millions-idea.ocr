package org.millions.idea.ocr.web.common.utility.encrypt;

import org.junit.Test;

import static org.junit.Assert.*;

public class Md5UtilTest {

    /**
     * Verify md5 content
     */
    @Test
    public void testGetMd5() {
            String md5 = Md5Util.getMd5("admin123456");
        assert md5.equalsIgnoreCase("a66abb5684c45962d887564f08346e8d");
    }
}