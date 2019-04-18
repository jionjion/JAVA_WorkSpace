# 简介
一套通用的API接口,便于自己在多方面使用.


# 接口模块

| URL                       | 参数描述                |
| ---                       | -------               |


# 格式
## API调用格式


## API返回格式
### 标准JSON返回格式
注解参考 https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
参考 https://www.baeldung.com/jackson-annotations
```json5
{
  "dataDto":null,
  "authDto":{
    "username":"GUEST",
    "token":"00000000000000000000000000000000"
  },
  _info: {
    "status":"WARNING",
    "code":300,
    "message":null
  }
}

```