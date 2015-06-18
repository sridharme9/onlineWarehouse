$(document).ready(function(){
	$('#btnSubmit').click(function(){
		var value = $('#martName').val();
		if(value){
			var headers = { 'Content-Type': 'application/json'};
		 	$.ajax({
				url: 'http://localhost:8080/DataMartWeb/datamart/createmart?mart=' + value,
				type: 'GET',
				contentType: 'application/json',
				timeout: 5000,
				success: function(response) {
					$('#martName').val("");
					console.log(response);
					response = JSON.parse(response);
					console.log(response.status);
					if(response.status == "200"){
						alert("Created Data Mart!");
					}else if(response.status = "201"){
						alert("SQL Exception");
					}else {
						alert("Something Went Wrong!");
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('error ' + textStatus + " " + errorThrown);
				}
			});
		}
	});

	$("#selectMart").on("change", function(){
		var mart = $("#selectMart").val();
		if(mart){
			$("#showMart").empty();
			var headers = { 'Content-Type': 'application/json'};
		 	$.ajax({
				url: 'http://localhost:8080/DataMartWeb/datamart/getmartdetails?mart=' + mart,
				type: 'GET',
				contentType: 'application/json',
				timeout: 5000,
				success: function(response) {
					var dims = response.dims;
					var facts = response.facts;
					var data = "";
					data += "<div> <b>Fact Tables: </b></div>";
					for(var i=0; i<facts.length;i++){
						data = data + "<div style=margin-left:40px>" + facts[i] + "</div>";
					}
					data += "<div> <b> Dimensions: </b></div>";
					for(var i=0; i<dims.length;i++){
						data = data +"<div style=margin-left:40px>" + dims[i] + "</div>";
					}
					$("#showMart").append(data);
					
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('error ' + textStatus + " " + errorThrown);
				}
			});
		}
	});
});