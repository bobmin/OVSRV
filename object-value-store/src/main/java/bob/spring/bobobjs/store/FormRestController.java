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

import bob.burobjs.model.Attr;
import bob.burobjs.model.Fa;
import bob.burobjs.model.Form;
import bob.burobjs.model.Of;
import bob.burobjs.model.Otyp;

@RestController
public class FormRestController {

	private static final String SELECT_FORM = "SELECT * FROM bob_form";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Liefert alle Formulare.
	 * 
	 * @return
	 */
	@RequestMapping("/form")
	public List<Form> findAll() {
		return jdbcTemplate.query(SELECT_FORM, new FormRowMapper());
	}

	/**
	 * Erstellt ein Formular.
	 * 
	 * @param newForm
	 */
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public void create(@RequestBody Form newForm) {
		String sql = "INSERT INTO bob_form (form_name) VALUES (?)";
		jdbcTemplate.update(sql, newForm.getName());
	}

	/**
	 * Liefert das Formular.
	 * 
	 * @param id
	 *            die ID vom Formular
	 * @return
	 */
	@RequestMapping("/form/{id}")
	public Form find(@PathVariable("id") int id) {
		String sql = SELECT_FORM + " WHERE form_id = ?";
		return jdbcTemplate.queryForObject(sql, new FormRowMapper(), id);
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.PUT)
	public void update(@RequestBody Form form) {
		String sql = "UPDATE bob_form SET form_name = ? WHERE form_id = ?";
		jdbcTemplate.update(sql, form.getName(), form.getId());
	}

	@RequestMapping("/form/{formId}/attr")
	public List<Fa> findAttr(@PathVariable("formId") int formId) {
		String sql = "SELECT fa_form_id, form_name, fa_attr_id, attr_name " + "FROM bob_form_attribute "
				+ "INNER JOIN bob_form ON fa_form_id = form_id " + "INNER JOIN bob_attribute ON fa_attr_id = attr_id "
				+ "WHERE fa_form_id = ?";
		return jdbcTemplate.query(sql, new FaRowMapper(), formId);
	}

	@RequestMapping(value = "/form/{id}/attr", method = RequestMethod.POST)
	public void createAttr(@PathVariable("id") int formId, @RequestBody Attr attr) {
		String sql = "INSERT INTO bob_form_attribute (fa_form_id, fa_attr_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, formId, attr.getId());
		// return FA?
	}

	@RequestMapping(value = "/form/{formId}/attr/{attrId}", method = RequestMethod.DELETE)
	public void deleteAttr(@PathVariable("formId") int formId, @PathVariable("attrId") int attrId) {
		String sql = "DELETE FROM bob_form_attribute WHERE fa_form_id = ? AND fa_attr_id = ?";
		jdbcTemplate.update(sql, formId, attrId);
	}

	// -----------------------------------------------------------------------

	@RequestMapping("/form/{formId}/otyp")
	public List<Of> findOtyp(@PathVariable("formId") int formId) {
		// @formatter:off
		String sql = "SELECT of_form_id, form_name, of_otyp_id, otyp_name " 
				+ "FROM bob_object_type_form "
				+ "INNER JOIN bob_form ON of_form_id = form_id " 
				+ "INNER JOIN bob_object_type ON of_otyp_id = otyp_id "
				+ "WHERE of_form_id = ?";
		// @formatter:on
		return jdbcTemplate.query(sql, new OfRowMapper(), formId);
	}

	@RequestMapping(value = "/form/{id}/otyp", method = RequestMethod.POST)
	public void createOtyp(@PathVariable("id") int formId, @RequestBody Otyp otyp) {
		String sql = "INSERT INTO bob_object_type_form (of_form_id, of_otyp_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, formId, otyp.getId());
		// return OF?
	}

	@RequestMapping(value = "/form/{formId}/otyp/{otypId}", method = RequestMethod.DELETE)
	public void deleteOtyp(@PathVariable("formId") int formId, @PathVariable("otypId") int otypId) {
		String sql = "DELETE FROM bob_object_type_form WHERE of_form_id = ? AND of_otyp_id = ?";
		jdbcTemplate.update(sql, formId, otypId);
	}

	// -----------------------------------------------------------------------

	private class FaRowMapper implements RowMapper<Fa> {

		@Override
		public Fa mapRow(ResultSet rs, int rowNum) throws SQLException {
			// Form
			Form form = new Form();
			form.setId(rs.getInt("fa_form_id"));
			form.setName(rs.getString("form_name"));
			// Attribut
			Attr attr = new Attr();
			attr.setId(rs.getInt("fa_attr_id"));
			attr.setName(rs.getString("attr_name"));
			return new Fa(form, attr);
		}

	}

	private class OfRowMapper implements RowMapper<Of> {

		@Override
		public Of mapRow(ResultSet rs, int rowNum) throws SQLException {
			Of x = new Of();
			x.setFormId(rs.getInt("of_form_id"));
			x.setFormName(rs.getString("form_name"));
			x.setOtypId(rs.getInt("of_otyp_id"));
			x.setOtypName(rs.getString("otyp_name"));
			return x;
		}

	}

}
