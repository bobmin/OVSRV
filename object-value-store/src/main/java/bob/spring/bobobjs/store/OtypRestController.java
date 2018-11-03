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

import bob.burobjs.model.Form;
import bob.burobjs.model.Otyp;

@RestController
public class OtypRestController {

	private static final String SELECT_OTYP = "SELECT otyp_id, otyp_name, otyp_description FROM bob_object_type";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// -----------------------------------------------------------------------

	@RequestMapping("/otyp")
	public List<Otyp> findAll() {
		return jdbcTemplate.query(SELECT_OTYP, new OtypRowMapper());
	}

	@RequestMapping(value = "/otyp", method = RequestMethod.POST)
	public void create(@RequestBody Otyp newOtyp) {
		String sql = "INSERT INTO bob_object_type (otyp_name, otyp_description) VALUES (?, ?)";
		jdbcTemplate.update(sql, newOtyp.getName(), newOtyp.getDescription());
	}

	// -----------------------------------------------------------------------

	@RequestMapping("/otyp/{otypId}")
	public Otyp find(@PathVariable("otypId") int otypId) {
		String otypSql = SELECT_OTYP + " WHERE otyp_id = ?";
		Otyp otyp = jdbcTemplate.queryForObject(otypSql, new OtypRowMapper(), otypId);
		return otyp;
	}

	@RequestMapping(value = "/otyp/{id}", method = RequestMethod.PUT)
	public void update(@RequestBody Otyp otyp) {
		String sql = "UPDATE bob_object_type SET otyp_name = ?, otyp_description = ? WHERE otyp_id = ?";
		jdbcTemplate.update(sql, otyp.getName(), otyp.getDescription(), otyp.getId());
	}

	// -----------------------------------------------------------------------

	@RequestMapping("/otyp/{otypId}/form")
	public List<Form> findForm(@PathVariable("otypId") int otypId) {
		// Formulare
		// @formatter:off
		String formSql = "SELECT of_form_id as form_id, form_name " 
				+ "FROM bob_object_type_form "
				+ "INNER JOIN bob_form ON of_form_id = form_id " 
				+ "WHERE of_otyp_id = ?";
		// @formatter:on
		List<Form> ofList = jdbcTemplate.query(formSql, new FormRowMapper(), otypId);
		return ofList;
	}

	// -----------------------------------------------------------------------

	private class OtypRowMapper implements RowMapper<Otyp> {

		@Override
		public Otyp mapRow(ResultSet rs, int rowNum) throws SQLException {
			Otyp x = new Otyp();
			x.setId(rs.getInt("otyp_id"));
			x.setName(rs.getString("otyp_name"));
			x.setDescription(rs.getString("otyp_description"));
			return x;
		}

	}

}
