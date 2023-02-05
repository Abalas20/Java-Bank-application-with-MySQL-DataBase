package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;

import Transactions.AtmBalance;
import Transactions.AtmFastWithdraw;
import Transactions.AtmGetMoneyAmount;
import Transactions.AtmPinChange;
import Transactions.AtmTransaction;

@SuppressWarnings("serial")
public class AtmGUI extends NewFrameWindow implements ActionListener {
	JButton depositButton, withdrawButton, passwordChangeButton, balanceButton, transferButton;
	String cardNumber;

	public AtmGUI(String cardNr) {
		cardNumber = cardNr;
		setButtons();
		setAtmTitle();
		setBackgroundImage(0);
		setVisible(true);
	}
	
	private void setAtmTitle() {
		JLabel textAtm = new JLabel("Please select your Transaction");
		textAtm.setBounds(442, 335, 400, 40);
		textAtm.setFont(new Font("System", Font.BOLD, 22));
		textAtm.setForeground(Color.white);
		add(textAtm);
	}
	
	private void setButtons() {
		depositButton = new JButton("Deposit");
		depositButton.setFont(new Font("System", Font.BOLD, 20));
		depositButton.setBounds(420, 425, 185, 30);
		depositButton.addActionListener(this);
		add(depositButton);
		
		withdrawButton = new JButton("Withdraw");
		withdrawButton.setFont(new Font("System", Font.BOLD, 20));
		withdrawButton.setBounds(420, 460, 185, 30);
		withdrawButton.addActionListener(this);
		add(withdrawButton);
		
		passwordChangeButton = new JButton("Pin change");
		passwordChangeButton.setFont(new Font("System", Font.BOLD, 20));
		passwordChangeButton.setBounds(420, 495, 185, 30);
		passwordChangeButton.addActionListener(this);
		add(passwordChangeButton);
		
		balanceButton = new JButton("Balance");
		balanceButton.setFont(new Font("System", Font.BOLD, 20));
		balanceButton.setBounds(610, 425, 185, 30);
		balanceButton.addActionListener(this);
		add(balanceButton);
		
		transferButton = new JButton("Transfer");
		transferButton.setFont(new Font("System", Font.BOLD, 20));
		transferButton.setBounds(610, 460, 185, 30);
		transferButton.addActionListener(this);
		add(transferButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == withdrawButton) {
			setVisible(false);
			new AtmFastWithdraw(cardNumber);
		} else if (e.getSource() == depositButton) {
			setVisible(false);
			new AtmGetMoneyAmount(cardNumber, true);
		} else if (e.getSource() == passwordChangeButton) {
			setVisible(false);
			new AtmPinChange(cardNumber);
		} else if (e.getSource() == balanceButton) {
			setVisible(false);
			try {
				new AtmBalance(cardNumber);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == transferButton) {
			setVisible(false);
			new AtmTransaction(cardNumber);
		}
	}
}
