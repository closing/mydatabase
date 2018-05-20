package chr10;

import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DaytimeServer extends Remote {
  public Date getDate() throws RemoteException;
}
