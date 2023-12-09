package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.PartyContoller;
import controllers.TableLoader;

public class PartyFrame extends JFrame {

	private JPanel contentPane;
	private JTable partyTable;
	private static final String[] colnames = {"파티ID", "개설자", "파티명", "약속장소", "약속시간"};
	private DefaultTableModel t_model = new DefaultTableModel(PartyFrame.colnames, 0);
	private JButton participationButton;
	private JTextField partyIDField;
	private JLabel lblNewLabel;
	private JButton leaveButton;
	private JButton quitButton;
	private PartyContoller con;
	private boolean[] party_hash = new boolean[100000];

	
	private String userID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyFrame frame = new PartyFrame(new PartyContoller(), "MEM00004");
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
	public PartyFrame(PartyContoller c, String userID) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 665, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.con = c;
		this.userID = userID;
		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 51, 606, 398);
		contentPane.add(scrollPane);
		
		partyTable = new JTable(t_model);
		scrollPane.setViewportView(partyTable);
		this.loadPartyTable();
		
		participationButton = new JButton("파티참여");
		participationButton.setFont(new Font("굴림", Font.BOLD, 15));
		participationButton.setBounds(275, 10, 109, 31);
		contentPane.add(participationButton);
		participationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String partyID = partyIDField.getText();
				
				if(partyID.length() == 0) {
					JOptionPane.showMessageDialog(null, "파티ID를 입력해주세요.", "입력오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(invalidPartyID(partyID)) {
					JOptionPane.showMessageDialog(null, "파티ID를 제대로 입력해주세요.", "입력오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// 파티 가입 처리
				if(con.participateInParty(partyID, userID)) {
					JOptionPane.showMessageDialog(null, "가입에 성공하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);	
				}
			}
		});
		
		
		partyIDField = new JTextField();
		partyIDField.setBounds(104, 10, 159, 30);
		contentPane.add(partyIDField);
		partyIDField.setColumns(10);
		
		lblNewLabel = new JLabel("파티ID :");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(22, 18, 71, 23);
		contentPane.add(lblNewLabel);
		
		leaveButton = new JButton("파티탈퇴");
		leaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(con.leaveParty(userID)) {
					JOptionPane.showMessageDialog(null, "탈퇴에 성공하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		// 탈퇴기능
		leaveButton.setFont(new Font("굴림", Font.BOLD, 15));
		leaveButton.setBounds(396, 10, 109, 31);
		contentPane.add(leaveButton);
		
		quitButton = new JButton("나가기");
		quitButton.setFont(new Font("굴림", Font.BOLD, 15));
		quitButton.setBounds(517, 10, 109, 31);
		contentPane.add(quitButton);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnOff();
			}
		});
		
		setVisible(true);
	}
	
	private void turnOff() {
		dispose();
	}
	
	private void loadPartyTable() {
		ArrayList<String[]> tuples = TableLoader.loadTables("파티");
		t_model =new DefaultTableModel(colnames, 0);
		Iterator<String[]> it = tuples.iterator();
		Arrays.fill(party_hash, true);
		int cnt = 0;
		while(it.hasNext()) {
			String[] tuple = it.next();
			party_hash[getPartyNumber(tuple[0])] = false;
			t_model.addRow(tuple);
		}
		partyTable.setModel(t_model);
	}
	
	private boolean invalidPartyID(String partyID) {
		if(partyID.charAt(0) != 'P' || partyID.charAt(1) != 'T' || partyID.length() != "PT00000".length()) {
			return true;
		}
		return party_hash[getPartyNumber(partyID)];
	}
	
	private int getPartyNumber(String party_id) {
		int i = 2;
		while(party_id.charAt(i) == '0') {
			i++;
		}
		
		return Integer.parseInt(party_id.substring(i));

	}
}