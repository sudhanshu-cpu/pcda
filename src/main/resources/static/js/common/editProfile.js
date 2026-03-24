$(document).ready(function() {
	
	$("#dateOfBrth").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#dateOfBrth").val("");
		}
	 
	});
	
	$("#dateOfCom_join").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#dateOfCom_join").val("");
		}
	 
	});
	
	$("#dateOfRtirementEdit").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		onShow: function () {
			  $("#dateOfRtirementEdit").val("");
		}
	 
	});
	
	   encryptUserAlias(); // added for encryption of pno
	
});



function encryptUserAlias(){

	var personalNo = $("#userAlias").val().trim();
	
	if(personalNo!=""){
		var encryptPNo = CryptoJS.AES.encrypt(personalNo,"Hidden Pass").toString();
		$("#userAlias").val(encryptPNo);
		
	}
}


var k=2; 
function initProfileEditing()
{
 $('#editProfileBtn').click(function()
 {
	
   
   var email=$('#email').val();

  $("#payAccountingOffc").show();  
  $("#PayAccountingOffc").hide(); 
  $("#edit_level_id").show();
  $("#edit_level_id1").show();
  $("#view_level_id").hide();
  $("#view_level_id1").hide(); 
  $("#view_level_id2").hide(); 
  $("#email").show();  
  $("#Email").hide(); 
  //$("#dateeOfBirth").show();  
 // $("#DateeOfBirth").hide(); 
  $("#dateOfCommissioning").show();  
  $("#DateOfCommissioning").hide(); 
  $("#dateOfRetirement").show();  
  $("#DateOfRetirement").hide(); 
  $("#retirementDateChangeReason").show();
  
  if($("#cdaoAccEditAllowId").val()==0){
  $("#cdaAccNo").show();  
  $("#CdaAccNo").hide();  
 }
  
  $("#maritalStatus").show();  
  $("#MaritalStatus").hide();
  
  $("#GenderStatus1").hide();  
  $("#genderStatus2").show();
   
  $("#homeTownNrs").show();  
  $("#HomeTownNrs").hide(); 
  $("#isShowSpr").show();  
  $("#IsShowSpr").hide(); 
  $("#sprNrs").show();  
  $("#SprNrs").hide(); 
  
  $("#homeTown").show();  
  $("#HomeTown").hide(); 
  $("#nrsDutyStn").show();  
  $("#NrsDutyStn").hide();
  //Start: Nearest Airport
  $("#naDutyStn").show();  
  $("#NaDutyStn").hide(); 
  $("#naHomeTown").show();  
  $("#NaHomeTown").hide();
  $("#naSpr").show();  
  $("#NaSpr").hide();
  $("#odsNRS").show();  
  $("#OdsNRS").hide();
  $("#odsNPA").show();  
  $("#OdsNPA").hide();
  $("#odsSprNRS").show();  
  $("#OdsSprNRS").hide();
  $("#odsSprNPA").show();  
  $("#OdsSprNPA").hide();
  
  //End: Nearest Airport   
  $("#mobileNo").show();  
  $("#MobileNo").hide(); 
  $("#phone").show();  
  $("#Phone").hide();
  $("#phone2").show();   
  $("#Phone2").hide(); 
  $("#ersPrintName").show();  
  $("#ErsPrintName").hide();  
  $("#dis_Acc_num").hide(); 
  $("#edit_Acc_num").show();
  $("#dis_ifsc_code").hide(); 
  $("#edit_ifsc_code").show();  
  
  if($("#nameEditAllowId").val()==0){
  $("#Name").hide(); 
  $("#name").show();   
  }
  
  $("#familyDetail").hide();   
  $('#editProfileBtn').hide();
  $('#saveProfileBtn').show();
  $('#addBtn').show();
  
  initEditProfileRequest();
  showFamilyEdit();
  
  
});
}


 function trim(n)
	{
   		return n.replace(/^\s+|\s+$/g,'');
	}
	
	function isEmpty(value)
	{
		return value==null||""==value;
	}

