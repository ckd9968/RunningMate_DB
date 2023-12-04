package boundaries;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

public class SearchCrewFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchCrewFrame frame = new SearchCrewFrame();
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
	public SearchCrewFrame() {
		setAlwaysOnTop(true);
		
		setVisible(true);
		
		setTitle("크루 검색하기");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 398, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("크루명");
		lblNewLabel.setBounds(26, 29, 50, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(86, 26, 150, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("검색하기");
		btnNewButton.setBounds(252, 25, 93, 23);
		contentPane.add(btnNewButton);
		
		table = new JTable();
		table.setBounds(30, 69, 315, 115);
		contentPane.add(table);
		
		table_1 = new JTable();
		table_1.setBounds(30, 226, 315, 190);
		contentPane.add(table_1);
		
		JButton btnNewButton_1 = new JButton("가입하기");
		btnNewButton_1.setBounds(65, 448, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("취소하기");
		btnNewButton_2.setBounds(212, 448, 93, 23);
		contentPane.add(btnNewButton_2);
	}

}
