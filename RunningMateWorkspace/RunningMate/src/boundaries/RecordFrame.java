package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controllers.RecordingController;

public class RecordFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField distanceTextField;
	private JTextField courseTextField;
	private JTextField companionField;
	
	private DefaultListModel<String> l_model = new DefaultListModel<String>();
	private JList<String> companionList = new JList<String>(l_model);
	
	private JScrollPane scp = null;
	
	private JRadioButton singleRecordButton, groupRecordButton, partyRecordButton;
	
	private ButtonGroup recordTypes = new ButtonGroup();
	
	private JButton addCompanionButton;
	
	private RecordingController recordController = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordFrame frame = new RecordFrame(null);
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
	public RecordFrame(RecordingController c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 500);
		setTitle("기록하기");
		
		this.recordController = c;
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("달린거리 :");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(12, 35, 92, 34);
		contentPane.add(lblNewLabel);
		
		distanceTextField = new JTextField();
		distanceTextField.setBounds(116, 35, 126, 34);
		contentPane.add(distanceTextField);
		distanceTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("달린장소 :");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_1.setBounds(12, 102, 92, 34);
		contentPane.add(lblNewLabel_1);
		
		courseTextField = new JTextField();
		courseTextField.setBounds(116, 102, 243, 34);
		contentPane.add(courseTextField);
		courseTextField.setColumns(10);
		
		singleRecordButton = new JRadioButton("혼자 기록");
		singleRecordButton.setFont(new Font("굴림", Font.BOLD, 18));
		singleRecordButton.setBounds(12, 170, 113, 23);
		singleRecordButton.addActionListener(this);
		singleRecordButton.setSelected(true);
		contentPane.add(singleRecordButton);
		
		groupRecordButton = new JRadioButton("동시 기록");
		groupRecordButton.setFont(new Font("굴림", Font.BOLD, 18));
		groupRecordButton.setBounds(129, 170, 113, 23);
		groupRecordButton.addActionListener(this);
		contentPane.add(groupRecordButton);
		
		partyRecordButton = new JRadioButton("파티 기록");
		partyRecordButton.setFont(new Font("굴림", Font.BOLD, 18));
		partyRecordButton.setBounds(246, 170, 113, 23);
		partyRecordButton.addActionListener(this);
		contentPane.add(partyRecordButton);
		
		recordTypes.add(singleRecordButton);
		recordTypes.add(groupRecordButton);
		recordTypes.add(partyRecordButton);
		
		
		//companionList.setBounds(222, 222, 113, 150);
		scp = new JScrollPane(companionList);
		scp.setBounds(222,222,113,150);
		contentPane.add(scp);
		
		addCompanionButton = new JButton("동행자 추가");
		addCompanionButton.setEnabled(false);
		addCompanionButton.setBounds(25, 298, 113, 28);
		
		addCompanionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(companionField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "입력오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				l_model.addElement(companionField.getText());
				companionField.setText("");
			}
		});
		contentPane.add(addCompanionButton);
		
		companionField = new JTextField();
		companionField.setBounds(25, 267, 113, 21);
		contentPane.add(companionField);
		companionField.setColumns(10);
		companionField.setEnabled(false);
		
		JLabel lblNewLabel_2 = new JLabel("   >>");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_2.setBounds(150, 284, 50, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("km");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 16));
		lblNewLabel_3.setBounds(252, 45, 50, 15);
		contentPane.add(lblNewLabel_3);
		
		
		// 기록 동작 제어
		JButton recordButton = new JButton("기록");
		recordButton.setFont(new Font("굴림", Font.BOLD, 18));
		recordButton.setBounds(25, 417, 139, 36);
		contentPane.add(recordButton);
		recordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//라디오버튼에 따라 다른 메소드 호출
				if(!checkFormData()) {
					return;
				}
				int record_type = getRadioButtonNumber();
				Object[] record_result = null;
				String[] ids;
				String[] record_arg = {distanceTextField.getText(), courseTextField.getText() };
				switch(record_type) {
				case 1:
					ids = new String[] { "내 아이디" };
					record_result = recordController.recordSingle(ids, record_arg); break;
				case 2:
					String id_list = 
					record_result = recordController.recordGroup(null, null); break;
				case 3:
					record_result = recordController.recordParty(null, null); break;
				}
			}
		});
		
		
		JButton cancleButton = new JButton("취소");
		cancleButton.setFont(new Font("굴림", Font.BOLD, 18));
		cancleButton.setBounds(207, 418, 139, 36);
		contentPane.add(cancleButton);
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnOff();
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		JRadioButton event_button = (JRadioButton)e.getSource();
		if(event_button.getText().equals("동시 기록") && event_button.isSelected()) {
			this.companionField.setEnabled(true);
			this.companionList.setEnabled(true);
			this.addCompanionButton.setEnabled(true);
		} else {
			this.companionField.setEnabled(false);
			this.companionList.setEnabled(false);
			this.addCompanionButton.setEnabled(false);
		}
	}
	
	private void turnOff() {
		this.dispose();
	}
	
	private int getRadioButtonNumber() {
		int num = 1;
		if(groupRecordButton.isSelected()) num += 1;
		
		if(partyRecordButton.isSelected()) num += 2;
		
		return num;
	}
	
	private boolean checkFormData() {
		// 달린거리와 장소를 입력했는가?
		// 달린거리는 형 변환이 가능한가?
		if(distanceTextField.getText().length() * courseTextField.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "입력값이 없습니다.", "입력오류", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		char[] dist = distanceTextField.getText().toCharArray();
		for(char c : dist) {
			if((c < '0' || c > '9') && (c != '.')) {
				JOptionPane.showMessageDialog(null, "거리입력이 잘못 됐습니다.\nusage: ppp.ss", "입력오류", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		return true;
	}
	
	private String[] getGroupIDs() {
		l_model.last
	}
}
