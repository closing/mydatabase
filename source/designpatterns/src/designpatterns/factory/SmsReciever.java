package designpatterns.factory;

public class SmsReciever implements Reciever {
	@Override
	public void Recieve() {
		System.out.println("This is sms reciever");
	}
}
