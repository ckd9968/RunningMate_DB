package RunningMate;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EquipmentForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTable table_1;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EquipmentForm frame = new EquipmentForm();
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
	public EquipmentForm() {
		setAlwaysOnTop(true);
		
		setVisible(true);
		
		setTitle("장비");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 621, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(160, 43, 284, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("장비 검색");
		lblNewLabel.setBounds(90, 42, 60, 26);
		contentPane.add(lblNewLabel);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("전체");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setBounds(160, 87, 70, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("장갑");
		chckbxNewCheckBox_1.setBounds(304, 87, 70, 23);
		contentPane.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("신발");
		chckbxNewCheckBox_2.setBounds(232, 87, 70, 23);
		contentPane.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("헤드셋");
		chckbxNewCheckBox_3.setBounds(374, 87, 70, 23);
		contentPane.add(chckbxNewCheckBox_3);
		
		table = new JTable();
		table.setBounds(23, 168, 226, 108);
		contentPane.add(table);
		
		JLabel lblNewLabel_1 = new JLabel("종류 선택");
		lblNewLabel_1.setBounds(90, 85, 63, 26);
		contentPane.add(lblNewLabel_1);
		
		table_1 = new JTable();
		table_1.setBounds(362, 168, 226, 108);
		contentPane.add(table_1);
		
		JButton btnNewButton = new JButton("담기");
		btnNewButton.setBounds(259, 168, 93, 44);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("삭제");
		btnNewButton_1.setBounds(259, 232, 93, 44);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("검색 결과");
		lblNewLabel_2.setBounds(121, 132, 70, 26);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("장바구니");
		lblNewLabel_3.setBounds(445, 138, 70, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("총 금액");
		lblNewLabel_4.setBounds(175, 303, 60, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("예산");
		lblNewLabel_5.setBounds(175, 342, 60, 15);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("남은 금액");
		lblNewLabel_6.setBounds(175, 383, 60, 15);
		contentPane.add(lblNewLabel_6);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(259, 300, 126, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(259, 336, 126, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(259, 377, 126, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("닫기");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton_2.setBounds(328, 430, 93, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("검색");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(464, 44, 93, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("계산");
		btnNewButton_4.setBounds(195, 430, 93, 23);
		contentPane.add(btnNewButton_4);
	}
}
