package syncdbo.dao;

import java.util.Collection;
import java.util.LinkedHashMap;

public class DBO {

	protected LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();

	public DBO() {
	}

	public DBO(LinkedHashMap<String, String> fields) {
		this.fields = fields;
	}

	public LinkedHashMap<String, String> getFields() {
		return fields;
	}

	public void setFields(LinkedHashMap<String, String> fields) {
		this.fields = fields;
	}

	public String keysToString() {

		String keysString = "";

		Object[] fieldsArray = fields.keySet().toArray();
		keysString = keysString + fieldsArray[0].toString();
		for (int i = 1; i < fieldsArray.length; i++) {
			keysString = keysString + ", " + (String) fieldsArray[i];
		}

		return keysString;
	}

	public String valuesToString() {

		String fieldsString = "";

		Collection<String> values = fields.values();
		Object[] fieldsArray = values.toArray();
		fieldsString = fieldsString + "\"" + fieldsArray[0].toString() + "\"";
		for (int i = 1; i < fieldsArray.length; i++) {
			String fieldValue = "\"" + (String) fieldsArray[i] + "\"";
			fieldsString = fieldsString + "," + fieldValue;
		}

		return fieldsString;
	}
}
