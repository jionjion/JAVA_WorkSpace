
# 简介

`Lombok`简要介绍


## 包结构



## 常用注解

`@NonNull`      检查参数是否为空,为`null`抛出异常
`@Cleanup`      对于`IO`流等需要关闭的,可以通过该注解自动关闭
`@Getter`       对属性生成`Getter`方法,布尔值为`Is`方法.通过`@Getter(lazy=true)`进行懒加载
`@Setter`       对非`final`属性生成`Setter`方法
`@ToString`     对类生成`toString`方法
`@EqualsAndHashCode`    重写`haseCode()`和`equal`方法
`@NoArgsConstructor`,`@RequiredArgsConstructor`, `@AllArgsConstructor` 定义构造函数
`@Data`         简写注解,可变的Bean对象
`@Value`        简写注解,不可变的Bean的对象
`@SneakyThrows` 将编译时异常改为运行时异常
`@Synchronized` 方法锁
`@Log`          日志组件,实例化 `java.util.logging.Logger` 类
`@CommonsLog`   日志组件,实例化 `org.apache.commons.logging.Log` 类
`@Flogger`      日志组件,实例化 `com.google.common.flogger.FluentLogger` 类
`@JBossLog`     日志组件,实例化 `org.jboss.logging.Logger` 类
`@Log4j`        日志组件,实例化 `org.apache.log4j.Logger` 类
`@Log4j2`       日志组件,实例化 `org.apache.logging.log4j.Logger` 类
`@Slf4j`        日志组件,实例化 `org.slf4j.Logger` 类
`@XSlf4j`       日志组件,实例化 `org.slf4j.ext.XLogger` 类


