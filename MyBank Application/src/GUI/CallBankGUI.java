package GUI;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Database.DatabaseConnection;


@SuppressWarnings("serial")
public class CallBankGUI extends NewFrameWindow implements ActionListener {
	JPanel phonePanel;
	JButton sendButton;
	JTextArea msg1Operator, msg1Receiver, msg2Operator, msg2Receiver, msg3Operator, msg3Receiver, msg4Operator,
			msg5Operator, msg4Receiver;
	boolean msg1Boolean, msg2Boolean, msg3Boolean;
	String[] locations = new String[42];
	String[] employees = new String[10];
	Choice bankLocationChoice;
	int bank_id;
	DatabaseConnection databaseConnection = new DatabaseConnection();
	int ticketNumber;

	public CallBankGUI() {
		Random random = new Random();
		ticketNumber = Math.abs((random.nextInt() % 1000) + 1000);
		setFrame();
		setBackgroundImage(2);
		setButton();
		msg1Boolean = true;
		setWelcomeMessage();
		setVisible(true);
	}

	private void setFrame() {
		phonePanel = new JPanel();
		phonePanel.setBounds(583, 66, 405, 655);
		phonePanel.setBackground(new Color(199, 233, 235));
		phonePanel.setLayout(null);
		add(phonePanel);
	}

	private void setWelcomeMessage() {
		msg1Operator = new JTextArea("Welcome to MyBank. My name is " + getOperatorName()
				+ " and I am one of the customer service representatives at MyBank. How may I assist you today?");
		msg1Operator.setLineWrap(true);
		msg1Operator.setWrapStyleWord(true);
		msg1Operator.setBackground(Color.white);
		msg1Operator.setEditable(false);
		msg1Operator.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg1Operator.setBounds(5, 5, 280, 100);
		phonePanel.add(msg1Operator);

		msg1Receiver = new JTextArea("Hello, I would like to make an appointment to MyBank!");
		msg1Receiver.setLineWrap(true);
		msg1Receiver.setWrapStyleWord(true);
		msg1Receiver.setBackground(Color.white);
		msg1Receiver.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg1Receiver.setBounds(0, 590, 330, 60);
		phonePanel.add(msg1Receiver);

		String loc = locations[2];
		msg3Receiver = new JTextArea(loc);
		msg3Receiver.setLineWrap(true);
		msg3Receiver.setWrapStyleWord(true);
		msg3Receiver.setBackground(Color.white);
		msg3Receiver.setEditable(false);
		msg3Receiver.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg3Receiver.setBounds(115, 350, 280, 30);
		phonePanel.add(msg3Receiver);
		msg3Receiver.setVisible(false);
	}

	private void setMsg2() {
		msg2Operator = new JTextArea("Sure! Can you tell me your name?");
		msg2Operator.setLineWrap(true);
		msg2Operator.setWrapStyleWord(true);
		msg2Operator.setBackground(Color.white);
		msg2Operator.setEditable(false);
		msg2Operator.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg2Operator.setBounds(5, 200, 280, 30);
		phonePanel.add(msg2Operator);

		msg2Receiver = new JTextArea("My name is ");
		msg2Receiver.setBackground(Color.white);
		msg2Receiver.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg2Receiver.setBounds(0, 590, 330, 60);
		phonePanel.add(msg2Receiver);

	}

	private void setMsg3() {
		msg3Operator = new JTextArea("In what county is you MyBank located?");
		msg3Operator.setLineWrap(true);
		msg3Operator.setWrapStyleWord(true);
		msg3Operator.setBackground(Color.white);
		msg3Operator.setEditable(false);
		msg3Operator.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg3Operator.setBounds(5, 290, 280, 45);
		phonePanel.add(msg3Operator);

		bankLocationChoice = new Choice();
		for (String loc : locations) {
			bankLocationChoice.add(loc);
		}
		bankLocationChoice.setFont(new Font("System", Font.TYPE1_FONT, 16));
		add(bankLocationChoice);
		bankLocationChoice.setBackground(Color.white);
		bankLocationChoice.setBounds(0, 590, 330, 100);
		phonePanel.add(bankLocationChoice);

	}

