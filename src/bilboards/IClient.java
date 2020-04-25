package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	Order getOrder() throws RemoteException;
	
	public void setOrderId(int orderId) throws RemoteException;
}
