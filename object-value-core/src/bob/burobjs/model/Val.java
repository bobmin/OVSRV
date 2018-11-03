package bob.burobjs.model;

import java.math.BigDecimal;

public class Val {

	private Attr attr;

	private String valString = null;

	private BigDecimal valNumber = null;

	private Integer valObject = null;

	public Val() {
	}

	public Attr getAttr() {
		return attr;
	}

	public void setAttr(Attr attr) {
		this.attr = attr;
	}

	public String getValString() {
		return valString;
	}

	public void setValString(String valString) {
		this.valString = valString;
	}

	public BigDecimal getValNumber() {
		return valNumber;
	}

	public void setValNumber(BigDecimal valNumber) {
		this.valNumber = valNumber;
	}

	public Integer getValObject() {
		return valObject;
	}

	public void setValObject(Integer valObject) {
		this.valObject = valObject;
	}

	public String getValueFormatted() {
		String x = null;
		if (null != valString) {
			x = valString;
		} else if (null != valNumber) {
			x = valNumber.toPlainString();
		} else if (null != valObject) {
			x = valObject.toString();
		}
		return x;
	}

	public void setValue(String value) {
		if (null == attr) {
			throw new IllegalStateException("attr missing");
		}
		if (attr.getAtyp().hasString()) {
			valString = (isNull(value) ? null : value);
		} else if (attr.getAtyp().hasNumber()) {
			valNumber = (isNull(value) ? null : new BigDecimal(value));
		} else if (attr.getAtyp().hasObject()) {
			valObject = (isNull(value) ? null : Integer.parseInt(value));
		} else {
			throw new IllegalStateException("atyp unknown: " + attr.getAtyp());
		}
	}

	/**
	 * Liefert <code>true</code> wenn {@code value} einem <code>null</code>
	 * entspricht.
	 * 
	 * @param value
	 *            der Wert
	 * @return <code>true</code> wenn <code>null</code>
	 */
	private boolean isNull(String value) {
		if (null == value || 0 == value.trim().length()) {
			return true;
		}
		return false;
	}

	/**
	 * Liefert <code>true</code> wenn kein Wert zugewiesen ist.
	 * 
	 * @return <code>true</code> wenn kein Wert
	 */
	public boolean isEmpty() {
		return (null == valString && null == valNumber && null == valObject);
	}

	@Override
	public String toString() {
		return "Val [attr=" + attr + ", valString=" + valString + ", valNumber=" + valNumber + ", valObject="
				+ valObject + "]";
	}

}
