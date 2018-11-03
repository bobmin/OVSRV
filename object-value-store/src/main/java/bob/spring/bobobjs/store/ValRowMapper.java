package bob.spring.bobobjs.store;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Atyp;
import bob.burobjs.model.Otyp;
import bob.burobjs.model.Val;

class ValRowMapper implements RowMapper<Val> {

	@Override
	public Val mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Attributstyp
		Atyp atyp = new Atyp();
		atyp.setId(rs.getInt("atyp_id"));
		atyp.setName(rs.getString("atyp_name"));
		String column = rs.getString("atyp_column");
		atyp.setColumn(column);

		// Attribut
		Attr attr = new Attr();
		attr.setId(rs.getInt("attr_id"));
		attr.setName(rs.getString("attr_name"));
		attr.setAtyp(atyp);

		// Objekttyp für Lookup
		int lookup_otyp_id = rs.getInt("lookup_otyp_id");
		if (0 < lookup_otyp_id) {
			String lookup_otyp_name = rs.getString("lookup_otyp_name");
			Otyp lookup = new Otyp(lookup_otyp_id, lookup_otyp_name);
			attr.setLookup(lookup);
		}

		// Value
		Val x = new Val();
		x.setAttr(attr);

		if (Atyp.VAL_STRING.equals(column)) {
			x.setValString(rs.getString("val_string"));
		} else if (Atyp.VAL_NUMBER.equals(column)) {
			x.setValNumber(rs.getBigDecimal("val_number"));
		} else if (Atyp.VAL_OBJECT.equals(column)) {
			x.setValObject(rs.getInt("val_object"));
		} else {
			throw new IllegalStateException("column unknown: " + column);
		}

		return x;
	}

}