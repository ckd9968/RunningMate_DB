package boundaries;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controllers.DB_Connector;
import controllers.EquipmentsCombinationController;

public class EquipmentsCombinationFrame extends JFrame {

	private JPanel contentPane;
	private JTextField budgetTextField;
	private EquipmentsCombinationController controller = null;
	
	private JComboBox<String> item_priority2, item_priority1, item_priority3;
	private Object[]demodata = {"mem01234", "eq01234", "우주선", "스파오", "보이저", "10000"}; 
	private static final String[] colnames = {"소유회원", "장비ID", "장비종류", "브랜드", "제품명", "가격"};
	
	private DefaultTableModel t_model = new DefaultTableModel(EquipmentsCombinationFrame.colnames, 0);
	private JTable combinationTable = new JTable(t_model);
	
	private Map<String, String> parameterMap = new HashMap<String,String>();
	{
		parameterMap.put("운동화", "shoes");
		parameterMap.put("장갑", "gloves");
		parameterMap.put("헤드폰", "head_phones");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EquipmentsCombinationFrame frame = new EquipmentsCombinationFrame(null);
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
	public EquipmentsCombinationFrame(EquipmentsCombinationController c) {
		this.controller = c;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 826, 360);
		setTitle("예산 맞춤 장비 조합");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		budgetTextField = new JTextField();
		budgetTextField.setFont(new Font("굴림", Font.PLAIN, 18));
		budgetTextField.setBounds(97, 24, 217, 32);
		contentPane.add(budgetTextField);
		budgetTextField.setColumns(10);
		
		JLabel budgetLabel = new JLabel("예산 :");
		budgetLabel.setFont(new Font("굴림", Font.BOLD, 18));
		budgetLabel.setBounds(12, 26, 73, 30);
		contentPane.add(budgetLabel);
		
		JLabel priority_title_label = new JLabel("예산분배 우선순위");
		priority_title_label.setFont(new Font("굴림", Font.BOLD, 18));
		priority_title_label.setBounds(44, 75, 178, 38);
		contentPane.add(priority_title_label);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(31, 115, 254, 138);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel pr1label = new JLabel("1번 :");
		pr1label.setBounds(11, 6, 43, 22);
		panel.add(pr1label);
		pr1label.setFont(new Font("굴림", Font.BOLD, 18));
		
		JLabel pr2label = new JLabel("2번 :");
		pr2label.setBounds(11, 60, 43, 22);
		panel.add(pr2label);
		pr2label.setFont(new Font("굴림", Font.BOLD, 18));
		
		item_priority1 = new JComboBox<String>();
		item_priority1.setBounds(66, 7, 117, 22);
		panel.add(item_priority1);
		item_priority1.setModel(new DefaultComboBoxModel<String>(new String[] {"운동화", "장갑", "헤드폰"}));
		item_priority1.setFont(new Font("굴림", Font.PLAIN, 13));
		
		item_priority2 = new JComboBox<String>();
		item_priority2.setBounds(66, 62, 117, 20);
		panel.add(item_priority2);
		item_priority2.setFont(new Font("굴림", Font.PLAIN, 13));
		item_priority2.setModel(new DefaultComboBoxModel<String>(new String[] {"운동화", "장갑", "헤드폰"}));
		
		JLabel pr3label = new JLabel("3번 :");
		pr3label.setFont(new Font("굴림", Font.BOLD, 18));
		pr3label.setBounds(11, 106, 50, 22);
		panel.add(pr3label);
		
		item_priority3 = new JComboBox<String>();
		item_priority3.setFont(new Font("굴림", Font.PLAIN, 13));
		item_priority3.setModel(new DefaultComboBoxModel<String>(new String[] {"운동화", "장갑", "헤드폰"}));
		item_priority3.setBounds(66, 107, 117, 22);
		panel.add(item_priority3);
		
		JButton executeButton = new JButton("조합 짜기");
		executeButton.setFont(new Font("굴림", Font.BOLD, 18));
		executeButton.setBounds(12, 273, 142, 39);
		contentPane.add(executeButton);
		executeButton.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				int budget = getBudget();
				String priority1 = parameterMap.get(getPriority1());
				String priority2 = parameterMap.get(getPriority2());
				String priority3 = parameterMap.get(getPriority3());
				System.out.println(priority1 + " " + priority2 + " " + priority3);
				if (budget == -1 || priority1.equals(priority2) || 
						priority2.equals(priority3) ||
						priority1.equals(priority3)) {
					JOptionPane.showMessageDialog(null, "입력형식이 유효하지 않습니다.", "입력오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//t_model.addRow(demodata);
				ResultSet rs = controller.executeCombination(budget, priority1, priority2, priority3);
				
				DefaultTableModel new_model = new DefaultTableModel(EquipmentsCombinationFrame.colnames, 0);
				
				if(rs == null) {
					JOptionPane.showMessageDialog(null, "결과집합이 전달되지 않았습니다.", "실행오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					ResultSetMetaData schema = rs.getMetaData();
					if(schema.getColumnCount() == 1) {
						JOptionPane.showMessageDialog(null, "가능한 조합이 없습니다.", "입력오류", JOptionPane.WARNING_MESSAGE);
						return;
					}
					t_model = new_model;
					
					while(rs.next()) {
						String[] newRow = new String[schema.getColumnCount()];
						for(int i = 1; i <= schema.getColumnCount(); i++) {
							newRow[i-1] = rs.getObject(i).toString();
						}
					
						t_model.addRow(newRow);
					};
				} catch(SQLException exception) {
					exception.printStackTrace();
				} finally {
					try {
					rs.close();}
					catch(SQLException exx) {
						exx.printStackTrace();
					}
					DB_Connector.closeConnection();
				}
				combinationTable.setModel(t_model);
			}
		});
		
		
		JButton btnNewButton = new JButton("취소");
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
		btnNewButton.setBounds(172, 273, 142, 38);
		contentPane.add(btnNewButton);
		
		
		JScrollPane scrollPane = new JScrollPane(combinationTable);
		scrollPane.setBounds(326, 24, 474, 288);
		contentPane.add(scrollPane);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnOff();
			}
		});
		
		setVisible(true);
	}
	
	public String getPriority1() {
		return this.item_priority1.getSelectedItem().toString();
	}
	
	public String getPriority2() {
		return this.item_priority2.getSelectedItem().toString();
	}
	
	public String getPriority3() {
		return this.item_priority3.getSelectedItem().toString();
	}
	
	public int getBudget() {
		if(!this.inputValueCheck_instant(this.budgetTextField.getText()) || this.budgetTextField.getText().length() == 0) {
			return -1;
		}
		return Integer.parseInt(this.budgetTextField.getText());
	}
	
	protected boolean inputValueCheck_instant(String s) {
		for(int i = 0; i < s.length(); i++) {
			if( !(s.charAt(i) >='0' || s.charAt(i) <= '9')) {
				return false;
			}
		}
		return true;
	}
	
	private void turnOff() {
		this.dispose();
	}
}
