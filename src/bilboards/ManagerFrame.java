package bilboards;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class ManagerFrame extends JFrame {

	private Manager manager;
	private JPanel contentPane;
	private JTable table;
	private JLabel lblStatus;
	private JScrollPane scrollPane;
	private JLabel lblIntervals;
	private JSpinner spinner;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerFrame frame = new ManagerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ManagerFrame() {
		setTitle("Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 97, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 6;
		gbc_scrollPane.gridwidth = 10;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setModel(new OrderTableModel());
		table.getModel().addTableModelListener(new OrderTableModelListener());
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 10;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 6;
		contentPane.add(separator, gbc_separator);
		
		lblIntervals = new JLabel("Interval (ms):");
		GridBagConstraints gbc_lblIntervalms = new GridBagConstraints();
		gbc_lblIntervalms.insets = new Insets(0, 0, 0, 5);
		gbc_lblIntervalms.gridx = 0;
		gbc_lblIntervalms.gridy = 7;
		contentPane.add(lblIntervals, gbc_lblIntervalms);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(3), null, null, new Integer(1)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 7;
		contentPane.add(spinner, gbc_spinner);

		JLabel labelStat = new JLabel("Status:");
		GridBagConstraints gbc_labelStat = new GridBagConstraints();
		gbc_labelStat.insets = new Insets(0, 0, 0, 5);
		gbc_labelStat.gridx = 8;
		gbc_labelStat.gridy = 7;
		contentPane.add(labelStat, gbc_labelStat);

		lblStatus = new JLabel("<status>");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.gridx = 9;
		gbc_lblStatus.gridy = 7;
		contentPane.add(lblStatus, gbc_lblStatus);
		
		
		try {
			manager = Manager.createManager(this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server exception!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public void managerRunning() {
		lblStatus.setText("Running");
	}
	
	public void insertBillboard(int id, IBillboard billboard) {
		OrderTableModel model = (OrderTableModel) table.getModel();
		model.addRow(new Object[] { id, "init", 0, 5000, true });
		this.repaint();
	}
	
	public void removeBillboard(int id) {
		OrderTableModel model = (OrderTableModel) table.getModel();
		model.removeRow(id);
		this.repaint();
	}
	
	public int getInterval() {
		return (int) spinner.getValue();
	}
	
	public void updateValue(int id, BillboardData data) {
		OrderTableModel model = (OrderTableModel) table.getModel();
		int row = model.getRow(id);

		model.setValueAt(data.advert, row, 1);
		model.setValueAt(data.capacity.length, row, 2);
		model.setValueAt(data.duration, row, 3);
	}

	class OrderTableModelListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent event) {
			int row = event.getFirstRow();
			int col = event.getColumn();

			try {
				OrderTableModel model = (OrderTableModel) table.getModel();
				int boardID = (int) model.getValueAt(row, 0);

				if (col == 4)
					manager.setInterval(boardID, (int) model.getValueAt(row, col));
				
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(ManagerFrame.this, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	


	
	
}
