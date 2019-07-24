
## 日志实现
SLF4J日志替换
将不同框架的日志方式,统一替换为Slf4j + Logback方式
[Slf4j](https://www.slf4j.org/images/legacy.png)

将不同框架的日志实现统一替换为步骤:
1.将系统的的其他日志框架首先排除
2.引入中间替换包,替换原有的日志包
3.引入Slf4j的实现包

## SpringBoot的日志
通过场景启动器
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
      <version>2.1.6.RELEASE</version>
      <scope>compile</scope>
    </dependency>
```
该依赖将SpringBoot用到的log4j,jul等日志通过适配器jar包,转给Slf4j的规范实现`logback-classic`
如果,项目引入其他框架,则需要将第三方依赖中的日志Jar包移除,避免与适配器Jar中的类冲突.
Spring会自动将其转为与框架一致的实现
```xml
    <dependency>
      <groupId>net.sourceforge.htmlunit</groupId>
      <artifactId>htmlunit</artifactId>
      <version>2.33</version>
      <scope>compile</scope>
      <exclusions>
        <exclusion>
          <artifactId>commons-logging</artifactId>
          <groupId>commons-logging</groupId>
        </exclusion>
      </exclusions>
      <optional>true</optional>
    </dependency>
```

## 日志输出
```properties
# 当前项目下生成日志目录.文件名spring.log
logging.path= log
# 指定生成的文件名及位置
logging.file= F:\\JAVA_WorkSpace\\demo\\log\\demo.log
# 控制台,文件日志格式
logging.pattern.console=
logging.pattern.file=
```

## 默认日志配置
在`org.springframework.boot.logging.logback`目录下 


## 覆盖默认配置
在resource目录下增加logback.xml或者spring-logback.xml进行日志配置
具体参阅 [官网](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-logging.html)

具有`spring-logback.xml`的扩展配置,可以在不同的环境下动态配置日志格式
