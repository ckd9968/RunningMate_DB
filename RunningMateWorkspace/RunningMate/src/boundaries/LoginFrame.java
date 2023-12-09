package boundaries;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.LoginController;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userIDField;
	private LoginController con;


	/**
	 * Create the frame.
	 */
	public LoginFrame(LoginController c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.con = c;
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("회원ID :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(28, 41, 83, 37);
		contentPane.add(lblNewLabel);
		
		userIDField = new JTextField();
		userIDField.setBounds(123, 41, 182, 37);
		contentPane.add(userIDField);
		userIDField.setColumns(10);
		
		JButton openButton = new JButton("GO 런닝메이트");
		openButton.setFont(new Font("굴림", Font.BOLD, 15));
		openButton.setBounds(123, 121, 145, 37);
		contentPane.add(openButton);
		openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(con.verifyUserID(userIDField.getText())) {
					new MainFrame(userIDField.getText());
					turnOff();
				}
			}
		});
		
		setVisible(true);
	}
	
	private void turnOff() {
		this.dispose();
	}
}
