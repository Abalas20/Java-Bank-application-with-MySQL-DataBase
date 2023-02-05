package GUI;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class NewFrameWindow extends JFrame {
	public NewFrameWindow() {
		setTitle("MyBank");
		setLayout(null);
		ImageIcon bankIconImage = new ImageIcon(ClassLoader.getSystemResource("res/BankIcon.jpg"));
		Image bankIcon = bankIconImage.getImage();
		getContentPane().setBackground(Color.white);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setIconImage(bankIcon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setBackgroundImage(int imageNumber) {	
		// imageNumber = 0 for simple ATM image
		// 1 for ATM with money image
		// 2 for phone image
		String source = "res/atm.jpg";
		if (imageNumber == 1) {
			source = "res/atmWmoney.jpg";
		} else if (imageNumber == 2) {
			source = "res/phone.png";
		}
		ImageIcon atmIcon = new ImageIcon(ClassLoader.getSystemResource(source));
		Image atmImage = atmIcon.getImage().getScaledInstance(960, 850, Image.SCALE_DEFAULT);
		atmIcon = new ImageIcon(atmImage);
		JLabel atmLabel = new JLabel(atmIcon);
		atmLabel.setBounds(250, 0, 960, 850);
		add(atmLabel);
		getContentPane().setBackground(new Color(144, 144, 144));
	}
}
