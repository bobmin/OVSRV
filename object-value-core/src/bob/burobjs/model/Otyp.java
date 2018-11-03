package bob.burobjs.model;

/**
 * Beschreibt einen Objekttypen.
 * 
 * @author maik@btmx.net
 *
 */
public class Otyp {

	/** die eindeutige ID */
	private int id;

	/** der Bezeichner */
	private String name;

	/** die Beschreibgung */
	private String description;

	public Otyp() {
	}

	public Otyp(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
