package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Database.DatabaseAccess;

@SuppressWarnings("serial")
public class LoginGUI extends NewFrameWindow implements ActionListener {

	JButton signInButton, clearButton, signUpButton, callBankButton;
	JFormattedTextField cardNumberTextField;
	JPasswordField pinPasswordField;

	public LoginGUI() {
		setLoginIconAndTitle();
		setCardNumberField();
		setPasswordField();
		setLoginButtons();
		setVisible(true);
	}

	private void setLoginIconAndTitle() {
		ImageIcon bankLogoIcon = new ImageIcon(ClassLoader.getSystemResource("res/BankLogo.png"));
		Image bankLogoImage = bankLogoIcon.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
		bankLogoIcon = new ImageIcon(bankLogoImage);
		JLabel logoLabel = new JLabel(bankLogoIcon);
		logoLabel.setBounds(35, 65, 250, 250);
		add(logoLabel);

		JLabel titleLabel = new JLabel("Welcome to MyBank");
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 100));
		titleLabel.setForeground(new Color(248, 165, 29));
		titleLabel.setBounds(340, -30, 1100, 450);
		add(titleLabel);
	}

	private void setCardNumberField() {
		JLabel cardNumber = new JLabel("Card number: ");
		cardNumber.setFont(new Font("Arial", Font.BOLD, 40));
		cardNumber.setBounds(400, 350, 400, 40);
		add(cardNumber);

		MaskFormatter formatterCardNumber = new MaskFormatter();
		try {
			formatterCardNumber.setMask("####-####-####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cardNumberTextField = new JFormattedTextField(formatterCardNumber);
		formatterCardNumber.setPlaceholderCharacter('#');
		cardNumberTextField.setBounds(680, 350, 410, 40);
		cardNumberTextField.setFormatterFactory(new DefaultFormatterFactory(formatterCardNumber));
		cardNumberTextField.setFont(new Font("Arial", Font.BOLD, 40));
		add(cardNumberTextField);
	}

	private void setPasswordField() {
		JLabel pinLabel = new JLabel("Password: ");
		pinLabel.setFont(new Font("Arial", Font.BOLD, 40));
		pinLabel.setBounds(400, 450, 400, 40);
		add(pinLabel);

		pinPasswordField = new JPasswordField();
		pinPasswordField.setBounds(680, 450, 410, 40);
		pinPasswordField.setFont(new Font("Arial", Font.BOLD, 40));
		add(pinPasswordField);
	}

	private void setLoginButtons() {
		signInButton = new JButton("SIGN IN");
		signInButton.setFont(new Font("Arial", Font.BOLD, 40));
		signInButton.setBounds(400, 550, 350, 75);
		signInButton.setBackground(new Color(248, 165, 29));
		signInButton.setForeground(Color.white);
		signInButton.addActionListener(this);
		add(signInButton);

		clearButton = new JButton("CLEAR");
		clearButton.setFont(new Font("Arial", Font.BOLD, 40));
		clearButton.setBounds(755, 550, 350, 75);
		clearButton.setBackground(new Color(248, 165, 29));
		clearButton.setForeground(Color.white);
		clearButton.addActionListener(this);
		add(clearButton);

		signUpButton = new JButton("SIGN UP");
		signUpButton.setFont(new Font("Arial", Font.BOLD, 40));
		signUpButton.setBounds(400, 650, 705, 75);
		signUpButton.setBackground(new Color(248, 165, 29));
		signUpButton.setForeground(Color.white);
		signUpButton.addActionListener(this);
		add(signUpButton);

		callBankButton = new JButton("Call-Bank \u260E");
		callBankButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 40));
		callBankButton.setBounds(1245, 750, 300, 75);
		callBankButton.setBackground(new Color(248, 165, 29));
		callBankButton.setForeground(Color.white);
		callBankButton.addActionListener(this);
		add(callBankButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearButton) {
			cardNumberTextField.setText("");
			pinPasswordField.setText("");
		} else if (e.getSource() == signInButton) {
			String cardNumber = cardNumberTextField.getText().replace("-", "");
			setVisible(false);
			if (DatabaseAccess.signIn(cardNumber, new String(pinPasswordField.getPassword()))) {
				setVisible(false);
				new AtmGUI(cardNumber);
			}
		} else if (e.getSource() == signUpButton) {
			setVisible(false);
			new SignUpGUI();
		} else if (e.getSource() == callBankButton) {
			setVisible(false);
			new CallBankGUI();
		}
	}

}
