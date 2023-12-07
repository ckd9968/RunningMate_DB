package boundaries;

import controllers.RecommendCourseController;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecommendCourseFrame extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable recommendCourseTable;
	private RecommendCourseController recommendCourseController;
	private String[] columnNames = {"주소", "분위기", "방문수"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecommendCourseFrame frame = new RecommendCourseFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RecommendCourseFrame() {
		recommendCourseController = new RecommendCourseController();
		
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            columnNames
	        );
		
		setAlwaysOnTop(true);
		
		setVisible(true);
		
		setTitle("추천 코스");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 609, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox addressBox = new JComboBox();
		addressBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "서울", "인천", "대전", "대구", "부산", "경주", "성남", "고양"}));
		addressBox.setBounds(133, 21, 73, 23);
		contentPane.add(addressBox);
		
		JLabel lblNewLabel_2 = new JLabel("분위기");
		lblNewLabel_2.setBounds(61, 58, 50, 15);
		contentPane.add(lblNewLabel_2);
		
		JComboBox atmosphereBox = new JComboBox();
		atmosphereBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "좋음", "보통", "나쁨"}));
		atmosphereBox.setBounds(133, 54, 73, 23);
		contentPane.add(atmosphereBox);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 139, 464, 149);
		contentPane.add(scrollPane);

		recommendCourseTable = new JTable();
		recommendCourseTable.setModel(model);
		scrollPane.setViewportView(recommendCourseTable);
		
		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		closeButton.setBounds(230, 304, 93, 23);
		contentPane.add(closeButton);
		
		JLabel lblNewLabel = new JLabel("지역");
		lblNewLabel.setBounds(61, 25, 50, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("방문수");
		lblNewLabel_1.setBounds(61, 96, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JTextField visitCountOver = new JTextField();
		visitCountOver.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int maxLength = 4;
				char c = e.getKeyChar();
				
		        if (!Character.isDigit(c)) {
		            e.consume(); // 입력된 값이 숫자가 아니면 입력을 무시
		        }else if (visitCountOver.getText().length() >= maxLength) {
		            e.consume(); // 최대 길이 초과 시 입력을 무시
		        }
			}
		});
		visitCountOver.setBounds(133, 93, 73, 21);
		contentPane.add(visitCountOver);
		visitCountOver.setColumns(10);
		
		
		JLabel lblNewLabel_3 = new JLabel("이상");
		lblNewLabel_3.setBounds(216, 96, 50, 15);
		contentPane.add(lblNewLabel_3);
		
		JTextField visitCountUnder = new JTextField();
		visitCountUnder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int maxLength = 4;
				char c = e.getKeyChar();
				
		        if (!Character.isDigit(c)) {
		            e.consume();
		        }else if (visitCountUnder.getText().length() >= maxLength) {
		            e.consume();
		        }
			}
		});
		visitCountUnder.setBounds(253, 93, 73, 21);
		contentPane.add(visitCountUnder);
		visitCountUnder.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("이하");
		lblNewLabel_4.setBounds(336, 96, 50, 15);
		contentPane.add(lblNewLabel_4);
		
		JButton searchButton = new JButton("검색하기");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedAddress = addressBox.getSelectedItem().toString();
				String selectedAtmosphere = atmosphereBox.getSelectedItem().toString();
			    // 방문수를 입력하지 않았을 경우 검색값을 0과 9999로 하기 위해 선언
				int visitOver = 0;
			    int visitUnder = 9999;
			    
			    String visitOverText = visitCountOver.getText();
			    String visitUnderText = visitCountUnder.getText();
			    
			    if (visitOverText != null && !visitOverText.isEmpty()) {
			        visitOver = Integer.parseInt(visitOverText);
			    }
			    
			    if (visitUnderText != null && !visitUnderText.isEmpty()) {
			        visitUnder = Integer.parseInt(visitUnderText);
			    }	
				
				recommendCourseController.searchCourse(selectedAddress, selectedAtmosphere, visitOver, visitUnder);
				
				ResultSet rs = recommendCourseController.searchCourse(selectedAddress, selectedAtmosphere, visitOver, visitUnder);
                DefaultTableModel model = (DefaultTableModel) recommendCourseTable.getModel();
                model.setRowCount(0);

                if (rs != null) {
                    try {
                        while (rs.next()) {
                            String addressOut = rs.getString("주소");
                            String atmosphereOut = rs.getString("분위기");
                            int visitCount = rs.getInt("방문수");

                            model.addRow(new Object[]{addressOut, atmosphereOut, visitCount});
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }else {
                	JOptionPane.showMessageDialog(null, "검색 결과가 없습니다");
                }
            }
        });
		searchButton.setBounds(396, 20, 129, 90);
		contentPane.add(searchButton);
		


	}
}