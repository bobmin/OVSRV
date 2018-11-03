package bob.spring.bobobjs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import bob.burobjs.model.Atyp;
import bob.burobjs.model.Otyp;

@Controller
public class AtypWebController {

	private static final String REDIRECT = "redirect:/admin/atyp";

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Liefert alle Datentypen.
	 * 
	 * @param editAtypId
	 *            die optionale ID vom Typ der bearbeitet werden soll
	 * @param model
	 *            die Antwortwerte
	 * @return der Templatename
	 */
	@RequestMapping("/admin/atyp")
	String findAll(@RequestParam(value = "edit", required = false) Integer editAtypId, Model model) {
		// alle Typen abfragen
		Atyp[] x = WebUtil.create(restTemplate).findAllAtyp();
		model.addAttribute("elements", x);

		// optinal einen Typ zur Bearbeitung laden
		if (null != editAtypId) {
			String url = WebUtil.REST_SERVER_URL + "/atyp/" + editAtypId.intValue();
			ResponseEntity<Atyp> response = restTemplate.getForEntity(url, Atyp.class);
			Atyp atyp = response.getBody();
			model.addAttribute("editAtyp", atyp);
		}

		return "atyp";
	}

	/**
	 * Erstellt einen neuen Datentyp und verweist auf die Übersichtsseite.
	 * 
	 * @param name
	 *            die Bezeichnung vom neuen Typ
	 * @param column
	 *            die Spaltenreferenz für den neuen Typ
	 * @return der Redirect zur Übersichtsseite
	 */
	@RequestMapping(value = "/admin/atyp", method = RequestMethod.POST)
	String create(@RequestParam("name") String name, @RequestParam("column") String column) {
		Atyp x = new Atyp();
		x.setName(name);
		x.setColumn(column);

		HttpEntity<Atyp> request = new HttpEntity<>(x);

		String otypResourceUrl = "http://localhost:8889/atyp";
		restTemplate.postForEntity(otypResourceUrl, request, Otyp.class);

		return REDIRECT;
	}

	/**
	 * Bearbeitet einen Datentyp.
	 * 
	 * @param id
	 *            die ID vom Datentyp, der bearbeitet werden soll
	 * @param name
	 *            die neue Bezeichnung
	 * @return der Redirect zur Übersichtsseite
	 */
	@RequestMapping(value = "/admin/atyp/{id}", method = RequestMethod.POST)
	String update(@PathVariable("id") int id, @RequestParam("editName") String name) {
		Atyp x = new Atyp(id, name);

		String url = WebUtil.REST_SERVER_URL + "/atyp/" + id;
		restTemplate.put(url, new HttpEntity<>(x));

		return REDIRECT;
	}

}
