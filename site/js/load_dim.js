$(document).ready(function(){
	
	$("#selectMart").on("change", function(){
		var martid = $('#selectMart').val();
		if(martid){
			$("#selectDim").empty();
			$("#selectDim").append('<option value="">Select Mart</option>');
			var headers = { 'Content-Type': 'application/json'};
		 	$.ajax({
				url: 'http://localhost:8080/DataMartWeb/datamart/getdims?mart='+martid,
				type: 'GET',
				contentType: 'application/json',
				timeout: 5000,
				success: function(data) {
					for (var i = 0; i < data.length; i++) {
						var options = '<option value=' + data[i].dim_id +'>' + data[i].dim_name +'</option>';
						$('#selectDim').append(options);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('error ' + textStatus + " " + errorThrown);
				}
			});
		}
	});


	


});