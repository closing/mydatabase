package chr16.shutdownhook;

public class ShutDownHookDemo {
  public static void main(String[] args) {
    ShutDownHookDemo demo = new ShutDownHookDemo();
    demo.start();
    try {
      System.in.read();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }
  public void start() {
    System.out.println("Demo");
    ShutDownHook shutDownHook = new ShutDownHook();
    Runtime.getRuntime().addShutdownHook(shutDownHook);
  }
}
