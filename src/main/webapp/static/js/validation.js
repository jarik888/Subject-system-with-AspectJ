
//'form[id="p_form"] [id="country"]'
//$(f + '[id="country"]')

function validatePersonForm(f) {

	validatePerson(f);

	if ($(f + '[id="customer"]').prop("checked") == true) {
		validateCustomer(f);
	}

	validateAddresses(f);
	validateContacts(f);

	if (!formContainsErrors(f)) {
		// alert($("#p_form").serialize());
		$("#p_form").submit();
	}
	
}

function validateEnterpriseForm(f) {

	var name = '[id="name"]';
	var full_name = '[id="full_name"]';

	// ------- Enterprise attributes ------

	isEmpty(name, f);
	isEmpty(full_name, f);
	
	if ($(f + '[id="customer"]').prop("checked") == true ||
		$(f + '[id="customer_enterprise"]').prop("checked") == true) {
		validateCustomer(f);
	}
	
	validateAddresses(f);
	validateContacts(f);


	if (!formContainsErrors(f)) {
		$("#e_form").submit();
	}
}

function validateEmployeeForm(f) {

	validatePerson(f);

	var enterprise = '[id="enterprise"]';
	var employee_role_type = '[id="employee_role_type"]';
	var employee_relation_type = '[id="employee_relation_type"]';

	var salary = '[id="salary"]';
	if (isNotEmpty(salary, f)) positiveNumber(salary, f);

	isSelected(enterprise, f);
	isSelected(employee_role_type, f);
	isSelected(employee_relation_type, f);

	validateAddresses(f);
	validateContacts(f);
	validateAccount(f);

	if (!formContainsErrors(f)) {
		$("#emp_form").submit();
	}
}

function validatePerson(f) {

	var first_name = '[id="first_name"]';
	var last_name = '[id="last_name"]';
	var identity_code = '[id="identity_code"]';
	var birthdate = '[id="birthdate"]';/*data*/

	isEmpty(first_name, f);
	isEmpty(last_name, f);
	isEmpty(identity_code, f);
	if (!isEmpty(birthdate, f)) validData(birthdate, f);

}

function validateCustomer(f) {

	var client_since = '[id="client_since"]';/*data*/
	var purchase_count = '[id="purchase_count"]';/*>=0*/
	var last_buy = '[id="last_buy"]';/*data*/
	var first_buy = '[id="first_buy"]';/*data*/
	var allahindluse = '[id="allahindluse__"]';/*0-100*/

	if (isNotEmpty(client_since, f)) validData(client_since, f);
	if (isNotEmpty(purchase_count, f)) positiveNumber(purchase_count, f);
	if (isNotEmpty(last_buy, f)) validData(last_buy, f);
	if (isNotEmpty(first_buy, f)) validData(first_buy, f);
	if (isNotEmpty(allahindluse, f)) validateProtsent(allahindluse, f);
}

//--------------------------------------------ADDRESSES----------------------------------------
function validateAddresses(f) {
	//'form[id="p_form"] [id="country"]'
	validateAddressFrom(['[id="country"]', '[id="county"]', //Main address
	                     '[id="town_village"]', '[id="street_address"]', '[id="zipcode"]'], f);

	var counter = 1;
	while ($(f + '[id="country' + counter.toString() + '"]').val() != undefined) { //additional addresses		
		validateAddressFrom([
				'[id="country' + counter.toString() + '"]',
				'[id="county' + counter.toString() + '"]',
				'[id="town_village' + counter.toString() + '"]',
				'[id="street_address' + counter.toString() + '"]',
				'[id="zipcode' + counter.toString() + '"]',
				], f);
		counter++;
	}
}

function validateAddressFrom(addressFields, f) {
	for (var i = 0; i < addressFields.length; i++) {
		isEmpty(addressFields[i], f);
	}
}

//---------------------------------------------CONTACTS-----------------------------------------
function validateContacts(f) {

	validateContactForm(['[id="contact_type"]', '[id="contact"]'], f);

	var counter = 1;
	while ($(f + '[id="contact_type' + counter.toString() + '"]').val() != undefined) { //additional contact		
		validateContactForm([
							'[id="contact_type' + counter.toString() + '"]',
							'[id="contact' + counter.toString() + '"]',
							], f);
		counter++;
	}

}

