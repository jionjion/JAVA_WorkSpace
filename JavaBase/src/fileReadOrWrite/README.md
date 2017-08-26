# JAVA中IO文件操作

Tags : JDK8 Eclipse IO

---

[TOC]

---

## 简介

java中对文件读取进行了抽象,实现方式有字节流,字符流.


## 包结构

* *[BufferedInputOrOutputType][1]*                 带缓冲的字节流读写
* *`[BufferedReaderOrWriteOrPrintType][2]`*    带缓冲的字符流过滤器读写
* *[DataInputOrOutputSteamType][3]*             扩展数据类型的字节流读写
* *[demo][4]*                                                    一些功能尝试
* *[encoedType][5]*                                          文本编码格式
* *FileReadOrFileWrite*                                文件字符流读写
* *fileType*                                                 java文件类型读写
* *InputOrOutputStreamType*                   字符流读写
* *InputSteamReaderOrOutputSteamWriteType*        带缓冲的字节流读写操作
* *ObjectReadeOrWriteType*                    对象读取操作
*  *RandomAccessFileType*                      随机文件读取

## 子包描述
### `BufferedInputOrOutputType`包

该类对`InputStream`和`OutputStream`进行包装,通过追加套接字的形式,为其提供缓存,提高了文件读写的效率.

### `BufferedReaderOrWriteOrPrintType`包

该类对`FileReader`和`FileWriter`类进行了包装,为其提供了缓冲,提高了文件的读写效率

### `DataInputOrOutputSteamType`包

该类对`InputStream`和`OutputStream`类进行了包装,增强了其对数据,格式数据的支持

### `demo`包

使用commons-io对文件的读取

### `encoedType`包

介绍了常用的编码格式

### `fileReadOrfilewrite`包



### `fileType`包
### `InputOrOutputStreamType`包
### `BufferedInputOrOutputType`包
### `InputSteamReaderOrOutputSteamWriteType`包
### `ObjectReadeOrWriteType`包
**这个交给各个子模块实现**


  [1]: https://github.com/jionjion/JAVA_WorkSpace/tree/master/JavaBase/bin/fileReadOrWrite/BufferedInputOrOutputType
  [2]: https://github.com/jionjion/JAVA_WorkSpace/tree/master/JavaBase/bin/fileReadOrWrite/BufferedReaderOrWriteOrPrintType
  [3]: https://github.com/jionjion/JAVA_WorkSpace/tree/master/JavaBase/bin/fileReadOrWrite/DataInputOrOutputSteamType
  [4]: https://github.com/jionjion/JAVA_WorkSpace/tree/master/JavaBase/bin/fileReadOrWrite/demo
  [5]: https://github.com/jionjion/JAVA_WorkSpace/tree/master/JavaBase/bin/fileReadOrWrite/encoedType