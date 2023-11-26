package RunningMate;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JButton;

public class RecommendCourseForm extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecommendCourseForm frame = new RecommendCourseForm();
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
	public RecommendCourseForm() {
		setAlwaysOnTop(true);
		
		setVisible(true);
		
		setTitle("추천 코스");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 413, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 21, 55, 23);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("도");
		lblNewLabel.setBounds(75, 25, 18, 15);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(103, 21, 55, 23);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel_1 = new JLabel("시");
		lblNewLabel_1.setBounds(168, 25, 18, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("분위기");
		lblNewLabel_2.setBounds(196, 25, 50, 15);
		contentPane.add(lblNewLabel_2);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"전체", "좋음", "보통", "나쁨"}));
		comboBox_2.setBounds(242, 21, 50, 23);
		contentPane.add(comboBox_2);
		
		table = new JTable();
		table.setBounds(10, 71, 359, 119);
		contentPane.add(table);
		
		JButton btnNewButton = new JButton("검색하기");
		btnNewButton.setBounds(302, 21, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("닫기");
		btnNewButton_1.setBounds(278, 232, 93, 23);
		contentPane.add(btnNewButton_1);
	}

}
