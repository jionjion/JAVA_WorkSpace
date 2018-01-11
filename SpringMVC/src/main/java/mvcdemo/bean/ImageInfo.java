package mvcdemo.bean;
/**
 * 	上传图片加水印中,图片对象信息			<br>
 * 	@真实路径 F:\JAVA_WorkSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SpringMVC\WEB-INF\resource\waterImages
 * @author JionJion
 */
public class ImageInfo {

	
	/** 没有图片水印的URL */
	private String notWaterMarkUrl ;
	
	/** 有图片水印的URL */
	private String withWaterMarkUrl ;

	public String getNotWaterMarkUrl() {
		return notWaterMarkUrl;
	}

	public void setNotWaterMarkUrl(String notWaterMarkUrl) {
		this.notWaterMarkUrl = notWaterMarkUrl;
	}

	public String getWithWaterMarkUrl() {
		return withWaterMarkUrl;
	}
	
	public void setWithWaterMarkUrl(String withWaterMarkUrl) {
		this.withWaterMarkUrl = withWaterMarkUrl;
	}
}