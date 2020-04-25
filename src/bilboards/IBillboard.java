package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;

public interface IBillboard extends Remote{

	boolean register(IClient s, char category) throws RemoteException; 
    boolean unregister(IClient s) throws RemoteException;
    BillboardData getBillboardData() throws RemoteException;
    
    
    
    

	public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException;

	public boolean removeAdvertisement(int orderId) throws RemoteException;

	public int[] getCapacity() throws RemoteException;

	public void setDisplayInterval(Duration displayInterval) throws RemoteException;

	public boolean start() throws RemoteException;

	public boolean stop() throws RemoteException;

}
