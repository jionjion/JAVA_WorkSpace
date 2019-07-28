## JAVA8新特性

### Lambda表达式



### 函数式接口
`java.util.function.*`包

`Consumer<T>`
消费型接口

`Supplier<T>`
供给型接口

`Function<T, R>`
函数型接口

`Predicate<T>`
断言型接口

在参数个数上扩展： 比如接收双参数的，有 Bi 前缀， 比如 BiConsumer<T,U>, BiFunction<T,U,R> ;
在类型上扩展： 比如接收原子类型参数的，有 [Int|Double|Long][Function|Consumer|Supplier|Predicate]
特殊常用的变形： 比如 BinaryOperator ， 是同类型的双参数 BiFunction<T,T,T> ，二元操作符 ； UnaryOperator 是 Function<T,T> 一元操作符。


### 方法引用与构造器引用
在Lambda表达式中,直接调用已经实现的方法.
要求:参数列表及返回类型必须与声明的一致

对象::实例方法名
类::静态方法名
类::示例方法名(要求,参数列表中第一个参数是方法调用实例,第二个参数是实例方法参数)


### Stream流
用于操作数据源的数据处理,不会改变源数据对象,经过处理后返回处理结果.

惰性求值:在多个操作组中,如果不触发终止操作,则不进行运算.而在终止操作发生时进行全部处理.

获得途径
从集合,数组,自行创建获得流

常用操作

串行流与并行流
Fork/Join:将任务拆解执行后并将结果进行汇总


### Fork/Join

### Optional类
容器类,代表一个值存在或者不存在.避免空指针异常

常用方法
`Optional.of(T value)`          创建一个实例
`Optional.empty()`              创建一个空实例
`Optional.ofNullable(T value)`  不为空时,创建实例;为空时,创建空实例
`get()`                         获得返回值,如果返回值为`null`,则返回`null`
`isPresent()`                   判断是否包含值
`orElse(T other)`               获得返回值,如果返回值不存在,则返回默认值T
`orElseGet(Supplier other)`     获得返回值,如果返回值不存在,则返回默认值T
`map(Function mapper)`          如果有值,则进行函数式计算,并返回其计算结果的`Optional`包装;如果为空,则直接返回空包装
`flatMap(Function mapper)`      同上


### 新的时间API
原有的日期类如`Date`或则`Calendar`均是线程不安全的类.日期计算,格式化复杂.
新的日期类线程安全,并每次创建时均会生成一个新的日期类对象.
`java.time.*`           日期
`java.time.chrono.*`    不同地区特殊的日期格式
`java.time.format.*`    日期格式化
`java.time.temporal.*`  日期运算操作
`java.time.zone.*`      时区操作

常用类
`LocalDate`         当地日期
`LocalTime`         当地时间
`LocalDateTime`     当地日期+时间
`Period`            日期之间的的间隔
`Duration`          时间之间的间隔
`DateTimeFormatter` 日期格式化工具
`ZoneDate`          时区日期
`ZoneTime`          时区时间
`ZoneDateTime`      时区日期+时间


### 可重复注解与类型注解
可重复注解:可以一个注解多次注解.
类型注解:可以在类属性或者方法参数中使用

