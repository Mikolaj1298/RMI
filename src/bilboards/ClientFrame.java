package bilboards;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ClientFrame extends JFrame {

	private Client client;
	private JPanel contentPane;
	private JComboBox<String> comboBoxClientType;
	private JComboBox<String> comboBoxBillboards;
	private JTextField advertTextField;
	private String advertText;
	private JSpinner spinner;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientFrame() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblSensorType = new JLabel("Select slot:");
		GridBagConstraints gbc_lblSensorType = new GridBagConstraints();
		gbc_lblSensorType.insets = new Insets(0, 0, 5, 5);
		gbc_lblSensorType.anchor = GridBagConstraints.WEST;
		gbc_lblSensorType.gridx = 0;
		gbc_lblSensorType.gridy = 0;
		contentPane.add(lblSensorType, gbc_lblSensorType);

		comboBoxClientType = new JComboBox<>();
		comboBoxClientType.addItem("First slot");
		comboBoxClientType.addItem("Second slot");
		comboBoxClientType.addItem("Third slot");
		GridBagConstraints gbc_comboBoxSensorType = new GridBagConstraints();
		gbc_comboBoxSensorType.gridwidth = 2;
		gbc_comboBoxSensorType.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxSensorType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSensorType.gridx = 2;
		gbc_comboBoxSensorType.gridy = 0;
		contentPane.add(comboBoxClientType, gbc_comboBoxSensorType);

		JLabel lblBoardId = new JLabel("Billboard ID:");
		GridBagConstraints gbc_lblBoardId = new GridBagConstraints();
		gbc_lblBoardId.anchor = GridBagConstraints.WEST;
		gbc_lblBoardId.insets = new Insets(0, 0, 5, 5);
		gbc_lblBoardId.gridx = 0;
		gbc_lblBoardId.gridy = 1;
		contentPane.add(lblBoardId, gbc_lblBoardId);

		comboBoxBillboards = new JComboBox<>();
		GridBagConstraints gbc_comboBoxBoards = new GridBagConstraints();
		gbc_comboBoxBoards.gridwidth = 2;
		gbc_comboBoxBoards.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxBoards.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxBoards.gridx = 2;
		gbc_comboBoxBoards.gridy = 1;
		contentPane.add(comboBoxBillboards, gbc_comboBoxBoards);
		
		JLabel lblInterval = new JLabel("Duration (s):");
		GridBagConstraints gbc_lblInterval = new GridBagConstraints();
		gbc_lblInterval.anchor = GridBagConstraints.WEST;
		gbc_lblInterval.insets = new Insets(0, 0, 5, 5);
		gbc_lblInterval.gridx = 0;
		gbc_lblInterval.gridy = 2;
		contentPane.add(lblInterval, gbc_lblInterval);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(3), null, null, new Integer(1)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.gridwidth = 2;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 2;
		contentPane.add(spinner, gbc_spinner);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 4;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 5;
		contentPane.add(separator, gbc_separator);

		JButton btnRegister = new JButton("Register");
		JButton btnUnregister = new JButton("Unregister");
		btnUnregister.setEnabled(false);
		btnRegister.setEnabled(false);
		
		btnRegister.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent event) {
				if (comboBoxBillboards.getSelectedItem() != null) {
					
					String billboardIDString = (String) comboBoxBillboards.getSelectedItem();
					int billboardID = Integer.parseInt(billboardIDString);
//					System.out.println(billboardID);
					
					if (btnRegister.getText().equals("Register")) {
						try {
							if (client.registerToBillboard(billboardID)) {
								btnUnregister.setEnabled(true);
								//comboBoxBillboards.disable();
								//comboBoxClientType.disable();
							}
							else
								JOptionPane.showMessageDialog(ClientFrame.this, "This slot is already used in that board.", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(ClientFrame.this, "Register failed", "Error",
									JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					} 
				}  else {
					JOptionPane.showMessageDialog(ClientFrame.this, "No Billboard selected", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnUnregister.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent event) {
				if (comboBoxBillboards.getSelectedItem() != null) {
					
					String billboardIDString = (String) comboBoxBillboards.getSelectedItem();
					int billboardID = Integer.parseInt(billboardIDString);
//					System.out.println(billboardID);
					
						try {
							if (client.unregisterFromBillboard(billboardID)) {
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(ClientFrame.this, "Unregister failed", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				
			}
		});
		
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.anchor = GridBagConstraints.EAST;
		gbc_btnRegister.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegister.gridx = 2;
		gbc_btnRegister.gridy = 5;
		contentPane.add(btnRegister, gbc_btnRegister);
		
		
		GridBagConstraints gbc_btnUnregister = new GridBagConstraints();
		gbc_btnUnregister.anchor = GridBagConstraints.EAST;
		gbc_btnUnregister.insets = new Insets(0, 0, 0, 0);
		gbc_btnUnregister.gridx = 1;
		gbc_btnUnregister.gridy = 5;
		contentPane.add(btnUnregister, gbc_btnUnregister);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pullBillboards();
			}
		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.gridx = 3;
		gbc_btnRefresh.gridy = 5;
		contentPane.add(btnRefresh, gbc_btnRefresh);
		
		
		JButton btn_setAdvertText = new JButton("Add Advert Text");
		btn_setAdvertText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				btnRegister.setEnabled(true);
				advertText = advertTextField.getText();
			}
		});
		GridBagConstraints gbc_setAdvertText = new GridBagConstraints();
		gbc_setAdvertText.insets = new Insets(5, 5, 5, 5);
		gbc_setAdvertText.gridx = 0;
		gbc_setAdvertText.gridy = 4;
		contentPane.add(btn_setAdvertText, gbc_setAdvertText);
		
		
		advertTextField = new JTextField("", 10);
		advertTextField.getText();
		GridBagConstraints gbc_advertTextField = new GridBagConstraints();
		gbc_advertTextField.gridwidth = 2;
		gbc_advertTextField.insets = new Insets(0, 0, 5, 0);
		gbc_advertTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_advertTextField.gridx = 2;
		gbc_advertTextField.gridy = 4;
		contentPane.add(advertTextField, gbc_advertTextField);
		
		client = new Client(this);
		pullBillboards();
	}

	public String getClientType() {
		return (String) comboBoxClientType.getSelectedItem();
	}
	
	public int getInterval() {
		return (int) spinner.getValue();
	}
	
	private void pullBillboards() {
		try {
			Registry registry = LocateRegistry.getRegistry();
			updateBillboards(registry.list());
		} catch (Exception e) {}
	}

	private void updateBillboards(String[] list) {

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		for (int i = 0; i < list.length; i++) {
			if (list[i].startsWith("IBillboard"))
				model.addElement(list[i].substring(10, list[i].length()));
		}

		comboBoxBillboards.setModel(model);
	}
	
	public String getAdvertText() {
		return advertText;
	}
}
