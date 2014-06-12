$(document).ready(function() {
	
	$('a.additional_address').on('click', function(event) {
		event.preventDefault();
		var $addrNumInput = $('input[name="addr_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">'
+ '<input type="hidden" name="address_type_fk" value="2" />'
+ '<input type="hidden" name="address_id'+(num + 1)+'" />'
+ '---------- Additional address #' + (num + 1) + ' ---------</td></tr>'
+ '<tr><th>Country</th><td><input type="text" name="country'+(num + 1)+'" id="country'+(num + 1)+'" /></td></tr>'
+ '<tr><td class="error" id="country'+(num + 1)+'_error" colspan="2"></td></tr>'
+ '<tr><th>County</th><td><input type="text" name="county'+(num + 1)+'" id="county'+(num + 1)+'" /></td></tr>'
+ '<tr><td class="error" id="county'+(num + 1)+'_error" colspan="2"></td></tr>'
+ '<tr><th>Town/village</th><td><input type="text" name="town_village'+(num + 1)+'" id="town_village'+(num + 1)+'" /></td></tr>'
+ '<tr><td class="error" id="town_village'+(num + 1)+'_error" colspan="2"></td></tr>'
+ '<tr><th>Street address</th><td><input type="text" name="street_address'+(num + 1)+'" id="street_address'+(num + 1)+'" /></td></tr>'
+ '<tr><td class="error" id="street_address'+(num + 1)+'_error" colspan="2"></td></tr>'
+ '<tr><th>ZipCode</th><td><input type="text" name="zipcode'+(num + 1)+'" id="zipcode'+(num + 1)+'" /></td></tr>'
+ '<tr><td class="error" id="zipcode'+(num + 1)+'_error" colspan="2"></td></tr>'));
		$addrNumInput.val(num);
	});
	
	$('a.additional_contact').on('click', function(event) {
		event.preventDefault();
		var $addrNumInput = $('input[name="cont_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">'
+ '---------------  #' + (num + 1) + ' --------------<input type="hidden"'
+ ' name="contact_id'+num+'" /><input type="hidden" name="contact_orderby'+num+'" value="'
+ (num + 1) + '" /></td></tr>'
+ '<tr><th>Type</th><td><select id="contact_type'+num+'" name="contact_type'+num+'"><option value="1">Email</option>'
+ '<option value="2">Phone number</option></select></td></tr>'
+ '<tr><th>Contact</th><td><input type="text" id="contact'+num+'" name="contact'+num+'" /></td></tr>'
+ '<tr><td class="error" id="contact'+num+'_error" colspan="2"></td></tr>'
+ '<tr><th>Note</th><td><input type="text" name="note'+num+'" /></td></tr>'));
		$addrNumInput.val(num);
	});

	$(document).on('click', 'a[name="deleteAddress"]', function(event) {
		event.preventDefault();
		$this = $(this);
		if (confirm("Delete this address?")) {
			var data = {
					addressId : $(this).attr('id')
					};
			$.post('s?service=subject&action=delete_address', data, function(answer) {
				if (answer == true) {
					$parentTr = $this.parents('tr');
					for (var i = 0; i < 11; i++) {
						$parentTr.prev().remove();
					}
					$parentTr.remove();
				} else {
					alert("Error: Address is not deleted.");
				}
				
			}, 'json').fail(function(answer) {
				alert("Server Error!");
			});
		}
	});
	
	$(document).on('click', 'a[name="deleteContact"]', function(event) {
		event.preventDefault();
		$this = $(this);
		if (confirm("Delete this contact?")) {
			var data = {
					contactId : $(this).attr('id')
					};
			$.post('s?service=subject&action=delete_contact', data, function(answer) {
				if (answer == true) {
					$parentTr = $this.parents('tr');
					for (var i = 0; i < 5; i++) {
						$parentTr.prev().remove();
					}
					$parentTr.remove();
				} else {
					alert("Error: Contact is not deleted.");
				}
				
			}, 'json').fail(function(answer) {
				alert("Server Error!");
			});
		}
	});
});