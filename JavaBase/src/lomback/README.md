
# 简介

`Lomback`简要介绍


## 包结构



## 常用注解

`@NonNull`      检查参数是否为空,为`null`抛出异常
`@Cleanup`      对于`IO`流等需要关闭的,可以通过该注解自动关闭
`@Getter`       对属性生成`Getter`方法,布尔值为`Is`方法
`@Setter`       对非`final`属性生成`Setter`方法
`@ToString`     对类生成`toString`方法
`@EqualsAndHashCode`    重写`haseCode()`和`equal`方法
