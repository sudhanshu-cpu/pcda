function getRailStation(obj, idx) {

	var railNRS = $(obj).val();
	var stationList = "";

	if (railNRS.length > 1) {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getNearestStation",
			data: "station=" + railNRS,
			datatype: "application/json",
			success: function(data) {
				$.each(data, function(index, name) {
 					stationList += '<li onClick="fillRecordRailStation(\'' + name + '\',' + idx + ')">' + name + '</li>';
				});
				$('#autoSuggestionsListRailStation' + idx).html(stationList);
				$('#suggestionsRailStation' + idx).show();
			}
		});
	} else {
		$('#autoSuggestionsListRailStation' + idx).html();
		$('#suggestionsRailStation' + idx).hide();
	}

}


function fillRecordRailStation(value, idx) {
	$('#fillStnNrs' + idx).val(value);
	setTimeout(() => {
		$('#suggestionsRailStation' + idx).hide();
	}, 200);
}



function getRailStationChild(obj, idx) {

	var railNRS = $(obj).val();
	var stationList = "";

	if (railNRS.length > 1) {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getNearestStation",
			data: "station=" + railNRS,
			datatype: "application/json",
			success: function(data) {
				$.each(data, function(index, name) {
					if(name=="Station Name Not Exist"){
						stationList += '<li>' + name + '</li>';
					}else{	
 					stationList += '<li onClick="fillChildHostelNRS(\'' + name + '\',' + idx + ')">' + name + '</li>';
 					}
				});
				$('#autoSuggestionsListRailStationChild' + idx).html(stationList);
				$('#suggestionsRailStationChild' + idx).show();
			}
		});
	} else {
		$('#autoSuggestionsListRailStationChild' + idx).html();
		$('#suggestionsRailStationChild' + idx).hide();
	}

}

function fillChildHostelNRS(value, idx) {
	$('#childHostelNRS' + idx).val(value);
	setTimeout(() => {
		$('#suggestionsRailStationChild' + idx).hide();
	}, 200);
}



function getAirport(obj, idx) {

	var officeNRS = $(obj).val();
	var airPortList = "";

	if (officeNRS.length > 1) {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getNearestAirport",
			data: "airPortName=" + officeNRS,
			datatype: "application/json",
			success: function(data) {
				$.each(data, function(index, name) {
					if(name=="Airport Name Not Exist"){
						airPortList += '<li>' + name + '</li>';
					}else{
					airPortList += '<li onClick="fillAirport(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
					}

				});
				$('#autoSuggestionsListAirport' + idx).html(airPortList);
				$('#suggestionsAirport' + idx).show();
			}
		});
	} else {
		$('#autoSuggestionsListAirport' + idx).html();
		$('#suggestionsAirport' + idx).hide();
	}

}

function fillAirport(thisValue, idx) {
	$('#fillAirport' + idx).val(thisValue);
	setTimeout(() => {
		$('#suggestionsAirport' + idx).hide();
	}, 200);
}



function getAirportChild(obj, idx) {

	var officeNRS = $(obj).val();
	var airPortList = "";

	if (officeNRS.length > 1) {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getNearestAirport",
			data: "airPortName=" + officeNRS,
			datatype: "application/json",
			success: function(data) {
				$.each(data, function(index, name) {
					if(name=="Airport Name Not Exist"){
						airPortList += '<li>' + name + '</li>';
					}else{	
					airPortList += '<li onClick="fillChildHostelNAP(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
					}

				});
				$('#autoSuggestionsListAirportChild' + idx).html(airPortList);
				$('#suggestionsAirportChild' + idx).show();
			}
		});
	} else {
		$('#autoSuggestionsListAirportChild' + idx).html();
		$('#suggestionsAirportChild' + idx).hide();
	}

}

function fillChildHostelNAP(thisValue, idx) {
	$('#childHostelNAP' + idx).val(thisValue);
	setTimeout(() => {
		$('#suggestionsAirportChild' + idx).hide();
	}, 200);
}

function fillEmp(ob, idx) {
	ob.value = "";
	setTimeout(() => {
		$('#suggestionsRailStation' + idx).hide();
	}, 200);
}

function fillApEm(ob, idx) {
	ob.value = "";
	setTimeout(() => {
		$('#suggestionsAirport' + idx).hide();
	}, 200);
}
