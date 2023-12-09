package boundaries;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controllers.RegisterEquipmentController;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegisterEquipmentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField inputEqBrand;
	private JTextField inputEqName;
	private JTextField inputEqPrice;
	private RegisterEquipmentController registerEquipmentController ;
	private String userID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterEquipmentController controller = new RegisterEquipmentController();
	                RegisterEquipmentFrame frame = new RegisterEquipmentFrame(controller, "MEM00001");
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
	public RegisterEquipmentFrame(RegisterEquipmentController registerEquipmentController, String userID) {
		this.registerEquipmentController = registerEquipmentController;
		this.userID = userID;
		
		
		setTitle("장비 등록");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 373, 379);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane = new JPanel(new GridLayout(5, 2, 10, 10));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("종류");
		lblNewLabel_1.setBounds(52, 46, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("브랜드");
		lblNewLabel_2.setBounds(52, 94, 50, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("제품명");
		lblNewLabel_3.setBounds(52, 146, 50, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("가격");
		lblNewLabel_4.setBounds(52, 199, 50, 15);
		contentPane.add(lblNewLabel_4);
		
		JComboBox selectEqType = new JComboBox();
		selectEqType.setModel(new DefaultComboBoxModel(new String[] {"운동화", "장갑", "헤드폰"}));
		selectEqType.setBounds(131, 42, 145, 23);
		contentPane.add(selectEqType);
		
		inputEqBrand = new JTextField();
		inputEqBrand.setBounds(131, 91, 145, 21);
		contentPane.add(inputEqBrand);
		inputEqBrand.setColumns(10);
		
		inputEqName = new JTextField();
		inputEqName.setBounds(131, 143, 145, 21);
		contentPane.add(inputEqName);
		inputEqName.setColumns(10);
		
		inputEqPrice = new JTextField();
		inputEqPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
		        if (!Character.isDigit(c)) {
		            e.consume(); // 입력된 값이 숫자가 아니면 입력을 무시
		        }
			}
		});
		inputEqPrice.setBounds(131, 196, 145, 21);
		contentPane.add(inputEqPrice);
		inputEqPrice.setColumns(10);
		
		
	    
		JButton EqRegisterButton = new JButton("등록하기");
		EqRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String EqType = selectEqType.getSelectedItem().toString();
		        String EqBrand = inputEqBrand.getText();
		        String EqName = inputEqName.getText();
		        String eqPriceText = inputEqPrice.getText();

		        // 값을 입력하지 않고 버튼을 클릭할 경우 등록 실패 메시지 출력
		        if (userID == null || EqType == null || EqBrand == null || EqName == null || eqPriceText == null || eqPriceText.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "등록에 실패하셨습니다.");
		            return;
		        }

		        int EqPrice = Integer.parseInt(eqPriceText);

		        registerEquipmentController.registerEquipment(userID, EqType, EqBrand, EqName, EqPrice);
		    }
		});
		EqRegisterButton.setBounds(62, 279, 93, 23);
		contentPane.add(EqRegisterButton);
		
		JButton EqCancelButton = new JButton("취소");
		EqCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			setVisible(false);
			}
		});
		EqCancelButton.setBounds(196, 279, 93, 23);
		contentPane.add(EqCancelButton);
		
		setContentPane(contentPane);
	}
}
