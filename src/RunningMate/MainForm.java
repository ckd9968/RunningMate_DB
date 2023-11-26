package RunningMate;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
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
	public MainForm() {
		setResizable(false);
		setTitle("런닝 메이트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("회원 검색");
		btnNewButton.addActionListener(new ActionListener() {
			// 회원 검색 버튼 클릭 시 창 전환
			public void actionPerformed(ActionEvent e) {
				new SearchMemberForm();
				setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 10, 418, 78);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("크루");
		btnNewButton_1.addActionListener(new ActionListener() {
			// 크루 검색 버튼 클릭 시 창 전환
			public void actionPerformed(ActionEvent e) {
				new SearchCrewForm();
				setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 98, 418, 78);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("파티");
		btnNewButton_2.addActionListener(new ActionListener() {
			
			// 파티 버튼 클릭 시 창 전환
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton_2.setBounds(10, 186, 418, 78);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("기록하기");
		btnNewButton_3.addActionListener(new ActionListener() {
			// 기록 버튼 클릭시 창 전환
			public void actionPerformed(ActionEvent e) {
				new RecordForm();
				setVisible(true);
			}
		});
		btnNewButton_3.setBounds(10, 274, 418, 78);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("코스 추천");
		btnNewButton_4.addActionListener(new ActionListener() {
			// 추천 코스 버튼 클릭시 창 전환
			public void actionPerformed(ActionEvent e) {
				new RecommendCourseForm();
				setVisible(true);
			}
		});
		btnNewButton_4.setBounds(10, 362, 418, 78);
		contentPane.add(btnNewButton_4);
	}
}