	private void setMsg4() {

		msg3Receiver.append(locations[bankLocationChoice.getSelectedIndex()]);
		msg3Receiver.setVisible(true);

		try {
			bank_id = getBankId(locations[bankLocationChoice.getSelectedIndex()]);
			getEmployees(bank_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		msg4Operator = new JTextArea("You can meet\n");
		for (String employee : employees) {
			msg4Operator.append(" " + employee + "\n");
		}
		msg4Operator.setLineWrap(true);
		msg4Operator.setWrapStyleWord(true);
		msg4Operator.setBackground(Color.white);
		msg4Operator.setEditable(false);
		msg4Operator.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg4Operator.setBounds(5, 390, 280, 88);
		phonePanel.add(msg4Operator);

		msg4Receiver = new JTextArea();
		msg4Receiver.setEditable(false);
		msg4Receiver.setBackground(Color.white);
		msg4Receiver.setBounds(0, 590, 330, 60);
		phonePanel.add(msg4Receiver);

		msg5Operator = new JTextArea("Thank you for your time! Your ticket number is " + ticketNumber);
		msg5Operator.setLineWrap(true);
		msg5Operator.setWrapStyleWord(true);
		msg5Operator.setBackground(Color.white);
		msg5Operator.setEditable(false);
		msg5Operator.setFont(new Font("System", Font.TYPE1_FONT, 16));
		msg5Operator.setBounds(5, 490, 280, 50);
		phonePanel.add(msg5Operator);

		sendButton.setText("End");
		sendButton.setBackground(Color.red);

	}

	private void getEmployees(int bank_id) throws SQLException {
		String query = "Select employee_name, employee_specialization from employees where bank_id = " + bank_id;
		ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
		int idx = 0;
		while (resultSet.next()) {
			employees[idx] = resultSet.getString("employee_name") + "(" + resultSet.getString("employee_specialization")
					+ ")";
			idx++;
		}
	}

	private void setButton() {
		sendButton = new JButton("Send");
		sendButton.setBackground(new Color(217, 249, 200));
		sendButton.setBounds(330, 590, 75, 60);
		sendButton.addActionListener(this);
		phonePanel.add(sendButton);
	}

	private String getOperatorName() {
		return "name";
	}

	private void getLocations() throws SQLException {
		String query = "Select bank_location from bank_building";
		ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
		int idx = 0;
		while (resultSet.next()) {
			locations[idx] = resultSet.getString("bank_location");
			idx++;
		}
	}

	private int getBankId(String bank_loc) throws SQLException {
		String query = "Select bank_id from bank_building where bank_location = '" + bank_loc + "'";
		ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
		try {
			while (resultSet.next()) {
				return Integer.parseInt(resultSet.getString("bank_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	private void addInDB() {
		String name = msg2Receiver.getText().replace("My name is ", "");
		StringBuilder query = new StringBuilder(
				"insert into bank_appointment (name, bank_id, appointmentCall) VALUE ('" + name + "', "
						+ bank_id + ", NOW())");
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			if (msg1Boolean == true) {
				msg1Receiver.setBounds(115, 125, 280, 55);
				msg1Receiver.setEditable(false);
				msg1Boolean = false;
				msg2Boolean = true;
				setMsg2();
			} else if (msg2Boolean == true) {
				msg2Boolean = false;
				msg2Receiver.setBounds(115, 250, 280, 30);
				msg2Receiver.setEditable(false);
				try {
					getLocations();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				setMsg3();
				msg3Boolean = true;
			} else if (msg3Boolean == true) {
				msg3Boolean = false;
				bankLocationChoice.setVisible(false);
				setMsg4();
			} else {
				addInDB();
				JOptionPane.showMessageDialog(null, "Your ticket is valid for the next 2 days!");
				setVisible(false);
				new LoginGUI();
			}
		}
	}

}
