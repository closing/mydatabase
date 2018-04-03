package struts.helloworld.action;

import com.opensymphony.xwork2.ActionSupport;

import struts.helloworld.model.MessageStore;

public class HelloWorldAction extends ActionSupport {
	private MessageStore messageStore;

	public String execute() {
		messageStore = new MessageStore();

		return SUCCESS;
	}

	public MessageStore getMessageStore() {
		return messageStore;
	}
}
