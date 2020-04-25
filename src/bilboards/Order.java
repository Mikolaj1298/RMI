package bilboards;

import java.io.Serializable;
import java.time.Duration;

@SuppressWarnings("serial")
public class Order implements Serializable{
	public char category;
	public float value;
	
	public String advertText;
	public Duration displayPeriod;
	public IClient client;

}
