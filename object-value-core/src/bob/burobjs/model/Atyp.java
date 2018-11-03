package bob.burobjs.model;

public class Atyp {

	/** Konstante für Attribut mit Zeichenkette */
	public static final String VAL_STRING = "VAL_STRING";

	/** Konstante für Attribut mit Dezimalzahl */
	public static final String VAL_NUMBER = "VAL_NUMBER";

	/**
	 * Konstante für Attribut mit Objektreferenz
	 * <p>
	 * Gespeichert wird die ID (BIGINT) vom referenzierten Objekt.
	 */
	public static final String VAL_OBJECT = "VAL_OBJECT";

	/**
	 * Konstante für Attribut mit Binärcode
	 * <p>
	 * Gespeichert wird beispielsweise die Base64-Entsprechung von einem Icon.
	 */
	public static final String VAL_BINARY = "VAL_BINARY";

	private int id;

	private String name;

	private String column;

	public Atyp(int id, String name, String column) {
		this.id = id;
		this.name = name;
		this.column = column;
	}

	public Atyp(int id, String name) {
		this(id, name, null);
	}

	public Atyp() {
		super();
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

	public void setColumn(String column) {
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	/**
	 * Liefert <code>true</code> wenn eine Zeichenkette beschrieben wird.
	 * 
	 * @return <code>true</code> wenn Zeichenkette
	 */
	public boolean hasString() {
		return VAL_STRING.equals(column);
	}

	/**
	 * Liefert <code>true</code> wenn eine Dezimalzahl beschrieben wird.
	 * 
	 * @return <code>true</code> wenn Dezimalzahl
	 */
	public boolean hasNumber() {
		return VAL_NUMBER.equals(column);
	}

	/**
	 * Liefert <code>true</code> wenn eine Objektreferenz beschrieben wird.
	 * 
	 * @return <code>true</code> wenn Objektreferenz
	 */
	public boolean hasObject() {
		return VAL_OBJECT.equals(column);
	}

	/**
	 * Liefert <code>true</code> wenn ein Binärcode beschrieben wird.
	 * 
	 * @return <code>true</code> wenn Binärcode
	 */
	public boolean hasBinary() {
		return VAL_BINARY.equals(column);
	}

	@Override
	public String toString() {
		return "Atyp [id=" + id + ", name=" + name + ", column=" + column + "]";
	}

}
