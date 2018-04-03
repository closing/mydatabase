package designpatterns.factory.abstractfactory;

import designpatterns.factory.Reciever;
import designpatterns.factory.Sender;
import designpatterns.factory.SmsReciever;
import designpatterns.factory.SmsSender;

public class SmsFactory  implements AbstractFactory {


	@Override
	public Sender CreateSender() {
		return new SmsSender();
	}

	@Override
	public Reciever CreateReciever() {
		return  new SmsReciever();
	}

}
