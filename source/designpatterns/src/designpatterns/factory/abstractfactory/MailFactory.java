package designpatterns.factory.abstractfactory;

import designpatterns.factory.MailReciever;
import designpatterns.factory.MailSender;
import designpatterns.factory.Reciever;
import designpatterns.factory.Sender;

public class MailFactory implements AbstractFactory {


	@Override
	public Sender CreateSender() {
		return new MailSender();
	}

	@Override
	public Reciever CreateReciever() {
		return  new MailReciever();
	}

}
