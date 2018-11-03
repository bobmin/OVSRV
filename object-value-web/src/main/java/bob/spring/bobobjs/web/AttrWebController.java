package bob.spring.bobobjs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Atyp;
import bob.burobjs.model.Otyp;

@Controller
public class AttrWebController {

	private static final String REDIRECT = "redirect:/admin/attr";

	/** der Logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/admin/attr")
	String findAll(@RequestParam(value = "edit", required = false) Integer editAttrId, Model model) {
		// alle Eigenschaften
		Attr[] attrArray = WebUtil.create(restTemplate).findAllAttr();
		model.addAttribute("elements", attrArray);
		// alle Datentypen
		Atyp[] atypArray = WebUtil.create(restTemplate).findAllAtyp();
		model.addAttribute("atypArray", atypArray);
		// alle Objekttypen (für Lookup)
		Otyp[] otypArray = WebUtil.create(restTemplate).findAllOtyp();
		model.addAttribute("otypArray", otypArray);
		// optional eine Eigenschaft zur Bearbeitung laden
		if (null != editAttrId) {
			int id = editAttrId.intValue();
			Attr attr = WebUtil.create(restTemplate).findAttr(id);
			model.addAttribute("editAttr", attr);
			log.info("attr is loaded, id = {}", id);
		}
		return "attr";
	}

	@PostMapping("/admin/attr")
	String create(@RequestParam("attrName") String attrName, @RequestParam("atypId") int atypId,
			@RequestParam(value = "otypId", defaultValue = "-1") int otypId) {

		WebUtil util = WebUtil.create(restTemplate);

		Atyp atyp = util.findAtyp(atypId);
		Attr x = new Attr(atyp, attrName);
		if (Atyp.VAL_OBJECT.equals(atyp.getColumn())) {
			if (1 > otypId) {
				throw new IllegalStateException("lookup not found");
			}
			Otyp otyp = util.findOtyp(otypId);
			x.setLookup(otyp);
		}

		log.info("insert: " + x);

		String url = WebUtil.REST_SERVER_URL + "/attr";
		restTemplate.postForEntity(url, new HttpEntity<>(x), Attr.class);

		return REDIRECT;
	}

	@PostMapping("/admin/attr/{id}")
	String update(@PathVariable("id") int id, @RequestParam("editAttrName") String name) {
		Attr attr = WebUtil.create(restTemplate).findAttr(id);
		attr.setName(name);

		String url = WebUtil.REST_SERVER_URL + "/attr/" + id;
		restTemplate.put(url, new HttpEntity<>(attr));

		return REDIRECT;
	}

}
