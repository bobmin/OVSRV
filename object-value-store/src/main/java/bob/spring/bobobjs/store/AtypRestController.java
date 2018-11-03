package bob.spring.bobobjs.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bob.burobjs.model.Atyp;

@RestController
public class AtypRestController {

	private static final String SELECT_FORM = "SELECT * FROM bob_attribute_type";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/atyp")
	public List<Atyp> findAll() {
		return jdbcTemplate.query(SELECT_FORM, new AtypRowMapper());
	}

	@RequestMapping(value = "/atyp", method = RequestMethod.POST)
	public void create(@RequestBody Atyp newAtyp) {
		String sql = "insert into bob_attribute_type (atyp_name, atyp_column) values (?, ?)";
		jdbcTemplate.update(sql, newAtyp.getName(), newAtyp.getColumn());
	}

	@RequestMapping("/atyp/{id}")
	public Atyp find(@PathVariable("id") int id) {
		String sql = SELECT_FORM + " WHERE atyp_id = ?";
		return jdbcTemplate.queryForObject(sql, new AtypRowMapper(), id);
	}

	@RequestMapping(value = "/atyp/{id}", method = RequestMethod.PUT)
	public void update(@RequestBody Atyp atyp) {
		String sql = "UPDATE bob_attribute_type SET atyp_name = ? WHERE atyp_id = ?";
		jdbcTemplate.update(sql, atyp.getName(), atyp.getId());
	}

	private class AtypRowMapper implements RowMapper<Atyp> {

		@Override
		public Atyp mapRow(ResultSet rs, int rowNum) throws SQLException {
			Atyp x = new Atyp();
			x.setId(rs.getInt("atyp_id"));
			x.setName(rs.getString("atyp_name"));
			x.setColumn(rs.getString("atyp_column"));
			return x;
		}

	}

}
