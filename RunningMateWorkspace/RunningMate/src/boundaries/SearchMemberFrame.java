package boundaries;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SearchMemberFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTable table_1;
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchMemberFrame frame = new SearchMemberFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchMemberFrame() {
		setAlwaysOnTop(true);
		
		setVisible(true);
		
		setTitle("회원 검색하기");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 406, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 247, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("검색");
		btnNewButton.setBounds(282, 9, 93, 23);
		contentPane.add(btnNewButton);
		
		table = new JTable();
		table.setBounds(10, 68, 365, 99);
		contentPane.add(table);
		
		table_1 = new JTable();
		table_1.setBounds(10, 344, 365, 170);
		contentPane.add(table_1);
		
		JLabel lblNewLabel = new JLabel("기간");
		lblNewLabel.setBounds(34, 302, 50, 21);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "1개월", "2개월", "6개월", "1년"}));
		comboBox.setBounds(94, 301, 50, 23);
		contentPane.add(comboBox);
		
		JButton btnNewButton_1 = new JButton("기록 조회");
		btnNewButton_1.setBounds(164, 301, 93, 23);
		contentPane.add(btnNewButton_1);
		
		table_2 = new JTable();
		table_2.setBounds(10, 193, 365, 99);
		contentPane.add(table_2);
	}
}
