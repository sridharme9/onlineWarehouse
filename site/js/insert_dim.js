$(document).ready(function(){
	$("#selectDim").on("change", function(){
		var value = $('#selectDim option:selected').val();
		var tableName = $('#selectDim option:selected').text();
		$('#addAttr').empty();
		if(value){
			var headers = { 'Content-Type': 'application/json'};
		 	$.ajax({
				url: 'http://localhost:8080/DataMartWeb/datamart/getcols?tableName='+tableName,
				type: 'GET',
				contentType: 'application/json',
				timeout: 5000,
				success: function(data) {
					for (var i = 0; i < data.length; i++) {
						var element = '<input type="text" placeholder="Enter Value" pattern="^[a-zA-Z]+$" name="'+ data[i] +'" class="textMargin" required></div>';
						var label = '<div><label>'+ data[i] + '</label>'
						$('#addAttr').append(label + element);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('error ' + textStatus + " " + errorThrown);
				}
			});
		}
	});

	$('#btnSubmit').click(function(){
		var fields =  $("#regForm :input[type=text]").serializeArray();
		var mart = $('#selectMart').val();
		var dim_id = $('#selectDim').val();
		var dimTable = $('#selectDim option:selected').text();
		if(mart ==""){
			alert("Please Select Mart");
			return false;
		}
		if(dim_id ==""){
			alert("Please Select Dimension");
			return false;
		}
		var param = "";
		for (var i = 0; i < fields.length; i++) {
			param = param + fields[i].name + "=" + fields[i].value + "&";
				
		}
		param = param + "mart_id=" + mart + "&table_id=" + dim_id + "&dimTable=" + dimTable;
		var headers = { 'Content-Type': 'application/json'};
	 	$.ajax({
			url: 'http://localhost:8080/DataMartWeb/datamart/insertdim?'+param,
			type: 'GET',
			//data: stringdata,
			contentType: 'application/json',
			timeout: 5000,
			success: function(data) {
				alert(JSON.stringify(data));
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('error ' + textStatus + " " + errorThrown);
			}
		});

	});
});