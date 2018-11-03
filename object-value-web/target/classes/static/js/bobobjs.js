function formObjAbort() {
	$( '#formObj' ).submit();
};

function gotoEntity(name, id, sub = null) {
	var newLocation = '/admin/' + name;
	if (id != -1) {
		newLocation += '/' + id;
	}
	if (sub != null) {
		newLocation += '/' + sub;
	}
	window.location.replace(newLocation);
};

function gotoListEdit(name, id) {
	window.location.replace('/admin/' + name + '?edit=' + id);
};

function deleteFa(formId, attrId) {
	$.confirm({
	    title: 'Verkn&uuml;pfung l&ouml;schen',
	    content: 'Die Verknüpfung zwischen Attribut und Formular l&ouml;schen?',
	    buttons: {
	        confirm: {
	        	text: 'L&ouml;schen',
	            btnClass: 'btn-red',
	            keys: ['enter', 'shift'],
	        	action: function () {
	            	console.log("delete: form=" + formId + ", attrId=" + attrId);
	            	document.doItFa.attrId.value=attrId;
	            	document.doItFa.attrTask.value="DELETE";
	            	document.doItFa.submit();
	        	}
	        },
	        cancel: {
	        	text: 'Abbrechen'
	        }
	    }
	});
};

function deleteOf(formId, otypId) {
	$.confirm({
		title: 'Verkn&uuml;pfung l&ouml;schen',
		content: 'Die Verknüpfung zwischen Objekttyp und Formular l&ouml;schen?',
		buttons: {
			confirm: {
				text: 'L&ouml;schen',
				btnClass: 'btn-red',
				keys: ['enter', 'shift'],
				action: function () {
					console.log("delete: form=" + formId + ", otypId=" + otypId);
					document.doItOf.otypId.value=otypId;
					document.doItOf.otypTask.value="DELETE";
					document.doItOf.submit();
				}
			},
			cancel: {
				text: 'Abbrechen'
			}
		}
	});
};

function deleteObj(objId) {
	$.confirm({
	    title: 'Objekt l&ouml;schen',
	    content: 'Das Objekt und seine Eigenschaften werden unwiederruflich aus der Datenbank entfernt.',
	    buttons: {
	        confirm: {
	        	text: 'L&ouml;schen',
	            btnClass: 'btn-red',
	            keys: ['enter', 'shift'],
	        	function () {
	            	console.log("delete: objId=" + objId);
					document.deleteObj.objId.value=objId;
					document.deleteObj.submit();
	        	}
	        },
	        cancel: {
	        	text: 'Abbrechen'
	        }
	    }
	});
};

function changeAtyp() {
	var atypId = document.insertAtyp.atypId.value;
	if (atypId == 3) {
		document.insertAtyp.otypId.disabled=false;		
	} else {
		document.insertAtyp.otypId.disabled=true;
	}
};
