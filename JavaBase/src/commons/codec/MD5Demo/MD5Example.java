package commons.codec.MD5Demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 *  MD5加解密
 */
public class MD5Example {

    @Test
    public void testMd5(){
        DigestUtils.md5Hex("密文...");
    }