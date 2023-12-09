package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.EquipmentsCombinationController;
import controllers.PartyContoller;
import controllers.PartyRegisterController;
import controllers.RecordingController;
import controllers.RegisterEquipmentController;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private String userID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame("MEM00001");
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
	public MainFrame(String userID) {
		setResizable(false);
		setTitle("런닝 메이트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.userID = userID;
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton openPartyFrameButton = new JButton("파티 가입");
		openPartyFrameButton.setFont(new Font("굴림", Font.BOLD, 18));
		openPartyFrameButton.addActionListener(new ActionListener() {
			
			// 파티 버튼 클릭 시 창 전환
			public void actionPerformed(ActionEvent e) {
				new PartyFrame(new PartyContoller(), userID);
			}
		});
		openPartyFrameButton.setBounds(12, 114, 418, 78);
		contentPane.add(openPartyFrameButton);
		
		JButton openRecordButton = new JButton("기록");
		openRecordButton.setFont(new Font("굴림", Font.BOLD, 18));
		openRecordButton.addActionListener(new ActionListener() {
			// 기록 버튼 클릭시 창 전환
			public void actionPerformed(ActionEvent e) {
				new RecordFrame(new RecordingController(), userID); // 생성자로 컨트롤러 전달
			}
		});
		openRecordButton.setBounds(12, 202, 418, 78);
		contentPane.add(openRecordButton);
		
		JButton openCourseReccomendationButton = new JButton("코스 추천");
		openCourseReccomendationButton.setFont(new Font("굴림", Font.BOLD, 18));
		openCourseReccomendationButton.addActionListener(new ActionListener() {
			// 추천 코스 버튼 클릭시 창 전환
			public void actionPerformed(ActionEvent e) {
				new RecommendCourseFrame();
			}
		});
		openCourseReccomendationButton.setBounds(12, 290, 418, 78);
		contentPane.add(openCourseReccomendationButton);
		
		JButton openEquipmentRegisterButton = new JButton("장비 등록");
		openEquipmentRegisterButton.setFont(new Font("굴림", Font.BOLD, 18));
		openEquipmentRegisterButton.addActionListener(new ActionListener() {
			// 장비 등록 버튼 클릭시 창 전환
			public void actionPerformed(ActionEvent e) {
				new RegisterEquipmentFrame(new RegisterEquipmentController(), userID);
			}
		});
		openEquipmentRegisterButton.setBounds(12, 378, 418, 78);
		contentPane.add(openEquipmentRegisterButton);
		
		JButton openPartyRegisterButton = new JButton("파티 등록");
		openPartyRegisterButton.setFont(new Font("굴림", Font.BOLD, 18));
		openPartyRegisterButton.setBounds(12, 25, 418, 79);
		contentPane.add(openPartyRegisterButton);
		openPartyRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PartyRegisterFrame(new PartyRegisterController(), userID);
			}
		});
		
		
		JButton openEquipmentsCombinationButton = new JButton("장비 조합 검색");
		openEquipmentsCombinationButton.setFont(new Font("굴림", Font.BOLD, 18));
		openEquipmentsCombinationButton.setBounds(12, 466, 418, 71);
		contentPane.add(openEquipmentsCombinationButton);
		openEquipmentsCombinationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EquipmentsCombinationFrame(new EquipmentsCombinationController());
			}
		});
		
		setVisible(true);
	}
}
