package orc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.xmlgraphics.image.loader.impl.ImageBuffered;


/** 下载 验证码*/
public class Download {
		private static final String DOWNLOAD_DIR = "C:\\Users\\14345\\Desktop\\imageCode\\";

	    public static void downloadImage() throws Exception {
	        HttpClient httpClient = new DefaultHttpClient(null,null);
	        String url = "http://10.18.111.103:8080/hls/imagecode";
	        for (int i = 1; i <= 30; i++) {
	            HttpGet getMethod = new HttpGet(url);
	            try {
	                HttpResponse response = httpClient.execute(getMethod, new BasicHttpContext());
	                HttpEntity entity = response.getEntity();
	                InputStream instream = entity.getContent(); 
	                BufferedImage imageBuffered = ReadOrWriteImage.readImg(instream);
	                ReadOrWriteImage.writeImg(imageBuffered, "png", DOWNLOAD_DIR + "img" + String.format("%02d", i));
	                
	            } finally {
	                getMethod.releaseConnection();
	            }
	        }

	        System.out.println("下载验证码完毕！");
	    }
}
