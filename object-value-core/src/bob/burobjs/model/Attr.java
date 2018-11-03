package bob.burobjs.model;

/**
 * Beschreibt eine Eigenschaft.
 * 
 * @author m.boettcher@btmx.net
 *
 */
public class Attr {

	/** die eindeutige ID */
	private int id;

	/** der Bezeichner */
	private String name;

	/** der Datentyp */
	private Atyp atyp;

	/** der optionale Objekttyp zum Nachschlagen */
	private Otyp lookup;

	public Attr() {
	}

	public Attr(Atyp atyp, String name) {
		this.atyp = atyp;
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

	public void setAtyp(Atyp atyp) {
		this.atyp = atyp;
	}

	public Atyp getAtyp() {
		return atyp;
	}

	public Otyp getLookup() {
		return lookup;
	}

	public void setLookup(Otyp otyp) {
		this.lookup = otyp;
	}

	@Override
	public String toString() {
		return "Attr [id=" + id + ", name=" + name + ", atyp=" + atyp + ", lookup=" + lookup + "]";
	}

}
