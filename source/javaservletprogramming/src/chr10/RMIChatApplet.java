package chr10;

import java.applet.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class RMIChatApplet extends Applet implements ChatClient {
    private static final long serialVersionUID = -1L;
    
    TextArea text;
    Label label;
    TextField input;
    Thread thread;
    String user;

    ChatServer chatServer;

    private int getRegistryPort() {
        try {
            return Integer.parseInt(getParameter("port"));
        } catch (NumberFormatException ignored) {
            return Registry.REGISTRY_PORT;
        }
    }

    private String getRegistryName() {
        String name = getParameter("name");
        return (name == null ? "ChatServlet" : name);
    }

    // Returns a reference to the remote chat server/servlet
    // Tries to exit if there's a problem.
    private ChatServer getChatServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(getCodeBase().getHost(), getRegistryPort());
            Object obj = registry.lookup(getRegistryName());
            return (ChatServer) obj;
        } catch (java.rmi.UnknownHostException e) {
            // Don't know the registry host, try to exit
            System.out.println("Host unknown in url: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            // Can't find our object, try to exit
            System.out.println("Name not bound: " + e.getMessage());
            System.exit(1);
        } catch (ClassCastException e) {
            // The object wasn't a ChatServer, try to exit
            System.out.println(getRegistryName() + " was not a ChatServer:" + e.getMessage());
            System.exit(1);
        } catch (RemoteException e) {
            // General RMI problem, try to exit
            System.out.println("Remote exception: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            // Some other problem, try to exit
            System.out.println("General exception: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return null; // return null if the exit() doesn't work
    }

    // Add ourselves as a client of the chat server
    // Notice there's no need for an RMI registry
    private void registerWithChatServer(ChatServer server) {
        try {
            UnicastRemoteObject.exportObject(this);
            server.addClient(this);
        } catch (RemoteException e) {
            // General RMI problem, try to exit
            System.out.println("Remote exception: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            // Some other problem, try to exit
            System.out.println("General exception: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    public void init() {
        // Check if this applet was loaded directly from the filesystem.
        // If so, explain to the user that this applet needs to be loaded
        // from a server in order to communicate with that server's servlets.
        URL codebase = getCodeBase();
        if (!"http".equals(codebase.getProtocol())) {
            System.out.println();
            System.out.println("*** Whoops! ***");
            System.out.println("This applet must be loaded from a web server.");
            System.out.println("Please try again, this time fetching the HTML");
            System.out.println("file containing this servlet as");
            System.out.println("\"http://server:port/file.html\".");
            System.out.println();
            System.exit(1); // Works only from appletviewer
                            // Browsers throw an exception and muddle on
        }

        // Get the remote chat server
        chatServer = getChatServer();

        // Register ourselves as one of its clients
        registerWithChatServer(chatServer);

        // Get this user's name from an applet parameter set by the servlet
        // We could just ask the user, but this demonstrates a
        // form of servlet->applet communication.
        user = getParameter("user");
        if (user == null)
            user = "anonymous";

        // Set up the user interface...
        // On top, a large TextArea showing what everyone's saying.
        // Underneath, a labeled TextField to accept this user's input.
        text = new TextArea();
        text.setEditable(false);
        label = new Label("Say something: ");
        input = new TextField();
        input.setEditable(true);

        setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());

        add("Center", text);
        add("South", panel);

        panel.add("West", label);
        panel.add("Center", input);
    }

    String getNextMessage() {
        String nextMessage = null;
        while (nextMessage == null) {
            try {
                nextMessage = chatServer.getNextMessage();
            } catch (RemoteException e) {
                // Remote exception, report and wait before trying again
                System.out.println("Remote Exception:" + e.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return nextMessage + "\n";
    }

    public void setNextMessage(String message) {
        text.appendText(message + "\n");
    }

    void broadcastMessage(String message) {
        message = user + ": " + message; // Pre-pend the speaker's name
        try {
            chatServer.broadcastMessage(message);
        } catch (RemoteException e) {
            // Remote exception, report it and abandon the broadcast
            System.out.println("Remote exception: " + e.getMessage());
        } catch (Exception e) {
            // Some other exception, report it and abandon the broadcast
            System.out.println("General exception: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public boolean handleEvent(Event event) {
        switch (event.id) {
        case Event.ACTION_EVENT:
            if (event.target == input) {
                broadcastMessage(input.getText());
                input.setText("");
                return true;
            }
        }
        return false;
    }
}
