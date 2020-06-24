package utils;

import java.util.Random;

/**
 *  @author Jion
 *      数据库索引,主键生成工具类
 */
public class KeyUtil {

    /** 生成主键编号,使用同步方法,避免多线程调用时出现重复
     *  格式 = 时间 + 随机数 */
    public static synchronized String getUniqueKey(){
        Long time = System.currentTimeMillis();
        Random random = new Random();
        //[100,1000)的三位数
        Integer integer = random.nextInt(900 ) + 100;
        //组合
        return String.valueOf(time) + String.valueOf(integer);
    }
}
