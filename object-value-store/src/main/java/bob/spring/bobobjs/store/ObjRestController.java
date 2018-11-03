package bob.spring.bobobjs.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bob.burobjs.model.Atyp;
import bob.burobjs.model.Obj;
import bob.burobjs.model.Otyp;
import bob.burobjs.model.Val;

@RestController
public class ObjRestController {

	/** der Logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String SELECT_OBJ = "SELECT obj_id, obj_otyp_id, otyp_name, val_string " + "FROM bob_object "
			+ "INNER JOIN bob_object_type ON obj_otyp_id = otyp_id "
			+ "LEFT JOIN bob_value ON obj_id = val_obj_id and val_attr_id = 0";

	private static final String INSERT_OBJ = "INSERT INTO bob_object (obj_otyp_id) VALUES (?)";

	// @formatter:off
	private static final String SELECT_VAL = "";
	// @formatter:on

	private static final String INSERT_VAL_STRING = "INSERT INTO bob_value (val_obj_id, val_attr_id, val_string) VALUES (?, ?, ?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/obj")
	public List<Obj> findAllObj(@RequestParam(value = "f", required = false) String filter) {
		List<Obj> x;
		if (null != filter && filter.matches("otyp=[\\d]+")) {
			int otypId = Integer.parseInt(filter.split("=")[1]);
			log.info("filter assigned, value = " + otypId);
			x = jdbcTemplate.query(SELECT_OBJ + " WHERE otyp_id = ?", new ObjRowMapper(), otypId);
		} else {
			x = jdbcTemplate.query(SELECT_OBJ, new ObjRowMapper());
		}
		return x;
	}

	@PostMapping("/obj")
	public void create(@RequestBody Obj obj) {
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_OBJ, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, obj.getOtyp().getId());
				return ps;
			}
		}, holder);

		jdbcTemplate.update(INSERT_VAL_STRING, holder.getKey().longValue(), 0, obj.getName());
	}

	@GetMapping("/obj/{objId}")
	public Obj findObj(@PathVariable("objId") long objId) {
		String sql = SELECT_OBJ + " WHERE obj_id = ?";
		return jdbcTemplate.queryForObject(sql, new ObjRowMapper(), objId);
	}

	@GetMapping("/obj/{objId}/val")
	public List<Val> findAllVal(@PathVariable("objId") long objId) {
		return jdbcTemplate.query(SELECT_VAL + " WHERE obj_id = ?", new ValRowMapper(), objId);
	}

	@GetMapping("/obj/{objId}/val/{attrId}")
	public Val findVal(@PathVariable("objId") long objId, @PathVariable("attrId") int attrId) {
		String sql = SELECT_VAL + " WHERE obj_id = ? AND attr_id = ?";
		return jdbcTemplate.queryForObject(sql, new ValRowMapper(), objId, attrId);
	}

	// -----------------------------------------------------------------------

	@GetMapping("/obj/{objId}/form/{formId}")
	public List<Val> findAllVal(@PathVariable("objId") long objId, @PathVariable("formId") int formId) {
		String sql = "select * from bob_obj_form where obj_id = ? and form_id = ?";
		return jdbcTemplate.query(sql, new ValRowMapper(), objId, formId);
	}

	@PutMapping("/obj/{objId}/form/{formId}")
	public void updateAttr(@PathVariable("objId") long objId, @PathVariable("formId") int formId,
			@RequestBody List<Val> vals) {
		for (Val newVal : vals) {
			int attrId = newVal.getAttr().getId();
			Val oldVal = StoreUtil.create(jdbcTemplate).findVal(objId, attrId);
			String sql;
			if (null != oldVal && newVal.isEmpty()) {
				sql = "DELETE FROM bob_value WHERE val_obj_id = ? AND val_attr_id = ?";
				jdbcTemplate.update(sql, objId, attrId);
			} else {
				Atyp atyp = newVal.getAttr().getAtyp();
				Object value;
				if (null == oldVal) {
					if (atyp.hasString()) {
						sql = "INSERT INTO bob_value (val_string, val_obj_id, val_attr_id) VALUES (?, ?, ?)";
						value = newVal.getValString();
					} else if (atyp.hasNumber()) {
						sql = "INSERT INTO bob_value (val_number, val_obj_id, val_attr_id) VALUES (?, ?, ?)";
						value = newVal.getValNumber();
					} else if (atyp.hasObject()) {
						sql = "INSERT INTO bob_value (val_object, val_obj_id, val_attr_id) VALUES (?, ?, ?)";
						value = newVal.getValObject();
					} else {
						throw new IllegalStateException("atyp unknown: " + atyp);
					}
				} else {
					if (atyp.hasString()) {
						sql = "UPDATE bob_value SET val_string = ? WHERE val_obj_id = ? AND val_attr_id = ?";
						value = newVal.getValString();
					} else if (atyp.hasNumber()) {
						sql = "UPDATE bob_value SET val_number = ? WHERE val_obj_id = ? AND val_attr_id = ?";
						value = newVal.getValNumber();
					} else if (atyp.hasObject()) {
						sql = "UPDATE bob_value SET val_object = ? WHERE val_obj_id = ? AND val_attr_id = ?";
						value = newVal.getValObject();
					} else {
						throw new IllegalStateException("atyp unknown: " + atyp);
					}
				}
				jdbcTemplate.update(sql, value, objId, attrId);
			}
		}
	}

	// -----------------------------------------------------------------------

	private class ObjRowMapper implements RowMapper<Obj> {

		@Override
		public Obj mapRow(ResultSet rs, int rowNum) throws SQLException {
			Obj x = new Obj();
			long objid = rs.getLong("obj_id");
			x.setId(objid);

			Otyp otyp = new Otyp();
			otyp.setId(rs.getInt("obj_otyp_id"));
			otyp.setName(rs.getString("otyp_name"));
			x.setOtyp(otyp);

			String name = rs.getString("val_string");
			if (null == name) {
				x.setName(String.format("Objekt %d", objid));
			} else {
				x.setName(name);
			}
			return x;
		}

	}

}
