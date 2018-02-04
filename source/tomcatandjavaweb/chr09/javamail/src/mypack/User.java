package mypack;

import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class User implements HttpSessionBindingListener {
    private OnlineUsers onlineUsers = OnlineUsers.getInstance();
    private String name = null;
    
    public User(String name){
        this.name = name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public String getName() {
        return this.name;
    }
    public void valueBound(HttpSessionBindingEvent event) {
        onlineUsers.add(name);
        System.out.println(name + " is bound with a session");
    }
    public void valueUnbound(HttpSessionBindingEvent event) {
        onlineUsers.remove(name);
        System.out.println(name + " is unbound with a session");
    }

}