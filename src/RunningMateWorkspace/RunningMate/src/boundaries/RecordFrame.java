package boundaries;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.JButton;

public class RecordFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordFrame frame = new RecordFrame();
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
	public RecordFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("달린거리 :");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(12, 35, 92, 34);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(116, 35, 126, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("달린장소 :");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_1.setBounds(12, 102, 92, 34);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(116, 102, 243, 34);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("혼자 기록");
		rdbtnNewRadioButton.setFont(new Font("굴림", Font.BOLD, 18));
		rdbtnNewRadioButton.setBounds(12, 170, 113, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("동시 기록");
		rdbtnNewRadioButton_1.setFont(new Font("굴림", Font.BOLD, 18));
		rdbtnNewRadioButton_1.setBounds(129, 170, 113, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("파티 기록");
		rdbtnNewRadioButton_2.setFont(new Font("굴림", Font.BOLD, 18));
		rdbtnNewRadioButton_2.setBounds(246, 170, 113, 23);
		contentPane.add(rdbtnNewRadioButton_2);
		
		JList list = new JList();
		list.setBounds(222, 222, 113, 150);
		contentPane.add(list);
		
		JButton btnNewButton = new JButton("동행자 추가");
		btnNewButton.setBounds(25, 298, 113, 28);
		contentPane.add(btnNewButton);
		
		textField_2 = new JTextField();
		textField_2.setBounds(25, 267, 113, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("   >>");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_2.setBounds(150, 284, 50, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("km");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 16));
		lblNewLabel_3.setBounds(252, 45, 50, 15);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("기록");
		btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
		btnNewButton_1.setBounds(25, 417, 139, 36);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("취소");
		btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 18));
		btnNewButton_2.setBounds(207, 418, 139, 36);
		contentPane.add(btnNewButton_2);
	}
}
