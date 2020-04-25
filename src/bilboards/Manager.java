package bilboards;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.TreeMap;

public class Manager implements IManager{
	private int nextId = 1;
	private Map<Integer, IBillboard> billboards;
	private ManagerFrame frame;
	private IClient clientStub;
	
	public Manager() {
		Thread dataUpdater = new Thread(() -> {
			while (true) {
				billboards.forEach((k, v) -> {
					try {
						BillboardData billboardData = v.getBillboardData();
						frame.updateValue(k, billboardData);
					} catch (RemoteException e) {
					}
				});
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
			}
		});
		
		dataUpdater.start();
	}
	
	public static Manager createManager(ManagerFrame frame) throws Exception {

		Registry registry = LocateRegistry.createRegistry(1099);
		Manager obj = new Manager();
		obj.frame = frame;
		obj.billboards = new TreeMap<>();
		IManager stub = (IManager) UnicastRemoteObject.exportObject(obj, 0);

		registry.bind("IManager", stub);
		obj.frame.managerRunning();

		return obj;
	}

	@Override
	public int bindBillboard(IBillboard s) throws RemoteException {
		if (!billboards.containsValue(s)) {
			billboards.put(nextId, s);
			frame.insertBillboard(nextId, s);

			return nextId++;
		} else
			return 0;
	}

	@Override
	public boolean unbindBillboard(int id) throws RemoteException {
		if (billboards.containsKey(id)) {
			billboards.remove(id);
			frame.removeBillboard(id);

			return true;
		} else
			return false;
	}
	
	public void toggleBoard(int id) throws RemoteException {
		billboards.get(id).toggle();
	}
	
	public void setInterval(int id, int value) throws RemoteException {
		billboards.get(id).setUpdateInterval(value);
	}



	@Override
	public boolean placeOrder(Order order) throws RemoteException {
		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {}

		Registry registry = LocateRegistry.getRegistry();
		try {
			IBillboard billboardStub = (IBillboard) registry.lookup("IBillboard" + order.value);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		order = (Order) UnicastRemoteObject.exportObject(this, 0);
		return true;
	}

	@Override
	public boolean withdrawOrder(int orderId) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry();
		IBillboard stub = null;
		try {
			stub = (IBillboard) registry.lookup("IBillboard" + orderId);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stub.unregister(clientStub);
	}
}
