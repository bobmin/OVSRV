package bob.spring.bobobjs.web;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import bob.burobjs.model.Attr;
import bob.burobjs.model.Ed;
import bob.burobjs.model.Form;
import bob.burobjs.model.Obj;
import bob.burobjs.model.Otyp;
import bob.burobjs.model.Val;

/**
 * Ermöglicht die Objektbearbeitung.
 * 
 * @author maik@btmx.net
 *
 */
@Controller
public class ObjWebController {

	/** die Konstante für den Rücksprung zur Objektübersicht */
	private final static String REDIRECT = "redirect:/admin/obj";

	/** der Logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Liefert die gespeicherten Objekt.
	 * 
	 * @param response
	 *            die Responseparameter
	 * @return ein Templateschlüssel
	 */
	@GetMapping("/admin/obj")
	public String showAll(@RequestParam(value = "filter", required = false) String filter, Model response) {
		// Objekte
		Obj[] objArray = WebUtil.create(restTemplate).findAllObj(filter);
		response.addAttribute("xObjArray", objArray);
		response.addAttribute("filterValue", filter);
		// Objekttypen für Neuanlage
		Otyp[] otypArray = WebUtil.create(restTemplate).findAllOtyp();
		response.addAttribute("xOtypArray", otypArray);
		// ggf. Bearbeitung
		if (response.containsAttribute("editObjName")) {
			response.addAttribute("editObjName", response.asMap().get("editObjName"));
		}
		log.info("objects searched, filter = " + filter + ", size = " + objArray.length);
		return "obj";
	}

	/**
	 * Erstellt ein neues Objekt.
	 * 
	 * @param otypId
	 *            der Objekttyp
	 * @param objName
	 *            der Objektname
	 * @return ein Templateschlüssel
	 */
	@PostMapping("/admin/obj")
	public String create(@RequestParam("otypId") int otypId, @RequestParam("objName") String objName) {
		Otyp otyp = WebUtil.create(restTemplate).findOtyp(otypId);
		Obj x = new Obj(otyp, objName);

		String url = WebUtil.REST_SERVER_URL + "/obj";
		restTemplate.postForEntity(url, new HttpEntity<>(x), Obj.class);

		return REDIRECT;
	}

	/**
	 * Liefert die Objektwerte gruppiert nach Formular.
	 * 
	 * @param objId
	 *            die ID vom Objekt
	 * @param formId
	 *            der optionale Formularfilter
	 * @param model
	 *            die Responseparameter
	 * @return ein Templateschlüssel
	 */
	@GetMapping("/admin/obj/{objId}/attr")
	public String show(@PathVariable("objId") long objId,
			@RequestParam(value = "form", required = false) String formFilter, Model model) {

		// das Objekt
		String url = WebUtil.REST_SERVER_URL + "/obj/" + objId;
		ResponseEntity<Obj> response = restTemplate.getForEntity(url, Obj.class);
		Obj xObj = response.getBody();
		if (null == xObj) {
			throw new IllegalStateException("object not found, id = " + objId);
		}
		model.addAttribute("xObj", xObj);

		int otypId = xObj.getOtyp().getId();

		// die Editor'en
		List<Ed> xEditorList = new LinkedList<>();

		Form[] forms = WebUtil.create(restTemplate).findFormByOtyp(otypId);

		for (Form f : forms) {
			if (null == formFilter || formFilter.contains(String.valueOf(f.getId()))) {
				Ed e = new Ed();
				e.setObj(xObj);
				e.setForm(f);
				Val[] val = WebUtil.create(restTemplate).findAllVal(objId, f.getId());
				e.setVal(val);
				xEditorList.add(e);
			}
		}

		model.addAttribute("xEds", xEditorList);

		return "objx";

	}

	/**
	 * Empfängt die Formulardaten zur Objekt-Bearbeitung.
	 * 
	 * @see https://stackoverflow.com/questions/36872540/spring-boot-rest-service-form-too-large
	 * 
	 * @param objId
	 *            die ID vom Objekt
	 * @param params
	 *            die Formularparameter
	 * @param model
	 *            die Redirectparameter
	 * @return ein Redirect
	 */
	@PostMapping("/admin/obj/{objId}/attr")
	public RedirectView update(@PathVariable("objId") long objId, @RequestParam Map<String, String> params,
			RedirectAttributes model) {
		// Formular-ID + Werteliste
		Map<Integer, List<Val>> cache = new HashMap<>();
		// ...aufbauen
		for (String k : params.keySet()) {
			if (k.matches("val_[\\d]+_[\\d]+")) {
				String a[] = k.split("_");
				Integer formId = Integer.valueOf(a[1]);
				int attrId = Integer.parseInt(a[2]);

				if (!cache.containsKey(formId)) {
					cache.put(formId, new LinkedList<Val>());
				}

				Attr attr = WebUtil.create(restTemplate).findAttr(attrId);

				Val val = new Val();
				val.setAttr(attr);
				val.setValue(params.get(k));

				cache.get(formId).add(val);

				log.debug("you have to update the object: obj = " + objId + ", key = " + k + ", attr = " + attrId
						+ ", value = " + params.get(k));
			}
		}
		// ...absenden
		for (Integer formId : cache.keySet()) {
			String url = WebUtil.REST_SERVER_URL + "/obj/" + objId + "/form/" + formId.intValue();
			restTemplate.put(url, new HttpEntity<>(cache.get(formId)));
		}
		// zurück zur Übersicht
		model.addFlashAttribute("bob_header_message",
				String.format("Das Objekt %d wurde erfolgreich gespeichert.", objId));
		return new RedirectView("/admin/obj");
	}

}
