package bilboards;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.time.Duration;
import java.util.*;

public class Billboard implements IBillboard{
	private BillboardFrame frame;
	private String advert = "init";
	private float temperature = 5;
	private float wind;
	private float precipitation;
	private int id = -1;
	private long interval = 3000;
	private boolean enabled = true;
	private Map<Character, IClient> clients;
	ArrayList<BillboardStorage> list = new ArrayList<BillboardStorage>();
	private int counter = 0;
	
	class BillboardStorage {
		char owner;
		String advertText;
		
		public BillboardStorage(char owner, String advertText) {
			this.owner = owner;
			this.advertText = advertText;
		}
		
		public char getOwner() {
			return owner;
		}
		
		public String getAdvertText() {
			return advertText;
		}
	}
	
	public Billboard(BillboardFrame frame) {
		this.frame = frame;
		this.clients = new HashMap<>();
		
		Thread dataReading = new Thread(() -> {
			while (true) {
				if (enabled) {

					try {
						if (clients.containsKey('t')) {
							Order order = clients.get('t').getOrder();
							boolean flag = false;
							for(int i = 0; i<list.size(); i++) {
								if(list.get(i).getAdvertText().contentEquals( order.advertText)) {
									flag = true;
								}
							}
							if(!flag) {
								BillboardStorage newItem = new BillboardStorage('t', order.advertText);
								list.add(newItem);
							} 
							
							for(int i=0; i< list.size(); i++) {
//								System.out.println(list.get(i));
							}
							
							System.out.println(list.get(counter).getOwner() + "  " + list.get(counter).getAdvertText() + "  " + counter);
							advert = list.get(counter).getAdvertText();
							order.advertText = advert;
							counter++;
							if(counter == list.size()) {
								counter = 0;
							}
							
							temperature = order.value;
							System.out.println("\n");
							frame.updateOrder(order);
						} else {
							for(int i=0; i< list.size(); i++) {
								if(list.get(i).getOwner() == 't') {
									list.remove(i);
								}
//								System.out.println(list.get(i));
							}
						}

						if (clients.containsKey('w')) {
							Order order = clients.get('w').getOrder();
							boolean flag = false;
							for(int i = 0; i<list.size(); i++) {
								if(list.get(i).getAdvertText().contentEquals( order.advertText)) {
									flag = true;
								}
							}
							if(!flag) {
								BillboardStorage newItem = new BillboardStorage('w', order.advertText);
								list.add(newItem);
							} 
							wind = order.value;
							frame.updateOrder(order);
						} else {
							for(int i=0; i< list.size(); i++) {
								if(list.get(i).getOwner() == 'w') {
									list.remove(i);
								}
							}
						
						}

						if (clients.containsKey('p')) {
							Order order = clients.get('p').getOrder();
							precipitation = order.value;
							frame.updateOrder(order);
						}
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		dataReading.start();
	}
	
public void registerToManager() throws Exception {
		
		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {}

		Registry registry = LocateRegistry.getRegistry();
		IManager stubManager = (IManager) registry.lookup("IManager");
		IBillboard stubBillboard = (IBillboard) UnicastRemoteObject.exportObject(this, 0);

		id = stubManager.bindBillboard(stubBillboard);
		if (id != 0)
			registry.bind("IBillboard" + id, stubBillboard);
	}

	public void unregisterFromManager() throws Exception {

		Registry registry = LocateRegistry.getRegistry();
		IManager stub = (IManager) registry.lookup("IManager");

		if(stub.unbindBillboard(id) == false) {
			id = 0;
		};
	}
	
	@Override
	public boolean register(IClient s, char category) {
		if (!clients.containsKey(category)) {
			clients.put(category, s);
			frame.updateLabels(clients);
			return true;
		} else
			return false;
	}

	@Override
	public boolean unregister(IClient s) throws RemoteException {
		Order order = s.getOrder();
		char category = order.category;

		if (clients.containsKey(category)) {
			clients.remove(category);
			frame.clearOrder(category);
			frame.updateLabels(clients);
			return true;
		}
		return false;
	}
	
	@Override
	public void toggle() {
		if (enabled) {
			enabled = false;
			frame.toggle(enabled);
		} else {
			enabled = true;
			frame.toggle(enabled);
		}
	}

	@Override
	public void setUpdateInterval(long milisec) {
		interval = milisec;
	}

	@Override
	public BillboardData getBillboardData() throws RemoteException {
		BillboardData billboardData = new BillboardData();
		
		billboardData.temperature = temperature;
		billboardData.wind = wind;
		billboardData.precipitation = precipitation;
		billboardData.advert = advert;
		
		return billboardData;
	}

	@Override
	public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAdvertisement(int orderId) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getCapacity() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplayInterval(Duration displayInterval) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean start() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
