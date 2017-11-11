package bean;
/**从表,对应命令返回内容*/
public class CommandContent {
	/**唯一主键*/
	private int id;
	/**内容*/
	private String content;
	/**外键*/
	private int commandId;
	/**主表的引用*/
	private Command command;
	
	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
