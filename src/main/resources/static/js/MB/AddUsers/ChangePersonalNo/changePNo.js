function searchPersonalNo(){

var alphaNo=trim($("#alphaNoId").val());
var personalNo=trim($("#personalNo").val());
var chkAlpha=$("#chkAlpha").val();
var serviceId=$("#alternateService").val();
var payAcOff=$("#payAcOff").val();
var airAcOff=$("#airAcOff").val().trim();
var rankId=$("#rankId").val();
var level=$("#levelId").val();
var dob=$('#dob').val().trim();

if(dob!=''){
 var d2ob =dob.split("-");
 dob=d2ob[2]+"-"+d2ob[1]+"-"+d2ob[0];
}
var dateOfCom_join=$('#dateOfCom_join').val().trim();
if(dateOfCom_join!=''){
var doc22 =dateOfCom_join.split("-");
dateOfCom_join=doc22[2]+"-"+doc22[1]+"-"+doc22[0];
}
var airForceCadetNo=$("#airForceCadetNo").val();
var cadetNo=$("#cadetNo").val();
var cadetChkAlpha=$("#cadetChkAlpha").val();
var courseSerialNo=$("#courseSerialNo").val();

var newPersonalNo='';

if(checkNavyAirforceCadets()){ //cadit
    	if(checkNavyArchitectOffice()){
    	newPersonalNo=cadetNo;	
    	chkAlpha='A';  //hard coded to bypass JS Check
    	}else if(checkAirforceCadet()){
    	newPersonalNo=	airForceCadetNo; 
    	chkAlpha='A';  //hard coded to bypass JS Check
    	}else{
    	newPersonalNo=cadetNo+cadetChkAlpha+courseSerialNo;
    	chkAlpha=cadetChkAlpha;
    	 if(cadetNo == '' || cadetChkAlpha == '' || courseSerialNo == ''){
    	 	newPersonalNo='';
    	 }
    	}
 }else{
 	newPersonalNo=alphaNo+''+personalNo+''+chkAlpha;
 }

if(serviceId==""){
alert("Service Can't Be Blank");
return false;
}  

if(level=="" || level=="-1"){
alert("Please select Level Name");
return false;
} 
 
if(rankId=="" || rankId=="-1"){
alert("Rank Can't Be Blank");
return false;
}  

if(newPersonalNo==""){
alert("Personal Number Can't Be Blank");
return false;
}

if(chkAlpha==""){
alert("Please Check The Personal Number");
return false;
}

var pNo=$("#personalNo").val();
	var checkAlpha=$("#chkAlpha").val();
	
	if(pNo!='' && checkAlpha !=''){
		if($("#validatePersonalNo").val() != null) 
		{
			if(($("#validatePersonalNo").val() == 'false') || ($("#validatePersonalNo").val() == ''))
			{
			  alert("Please Click On Validate Personal Number Button");
			  $("#chkAlpha").focus();
			  return false;
			}
			
		}
	}

if(payAcOff=="" || payAcOff=="-1"){
alert("Rail Pay Account Office Can't Be Blank");
return false;
}

if(airAcOff=="" || airAcOff=="-1"){
alert("Air Pay Account Office Can't Be Blank");
return false;
}

if (dob == '') {
		alert("Please fill the Date of Birth field");
		$("#dob").focus();
		return false;
	}

if (dateOfCom_join == '') {
		alert("Please fill the Date of Commissioning/Enrollment field");
		$("#dateOfCom_join").focus();
		return false;
	}
	
	
 var now = new Date();
       var currentdate = (now.getDate() < 10 ? "0" + now.getDate().toString() : now.getDate().toString()) + "-" +
       (now.getMonth() < 10 ? "0" + (now.getMonth()+1).toString() : now.getMonth().toString()) + "-" + now.getFullYear().toString();
  		
  		var cdate=currentdate.split("-");
    	var currdate=new Date(cdate[2],cdate[1]-1,cdate[0]);
    	var currYr=cdate[2];
    	currYr=parseInt(currYr)+0;
    	
    	if(dob!="")
    	{
			
    		var dobDate=dob.split("-");
    		var year=dobDate[0];
    		year=parseInt(year);
    	  
    		if(year>=currYr){
	    		alert("Please select valid date of birth");
	    		document.getElementById("dob").focus();
	    		return false;
    		}else
    		{
    			if((currYr-year)>62||(currYr-year)<16)
    			{
	    			alert("DOB should not be less than 16 and not more than 62 years");
    				document.getElementById("dob").focus();
    				return false;
    			}
	    	}
   		}

var USER_ALIAS="";

var response="";
$.ajax({

      url: $("#context_path").val()+"mb/getDataPno",
      type: "post",
      data: "dob="+dob+"&enrollmentDate="+dateOfCom_join ,
     datatype: "application/json",
		success: function(msg) {
                  var tdvalue = '';
							
							$.each(msg, function(i) {

				var obj = JSON.parse(JSON.stringify(this));
				
						tdvalue +='<Table border="1" width="100%" class="filtersearch"><tbody class="all3">'
//						 $.each(msg,function(){
						 tdvalue +='<tr align="left">';
						 
                            
								USER_ALIAS =  obj.userAlias;
								var DATE_OF_BIRTH = obj.dobStr;
								var DATE_OF_COMMISIONING = obj.enrollmentDateStr;
								var UNIT_ID = obj.unitId
								var UNIT_NAME = obj.unitName;
								var NAME = obj.name;
									//tdvalue += '<td width="15%"><input type="radio" name="mapchkbx" value="'+ USER_ALIAS+'" onclick="reasonForChangePersonalNo(this.value)" id="mapchkbx"/></td>';
									tdvalue += '<td style="width:7%"><input type="radio" name="mapchkbx" value="'+ USER_ALIAS+'" onclick="reasonForChangePersonalNo_New(\''+USER_ALIAS+'\',\''+UNIT_ID+'\')" id="mapchkbx"/></td>';
									tdvalue += '<td style="width:15%">'+USER_ALIAS+'</td>';
									tdvalue += '<td style="width:18%">'+ NAME +'</td>';
									tdvalue += '<td style="width:20%">'+ UNIT_NAME +'</td>';
									tdvalue += '<td style="width:20%">'+ DATE_OF_BIRTH +'</td>';
									tdvalue += '<td style="width:20%">'+ DATE_OF_COMMISIONING +'</td>';
								tdvalue +='</tr>';
//								}); 
								 
								tdvalue +='</tbody></Table>';
								
					if(USER_ALIAS=="")
					{
						
						$("#mapPersonalNo").html("<option value=''>No Personal Number Found For Above Combination<\/option>");	 
						$("#reasonDiv").hide();
					}	
					else
					{
						$("#mapPersonalNo").show();
						$("#reasonDiv").hide();
						$("#mapPersonalNo").html(tdvalue);
					}	
        response+=obj;  
         });
       }
       });
     beforeSend:$('#mapPersonalNo').html("Getting response from server....").css("color", "red"); 
   
 }
 
