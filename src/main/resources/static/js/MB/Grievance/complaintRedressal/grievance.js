$(document).ready(function() {
	
	$('#myTable').DataTable({
			searching: false,
			info: false,
			ordering: false
		});
	
		$("#welcomeboxDivId").hide();
	
		$("#textAreaDivId").hide();
		$("#complaintDivId").show();
		
		
		$("#faqCheckboxNo").prop("checked", false);
		$("#faqCheckboxYes").prop("checked", false);
		

	
	
	getAllCategory();

	$("#faqCheckboxNo").prop("checked", false);
	$("#faqCheckboxYes").prop("checked", false);
	
	$("#SaveForm").on("keypress", function(e) {
        if (e.key === "Enter" && e.target.id !== "text_area") {
            e.preventDefault();
            return false;
        }
    });
	  

});

function validateEdit(){

	const input = document.getElementById('uploadFile');
    const dataTransfer = new DataTransfer(); 
    allFiles.forEach(file => dataTransfer.items.add(file));

    input.files = dataTransfer.files;
	
	
	var textArea = $("#text_area").val().trim();
	
		if (textArea == "") {
		alert("Please Fill Text Area");
		return false;	
	}
	
	if (textArea.length < 10) {
        alert("Grievance must have at least 10 characters.");
        return false;
    }
    if(textArea.length>500){
		$("#text_area").focus();
		alert("Grievance text must be within 500 characters.");
		return false;
	}
    $("#editForm").submit();
}

function resetFile(){
	$("#uploadFile").val("");
}

 function viewComplaintHistory(grievanceId){
	 

	 $.ajax({
		 url:$("#context_path").val()+"mb/getComplaintStatus",
		 data:"complaint=" + grievanceId,
		 dataType:"",
		
			 
	 });
 }



function getAllCategory() {

	var html =
		'<li class="nav-item dropdown">' +
		'<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Select</a>' +
		'<ul class="dropdown-menu">';

	$.ajax({

		url: $("#context_path").val() + "mb/getGrievanceCategory",
		type: "get",
		datatype: "application/json",
		async: false,
		success: function(msg) {

			$.each(msg, function(i, v) {
				if (parseInt(v.categoryType) != 0) {
        			  return; 
        		}
				
				if (v.childmodule && v.childmodule.length) {
					html +=
						'<li class="dropend">' +
						'<a class="dropdown-item dropdown-toggle" href="#" data-bs-toggle="dropdown">' +
						v.modulename +
						"</a>";
					html += showHoweringChild(v.childmodule, v.modulename);
				} else {
					html +=
						'<li><a class="dropdown-item" href="#" onclick="selectedChildModule(\'' +
						v.modulename +
						'\',\'' +""
						 +
						'\',\'' +
						v.grievanceCategoryId +
						'\')" style="padding-left: 15px;">' +
						v.modulename +
						"</a></li>";
				}
			});

		}

	});
	html += "</ul></li>";
$("#categoryId").html(html);
}



function showHoweringChild(selectedModule, parentModule) {
	var html = '<ul class="dropdown-menu">';
	$.each(selectedModule, function(i, v) {
		if (v.childmodule && v.childmodule.length) {
			html +=
				'<li class="dropend">' +
				'<a class="dropdown-item dropdown-toggle" href="#" data-bs-toggle="dropdown">' +
				v.modulename +
				"</a>";
			html += showHoweringChild(v.childmodule, v.modulename);
		} else {
			html +=
				'<li><a class="dropdown-item" href="#" onclick="selectedChildModule(\'' +
				v.modulename +
				"', '" +
				parentModule +
				"','" +
				v.grievanceCategoryId +
				"' )\">" +
				v.modulename +
				"</a></li>";
		}
	});
	html += "</ul>";
	return html;
}


function selectedChildModule(childmodule, parentModule, grievanceCategoryId) {
	
	var html = "";
    if (parentModule != null && parentModule != "undefined" && parentModule != "") {
		if (parentModule != "Request Creation") {
			html = "<span>" + parentModule + "</span>";
			html = html + "<span>" + " -> " + "</span>";
		}
	}

	$("#grievanceCategory").val(grievanceCategoryId);
	html = html + "<span>" + childmodule + "</span>";
    $("#selectedGeievanceId").html(html);

	getFAQ(grievanceCategoryId);

   
    if (grievanceCategoryId === "110##01") {  
        $("#personalNumberRow").hide();
    } else {
        $("#personalNumberRow").show();
    }
}


