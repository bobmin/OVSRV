package bob.spring.bobobjs.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Otyp;

@RestController
public class AttrRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Liefert alle Attribute.
	 * 
	 * @return ein Objekt
	 */
	@GetMapping("/attr")
	public List<Attr> findAll() {
		return jdbcTemplate.query(StoreUtil.SELECT_ATTR, new AttrRowMapper());
	}

	/**
	 * Erstellt ein neues Attribut.
	 * 
	 * @param newAttr
	 *            das neue Attribut
	 */
	@PostMapping("/attr")
	public void create(@RequestBody Attr newAttr) {
		Otyp lookup = newAttr.getLookup();
		if (null == lookup) {
			String sql = "INSERT INTO bob_attribute (attr_atyp_id, attr_name) VALUES (?, ?)";
			jdbcTemplate.update(sql, newAttr.getAtyp().getId(), newAttr.getName());
		} else {
			String sql = "INSERT INTO bob_attribute (attr_atyp_id, attr_name, attr_lookup_otyp_id) VALUES (?, ?, ?)";
			jdbcTemplate.update(sql, newAttr.getAtyp().getId(), newAttr.getName(), lookup.getId());
		}
	}

	/**
	 * Liefert ein einzelnes Attribut.
	 * 
	 * @param id
	 *            die ID vom Attribut
	 * @return ein Objekt oder <code>null</code>
	 */
	@GetMapping("/attr/{id}")
	public Attr find(@PathVariable("id") int id) {
		String sql = StoreUtil.SELECT_ATTR + " WHERE attr_id = ?";
		return jdbcTemplate.queryForObject(sql, new AttrRowMapper(), id);
	}

	/**
	 * Aktualisiert ein Attribut.
	 * 
	 * @param attr
	 *            das geänderte Attribut
	 */
	@PutMapping("/attr/{id}")
	public void update(@RequestBody Attr attr) {
		String sql = "UPDATE bob_attribute SET attr_name = ? WHERE attr_id = ?";
		jdbcTemplate.update(sql, attr.getName(), attr.getId());
	}

}
