package bob.spring.bobobjs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import bob.burobjs.model.Otyp;

@Controller
public class OtypWebController {

	private static final String REDIRECT = "redirect:/admin/otyp";

	/** der Logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/admin/otyp")
	String findAll(@RequestParam(value = "edit", required = false) Integer editOtypId, Model model) {
		// alle Typen laden
		Otyp[] x = WebUtil.create(restTemplate).findAllOtyp();
		model.addAttribute("elements", x);

		// optional einen Typ zur Bearbeitung ergänzen
		if (null != editOtypId) {
			int id = editOtypId.intValue();
			Otyp otyp = WebUtil.create(restTemplate).findOtyp(id);
			model.addAttribute("editOtyp", otyp);
			log.info("otyp is loaded, id = {}", id);
		}

		return "otyp";
	}

	@RequestMapping(value = "/admin/otyp", method = RequestMethod.POST)
	String createOtyp(@RequestParam("name") String name, @RequestParam("description") String description) {
		Otyp x = new Otyp();
		x.setName(name);
		x.setDescription(description);

		HttpEntity<Otyp> request = new HttpEntity<>(x);

		String otypResourceUrl = "http://localhost:8889/otyp";
		restTemplate.postForEntity(otypResourceUrl, request, Otyp.class);

		return REDIRECT;
	}

	// @RequestMapping("/admin/otyp/{otypId}")
	// String find(Model model, @PathVariable("otypId") int otypId) {
	// Otyp x = WebUtil.create(restTemplate).findOtyp(otypId);
	// model.addAttribute("x", x);
	// return "of";
	// }

	@RequestMapping(value = "/admin/otyp/{id}", method = RequestMethod.POST)
	String update(@PathVariable("id") int id, @RequestParam("editName") String name,
			@RequestParam("editDescription") String desc) {
		Otyp otyp = WebUtil.create(restTemplate).findOtyp(id);
		otyp.setName(name);
		otyp.setDescription(desc);

		String url = WebUtil.REST_SERVER_URL + "/otyp/" + id;
		restTemplate.put(url, new HttpEntity<>(otyp));

		return REDIRECT;
	}

}
