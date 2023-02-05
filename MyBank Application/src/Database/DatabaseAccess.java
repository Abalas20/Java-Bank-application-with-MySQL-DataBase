package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class DatabaseAccess {
	static DatabaseConnection databaseConnection = new DatabaseConnection();
	public static boolean signIn(String cardNr, String cardPassword) {
		StringBuilder query = new StringBuilder("select card_password from card where card_id ='" + cardNr + "'");
		try {
			boolean notValidated = true;
			ResultSet resultSet = databaseConnection.statement.executeQuery(query.toString());
			while (resultSet.next() && notValidated == true) {
				if (cardPassword.equals(resultSet.getString("card_password"))) {
					return true;
				}
			}
			if (notValidated) {
				JOptionPane.showMessageDialog(null, "Password or Card number is incorrect!");
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}
	
	public static void sendMoney(String srcId, String desId, String amount) {
		long balanceSrc = getBalance(srcId) - Long.parseLong(amount), balanceDes = getBalance(desId) + Long.parseLong(amount);
		if (balanceSrc < 0) {
			JOptionPane.showMessageDialog(null, "Transaction denied\\nYou have insufficient funds to complete this transaction");
			return;
		}
		setBalance(balanceSrc, srcId);
		setBalance(balanceDes, desId);
		createTransaction(srcId, desId, amount);
	}
	
	public static void createTransaction(String src, String des, String amount) {
		StringBuilder query = new StringBuilder("insert into transactions (card_source_id, card_destination_id, amount, transaction_date, transaction_type) VALUE ('");
		query.append(src + "', '" + des +"', '" + amount + "', NOW(), 'transfer')" );
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static long getBalance(String cardNumber) {
		StringBuilder query = new StringBuilder();
		query.append("select card_balance from card where card_id ='" + cardNumber + "'");
			ResultSet resultSet;
			try {
				resultSet = databaseConnection.statement.executeQuery(query.toString());
				while (resultSet.next()) {
					return Long.parseLong(resultSet.getString("card_balance"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 0;
	}
	
	public static void setBalance(long balanceSrc, String cardNumber) {
		StringBuilder query = new StringBuilder();
		query.append("update card set card_balance = '" + balanceSrc + "' where card_id = '" + cardNumber + "'");
		try {
			databaseConnection.statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	
}
