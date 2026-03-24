
////$("#dutyStations").keyup(function() {
function filsRailStationNRS(obj, idx) {

	var railNRS = obj;
	var stationList = "";

	if (railNRS.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getStation",
			data: "station=" + railNRS,
			datatype: "application/json",
			success: function(data) {
				$.each(data, function(index, name) {
					if(name=="Station Name Not Exist"){
						stationList += '<li>' + name + '</li>';
					}else{
					stationList += '<li onClick="fillRecordRailStationNRS(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
					}
				});
				$('#autoSuggestionsListRailStationNRS_' + idx).html(stationList);
				$('#suggestionsRailStationNRS_' + idx).show();
			}
		});

	}
}

function fillRecordRailStationNRS(thisValue, idx) {
	$('#dutyStations_' + idx).val(thisValue);
	setTimeout(() => {
		$('#suggestionsRailStationNRS_' + idx).hide();
	}, 200);

}

