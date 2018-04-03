package designpatterns.client;

import designpatterns.factory.Reciever;
import designpatterns.factory.Sender;
import designpatterns.factory.abstractfactory.AbstractFactory;
import designpatterns.factory.abstractfactory.SmsFactory;
import designpatterns.factory.factorymethod.MailSenderFactory;
import designpatterns.factory.simplefactory.SimpleFactory;

public class FactoryTest {

	public static void main(String[] args) {
		// normal
		SimpleFactory factory = new SimpleFactory();
		Sender sender = factory.produce("sms");
		sender.Send();

		// multrip method
		sender = factory.produceMail();
		sender.Send();

		// multrip static method
		sender = SimpleFactory.produceSSms();
		sender.Send();

		// Method Factory
		MailSenderFactory mailFactory = new MailSenderFactory();
		sender = mailFactory.produce();
		sender.Send();

		// Abstract Factroy
		AbstractFactory smsFactory = new SmsFactory();
		sender = smsFactory.CreateSender();
		Reciever reciever = smsFactory.CreateReciever();
		sender.Send();
		reciever.Recieve();

	}

}
