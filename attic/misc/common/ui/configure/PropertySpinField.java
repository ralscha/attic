package common.ui.configure;

import common.ui.spinner.JSpinField;
import java.util.StringTokenizer;

public class PropertySpinField extends JSpinField implements PropertyField {

	public PropertySpinField() {
		super();
	}

	public String getValue() {
   		return String.valueOf(getFieldValue()) + "," +
   				String.valueOf(getMinimum()) + "," +
   				String.valueOf(getMaximum());
   	}

	public void setValue(String value) {
		
		StringTokenizer tokenizer = new StringTokenizer(value, ",", false);
		
		int iv = Integer.parseInt(tokenizer.nextToken());
		setMinimum(Integer.parseInt(tokenizer.nextToken()));
		setMaximum(Integer.parseInt(tokenizer.nextToken()));
		setFieldValue(iv);
		
	}
}