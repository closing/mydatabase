package chr10;

import java.applet.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

import common.servlet.HttpMessage;

public class DaytimeApplet extends Applet {
    private static final long serialVersionUID = -1L;

    static final int DEFAULT_PORT = 1313;

    TextField httpText, httpObject, socketText, socketObject, RMIObject;
    Button refresh;

    public void init() {
        // Construct the user interface

        setLayout(new BorderLayout());

        // On the left create labels for the various communication
        // mechanisms
        Panel west = new Panel();
        west.setLayout(new GridLayout(5, 1));
        west.add(new Label("HTTP text: ", Label.RIGHT));
        west.add(new Label("HTTP object: ", Label.RIGHT));
        west.add(new Label("Socket text: ", Label.RIGHT));
        west.add(new Label("Socket object: ", Label.RIGHT));
        west.add(new Label("RMI object: ", Label.RIGHT));
        add("West", west);

        // On the right create text fields to display the retrieved time values
        Panel center = new Panel();
        center.setLayout(new GridLayout(5, 1));

        httpText = new TextField();
        httpText.setEditable(false);
        center.add(httpText);

        httpObject = new TextField();
        httpObject.setEditable(false);
        center.add(httpObject);

        socketText = new TextField();
        socketText.setEditable(false);
        center.add(socketText);

        socketObject = new TextField();
        socketObject.setEditable(false);
        center.add(socketObject);

        RMIObject = new TextField();
        RMIObject.setEditable(false);
        center.add(RMIObject);

        add("Center", center);

        // On the bottom create a button to update the times
        Panel south = new Panel();
        refresh = new Button("Refresh");
        south.add(refresh);
        add("South", south);
    }

    public void start() {
        refresh();
    }

    private void refresh() {
        // Fetch and display the time values
        httpText.setText(getDateUsingHttpText());
        httpObject.setText(getDateUsingHttpObject());
        socketText.setText(getDateUsingSocketText());
        socketObject.setText(getDateUsingSocketObject());
        RMIObject.setText(getDateUsingRMIObject());
    }

    private String getDateUsingHttpText() {
        try {
            // Construct a URL referring to the servlet
            URL url = new URL(getCodeBase(), "/daytime");

            HttpMessage msg = new HttpMessage(url);

            InputStream in = msg.sendGetMessage();

            DataInputStream result = new DataInputStream(new BufferedInputStream(in));

            String date = result.readLine();

            in.close();

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDateUsingHttpObject() {
        try {
            // Construct a URL referring to the servlet
            URL url = new URL(getCodeBase(), "/daytime");

            // Create a com.oreilly.servlet.HttpMessage to communicate with that URL
            HttpMessage msg = new HttpMessage(url);

            // Construct a Properties list to say format=object
            Properties props = new Properties();
            props.put("format", "object");

            // Send a GET message to the servlet, passing "props" as a query string
            // Get the response as an ObjectInputStream
            InputStream in = msg.sendGetMessage(props);
            ObjectInputStream result = new ObjectInputStream(in);

            // Read the Date object from the stream
            Object obj = result.readObject();
            Date date = (Date) obj;

            // Return the string representation of the Date
            return date.toString();
        } catch (Exception e) {
            // If there was a problem, print to System.out
            // (typically the Java console) and return null
            e.printStackTrace();
            return null;
        }
    }

    private int getSocketPort() {
        try {
            return Integer.parseInt(getParameter("socketPort"));
        } catch (NumberFormatException e) {
            return DEFAULT_PORT;
        }
    }

    private String getDateUsingSocketText() {
        InputStream in = null;
        try {
            // Establish a socket connection with the servlet
            Socket socket = new Socket(getCodeBase().getHost(), getSocketPort());

            // Print an empty line, indicating we want the time as plain text
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println();
            out.flush();

            // Read the first line of the response
            // It should contain the current time
            in = socket.getInputStream();
            DataInputStream result = new DataInputStream(new BufferedInputStream(in));
            String date = result.readLine();

            // Return the retrieved string
            return date;
        } catch (Exception e) {
            // If there was a problem, print to System.out
            // (typically the Java console) and return null
            e.printStackTrace();
            return null;
        } finally {
            // Always close the connection
            // This code executes no matter how the try block completes
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private String getDateUsingSocketObject() {
        InputStream in = null;
        try {
            // Establish a socket connection with the servlet
            Socket socket = new Socket(getCodeBase().getHost(), getSocketPort());

            // Print a line saying "object", indicating we want the time as 
            // a serialized Date object
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println("object");
            out.flush();

            // Create an ObjectInputStream to read the response
            in = socket.getInputStream();
            ObjectInputStream result = new ObjectInputStream(new BufferedInputStream(in));

            // Read an object, and cast it to be a Date
            Object obj = result.readObject();
            Date date = (Date) obj;

            // Return a string representation of the retrieved Date
            return date.toString();
        } catch (Exception e) {
            // If there was a problem, print to System.out
            // (typically the Java console) and return null
            e.printStackTrace();
            return null;
        } finally {
            // Always close the connection
            // This code executes no matter how the try block completes
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private String getRegistryHost() {
        return getCodeBase().getHost();
    }

    private int getRegistryPort() {
        try {
            return Integer.parseInt(getParameter("registryPort"));
        } catch (NumberFormatException e) {
            return Registry.REGISTRY_PORT;
        }
    }

    private String getRegistryName() {
        String name = getParameter("registryName");
        if (name == null) {
            name = "DaytimeServlet"; // default
        }
        return name;
    }

    private String getDateUsingRMIObject() {
        try {
            Registry registry = LocateRegistry.getRegistry(getRegistryHost(), getRegistryPort());
            DaytimeServer daytime = (DaytimeServer) registry.lookup(getRegistryName());
            return daytime.getDate().toString();
        } catch (ClassCastException e) {
            System.out.println("Retrieved object was not a DaytimeServer: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println(getRegistryName() + " not bound: " + e.getMessage());
        } catch (RemoteException e) {
            System.out.println("Hit remote exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(
                    "Problem getting DaytimeServer reference: " + e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public boolean handleEvent(Event event) {
        // When the refresh button is pushed, refresh the display
        // Use JDK 1.0 events for maximum portability
        switch (event.id) {
        case Event.ACTION_EVENT:
            if (event.target == refresh) {
                refresh();
                return true;
            }
        }
        return false;
    }
}
