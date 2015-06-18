$(document).ready(function(){
	var headers = { 'Content-Type': 'application/json'};
 	$.ajax({
		url: 'http://localhost:8080/DataMartWeb/datamart/getmarts',
		type: 'GET',
		contentType: 'application/json',
		timeout: 5000,
		success: function(data) {
			for (var i = 0; i < data.length; i++) {
				var options = '<option value=' + data[i].mart_id +'>' + data[i].mart_name +'</option>';
				$('#selectMart').append(options);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('error ' + textStatus + " " + errorThrown);
		}
	});
});