function showtextAreaDiv(obj) {
	if (obj.checked) {
		$("#textAreaDivId").show();
	} else {
		$("#textAreaDivId").hide();
	}
}

function validateField() {
	
	
    const input = document.getElementById('uploadFile');
    const dataTransfer = new DataTransfer(); 
    allFiles.forEach(file => dataTransfer.items.add(file));

    input.files = dataTransfer.files;
	
	
	var categoryId = $("#categoryId").val();
	 var grievanceCategoryId = $("#grievanceCategory").val();
	
	
	var textArea = $("#text_area").val().trim();
	var personalNo = $("#personalNo").val();
	

if(grievanceCategoryId!=="110##01"){
	if (personalNo == "") {
		alert("Please Fill personalNo");
		$("#personalNo").focus();
		return false;
	}
	}



	if (textArea == "") {
		alert("Please Fill Text Area");
		$("#textArea").focus();
		return false;	
	}
	
	if (textArea.length < 10) {
        alert("Queries must have at least 10 characters.");
		return false;
	}
    if(textArea.length>500){
		$("#text_area").focus();
		alert("Queries text must be within 500 characters.");
		return false;
	}
	
	$("#SaveForm").submit();
}



function showDiv() {
	var compNo = $("#complaintId").val();
	if (compNo == "") {
		alert("Kindly Enter Complaint Id");
		return false;
	}
	
	
	$("#complaintForm").submit();
}

function showDashDiv() {

	var personalNo = $("#personalNo").val();
	var grievanceId= $("#grievanceId").val();
	if (personalNo == "" && grievanceId == "") {
		 alert("Kindly enter Personal No or Grievance No");
		return false;
	}

	$("#historyFormData").submit();
	
}

function showComplaints() {
	$("#complaintNumberId").show();
	$("#complaintDivId").hide();
}

function showFAQ(faqList) {
	$("#welcomeboxDivId").show();
	$("#faqTableId .all1").empty();
	
	
	faqList.forEach((faq, index) => {
		const questionRow = $(
			`<tr class="question-row" data-index="${index}" style="cursor:pointer;"><td style="font-weight: bold;"><span>Q. </span>${faq.question}</td></tr>`
		);
		const answerRow = $(
			`<tr class="answer-row" id="answer-${index}" style="display:none"><td class="answer-cell"><span>Ans. </span>${faq.answer}</td></tr>`
		);

		$("#faqTableId .all1").append(questionRow, answerRow);
	});
	
	$('.question-row').on('click', function () {
      const index = $(this).data('index');
      const answerRow = $(`#answer-${index}`);

     
      $('.answer-row').not(answerRow).slideUp();

 
      answerRow.slideToggle();
    });
	


}


function getFAQ(grievanceCatId) {
    $.ajax({
        url: $("#context_path").val() + "mb/getFaqByCategoryId",
        type: "get",
        data: "grievanceCatId="+ grievanceCatId,
        dataType: "json",
        async: false,
        success: function(msg) {
		   showFAQ(msg);
        },
        error: function(xhr) {
            $("#faqTableId .all1").empty()
                .append("<tr><td>Error: " + xhr.responseText + "</td></tr>");
        }
    });
}


function personalNoValidation() {
	
    var personalNo = $('#personalNo').val();

  
    $.ajax({
        url: $("#context_path").val() + "mb/validatePersonal",
        type: "get",
        data: "personalNo="+ personalNo , 
        dataType: "json",
        success: function (msg) {
			if(msg.errorMessage!="OK"){
				alert(msg.errorMessage);
				 $('#personalNo').val("");
				return false;
			}	
}
});
}

