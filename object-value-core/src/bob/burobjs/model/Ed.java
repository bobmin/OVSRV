package bob.burobjs.model;

/**
 * Der Editor (Ed) verwaltet Formularwerte für ein Objekt.
 * 
 * @author maik@btmx.net
 *
 */
public class Ed {

	Obj obj;

	Form form;

	Val[] val;

	public Ed() {
	}

	public Obj getObj() {
		return obj;
	}

	public void setObj(Obj obj) {
		this.obj = obj;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Val[] getVal() {
		return val;
	}

	public void setVal(Val[] val) {
		this.val = val;
	}

}
