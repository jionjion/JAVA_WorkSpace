package com.model.observer;

/**
 *	观察者的具体实现
 *	具体的观察者对象	
 */
public class WeatherConcreateObserver implements WeatherObserver{

	/*进行消息提示*/
	private String observerName;				//提醒对象的名称
	
	private String weatherContent;				//天气状态
	
	private String remindThing;					//提醒内容
	
	/**
	 * 	将目标类的状态同步到观察者的状态中
	 */
	@Override
	public void update(WeatherSubject subject) {
		this.weatherContent = ((WeatherConcreteSubject) subject).getWeatherContent();
		
		System.out.println(observerName + "收到了," + weatherContent + "提醒的" + remindThing);
	}

	
	
	public String getObserverName() {
		return observerName;
	}

	public void setObserverName(String observerName) {
		this.observerName = observerName;
	}

	public String getWeatherContent() {
		return weatherContent;
	}

	public void setWeatherContent(String weatherContent) {
		this.weatherContent = weatherContent;
	}

	public String getRemindThing() {
		return remindThing;
	}

	public void setRemindThing(String remindThing) {
		this.remindThing = remindThing;
	}

	
}
