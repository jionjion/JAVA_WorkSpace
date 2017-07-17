package springJMS.producer;
/**	消息生产者的接口,在producer.xml中配置其初始化信息*/
public interface ProducerService {

	public void sendMessage(String message);
}
