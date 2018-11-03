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

import bob.burobjs.model.Attr;
import bob.burobjs.model.Fa;
import bob.burobjs.model.Form;
import bob.burobjs.model.Of;
import bob.burobjs.model.Otyp;

@Controller
public class FormWebController {

	private static final String REDIRECT = "redirect:/admin/form";

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 
	 * @param editFormId
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/form")
	public String findAll(@RequestParam(value = "edit", required = false) Integer editFormId, Model model) {
		// alle Formulare laden
		String url = WebUtil.REST_SERVER_URL + "/form";
		ResponseEntity<Form[]> response = restTemplate.getForEntity(url, Form[].class);
		Form[] x = response.getBody();
		model.addAttribute("elements", x);

		// optional: ein Formular zur Bearbeitung ergänzen
		if (null != editFormId) {
			String formUrl = WebUtil.REST_SERVER_URL + "/form/" + editFormId.intValue();
			ResponseEntity<Form> formResponse = restTemplate.getForEntity(formUrl, Form.class);
			Form form = formResponse.getBody();
			model.addAttribute("editForm", form);
		}

		return "form";
	}

	@RequestMapping(value = "/admin/form", method = RequestMethod.POST)
	public String create(@RequestParam("newName") String name) {
		Form x = new Form();
		x.setName(name);

		HttpEntity<Form> request = new HttpEntity<>(x);

		String url = WebUtil.REST_SERVER_URL + "/form";
		restTemplate.postForEntity(url, request, Form.class);

		return REDIRECT;
	}

	@RequestMapping(value = "/admin/form/{id}", method = RequestMethod.POST)
	public String update(@PathVariable("id") int id, @RequestParam("editName") String name) {
		Form x = new Form();
		x.setId(id);
		x.setName(name);

		HttpEntity<Form> request = new HttpEntity<>(x);

		String url = WebUtil.REST_SERVER_URL + "/form/" + id;
		restTemplate.put(url, request);

		return REDIRECT;
	}

	// -----------------------------------------------------------------------

	/**
	 * Attribute vom Formular bearbeiten.
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/form/{id}/attr")
	public String findAttr(@PathVariable("id") int id, Model model) {
		// das Formular
		pushForm(id, model);
		// die Attribute
		String url = WebUtil.REST_SERVER_URL + "/form/" + id + "/attr";
		ResponseEntity<Fa[]> response = restTemplate.getForEntity(url, Fa[].class);
		Fa[] x = response.getBody();
		model.addAttribute("elements", x);
		// Eigenschaften für Auswahl zur Erweiterung
		Attr[] attrArray = WebUtil.create(restTemplate).findAllAttr();
		model.addAttribute("attrArray", attrArray);
		// Sonstiges
		model.addAttribute("formId", id);
		return "fa";
	}

	@RequestMapping(value = "/admin/form/{id}/attr", method = RequestMethod.POST)
	public String doItAttr(@PathVariable("id") int formId, @RequestParam("attrId") int attrId,
			@RequestParam("attrTask") String attrTask) {
		if ("INSERT".equals(attrTask)) {
			// Neuanlage
			Attr attr = WebUtil.create(restTemplate).findAttr(attrId);
			String url = WebUtil.REST_SERVER_URL + "/form/" + formId + "/attr";
			restTemplate.postForEntity(url, new HttpEntity<>(attr), Fa.class);
		} else if ("DELETE".equals(attrTask)) {
			// Löschung
			String url = WebUtil.REST_SERVER_URL + "/form/" + formId + "/attr/" + attrId;
			restTemplate.delete(url);
		} else {
			// Fehler: Aufgabe unbekannt
		}

		return String.format("redirect:/admin/form/%d/attr", formId);
	}

	// -----------------------------------------------------------------------

	@RequestMapping("/admin/form/{id}/otyp")
	public String findOtyp(@PathVariable("id") int id, Model model) {
		// das Formular
		pushForm(id, model);
		// die Objekttypen
		String url = WebUtil.REST_SERVER_URL + "/form/" + id + "/otyp";
		ResponseEntity<Of[]> response = restTemplate.getForEntity(url, Of[].class);
		Of[] x = response.getBody();
		model.addAttribute("elements", x);
		// Eigenschaften für Auswahl zur Erweiterung
		Otyp[] otypArray = WebUtil.create(restTemplate).findAllOtyp();
		model.addAttribute("otypArray", otypArray);
		return "of";
	}

	@RequestMapping(value = "/admin/form/{id}/otyp", method = RequestMethod.POST)
	public String doItOtyp(@PathVariable("id") int formId, @RequestParam("otypId") int otypId,
			@RequestParam("otypTask") String otypTask) {
		if ("INSERT".equals(otypTask)) {
			// Neuanlage
			Otyp otyp = WebUtil.create(restTemplate).findOtyp(otypId);
			String url = WebUtil.REST_SERVER_URL + "/form/" + formId + "/otyp";
			restTemplate.postForEntity(url, new HttpEntity<>(otyp), Of.class);
		} else if ("DELETE".equals(otypTask)) {
			// Löschung
			String url = WebUtil.REST_SERVER_URL + "/form/" + formId + "/otyp/" + otypId;
			restTemplate.delete(url);
		} else {
			// Fehler: Aufgabe unbekannt
		}

		return String.format("redirect:/admin/form/%d/otyp", formId);
	}

	// -----------------------------------------------------------------------

	private void pushForm(int id, Model model) {
		String formUrl = WebUtil.REST_SERVER_URL + "/form/" + id;
		ResponseEntity<Form> formResponse = restTemplate.getForEntity(formUrl, Form.class);
		Form form = formResponse.getBody();
		model.addAttribute("xForm", form);
	}

}
