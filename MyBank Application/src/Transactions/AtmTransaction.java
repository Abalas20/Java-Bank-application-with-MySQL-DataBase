package Transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import AtmInput.AtmTextField;
import Database.DatabaseAccess;
import GUI.AtmGUI;
import GUI.NewFrameWindow;

@SuppressWarnings("serial")
public class AtmTransaction extends NewFrameWindow implements ActionListener {

	AtmTextField idTextField;
	JTextField nameTextField, atmAmount;
	JButton confirmButton, cancelButton;
	String cardNumber;
	
	public AtmTransaction(String cardNr) {
		cardNumber = cardNr;
		setAtmTitle();
		setFields();
		setButtons();
		setBackgroundImage(0);
		setVisible(true);
	}
	
	private void setAtmTitle() {
		JLabel textAtm = new JLabel("Complete the following");
		textAtm.setBounds(480, 335, 400, 40);
		textAtm.setFont(new Font("System", Font.BOLD, 22));
		textAtm.setForeground(Color.white);
		add(textAtm);
	}
	
	private void setFields() {
		
		JLabel idLabel = new JLabel("Enter iban");
		idLabel.setFont(new Font("System", Font.BOLD, 22));
		idLabel.setForeground(Color.white);
		idLabel.setBounds(420, 425, 150, 31);
		add(idLabel);
		
		idTextField = new AtmTextField("", 16);
		idTextField.setFont(new Font("System", Font.BOLD, 22));
		idTextField.setBounds(565, 425, 220, 31);
		add(idTextField);
		
		JLabel nameLabel = new JLabel("Enter name");
		nameLabel.setFont(new Font("System", Font.BOLD, 22));
		nameLabel.setForeground(Color.white);
		nameLabel.setBounds(420, 460, 150, 31);
		add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("System", Font.BOLD, 22));
		nameTextField.setBounds(565, 460, 220, 31);
		add(nameTextField);
		
		JLabel moneyLabel = new JLabel("Enter money");
		moneyLabel.setFont(new Font("System", Font.BOLD, 22));
		moneyLabel.setForeground(Color.white);
		moneyLabel.setBounds(420, 495, 150, 31);
		add(moneyLabel);
		
		atmAmount = new AtmTextField(".00 lei", 20);
		atmAmount.setFont(new Font("System", Font.BOLD, 20));
		atmAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		atmAmount.setBounds(565, 495, 220, 31);
		atmAmount.setBackground(Color.white);
		atmAmount.setForeground(Color.black);
		atmAmount.setCaretPosition(0);
		add(atmAmount);
	}
	
	private void setButtons() {
		confirmButton = new JButton("Confirm");
		confirmButton.setFont(new Font("System", Font.BOLD, 20));
		confirmButton.setBounds(440, 530, 165, 30);
		confirmButton.addActionListener(this);
		add(confirmButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("System", Font.BOLD, 20));
		cancelButton.setBounds(610, 530, 165, 30);
		cancelButton.addActionListener(this);
		add(cancelButton);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			setVisible(false);
			new AtmGUI(cardNumber);
		} else if (e.getSource() == confirmButton) {
			String destinationCard = idTextField.getText();
			String amount = atmAmount.getText().replace(".00 lei", "");
			if (destinationCard.length() < 16) {
				JOptionPane.showMessageDialog(null, "Not a valid iban ");
			} else if (nameTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a name ");
			} else if (!amount.isEmpty()) {
				DatabaseAccess.sendMoney(cardNumber, destinationCard, amount);
				JOptionPane.showMessageDialog(null, "The transfer with " + nameTextField.getText() + " was successfully completed!");
				
				setVisible(false);
				new AtmGUI(cardNumber);
			}
		}
		
	}
}
