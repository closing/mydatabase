package chr16.shutdownhook;

public class ShutDownHook extends Thread{
  public void run() {
    System.out.println("Shutting down");
  }
}
