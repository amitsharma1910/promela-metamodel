package model.promela;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mtype variable or field in a utype and its valid values
 * 
 * @author Duc
 * 
 */
public class MtypeUtypeField {
	private String varName;
	private String fieldName;
	private String utypeName;
	private List<String> validValues = new ArrayList<String>();

	public MtypeUtypeField() {

	}

	public MtypeUtypeField(String varName, String fieldName) {
		setVarName(varName);
		setFieldName(fieldName);
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getUtypeName() {
		return utypeName;
	}

	public void setUtypeName(String utypeName) {
		this.utypeName = utypeName;
	}

	public List<String> getValidValues() {
		return validValues;
	}

	public void setValidValues(List<String> validValues) {
		this.validValues = validValues;
	}

	/**
	 * Add an valid value into this mtype.
	 * 
	 * @param validValue
	 */
	public void addValidValue(String validValue) {
		if (!containsValidValue(validValue))
			validValues.add(validValue);
	}

	/**
	 * Check if the valid value is in the list of valid values.
	 * 
	 * @param validValue
	 * @return
	 */
	public boolean containsValidValue(String validValue) {
		return validValues.contains(validValue);
	}
}
