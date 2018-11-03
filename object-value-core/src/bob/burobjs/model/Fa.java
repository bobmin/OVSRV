package bob.burobjs.model;

/**
 * Beschreibt ein Attribut auf einem Formular.
 * 
 * @author maik@btmx.net
 *
 */
public class Fa {

	private Form form;

	private Attr attr;

	private int sort;

	public Fa() {
	}

	public Fa(Form form, Attr attr) {
		this.form = form;
		this.attr = attr;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Attr getAttr() {
		return attr;
	}

	public void setAttr(Attr attr) {
		this.attr = attr;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
