package GUI;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import Database.DatabaseConnection;
import Database.PersonDetails;

@SuppressWarnings("serial")
public class SignUpGUI extends NewFrameWindow implements ActionListener{
	private static final long maxLengthCardNumber = 10000000000000000L, cardNumberFormat = 5040936000000000L;
	private static final String[] alphabet = { " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	Random random = new Random();
	long cardNumber, cardPassword;
	JTextField fullNameTextField, emailTextField, pinTextField, addressTextField, cityTextField, categoryTextField,
			incomeTextField;
	Choice fathersInititalChoice;
	JDateChooser dateChooser;
	JRadioButton maleGender, femaleGender, marriedButton, singleButton;
	String[] occupationVariables = { "Salaried", "Self-employed", "Bussnisess", "Student", "Retired", "Other" };
	JComboBox<String> occupationComboBox = new JComboBox<String>(occupationVariables);
	String[] categoryVariables = { "Savings account", "Current account", "Credit account", "Pension account",
			"Business account" };
	JComboBox<String> categoryComboBox = new JComboBox<String>(categoryVariables);
	String[] incomeVariables = { " < 25,000 lei per year", "25,000 - 50,000 lei per year", " > 50,000 lei per year" };
	JComboBox<String> incomeComboBox = new JComboBox<String>(incomeVariables);
	String[] educationVariables = { "Primary education", "Secondary education", "Post-Secondary education",
			"Undergraduate education", "Graduate education" };
	JComboBox<String> educationComboBox = new JComboBox<String>(educationVariables);
	JCheckBox confirmInformations;
	JButton submitButton, cancelButton;
	
	DatabaseConnection databaseConnection = new DatabaseConnection();
	PersonDetails personDetails = new PersonDetails();

	
	public SignUpGUI() {
		try {
			generateCardNumber();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cardPassword = Math.abs((random.nextLong() % 9000L) + 50280L) % 10000;

		setFormularTitle();
		setPersonalDetailsField();
		setAccountDetailsField();

		setVisible(true);
	}
	
	private void setFormularTitle() {
		JLabel formularNumberLabel = new JLabel("Application formular No. " + Math.abs((random.nextLong() % 9000L) + 1000L));
		formularNumberLabel.setFont(new Font("Ariel", Font.BOLD, 75));
		formularNumberLabel.setBounds(200, 20, 1200, 75);
		add(formularNumberLabel);
	}
	
	private void generateCardNumber() throws SQLException {
		cardNumber = Math.abs(((random.nextLong() % 9000001L) + cardNumberFormat) % maxLengthCardNumber);
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM card WHERE card_id = '" + cardNumber + "'");
		ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
		boolean valueExists = false;
		while (resultSet.next()) {
			if (resultSet.getString("card_id").equals(String.valueOf(cardNumber))) {
				valueExists = true;
				break;
			}
		}
		if (!valueExists) {
			return;
		}
		generateCardNumber();
	}
	
	private void setPersonalDetailsField() {
		JLabel personDetailsLabel = new JLabel("Personal details");
		personDetailsLabel.setFont(new Font("Ariel", Font.BOLD, 50));
		personDetailsLabel.setBounds(160, 130, 500, 60);
		add(personDetailsLabel);

		JLabel FullNameLabel = new JLabel("Full name:");
		FullNameLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		FullNameLabel.setBounds(60, 250, 310, 40);
		add(FullNameLabel);

		fullNameTextField = new JTextField();
		fullNameTextField.setFont(new Font("Ariel", Font.BOLD, 18));
		fullNameTextField.setBounds(335, 253, 310, 40);
		add(fullNameTextField);

		JLabel fathersInitialLabel = new JLabel("Father's initial:");
		fathersInitialLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		fathersInitialLabel.setBounds(60, 310, 260, 40);
		add(fathersInitialLabel);

		fathersInititalChoice = new Choice();
		fathersInititalChoice.setBounds(335, 315, 310, 40);
		for (String letter : alphabet) {
			fathersInititalChoice.add(letter);
		}
		fathersInititalChoice.setFont(new Font("Ariel", Font.BOLD, 22));
		add(fathersInititalChoice);

		JLabel dobLabel = new JLabel("Date of Birth:");
		dobLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		dobLabel.setBounds(60, 367, 415, 40);
		add(dobLabel);

		dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Ariel", Font.BOLD, 18));
		dateChooser.setBounds(335, 367, 310, 40);
		add(dateChooser);

		JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		genderLabel.setBounds(60, 424, 415, 40);
		add(genderLabel);

		maleGender = new JRadioButton("Male");
		maleGender.setFont(new Font("Ariel", Font.BOLD, 30));
		maleGender.setBounds(335, 424, 115, 40);
		maleGender.setBackground(Color.white);
		add(maleGender);

		femaleGender = new JRadioButton("Female");
		femaleGender.setFont(new Font("Ariel", Font.BOLD, 30));
		femaleGender.setBounds(460, 424, 150, 40);
		femaleGender.setBackground(Color.white);
		add(femaleGender);

		ButtonGroup genderButtons = new ButtonGroup();
		genderButtons.add(femaleGender);
		genderButtons.add(maleGender);

		JLabel emailLabel = new JLabel("Email address:");
		emailLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		emailLabel.setBounds(60, 481, 310, 40);
		add(emailLabel);

		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Ariel", Font.BOLD, 18));
		emailTextField.setBounds(335, 481, 310, 40);
		add(emailTextField);

		JLabel martialStatusLabel = new JLabel("Martial status:");
		martialStatusLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		martialStatusLabel.setBounds(60, 538, 310, 40);
		add(martialStatusLabel);

		marriedButton = new JRadioButton("Married");
		marriedButton.setFont(new Font("Ariel", Font.BOLD, 30));
		marriedButton.setBounds(460, 538, 200, 40);
		marriedButton.setBackground(Color.white);
		add(marriedButton);

		singleButton = new JRadioButton("Single");
		singleButton.setFont(new Font("Ariel", Font.BOLD, 30));
		singleButton.setBounds(335, 538, 120, 40);
		singleButton.setBackground(Color.white);
		add(singleButton);

		ButtonGroup martialStatusButtons = new ButtonGroup();
		martialStatusButtons.add(marriedButton);
		martialStatusButtons.add(singleButton);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		addressLabel.setBounds(60, 595, 200, 40);
		add(addressLabel);

		addressTextField = new JTextField();
		addressTextField.setFont(new Font("Ariel", Font.BOLD, 18));
		addressTextField.setBounds(335, 595, 310, 40);
		add(addressTextField);

		JLabel cityLabel = new JLabel("City:");
		cityLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		cityLabel.setBounds(60, 652, 200, 40);
		add(cityLabel);

		cityTextField = new JTextField();
		cityTextField.setFont(new Font("Ariel", Font.BOLD, 18));
		cityTextField.setBounds(335, 652, 310, 40);
		add(cityTextField);

		JLabel pinCodeLabel = new JLabel("Pin Code:");
		pinCodeLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		pinCodeLabel.setBounds(60, 709, 200, 40);
		add(pinCodeLabel);

		pinTextField = new JTextField();
		pinTextField.setFont(new Font("Ariel", Font.BOLD, 18));
		pinTextField.setBounds(335, 709, 310, 40);
		add(pinTextField);

	}

	private void setAccountDetailsField() {
		JLabel additionalInfLabel = new JLabel("Account information");
		additionalInfLabel.setFont(new Font("Ariel", Font.BOLD, 50));
		additionalInfLabel.setBounds(860, 130, 600, 60);
		add(additionalInfLabel);

		JLabel categoryLabel = new JLabel("Account type:");
		categoryLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		categoryLabel.setBounds(680, 250, 300, 45);
		add(categoryLabel);

		categoryComboBox.setFont(new Font("Ariel", Font.BOLD, 18));
		categoryComboBox.setBounds(930, 250, 500, 40);
		categoryComboBox.setBackground(Color.white);
		add(categoryComboBox);

		JLabel incomeLabel = new JLabel("Income:");
		incomeLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		incomeLabel.setBounds(680, 310, 300, 40);
		add(incomeLabel);

		incomeComboBox.setFont(new Font("Ariel", Font.BOLD, 18));
		incomeComboBox.setBounds(930, 310, 500, 40);
		incomeComboBox.setBackground(Color.white);
		add(incomeComboBox);

		JLabel educationLabel = new JLabel("Education:");
		educationLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		educationLabel.setBounds(680, 367, 300, 40);
		add(educationLabel);

		educationComboBox.setFont(new Font("Ariel", Font.BOLD, 18));
		educationComboBox.setBounds(930, 367, 500, 40);
		educationComboBox.setBackground(Color.white);
		add(educationComboBox);

		JLabel occupationLabel = new JLabel("Occupation:");
		occupationLabel.setFont(new Font("Raleway", Font.BOLD, 36));
		occupationLabel.setBounds(680, 424, 300, 40);
		add(occupationLabel);

		occupationComboBox.setFont(new Font("Ariel", Font.BOLD, 18));
		occupationComboBox.setBounds(930, 424, 500, 40);
		occupationComboBox.setBackground(Color.white);
		add(occupationComboBox);

		JLabel cardNrLabel = new JLabel("Card Number:");
		cardNrLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		cardNrLabel.setBounds(680, 481, 300, 40);
		add(cardNrLabel);

		JLabel cardNumberX = new JLabel("XXXX-XXXX-XXXX-" + cardNumber % 10000);
		cardNumberX.setFont(new Font("Ariel", Font.BOLD, 36));
		cardNumberX.setBounds(950, 481, 500, 40);
		add(cardNumberX);

		JLabel cardNumberInfo = new JLabel("Your 16 Digit Card Number");
		cardNumberInfo.setFont(new Font("Ariel", Font.BOLD, 14));
		cardNumberInfo.setBounds(700, 505, 200, 40);
		add(cardNumberInfo);

		JLabel PinLabel = new JLabel("Password:");
		PinLabel.setFont(new Font("Ariel", Font.BOLD, 36));
		PinLabel.setBounds(680, 538, 300, 40);
		add(PinLabel);

		JLabel pinInfo = new JLabel("Your 4 Digit Password");
		pinInfo.setFont(new Font("Ariel", Font.BOLD, 14));
		pinInfo.setBounds(700, 562, 200, 40);
		add(pinInfo);

		JLabel PinForm = new JLabel("XXXX");
		PinForm.setFont(new Font("Ariel", Font.BOLD, 36));
		PinForm.setBounds(950, 538, 400, 40);
		add(PinForm);

		confirmInformations = new JCheckBox(
				"I confirm that the information provided in this form is accurate and complete to the best of my knowledge");
		confirmInformations.setFont(new Font("Ariel", Font.BOLD, 14));
		confirmInformations.setBounds(680, 595, 760, 40);
		add(confirmInformations);

		submitButton = new JButton("SUBMIT");
		submitButton.setBounds(680, 652, 300, 100);
		submitButton.setFont(new Font("Ariel", Font.BOLD, 40));
		submitButton.setBackground(new Color(248, 165, 29));
		submitButton.setForeground(Color.white);
		submitButton.addActionListener(this);
		add(submitButton);

		cancelButton = new JButton("CANCEL");
		cancelButton.setBounds(1000, 652, 300, 100);
		cancelButton.setFont(new Font("Ariel", Font.BOLD, 40));
		cancelButton.setBackground(new Color(248, 165, 29));
		cancelButton.setForeground(Color.white);
		cancelButton.addActionListener(this);
		add(cancelButton);
	}
	
	private void getInput() {
		personDetails.setName(fullNameTextField.getText());
		personDetails.setFathersInitial(alphabet[fathersInititalChoice.getSelectedIndex()]);
		personDetails.setDob(((JTextField) dateChooser.getDateEditor().getUiComponent()).getText());
		
		if (maleGender.isSelected()) {
			personDetails.setGender("Male");
		} else if (femaleGender.isSelected()) {
			personDetails.setGender("Female");
		}
		personDetails.setEmail(emailTextField.getText());
		
		if (marriedButton.isSelected()) {
			personDetails.setMartial("Married");
		} else if (singleButton.isSelected()) {
			personDetails.setMartial("Single");
		}
		personDetails.setAddress(addressTextField.getText());
		personDetails.setCity(cityTextField.getText());
		personDetails.setPincode(pinTextField.getText());
		personDetails.setAccountType(categoryVariables[categoryComboBox.getSelectedIndex()]);
		personDetails.setIncome(incomeVariables[incomeComboBox.getSelectedIndex()]);
		personDetails.setEducation(educationVariables[educationComboBox.getSelectedIndex()]);
		personDetails.setOccupation(occupationVariables[occupationComboBox.getSelectedIndex()]);
	}
	
	private void createCardList() {
		StringBuilder queryCount = new StringBuilder(), query = new StringBuilder();
		queryCount.append("select count(account_id) from bank_account");
		int count = 1;
		try {
			ResultSet resultSet = databaseConnection.statement.executeQuery(queryCount.toString());
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			query.append("insert into card_list (acc_id, card_id) value('" + count + "', '" + cardNumber + "')");
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createCard() {
		StringBuilder query = new StringBuilder();
		query.append("insert into card (card_id, card_password, card_balance) VALUE ('" + cardNumber + "', '"
				+ cardPassword + "','0')");
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void createCustomer() {
		StringBuilder query = new StringBuilder();
		query.append(
				"insert into customer (full_name, fathers_initial, dob, gender, email, martial_status, address, city, pincode, income, education, occupation) value ('"
						+ personDetails.getName() + "', '" + personDetails.getFathersInitial() + "', '" + personDetails.getDob() + "', '" + personDetails.getGender() + "', '" + personDetails.getEmail() + "', '"
						+ personDetails.getMartial() + "', '" + personDetails.getAddress() + "', '" + personDetails.getCity() + "', '" + personDetails.getPincode() + "', '" + personDetails.getIncome() + "', '"
						+ personDetails.getEducation() + "', '" + personDetails.getOccupation() + "')");
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void createAccount() {
		StringBuilder query = new StringBuilder();
		query.append("insert into bank_account (account_type) value ('" + personDetails.getAccountType() + "')");
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (cancelButton == e.getSource()) {
			setVisible(false);
			new LoginGUI();
		} else if (e.getSource() == submitButton) {
			getInput();
			
			if (personDetails.checkInput()) {
				createAccount();
				createCustomer();
				createCard();
				createCardList();
				
				JOptionPane.showMessageDialog(null, "Card number: " + cardNumber + "\nPassword: " + cardPassword);
				
				setVisible(false);
				new LoginGUI();
			}
		}
	}
}
