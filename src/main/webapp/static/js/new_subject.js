$(document).ready(function() {
	
	$("select[name=subject_type]").on('change', function() {

		$(this).parent().find("div.form").css("display", "none");
		if ($(this).val() == 1) {
			$("#person_form").css("display", "block");
		} else if ($(this).val() == 2) {
			$("#enterprise_form").css("display", "block");
		} else if ($(this).val() == 3) {
			$("#employee_form").css("display", "block");
		}		
	});
	//customer_attribute
	//.checked
	$("#customer").change(function() {
    if(this.checked) {
        $("#customer_attribute").show();
    } else {
    	$("#customer_attribute").hide();
    }
    
	});
	
	$("#customer_enterprise").change(function() {
	    if(this.checked) {
	        $("#customer_enterprise_attribute").show();
	    } else {
	    	$("#customer_enterprise_attribute").hide();
	    }
	    
	});
	
});