

function getStationAir(obj, idx) {

	var officeNRS = obj;
	var stationList = "";

	if (officeNRS.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getAirport",
			data: "airPortName=" + officeNRS,
			datatype: "application/json",
			success: function(data) {
				$.each(data, function(index, name) {
					if(name=="Airport Name Not Exist"){
						stationList += '<li>' + name + '</li>';
					}else{	
					stationList += '<li onClick="fillRecordOffice(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
					}

				});
				$('#autoSuggestionsListOfficeNRS1_' + idx).html(stationList);

				$('#suggestionsOfficeNRS1_' + idx).show();

			}
		});
	}

}


function fillRecordOffice(thisValue, idx) {
	$('#airportStationText_0').val(thisValue);

	setTimeout(() => {
		$('#suggestionsOfficeNRS1_' + idx).hide();
	}, 200);


}
