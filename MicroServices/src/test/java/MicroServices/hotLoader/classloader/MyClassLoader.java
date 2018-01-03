package MicroServices.hotLoader.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 	自定义类加载器,实现类的热加载
 * @author JionJion
 */
public class MyClassLoader extends ClassLoader {

	/**加载类的路径*/
	private String classpath ;
	
	/**通过构造方法,传入类家在路径*/
	public MyClassLoader(String classpath) {
		//调用父类的构造方式,加载类加载器
		super(ClassLoader.getSystemClassLoader());	
		this.classpath = classpath;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		//使用自己写的方法,将类名和类所在路径加载,返回字节数组
		byte[] data = this.loadClassData(name);
		//调用父类方法,传入类文件读取后的字节数组,创建类对象
		return this.defineClass(name, data, 0, data.length);
	}

	/**通过类名,加载类文件为byte[]数组*/
	private byte[] loadClassData(String name) {
		try {
			//将传入的类名中包含.的包名转为斜线的路径地址
			name = name.replace(".", "//");
			//文件输入流
			FileInputStream fileInputStream = new FileInputStream(new File(classpath + name +".class"));
			//字节输出流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			//使用字节进行读取
			int b = 0;
			while( (b=fileInputStream.read()) !=-1 ) {
				byteArrayOutputStream.write(b);
			}
			//关闭文件
			byteArrayOutputStream.close();
			fileInputStream.close();
			//返回输出的字节数组
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
