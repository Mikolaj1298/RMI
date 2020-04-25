package bilboards;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client implements IClient{

	private IClient clientStub;
	private ClientFrame frame;
	private Order order = new Order();
	
	 ArrayList<String> list = new ArrayList<String>();
	 int counter = 0;
	 

	public Client(ClientFrame frame) {
		this.frame = frame;

		Thread dataGenerator = new Thread(() -> {
			while (true) {
				order.category = frame.getClientType().toLowerCase().charAt(0);

				//System.out.println(frame.getAdvertText());
				switch (order.category) {
				case 'f':
					order.advertText = frame.getAdvertText();
					
					break;
				case 's':
					order.advertText = frame.getAdvertText();
					break;
				case 't':
					order.advertText = frame.getAdvertText();
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		dataGenerator.start();
	}

	public boolean registerToBillboard(int billboardID) throws Exception {
		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {}
		Registry registry = LocateRegistry.getRegistry();
		IBillboard billboardStub = (IBillboard) registry.lookup("IBillboard" + billboardID);
		clientStub = (IClient) UnicastRemoteObject.exportObject(this, 0);

		return billboardStub.register(clientStub, frame.getClientType().toLowerCase().charAt(0));
	}

	public boolean unregisterFromBillboard(int billboardID) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IBillboard stub = (IBillboard) registry.lookup("IBillboard" + billboardID);

		return stub.unregister(clientStub);
	}


	@Override
	public Order getOrder() throws RemoteException {
		return order;
	}

	@Override
	public void setOrderId(int orderId) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
