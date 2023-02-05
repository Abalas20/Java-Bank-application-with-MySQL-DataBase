package Transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import Database.DatabaseConnection;
import GUI.AtmGUI;
import GUI.LoginGUI;
import GUI.NewFrameWindow;

@SuppressWarnings("serial")
public class AtmBalance extends NewFrameWindow implements ActionListener{
	String cardNumber;
	JPasswordField pinPasswordField;
	JButton confirmButton, cancelButton;
	
	public AtmBalance(String cardNr) throws SQLException {
		cardNumber = cardNr;
		setAtmTitle();
		setButtons();
		setBalanceField();
		setBackgroundImage(0);
		setVisible(true);
	}
	
	private void setAtmTitle() {
		JLabel textAtm = new JLabel("Your balance is:");
		textAtm.setBounds(452, 350, 400, 40);
		textAtm.setFont(new Font("System", Font.BOLD, 22));
		textAtm.setForeground(Color.white);
		add(textAtm);

		JLabel continueAtm = new JLabel("Do you want to execute another transaction?");
		continueAtm.setBounds(435, 435, 600, 40);
		continueAtm.setFont(new Font("System", Font.BOLD, 16));
		continueAtm.setForeground(Color.white);
		add(continueAtm);
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
	
	private void setBalanceField() throws SQLException {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		StringBuilder query = new StringBuilder("select card_balance from card where card_id ='" + cardNumber + "'");
		ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
		String balance = "";
		while (resultSet.next()) {
			balance = resultSet.getString("card_balance");
		}
		JLabel balanceAtm = new JLabel(balance + " $");
		balanceAtm.setBounds(635, 350, 600, 40);
		balanceAtm.setFont(new Font("System", Font.BOLD, 22));
		balanceAtm.setForeground(Color.white);
		add(balanceAtm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirmButton) {
			setVisible(false);
			new AtmGUI(cardNumber);
		} else if (e.getSource() == cancelButton) {
			setVisible(false);
			new LoginGUI();
		}
		
	}
}
