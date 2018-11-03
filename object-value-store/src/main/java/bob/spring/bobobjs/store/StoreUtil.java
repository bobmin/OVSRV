package bob.spring.bobobjs.store;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Val;

public class StoreUtil {

	public static final String SELECT_ATTR = "SELECT * FROM bob_attr";

	public static final String SELECT_VAL = "SELECT * FROM bob_val";

	private final JdbcTemplate jdbcTemplate;

	private StoreUtil(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static StoreUtil create(final JdbcTemplate jdbcTemplate) {
		return new StoreUtil(jdbcTemplate);
	}

	public Attr findAttr(int id) {
		String sql = SELECT_ATTR + " WHERE attr_id = ?";
		return jdbcTemplate.queryForObject(sql, new AttrRowMapper(), id);
	}

	/**
	 * Liefert den Wert zum Objekt.
	 * 
	 * @param objId
	 *            die ID vom Objekt
	 * @param attrId
	 *            die ID vom Attribut
	 * @return ein Objekt oder <code>null</code>
	 */
	public Val findVal(long objId, int attrId) {
		String sql = SELECT_VAL + " WHERE obj_id = ? AND attr_id = ?";
		List<Val> x = jdbcTemplate.query(sql, new ValRowMapper(), objId, attrId);
		return (0 == x.size() ? null : x.get(0));
	}

}
