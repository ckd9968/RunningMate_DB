package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.PartyRegisterController;
import entities.Party;

public class PartyRegisterFrame extends JFrame {
	private String userName = null;
	private JPanel contentPane;
	private JTextField partyNameField;
	private JTextField meetingPlaceField;
	private JTextField yearField;
	private JTextField monthField;
	private JTextField dateField;
	private JTextField hourField;
	private JTextField minuteField;
	private PartyRegisterController con = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyRegisterFrame frame = new PartyRegisterFrame(null, null);
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
	public PartyRegisterFrame(PartyRegisterController c, String userID) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.con = c;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.userName = userID;
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		
		JLabel lblNewLabel = new JLabel("파티명 :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(12, 26, 123, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("모임장소 :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_1.setBounds(12, 69, 123, 22);
		contentPane.add(lblNewLabel_1);
		
		partyNameField = new JTextField();
		partyNameField.setBounds(150, 20, 231, 29);
		contentPane.add(partyNameField);
		partyNameField.setColumns(10);
		
		meetingPlaceField = new JTextField();
		meetingPlaceField.setBounds(150, 62, 231, 29);
		contentPane.add(meetingPlaceField);
		meetingPlaceField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("-약속시간-");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblNewLabel_2.setBounds(172, 101, 91, 40);
		contentPane.add(lblNewLabel_2);
		
		yearField = new JTextField();
		yearField.setBounds(12, 151, 81, 21);
		contentPane.add(yearField);
		yearField.setColumns(10);
		
		monthField = new JTextField();
		monthField.setBounds(124, 151, 40, 21);
		contentPane.add(monthField);
		monthField.setColumns(10);
		
		dateField = new JTextField();
		dateField.setBounds(197, 151, 40, 21);
		contentPane.add(dateField);
		dateField.setColumns(10);
		
		hourField = new JTextField();
		hourField.setBounds(281, 151, 40, 21);
		contentPane.add(hourField);
		hourField.setColumns(10);
		
		minuteField = new JTextField();
		minuteField.setBounds(357, 151, 40, 21);
		contentPane.add(minuteField);
		minuteField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("년");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_3.setBounds(98, 151, 28, 21);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("월");
		lblNewLabel_4.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_4.setBounds(168, 151, 50, 18);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("일");
		lblNewLabel_5.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_5.setBounds(241, 151, 28, 18);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("시");
		lblNewLabel_6.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_6.setBounds(327, 151, 28, 18);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("분");
		lblNewLabel_7.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_7.setBounds(409, 151, 40, 21);
		contentPane.add(lblNewLabel_7);
		
		JButton partyRegisterButton = new JButton("파티등록");
		partyRegisterButton.setBounds(21, 212, 128, 41);
		contentPane.add(partyRegisterButton);
		partyRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(wrongFormData()) {
					return;
				}
				String partyName = partyNameField.getText();
				String meetingPlace = meetingPlaceField.getText();
				Party newparty = new Party(null, partyName, meetingPlace, userName, yearField.getText(), monthField.getText(), dateField.getText(), hourField.getText(), minuteField.getText());
				if(con.registerParty(newparty)) {
					JOptionPane.showMessageDialog(null, "성공적으로 파티를 등록했습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "이미 등록된 파티가 있습니다.", "중복 등록", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton cancleButton = new JButton("취소");
		cancleButton.setBounds(296, 212, 128, 41);
		contentPane.add(cancleButton);
		
		JButton partyRemovalButton = new JButton("파티 삭제");
		partyRemovalButton.setBounds(161, 212, 123, 41);
		contentPane.add(partyRemovalButton);
		partyRemovalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(con.removeParty(userID)) {
					JOptionPane.showMessageDialog(null, "파티가 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "파티 삭제에 실패했습니다.", "기능 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnOff();
			}
		});
		
		setVisible(true);
	}
	
	private void turnOff() {
		dispose();
	}
	
	private boolean wrongFormData() {
		// 달린거리와 장소를 입력했는가?
		// 달린거리는 형 변환이 가능한가?
		if(partyNameField.getText().length() * meetingPlaceField.getText().length()
				* hourField.getText().length() * minuteField.getText().length() * yearField.getText().length()
				* monthField.getText().length() * dateField.getText().length()== 0) {
			JOptionPane.showMessageDialog(null, "입력값이 없습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		String[] time_args = {hourField.getText(), minuteField.getText(), yearField.getText(), monthField.getText(), dateField.getText()};
		for(int i = 0; i < time_args.length; i++) {
			char[] time_tokken = time_args[i].toCharArray();
			for(char j : time_tokken) {
				if(j< '0' || j > '9') {
					JOptionPane.showMessageDialog(null, "시간엔 숫자만 가능합니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		}
		
		return false;
	}
}