function reasonForChangePersonalNo(personal){
var reasonDiv=$("#reasonDiv");
var mapPersonalNodiv=$("#mapPersonalNo");
if (confirm("Do You Really Want To Change  Your Old Existing Personal No With This New Personal Number ? You will not be allowed to change it again.")){
reasonDiv.show();
mapPersonalNodiv.hide();

$("#oldPersonalNo").val()=personal;
}else{
window.location.reload(); 
reasonDiv.hide();
mapPersonalNodiv.show();
}
}

/*-----------New Method Implemented For New Changes Start-----------------*/


function reasonForChangePersonalNo_New(personal,personalUnitId)
{
	var reasonDiv=$("#reasonDiv");
	var mapPersonalNodiv=$("#mapPersonalNo");
	var loginUserUnitId=$("#loginUserUnitId").val();
	

	
	//alert("loginUserUnitId="+loginUserUnitId+"||personal="+personal+"||personalUnitId="+personalUnitId);
	if(personalUnitId==loginUserUnitId){
		
		if (confirm("Do You Really Want To Change  Your Old Existing Personal No With This New Personal Number ? You will not be allowed to change it again."))
		{
				$("#oldPersonalNo").val(personal);
		
			reasonDiv.show();
			mapPersonalNodiv.hide();
		
		}
		else
		{
			window.location.reload(); 
			reasonDiv.hide();
			mapPersonalNodiv.hide();
		}
		
	}else{
		
		alert("Personal["+personal+"] Number Does Not Belong To Your Unit.First Use The Transfer Functionality For Personal Number And Then Map It.")
		reasonDiv.hide();
		mapPersonalNodiv.hide();
	}

}

