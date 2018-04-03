package designpatterns.factory.factorymethod;

import designpatterns.factory.MailSender;
import designpatterns.factory.Sender;

public class MailSenderFactory implements FactoryMethod {
	public Sender produce() {
		return new MailSender();
	}

}
