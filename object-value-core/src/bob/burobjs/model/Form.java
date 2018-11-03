package bob.burobjs.model;

/**
 * Beschreibt ein Formular.
 * 
 * @author m.boettcher@btmx.net
 *
 */
public class Form {

	private int id;

	private String name;

	public Form(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Form() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
