package bob.burobjs.model;

public class Of {

	private int formId;

	private String formName;

	private int otypId;

	private String otypName;

	public Of() {
	}

	public Of(int formId, int otypId) {
		this.formId = formId;
		this.otypId = otypId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int getOtypId() {
		return otypId;
	}

	public void setOtypId(int attrId) {
		this.otypId = attrId;
	}

	public String getOtypName() {
		return otypName;
	}

	public void setOtypName(String attrName) {
		this.otypName = attrName;
	}

}
