package bob.spring.bobobjs.store;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Atyp;
import bob.burobjs.model.Otyp;

class AttrRowMapper implements RowMapper<Attr> {

	@Override
	public Attr mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attr x = new Attr();
		x.setId(rs.getInt("attr_id"));
		x.setName(rs.getString("attr_name"));

		Atyp atyp = new Atyp();
		atyp.setId(rs.getInt("atyp_id"));
		atyp.setName(rs.getString("atyp_name"));
		atyp.setColumn(rs.getString("atyp_column"));
		x.setAtyp(atyp);

		int lookupOtypId = rs.getInt("attr_lookup_otyp_id");
		if (0 < lookupOtypId) {
			String lookupOtypName = rs.getString("attr_lookup_otyp_name");
			Otyp lookup = new Otyp(lookupOtypId, lookupOtypName);
			x.setLookup(lookup);
		}

		return x;
	}

}