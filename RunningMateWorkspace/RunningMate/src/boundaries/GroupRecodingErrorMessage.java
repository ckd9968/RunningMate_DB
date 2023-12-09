package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class GroupRecodingErrorMessage extends JFrame {

	private JPanel contentPane;
	private JTextArea idArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vector<String> v = new Vector<String>();
					v.add("id1");
					v.add("id2");
					GroupRecodingErrorMessage frame = new GroupRecodingErrorMessage(v);
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
	public GroupRecodingErrorMessage(Vector<String> ids) {
		setTitle("그룹 등록 실패");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 252, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("기록에 실패하였습니다.");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(24, 25, 233, 25);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 73, 168, 186);
		contentPane.add(scrollPane);
		
		idArea = new JTextArea();
		idArea.setEditable(false);
		Iterator<String> it = ids.iterator();
		while(it.hasNext()) {
			idArea.setText(idArea.getText() + it.next() + "\n");
		}
		scrollPane.setViewportView(idArea);
		
		JLabel lblNewLabel_1 = new JLabel("유효하지 않은 아이디 :");
		lblNewLabel_1.setBounds(24, 50, 156, 25);
		contentPane.add(lblNewLabel_1);
		
		JButton yesButton = new JButton("확인");
		yesButton.setFont(new Font("굴림", Font.BOLD, 18));
		yesButton.setBounds(55, 273, 130, 36);
		contentPane.add(yesButton);
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnOff();
			}
		});
		
		setVisible(true);
	}
	
	private void turnOff() {
		this.dispose();
	}
}
