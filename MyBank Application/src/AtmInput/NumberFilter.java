package AtmInput;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberFilter extends DocumentFilter {
	
	int acceptabaleLength;
	public NumberFilter(int maxLenght) {
		acceptabaleLength = maxLenght;
	}
	
	  @Override
	  public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
	    // Insert the text if it consists only of digits
	    if (string.matches("\\d") && fb.getDocument().getLength() + string.toString().length() <= acceptabaleLength) {
	      super.insertString(fb, offset, string.toString(), attr);
	    }
	  }

	  @Override
	  public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
	    // Only replace the text if it consists only of digits
	    if (string.matches("\\d") && fb.getDocument().getLength() + string.toString().length() - length <= acceptabaleLength) {
	      super.replace(fb, offset, length, string.toString(), attr);
	    }
	  }
}
