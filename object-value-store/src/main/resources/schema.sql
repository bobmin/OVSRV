CREATE TABLE bob_object_type (
	otyp_id          INT     PRIMARY KEY auto_increment,
    otyp_name        VARCHAR UNIQUE,
    otyp_description VARCHAR
);

CREATE TABLE bob_attribute_type (
	atyp_id     INT         PRIMARY KEY auto_increment,
    atyp_name   VARCHAR     UNIQUE,
    atyp_column VARCHAR(10)
);

CREATE TABLE bob_attribute (
	attr_id      		INT     	NOT NULL	PRIMARY KEY auto_increment,
    attr_name    		VARCHAR		NOT NULL,
	attr_atyp_id 		INT     	NOT NULL	REFERENCES bob_attribute_type(atyp_id),
    attr_lookup_otyp_id	INT			NULL		REFERENCES bob_object_type(otyp_id),
    CONSTRAINT uq_attr_atyp_id_attr_name UNIQUE (attr_atyp_id, attr_name)
);

CREATE TABLE bob_form (
	form_id     INT     PRIMARY KEY auto_increment,
    form_name   VARCHAR UNIQUE,
);

CREATE TABLE bob_form_attribute (
	fa_form_id INT,
    fa_attr_id INT
);

CREATE TABLE bob_object_type_form (
	of_otyp_id	INT,
	of_form_id	INT,
	CONSTRAINT uq_of_otyp_id_of_form_id UNIQUE (of_otyp_id, of_form_id)
);

CREATE TABLE bob_object (
	obj_id      BIGINT PRIMARY KEY auto_increment,
    obj_otyp_id INT
);

CREATE TABLE bob_value (
	val_obj_id  BIGINT,
    val_attr_id INT,
    val_string  VARCHAR,
    val_number  NUMERIC,  
    val_object  BIGINT,
    val_binary  BLOB
);

CREATE TABLE object_relation (
	orel_parent_obj_id BIGINT,
    orel_child_obj_id  BIGINT
);

CREATE VIEW bob_obj_form AS 
SELECT obj_id, of_form_id as form_id, attr_id, attr_name
, atyp_id, atyp_name, atyp_column
, val_string, val_number, val_object 
, attr_lookup_otyp_id as lookup_otyp_id, otyp_name as lookup_otyp_name
FROM bob_object 
INNER JOIN bob_object_type_form ON obj_otyp_id  = of_otyp_id 
INNER JOIN bob_form_attribute   ON of_form_id   = fa_form_id 
INNER JOIN bob_attribute        ON fa_attr_id   = attr_id 
INNER JOIN bob_attribute_type   ON attr_atyp_id = atyp_id 
LEFT JOIN bob_value            	ON fa_attr_id   = val_attr_id AND val_obj_id = obj_id
LEFT JOIN bob_object_type 		ON attr_lookup_otyp_id = otyp_id;

CREATE VIEW bob_attr AS
SELECT attr_id, attr_name
, attr_atyp_id as atyp_id, atyp_name, atyp_column 
, attr_lookup_otyp_id, otyp_name as attr_lookup_otyp_name
FROM bob_attribute 
INNER JOIN bob_attribute_type ON attr_atyp_id = atyp_id
LEFT JOIN bob_object_type ON attr_lookup_otyp_id = otyp_id;

CREATE VIEW bob_val AS
SELECT val_obj_id as obj_id
, val_attr_id as attr_id, attr_name
, atyp_id, atyp_name, atyp_column
, val_string, val_number, val_object 
FROM bob_value 
INNER JOIN bob_attribute ON val_attr_id = attr_id 
INNER JOIN bob_attribute_type ON attr_atyp_id = atyp_id;
