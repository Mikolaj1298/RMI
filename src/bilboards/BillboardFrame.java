package bilboards;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class BillboardFrame extends JFrame {

	private Billboard billboard;
	private JPanel contentPane;
	private JLabel lblTRegistered;
	private JLabel lblWRegistered;
	private JLabel lblPRegistered;
	private JLabel lblTReadings;
	private JLabel lblWReadings;
	private JLabel lblPReadings;
	private JLabel lblEnabled;

	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillboardFrame frame = new BillboardFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BillboardFrame() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				try {
					billboard.unregisterFromManager();
				} catch (Exception e) {}
			}
		});
		setTitle("Billboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		
//		try {
//			billboard.registerToManager();
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(BillboardFrame.this, "Register failed", "Error",
//					JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
//		}
		

//		JButton btnRegister = new JButton("Register");
//		btnRegister.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent event) {
//				if (btnRegister.getText().equals("Register")) {
//					try {
//						billboard.registerToManager();
//						btnRegister.setText("Unregister");
//					} catch (Exception e) {
//						JOptionPane.showMessageDialog(BillboardFrame.this, "Register failed", "Error",
//								JOptionPane.ERROR_MESSAGE);
//						e.printStackTrace();
//					}
//				} else {
//					try {
//						billboard.unregisterFromManager();
//						btnRegister.setText("Register");
//					} catch (Exception e) {
//						JOptionPane.showMessageDialog(BillboardFrame.this, "Unregister failed", "Error",
//								JOptionPane.ERROR_MESSAGE);
//						e.printStackTrace();
//					}
//				}
//			}
//		});
		
//		JLabel lblTemperatureSensor = new JLabel("Temperature Sensor");
//		GridBagConstraints gbc_lblTemperatureSensor = new GridBagConstraints();
//		gbc_lblTemperatureSensor.insets = new Insets(0, 0, 5, 5);
//		gbc_lblTemperatureSensor.gridx = 0;
//		gbc_lblTemperatureSensor.gridy = 0;
//		contentPane.add(lblTemperatureSensor, gbc_lblTemperatureSensor);
		
		
		
//		lblTRegistered = new JLabel("NO");
//		GridBagConstraints gbc_lblTRegistered = new GridBagConstraints();
//		gbc_lblTRegistered.insets = new Insets(0, 0, 5, 5);
//		gbc_lblTRegistered.gridx = 1;
//		gbc_lblTRegistered.gridy = 1;
//		contentPane.add(lblTRegistered, gbc_lblTRegistered);
		
//		JLabel lblReadings = new JLabel("Readings:");
//		GridBagConstraints gbc_lblReadings = new GridBagConstraints();
//		gbc_lblReadings.insets = new Insets(0, 0, 5, 5);
//		gbc_lblReadings.gridx = 3;
//		gbc_lblReadings.gridy = 1;
//		contentPane.add(lblReadings, gbc_lblReadings);
		
		lblTReadings = new JLabel("To miejsce czeka na Twój billboard");
		lblTReadings.setFont(new Font("Serif", Font.PLAIN, 20));
		GridBagConstraints gbc_lblTReadings = new GridBagConstraints();
		gbc_lblTReadings.anchor = GridBagConstraints.EAST;
		gbc_lblTReadings.insets = new Insets(0, 0, 5, 0);
		gbc_lblTReadings.gridx = 1;
		gbc_lblTReadings.gridy = 1;
		contentPane.add(lblTReadings, gbc_lblTReadings);
		
//		JSeparator separator = new JSeparator();
//		separator.setForeground(Color.DARK_GRAY);
//		GridBagConstraints gbc_separator = new GridBagConstraints();
//		gbc_separator.gridwidth = 5;
//		gbc_separator.fill = GridBagConstraints.BOTH;
//		gbc_separator.insets = new Insets(0, 0, 5, 0);
//		gbc_separator.gridx = 0;
//		gbc_separator.gridy = 2;
//		contentPane.add(separator, gbc_separator);
		
//		JLabel lblWindSensor = new JLabel("Free Slots");
//		GridBagConstraints gbc_lblWindSensor = new GridBagConstraints();
//		gbc_lblWindSensor.anchor = GridBagConstraints.WEST;
//		gbc_lblWindSensor.insets = new Insets(0, 0, 5, 5);
//		gbc_lblWindSensor.gridx = 0;
//		gbc_lblWindSensor.gridy = 3;
//		contentPane.add(lblWindSensor, gbc_lblWindSensor);
		
//		JLabel label = new JLabel("Registered:");
//		GridBagConstraints gbc_label = new GridBagConstraints();
//		gbc_label.anchor = GridBagConstraints.WEST;
//		gbc_label.insets = new Insets(0, 0, 5, 5);
//		gbc_label.gridx = 0;
//		gbc_label.gridy = 4;
//		contentPane.add(label, gbc_label);
		
//		lblWRegistered = new JLabel("NO");
//		GridBagConstraints gbc_lblWRegistered = new GridBagConstraints();
//		gbc_lblWRegistered.insets = new Insets(0, 0, 5, 5);
//		gbc_lblWRegistered.gridx = 1;
//		gbc_lblWRegistered.gridy = 4;
//		contentPane.add(lblWRegistered, gbc_lblWRegistered);
		
//		JLabel label_4 = new JLabel("Readings:");
//		GridBagConstraints gbc_label_4 = new GridBagConstraints();
//		gbc_label_4.insets = new Insets(0, 0, 5, 5);
//		gbc_label_4.gridx = 3;
//		gbc_label_4.gridy = 4;
//		contentPane.add(label_4, gbc_label_4);
		
//		lblWReadings = new JLabel("[km/h]");
//		GridBagConstraints gbc_lblWReadings = new GridBagConstraints();
//		gbc_lblWReadings.anchor = GridBagConstraints.EAST;
//		gbc_lblWReadings.insets = new Insets(0, 0, 5, 0);
//		gbc_lblWReadings.gridx = 4;
//		gbc_lblWReadings.gridy = 4;
//		contentPane.add(lblWReadings, gbc_lblWReadings);
		
//		JSeparator separator_1 = new JSeparator();
//		separator_1.setForeground(Color.DARK_GRAY);
//		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
//		gbc_separator_1.gridwidth = 5;
//		gbc_separator_1.fill = GridBagConstraints.BOTH;
//		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
//		gbc_separator_1.gridx = 0;
//		gbc_separator_1.gridy = 5;
//		contentPane.add(separator_1, gbc_separator_1);
		
//		JLabel lblNewLabel = new JLabel("Precipitation Sensor");
//		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
//		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
//		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
//		gbc_lblNewLabel.gridx = 0;
//		gbc_lblNewLabel.gridy = 6;
//		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
//		JLabel label_1 = new JLabel("Registered:");
//		GridBagConstraints gbc_label_1 = new GridBagConstraints();
//		gbc_label_1.anchor = GridBagConstraints.WEST;
//		gbc_label_1.insets = new Insets(0, 0, 5, 5);
//		gbc_label_1.gridx = 0;
//		gbc_label_1.gridy = 7;
//		contentPane.add(label_1, gbc_label_1);
		
//		lblPRegistered = new JLabel("NO");
//		GridBagConstraints gbc_lblPRegistered = new GridBagConstraints();
//		gbc_lblPRegistered.insets = new Insets(0, 0, 5, 5);
//		gbc_lblPRegistered.gridx = 1;
//		gbc_lblPRegistered.gridy = 7;
//		contentPane.add(lblPRegistered, gbc_lblPRegistered);
		
//		JLabel label_5 = new JLabel("Readings:");
//		GridBagConstraints gbc_label_5 = new GridBagConstraints();
//		gbc_label_5.insets = new Insets(0, 0, 5, 5);
//		gbc_label_5.gridx = 3;
//		gbc_label_5.gridy = 7;
//		contentPane.add(label_5, gbc_label_5);
		
//		lblPReadings = new JLabel("[mm]");
//		GridBagConstraints gbc_lblPReadings = new GridBagConstraints();
//		gbc_lblPReadings.anchor = GridBagConstraints.EAST;
//		gbc_lblPReadings.insets = new Insets(0, 0, 5, 0);
//		gbc_lblPReadings.gridx = 4;
//		gbc_lblPReadings.gridy = 7;
//		contentPane.add(lblPReadings, gbc_lblPReadings);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 5;
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 8;
		contentPane.add(separator_2, gbc_separator_2);
//		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
//		gbc_btnRegister.fill = GridBagConstraints.HORIZONTAL;
//		gbc_btnRegister.insets = new Insets(0, 0, 0, 5);
//		gbc_btnRegister.gridx = 0;
//		gbc_btnRegister.gridy = 9;
//		contentPane.add(btnRegister, gbc_btnRegister);
		
//		JLabel lblEn = new JLabel("Enabled:");
//		GridBagConstraints gbc_lblEn = new GridBagConstraints();
//		gbc_lblEn.insets = new Insets(0, 0, 0, 5);
//		gbc_lblEn.gridx = 3;
//		gbc_lblEn.gridy = 9;
//		contentPane.add(lblEn, gbc_lblEn);
		
//		lblEnabled = new JLabel("YES");
//		GridBagConstraints gbc_lblEnabled = new GridBagConstraints();
//		gbc_lblEnabled.gridx = 4;
//		gbc_lblEnabled.gridy = 9;
//		contentPane.add(lblEnabled, gbc_lblEnabled);


		billboard = new Billboard(this);
		
		try {
			billboard.registerToManager();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(BillboardFrame.this, "Register failed", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
public void updateLabels(Map<Character, IClient> clients) {
//	if (clients.containsKey('t'))
//		lblTRegistered.setText("YES");
//	else
//		lblTRegistered.setText("NO");
//	
//	if (clients.containsKey('w'))
//		lblWRegistered.setText("YES");
//	else
//		lblWRegistered.setText("NO");
//	
//	if (clients.containsKey('p'))
//		lblPRegistered.setText("YES");
//	else
//		lblPRegistered.setText("NO");
	}

	public void updateOrder(Order order) {
		char clientType = order.category;
		float value = order.value;
		String advert = order.advertText;
		
		switch (clientType) {
		case 't':
			lblTReadings.setText(advert);
			
			break;
		}
		
	}
	
	public void clearOrder(char category) {
		switch (category) {
		case 't':
			lblTReadings.setText("[°C]");
			break;
		case 'w':
			lblWReadings.setText("[km/h]");
			break;
		case 'p':
			lblPReadings.setText("[mm]");
		}
	}

	public void toggle(boolean value) {
		if (value) {
			lblEnabled.setText("YES");
			lblTReadings.setText("[°C]");
			lblWReadings.setText("[km/h]");
			lblPReadings.setText("[mm]");
		}
		else {
			lblEnabled.setText("NO");
			lblTReadings.setText("X");
			lblWReadings.setText("X");
			lblPReadings.setText("X");
		}
	}

	
}