function validateContactForm(contactFields, f) {
	// contact_type, contact, note
	var contact_type = $(f + contactFields[0]).val();
	var contactF = $(f + contactFields[1]);

	if (!isEmpty(contactFields[1], f) && contact_type == 1) { //Email
		var eMailRegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    if (!eMailRegExp.test(contactF.val())) {
	      	$(f + '[id="' + contactF.attr('id') + '_error"]').html("Wrong email format!\n(email@example.com)");
	    } else {
	      	$(f + '[id="' + contactF.attr('id') + '_error"]').html("");
	    }
	} else if (!isEmpty(contactFields[1], f) && contact_type == 2) { //Phone
		var phoneRegExp = /^[0-9+#* ]+$/;
		if (!phoneRegExp.test(contactF.val())) {
	      	$(f + '[id="' + contactF.attr('id') + '_error"]').html("Wrong phone format!");
	    } else {
	      	$(f + '[id="' + contactF.attr('id') + '_error"]').html("");
	    }
	}
}

//---------------------------------------------ACCOUNT-----------------------------------------
function validateAccount(f) {

	var username = '[id="username"]'; /*exists?*/
	var password = '[id="password"]';
	var valid_from = '[id="valid_from"]'; /*date*/
	var valid_to = '[id="valid_to"]'; /*date*/

	var edit_mode = $(f + username).prop("disabled");

	if (!edit_mode) { //create mode
		if (!isEmpty(username, f)) validUsername(username, f); /*ajax request*/
		isEmpty(password, f);
	}
	
	if (!isEmpty(valid_from, f)) validData(valid_from, f);
	if (!isEmpty(valid_to, f)) validData(valid_to, f);

}

function validUsername(id, f) {

	var el = $(f + id);

	var data = { uname : el.val() };	

	$.ajax({
        url: 's?service=subject&action=check_username',
        data: data,
        success: function(result) {
                    if(result == true) {
                       	$(f + '[id="' + el.attr('id') + '_error"]').html("Username already exists!");
                    }
        },
        dataType: 'json',
        async: false
   	});
}

//---------------------------------------------ERRORS-CHECK------------------------------------
function formContainsErrors(f) {
	var errorFields = $(f + '[class="error"]');
	for (var i = 0; i < errorFields.length; i++) {
		var field = errorFields[i].innerHTML;
		if (field != "") {
			return true;
		}
	}	
}

function isEmpty(id, f) {
	var el = $(f + id);
  	if (el.val().trim() == "") {
    	$(f + '[id="' + el.attr('id') + '_error"]').html("Cannot be blank!");
    	return true;
  	} else {
    	$(f + '[id="' + el.attr('id') + '_error"]').html("");
    	return false;
  	}
}

function isNotEmpty(id, f) {
	var el = $(f + id);
  	if (el.val().trim() == "") {
    	return false;
  	} else {
    	return true;
  	}
}

function isSelected(id, f) {
	var el = $(f + id);
	if (el.val() == "empty") {
    	$(f + '[id="' + el.attr('id') + '_error"]').html("Please select value!");
    	return false;
  	} else {
    	$(f + '[id="' + el.attr('id') + '_error"]').html("");
    	return true;
  	}
}

//----------------------------------------RULES---------------------------------------
function validData(id, f) {
	var dataRegExp = /^(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\d\d$/;
	var el = $(f + id);	
	if (el.val().match(dataRegExp)) {
		$(f + '[id="' + el.attr('id') + '_error"]').html("");
	} else {
		$(f + '[id="' + el.attr('id') + '_error"]').html("Not match: dd.mm.yyyy dd-mm-yyyy dd/mm/yyyy");
	}
}

function positiveNumber(id, f) {
	var percentRegExp = /^[0-9]+$/;
	var el = $(f + id);
	if (el.val().match(percentRegExp)) {		
		$(f + '[id="' + el.attr('id') + '_error"]').html("");
	} else {
		$(f + '[id="' + el.attr('id') + '_error"]').html("Enter only positive integer numbers!");
	}
}

function validateProtsent(id, f) {
	var percentRegExp = /^([0-9]|[0-9][0-9]|100)$/;
	var el = $(f + id);
	if (el.val().match(percentRegExp)) {		
		$(f + '[id="' + el.attr('id') + '_error"]').html("");
	} else {
		$(f + '[id="' + el.attr('id') + '_error"]').html("% can only be integer between 0 and 100");
	}
}