function testForNumericKey(e) { 


	var myBrow=getBrowserInfo();

	if(myBrow.indexOf('Netscape')> -1)
	{
		var k;
		if ((e.which >=48 && e.which <=57) || (e.which == 8) || (e.which == 0))
		{
		  	return true;
		}else
		{
			alert("Please Enter Only Numerals");
			e.preventDefault();
		}
	}else{
		return true;	
	}
}
function getBrowserInfo()
{
	var bName='';
	var useragent = navigator.userAgent;
	bName = (useragent.indexOf('Opera') > -1) ? 'Opera' : navigator.appName;
	return bName;
}

function getSuggestion(obj) {

	var complaintId = $(obj).val();
	if (complaintId.length > 1) {
		var SuggestionList = "";
	$.ajax({
		url: $("#context_path").val() + "mb/getSuggestionData",
			type: "get",
			data: "complaintId=" + complaintId,
		dataType: "json",
			success: function(msg) {

				$.each(msg, function(index, name) {

					if (name == "No Data Found") {

						SuggestionList += '<li>' + name + '</li>';
					} else {
						SuggestionList += '<li onClick="fillSuggestion(\'' + name + '\')">' + name + '</li>';
		                  }
	                  });
				$("#autoSuggestionsListTo").html(SuggestionList);
	                 	$('#suggestionsTo').show();
		}


	});
	}
}
function fillSuggestion(name) {

	$("#grievanceId").val(name);  
    $("#suggestionsTo").hide();  
}

function clearField(ob){
	
		ob.value = "";
		
	setTimeout(() => {
		$('#suggestionsTo').hide();
	}, 200);
	
}


function viewGrievanceStatus(grievanceId) {

    ajaxUserFace = new LightFace.Request({
        width: 1200,
        height: 500,
        url: $("#context_path").val() + "mb/grievanceStatusView",
        buttons: [
            { title: 'Close', event: function() { this.close(); } }
        ],
        request: {
            data: { grievanceId: grievanceId },
            method: 'post'
        },
        title: 'Query Status View'
    });
    ajaxUserFace.open();
}







let allFiles = []; 

function addSelectedFiles() {
    const input = document.getElementById('uploadFile');
    const fileList = document.getElementById('selectedFiles');
   
    
    if(validateUploadFile(input.files)){
    for (let file of input.files) {
        if (!allFiles.some(f => f.name === file.name && f.size === file.size)) {
            allFiles.push(file);
        }else{
			alert("The file " +file.name + " is already selected. Please choose a different file");
			$("#uploadFile").val("");
			return false;
        }
    }
    renderFileList();
	 }else{
		 return false;
	 }
    
     
     
    

    input.value = '';
}

function renderFileList() {
    const fileList = document.getElementById('selectedFiles');
    fileList.innerHTML = '';
    const ul = document.createElement('ul');

    allFiles.forEach((file, index) => {
        const li = document.createElement('li');
        li.textContent = file.name + ' (' + Math.round(file.size / 1024) + ' KB) ';

        const removeBtn = document.createElement('button');
        removeBtn.textContent = '✖';
        removeBtn.onclick = () => removeFile(index);
 		removeBtn.style.fontSize = '0.7rem';
        removeBtn.style.color = 'red';
        removeBtn.style.border = '2px solid  #aaa';
        
        li.appendChild(removeBtn);
        ul.appendChild(li);
    });

    fileList.appendChild(ul);
}

function removeFile(index) {
    allFiles.splice(index, 1);
    renderFileList();
}

function resetFile() {
    allFiles = [];
    document.getElementById('selectedFiles').innerHTML = '';
    document.getElementById('uploadFile').value = '';
}

function validateUploadFile(files){
	var file = files[0];
				if (file) {
					var allowedExtensions = /(\.pdf|\.jpg|\.jpeg|\.png)$/i;
					if (!allowedExtensions.exec(file.name)) {
						alert("Only PDF and Image files (jpg, jpeg, png) are allowed!");
						$("#uploadFile").val("");
						return false;
					}
					if (file.size >= 1024 * 1024) { // 1MB
						alert("File size must be less than or equal 1 MB!");
						$("#uploadFile").val("");
						return false;
					}
				}
				return true;
			
	
}

function handleFaqCheckbox(checkbox) {
    if (checkbox.checked) {
        window.location.href = $("#context_path").val()+"mb/faqCheckboxYes";
    }
}






