package designpatterns.factory.simplefactory;

import designpatterns.factory.MailSender;
import designpatterns.factory.Sender;
import designpatterns.factory.SmsSender;

public class SimpleFactory {
	// normal
	public Sender produce(String type) {
		Sender sender;
		if ("mail".equals(type)) {
			sender = new MailSender();
		} else if ("sms".equals(type)) {
			sender = new SmsSender();
		} else {
			throw new IllegalArgumentException("Invalid type:" + type);
		}
		return sender;

	}

	// multip method
	public Sender produceMail() {
		return new MailSender();
	}
	public Sender produceSms() {
		return new SmsSender();
	}

	// multrip static method
	public static Sender produceSMail() {
		return new MailSender();
	}
	public static Sender produceSSms() {
		return new SmsSender();
	}

}
