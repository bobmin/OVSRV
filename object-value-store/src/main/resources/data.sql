-- Objekttypen
INSERT INTO bob_object_type (otyp_id, otyp_name, otyp_description) VALUES (1, 'Fahrzeug', 'vom Mensch geschaffenes Transportmittel');
INSERT INTO bob_object_type (otyp_id, otyp_name, otyp_description) VALUES (2, 'Pfanze', 'von der Natur geschaffene Lebewesen');
INSERT INTO bob_object_type (otyp_id, otyp_name, otyp_description) VALUES (3, 'Option', 'einfache Ja/Nein-Entscheidungen');

-- Attribute
INSERT INTO bob_attribute_type (atyp_id, atyp_name, atyp_column) VALUES (1, 'ein Text', 'VAL_STRING');
INSERT INTO bob_attribute_type (atyp_id, atyp_name, atyp_column) VALUES (2, 'eine Zahl', 'VAL_NUMBER');
INSERT INTO bob_attribute_type (atyp_id, atyp_name, atyp_column) VALUES (3, 'eine Auswahl', 'VAL_OBJECT');

INSERT INTO bob_attribute (attr_id, attr_atyp_id, attr_name) VALUES (0, 1, 'Bezeichnung');
INSERT INTO bob_attribute (attr_id, attr_atyp_id, attr_name) VALUES (1, 2, 'Anzahl Räder');
INSERT INTO bob_attribute (attr_id, attr_atyp_id, attr_name, attr_lookup_otyp_id) VALUES (2, 3, 'Umweltbelastung', 3);

INSERT INTO bob_form (form_id, form_name) VALUES (1, 'Fahrzeugwerte');
INSERT INTO bob_form (form_id, form_name) VALUES (2, 'Umweltwerte');

-- Formular + Attribut
INSERT INTO bob_form_attribute (fa_form_id, fa_attr_id) VALUES (1, 0);
INSERT INTO bob_form_attribute (fa_form_id, fa_attr_id) VALUES (1, 1);
INSERT INTO bob_form_attribute (fa_form_id, fa_attr_id) VALUES (2, 2);

INSERT INTO bob_object_type_form (of_otyp_id, of_form_id) VALUES (1, 1);
INSERT INTO bob_object_type_form (of_otyp_id, of_form_id) VALUES (1, 2);

-- Objekte
INSERT INTO bob_object (obj_id, obj_otyp_id) VALUES (1, 1);
INSERT INTO bob_object (obj_id, obj_otyp_id) VALUES (2, 1);
INSERT INTO bob_object (obj_id, obj_otyp_id) VALUES (3, 3);
INSERT INTO bob_object (obj_id, obj_otyp_id) VALUES (4, 3);

INSERT INTO bob_value (val_obj_id, val_attr_id, val_string) VALUES (1, 0, 'Fahrrad');
INSERT INTO bob_value (val_obj_id, val_attr_id, val_number) VALUES (1, 1, 2);
INSERT INTO bob_value (val_obj_id, val_attr_id, val_string) VALUES (2, 0, 'Auto');
INSERT INTO bob_value (val_obj_id, val_attr_id, val_number) VALUES (2, 1, 4);
INSERT INTO bob_value (val_obj_id, val_attr_id, val_string) VALUES (3, 0, 'Ja');
INSERT INTO bob_value (val_obj_id, val_attr_id, val_string) VALUES (4, 0, 'Nein');
