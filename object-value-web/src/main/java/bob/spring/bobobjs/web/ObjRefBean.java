package bob.spring.bobobjs.web;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import bob.burobjs.model.Obj;

public class ObjRefBean {

	/** der Logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	public ObjRefBean() {
	}

	public List<ObjRef> searchObjRef(int otypId) {
		List<ObjRef> x = new LinkedList<>();

		String search = String.format("otyp=%d", otypId);
		Obj[] objs = WebUtil.create(restTemplate).findAllObj(search);

		for (Obj o : objs) {
			x.add(new ObjRef(o.getId(), o.getName()));
		}

		log.info("refs loaded, otyp = " + otypId + ", size = " + x.size());
		return x;
	}

	public static class ObjRef {

		private long id;

		private String label;

		public ObjRef(long id, String label) {
			this.id = id;
			this.label = label;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

	}

}
