package Database;

import javax.swing.JOptionPane;

public class PersonDetails {

	private String name, fathersInitial, dob, gender, email, martial, address, city, pincode, accountType, income,
			education, occupation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFathersInitial() {
		return fathersInitial;
	}

	public void setFathersInitial(String fathersInitial) {
		this.fathersInitial = fathersInitial;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMartial() {
		return martial;
	}

	public void setMartial(String martial) {
		this.martial = martial;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public boolean checkInput() {
		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "Name is requierd!");
			return false;
		} else if (fathersInitial.charAt(0) == ' ') {
			JOptionPane.showMessageDialog(null, "Father's initial not selected!");
			return false;
		} else if (dob.equals("") || dob.length() < 10) {
			JOptionPane.showMessageDialog(null, "Please select a valid date!");
			return false;
		} else if (gender == null) {
			JOptionPane.showMessageDialog(null, "Please select a gender!");
			return false;
		} else if (email.equals("")) {
			JOptionPane.showMessageDialog(null, "Email address is requiered!");
			return false;
		} else if (martial == null) {
			JOptionPane.showMessageDialog(null, "Please select a martial status");
			return false;
		} else if (address.equals("")) {
			JOptionPane.showMessageDialog(null, "Address is requiered!");
			return false;
		} else if (city.equals("")) {
			JOptionPane.showMessageDialog(null, "City is requiered!");
			return false;
		} else if (pincode.equals("")) {
			JOptionPane.showMessageDialog(null, "Pin code is required!");
			return false;
		}
		return true;
	}
	
}