function validateUserProfile(event){

	checkRetirementDate();
	var spouseCheck=false;
	var stepMotherCheck=false;
	var stepFatherCheck=false;
	var motherCheck=false;
	var fatherCheck=false;

	var tbl = document.getElementById('tblGrid');
  	var lastRow = tbl.rows.length;

  	var noOfFilyDtls=lastRow-1;
  	$("#lastRowIndex").val(noOfFilyDtls);

	var fName=isEmpty(trim($("#fName").val()));
	var lName=isEmpty(trim($("#lName").val()));
	var accOffice=$("#payAccOff").val();
	var airAccOff=$("#airAccOff").val();
	var ersPrintName=isEmpty(trim($("#ersPrntName").val()));
	var emailId=$("#emailId").val().trim();
	var mobNo=$("#mobNo").val().trim();
	var dateOfBirth=$("#dateOfBrth").val();
	var dateOfRetirement=trim($("[name^=dateOfRetirement]").val());
	var dateOfCom_join=$("#dateOfCom_join").val();
		// Code commented as changed requirement by client on 23-dec-2009
	    //var mobNo=document.editProfileForm.mobileNo.value;
        //	var stdCdeNo1=document.editProfileForm.stdCdeNo1.value;
        //	var telNo1=document.editProfileForm.telNo1.value;
        //	var stdCdeNo2=document.editProfileForm.stdCdeNo2.value;
        //	var telNo2=document.editProfileForm.telNo2.value;
	var dutyStnNrs=isEmpty(trim($('[name^=dutyStnNrs]').val()));
	var hmeTwnStnPlace=isEmpty(trim($('[name^=hmeTwnStnPlace]').val()));
	var sprPlaceID=isEmpty(trim($('[name^=sprPlace]').val()));
	var sprNrsPlaceID=isEmpty(trim($('[name^=sprNrs]').val()));
    var hmeTwnStnNrs=isEmpty(trim($('[name^=hmeTwnStnNrs]').val()));
 	var personalBlock=$('#persnBlock').val();
 	var rankName=$("#rankId").val();
 	var level=$("#levelId").val();
 	var retireAge=$("#retireAge").val();
 	var serviceName =$("#ServiceName").val();
 	var serviceId=$("#ServiceId").val();
	if('Civilian'==serviceName || 'DRDO' == serviceName || serviceId == '100001'){
	var newCategoryId=$("#categoryId").val();
	var oldCategoryId=$("#CategoryId").val(); 
  if(newCategoryId!=oldCategoryId ){
  	if($("#changeAuthority").val()==""){
    	alert("Please enter change authority");
    	$("#changeAuthority").focus();
		return false;
    }
  }
}

var sprPlace =trim($('[name^=sprPlace]').val());
var sprNrsResrv =trim($('[name^=sprNrs]').val());
var dutyStation= trim($('[name^=dutyStnNrs]').val());


 	//document.editProfileForm.fName.focus();
 	if(level=="-1"){
    	alert("Please Select Level");
    	$("#levelId").focus();
		return false;
    }

    if(rankName=="-1" || rankName==""){
    	alert("Grade Pay Not Map with current Level.");
    	return false;
    }

    if(retireAge=="" || retireAge <0){
    	alert("Retirement Age Should Be greater Than Zero.");
		return false;
    }
    
    if(accOffice=="-1"){
    	alert("Please Select Rail Pay Account Office");
    	$("#payAccOff").focus();
		return false;
    }

	if(airAccOff=="-1"){
    	alert("Please Select Air Pay Account Office");
    	$("#airAccOff").focus();
		return false;
    }  

    if(serviceName.toUpperCase()=='ARMY' && (accOffice != airAccOff)){
        alert("Please Select same Rail Pay Accounting Office and Air Pay Accounting Office");
		$('#payAccOff').focus();
		return false;
    }

	if(personalBlock=='false'){
		alert("Personal No doesn't exists between specified block of Personal No. Please recheck either personal number or Pay Account Office");  
	//	return false;
	}
			
	if(fName){
		alert("Please fill the First Name field");
		$("#fName").focus();
		return false;
	}
	
	/*if(lName){
		alert("Please fill the Last Name field");
		$("#lName").focus();
		return false;
	}*/


	if(!checkSplChar($("#fName"))){
	  return false; 
	}

	if($('#mName').val() != ""){
    	if(!checkSplChar($("#mName")))
    	  return false;
    	}

   if($('#lName').val() != ""){
	if(!checkSplChar($("#lName")))
	  return false;   
	}


	if(ersPrintName){
		alert("Please fill ERS Print Name Field");
		$("#fName").focus();
		return false;
	}
	if(!checkSplChar($("#ersPrntName"))){
	  return false; 
	  }
	  
	if(ersPrintName.length > 15){
  	  alert("ERS Print Name can't be greater than 15 letters");
  	  $("#fName").focus();
	  return false;
    }
   
    if(isEmpty(trim(dateOfBirth)) || dateOfBirth=='dd/mm/yyyy'){
		alert("Please fill the date Of birth ");
		$("#dateOfBrth").focus();
		return false;
	}

	if(!validateDob(dateOfBirth,'',''))
	return false;



	if((dateOfRetirement=='') || (dateOfRetirement=='dd/mm/yyyy')){
		alert("Please fill the date of retirement");
		$("#dateOfRtirement").focus();
		return false;
	}
	
	if(!validateDateOfRetirementOnEdit(dateOfRetirement)){
	  return false;
	  }
	
	if($("#retirementDateFlag").val()== 'true'){
		if(trim($("#retirementChangeReason").val()) == ""){
			alert("Please fill the Retirement Date Change Reason");
		    $("#retirementChangeReason").focus();
		    return false;
		}

		if(trim($("#retirementChangeAuthority").val()) == ""){
			alert("Please fill the Retirement Date Change Authority");
		    $("#retirementChangeAuthority").focus();
		    return false;
		}
	}

	if((isEmpty(trim(dateOfCom_join))) || dateOfCom_join=="dd/mm/yyyy"){
		alert("Please fill the Date of Commissioning/Enrolment field");
		$("#dateOfCom_join").focus();
		return false; 
	}
	
	
	if(emailId==''){
		alert("Please Enter EmailId");
		$("#emailId").focus();
		return false; 
	}
	
		if(mobNo==''){
		alert("Please Enter Mobile Number");
		$("#mobNo").focus();
		return false; 
	}
	


//	if(!isGreaterThanTodayDate(document.getElementById("dateOfCom_join")))
//	return false;
	
	if(!validateDobAndDateOfComm(dateOfBirth,dateOfCom_join,'',''))
	return false;
	
	if(!validateDateOfCommAndDateOfRetireOnEdit(dateOfCom_join,dateOfRetirement))
	   return false;
	
	if($("#isRetirementDateUpdated").val()== 'false'){
	/*-----------Calculating Retirement Date ----------*/
		getEditRetirementDate();
	/*-----------Calculating Retirement Date ----------*/
	
	}

	if(emailId!=''){
		if(!checkemail(emailId)){
			 $("#emailId").focus();
			 return false;
	 	}
	}
	
	
		if($("#mobNo").val()!=""){
		if (!isValidMobileNumber($("#mobNo").val())){
		 $("$mobNo").val("");
			 $("#mobNo").focus();
	 	 return false;
	 	}
	}

	
	
	var serviceName="";
    serviceName=document.getElementById('ServiceName').value;
    var categoryName="";
    categoryName=document.getElementById('CategoryName').value;
//    var rankName="";
//    rankName=document.getElementById('RankName').value;

     if(serviceName=='Army' && categoryName=='Officer')
     {	
		 var accN = document.getElementById("acNo");
     var accountNo="";
		 if(accN != null) {
     accountNo=accN.value;
		 }
     var validateCDAOAN= document.getElementById("validateCDAOANo");
     var validateCDAOANo= ""; 
     if (validateCDAOAN != null) {
		validateCDAOANo= validateCDAOAN.value;
	 }
	  if(accountNo!='')
	  {
		  if((validateCDAOANo == 'false') || (validateCDAOANo== ''))
			{
			 	alert("Please Click On Validate Account Number Button");
			 	document.getElementById("acNo").focus();
		return false;
	}
	
	}
	
	}


	if(dutyStnNrs){
		     alert("Please enter Nearest Railway Station")
			 $(dutyStnNrs).focus();
			 return false;
	 	}
	 
	if(hmeTwnStnPlace){
		     alert("Please enter Home Town")
			 $(hmeTwnStnPlace).focus();
			 return false;
	}
	 if(hmeTwnStnNrs){
		     alert("Please enter Home Town NRS")
			 $(hmeTwnStnNrs).focus();
			 return false;
	}

	if(sprPlace!='' || sprPlace!=undefined || sprPlace!=' '){
if(dutyStation==sprPlaceID){
	
	alert("Nearest Railway Station (Duty Station) And Nearest Railway Station (SPR) cannot be same");
	 return false;
}
}
//alert(sprNrsResrv);
	if(sprNrsResrv!='' || sprNrsResrv!=undefined || sprNrsResrv!=' '){
if(dutyStation==sprNrsResrv){
	
	alert("Nearest Railway Station (Duty Station) And Nearest Railway Station (SPR) cannot be same");
	 return false;
}
}

	//if(!validateAccountDetails()){return false;}

	//if(sprPlaceID){
//		     alert("Please enter SPR")
//			 document.getElementById("sprPlaceID").focus();
//			 return false;
//	}
//	 if(sprNrsPlaceID){
//		     alert("Please enter Spr NRS")
//			 document.getElementById("sprNrsPlaceID").focus();
//			 return false;
//	}
	document.getElementById("flag").value="false";
	
	var firstmemberName; var middlememberName; var lastmemberName;	var memGender; var memdob;	var memRelation;	var memMaritalStatus;
  	var doIIPartNo;	var doIIDate;	var memReson; var isDepen; var countChildDep=0;
  	var ersFamilyPrintName; var memberName;
    
    /* memberName=document.getElementsByName("memberName") */
    
 	 firstmemberName=document.getElementsByName("firstmemberName");
 	 middlememberName=document.getElementsByName("middlememberName");
 	 lastmemberName=document.getElementsByName("lastmemberName");

 	 memGender=document.getElementsByName("memGender");
 	 memdob=document.getElementsByName("memdob");
  	 memRelation=document.getElementsByName("memRelation");
   	 memMaritalStatus=document.getElementsByName("memMaritalStatus");
   	 doIIPartNo=document.getElementsByName("doIIPartNo");
  	 doIIDate=document.getElementsByName("doIIDate");
  	 memReson=document.getElementsByName("memReson");
  	 isDepen=document.getElementsByName("isDepen");
     ersFamilyPrintName=document.getElementsByName("ersPrintName");
  	 var status=document.getElementsByName("status");

  	var checkBox=$('input[name=mstatus]');
    var tbl = document.getElementById('editFamilyDetails');
  	var opType=document.getElementsByName('opType');
  	var rowNumber=document.getElementById('rowNumber');
  	var remark=document.getElementById('remark'); 
  	var checkedValues=0;

    var validFristName="";
    var validMiddleName="";
    var validLastName ="";
    var validDob="";
	 	
    var isFnameDuplicate     = false;
    var isMnameDuplicate     = false;
    var isLnameDuplicate     = false;
    var isDobDuplicate       = false;
    var isDuplicate=false;

var childDOB=[];

    for(var x=0;x<firstmemberName.length;x++) {
    for(var count=firstmemberName.length-1;count>=0; count--)
	{

	  isFnameDuplicate     = false;
      isMnameDuplicate     = false;
      isLnameDuplicate     = false;
      isDobDuplicate       = false;

	if(firstmemberName[x].value==firstmemberName[count].value && count!=x ){
	   isFnameDuplicate=true;
	}
	if(middlememberName[x].value==middlememberName[count].value && count!=x){
	   isMnameDuplicate=true;
	}
	if(lastmemberName[x].value==lastmemberName[count].value && count!=x ){
	  isLnameDuplicate=true;
	}
	if(memdob[x].value==memdob[count].value && count!=x  ){
	  isDobDuplicate=true;
	}
if(isFnameDuplicate &&  isMnameDuplicate  && isLnameDuplicate  &&  isDobDuplicate ){
isDuplicate=true;
}
}   

if(checkBox[x].value == '1' && typeof status[x] !== "undefined" && status[x].value=='Off-line')
	  	{


var elements = document.getElementsByName( 'mstatus' );
var id = elements[x].getAttribute( 'id' );
var idVal=id.replace('check','remark'); 

if(document.getElementById(idVal).value==""){
	  	alert("Please enter remark while making dependent online");
	  	document.getElementById(idVal).focus();
	  	return false;
	  	}
	}

	  	if(checkBox[x].value == '0')
	  	{
	  		
	  		if(opType[x].value=='add' || opType[x].value=='remove')
	  		{
	   			tbl.deleteRow(x+1);
		   	  	opType[x].value='remove';
	   		}
	   		else if((opType[x].value !='add' || opType[x].value !='remove') && status[x].value && status[x].value=='On-line' &&  checkBox[x].value == '0' )
	   		{
	   			opType[x].value='delete';
	   		}
	  	}

	  	if(checkBox[x].value == '1' &&status[x]&& status[x].value=='Off-line' ){
	   			opType[x].value='online';
	  	}

	   	$('#famIndex').val(x);

	/*   	if(memberName[x].value=='')
	   	{
	  		alert("Please fill the family member name");
	  		memberName[x].focus();
	  		return false;
	  	} */

	  	if(firstmemberName[x].value=='')
	   	{
	  		alert("Please fill the family member first name");
	  		firstmemberName[x].focus();
	  		return false;
	  	}

	  	if(x==0){
	  	validFristName=firstmemberName[x].value+' '+middlememberName[x].value+' '+lastmemberName[x].value;
	  	validDob=memdob[x].value;
	  	}
	  	else{
	  	validFristName=validFristName+"@"+firstmemberName[x].value+' '+middlememberName[x].value+' '+lastmemberName[x].value
	  	validDob=validDob+'@'+memdob[x].value;
	  	}
	  	
	  	
     /*    	if(lastmemberName[x].value=='')
	   	{
	  		alert("Please fill the family member last name");
	  		lastmemberName[x].focus();
	  		return false;
	  	}  */
	  	
	 	if(memGender[x].value=='Select' || memGender[x].value==-1)
  	 	{
  			alert("Please select the Gender");
  			memGender[x].focus();
  			return false;
  	 	}
  	 	
  	 	if(memdob[x].value=='')
  	 	{
  			alert("Please select the member's date of birth");
  			memdob[x].focus();
  			return false;
  	 	}
  	 	
  	 	if(!isGreaterThanTodayDate(memdob[x]))
  	 	{
        	return false;
     	}
     
     		
   	    if(memRelation[x].value=='' || memRelation[x].value==-1)
   	    {
  			alert("Please select the Relation");
  			memRelation[x].focus();
  			return false;
  	 	}
  	 
  	 	if(memMaritalStatus[x].value=='' || memMaritalStatus[x].value==-1)
  	 	{
  			alert("Please select the Marital Status");
  			memMaritalStatus[x].focus();
  			return false;
  	 	}	
  	 	
  	 	if(ersFamilyPrintName[x].value=='')
  	 	{
  			alert("Please fill the ERS Print Name");
  			ersFamilyPrintName[x].focus();
  			return false;
  	 	}

  	 	if(ersFamilyPrintName[x].value.length>15)
  	 	{
  			alert("ERS Name can't be greater than 15 letters");
  			ersFamilyPrintName[x].focus();
  			return false;
  	 	}
	  	
	  	if(!checkNoneChar(ersFamilyPrintName[x].value))
	  	{
  			alert("ERS Name should be between a-z Or A-Z"); 
  			ersFamilyPrintName[x].focus();
  			return false;
  	 	}
  	 	
  	 	 if(doIIPartNo[x].value != ""){
			if(!validateDoIIPartNo(doIIPartNo[x].value)){
				alert("Special characters !#$%^*' not allowed");
				document.getElementById("doIIPartNo" + i).focus();
				return false;
			}
		 }
  	 	
   	 	if((doIIPartNo[x].value=="" || doIIDate[x].value=="") &&  memReson[x].value=="")
   	 	{
				if(serviceName=="Navy"){
					alert("Enter either( GX NO & GX Date ) or Reason . Do not fill all three fields.");
				}else if(serviceName=="Air Force"){
					alert("Enter either ( POR No & POR Date ) or Reason . Do not fill all three fields.");
				}
				else{
  			alert("Either enter ( DOII Part No and DOII Date ) OR Reason . Do not fill all three fields.");
				}
			
  			memReson[x].readOnly=false;
  			return false;
  	 	}
  		
  	 	
  		if((doIIPartNo[x].value!="" || doIIDate[x].value!="") &&  memReson[x].value!=""){
			  
			 if(serviceName=="Navy"){
					alert("Enter either ( GX NO & GX Date ) or Reason . Do not fill all three fields.");
				}else if(serviceName=="Air Force"){
					alert("Enter either ( POR No & POR Date ) or Reason . Do not fill all three fields.");
				}
				else{
  			alert("Either enter ( DOII Part No and DOII Date ) OR Reason . Do not fill all three fields.");
				}
			  return false;
		  }
  	 	
		var do2date = doIIDate[x].value.split("/");
  	    var dateformatDate2 = new Date(do2date[2]+","+(do2date[1])+","+do2date[0]);
  	    if(dateformatDate2>=new Date()){
	           
	            if(serviceName=="Navy"){
					alert("GX Date should be less than or equal to today's date");
				}else if(serviceName=="AirForce"){
					alert("POR Date should be less than today date");
				}else{	  
			      alert("DOII Date should be less than today date");
		        }
		        
  		memReson[x].readOnly=false;
  		return false;
  		}
  		
  			var docdate=dateOfCom_join.split("/");
            var docDateFormat=new Date(docdate[2]+","+(docdate[1])+","+docdate[0]);
  if(docDateFormat>dateformatDate2){
	 
	   if(serviceName=="Navy"){
					alert("GX Date should be greater than Date of Enrollenment date");
				}else if(serviceName=="AirForce"){
					alert("POR Date should be greater than Date of Enrollenment date");
				}else{	  
			      alert("DOII Date should be greater than Date of Enrollenment date");
		        }
  		memReson[x].readOnly=false;
  		return false;
  }
  		
    //-------------------- DOII AND DEPENDENT DOB VALIDATION START  --------//
		var do2date = doIIDate[x].value.split("/");
		var dateformatDate2 = new Date(do2date[2] + "," + (do2date[1]) + "," + do2date[0]);
		var memberDob = memdob[x].value.split("/");
		var memberFormatDob = new Date(memberDob[2] + "," + (memberDob[1]) + "," + memberDob[0]);


		if (memberFormatDob > dateformatDate2) {
			alert("DOII date should not be prior to Dependent's DOB");
			return false;
		}

    //-----------------VALIDATION ENDS ------------------------------------//
    
    
    
  
  
  
  
  
  
  
  		
  		
	 	if(memRelation[x].value==4 || memRelation[x].value==5 || memRelation[x].value==3 || memRelation[x].value==2)
	 	{
	 	
		    if(new Date(memdob[x].value).getTime()<=new Date(dateOfBirth).getTime())
		    {
	 			alert("Child Date Of Birth should be less than traveler Date Of Birth");
	 			return false;
	 		}
	 	}
	 
	 	if(memRelation[x].value==8 || memRelation[x].value==9 || memRelation[x].value==10 || memRelation[x].value==11)
	 	{
	 
		   	if(new Date(memdob[x].value).getTime()>new Date(dateOfBirth).getTime())
		   	{
	 			alert("Father/Mother date of birth should be greater than traveler date of birth");
		 		return false;
	 		}
	 	}
  	 
	  	 var question="";
  		
  		if(isDepen[x].value=='')
  		{
  		
  			if((memRelation[x].value==4 || memRelation[x].value==5)  && memMaritalStatus[x].value==0 )
  			{
		  		 $("input[name^=incomeRange1]").removeAttr("checked");
	  			 $("input[name^=isSept1]").removeAttr("checked");
	  			 $("input[name^=isDep1]").removeAttr("checked");
		  		 $("input[name^=isDepAsTR1]").removeAttr("checked");
	    	     $('#isSeprt').hide();
	        	 $('#whlyDep1').hide();
		         $('#DepPerTR1').hide();
	   		     $('#depQues').show();
	  			 $('#incmRang1').show();
		  		 $('#daughMarried').show();
	  			 $('#famIndex').val(x);
	  			 return false;
  			}
  		
  			if(memRelation[x].value==7 && memMaritalStatus[x].value==1)
  			{
		  		$("input[name^=isParAliv3]").removeAttr("checked");
				$("input[name^=isUnm3]").removeAttr("checked");
	  		  	$("input[name^=isPartDep3]").removeAttr("checked");
	  		  	$("input[name^=isResPer3]").removeAttr("checked");
	  		  	$("input[name^=incomeRange3]").removeAttr("checked");
	  		  	$('#isUnmarr3').hide();
	          	$('#isParAlv3').hide();
		  		$('#isParDep3').hide();
		        $('#isResPers3').hide();
		  		$('#incmRange3').show();
		  		$('#sister').show();
		  		$('#depQues').show();
		  		$('#famIndex').val(x);
  		  		return false;
  			}
	  		if(memRelation[x].value==7 && memMaritalStatus[x].value==0)
  			{
		  		$("input[name^=incomeRange5]").removeAttr("checked");
		  		$("input[name^=isSep5]").removeAttr("checked");
		  		$("input[name^=isParAliv5]").removeAttr("checked");
		  		$("input[name^=isPartDep5]").removeAttr("checked");
		  		$("input[name^=isResPer5]").removeAttr("checked");
		  		$("input[name^=depAsPerTR5]").removeAttr("checked");
		  		$('#depPerTR5').hide();
		        $('#sept5').hide();
		  		$('#isParAlv5').hide();
		        $('#isParDep5').hide();
		  		$('#isResPers5').hide();
		  		$('#incmRange5').show();
		  		$('#sister').show();
		  		$('#depQues').show();
		  		$('#famIndex').val(x);
		  		return false;
  			}
  	
  		  		
	  		if(memRelation[x].value==6 && memMaritalStatus[x].value==1)
	  		{
	  		 	$("input[name^=incomeRange2]").prop('checked',false);
	  		 	$("input[name^=isParAlive2]").prop('checked',false);
	  		 	$("input[name^=isParsDep2]").prop('checked',false);
	  		 	$("input[name^=isResPer2]").prop('checked',false);
	  		 	$("input[name^=isDepAsTR2]").prop('checked',false);
	  		 
	  		  	$('#isparAlv2').hide();
	          	$('#isParDep2').hide();
	  		  	$('#isResPers2').hide();
	  		  	$('#DepPerTR2').hide();
	  		    
	  		  	$('#incmrange2').show();
	  		  	$('#broUnmarried').show();
	  		  	$('#depQues').show();
	  		  	$('#famIndex').val(x);
	  		 	return false;
	  		}
  		
	  		if(memRelation[x].value==6 && memMaritalStatus[x].value==0)
	  		{
	  		 	//$('#famIndex').val(x);
	  		 	closeEditWindow("","no")
	  		 	return false;
	  		}
	  		if(memRelation[x].value==8 || memRelation[x].value==9 || memRelation[x].value==10 || memRelation[x].value==11)
	  		{
	  		   $("input[name^=incomeRange4]").prop('checked',false);
	  		   $("input[name^=isResPer4]").prop('checked',false);
	  		   $("input[name^=depAsPerTR4]").prop('checked',false);
	  		     
	  		   $('#isResPers4').hide();
	           $('#depPerTR4').hide();
	  		   
	  		   $('#incmRange4').show();
	  		   $('#parStepPar').show();
	  		   $('#depQues').show();
	  		   $('#famIndex').val(x);
	  		 return false;
	  		}
	  		if((memRelation[x].value==2 || memRelation[x].value==3) && memMaritalStatus[x].value==0){
	  		 //$('#famIndex').val(x);
	  		 closeEditWindow("","no")
	  		 return false;
	  		}
	  		
	  		if(memRelation[x].value!='1')
	  		{
		  		 $("input[name^=incomeRange6]").prop('checked',false);
		         $("input[name^=depAsPerTR6]").prop('checked',false);
		         $('#depPerTR6').hide();
		         $('#incmRange6').show();
		  		 $('#depQues').show();
		  		 $('#famIndex').val(x);
		  		 return false;
	  		}
  		
  		}
  		
  		if((memRelation[x].value==2 || memRelation[x].value==3 || memRelation[x].value==4 ||memRelation[x].value==5) && $.inArray(memdob[x].value,childDOB)=== -1){
				childDOB.push(memdob[x].value);
		}
			
		if(childDOB.length > 2){
			alert("Children relation should not be more than 2.");
			return false;
		}
  		
 		if(memRelation[x].value==1 && spouseCheck==true)
   	 	{
   	 		alert("Spouse relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation[x].value==10 && fatherCheck==true)
   	 	{
   	 		alert("Father relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation[x].value==11 && motherCheck==true)
   	 	{
   	 		alert("Mother relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation[x].value==8 && stepMotherCheck==true)
   	 	{
   	 		alert("Step Mother relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation[x].value==9 && stepFatherCheck==true)
   	 	{
   	 		alert("Step Father relation should be inserted only once");
   	 		return false;
   	 	}
  		
  		if(memRelation[x].value==1)
  		spouseCheck=true;
  		
  		if(memRelation[x].value==10)
  		fatherCheck=true;
  		
  		if(memRelation[x].value==11)
  		motherCheck=true;
  		
  		if(memRelation[x].value==8)
  		stepMotherCheck=false;
  		
  		if(memRelation[x].value==9)
  		stepFatherCheck=false;
  	
  		}
 //alert(isFnameDuplicate +"1"+  isMnameDuplicate +"2"+ isLnameDuplicate +"3"+ isGenderDuplicate +"4"+  isDobDuplicate +"5"+ isRelationDuplicate +"6"+ isMaritalStatusDuplicate +"7"+ isDoIIPartNoDuplicate +"8"+ isDoIIPartDateDuplicate +"9"+ isResonDuplcate +"10"+ isErsPrintNameDuplicate)
  //if(isFnameDuplicate &&  isMnameDuplicate  && isLnameDuplicate && isGenderDuplicate &&  isDobDuplicate && isRelationDuplicate && isMaritalStatusDuplicate && isDoIIPartNoDuplicate && isDoIIPartDateDuplicate && isResonDuplcate && isErsPrintNameDuplicate)
   if(isDuplicate )
    {
    alert("Dependent details are duplicate");
    return false;
    }
   /* if(isOfflineDuplicateDependent(validFristName,validDob,$("#userAlias").val())){
    alert("Dependent details are present in offline mode");
    return false;
    }
    */


  
	event.currentTarget.submit();

}

 function checkemail(str){
 
 var filter=/^.+@.+\..{2,3}$/
 if (filter.test(str))
    return true
 else {
    alert("Please enter a valid email address")
    return false
}

}

 function isValidMobileNumber(name){
	var iChars = "0123456789";
	if(name!="")
	{
    	 // alert(name.length);
		for (var i = 0; i < name.length; i++) 
		{
        	if (iChars.indexOf(name.charAt(i)) == -1){
			return false;
     		}
   		}
	}
		return true;
 }
 
 function isValidStdCode(name)
 {
	var iChars = "0123456789";
	if(name!="")
	{
		if(name.length<3)
		return false;
		for (var i = 0; i < name.length; i++) 
		{
        	if (iChars.indexOf(name.charAt(i)) == -1)
        	{
				return false;
     		}
   		}
	}
		return true;
 }

function addEditRow(){
var serviceName=document.getElementById('ServiceName').value;
var dateOfCom_join=$("#dateOfCom_join").val();
    var spouseCount=1;
     
  	var tbl = document.getElementById('editFamilyDetails');
  
  	var lastRow = tbl.rows.length;
  	document.getElementById("flag").value="true";
	
    var memberName;	var memGender; var memdob;	var memRelation;	var memMaritalStatus;
  	var doIIPartNo;	var doIIDate;	var memReson; var isDepen; var countChildDep=0;
  	var ersFamilyPrintName; var firstmemberName; var middlememberName; var lastmemberName;
    
 	// memberName=document.getElementsByName("firstmemberName"); 
 	 firstmemberName=document.getElementsByName("firstmemberName");
 	 middlememberName=document.getElementsByName("middlememberName");
 	 lastmemberName=document.getElementsByName("lastmemberName");

 	 memGender=document.getElementsByName("memGender");
 	 memdob=document.getElementsByName("memdob");  
  	 memRelation=document.getElementsByName("memRelation");
   	 memMaritalStatus=document.getElementsByName("memMaritalStatus");
   	 doIIPartNo=document.getElementsByName("doIIPartNo");
  	 doIIDate=document.getElementsByName("doIIDate");
  	 memReson=document.getElementsByName("memReson");
  	 isDepen=document.getElementsByName("isDepen");
  	 
  	 ersFamilyPrintName=document.getElementsByName("ersPrintName");
  	
  	
   	 for(var x=0;x<firstmemberName.length;x++){
   	
   	 $('#famIndex').val(x);
   	/* if(memberName[x].value==''){
  			alert("Please fill the family member name");
  			memberName[x].focus();
  			return false;
  	 } */
  	 
  	 if(firstmemberName[x].value==''){
  			
  			alert("Please fill the family member first name");
  			firstmemberName[x].focus();
  			return false;
  	 }
  	
  /*	if(lastmemberName[x].value==''){
  		    
  			alert("Please fill the family member last name");
  			lastmemberName[x].focus();
  			return false;
  	 } */
  	
  	 if(memGender[x].value=='Select'){
  			alert("Please select the Gender");
  			memGender[x].focus();
  			return false;
  	 }
  	 if(memdob[x].value==''){
  			alert("Please select the member's date of birth");
  			memdob[x].focus();
  			return false;
  	 }
  	 if(!isGreaterThanTodayDate(memdob[x])){
        return false;
     }
   
     if(memRelation[x].value==''){
  			alert("Please select the Relation");
  			memRelation[x].focus();
  			return false;
  	 }
  	
  	 
  	 if(memRelation[x].value==1)
  	 {
  	 if(memRelation[x].value==1 && document.getElementById("spouseCheck").value=='true' && spouseCount>1 && isDepen[x].value==''){
  		alert("You have already entered Spouse Information");
  		var tbl = document.getElementById('editFamilyDetails');

		var rowNum=parseInt(x)+1;

		tbl.deleteRow(rowNum);
  		return false;
  		}
  		else
  		spouseCount++
  	 }
  		
  		if(memRelation[x].value==10 && document.getElementById("fatherCheck").value=='true' && isDepen[x].value==''){
  		alert("You have already entered Father Information");
  		var tbl = document.getElementById('editFamilyDetails');

		var rowNum=parseInt(x)+1;

		tbl.deleteRow(rowNum);
  		return false;
  		}
  		
	  	 if(memRelation[x].value==11 && document.getElementById("motherCheck").value=='true' && isDepen[x].value==''){
	  		alert("You have already entered Mother Information");
	  		var tbl = document.getElementById('editFamilyDetails');

		var rowNum=parseInt(x)+1;

		tbl.deleteRow(rowNum);
	   		return false;
	  	 }
  	 
  	 	if(memRelation[x].value==9 && document.getElementById("stepFatherCheck").value=='true' && isDepen[x].value==''){
  		alert("You have already entered Step Father Information");
  		var tbl = document.getElementById('editFamilyDetails');

		var rowNum=parseInt(x)+1;

		tbl.deleteRow(rowNum);
  		return false;
  		}
  		
  		if(memRelation[x].value==8 && document.getElementById("stepMotherCheck").value=='true' && isDepen[x].value==''){
  		alert("You have already entered Step Mother Information");
  		var tbl = document.getElementById('editFamilyDetails');

		var rowNum=parseInt(x)+1;

		tbl.deleteRow(rowNum);
  		return false;
  		}
  		
  		
  
  	 if(memMaritalStatus[x].value==''){
  			alert("Please select the Marital Status");
  			memMaritalStatus[x].focus();
  			return false;
  	 }
  	 
  	 if(ersFamilyPrintName[x].value==''){
  			alert("Please fill the ERS Print Name");
  			ersFamilyPrintName[x].focus();
  			return false;
  	 }
  	 if(ersFamilyPrintName[x].value.length>15){
  		alert("ERS Name can't be greater than 15 letters");
  		ersFamilyPrintName[x].focus();
  		return false;
  	 }
  	 if(!checkNoneChar(ersFamilyPrintName[x].value)){
  		alert("ERS Name should be between a-z Or A-Z"); 
  		ersFamilyPrintName[x].focus();
  		return false;
  	 }
  	 
  	  if(doIIPartNo[x].value != ""){
		if(!validateDoIIPartNo(doIIPartNo[x].value)){
			alert("Special characters !#$%^* not allowed");
			document.getElementById("doIIPartNo" + i).focus();
			return false;
		}
	 }
  	 
   	 if((doIIPartNo[x].value=="" || doIIDate[x].value=="") &&  memReson[x].value==""){
			if(serviceName=="Navy"){
					alert("Enter either ( GX NO & GX Date ) or Reason. Do not fill all three fields.");
				}else if(serviceName=="Air Force"){
					alert("Enter either ( POR No & POR Date ) or Reason. Do not fill all three fields.");
				}
				else{
  		alert("Either enter ( DOII Part No and DOII Date) OR Reason. Do not fill all three fields.");
				}
			
  
  		memReson[x].readOnly=false;
  		return false;
  	 }
  	 
  	 	 	
		var do2date = doIIDate[x].value.split("/");
  	    var dateformatDate2 = new Date(do2date[2]+","+(do2date[1])+","+do2date[0]);
  	    if(dateformatDate2>=new Date()){
	           
	            if(serviceName=="Navy"){
					alert("GX Date should be less than or equal to today's date");
				}else if(serviceName=="AirForce"){
					alert("POR Date should be less than today date");
				}else{	  
			      alert("DOII Date should be less than today date");
		        }
		        
  		memReson[x].readOnly=false;
  		return false;
  		}
  		
  			var docdate=dateOfCom_join.split("/");
    var docDateFormat=new Date(docdate[2]+","+(docdate[1])+","+docdate[0]);
  if(docDateFormat>dateformatDate2){
	 
	   if(serviceName=="Navy"){
					alert("GX Date should be greater than Date of Enrollment date");
				}else if(serviceName=="AirForce"){
					alert("POR Date should be greater than Date of Enrollment date");
				}else{	  
			      alert("DOII Date should be greater than Date of Enrollment date");
		        }
  		memReson[x].readOnly=false;
  		return false;
  }
  	 
  	 
    //-------------------- DOII AND DEPENDENT DOB VALIDATION START  --------//
  var do2date = doIIDate[x].value.split("/");
  var dateformatDate2 = new Date(do2date[2]+","+(do2date[1])+","+do2date[0]);
  var memberDob  = memdob[x].value.split("/");
  var memberFormatDob = new Date(memberDob[2]+","+(memberDob[1])+ ","+memberDob[0]);
  
  
  if(memberFormatDob > dateformatDate2){
	  alert("DOII date should not be prior to Dependent's DOB");
	  return false;
  }
  //-----------------VALIDATION ENDS ------------------------------------//
  
  
  
  
  
  
  

  	 
  	 

  	if(memRelation[x].value==4 || memRelation[x].value==5 || memRelation[x].value==3 || memRelation[x].value==2){
	var dateOfBirth=$("#dateOfBrth").val();
	    if(new Date(memdob[x].value).getTime()<=new Date(dateOfBirth).getTime()){
	 		alert("Child date of birth should be less than traveler date of birth");
	 		return false;
	 	}
	 }

	 if(memRelation[x].value==8 || memRelation[x].value==9 || memRelation[x].value==10 || memRelation[x].value==11){
	var dateOfBirth=$("#dateOfBrth").val();
	   	if(new Date(memdob[x].value).getTime()>new Date(dateOfBirth).getTime()){
	 		alert("Father/Mother Date Of Birth should be greater than traveler Date Of Birth");
	 		return false;
	 	}
	 }
	 
	 
	 if(memRelation[x].value==1) 
 	  			$('#spouseCheck').val('true');
 	  						
  	var question="";
	
  		if(isDepen[x].value==''){
  		
  		if((memRelation[x].value==4 || memRelation[x].value==5)  && memMaritalStatus[x].value==0 )
  		{
  		 $("input[name^=incomeRange1]").removeAttr("checked");
  		 $("input[name^=isSept1]").removeAttr("checked");
  		 $("input[name^=isDep1]").removeAttr("checked");
  		 $("input[name^=isDepAsTR1]").removeAttr("checked");
         $('#isSeprt').hide();
         $('#whlyDep1').hide();
         $('#DepPerTR1').hide();
   	     $('#depQues').show();
  		 $('#incmRang1').show();
  		 $('#daughMarried').show();
  		 $('#famIndex').val(x);
  		 return false;
  		}
  		
  		if(memRelation[x].value==7 && memMaritalStatus[x].value==1)
  		{
  		  $("input[name^=isParAliv3]").removeAttr("checked");
  		  $("input[name^=isUnm3]").removeAttr("checked");
  		  $("input[name^=isPartDep3]").removeAttr("checked");
  		  $("input[name^=isResPer3]").removeAttr("checked");
  		  $("input[name^=incomeRange3]").removeAttr("checked");
  		  $('#isUnmarr3').hide();
          $('#isParAlv3').hide();
  		  $('#isParDep3').hide();
          $('#isResPers3').hide();
  		  $('#incmRange3').show();
  		  $('#sister').show();
  		  $('#depQues').show();
  		  $('#famIndex').val(x);
  		  return false;
  		}
  		if(memRelation[x].value==7 && memMaritalStatus[x].value==0)
  		{
  		$("input[name^=incomeRange5]").removeAttr("checked");
  		 $("input[name^=isSep5]").removeAttr("checked");
  		 $("input[name^=isParAliv5]").removeAttr("checked");
  		 $("input[name^=isPartDep5]").removeAttr("checked");
  		  $("input[name^=isResPer5]").removeAttr("checked");
  		  $("input[name^=depAsPerTR5]").removeAttr("checked");
  		  
  		  $('#depPerTR5').hide();
          $('#sept5').hide();
  		  $('#isParAlv5').hide();
          $('#isParDep5').hide();
  		 $('#isResPers5').hide();
  		 $('#incmRange5').show();
  		 $('#sister').show();
  		 $('#depQues').show();
  		  $('#famIndex').val(x);
  		  return false;
  		}
  	
  		  		
  		if(memRelation[x].value==6 && memMaritalStatus[x].value==1)
  		{
  		 $("input[name^=incomeRange2]").prop('checked',false);
  		 $("input[name^=isParAlive2]").prop('checked',false);
  		 $("input[name^=isParsDep2]").prop('checked',false);
  		 $("input[name^=isResPer2]").prop('checked',false);
  		 $("input[name^=isDepAsTR2]").prop('checked',false);
  		 
  		  $('#isparAlv2').hide();
          $('#isParDep2').hide();
  		  $('#isResPers2').hide();
  		  $('#DepPerTR2').hide();
  		    
  		  $('#incmrange2').show();
  		  $('#broUnmarried').show();
  		  $('#depQues').show();
  		  $('#famIndex').val(x);
  		 return false;
  		}
  		
  		if(memRelation[x].value==6 && memMaritalStatus[x].value==0)
  		{
  		 //$('#famIndex').val(x);
  		 closeEditWindow("","no")
  		 return false;
  		}
  		if(memRelation[x].value==8 || memRelation[x].value==9 || memRelation[x].value==10 || memRelation[x].value==11)
  		{
  		   $("input[name^=incomeRange4]").prop('checked',false);
  		   $("input[name^=isResPer4]").prop('checked',false);
  		   $("input[name^=depAsPerTR4]").prop('checked',false);
  		     
  		   $('#isResPers4').hide();
           $('#depPerTR4').hide();
  		   
  		   $('#incmRange4').show();
  		   $('#parStepPar').show();
  		   $('#depQues').show();
  		   $('#famIndex').val(x);
  		 return false;
  		}
  		if((memRelation[x].value==2 || memRelation[x].value==3) && memMaritalStatus[x].value==0){
  		 //$('#famIndex').val(x);
  		 closeEditWindow("","no")
  		 return false;
  		}
  		if(memRelation[x].value!='1'){
  		
  		 $("input[name^=incomeRange6]").prop('checked',false);
         $("input[name^=depAsPerTR6]").prop('checked',false);
         $('#depPerTR6').hide();
         $('#incmRange6').show();
  		 $('#depQues').show();
  		 $('#famIndex').val(x);
  		 return false;
  		}
  		}
  
  		 }
     var row=tbl.insertRow(lastRow);
	 var cellRight = row.insertCell(0);
	 
	 var el = document.createElement('input');
	 el.type = 'text';
	
	 el.name = 'firstmemberName';
	 el.id = 'firstmemberName';
	 el.className = "txtfldauto";
	 el.setAttribute("placeholder","First");
	 el.setAttribute("onkeydown","checkSplChar1(event)")  //Adding event for special char check
	  el.setAttribute("onblur","setPrintERSForDependents("+x+")")
	 $(el).attr("autocomplete", "off");
	
 	cellRight.appendChild(el);
	
	 
	var cellRight = row.insertCell(1);	  
	 
	 var e2 = document.createElement('input');
	 e2.type = 'text';
	
	 e2.name = 'middlememberName';
	 e2.id = 'middlememberName';
	 e2.className = "txtfldauto";
	 e2.setAttribute("placeholder","Middle");
	 e2.setAttribute("onkeydown","checkSplChar1(event)")  //Adding event for special char check
	 e2.setAttribute("onblur","setPrintERSForDependents("+x+")")
	 $(e2).attr("autocomplete", "off");
	 cellRight.appendChild(e2);
	
	 
	 var cellRight = row.insertCell(2);
	 
	 var e3 = document.createElement('input');
	 e3.type = 'text';
	
	 e3.name = 'lastmemberName';
	 e3.id = 'lastmemberName';
	 e3.className = "txtfldauto";
	 e3.setAttribute("placeholder","Last");
	 e3.setAttribute("onkeydown","checkSplChar1(event)")  //Adding event for special char check
	 e3.setAttribute("onblur","setPrintERSForDependents("+x+")")
	 $(e3).attr("autocomplete", "off");
	 cellRight.appendChild(e3);
	
	var cellRight = row.insertCell(3);
	var textNode = document.createElement('select');  
	textNode.options[0]=new Option("Select","Select");
	var enumGend = document.getElementById('genderFamilyEnum').value; 
	  
	var gendCode; 
	var spltedVals=enumGend.split(',');
	if(spltedVals.length>0){
	  for(var i=0;i<spltedVals.length-1;i++)
  		  		{
	  	 var gendArr=spltedVals[i].split("::");
  		 textNode.options[i+1]=new Option(gendArr[0],gendArr[1]);
  	  }
  	}	  	    
	textNode.name = 'memGender';
	textNode.id = 'memGender'; 
	textNode.className = "comboauto";  
	textNode.setAttribute("onchange","setDepRelationAccToGender(this.value,'"+x+"');")
	 
	cellRight.appendChild(textNode);
	  
	var cellRight = row.insertCell(4);
	cellRight.width=10;
	var el = document.createElement('input');
	el.type = 'text';
	el.className = "txtfldauto"; 
	el.name = 'memdob';
	el.id = 'dob';
	el.setAttribute('readonly','readonly');  
	el.size = 10;
	 $(e1).attr("autocomplete", "off");
	cellRight.appendChild(el);



	var cellRight = row.insertCell(5);	  
	var textNodeDiv=document.createElement('div')
	textNodeDiv.id="reltionDiv"+x
	var textNode = document.createElement('select');  
	textNode.options[0]=new Option("Select","Select");
	
	var enumRel = document.getElementById('relationFamilyEnum').value; 
	var relCode; 
	  
	var spltVals=enumRel.split(',');
	if(spltVals.length>0){
	  for(var i=0;i<spltVals.length-1;i++)
  		  		{
  		 var relArr=spltVals[i].split("::");
  	     textNode.options[i+1]=new Option(relArr[0],relArr[1]);
  	  }
  	}	  	    
	textNode.name = 'memRelation';
	textNode.id = 'memRelation'; 
	textNode.className = "comboauto";  
	textNode.setAttribute("onchange","setDepMarStatAccToRelation(this,'"+x+"');")
	textNodeDiv.appendChild(textNode)
	cellRight.appendChild(textNodeDiv);
	  
	var cellRight = row.insertCell(6);	
	var marStatDivNode=document.createElement('div')
	marStatDivNode.id="maritalStatDiv"+x
	var textNode = document.createElement('select');  
	textNode.options[0]=new Option("Select","Select");
	var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	var marCode; 
	var spltedVals=enumMar.split(',');
	if(spltedVals.length>0){
	  for(var i=0;i<spltedVals.length-1;i++)
  		  		{
	  		 var marArr=spltedVals[i].split("::");
	  	     textNode.options[i+1]=new Option(marArr[0],marArr[1]);
  	    }
    }	  	    
	textNode.name = 'memMaritalStatus';
	textNode.id = 'memMaritalStatus'; 
	textNode.className = "comboauto";  
	marStatDivNode.appendChild(textNode);
	cellRight.appendChild(marStatDivNode);
	 
	var cellRight = row.insertCell(7);
	cellRight.width=15;
	var el = document.createElement('input');
	el.type = 'text';
	el.className = "txtfldauto"; 
	el.name = 'doIIPartNo';
	el.id = 'doIIPartNo';
	el.setAttribute('onclick',"disableTextBox();");
	el.size = 10;
	 $(e1).attr("autocomplete", "off");
	cellRight.appendChild(el);
	
	var cellRight = row.insertCell(8);
	cellRight.width=15;
	var el = document.createElement('input');
	el.type = 'text';
	el.className = "txtfldauto"; 
	el.name = 'doIIDate';
	el.id = 'doIIDate';
	el.setAttribute('readonly','readonly');  
	el.setAttribute('onclick',"setDo2Date(this);");
	
	el.size = 10;
	 $(e1).attr("autocomplete", "off");
	cellRight.appendChild(el);
	
	var cellRight = row.insertCell(9);
	cellRight.width=15;
	var el = document.createElement('input');
	el.type = 'text';
	el.className = "txtfldauto"; 
	el.name = 'memReson';
	el.id = 'memReson';
	el.setAttribute('readonly','readonly');  
	 $(e1).attr("autocomplete", "off");
	cellRight.appendChild(el);
	
	var cellRight = row.insertCell(10);
	cellRight.width=16;
	var el = document.createElement('input');
	el.type = 'text';
	el.className = "txtfldauto"; 
	el.name = 'ersPrintName';
	el.setAttribute("readonly","readonly")
	el.size = 8;
	 el.id = 'ersFamilyPrintName'; 
	 $(e1).attr("autocomplete", "off");
	cellRight.appendChild(el);
	
	var cellRight = row.insertCell(11);
	cellRight.width=8;
	var el = document.createElement('input');
	el.type = 'checkbox';
	el.name="mstatus";
	el.id="depChkBox";
	el.size = 2;
    el.setAttribute('onclick',"hideDepDetails();");
    el.setAttribute('disabled','disabled');
	cellRight.appendChild(el);
	
	
	 // Hostel NRS Start
	
	var cellRight = row.insertCell(12);
     cellRight.width=16;

	 var e1 = document.createElement('input');
	 e1.type = 'text';
	 e1.className = "txtfldauto"; 
	 e1.name = 'childHostelNRS';
	 e1.setAttribute("autocomplete","off")
	 e1.size = 10;
	 e1.value = "";
	 e1.setAttribute("onblur","fillChildHostelNRS('','"+parseInt(x)+1+"');");
	 e1.setAttribute("onkeyup","getRailStationChild(this,'"+parseInt(x)+1+"');");
	 e1.setAttribute("placeholder","NRS");
	 e1.setAttribute('disabled','disabled');  
	 e1.id = 'childHostelNRS'+parseInt(x)+1; 

	 var e2 = document.createElement('div');
	 e2.appendChild(e1);

	 var e3 = document.createElement('div');
	 e3.className = "suggestionList";
	 e3.id = 'autoSuggestionsListRailStationChild'+parseInt(x)+1;

	 var e4 = document.createElement('div');
	 e4.className = "suggestionsBox";
	 e4.id = 'suggestionsRailStationChild'+parseInt(x)+1;
	 e4.style = 'display: none;';
	 e4.appendChild(e3);

	 var e5 = document.createElement('div');
	 e5.appendChild(e2);
	 e5.appendChild(e4);
	
	 cellRight.appendChild(e5);
     
     // Hostel NRS End
     
     // Hostel NAP Start
	 
	 var cellRight = row.insertCell(13);
     cellRight.width=16;

	 var e1 = document.createElement('input');
	 e1.type = 'text';
	 e1.className = "txtfldauto"; 
	 e1.name = 'childHostelNAP';
	 e1.setAttribute("autocomplete","off")
	 e1.size = 10;
	 e1.value = "";
	 e1.setAttribute("onblur","fillChildHostelNAP('','"+parseInt(x)+1+"');");
	 e1.setAttribute("onkeyup","getAirportChild(this,'"+parseInt(x)+1+"');");
	 e1.setAttribute("placeholder","NAP");
	 e1.setAttribute('disabled','disabled');  
	 e1.id = 'childHostelNAP'+parseInt(x)+1; 

	 var e2 = document.createElement('div');
	 e2.appendChild(e1);

	 var e3 = document.createElement('div');
	 e3.className = "suggestionList";
	 e3.id = 'autoSuggestionsListAirportChild'+parseInt(x)+1;

	 var e4 = document.createElement('div');
	 e4.className = "suggestionsBox";
	 e4.id = 'suggestionsAirportChild'+parseInt(x)+1;
	 e4.style = 'display: none;';
	 e4.appendChild(e3);

	 var e5 = document.createElement('div');
	 e5.appendChild(e2);
	 e5.appendChild(e4);
	
	 cellRight.appendChild(e5);
     
     // Hostel NAP End
	
	
	
	var cellRight = row.insertCell(14);
	cellRight.width=8;
	var e2 = document.createElement('input');
	e2.type = "hidden";
	e2.name="remark";
	e2.id="remark";
	e2.size = 2;
	cellRight.appendChild(e2);
	
	var cellRight = row.insertCell(15);
		cellRight.width=10;
		var butt = document.createElement('input');
		butt.type = 'button'; 
		butt.setAttribute("class","butn");
		butt.value='X';
		butt.onclick=function(){
		deleteCurrentRow(this);
		};
		cellRight.appendChild(butt);

	var el = document.createElement('input');
	el.type = 'hidden';
	el.className = "txtfldauto"; 
	el.name = 'isDepen';
	el.id = 'isDepen';
	cellRight.appendChild(el);
	
	var el = document.createElement('input');
	el.type = 'hidden';
	el.className = "txtfldauto"; 
	el.name = 'opType';
	el.id = 'opType';
	el.value='add';
	cellRight.appendChild(el);	  
	
	var el = document.createElement('input');
	el.type = 'hidden';
	el.className = "txtfldauto"; 
	el.name = 'seqNo';
	el.id = 'seqNo';
	el.value=' ';
	cellRight.appendChild(el);	 
	
	
	cellRight.width=10;
	var el = document.createElement('input');
	el.type = 'hidden';
	el.name = 'memSeqNo';
	el.id = 'memSeqNo';
	el.value="-1"
	cellRight.appendChild(el); 
	

	 
	
//	var depDob = document.getElementsByName('memdob');
//    var depDo2Date = document.getElementsByName('doIIDate');
//
//    for(var n=depDob.length;n>0;n--){
//
// 	  calendar2 = new Epoch('cal2','popup',depDob[n-1],false);
//  	  calendar2 = new Epoch('cal2','popup',depDo2Date[n-1],false);
//
//		}




  var depDob = $('input[name="memdob"]');
  var depDo2Date = $('input[name="doIIDate"]')
	 
	

 for(var n=0;n<depDob.length;n++){
	
	
	depDob.datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		yearStart:1930,
		onShow: function () {
			  $("#dob").val("");
		}
	 
	});
	 
 	
 	 
 	 
	depDo2Date.datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		
	});


 	}



  }
  

function deleteCurrentRow(obj) {
		var delRow = obj.parentNode.parentNode;
		var tbl = delRow.parentNode.parentNode;
		var rIndex = delRow.sectionRowIndex;
		var rowArray = new Array(delRow);
		deleteRows(rowArray);
		
		//maxcount--;	
}
	
function deleteRows(rowObjArray) {
			for (var i=0; i<rowObjArray.length; i++) {
				var rIndex = rowObjArray[i].sectionRowIndex;
				//alert("rIndex:"+rIndex);
				rowObjArray[i].parentNode.deleteRow(rIndex);
			}
		
}



function removeEditRow(obj){
 	var tbl = document.getElementById('editFamilyDetails');
  
  	var lastRow = tbl.rows.length;
  
    $('#editFamilyDetails tr:last').remove();
		
}

function getEditRetirementDate(){
    var now = new Date();
    
    var currentdate = (now.getDate() < 10 ? "0" + now.getDate().toString() : now.getDate().toString()) + "-" +

    (now.getMonth() < 10 ? "0" + (now.getMonth()+1).toString() : now.getMonth().toString()) + "-" + now.getFullYear().toString();
  		
  	var cdate=currentdate.split("-");
    	
    var currdate=new Date(cdate[2],cdate[1]-1,cdate[0]);
    var currYr=cdate[2];
    	
    currYr=parseInt(currYr)+0;


if(document.getElementById("dateOfBrth").value!="")
    {
    	var dob=document.getElementById("dateOfBrth").value.split("/");
    	var year=dob[2];
    	year=parseInt(year);
    	    	
    	if(year>=currYr){
	    	alert("Please select valid Date of Birth");
	    	document.getElementById("dob").focus();
	    	return false;
    	}
    	else{
    		if((currYr-year)>62||(currYr-year)<16){
	    	alert("It should not take less than 16 and not more than 62 years");
    		document.getElementById("dob").focus();
    		return false;
    	}
     }
   }
   
	if(document.getElementById("dateOfBrth").value!="")
	{
     	if(document.getElementById("retireAge").value!="")
    	{
    		var age=document.getElementById("retireAge").value;
       		var year=dob[2];
    		var month=dob[1];
    		var retireYear=parseInt(age)+parseInt(year); 
    		var birthMonth;
    		
    		if(dob[1]=='09')
    		birthMonth=9;
    		
    		else if(dob[1]=='08')
    		birthMonth=8; 
    	
    		else if(dob[1]=='07')
    		birthMonth=7; 
    	
    		else if(dob[1]=='06')
    		birthMonth=6; 
    	
    		else if(dob[1]=='05')
    		birthMonth=5; 
    	
    		else if(dob[1]=='04')
    		birthMonth=4; 
    	
    		else if(dob[1]=='03')
    		birthMonth=3; 
    	
    		else if(dob[1]=='02')
    		birthMonth=2; 
    	
    		else if(dob[1]=='01')
    		birthMonth=1; 
    	
    		else if(dob[1]!='09' && dob[1]!='08' && dob[1]!='07' && dob[1]!='06' && dob[1]!='05' && dob[1]!='04' && dob[1]!='03' && dob[1]!='02' && dob[1]!='01')
    		birthMonth=parseInt(dob[1]);
    	
    	
   	 		var birthDate;
    	
    		if(dob[0]=='09')
    		birthDate=9;
    	
    		else if(dob[0]=='08')
    		birthDate=8; 
    	
    		else if(dob[0]=='07')
    		birthDate=7; 
    	
    		else if(dob[0]=='06')
    		birthDate=6; 
    	
    		else if(dob[0]=='05')
    		birthDate=5; 
    	
    		else if(dob[0]=='04')
    		birthDate=4; 
    	
    		else if(dob[0]=='03')
    		birthDate=3; 
    	
    		else if(dob[0]=='02')
    		birthDate=2; 
    	
    		else if(dob[0]=='01')
    		birthDate=1; 
    	
    		else if(dob[0]!='09' && dob[0]!='08' && dob[0]!='07' && dob[0]!='06' && dob[0]!='05' && dob[0]!='04' && dob[0]!='03' && dob[0]!='02' && dob[0]!='01')
    		birthDate=parseInt(dob[0]);
    	
    	
	    	if(birthMonth==1&&birthDate==1)
	    	{
		    		retireYear=retireYear-1;
	    			birthMonth=12;
	    			birthDate=31;
	    	}
    	
	    	if(birthMonth!=1&&birthDate==1)
	    	{
	 	   			if(birthMonth==5||birthMonth==7||birthMonth==10||birthMonth==12)
	    				birthDate=30;
	    	
			    	if(birthMonth==3)
			    	{
			    				if(retireYear%4==0)
			    				birthDate=29;
			    				else    
			    				birthDate=28;
			    	}
	    	
	    	
			    	if(birthMonth==2||birthMonth==4||birthMonth==8||birthMonth==6||birthMonth==9||birthMonth==11)
			    		birthDate=31;
	    	
	    		birthMonth=birthMonth-1;
	    	}
    	
    	
	    	if(birthDate!=1)
	    	{
	    			if(birthMonth==1||birthMonth==3||birthMonth==5||birthMonth==7||birthMonth==8||birthMonth==10||birthMonth==12)
	    				birthDate=31;
	   	
			   		if(birthMonth==2)
			    	{
			    				if(retireYear%4==0)
			    				birthDate=29;
			    				else
			    				birthDate=28;
			    	}
	    	
			    	if(birthMonth==4||birthMonth==6||birthMonth==9||birthMonth==11)
			    			birthDate=30;
			       		
			 }
			 
			 var ttt=birthDate+'/'+birthMonth+'/'+retireYear;
       		$("#dateOfRtirement").val(ttt);
  		}
    	
    }
}



	function closeEditWindow(divValue,check)
	{

	    var idx=$('#famIndex').val();
   
   		var flag=$('#flag').val();
 
 		var isDepen=document.getElementsByName('isDepen');
  		var opType=document.getElementsByName('opType');
  	
  //	alert(opType.length);
  		var k=opType.length;
   		var tbl = document.getElementById('editFamilyDetails');
  		var lastRow = tbl.rows.length;
  		var memRelation=document.getElementsByName('memRelation');
  		var motherCheck=document.getElementById('motherCheck');
    	var fatherCheck=document.getElementById('fatherCheck');
     	if(check=='no')
     	{
     		alert("This Dependant will not allow to do Journey");
   
 		    isDepen[idx].value=2;
    
    	 	var rowNum=parseInt(idx)+1;
    
     		var rowNumber=document.getElementById('rowNumber');
     
     		$('#rowNumber').val(rowNum);
   
			//$('#editFamilyDetails').hide(parseInt(idx)+1);
			//tbl.rows[rowNum].style.display = 'none';
   			tbl.deleteRow(rowNum);
			//alert(rowNum);
			//opType[k].value='delete';
		
		    //document.getElementById("editFamilyDetails").deleteRow(parseInt(idx)+1);
     	
     }
     
     if(check=='yes')
     {
 	 		alert("This Dependant is eligible to do Journey");
 	
 		 	if(memRelation[idx].value==11)
 	  			$('#motherCheck').val('true');
 	 		if(memRelation[idx].value==10) 
 	  			$('#fatherCheck').val('true');
 	  		if(memRelation[idx].value==8)
 	  			$('#stepMotherCheck').val('true');
 	 		if(memRelation[idx].value==9) 
 	  			$('#stepFatherCheck').val('true');
 	
 	 		isDepen[idx].value=1;

     }
     	$('#depQues').hide();
     	if(divValue!="")
     	$('#'+divValue).hide();
    
     	if(flag=="false"){
     	validateUserProfile()
     	}else{
     	addEditRow();
     	}
     
   }
 
function setDepFamilyPrintERSName(name)
{
	name=name.substring(0,15);
	var ersFamPrintName="";
	ersFamPrintName=document.getElementById("ersPrintName");
	ersFamPrintName.value=name;
}

function initEditProfileRequest() {
   		var accOffc=document.getElementById('payAccOffc').value;
   		var airOffc=document.getElementById('airAccOffc').value;

	 	var paoOffId=$('#paoOffId');
 		var serviceId="";
 		serviceId=$('#ServiceId').val();
 		var serviceName=document.getElementById('ServiceName').value;

  		var categoryId="";
 		categoryId=$('#CategoryId').val();	
 		var categoryName=document.getElementById('CategoryName').value;
		//alert("ServiceId="+serviceId+"||serviceName="+serviceName+"categoryId="+categoryId+"||categoryName="+categoryName); 
// 		var rankName="";
// 		rankName=document.getElementById('RankName').value;
//	  	var groupString= document.getElementById("groupString").value;
	
//		var groupArr=groupString.split(",");

	  	var check=false;
	 	var checkOff=false;
	 	var options="";
	 	var afcaoCheck=false;
	 	var rail_options;
	 	var air_options;
		//Check for Airforce	 

	if (categoryId != "") {
			$.ajax({
	      	url: $("#context_path").val() + "mb/getPAO",
		    type: "POST",
	      	data: "serviceId="+serviceId+"&categoryId="+categoryId,
			datatype: "application/json",

  		  	success: function(data){
  		  		var obj = JSON.parse(JSON.stringify(data));

				var railCount;
				var RailVisitorGroup;
				var airCount;
				var AirVisitorGroup;

  		  		$.each(obj, function(i, v) {
					if (i == 0) {
						railCount = $(v).length;
						RailVisitorGroup = $(v);
					}
					if (i == 1) {
						airCount = $(v).length;
						AirVisitorGroup = $(v);
					}
				});

  				if(railCount != 1){
					rail_options += '<option value="-1">select<\/option>';
  				}

 				$(RailVisitorGroup).each(function(index) {
					var id = RailVisitorGroup[index].acuntoficeId;
					var name = RailVisitorGroup[index].name;

//					rail_options += '<option value="' + id + '">' +name + '<\/option>';

			  			if(accOffc==id)
							rail_options += '<option value="' + id + '" selected="selected" >' +name + '<\/option>';
			  			else
							rail_options += '<option value="' + id + '">' +name + '<\/option>';
		 		});

		 		if(airCount != 1){
		 			air_options += '<option value="-1">select<\/option>';
		 		}

		 		$(AirVisitorGroup).each(function(index){
					var id = AirVisitorGroup[index].acuntoficeId;
					var name = AirVisitorGroup[index].name;

//						air_options += '<option value="' + id + '">' +name + '<\/option>';

			  			if(airOffc==id)
							air_options += '<option value="' + id + '" selected="selected" >' +name + '<\/option>';
			  			else
							air_options += '<option value="' + id + '">' +name + '<\/option>';

		 		});

		 		$("#payAccOff").html(rail_options);
		  		$("#airAccOff").html(air_options);
			}//END SUCCESS
	    });
	}		        
		      
		   
		        
		        
		//check for block no in case of pbor
		// ******************************* \\
		 $('#payAccOff').change(function()
		 {
		 	 $('#persnBlock').val('true');
			 var payAcOff=$('#payAccOff').val();
			 
			 if(serviceName == 'Army')
			 {
				if(categoryName == 'PBOR')
				{
				 var persnlNo= CryptoJS.AES.decrypt($("#userAlias").val(),"Hidden Pass").toString(CryptoJS.enc.Utf8)
					
					 var response="";
		     		$.ajax({
		      					url: $("#context_path").val() + "mb/validateBlockOfPersnlNo",
		      					type: "post",
		      					data: "paoCode="+payAcOff+"&persnlNo="+persnlNo,
		      					dataType: "text",
		      					success: function(msg){
		                			if(msg=="false"){
		                				alert("Personal No doesn't exists between specified block of Personal No. Please recheck either personal number or Pay Account Office");
		                				$("#persnBlock").val(msg);
		                			}
				          		}
				          	});
					 }
				}
		 });

		//For editing of rank on the basis of service and category   

		return true;
	
 }

function setDepRelationAccToGender(value,x) {
  var marStat=$("input[name='maritalStatus']:checked").val();
  var gender=$("input[name='gender']:checked").val();
 // var gender=document.getElementById('gender').value;
  
  var options="<select id='memRelation' class='comboauto' name='memRelation' onchange='setDepMarStatAccToRelation(this,"+x+")'>"
  options+="<option value=''>Select</option>"
      var enumRel = document.getElementById('relationFamilyEnum').value; 
	  var relCode; 
	
	  var spltVals=enumRel.split(',');
	  
	if(spltVals.length>0)
	{
	  for(var i=0;i<spltVals.length-1;i++)
  		  		{	
  		  		var relArr=spltVals[i].split("::");
  		  		
  		  		if(value==0 && marStat==0){
  		  		if((relArr[1]==1 && gender==1) ||relArr[1]==2 || relArr[1]==3 || relArr[1]==6 || relArr[1]==9 || relArr[1]==10){
  		  	     options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	}
  		  	if(value==0 && marStat==1){ // Dependent [Male] && Marital_Status : [Unmarried]{
  		  		if(relArr[1]==9 ||relArr[1]==10 || relArr[1]==6 || relArr[1] == 2 ){ //Adding Son as Dependent
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	}
  		  	     if(value==1 && marStat==0){
  		  		if((relArr[1]==1 && gender==0) || relArr[1]==4 || relArr[1]==5 || relArr[1]==7 || relArr[1]==8 || relArr[1]==11){
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	}
  		  	     if(value==1 && marStat==1){ // [ Female, Unmarried ]
  		  		if(relArr[1]==7 || relArr[1]==8 || relArr[1]==11 || relArr[1] == 4){ // Adding female son as dependent
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	    }
  	  }	  	
  	}	  	
  	  options+='</select>'   
  	var famGender=document.getElementsByName('memGender');
    var relDiv=document.getElementsByName('memRelation');

	$('#reltionDiv'+x).html(options);

}

function setPrintERSForDependents(k){
   
    
   var ersFamPrintName="";

   ersFamPrintName=document.getElementsByName("ersPrintName");

   fName=document.getElementsByName("firstmemberName");
   mName=document.getElementsByName("middlememberName");
   lName=document.getElementsByName("lastmemberName");

   var PrintName = fName;

   var idx=PrintName.length;

   for(var z=idx-1;z>=0;z--){

   var depName=PrintName[z].value;
   if(mName[z].value.trim().length > 0){
   	 depName=depName+" "+mName[z].value;
   }
   
   if(lName[z].value.trim().length > 0){
   	 depName=depName+" "+lName[z].value;
   }
   
   if(depName.length > 15){
   depName=depName.substring(0,15);
   }

   ersFamPrintName[z].value=depName;
}
}
  	
function setDepMarStatAccToRelation(obj,x) {
 var value = obj.value;

 	var selectBoxText='<select id="memMaritalStatus" class="comboauto" name="memMaritalStatus">';
    var defaultOption="<option value=''>Select</option>";
    var options="";
    var optionCount=0;
    var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	var marCode; 
	var spltedVals=enumMar.split(',');
		var node = obj.parentNode.parentNode.parentNode;
	if (value == 2 || value == 3 || value == 4 || value == 5) {
		$(node).find('input[name="childHostelNRS"]').prop('disabled', false);
		$(node).find('input[name="childHostelNAP"]').prop('disabled', false);
	} else {
		$(node).find('input[name="childHostelNRS"]').val("");
		$(node).find('input[name="childHostelNRS"]').prop('disabled', true);
		$(node).find('input[name="childHostelNAP"]').val("");
		$(node).find('input[name="childHostelNAP"]').prop('disabled', true);
	}
	if(spltedVals.length>0)
	{
	    for(var i=0;i<spltedVals.length-1;i++)
  		{
  		 	var marArr=spltedVals[i].split("::");

  		  	if(value==1 && marArr[1]==0){
  		  		options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>';
  		  		optionCount++;
  		  	}
  		  	if ((marArr[1]==0 || marArr[1]==2 || marArr[1]==3 || marArr[1]==4) && (value==8 || value==9 || value==10 || value==11 )){
  		  		options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>';
  		  		optionCount++;
  		  	}
  		  	if(value==4 || value==2 || value==3 || value==5 ||  value==6 || value==7){
  		  		options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>';
  		  		optionCount++;
  		  	}
  		}
  	}
  
  	if(optionCount==1){
		selectBoxText+=options;
	}else{
		selectBoxText+=defaultOption;
		selectBoxText+=options;
	}
    selectBoxText+='</select>'  
    $('#maritalStatDiv'+x).html(selectBoxText);  
}
 


function calculateEditRetirementDate(){
    
     var retirementDate="";
    
	if(document.getElementById("dateOfBrth").value!="" && document.getElementById("retireAge").value!="")
	{
     	    var dob=document.getElementById("dateOfBrth").value.split("/");
    		var age=document.getElementById("retireAge").value;
       		var year=dob[2];
    		var month=dob[1];
    		var retireYear=parseInt(age)+parseInt(year); 
    		var birthMonth;
    		
    		if(dob[1]=='09')
    		birthMonth=9;
    		
    		else if(dob[1]=='08')
    		birthMonth=8; 
    	
    		else if(dob[1]=='07')
    		birthMonth=7; 
    	
    		else if(dob[1]=='06')
    		birthMonth=6; 
    	
    		else if(dob[1]=='05')
    		birthMonth=5; 
    	
    		else if(dob[1]=='04')
    		birthMonth=4; 
    	
    		else if(dob[1]=='03')
    		birthMonth=3; 
    	
    		else if(dob[1]=='02')
    		birthMonth=2; 
    	
    		else if(dob[1]=='01')
    		birthMonth=1; 
    	
    		else if(dob[1]!='09' && dob[1]!='08' && dob[1]!='07' && dob[1]!='06' && dob[1]!='05' && dob[1]!='04' && dob[1]!='03' && dob[1]!='02' && dob[1]!='01')
    		birthMonth=parseInt(dob[1]);
    	
    	
   	 		var birthDate;
    	
    		if(dob[0]=='09')
    		birthDate=9;
    	
    		else if(dob[0]=='08')
    		birthDate=8; 
    	
    		else if(dob[0]=='07')
    		birthDate=7; 
    	
    		else if(dob[0]=='06')
    		birthDate=6; 
    	
    		else if(dob[0]=='05')
    		birthDate=5; 
    	
    		else if(dob[0]=='04')
    		birthDate=4; 
    	
    		else if(dob[0]=='03')
    		birthDate=3; 
    	
    		else if(dob[0]=='02')
    		birthDate=2; 
    	
    		else if(dob[0]=='01')
    		birthDate=1; 
    	
    		else if(dob[0]!='09' && dob[0]!='08' && dob[0]!='07' && dob[0]!='06' && dob[0]!='05' && dob[0]!='04' && dob[0]!='03' && dob[0]!='02' && dob[0]!='01')
    		birthDate=parseInt(dob[0]);
    	
    	
	    	if(birthMonth==1&&birthDate==1)
	    	{
		    		retireYear=retireYear-1;
	    			birthMonth=12;
	    			birthDate=31;
	    	}
    	
	    	if(birthMonth!=1&&birthDate==1)
	    	{
	 	   			if(birthMonth==5||birthMonth==7||birthMonth==10||birthMonth==12)
	    				birthDate=30;
	    	
			    	if(birthMonth==3)
			    	{
			    				if(retireYear%4==0)
			    				birthDate=29;
			    				else    
			    				birthDate=28;
			    	}
	    	
	    	
			    	if(birthMonth==2||birthMonth==4||birthMonth==8||birthMonth==6||birthMonth==9||birthMonth==11)
			    		birthDate=31;
	    	
	    		birthMonth=birthMonth-1;
	    	}
    	
    	
	    	if(birthDate!=1)
	    	{
	    			if(birthMonth==1||birthMonth==3||birthMonth==5||birthMonth==7||birthMonth==8||birthMonth==10||birthMonth==12)
	    				birthDate=31;
	   	
			   		if(birthMonth==2)
			    	{
			    				if(retireYear%4==0)
			    				birthDate=29;
			    				else
			    				birthDate=28;
			    	}
	    	
			    	if(birthMonth==4||birthMonth==6||birthMonth==9||birthMonth==11)
			    			birthDate=30;
			       		
			 }
       		retirementDate=birthDate+'/'+birthMonth+'/'+retireYear;
    	
    }
    
    return retirementDate;
}



function checkRetirementDate(){
	var dateOfRetirement=trim($("#dateOfRtirementEdit").val()); 
	var retirementDate=$("#oldRetirementDate").val();
	var isRetirementDateUpdated=$("#isRetirementDateUpdated").val();
	
  //	if(isRetirementDateUpdated && retirementDate != dateOfRetirement){
	
	if(isRetirementDateUpdated == 'true'  && (retirementDate != dateOfRetirement)){
	$("#retirementDateFlag").val('true');
		
	}
}
 



function getGradePayResponseEdit(httpRes){
	/*var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	var retirementAge=$(doc).find("RETIREMENT_AGE").text();
	var rankId=$(doc).find("RANK_ID").text();
	var rankName=$(doc).find("RANK_NAME").text();
	$("#rankNameInner").html(rankName);
	 */

	var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	var retirementAge=$(doc).find("RETIREMENT_AGE").text();
	var rankId=$(doc).find("RANK_ID").text();
	var rankName=$(doc).find("RANK_NAME").text();
	$("#rankNameInner").html(rankName);
	$("#rankId").val(rankId);
	if(parseInt(retirementAge) > 0){
	$("#retireAge").val(retirementAge);
	}	
}

    
   
function getCategoryResponseEdit(httpRes)
{
	var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	var categoryDoc =doc.documentElement;	
	if(typeOfUser=='traveller'){
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');}
	else{
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');
	}
	if(typeOfUser=='allTypeUser'){ 
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');}
	else{
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');
	}
	
	var catNameDoc=categoryDoc.getElementsByTagName('categoryName');
	
		
	var categoryStr="";
	categoryStr+='<select name=\"category\" class=\"combo\" id=\"categoryId\" onchange=\"getLevelDetailsEdit();\">';
	categoryStr+='<option value=\"-1\">Select</option>';
	for(var i=0;i < catNameDoc.length ; i++){
		if(catNameDoc[i].firstChild!=null && catIdDoc[i].firstChild!=null)
		categoryStr+='<option value=\"'+catIdDoc[i].firstChild.nodeValue+'\">'+catNameDoc[i].firstChild.nodeValue+'</option>';
	}
	categoryStr+='</select>';	
	categoryIdTd.innerHTML=categoryStr;
	if(typeOfUser=='traveller'){
	setDoIINo();
	}
	
	document.getElementById("categoryId").value =document.getElementById("CategoryId").value 
	
}



  

function confirmOffline(id, status,remarkId,seq) {
	if (status == 'offline' && document.getElementById(id).checked == true) {
      $("#"+remarkId).prop('readonly', true);
      document.getElementById(remarkId).value="";
     } 
     if (status == '1') {
         if (document.getElementById(id).checked == false) {
             var r = confirm("Do you want to make selected dependent as offline? ");
             if (r == true) {
				$("#mstatus"+seq).val("0");
                 hideDepDetails();
             } else {
                 document.getElementById(id).checked = false;
             }
         } else {
			$("#mstatus"+seq).val("1");
             hideDepDetails();
         }
     }

     if (status == '0') {
         if (document.getElementById(id).checked == true) {

             var r = confirm("Do you want to make  selected dependent online?");
             if (r == true) {
				$("#mstatus"+seq).val("1");

                 $("#"+remarkId).prop('readonly', false);
                 hideDepDetails();
             } else {
                 document.getElementById(id).checked = true;
             }
         } else {
			$("#mstatus"+seq).val("0");
             hideDepDetails();
         }
     }

//     if(document.getElementById(id).checked == true){
// $("#"+toolTip).html("Off-line");
// }else{
// $("#"+toolTip).html("On-line");
// }

}

function getGender() {
	var enumGend = document.getElementById('genderFamilyEnum').value;
	var gendCode;
	var spltedVals = enumGend.split(',');
	var memGender = document.getElementsByName("memGender");
	for (var x = 0; x < memGender.length; x++) {
		var id = memGender[x].getAttribute('id');
		var val = document.getElementById(id).value;
		setDepRelation(val, x);
		$("#" + id + " option:selected").remove();
		if (spltedVals.length > 0) {
			for (var i = 0; i < spltedVals.length - 1; i++) {
				var gendArr = spltedVals[i].split("::");
				var select = document.getElementById(id);
				select.options[select.options.length] = new Option(gendArr[0], gendArr[1], false, false);
			}
		}
		if ($("#" + id + " option[value=" + val + "]").length > 0)
			$('#' + id).val(val);
		else
			$('#' + id).val('-1');
	}
}

function setDepRelation(value, x) {
	var marStat = $("input[name='maritalStatus']:checked").val();
	var gender = $("input[name='gender']:checked").val();

	var enumRel = document.getElementById('relationFamilyEnum').value;
	var relCode;
	var spltVals = enumRel.split(',');
	var memRelation = document.getElementsByName("memRelation");
	var id = memRelation[x].getAttribute('id');
	var val = document.getElementById(id).value;
	var select = document.getElementById(id);
	if (spltVals.length > 0) {
		for (var i = 0; i < spltVals.length - 1; i++) {
			var relArr = spltVals[i].split("::");

			if (value == 0 && marStat == 0) {
				if ((relArr[1] == 1 && gender == 1) || relArr[1] == 2 || relArr[1] == 3 || relArr[1] == 6 || relArr[1] == 9 || relArr[1] == 10) {
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
			if (value == 0 && marStat == 1) {
				if (relArr[1] == 9 || relArr[1] == 10 || relArr[1] == 6 || relArr[1] == 2) {  // Adding son By Default on Edit
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
			if (value == 1 && marStat == 0) {
				if ((relArr[1] == 1 && gender == 0) || relArr[1] == 4 || relArr[1] == 5 || relArr[1] == 7 || relArr[1] == 8 || relArr[1] == 11) {
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
			if (value == 1 && marStat == 1) {
				if (relArr[1] == 7 || relArr[1] == 8 || relArr[1] == 11 || relArr[1] == 4) {  // Adding Daughter By Default on Edit
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
		}
		$("#" + id + " option:selected").remove();
		if ($("#" + id + " option[value=" + val + "]").length > 0)
			$('#' + id).val(val);
		else
			$('#' + id).val('-1');
	}
}

function getMaritalStatusVal() {
	var enumMar = document.getElementById('marStatusFamilyEnum').value;
	var gendCode;
	var spltedVals = enumMar.split(',');
	var memMaritalStatus = document.getElementsByName("memMaritalStatus");
	for (var x = 0; x < memMaritalStatus.length; x++) {
		var id = memMaritalStatus[x].getAttribute('id');

		var val = document.getElementById(id).value;

		$("#" + id + " option:selected").remove();
		if (spltedVals.length > 0) {
			for (var i = 0; i < spltedVals.length - 1; i++) {
				var maritalSt = spltedVals[i].split("::");
				var select = document.getElementById(id);
				select.options[select.options.length] = new Option(maritalSt[0], maritalSt[1], false, false);
			}
		}
		if ($("#" + id + " option[value=" + val + "]").length > 0)
			$('#' + id).val(val);
		else
			$('#' + id).val('-1');
	}

}

function setDepRel(value, id) {
	$('#' + id).empty()
	var marStat = $("input[name='maritalStatus']:checked").val();
	var gender = $("input[name='gender']:checked").val();

	var enumRel = document.getElementById('relationFamilyEnum').value;
	var relCode;
	var spltVals = enumRel.split(',');
	var memRelation = document.getElementsByName("memRelation");

	var select = document.getElementById(id);
	select.options[select.options.length] = new Option('Select', '', false, false);
	if (spltVals.length > 0) {
		for (var i = 0; i < spltVals.length - 1; i++) {
			var relArr = spltVals[i].split("::");

			if (value == 0 && marStat == 0) {
				if ((relArr[1] == 1 && gender == 1) || relArr[1] == 2 || relArr[1] == 3 || relArr[1] == 6 || relArr[1] == 9 || relArr[1] == 10) {
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
			if (value == 0 && marStat == 1) {
				if (relArr[1] == 9 || relArr[1] == 10 || relArr[1] == 6 || relArr[1] == 2 ) {  // adding son as dependent
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
			if (value == 1 && marStat == 0) {
				if ((relArr[1] == 1 && gender == 0) || relArr[1] == 4 || relArr[1] == 5 || relArr[1] == 7 || relArr[1] == 8 || relArr[1] == 11) {
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
			if (value == 1 && marStat == 1) {
				if (relArr[1] == 7 || relArr[1] == 8 || relArr[1] == 11 ||  relArr[1] == 4) {  //adding daughter dependent
					select.options[select.options.length] = new Option(relArr[0], relArr[1], false, false);
				}
			}
		}

	}

}


function setDepMaritalStatus(value, id) {
	$('#' + id).empty()
	var enumMar = document.getElementById('marStatusFamilyEnum').value;
	var marCode;
	var select = document.getElementById(id);
	select.options[select.options.length] = new Option('Select', '', false, false);;
	var spltedVals = enumMar.split(',');
	if (spltedVals.length > 0) {
		for (var i = 0; i < spltedVals.length - 1; i++) {
			var marArr = spltedVals[i].split("::");

			if (value == 1 && marArr[1] == 0) {
				select.options[select.options.length] = new Option(marArr[0], marArr[1], false, false);;
			}
			if ((marArr[1] == 0 || marArr[1] == 2 || marArr[1] == 3 || marArr[1] == 4) && (value == 8 || value == 9 || value == 10 || value == 11)) {
				select.options[select.options.length] = new Option(marArr[0], marArr[1], false, false);;
			}
			if (value == 4 || value == 2 || value == 3 || value == 5 || value == 6 || value == 7) {
				select.options[select.options.length] = new Option(marArr[0], marArr[1], false, false);;
			}
		}
	}
}

function checkNumeric(textValue){
	var numberRegEx = /^[0-9]*$/;
	if(numberRegEx.test(textValue)){
		return true;
	}else{
		return false;
	} 
}

function checkAlphaNumeric(textValue){
	var alphaNumRegEx = /^[a-zA-Z0-9]*$/;
	if(alphaNumRegEx.test(textValue)){
		return true;
	}else{
		return false;
	}  
}

function isAlphaNumericAllow(e) {
      var specialKeys = new Array();
      specialKeys.push(8); //Backspace
      specialKeys.push(46); //Delete
      specialKeys.push(36); //Home
      specialKeys.push(35); //End
      specialKeys.push(37); //Left
      specialKeys.push(39); //Right
      
      var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
      var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
      if(!ret){
      	e.preventDefault();
      	
      }
}


function isNumericAllow(e) {
	  var specialKeys = new Array();
	  specialKeys.push(8); //Backspace
      specialKeys.push(46); //Delete
      specialKeys.push(36); //Home
      specialKeys.push(35); //End
      specialKeys.push(37); //Left
      specialKeys.push(39); //Right
      
      var keyCode = e.which ? e.which : e.keyCode
      var ret = ((keyCode >= 48 && keyCode <= 57) || (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
      if(!ret){
      	e.preventDefault();
      }
      
}

function validateDoIIPartNo(doIIPartNo){
	
	var filter="!#$%^*";
	if (doIIPartNo != "") {
		for (var i = 0; i < doIIPartNo.length; i++) {
			if (filter.indexOf(doIIPartNo.charAt(i)) > -1) {
				return false;
			}
		}
	}
	return true;
}


$(document).ready(function() {

 $(".bindCPC").bind('copy paste cut',function(e) { 
 e.preventDefault(); //disable cut,copy,paste
 });

});


function getLevelDetailsEdit(){
	$("#changeAuthority").val("");
	
	var categoryName=document.getElementById("categoryId");
	var alternateServ=document.getElementById("ServiceId");
	var categoryId=categoryName.options[categoryName.options.selectedIndex].value;
	
	$("#CategoryId").val(categoryId);
	
	$.ajax({
		type: "post",
		url: $("#context_path").val() + "mb/getLevelOnCategory",
		data: "serviceId=" + alternateServ.value+"&categoryId="+categoryId,
		datatype: "application/json",

		success: function(data) {

			$("#rankNameInner").html("");
			var levels = $("#levelId");
			levels.children('option:not(:first)').remove();
		   $.each(data, function(i) {
		 	   var levelOption = document.createElement("option");
		 		levelOption.value = JSON.stringify(data[i]);
		 		levelOption.text = data[i].levelName;
		 	    levels.append(levelOption);
		    });
	
			
			

		}
	});
	
	return true;
}
