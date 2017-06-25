package com.imooc.beanannotation.multibean;
/***实现了BeanInterface的空继承*/
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)		//排序为1,传入整形数字
@Component		//万能的注解
public class BeanImplOne implements BeanInterface {

}
