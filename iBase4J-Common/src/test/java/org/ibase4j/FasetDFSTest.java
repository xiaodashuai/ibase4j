package org.ibase4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class FasetDFSTest {
    protected final Logger logger = LogManager.getLogger();
    @Test
    public void main() {
        String str="hello";
        logger.debug("aa={}",str);
//        logger.debug(UploadUtil.remove2DFS(null, null, "D:\\xsit\\更新价格js.txt"));
    }

}
