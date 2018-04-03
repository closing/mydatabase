package designpatterns.client;

import designpatterns.singleton.Singleton;
import designpatterns.singleton.SynchronizedSingleton;
import designpatterns.singleton.ThreadSafeSingleton;
import designpatterns.singleton.ThreadUnsafeSingleton;

public class SingletonTest {
	public static void main(String args[]) {
		// instance1
		Singleton instance1 = Singleton.getInstance();
		System.out.println(instance1.toString());

		// instance2
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance2.toString());

		//Thread safe but unefficient
		SynchronizedSingleton instance3 = SynchronizedSingleton.getInstance();
		System.out.println(instance3.toString());
		SynchronizedSingleton instance4 = SynchronizedSingleton.getInstance();
		System.out.println(instance4.toString());

		// Efficient but thread unsafe
		ThreadUnsafeSingleton instance5 = ThreadUnsafeSingleton.getInstance();
		ThreadUnsafeSingleton instance6 = ThreadUnsafeSingleton.getInstance();
		System.out.println(instance5.toString());
		System.out.println(instance6.toString());

		//  Efficient and thread unsafe
		ThreadSafeSingleton instance7 = ThreadSafeSingleton.getInstance();
		ThreadSafeSingleton instance8 = ThreadSafeSingleton.getInstance();
		System.out.println(instance7.toString());
		System.out.println(instance8.toString());

	}
}
