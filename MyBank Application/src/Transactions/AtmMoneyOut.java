package Transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import GUI.AtmGUI;
import GUI.LoginGUI;
import GUI.NewFrameWindow;

@SuppressWarnings("serial")
public class AtmMoneyOut extends NewFrameWindow implements ActionListener {

	String cardNumber;
	JButton exitButton, continueButton;
	
	AtmMoneyOut(String cardNr) {
		cardNumber = cardNr;
		setAtmTitle();
		setButtons();
		setBackgroundImage(1);
		setVisible(true);
	}
	
	private void setAtmTitle() {
		JLabel completAtm = new JLabel("Transaction complete.");
		completAtm.setBounds(450, 340, 400, 40);
		completAtm.setFont(new Font("System", Font.BOLD, 30));
		completAtm.setForeground(Color.white);
		add(completAtm);
		
		JLabel continueAtm = new JLabel("Do you want to execute another transaction?");
		continueAtm.setBounds(435, 455, 600, 40);
		continueAtm.setFont(new Font("System", Font.BOLD, 16));
		continueAtm.setForeground(Color.white);
		add(continueAtm);
	}
	
	private void setButtons() {
		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("System", Font.BOLD, 20));
		exitButton.setBounds(610, 500, 165, 36);		
		exitButton.addActionListener(this);
		add(exitButton);
		
		continueButton = new JButton("Continue");
		continueButton.setFont(new Font("System", Font.BOLD, 20));
		continueButton.setBounds(440, 500, 165, 36);
		continueButton.addActionListener(this);
		add(continueButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			setVisible(false);
			new LoginGUI();
		} else if (e.getSource() == continueButton) {
			setVisible(false);
			new AtmGUI(cardNumber);
		}
		
	}

}
