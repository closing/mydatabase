package chr16.shutdownhook;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MySwingAppWithShutDownHook  extends JFrame{
  JButton exitButton = new JButton();
  JTextArea jTextArea = new JTextArea();
  String dir = System.getProperty("user.dir");
  String fileName = "temp.txt";

  public MySwingAppWithShutDownHook() {
    exitButton.setText("Exit");
    exitButton.setBounds(new Rectangle(304,248,76,37));
    exitButton.addActionListener(new ActionListener(){
    
      @Override
      public void actionPerformed(ActionEvent e) {
        exitButton_actionPerformed(e);
      }
    });
    this.getContentPane().setLayout(null);
    jTextArea.setText("Click the Exit button to quit");
    jTextArea.setBounds(new Rectangle(9,7,371,235));
    this.getContentPane().add(exitButton);
    this.getContentPane().add(jTextArea);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setBounds(0, 0, 400, 330);
    this.setVisible(true);
    initialize();
  }
  private void initialize() {
    MyShutDownHook myShutDownHook = new MyShutDownHook();
    Runtime.getRuntime().addShutdownHook(myShutDownHook);
    
    File file = new File(dir,fileName);
    try {
      System.out.println("Creating temporary file");
      file.createNewFile();
    }
    catch(IOException e){
      System.out.println("Failed creating temporary file.");
    }
  }
  private void shutdown(){
    File file = new File(dir, fileName);
    if (file.exists()){
      System.out.println("Deleting temporary file.");
      file.delete();
    }
  }
  private void exitButton_actionPerformed(ActionEvent e){
    shutdown();
    System.exit(0);
  }
  public static void main(String[] args) {
    MySwingAppWithShutDownHook mySwingApp = new MySwingAppWithShutDownHook();
  }
  private class MyShutDownHook extends Thread{
    public void run(){
      shutdown();
    }
  }
}
