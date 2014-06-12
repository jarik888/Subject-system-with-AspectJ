$(function() {
	
	var $select = $('select#subject_type');
	
	$select.on('change', function() {
		if ($select.val() == 1) {
			$("#person_attributes_table").show();
			$("#enterprise_attributes_table").hide();
			$("#employee_attributes_table").hide();
			$("#customer_attributes_table").hide();
		} else if ($select.val() == 2) {
			$("#person_attributes_table").hide();
			$("#enterprise_attributes_table").show();
			$("#employee_attributes_table").hide();
			$("#customer_attributes_table").hide();
		} else if ($select.val() == 3) {
			$("#person_attributes_table").hide();
			$("#enterprise_attributes_table").hide();
			$("#employee_attributes_table").show();
			$("#customer_attributes_table").hide();
		} else if ($select.val() == 4) {
			$("#person_attributes_table").hide();
			$("#enterprise_attributes_table").hide();
			$("#employee_attributes_table").hide();
			$("#customer_attributes_table").show();
		}
	});

	$(document).on('click', 'button[name="deleteSubject"]', function(event) {
		event.preventDefault();
		$this = $(this);
		if (confirm("Delete this subject?")) {
			var data = {
					subjectId : $(this).attr('id'),
					subjectType : $('select#subject_type').val()
					};
			$.post('s?service=subject&action=delete_subject', data, function(answer) {
				if (answer == true) {
					$this.parents('tr').remove();
				} else {
					alert("Enterprise is related with employees, please delete all of them before");
				}
				
			}, 'json').fail(function(answer) {
				alert("Server Error!");
			});
		}
	});
	
});


function searchSubject() {
	var subjectType = $('select#subject_type').val();
	var answerField = $("#answer");
	var data = {
		subject_type: subjectType
	};
	var args = $("#s_form").serialize();

	$.post('s?service=subject&action=search_subject&' + args, data, function(answer) {
		
		answerField.html(answer);
				
	}, 'html');

}