/*-----------New Method Implemented For New Changes End-----------------*/

function mapPersonalNo(formName, event){



var reason=trim($("#reason").val());
var submitForm=document.createElement("form");
var alphaNo=trim($("#alphaNoId").val());
var personalNo=trim($("#personalNo").val());
var chkAlpha=$("#chkAlpha").val();
var oldPersonalNo=$("#oldPersonalNo").val();
var unitService=$("#serviceId").val();
var travelerService=$("#alternateService").val();
var payAcOff=$("#payAcOff").val();
var airAcOff=$("#airAcOff").val();//AIR_PO_MAPPING
var rankId=$("#rankId").val(); 
var category=$("#category").val(); 
var level=$("#levelId").val();
var retireAge=$("#retireAge").val();

 var airForceCadetNo=$("#airForceCadetNo").val();
 var cadetNo=$("cadetNo").val();
 var cadetChkAlpha=$("cadetChkAlpha").val();
 var courseSerialNo=$("courseSerialNo").val();

var newPersonalNo='';

if(checkNavyAirforceCadets()){ //cadit

    	if(checkNavyArchitectOffice()){
    	newPersonalNo=cadetNo;	
    	chkAlpha='A';  //hard coded to bypass JS Check
    	}else if(checkAirforceCadet()){
    	newPersonalNo=	airForceCadetNo;
    	chkAlpha='A';  //hard coded to bypass JS Check
    	}else{
    	newPersonalNo=cadetNo+cadetChkAlpha+courseSerialNo;
    	chkAlpha=cadetChkAlpha;
    	 if(cadetNo == '' || cadetChkAlpha == '' || courseSerialNo == ''){
    	 	newPersonalNo='';
    	 }
    	}
 }else{
	 
 	newPersonalNo=alphaNo+''+personalNo+''+chkAlpha;
 	

 	if(personalNo==""){ 
		alert("Personal Number Cannot Be Blank");
		return false;
	}
 }

var persnBlock=$("persnBlock").val();

if(travelerService=="" || travelerService=="-1"){
alert("Please Select Traveler Service");
return false;
}
if(category=="" || category=="-1"){
alert("Please Select Category Name");
return false;
}
if(level=="" || level=="-1"){
alert("Please Select Level Name");
return false;
}
if(rankId=="" || rankId=="-1"){
alert("Grade Pay is not mapped with selected level.");
return false;
}

if(newPersonalNo==""){
alert("Personal Number Cannot Be Blank");
return false;
}
if(chkAlpha==""){
alert("Please Check The Personal Number");
return false;
}
if(payAcOff=="" || payAcOff=="-1"){
alert("Rail Pay Account Office Cannot Be Blank");
return false;
}

if(airAcOff=="" || airAcOff=="-1"){
alert("Air Pay Account Office Cannot Be Blank");
return false;
}


/*
if(persnBlock=='false'){ 
alert("Personal No doesnot exists between Specified Block of Personal No.");  
return false;
}
*/
if(reason==""){
alert("Please Enter Reason");
return false;
}

if (confirm("Do You Really Want To Change  Your Old Existing Personal No With This New Personal Number ? You will not be allowed to change it again.")){

}else{
window.location.reload();


return false;
}


//alpha logic check


// end of check of personal number

document.body.appendChild(submitForm);
submitForm.method = "POST";

var hiddenEevent =document.createElement('input');
hiddenEevent.name="event";
hiddenEevent.id="event"; 
hiddenEevent.type="hidden";
hiddenEevent.value =event;
submitForm.appendChild(hiddenEevent);

var hiddenElement =document.createElement('input');
hiddenElement.name="oldPersonalNo";
hiddenElement.id="oldPersonalNo";
hiddenElement.type="hidden";
hiddenElement.value =oldPersonalNo;
submitForm.appendChild(hiddenElement);

//unit details

var hiddenUnitServiceId =document.createElement('input');
hiddenUnitServiceId.name="unitService";
hiddenUnitServiceId.id="unitService";
hiddenUnitServiceId.type="hidden";
hiddenUnitServiceId.value =unitService;
submitForm.appendChild(hiddenUnitServiceId);

var hiddenTravelerServiceId =document.createElement('input');
hiddenTravelerServiceId.name="travelerService";
hiddenTravelerServiceId.id="travelerService";
hiddenTravelerServiceId.type="hidden";
hiddenTravelerServiceId.value =travelerService;
submitForm.appendChild(hiddenTravelerServiceId);

var hiddenPayAcOff =document.createElement('input');
hiddenPayAcOff.name="payAcOff";
hiddenPayAcOff.id="payAcOff";
hiddenPayAcOff.type="hidden";
hiddenPayAcOff.value =payAcOff;
submitForm.appendChild(hiddenPayAcOff);

var hiddenairAcOff =document.createElement('input');
hiddenairAcOff.name="airAcOff";
hiddenairAcOff.id="airAcOff";
hiddenairAcOff.type="hidden";
hiddenairAcOff.value =airAcOff;
submitForm.appendChild(hiddenairAcOff);

var hiddenCategory =document.createElement('input');
hiddenCategory.name="category";
hiddenCategory.id="category";
hiddenCategory.type="hidden";
hiddenCategory.value =category;
submitForm.appendChild(hiddenCategory);

var hiddenRankId =document.createElement('input');
hiddenRankId.name="rankId";
hiddenRankId.id="rankId";
hiddenRankId.type="hidden";
hiddenRankId.value =rankId;
submitForm.appendChild(hiddenRankId);

//End
var hiddenReason=document.createElement('input');
hiddenReason.name="reason";
hiddenReason.id="reason";
hiddenReason.type="hidden";
hiddenReason.value =reason;
submitForm.appendChild(hiddenReason);

var hiddenNewPersonalNo =document.createElement('input');
hiddenNewPersonalNo.name="newPersonalNo";
hiddenNewPersonalNo.id="newPersonalNo";
hiddenNewPersonalNo.type="hidden";
hiddenNewPersonalNo.value =newPersonalNo;
submitForm.appendChild(hiddenNewPersonalNo);

var hiddenLevel =document.createElement('input');
hiddenLevel.name="levelName";
hiddenLevel.id="levelName";
hiddenLevel.type="hidden";
hiddenLevel.value =level;
submitForm.appendChild(hiddenLevel);


submitForm.action=formName.replace('/page', "");
$("#newPno").val(newPersonalNo);
$("#changePnoForm").submit(); 

}

function trim(n)
	{
   		return n.replace(/^\s+|\s+$/g,'');
	}
function isEmpty(value)
	{
		return value==null||""==value;
	}
function checkNoneChar(value, name){
	var flag=true;
	var length=value.length;
	for (var j = 0; j < length; j++){
		var ch = value.substring(j, j + 1);
		if (((ch < "a" || "z" < ch) && (ch < "A" || "Z" < ch) &&(ch!=" ") && (ch!="(") && (ch!=")"))){
			if((ch < "0" || "9" < ch || ch != "_" || ch != "/" || ch != "*" || ch != "+" || ch != "-" || ch != "%" || ch != "^" || ch != "#" || ch != "@" || ch != "!" || ch != "$" || ch != "<" || ch != ">" || ch != ":")){
				alert(" Please use only Characters between \n a to z or A to Z , Numbers and special Characters are not accepted.");
				flag=false;
				break;
			}
		}
   	}
	return flag;
}		