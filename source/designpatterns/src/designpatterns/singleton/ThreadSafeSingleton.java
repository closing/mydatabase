package designpatterns.singleton;

public class ThreadSafeSingleton {
	private static class ThreadSafeSingletonFactory {
		private static ThreadSafeSingleton instance = new ThreadSafeSingleton();

	}

	public static ThreadSafeSingleton getInstance() {
		return ThreadSafeSingletonFactory.instance;
	}
}
