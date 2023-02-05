package Transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import javax.swing.SwingConstants;

import AtmInput.AtmTextField;
import Database.DatabaseConnection;
import GUI.AtmGUI;
import GUI.NewFrameWindow;

@SuppressWarnings("serial")
public class AtmGetMoneyAmount extends NewFrameWindow implements ActionListener {

	JTextField atmAmount;
	JButton confirmButton, cancelButton;
	String cardNumber;
	DatabaseConnection databaseConnection = new DatabaseConnection();
	long amount;
	boolean deposit_transaction;

	public AtmGetMoneyAmount(String cardNR, boolean deposit) {
		deposit_transaction = deposit;
		cardNumber = cardNR;
		setAtmTitle();
		setAtmAmounTextField();
		setButtons();
		setBackgroundImage(0);
		setVisible(true);
	}

	private void setAtmTitle() {
		JLabel textAtm = new JLabel("Select an amount to withdraw");
		textAtm.setBounds(442, 335, 400, 40);
		textAtm.setFont(new Font("System", Font.BOLD, 22));
		textAtm.setForeground(Color.white);

		add(textAtm);
	}

	private void setAtmAmounTextField() {
		atmAmount = new AtmTextField(".00 lei", 23);
		atmAmount.setFont(new Font("System", Font.BOLD, 20));
		atmAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		atmAmount.setBounds(440, 460, 335, 31);
		atmAmount.setBackground(Color.white);
		atmAmount.setForeground(Color.black);
		atmAmount.setCaretPosition(0);
		add(atmAmount);
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

			String amountTextField = atmAmount.getText();
			amountTextField = amountTextField.replace(".00 lei", "");
			amount = Long.parseLong(amountTextField);
			withdrawOrDeposit();
		} else if (e.getSource() == cancelButton) {
			setVisible(false);
			new AtmGUI(cardNumber);
		}
	}

	private void withdrawOrDeposit() {
		StringBuilder query = new StringBuilder();
		query.append("select card_balance from card where card_id ='" + cardNumber + "'");
		long balance = 0;
		try {
			ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
			while (resultSet.next()) {
				balance = Integer.parseInt(resultSet.getString("card_balance"));
			}
			if (amount <= balance && !deposit_transaction) {
				balance = balance - amount;
				query.setLength(0);
				query.append("update card set card_balance = '" + balance + "' where card_id = '" + cardNumber + "'");
				databaseConnection.statement.executeUpdate(query.toString());
				 createTransactionWithdraw();
				setVisible(false);
				new AtmMoneyOut(cardNumber);
			} else if (amount > balance && !deposit_transaction) {
				JOptionPane.showMessageDialog(null,
						"Transaction denied\nYou have insufficient funds to complete this transaction");
				setVisible(false);
				new AtmGUI(cardNumber);
			}
			else if (deposit_transaction) {
				balance = balance + amount;
				query.setLength(0);
				query.append("update card set card_balance = '" + balance + "' where card_id = '" + cardNumber + "'");
				databaseConnection.statement.executeUpdate(query.toString());
				createTransactionDeposit();
				setVisible(false);
				new AtmGUI(cardNumber);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void createTransactionWithdraw() {
		StringBuilder query = new StringBuilder("insert into transactions (card_source_id, card_destination_id, amount, transaction_date, transaction_type) VALUE ('");
		query.append(cardNumber + "', 'BANK', '" + amount + "', NOW(), 'withdraw')" );
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void createTransactionDeposit() {
		StringBuilder query = new StringBuilder("insert into transactions (card_source_id, card_destination_id, amount, transaction_date, transaction_type) VALUE ('");
		query.append(cardNumber + "', 'BANK', '" + amount + "', NOW(), 'deposit')" );
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
