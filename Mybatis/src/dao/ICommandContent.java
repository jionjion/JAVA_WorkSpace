package dao;

import java.util.List;

import bean.CommandContent;

public interface ICommandContent {

	void insertBatch(List<CommandContent> contentList);

}
