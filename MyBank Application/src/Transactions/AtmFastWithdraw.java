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

import Database.DatabaseConnection;
import GUI.AtmGUI;
import GUI.NewFrameWindow;

@SuppressWarnings("serial")
public class AtmFastWithdraw extends NewFrameWindow implements ActionListener {
	private JButton tenButton, twentyButton, fiftyButton, otherButton, oneHundredButton, twoHundredButton,
			fourHundredButton, fiveHundredButton;
	Long amount;
	String cardNumber;
	DatabaseConnection databaseConnection = new DatabaseConnection();

	public AtmFastWithdraw(String cardNR) {
		cardNumber = cardNR;
		setButtons();
		setAtmTitle();
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

	private void setButtons() {
		tenButton = new JButton("10");
		tenButton.setFont(new Font("System", Font.BOLD, 20));
		tenButton.setBounds(420, 425, 185, 30);
		tenButton.addActionListener(this);
		add(tenButton);

		twentyButton = new JButton("20");
		twentyButton.setFont(new Font("System", Font.BOLD, 20));
		twentyButton.setBounds(420, 460, 185, 30);
		twentyButton.addActionListener(this);
		add(twentyButton);

		fiftyButton = new JButton("50");
		fiftyButton.setFont(new Font("System", Font.BOLD, 20));
		fiftyButton.setBounds(420, 495, 185, 30);
		fiftyButton.addActionListener(this);
		add(fiftyButton);

		otherButton = new JButton("Other amount");
		otherButton.setFont(new Font("System", Font.BOLD, 20));
		otherButton.setBounds(420, 530, 185, 30);
		otherButton.addActionListener(this);
		add(otherButton);

		oneHundredButton = new JButton("100");
		oneHundredButton.setFont(new Font("System", Font.BOLD, 20));
		oneHundredButton.setBounds(610, 425, 185, 30);
		oneHundredButton.addActionListener(this);
		add(oneHundredButton);

		twoHundredButton = new JButton("200");
		twoHundredButton.setFont(new Font("System", Font.BOLD, 20));
		twoHundredButton.setBounds(610, 460, 185, 30);
		twoHundredButton.addActionListener(this);
		add(twoHundredButton);

		fourHundredButton = new JButton("400");
		fourHundredButton.setFont(new Font("System", Font.BOLD, 20));
		fourHundredButton.setBounds(610, 495, 185, 30);
		fourHundredButton.addActionListener(this);
		add(fourHundredButton);

		fiveHundredButton = new JButton("500");
		fiveHundredButton.setFont(new Font("System", Font.BOLD, 20));
		fiveHundredButton.setBounds(610, 530, 185, 30);
		fiveHundredButton.addActionListener(this);
		add(fiveHundredButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean getMoney = true;
		if (e.getSource() == tenButton) {
			amount = 10L;
		} else if (e.getSource() == twentyButton) {
			amount = 20L;
		} else if (e.getSource() == fiftyButton) {
			amount = 50L;
		} else if (e.getSource() == oneHundredButton) {
			amount = 100L;
		} else if (e.getSource() == twoHundredButton) {
			amount = 200L;
		} else if (e.getSource() == fourHundredButton) {
			amount = 400L;
		} else if (e.getSource() == fiveHundredButton) {
			amount = 500L;
		} else if (e.getSource() == otherButton) {
			getMoney = false;
			setVisible(false);
			new AtmGetMoneyAmount(cardNumber, false);
		}
		if (getMoney) {

			StringBuilder query = new StringBuilder();
			query.append("select card_balance from card where card_id ='" + cardNumber + "'");
			long balance = 0;
			try {
				ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
				while (resultSet.next()) {
					balance = Integer.parseInt(resultSet.getString("card_balance"));
				}
				if (amount <= balance) {
					balance = balance - amount;
					query.setLength(0);
					query.append(
							"update card set card_balance = '" + balance + "' where card_id = '" + cardNumber + "'");
					databaseConnection.statement.executeUpdate(query.toString());
					createTransaction();
					setVisible(false);
					new AtmMoneyOut(cardNumber);
				} else if (amount > balance) {
					JOptionPane.showMessageDialog(null,
							"Transaction denied\nYou have insufficient funds to complete this transaction");
					setVisible(false);
					new AtmGUI(cardNumber);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void createTransaction() {
		StringBuilder query = new StringBuilder(
				"insert into transactions (card_source_id, card_destination_id, amount, transaction_date, transaction_type) VALUE ('");
		query.append(cardNumber + "', 'BANK', '" + amount + "', NOW(), 'withdraw')");
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
