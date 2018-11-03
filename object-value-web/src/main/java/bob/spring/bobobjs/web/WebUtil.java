package bob.spring.bobobjs.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Atyp;
import bob.burobjs.model.Form;
import bob.burobjs.model.Obj;
import bob.burobjs.model.Otyp;
import bob.burobjs.model.Val;

public class WebUtil {

	public static final String REST_SERVER_URL = "http://localhost:8889";

	private final RestTemplate restTemplate;

	private WebUtil(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static WebUtil create(final RestTemplate restTemplate) {
		return new WebUtil(restTemplate);
	}

	public Atyp[] findAllAtyp() {
		String url = REST_SERVER_URL + "/atyp";
		ResponseEntity<Atyp[]> response = restTemplate.getForEntity(url, Atyp[].class);
		return response.getBody();
	}

	public Attr[] findAllAttr() {
		String url = REST_SERVER_URL + "/attr";
		ResponseEntity<Attr[]> response = restTemplate.getForEntity(url, Attr[].class);
		return response.getBody();
	}

	public Obj[] findAllObj(String filter) {
		String url = REST_SERVER_URL + "/obj";
		if (null != filter) {
			url += "?f=" + filter;
		}
		ResponseEntity<Obj[]> response = restTemplate.getForEntity(url, Obj[].class);
		return response.getBody();
	}

	public Otyp[] findAllOtyp() {
		String otypResourceUrl = REST_SERVER_URL + "/otyp";
		ResponseEntity<Otyp[]> response = restTemplate.getForEntity(otypResourceUrl, Otyp[].class);
		return response.getBody();
	}

	public Otyp findOtyp(int otypId) {
		String url = WebUtil.REST_SERVER_URL + "/otyp/" + otypId;
		ResponseEntity<Otyp> response = restTemplate.getForEntity(url, Otyp.class);
		return response.getBody();
	}

	public Form findForm(int formId) {
		String url = WebUtil.REST_SERVER_URL + "/form/" + formId;
		ResponseEntity<Form> response = restTemplate.getForEntity(url, Form.class);
		return response.getBody();
	}

	public Val[] findAllVal(long objId, int formId) {
		String objFormUrl = WebUtil.REST_SERVER_URL + "/obj/" + objId + "/form/" + formId;
		ResponseEntity<Val[]> response = restTemplate.getForEntity(objFormUrl, Val[].class);
		return response.getBody();
	}

	/**
	 * Liefert ein Attribut.
	 * 
	 * @param id
	 *            die ID vom Attribut
	 * @return ein Objekt oder <code>null</code>
	 */
	public Attr findAttr(int id) {
		String url = WebUtil.REST_SERVER_URL + "/attr/" + id;
		ResponseEntity<Attr> response = restTemplate.getForEntity(url, Attr.class);
		return response.getBody();
	}

	public Form[] findFormByOtyp(int otypId) {
		String url = WebUtil.REST_SERVER_URL + "/otyp/" + otypId + "/form/";
		ResponseEntity<Form[]> response = restTemplate.getForEntity(url, Form[].class);
		return response.getBody();
	}

	public Atyp findAtyp(int id) {
		String url = WebUtil.REST_SERVER_URL + "/atyp/" + id;
		ResponseEntity<Atyp> response = restTemplate.getForEntity(url, Atyp.class);
		return response.getBody();
	}

}
