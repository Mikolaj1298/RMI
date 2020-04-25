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
import java.rmi.RemoteException;
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

	private JLabel lblAdvert;
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

	public BillboardFrame() throws RemoteException {
		
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
		

		
		lblAdvert = new JLabel("To miejsce czeka na Twój billboard");
		lblAdvert.setFont(new Font("Serif", Font.PLAIN, 20));
		GridBagConstraints gbc_lblAdvert = new GridBagConstraints();
		gbc_lblAdvert.anchor = GridBagConstraints.EAST;
		gbc_lblAdvert.insets = new Insets(0, 0, 5, 0);
		gbc_lblAdvert.gridx = 1;
		gbc_lblAdvert.gridy = 1;
		contentPane.add(lblAdvert, gbc_lblAdvert);
		

		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 5;
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 8;
		contentPane.add(separator_2, gbc_separator_2);



		billboard = new Billboard(this);
		
		try {
			billboard.registerToManager();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(BillboardFrame.this, "Register failed", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	


	public void updateOrder(Order order) {
		char clientType = order.category;

		String advert = order.advertText;
		
		lblAdvert.setText(advert);	

		
	}
	
	public void clearOrder(char category) {
		switch (category) {
		case 'f':
			lblAdvert.setText("To miejsce czeka na Twój billboard");
			break;
		}
	}

	

	
}
