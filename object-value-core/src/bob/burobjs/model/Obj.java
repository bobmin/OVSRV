package bob.burobjs.model;

public class Obj {

	private long id;

	private String name;

	private Otyp otyp;

	public Obj() {
	}

	public Obj(Otyp otyp, String name) {
		this.otyp = otyp;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Otyp getOtyp() {
		return otyp;
	}

	public void setOtyp(Otyp otyp) {
		this.otyp = otyp;
	}

}
