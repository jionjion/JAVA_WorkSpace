package bean;

import java.util.List;

/**主表,命令列表*/
public class Command {
	/**唯一主键*/
	private int id;
	/**命令*/
	private String name;
	/**描述*/
	private String description;
	/**对应的多方列表*/
	private List<CommandContent> contentsList; 
	
	public List<CommandContent> getContentsList() {
		return contentsList;
	}

	public void setContentsList(List<CommandContent> contentsList) {
		this.contentsList = contentsList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
