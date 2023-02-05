package Transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import Database.DatabaseConnection;
import GUI.AtmGUI;
import GUI.NewFrameWindow;

@SuppressWarnings("serial")
public class AtmPinChange extends NewFrameWindow implements ActionListener{
	String cardNumber;
	JPasswordField pinPasswordField;
	JButton confirmButton, cancelButton;
	
	public AtmPinChange(String cardNr) {
		cardNumber = cardNr;
		setPasswordField();
		setAtmTitle();
		setButtons();
		setBackgroundImage(0);
		setVisible(true);
	}
	
	private void setAtmTitle() {
		JLabel textAtm = new JLabel("Please enter a new password");
		textAtm.setBounds(442, 335, 400, 40);
		textAtm.setFont(new Font("System", Font.BOLD, 22));
		textAtm.setForeground(Color.white);

		add(textAtm);
	}
	
	private void setPasswordField() {
		pinPasswordField = new JPasswordField();
		pinPasswordField.setFont(new Font("System", Font.BOLD, 20));
		pinPasswordField.setBounds(440, 450, 335, 31);
		pinPasswordField.setBackground(Color.white);
		pinPasswordField.setForeground(Color.black);
		add(pinPasswordField);
	}
	
	private void setButtons() {
		confirmButton = new JButton("Confirm");
		confirmButton.setFont(new Font("System", Font.BOLD, 20));
		confirmButton.setBounds(440, 500, 165, 30);
		confirmButton.addActionListener(this);
		add(confirmButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("System", Font.BOLD, 20));
		cancelButton.setBounds(610, 500, 165, 30);
		cancelButton.addActionListener(this);
		add(cancelButton);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirmButton) {
			try {
				changePassword();
				JOptionPane.showMessageDialog(null, "Your password has been changed successfully.\nPlease use your new password the next time you log in.");
				setVisible(false);
				new AtmGUI(cardNumber);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == cancelButton) {
			setVisible(false);
			new AtmGUI(cardNumber);
		}
	}
	
	private void changePassword() throws SQLException {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		String newPassword = new String(pinPasswordField.getPassword());
		StringBuilder query = new StringBuilder("update card set card_password = '" + newPassword +"' where card_id = '" + cardNumber + "'");
		databaseConnection.statement.executeUpdate(query.toString());
	}
}
