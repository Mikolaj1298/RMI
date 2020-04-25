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
	
	public Billboard(BillboardFrame frame) throws RemoteException {
		this.frame = frame;
		this.clients = new HashMap<>();
		start();

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
			
			if(id < list.size()) {
				removeAdvertisement(id);
			}
			return true;
		}
		return false;
	}

	
	@Override
	public void setDisplayInterval(Duration displayInterval) throws RemoteException {
		interval = displayInterval.toMillis();
	}

	@Override
	public BillboardData getBillboardData() throws RemoteException {
		BillboardData billboardData = new BillboardData();
		
		billboardData.temperature = temperature;
		billboardData.wind = wind;
		billboardData.duration = (int)interval;
		billboardData.advert = advert;
		billboardData.capacity = getCapacity();
		return billboardData;
	}


	@Override
	public boolean removeAdvertisement(int orderId) throws RemoteException {
		list.remove(orderId);
		return true;
	}

	@Override
	public int[] getCapacity() throws RemoteException {
		int capacity[] = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			capacity[i] = i;
		}
		return capacity;
	}

	
	@Override
	public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
		if(list.get(orderId).getAdvertText().contentEquals(advertText)) {
		//	System.out.println("True");
			return true;
		} else {
	//		System.out.println("False");
			return false;
		}
	}
	
	@Override
	public boolean start() throws RemoteException {
		if(enabled) {
			Thread dataReading = new Thread(() -> {
				while (true) {
					if (enabled) {

						try {
							if (clients.containsKey('f')) {
								Order order = clients.get('f').getOrder();
								boolean flag = false;
								for(int i = 0; i<list.size(); i++) {
//									flag = addAdvertisement(order.advertText, null, i);
								}
								for(int i = 0; i<list.size(); i++) {
									if(addAdvertisement(order.advertText, null, i)) {
										flag = true;
									} 
								}
								if(!flag) {
									BillboardStorage newItem = new BillboardStorage('f', order.advertText);
									System.out.println("DODAJÊ");
									list.add(newItem);
									
									flag = false;
								} 
								
								System.out.println(list.get(counter).getOwner() + "  " + list.get(counter).getAdvertText() + "  " + counter);
								advert = list.get(counter).getAdvertText();
								order.advertText = advert;
								counter++;
								if(counter == list.size()) {
									counter = 0;
								}
															
								System.out.println("\n");
								
								frame.updateOrder(order);
							} else {
								for(int i=0; i< list.size(); i++) {
									if(list.get(i).getOwner() == 'f') {
										list.remove(i);
									}
								}
							}

							if (clients.containsKey('s')) {
								Order order = clients.get('s').getOrder();
								boolean flag = false;
								for(int i = 0; i<list.size(); i++) {
									if(list.get(i).getAdvertText().contentEquals( order.advertText)) {
										flag = true;
									}
								}
								if(!flag) {
									BillboardStorage newItem = new BillboardStorage('s', order.advertText);
									System.out.println("DODAJÊ");
									list.add(newItem);
									flag = false;
								} 

								//frame.updateOrder(order);
							} else {
								for(int i=0; i< list.size(); i++) {
									if(list.get(i).getOwner() == 's') {
										list.remove(i);
									}
								}
							
							}
							
							
							
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
									System.out.println("DODAJÊ");
									list.add(newItem);
									flag = false;
								} 
								//frame.updateOrder(order);
							} else {
								for(int i=0; i< list.size(); i++) {
									if(list.get(i).getOwner() == 't') {
										list.remove(i);
									}
								}
							
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
			return true;
		} else {
			return false;
		}
		
		
	}

	@Override
	public boolean stop() throws RemoteException {
		enabled = false;
		return false;
	}
}
