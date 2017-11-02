---
title : JAVA中安全框架
tags : JDK8 , Eclipse 
---

[TOC]

---

# 简介
介绍了JAVA中常用到的加解密方式,对信息安全传递进行了简要介绍.

## 相关知识
**明文:**等待加密的信息
**密文:**经过加密后的明文
**加密:**明文转为密文的过程
**加密算法:**明文转为密文的转换算法
**加密密钥:**通过加密算法进行加密操作的密钥
**解密:**将密文转为明文的过程
**解密算法:**密文转为明文的算法
**解密密钥:**通过解密算法进行解密操作的密钥
**密码体制:**明文空间,密文空间,秘钥空间,加密算法和解密算法五部分构成
**密码协议:**安全协议,以密码学为基础的信息交换的通信协议,目的是在网络环境中提供安全的服务.
**柯克霍夫原则:**数据的安全基于秘钥而不是算法的保密.系统的安全取决于秘钥,对秘钥保密,对算法公开.

## 实现方式
通过扩展JDK的配置文件,指明包的位置实现加载,配置文件在JDK的安装目录下的`jre\lib\security`的`java.security`文件中,该文件中加入了JAVA自带的实现方式.
通过添加新的实现方式,完成注入.
``` 
security.provider.1=sun.security.provider.Sun
security.provider.2=sun.security.rsa.SunRsaSign
security.provider.3=sun.security.ec.SunEC
```

也可以通过`addProvider()`或`insertProviderAt()`实现动态添加算法实现类

**常用的加密方式**
`java.security`消息摘要,安全框架实现的基础
`javax.crypto`安全消息摘要,消息认证码,最为完整的加解密方式
`java.net.ssl`安全套接字,网络传输时加密.

**第三方扩展**
*Bouncy Castle*
支持以上两种配置方式
*Commons Codec*
Base64,二进制,十六进制,字符集编码,URL编码

## 包结构

`aesDemo`
`base64Demo`       Base64算法
`des3Demo`
`desDemo`
`dhDemo`
`dsaDemo`
`ecdsaDemo`
`elGamalDemo`
`macDemo`
`mdDemo`             MD消息摘要算法
`pbeDemo`
`rsaDemo`
`rsaDemoTwo`
`shaDemo`


# 安全加解密的方式


## Base64算法

使用Base64算法,完成基础的敏感字段的加解密方式.


## 消息摘要算法
消息摘要算法是一种单向的加密方式,常用来验证数据完整性和作为数字签名,通过对此明文加密后与密文的重合度,进而判断数据真实性.


### MD消息摘要算法

| 算法 | 摘要长度 | 实现方        |
| ---- | -------- | ------------- |
| MD2  | 128      | JDK           |
| MD4  | 128      | Bouncy Castle |
| MD5  | 128      | JDK           |


![消息摘要加密][1]


### SHA消息摘要算法
安全散列算法,生成的摘要信息为固定长度

| 算法    | 摘要长度 | 实现方        |
| ------- | -------- | ------------- |
| SHA-1   | 160      | JDK           |
| SHA-224 | 224      | Bouncy Castle |
| SHA-256 | 256      | JDK           |
| SHA-384 | 384      | JDK           |
| SHA-512 | 512      | JDK           |


![SHA消息摘要算法][2]

### MAC消息摘要算法

散列函数算法

| 算法       | 摘要长度 | 实现方        |
| ---------- | -------- | ------------- |
| HmacMD2    | 128      | Bouncy Castle |
| HmacMD4    | 128      | Bouncy Castle |
| HmacMD5    | 128      | JDK           |
| HmacSHA1   | 160      | JDK           |
| HmacSHA224 | 224      | Bouncy Castle |
| HmacSHA256 | 256      | JDK           |
| HmacSHA384 | 384      | JDK           |
| HmacSHA512 | 512      | JDK           |


![MAC消息摘要算法][3]


## 对称加密算法
### DES对称加密算法

### 3DES对称加密算法

### AES对称加密算法

### PEB对称加密算法

## 非对称加密算法
### DH秘钥交换非对称加密算法

### RSA因子分解非对称加密算法

### ElGamal离散对数非对称加密算法

### ECC椭圆曲线非对称加密算法

## 数字签名算法

### RSA签名算法

### DSA签名算法

### ECDSA签名算法





























  

  [1]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/javaBase/encryption-01.png "encryption-01"
  
  [2]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/javaBase/encryption-02.png

  [3]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/javaBase/encryption-03.png "encryption-03"