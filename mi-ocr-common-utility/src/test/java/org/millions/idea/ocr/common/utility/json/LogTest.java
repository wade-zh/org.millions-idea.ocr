package org.millions.idea.ocr.common.utility.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;



public class LogTest {
    private static Logger logger = LogManager.getLogger(LogTest.class);

    @Test
    public void testLoggerIsNull() {
        assert logger != null;
    }

    @Test
    public void testWriteLine() {
        try{
            logger.debug("hello log4j!");
        }catch(Exception e){
            assert e == null;
        }
    }
}