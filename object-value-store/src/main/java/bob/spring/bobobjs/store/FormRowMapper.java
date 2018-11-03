package bob.spring.bobobjs.store;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import bob.burobjs.model.Form;

public class FormRowMapper implements RowMapper<Form> {

	@Override
	public Form mapRow(ResultSet rs, int rowNum) throws SQLException {
		Form x = new Form();
		x.setId(rs.getInt("form_id"));
		x.setName(rs.getString("form_name"));
		return x;
	}

}
