package AtmInput;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

@SuppressWarnings("serial")
public class AtmTextField extends JTextField {
	public AtmTextField(String text, int maxLength) {
		super(text);
		AbstractDocument doc = (AbstractDocument) getDocument();
		doc.setDocumentFilter(new NumberFilter(maxLength));
	}
}
