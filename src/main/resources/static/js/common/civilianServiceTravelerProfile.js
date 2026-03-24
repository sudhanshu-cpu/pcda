/*----------  On Selection Category And Rank Used Function Start-----------*/

var http;
var respText;

function getLevelResponse(httpRes){
	
	$("#rankNameInner").html("");
	$("#rankId").val("");
	$("#retireAge").val("");
	
	var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	
	var levels = $("#levelId");
	levels.children('option:not(:first)').remove();
    $(doc).find("LEVELS").each(function(){
 	   var levelOption = document.createElement("option");
 		levelOption.value = $(this).find("LEVEL_ID").text();
 		levelOption.text = $(this).find("LEVEL_NAME").text();
 	    levels.append(levelOption);
    });
	
	
	/* Code Block To Set Personal Number Prefix Start */
	
	var alphaNoId=$("#alphaNoId");
	var categoryNameVal = categoryId.options[categoryId.selectedIndex].text;
	if(alphaNoId != null){
		
		if(categoryNameVal=='Coast Guard Officers'||categoryNameVal=='Civilian NonGazetted Personnel'||categoryNameVal=='Enrolled Personnel'){
			alphaNoId.children('option').remove();
		}
		else{
			alphaNoId.children('option').remove();
			var prefixOption = document.createElement("option");
	 		prefixOption.value ="";
	 		prefixOption.text ="NA";
	 	    alphaNoId.append(prefixOption);
		}
		
		$(doc).find("PREFIX_DTLS").each(function(){
	 	   var prefixOption = document.createElement("option");
	 		prefixOption.value = $(this).find("PREFIX").text();
	 		prefixOption.text = $(this).find("PREFIX").text();
	 	    alphaNoId.append(prefixOption);
	    });
	}
    /* Code Block To Set Personal Number Prefix End */
   
}



function getGradePayResponse(httpRes){
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


function getRankDtl(http)
{
	var respText=http.responseText;
	respText = respText.replace(/&/g,"and");
	
	var doc=getXML(respText);
	var rankDoc =doc.documentElement;	
	/*
	if(document.getElementById('RankName')!=null){
		var RankName=document.getElementById('RankName').value;
	}
*/
	var rankName=rankDoc.getElementsByTagName("rankName");
	var rankId=rankDoc.getElementsByTagName("rankId");
	var retireAge=rankDoc.getElementsByTagName("retireAge");
	
	var rankStr="";
	rankStr+='<select name=\"rankId\" class=\"combo\" id=\"rankId\"  onchange=\"getRetirementAge();\">';
	rankStr+='<option value=\"-1\">Select</option>';
	for(var i=0;i < rankName.length ; i++)
	{
		if(rankName[i].firstChild!=null && rankId[i].firstChild!=null && retireAge[i].firstChild!=null)
		{
			rankStr+='<option value=\"'+rankId[i].firstChild.nodeValue+','+retireAge[i].firstChild.nodeValue+'\">'+rankName[i].firstChild.nodeValue+'</option>';
		}
	}
	
	rankStr+='</select>';
	
	var rankIdStr=document.getElementById("rankIdTd");
    rankIdStr.innerHTML=rankStr;
	
	/* Code Block To Set Personal Number Prefix Start */
    var prefixNameArr=rankDoc.getElementsByTagName("prefix");
    var prefixSelectEleStr="";
	if(prefixNameArr.length>0){
		for(var i=0;i < prefixNameArr.length ; i++)
		{
			prefixSelectEleStr+='<option value=\"'+prefixNameArr[i].firstChild.nodeValue+'\">'+prefixNameArr[i].firstChild.nodeValue+'</option>';
		}	
	}else{
		prefixSelectEleStr+='<option value=\"\">NA</option>';
	}
    //alert("prefixSelectEleStr-"+prefixSelectEleStr);
    if(document.getElementById('alphaNoId')!=null){
		document.getElementById('alphaNoId').innerHTML=prefixSelectEleStr;
		prefixSelectEleStr="";
	}
	/* Code Block To Set Personal Number Prefix End */
	
}

function getRetirementAge()
{
	var rankIdValue=$('#rankId :selected').val();
	var rankIdArr=rankIdValue.split(",");
	if(rankIdArr[0]==-1)
	{
		document.getElementById("retireAge").value="";
	}else
	{
		document.getElementById("retireAge").value=rankIdArr[1];
	}
	
}

/*----------  On Selection Category And Rank Used Function End -----------*/
var isChkforduplicateTraveller=false;
var isChkforRankId=false;
var isCallForLevelId=false;
var isCallForGradePay=false;

/*----------  Validate Coast Guard Personal Number Function START -----------*/
function chkForDuplicateTraveller(personalNo) {
	isChkforduplicateTraveller=true;
	isChkforRankId=false;
	isCallForLevelId=false;
	isCallForGradePay=false;
	var url = $("#context_path").val() + "mb/checkPersonalNo?personalNo=" + personalNo;
	getHttpResponse(url);
	return true;
}
/*----------  Validate Coast Guard Personal Number Function END -----------*/

/*----------  Validate PAN Number Function START -----------*/

function validatePanNumber()
{
		
	var categoryName=document.getElementById("categoryId");
	var categoryNameVal=categoryName.options[categoryName.options.selectedIndex].text;
	
	if(categoryNameVal=='select' || categoryNameVal=='Select'){
		alert("Please select the category");
		categoryName.focus();
		return false;
	}
	
	var level=$("#levelId").val();
	
	if(level=='-1' || level==''){
		alert("Please select the Level");
		$("#levelId").focus();
		return false;
	}
	
	
	var rankVal=document.getElementById("rankId").value; 
	
	if(rankVal=='' || rankVal=='-1' || rankVal=='Select'){
		alert("Grade Pay is not mapped with selected level.");
		return false;
	}
	
	var personalNo=document.getElementById("personalNo").value;
	if(personalNo==''){
		alert("Please Fill The PAN Number Field");
		document.getElementById("personalNo").focus();
		return false;
	}else if(personalNo.length != 10){
		alert("Please Enter Valid PAN Number Of Length 10");
		document.getElementById("personalNo").focus();
		return false;
	}else if(personalNo.trim().length==10){ // PAN Validation
		var firstPart=personalNo.substring(0,5);
		var secondPart=personalNo.substring(5,9);
		var thirdPart=personalNo.substring(9,10);
		if(!/^[a-zA-Z]*$/g.test(firstPart.trim()) || !/^[a-zA-Z]*$/g.test(thirdPart.trim()) ){
		  alert("Please Enter Valid PAN Number.");
		  document.getElementById("personalNo").focus();
		  return false;	
		}else if(!isNumberic(secondPart)){
		  alert("Please Enter Valid PAN Number.");
		  document.getElementById("personalNo").focus();
		  return false;	
		}
	}
	
	personalNo=personalNo.toUpperCase();
	
	isChkforduplicateTraveller=true;
	isChkforRankId=false;
	isCallForLevelId=false;
	isCallForGradePay=false;
	var url = $("#context_path").val() + "mb/checkPersonalNo?personalNo=" + personalNo;
	getHttpResponse(url);
	return true;
	
}

function isNumberic(name){

	var iChars = "0123456789";
		
	if(name!="")
	{
      
	for (var i = 0; i < name.length; i++) {
		if (iChars.indexOf(name.charAt(i)) == -1){
		return false;
     	}
     
   }
	}
		return true;
 
 }

function chkForTravellerProfile(http){
	var respText=http.responseText;	
	respText = respText.replace(/&/g,"and");


	if(respText.indexOf('Special')>-1)
	{
		alert(respText);
		document.getElementById("personalNo").value="";
		document.getElementById("personalNo").focus();
	}
	else if(respText.indexOf('Reserve Words')>-1)
	{
		alert(respText);
		document.getElementById("personalNo").value="";
		document.getElementById("personalNo").focus();
	}
	else if(respText.indexOf('Reserve Words')>-1)
	{
		alert(respText);
		document.getElementById("personalNo").value="";
		document.getElementById("personalNo").focus();
	}
	else if(respText=='Not Valid')
	{
		alert("Please Enter Valid Personal Number");
		document.getElementById("personalNo").value="";
		document.getElementById("personalNo").focus();
	}
	else if(respText=='false')
	{
		alert("Traveler already Created");
		$("#personalNo").val("");
		$("#personalNo").focus();
		if ($("#validatePersonalNo") != null)
			$("#validatePersonalNo").val("false");
	}
	else if(respText=='true')
	{
		if ($("#validatePersonalNo") != null)
			$("#validatePersonalNo").val("true");
		 alert("Traveler Number Is Valid.");	
	}
}

/*----------  Validate PAN Number Function END  -----------*/

function testPersonalValidationCheck() {
	if(document.getElementById("validatePersonalNo") != null)
	{
		if(document.getElementById("validatePersonalNo").value=='')
		{
			alert("Please Click On Validate Button");
		$("#validatebutton").focus();
			return false;
		}
	}

	return true;
}

function setPrintERSName() {

	var fName=$("#fName").val();
	var mName=$("#mName").val();
	var lname=$("#lName").val();

	var name="";

	var flag=false;
	if(fName.trim().length > 0){
	 name=name+fName;
	 flag=true;
	}

	if(mName.trim().length > 0){
		if(flag){
			name=name+" "+mName;
	 	}else{
			 name=name+mName;
			flag=true;
	 	}
	}

	if(lname.trim().length > 0){
	 if(flag){
	    name=name+" "+lname;
	    }else{
	    name=name+lname;
	    flag=true;
	    }
	}

	if(name.length > 15){
		name=name.substring(0,15);
	}
	$("#ersPrntName").val(name);
	if (lname.trim().length == 0 && mName.trim().length>0){
	$("#nameOnBoardingPass").val(fName+" "+mName+" "+fName);
	}else if(lname.trim().length == 0 && mName.trim().length == 0){
	$("#nameOnBoardingPass").val(fName+" "+fName);	
	}else if(lname.trim().length>0 && mName.trim().length == 0 && fName.trim().length > 0){
	$("#nameOnBoardingPass").val(lname+" "+fName);	
	}
	else if(lname.trim().length == 1 && mName.trim().length == 0){
		$("#nameOnBoardingPass").val(fName+" "+lname+" "+fName);
	}else if(lname.trim().length == 1 && mName.trim().length>0){
		$("#nameOnBoardingPass").val(fName+" "+lname+" "+mName+" "+fName);
	}
	else{
	$("#nameOnBoardingPass").val(lname+" "+mName+" "+fName);
	}
}

/*----------  Depandent Related Function START  -----------*/

function setRelationFields(value)
{   	
	   if(value==1){
	   	 $("#spouseServiceRow").hide();
	   }
	   if(value==0){
	   	 $("#spouseServiceRow").show();
	   }

       var k1=memIndex-1
   	   var options="<select id='memRelation"+k1+"' class='comboauto' name='memRelation' onchange='setMarStatAccToRelation(this.value,"+k1+")'>"
       options+="<option value=''>Select</option>"
       var enumRel = document.getElementById('relationFamilyEnum').value; 
	   var relCode; 
	 
	  var spltVals=enumRel.split(',');
	  if(spltVals.length>0)
	  {
	  	for(var i=0;i<spltVals.length-1;i++)
  		{
  		  		var relArr=spltVals[i].split("::");
  		  		if(value==1)
  		  		{
  		  			if(relArr[1]==6 ||relArr[1]==7 || relArr[1]==8 || relArr[1]==9 || relArr[1]==10 || relArr[1]==11 ||  relArr[1]==13 ||  relArr[1]==14 )
  		  	     	options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	    }else
  		  	       options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		}
  	  }	  	
  	  options+='</select>'    
  	  $('#relDiv'+k1).html(options);
}


function setRelationAccToGender(value,k) {
  var marStat=$("input[name='maritalStatus']:checked").val();
  var gender=$("input[name='gender']:checked").val();
  var options="<select id='memRelation"+k+"' class='comboauto' name='memRelation' onchange='setMarStatAccToRelation(this.value,"+k+")'>"
  options+="<option value=''>Select</option>"

      var enumRel = document.getElementById('relationFamilyEnum').value; 
	  var relCode; 
	  var spltVals=enumRel.split(',');
	  if(spltVals.length>0)
	  {
	  	for(var i=0;i<spltVals.length-1;i++)
  		{
  			var relArr=spltVals[i].split("::");
  			if(value==0 && marStat==0)
  			{
  		  		if((relArr[1]==1 && gender==1) ||relArr[1]==2 || relArr[1]==3 || relArr[1]==6 || relArr[1]==9 || relArr[1]==10 || relArr[1]==13)
  		  	     options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	}
  		  	if(value==0 && marStat==1) // male - unmarried
  		  	{
  		  		  if(relArr[1]==9 ||relArr[1]==10 || relArr[1]==6 || relArr[1]==13 || relArr[1] == 2) // Adding son dependent
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	}
  		  	if(value==1 && marStat==0)
  		  	{
  		  		  if((relArr[1]==1 && gender==0) || relArr[1]==4 || relArr[1]==5 || relArr[1]==7 || relArr[1]==8 || relArr[1]==11 || relArr[1]==14)
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	}
  		  	if(value==1 && marStat==1) // female - unmarried
  		  	{
  		  		  if(relArr[1]==7 || relArr[1]==8 || relArr[1]==11 || relArr[1]==14 || relArr[1] == 4) //adding Daughter dependent
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	}
  		 }
  	   }	  	
  	  options+='</select>'    
  	  $('#relDiv'+k).html(options);
}


function setMarStatAccToRelation(value,id)
{
	if(value==2 || value==3 || value==4 || value==5){
		fillChildHostelNAP(id,"");
	    fillChildHostelNRS(id,"");
		$("#childHostelNRS"+id).removeAttr('disabled');
	    $("#childHostelNAP"+id).removeAttr('disabled');
	}else{
		fillChildHostelNAP(id,"");
	    fillChildHostelNRS(id,"");
		$("#childHostelNRS"+id).val('');
		$("#childHostelNRS"+id).attr('disabled','disabled');
	    $("#childHostelNAP"+id).val('');
	    $("#childHostelNAP"+id).attr('disabled','disabled');

	}
	
       var k1=memIndex-1;
    var selectBoxText="<select id='memMaritalStatus"+k1+"' class='comboauto' name='memMaritalStatus'>"
    var defaultOption="<option value=''>Select</option>"
    var options="";
    var optionCount=0;
	var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	var marCode; 
	var spltedVals=enumMar.split(',');
	
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
  		  	if(value==4 || value==2 || value==3 || value==5 ||  value==6 || value==7 || value==13 || value==14){
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
       
    $('#marStatDiv'+k1).html(selectBoxText);  
       
}



function setMarStatAccToRelation_OLDTILL26DEC2016(value)
{
       var k1=memIndex-1;
       var options="<select id='memMaritalStatus"+k1+"' class='comboauto' name='memMaritalStatus'>"
       options+="<option value=''>Select</option>"
       var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	   var marCode; 
	   var spltedVals=enumMar.split(',');
	   if(spltedVals.length>0)
	   {
	     for(var i=0;i<spltedVals.length-1;i++)
  		 {
  		  	var marArr=spltedVals[i].split("::");
  		  	
  		  	if(value==1 && marArr[1]==0)
  		  	      options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  	if ((marArr[1]==0 || marArr[1]==2 || marArr[1]==3 || marArr[1]==4) && (value==8 || value==9 || value==10 || value==11 ))
  		  	      options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  	if(value==4 || value==2 || value==3 || value==5 ||  value==6 || value==7 || value==13 || value==14)
  		  	       options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  }
  	    }	  	    
       
       options+='</select>'  
       
       $('#marStatDiv'+k1).html(options);  
}

/*function setDepPrintERSName(name,k)
{
	name=name.substring(0,15);
	document.getElementById("ersPrintName"+k).value=name;
} */
function setDepPrintERSName(k){
var fName=document.getElementById("firstmemberName"+k).value;
var mName=document.getElementById("middlememberName"+k).value;
var lname=document.getElementById("lastmemberName"+k).value;

var name="";

var flag=false;
if(fName.trim().length > 0){
 name=name+fName;
 flag=true;
}

if(mName.trim().length > 0){
 if(flag){
   name=name+" "+mName;
 }else{
   name=name+mName;
   flag=true;
 }
}

if(lname.trim().length > 0){
 if(flag){
    name=name+" "+lname;
    }else{
    name=name+lname;
    flag=true;
    }
}

if(name.length > 15){
name=name.substring(0,15);
}

document.getElementById("ersPrintName"+k).value=name;
} 



/*----------  Depandent Related Function END  -----------*/


/*-----------------------Init Request Creation Method Strat-----------------*/

/*------------------------------------------------End-----------------------*/



/*------------------------Adding And Removing Family Dependent Mumber--------------------*/

var rowIndex = 1;
var memIndex = 1;

function addRow() {

 	var spouseCount=0;

  	var tbl = document.getElementById('tblGrid');

  	var lastRow = tbl.rows.length;
  	$("#flag").val("true");

	for(var c=0;c<lastRow;c++) {

  		if($("#memRelation"+c)!=null) {
			  if($("#memRelation"+c).val() == 1)
				spouseCount++;
  		}
  	}

	if(memIndex>1) {

		var k1=memIndex-1
    	var memberName;	var memGender; var memdob;	var memRelation;	var memMaritalStatus;
  		var doIIPartNo;	var doIIDate;	var memReson; var isDepen; var countChildDep=0;

	   		memberName = $("#firstmemberName"+k1).val();
	  	   	isDepen = $("#isDepen"+k1).val();
	  	   
	  	    if(memberName==''){
	  			alert("Please fill the Family member First name");
	  			$("#firstmemberName"+k1).focus();
	  			return false;
	  		}
	  		if(!checkSplChar($("#firstmemberName"+k1)))
		     return false; 

	  	    memGender = $("#memGender"+k1).val();
	  		if(memGender=='Select'){
	  			alert("Please select the Gender");
	  			$("#memGender"+k1).focus();
	  			return false;
	  		}

	  		memdob = $("#dob"+k1).val();
	  		if(memdob==''){
	  			alert("Please select the member Date of Birth");
	  			$("#dob"+k1).focus();
	  			return false;
	  		}
	  		if(!isGreaterThanTodayDate($("#dob"+k1)))
	        return false;

	  		memRelation = $("#memRelation"+k1).val();
	  		if(memRelation=='Select' || memRelation==""){
	  			alert("Please select the Relation");
	  			$("#memRelation"+k1).focus();
	  			return false;
	  		}

	  		if(memRelation==1) {
  				if(!validateSpouseDob($("#dob"+k1),memGender))
  				return false;
	  		}

	  		memMaritalStatus=$("#memMaritalStatus"+k1).val();

	  		if(memMaritalStatus=='Select'||memMaritalStatus==''){
	  			alert("Please select the Marital Status");
	  			$("#memMaritalStatus"+k1).focus();
	  			return false;
	  		}

	  		if(memRelation==1 && $("#spouseCheck").val() == 'true' && spouseCount>1 && isDepen==''){
	  			alert("You have already entered Spouse Information");
	  			removeRow();
				return false;
	  		}

	  		if(memRelation==10 && $("#fatherCheck").val()=='true' && isDepen==''){
	  			alert("You Have Already Entered Father Information");
	  			removeRow();
	  			return false;
	  		}
	  		
	  		if(memRelation==11 && $("#motherCheck").val()=='true' && isDepen==''){
	  			alert("You Have Already Entered Mother Information");
	  		 	removeRow();
	  			return false;
	  		}
	  		
	  		if(memRelation==9 && $("#stepFatherCheck").val()=='true' && isDepen==''){
	  			alert("You have already entered Step Father Information");
	  		 	removeRow();
	  			return false;
	  		}

	  		if(memRelation==8 && $("#stepMotherCheck").val()=='true' && isDepen==''){
	  			alert("You have already entered Step Mother Information");
	  			removeRow();
	  			return false;
	  		}
	  		
  			doIIPartNo = $("#doIIPartNo"+k1).val();
	  		doIIDate = $("#doIIDate"+k1).val();
	  		memReson = $("#memReson"+k1).val();

	  		if((doIIPartNo=="" || doIIDate=="") &&  memReson==""){
		  		alert("Either Enter ( DOII Part No And DOII Date) OR Reason . Do not fill all three fields.");
		  		$("#memReson"+k1).removeAttr('disabled');
		  		return false;
	  		}

	  		if(doIIDate!=null && doIIDate.length>0) {
	  			var dateJoin=$("#dateOfCom_join").val();
	  			if(new Date(doIIDate).getTime()<=new Date(dateJoin).getTime()){
			  		 alert("DOII Date Should Be Greater Than Traveler Date Of Joining");
			  		 return false;
		  		}
		  		
		  		var dateOfRetirement = $("#dateOfRetirement").val();
		  		if(new Date(doIIDate).getTime()>new Date(dateOfRetirement).getTime()){
			  		 alert("DOII Date Should Be Less Than Traveler Date Of Retirement");
			  		 return false;
		  		}
		  			var dateOfBirth =trim($("#dob").val());
	  		if(new Date(doIIDate).getTime()<=new Date(dateOfBirth).getTime()){
		  		 alert("DOII Date Should Be Greater Than Traveler Date Of Birth");
		  		 return false;
	  		}
	  		}

	 		if(memRelation==4 || memRelation==5 || memRelation==3 || memRelation==2){
		  		 var dob = $("#dob").val();
		  		 if(new Date(memdob).getTime()<=new Date(dob).getTime()){
			  		 alert("Child Date Of Birth Should Be Less Than Traveler Date Of Birth");
			  		 return false;
		  		 }
		  	}
	  		if(memRelation==8 || memRelation==9 || memRelation==10 || memRelation==11){
	  			var dob = $("#dob").val();
	  		 	if(new Date(memdob).getTime()>new Date(dob).getTime()){
	  		 		alert("Father/Mother Date Of Birth Should Be Greater Than Traveler Date Of Birth");
	  		 		return false;
	  		 	}
	  		}
	  		
	  		if(memRelation==1) 
	 	  			$('#spouseCheck').val('true');
	 	  			
	  		var question="";
	  		
		  	if(isDepen=='')
		  	{
		  		
		  		if((memRelation==4 || memRelation==5)  && memMaritalStatus==0 )
		  		{
		  			 $("input[name^=incomeRange1]").prop('checked',false);
			  		 $("input[name^=isSept1]").prop('checked',false);
			  		 $("input[name^=isDep1]").prop('checked',false);
			  		 $("input[name^=isDepAsTR1]").prop('checked',false);
			  		 $('#isSeprt').hide();
			         $('#whlyDep1').hide();
			         $('#DepPerTR1').hide();
			  		 $('#depQues').show();
			  		 $('#incmRang1').show();
			  		 $('#daughMarried').show();
			  		 $('#famIndex').val(k1);
			  		 return false;
		  		}
		  		else if(memRelation==7 && memMaritalStatus==1)
		  		{
		  			$("input[name^=isParAliv3]").prop('checked',false);
		  		 	$("input[name^=isUnm3]").prop('checked',false);
		  		 	$("input[name^=isPartDep3]").prop('checked',false);
		  		 	$("input[name^=isResPer3]").prop('checked',false);
		  		  	$("input[name^=incomeRange3]").prop('checked',false);
		  		  	$('#isUnmarr3').hide();
		          	$('#isParAlv3').hide();
		  		  	$('#isParDep3').hide();
		          	$('#isResPers3').hide();
		  		 	$('#incmRange3').show();
		  		 	$('#sister').show();
		  		 	$('#depQues').show();
		  		 	$('#famIndex').val(k1);
		  		 	return false;
		  		}
		  		else if(memRelation==7 && memMaritalStatus==0)
		  		{
		  			$("input[name^=incomeRange5]").prop('checked',false);
		  		 	$("input[name^=isSep5]").prop('checked',false);
		  		 	$("input[name^=isParAliv5]").prop('checked',false);
		  		 	$("input[name^=isPartDep5]").prop('checked',false);
		  		  	$("input[name^=isResPer5]").prop('checked',false);
		  		  	$("input[name^=depAsPerTR5]").prop('checked',false);
		 		  	$('#depPerTR5').hide();
		          	$('#sept5').hide();
		  		  	$('#isParAlv5').hide();
		          	$('#isParDep5').hide();
		  		 	$('#isResPers5').hide();
		  		 	$('#incmRange5').show();
		  		 	$('#sister').show();
		  		 	$('#depQues').show();
		  		 	$('#famIndex').val(k1);
		  		 	return false;
		  		}


		  		else if(memRelation==6 && memMaritalStatus==1)
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
		  		 	$('#famIndex').val(k1);
		  		 	return false;
		  		}
		  		
		  		else if(memRelation==6 && memMaritalStatus==0)
		  		{
		  		 	closeWindow("","no")
		  		 	return false;
		  		}
		  		else if(memRelation==8 || memRelation==9 || memRelation==10 || memRelation==11)
		  		{
		  		 	$("input[name^=incomeRange4]").prop('checked',false);
		  		 	$("input[name^=isResPer4]").prop('checked',false);
		         	$("input[name^=depAsPerTR4]").prop('checked',false);
		  		    $('#isResPers4').hide();
		          	$('#depPerTR4').hide();
		  		    $('#incmRange4').show();
		  		    $('#parStepPar').show();
		  		    $('#depQues').show();
		  		    $('#famIndex').val(k1);
		  		 	return false;
		  		}
		  		else if((memRelation==2 || memRelation==3) && memMaritalStatus==0){
		  		 	closeWindow("","no")
		  		 	return false;
		  		}
		  		else if(memRelation!=='1'){
		  		 	$("input[name^=incomeRange6]").prop('checked',false);
		         	$("input[name^=depAsPerTR6]").prop('checked',false);
		          	$('#depPerTR6').hide();
		          	$('#incmRange6').show();
		  		   	$('#depQues').show();
		  		   	$('#famIndex').val(k1);
		  		 	return false;
		  		}
		  	}
		}
	
     var row=tbl.insertRow(lastRow);	
	 var cellRight = row.insertCell(0);
	 cellRight.className = 'testClass';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'firstmemberName';
	 el.id = 'firstmemberName'+memIndex;
     el.setAttribute("onkeydown","checkSplChar1(event)")  //Adding event for special char check
      el.setAttribute("onblur","setDepPrintERSName("+memIndex+")")
	 el.setAttribute("placeholder","First")
	 el.setAttribute("autocomplete","off")
	 
	 el.size = 10;
 	 cellRight.appendChild(el);
	 
 	 
 	  cellRight.appendChild(el);
	 
	  var cellRight = row.insertCell(1);	  
	 cellRight.className = 'testClass';
	 var e2 = document.createElement('input');
	 e2.type = 'text';
	 e2.className = "txtfldauto"; 
	 e2.name = 'middlememberName';
	 e2.id = 'middlememberName'+memIndex;
	 e2.setAttribute("onkeydown","checkSplChar1(event)")  //Adding event for special char check
	 e2.setAttribute("placeholder","Middle");
	 e2.setAttribute("onblur","setDepPrintERSName("+memIndex+")")
     e2.setAttribute("autocomplete","off")
	 e2.size = 10;
 	 
	 
	 cellRight.appendChild(e2); 
	 
	  var cellRight = row.insertCell(2);
	 cellRight.className = 'testClass';
	 var e3 = document.createElement('input');
	 e3.type = 'text';
	 e3.className = "txtfldauto"; 
	 e3.name = 'lastmemberName';
	 e3.id = 'lastmemberName'+memIndex;
	 e3.setAttribute("onkeydown","checkSplChar1(event)")  //Adding event for special char check
	 e3.setAttribute("placeholder","Last");
	 e3.setAttribute("onblur","setDepPrintERSName("+memIndex+")")
    e3.setAttribute("autocomplete","off")
	 e3.size = 10; 
	  cellRight.appendChild(e3); 
 	 //End Pramod changes 

	  var cellRight = row.insertCell(3);
	  cellRight.className = 'testClass';	  
	  var textNode = document.createElement('select');  
	  textNode.options[0]=new Option("Select","Select");
	  var enumGend = document.getElementById('genderFamilyEnum').value; 
	  var gendCode;
	  var spltedVals=enumGend.split(',');
	  if(spltedVals.length>0)
	  {
	  	for(var i=0;i<spltedVals.length-1;i++)
  		{
  		 	var gendArr=spltedVals[i].split("::");
  	     	textNode.options[i+1]=new Option(gendArr[0],gendArr[1]);
  		}
  	  }	  	    
	  textNode.name = 'memGender';
	  textNode.id = 'memGender'+memIndex; 
	  textNode.className = "comboauto";  
	  textNode.setAttribute("onchange","setRelationAccToGender(this.value,'"+memIndex+"');")
	  cellRight.appendChild(textNode);
	  
	  
	 var cellRight = row.insertCell(4);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'memdob';
	 el.id = 'dob'+memIndex;
	 el.setAttribute("readonly","readonly")
	 el.size = 10;
	 cellRight.appendChild(el);
	  
	  var cellRight = row.insertCell(5);
	  cellRight.className = 'testClass';	  
	  var textNodeDiv=document.createElement('div')
	  textNodeDiv.id="relDiv"+memIndex
	  var textNode = document.createElement('select');
	  textNode.options[0]=new Option("Select","Select"); 
	  var enumRel = document.getElementById('relationFamilyEnum').value; 
	  var relCode; 
	  var spltVals=enumRel.split(',');
	  if(spltVals.length>0)
	  {
	  	for(var i=0;i<spltVals.length-1;i++)
  		{
  			var relArr=spltVals[i].split("::");
  		    textNode.options[i+1]=new Option(relArr[0],relArr[1]);
  		}
  	  }	  	    
	  textNode.name ='memRelation';
	  textNode.id ='memRelation'+memIndex; 
	  textNode.className = "comboauto";  
	  textNode.setAttribute("onchange","setMarStatAccToRelation(this.value,'"+memIndex+"')");
	  textNodeDiv.appendChild(textNode)
	  cellRight.appendChild(textNodeDiv);
	  
	  var cellRight = row.insertCell(6);
	  cellRight.className = 'testClass';	
	  var marStatDivNode=document.createElement('div')
	  marStatDivNode.id="marStatDiv"+memIndex  
	  var textNode = document.createElement('select');  
	  textNode.options[0]=new Option("Select","Select"); 
	  var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	  var marCode; 
	  var spltedVals=enumMar.split(',');
	  if(spltedVals.length>0)
	  {
	  	for(var i=0;i<spltedVals.length-1;i++)
  		{
  			 var marArr=spltedVals[i].split("::");
  		  	 textNode.options[i+1]=new Option(marArr[0],marArr[1]);
  		}
  	  }	  	    
	  textNode.name = 'memMaritalStatus';
	  textNode.id = 'memMaritalStatus'+memIndex; 
	  textNode.className = "comboauto";  
	  marStatDivNode.appendChild(textNode);
	  cellRight.appendChild(marStatDivNode);
	  
	 var cellRight = row.insertCell(7);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'doIIPartNo';
	 el.id = 'doIIPartNo'+memIndex;
	 el.setAttribute('onclick',"disableTextBox("+memIndex+");");
	  el.setAttribute("autocomplete","off")
	 el.size = 10;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(8);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.setAttribute("readonly","readonly")
	 el.name = 'doIIDate';
	 el.id = 'doIIDate'+memIndex;
	 el.setAttribute('onclick',"disableTextBox(" +memIndex +");");
	 el.size = 10;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(9);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'memReson';
	 el.value = '';
	 el.id = 'memReson'+memIndex;
	 el.setAttribute('disabled','disabled');  
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(10);
	 cellRight.className = 'testClass';
	 //cellRight.width=56;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'ersPrintName';
	 el.setAttribute("readonly","readonly")
	 el.size = 14;
	 el.id = 'ersPrintName'+memIndex; 
	 cellRight.appendChild(el);
	 
	 // Hostel NRS Start
	 
	 var cellRight = row.insertCell(11);
	 cellRight.className = 'testClass';
     //cellRight.width=56;
	 var e1 = document.createElement('input');
	 e1.type = 'text';
	 e1.className = "txtfldauto"; 
	 e1.name = 'childHostelNRS';
	 e1.setAttribute("autocomplete","off")
	 e1.size = 10;
	 e1.value = "";
	 e1.setAttribute("onblur","fillChildHostelNRS('','"+memIndex+"');");
	 e1.setAttribute("onkeyup","getRailStationChild(this,'"+memIndex+"');");
	 e1.setAttribute("placeholder","NRS");
	 e1.setAttribute('disabled','disabled');  
	 e1.id = 'childHostelNRS'+memIndex; 
	 $(e1).attr("autocomplete", "off");

	 var e2 = document.createElement('div');
	 e2.appendChild(e1);

	 var e3 = document.createElement('div');
	 e3.className = "suggestionList";
	 e3.id = 'autoSuggestionsListRailStationChild' + memIndex;

	 var e4 = document.createElement('div');
	 e4.className = "suggestionsBox";
	 e4.id = 'suggestionsRailStationChild' + memIndex;
	 e4.style = 'display: none;';
	 e4.appendChild(e3);

	 var e5 = document.createElement('div');
	 e5.appendChild(e2);
	 e5.appendChild(e4);
	
	 cellRight.appendChild(e5);
     
     // Hostel NRS End
     
     // Hostel NAP Start
	 
	 var cellRight = row.insertCell(12);
	 cellRight.className = 'testClass';
     //cellRight.width=55;
	 var e1 = document.createElement('input');
	 e1.type = 'text';
	 e1.className = "txtfldauto"; 
	 e1.name = 'childHostelNAP';
	 e1.setAttribute("autocomplete","off")
	 e1.size = 10;
	 e1.value = "";
	 e1.setAttribute("onblur","fillChildHostelNAP('','"+memIndex+"');");
	 e1.setAttribute("onkeyup","getAirportChild(this,'"+memIndex+"');");
	 e1.setAttribute("placeholder","NAP");
	 e1.setAttribute('disabled','disabled');  
	 e1.id = 'childHostelNAP'+memIndex; 
	 $(e1).attr("autocomplete", "off");

	 var e2 = document.createElement('div');
	 e2.appendChild(e1);

	 var e3 = document.createElement('div');
	 e3.className = "suggestionList";
	 e3.id = 'autoSuggestionsListAirportChild'+memIndex;

	 var e4 = document.createElement('div');
	 e4.className = "suggestionsBox";
	 e4.id = 'suggestionsAirportChild'+memIndex;
	 e4.style = 'display: none;';
	 e4.appendChild(e3);

	 var e5 = document.createElement('div');
	 e5.appendChild(e2);
	 e5.appendChild(e4);
	
	 cellRight.appendChild(e5);
     
     // Hostel NAP End
	 
	 var el = document.createElement('input');
	 el.type = 'hidden';
	 el.className = "txtfldauto"; 
	 el.name = 'isDepen';
	 el.id = 'isDepen'+memIndex;
	 cellRight.appendChild(el);
   	  
   	  	$("#dob"+memIndex).datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearStart : 1930,
		yearEnd : 2100,
		
		onShow: function () {
			  $("#dob"+memIndex).val("");
		}

	});
		$("#doIIDate"+memIndex).datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#doIIDate"+memIndex).val("");
		}

	});
//	 new Epoch('cal2','popup',document.getElementById('dob'+memIndex),false);
//     new Epoch('cal2','popup',document.getElementById('doIIDate'+memIndex),false);
     
     memIndex++;
     rowIndex++;
}

function removeRow() {
	if(rowIndex>1) {
		var tbl = document.getElementById('tblGrid');
	    var lastRow = tbl.rows.length;
	    tbl.deleteRow(lastRow - 1);
	    rowIndex--;
	}
	memIndex--;
}

/*------------------------------End--------------------------------------------------------*/



function getHttpResponse(url){
 	if (window.XMLHttpRequest) {
        http = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        http = new ActiveXObject("Microsoft.XMLHTTP");
    }
    http.onreadystatechange=getResponse;
	http.open("POST", url, true);
    http.onreadystatechange=getResponse;
 	http.send(null);
}

function getResponse(){
	if (http.readyState == 4) 
	{
	    if (http.status == 200) 
	    {
	    	if(isChkforRankId){getRankDtl(http);}
	    	if(isChkforduplicateTraveller){chkForTravellerProfile(http);}
	    	if(isCallForLevelId){getLevelResponse(http);}
	    	if(isCallForGradePay){getGradePayResponse(http);}
	    }
	}
}


/*----------------------------------------------*/

function validateSpouseDob(dob,gender)
{
  // alert("Inside validateSpouseDob"+dob.value+"||gender="+gender);
   var now = new Date();
   var currentdate = (now.getDate() < 10 ? "0" + now.getDate().toString() : now.getDate().toString()) + "-" +

  (now.getMonth() < 10 ? "0" + (now.getMonth()+1).toString() : now.getMonth().toString()) + "-" + now.getFullYear().toString();

	var cdate=currentdate.split("-");
	
	var currdate=new Date(cdate[2],cdate[1]-1,cdate[0]);
	var currYr=cdate[2];
	
	currYr=parseInt(currYr)+0;
	
	var DOB=dob.val();
	
	if(dob!=null && DOB!="")
	{
		var dateofbirth=DOB.split("/");
		var year=dateofbirth[2];
		year=parseInt(year);
		
		//alert ("year="+year+"||currYr="+currYr);
		if(year>=currYr)
		{
			alert("Please Select Valid Date of Birth");
			dob.focus();
			return false;
		}
		else 
		{
			if(gender==0)
			{
				if((currYr-year)<21)
				{
					
					alert("Date of Birth Should Be Greater Then 21 Years");
					dob.focus();
					return false;
				}
			}
			
			if(gender==1)
			{
				if((currYr-year)<16)
				{
					
					alert("Date of Birth Should Be Greater Then 16 Years");
					dob.focus();
					return false;
				}
			}
		}
	}
	return true;
}

function validateDob(DOB) {
   var now = new Date();
   var currentdate = (now.getDate() < 10 ? "0" + now.getDate().toString() : now.getDate().toString()) + "-" +

  (now.getMonth() < 10 ? "0" + (now.getMonth()+1).toString() : now.getMonth().toString()) + "-" + now.getFullYear().toString();

	var cdate=currentdate.split("-");
	
	var currdate=new Date(cdate[2],cdate[1]-1,cdate[0]);
	var currYr=cdate[2];
	
	currYr=parseInt(currYr)+0;
	if(DOB!="")
	{
		var dateofbirth=DOB.split("/");
		var year=dateofbirth[2];
		year=parseInt(year);
		if(year>=currYr)
		{
			alert("Please Select Valid Date of Birth");
			$("#dob").focus();
			return false;
		}
		else 
		{
			if((currYr-year)>62||(currYr-year)<16)
			{
				alert("Date of Birth Should Be Between 16 To And 62 Years");
				$("#dob").focus();
				return false;
			}
		}
	}
	return true;
}  

function validateDobAndDateOfComm(DOB,DOC) {
	var dateofbirth=DOB.split("/");
	var year=dateofbirth[2];
	year=parseInt(year);
	var dateofComm=DOC.split("/");
	var yearCom=dateofComm[2];
	yearCom=parseInt(yearCom);
	if(year+16>yearCom)
	{
		alert("Date Of Commissioning should be In between Minimum 16 years & Maximum 62 Years from Date Of Birth");
		$("#dob").focus();
		return false;
	}
   
		return true;
}



function checkSplChar(val) {
	$(val).css("background-color", 'white'); 
	var value = $(val).val();
	var flag=true;
	var length=value.length;
	for (var j = 0; j < length; j++)
	{
		var ch = value.substring(j, j + 1);
		if (((ch < "a" || "z" < ch) && (ch < "A" || "Z" < ch) && (ch!=" ")))
		{
			if((ch < "0" || "9" < ch || ch != "_" || ch != "/" || ch != "*" || ch != "+" || ch != "-" || ch != "%" || ch != "^" || ch != "#" || ch != "@" || ch != "!" || ch != "$" || ch != "<" || ch != ">" || ch != ":"))
			{
				alert(" Please use only Characters between \n a to z or A to Z , Numbers and special Characters are not accepted.");
                $(val).focus();
                $(val).select();
			    flag=false;
				return clearText($(val));
				break;
		 	}
		}
	}
	return flag;
} 

function checkSplCharIncludingSpace(val)
{
		val.style.backgroundColor='white'; 
	value=val.value
	var flag=true;
	var length=value.length;
	for (var j = 0; j < length; j++)
	{
		var ch = value.substring(j, j + 1);
		if (((ch < "a" || "z" < ch) && (ch < "A" || "Z" < ch)))
		{
			if((ch < "0" || "9" < ch || ch != "_" || ch != "/" || ch != "*" || ch != "+" || ch != "-" || ch != "%" || ch != "^" || ch != "#" || ch != "@" || ch != "!" || ch != "$" || ch != "<" || ch != ">" || ch != ":"))
			{
				alert(" Please use only Characters between \n a to z or A to Z , Numbers and special Characters are not accepted.");
                val.focus();
                val.select();
			    flag=false;
				return	clearText(val);
				break;
		 	}
		}
	}
	return flag;
} 

function clearText(thefield) {
	$(thefield).select();
}

function isGreaterThanTodayDate(ctl) {
   var cal = new Array();
   cal.JAN = "January";
   cal.FEB = "February";
   cal.MAR = "March";
   cal.APR = "April";
   cal.MAY = "May";
   cal.JUN = "June";
   cal.JUL = "July";
   cal.AUG = "August";
   cal.SEP = "September";
   cal.OCT = "October";
   cal.NOV = "November";
   cal.DEC = "December";

   var sysDate = new Date();
   var effDateChar = $(ctl).val(); // P_EFFECTIVE_DATE
   // translate date entered by user into JS format
   var bufArray = effDateChar.split("/");
   var effDateMonth = bufArray[1];
   var effDateDay = bufArray[0];
   var effDateYear = bufArray[2];
   var effDateDate = new Date(effDateMonth+" "+ effDateDay +
                       ", "+effDateYear+" 23:59:59");

   if (effDateDate > sysDate)
   {
   		alert("Date of Birth must be Equal to less than Today Date");
      	$(ctl).focus();
      	return false;
   }
   return true;
}    	  






function dateOfJOiningTodayDate(ctl) {
   var cal = new Array();
   cal.JAN = "January";
   cal.FEB = "February";
   cal.MAR = "March";
   cal.APR = "April";
   cal.MAY = "May";
   cal.JUN = "June";
   cal.JUL = "July";
   cal.AUG = "August";
   cal.SEP = "September";
   cal.OCT = "October";
   cal.NOV = "November";
   cal.DEC = "December";

   var sysDate = new Date();
   var effDateChar = $(ctl).val(); // P_EFFECTIVE_DATE
   // translate date entered by user into JS format
   var bufArray = effDateChar.split("/");
   var effDateMonth = bufArray[1];
   var effDateDay = bufArray[0];
   var effDateYear = bufArray[2];
   var effDateDate = new Date(effDateMonth+" "+ effDateDay +
                       ", "+effDateYear+" 23:59:59");

   if (effDateDate > sysDate)
   {
   		alert("Date of Joining  must be Equal to less than Today Date");
      	$(ctl).focus();
      	return false;
   }
   return true;
}    	  



 
 
 //For dependant questions
 
 function gotoNextQuestion(curr,next){
     $('#'+next).show();
     $('#'+curr).hide();
 }

function closeWindow(divValue,check,headDiv)
 {
     var idx=$('#famIndex').val();
     if(check=='no')
     {
	     alert("This Dependant will Not Allow To Do Journey");
	     $('#isDepen'+idx).val('0');
	     removeRow();
     }
     if(check=='yes')
     {
	     alert("This Dependant is Eligible To Do Journey");
	     
	     if($('#memRelation'+idx).val()==11)
	     $('#motherCheck').val('true');
	     
	     if($('#memRelation'+idx).val()==10)
	     $('#fatherCheck').val('true');
	     
	     if($('#memRelation'+idx).val()==8)
	     $('#stepMotherCheck').val('true');
	 	 
	 	 if($('#memRelation'+idx).val()==9) 
	 	 $('#stepFatherCheck').val('true');
	     
	     $('#isDepen'+idx).val('1');
     }
     
     $('#depQues').hide();
     if(divValue!="")
     $('#'+divValue).hide();
     
     if(headDiv!="" && headDiv!=undefined)
     {
     	$('#'+headDiv).hide();
     }
     
     var flag=$('#flag').val();
    
     if(flag=="false")
     	validateTravellerProfile()
     else
     	addRow();
 }
 
 
 function doDateCheckStarting(sdate, edate) 
 {
    if (Date.parse(sdate) <= Date.parse(edate)) {
		return true;
	}else{
		return false;
	}
	return true;
}

function checkNoneChar(value){
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

/*---------------------------------Validating Traveller Profile-----------------*/



function validateTravellerProfile(event) {

	var spouseCheck=false;
	var stepMotherCheck=false;
	var stepFatherCheck=false;
	var motherCheck=false;
	var fatherCheck=false;

	 var serviceId = $("#loginVisitorServiceId").val().trim();

	var personalNo = $("#personalNo").val();
	if(personalNo!='') {
		if($("#validatePersonalNo") != null) {
			if(($("#validatePersonalNo").val() == 'false') || ($("#validatePersonalNo").val() == '')) {
			  alert("Please Click On Validate Button");
			  $("#personalNo").focus();
			  return false;
			}
		}
	}
	

	if (serviceId == "1000019" && $("#validateServiceNo").val() == 'false') {
		alert("Please Click On Validate Service Number Button");
		$("#civilianServiceNoAlpha").focus();
		return false;

	}



	var category = $("#categoryId").val();
	var rank = $("#rankId").val();
	var personalNo = $("#personalNo").val();

	var fName = trim($("#fName").val());
	var lName = trim($("#lName").val());
	var dob = trim($("#dob").val());
	var dateOfCom_join = trim($("#dateOfCom_join").val());
	var emailId = trim($("#email").val());
	var mobNo = trim($("#mobNo").val());

	var dutyStnNrs = trim($("#fillStnNrs1").val());
	var homeTown = trim($("#fillStnNrs2").val());
	var homeTownNrs = trim($("#fillStnNrs3").val());
	 var nrsDutyStnAir = $("#suggestionsAirport1").text().trim();
	  var nrsHomeStnAir = $("#suggestionsAirport2").text().trim();
		var sprNrs = trim($("#fillStnNrs5").val());
		var sprNrsResrv = trim($("#fillStnNrs6").val());
	var hmeTwnStnPlace = trim($('[name="hmeTwnStnPlace"]').val());
	var hmeTwnStnNrs=trim($('[name="hmeTwnStnNrs"]').val());
	var sprPlaceID=$("#sprPlaceID").val();
	
    //var cv_fd_used=document.saveCivilianTraveller.cv_fd_used.value;
    var chkForService=$("#loginVisitorServiceName").val();



    var categoryId=$('#categoryId :selected').text().toUpperCase();
    var ersPrntName=trim($("#ersPrntName").val());

    var level=$("#levelId").val();
    var retireAge=$("#retireAge").val();

    var accountNumber=$("#accountNumber").val();
    var ifscCode=$("#ifscCode").val();
  
   
 
    if(category=='-1' || category==''){
		alert("Please select the category");
		$("#categoryId").focus();
		return false;
	}

	if(level=='-1' || level==''){
		alert("Please select the Level");
		$("#levelId").focus();
		return false;
	}
	
	if(rank=='-1' || rank==''){
		alert("Grade Pay is not mapped with selected level.");
		return false;
	}
	
	if(retireAge=='' || parseInt(retireAge) <= 0){
		alert("Retirement date for selected Level is not assigned. Kindly contact DTS Helpline.");
		return false;
	}
	
	if(personalNo==''){
		alert("Please fill the PAN/Personal Number");
		$("#personalNo").focus();
		return false;
	}
	//alert("chkForService-"+chkForService+"||personalNo-"+personalNo);
	if(chkForService.indexOf('COAST GUARD') > -1)
	{	}else{
		if(personalNo.length<10){
			alert("Please Enter Valid PAN Number Of Length 10");
		$("#personalNo").focus();
			return false;
		}
	}
	
		
	if(fName==''){
		alert("Please fill the First Name field");
		$("#fName").focus();
		return false;
	}
	if(!checkSplChar($("#fName")))
	  return false; 


    if($('#mName').val() != ""){    /* Middle Name Validation Special-Char */
      if(!checkSplChar($("#mName")))
          	  return false;
    }
	  
	if(!checkSplChar($("#lName")))
	  return false;


	if(ersPrntName=="") {
		alert("Please fill the ERS Print Name");
		$("#ersPrntName").val("");
		$("#ersPrntName").focus();
		return false;
	} else {
  		if(ersPrntName.length>15)
  		{
  			alert("ERS Print Name can't be greater than 15 letters");
  			$("#ersPrntName").focus();
  			return false;  
  		}if(!checkNoneChar(ersPrntName)){
  			$("#ersPrntName").focus();
  			return false;
  		}
  	}

	var spousePanNumber = $("#spousePanNumber");

	if(spousePanNumber!=null && $(spousePanNumber).val().length>0) {

		if($(spousePanNumber).val().length<10){
			alert("Please Enter Valid PAN Number Of Length 10");
			$(spousePanNumber).focus();
			return false;
		}

		if($(spousePanNumber).val() == personalNo){
			alert("Your Spouse PAN Number Should Not Be Same");
			return false;
		}
	}

 
  if(lName == ''){
		alert("Please fill Last Name");
		$("#lName").focus();
		return false;
	}   


	if((dob=='') || (dob=='dd/mm/yyyy')){
		alert("Please fill the date of birth");
		$("#dob").focus();
		return false;
	}
	
	if(!validateDob(dob))
		return false;

	if((dateOfCom_join=='') || (dateOfCom_join=="dd/mm/yyyy")){
		alert("Please fill the date of joining field");
		$("#dateOfCom_join").focus();
		return false;
	}
	
	if(!dateOfJOiningTodayDate($("#dateOfCom_join")))
	return false;
	if(!validateDobAndDateOfComm(dob,dateOfCom_join))
	return false; 
	
	
	 if(emailId == ''){
		alert("Please fill Email Id");
		$("#email").focus();
		return false;
	} 
	
	if($("#email").val()!=""){
		if(!checkemail($("#email").val())){
		      $("#email").val("");
			 $("#email").focus();
			 return false;
	 	}
	}
	
	   if(mobNo == ''){
		alert("Please fill Mobile Number");
		$("#mobNo").focus();
		return false;
	} 
	
	if($("#mobNo").val()!=""){
		if (!isValidMobileNumber($("#mobNo").val())){
		 $("$mobNo").val("");
			 $("#mobNo").focus();
	 	 return false;
	 	}
	}
	
	if($("#dorBtnClick").val()=="NO") {
	   alert("Please Click Calculate DOR Button.");
	   return false;
	}
	
	
	
	if($("#isOnExtention").val()=="YES") {
		if(!getExtensionRetirementDate())
		return false;
	}
	
	
   
	
	if(dutyStnNrs == '' || dutyStnNrs =='Station Name Not Exist'){
		     alert("Please Enter Nearest Duty Station")
			 $("#fillStnNrs1").focus();
			 return false;
	}
	 
	
	 if(homeTown == ''|| homeTown =='Station Name Not Exist'){
		     alert("Please Enter Home Town")
			 $("#fillStnNrs2").focus();
			 return false;
	}
	
	
	 if(homeTownNrs == ''|| homeTownNrs =='Station Name Not Exist'){
		     alert("Please Enter Home Town NRS")
			 $("#fillStnNrs3").focus();
			 return false;
	}
	
	 
    if(nrsDutyStnAir=='Airport Name Not Exist'){
		 alert("Please Enter Home Town Airport");
		 return false;
	}
	 if(nrsHomeStnAir=='Airport Name Not Exist'){
		 alert("Please Enter Home Town Airport");
		  return false;
	}
	
//	if($("#hmeTwnStnPlace").val()==""){
//		     alert("Please Enter Home Town")
//			 $("#hmeTwnStnPlace").focus();
//			 return false;
//	}
	
//	if($("#hmeTwnStnNrs").val()==""){
//		     alert("Please Enter Home Town NRS")
//			 $("#hmeTwnStnNrs").focus();
//			 return false;
//	}


//	if(accountNumber!="" && (accountNumber.length < 5 || accountNumber.length > 20)){
//		     alert("Account Number Should Be Minuman 5 And Maximum 20 Character.")
//			 $("#accountNumber").focus();
//			 return false;
// 	}
 	
// 	if(accountNumber!="" && !checkNumeric(accountNumber)){
//		     alert("Account Number Should be Numeric Character.")
//			 $("#accountNumber").focus();
//			 return false;
// 	}
 	
// 	if(accountNumber!="" && $("#accountNumber").val()!=$("#reAccountNumber").val()){
// 		 alert("Enter And Re-Enter Account Number Not Match.")
//		 $("#accountNumber").focus();
//		 return false;
// 	}
//	 
// 	if(ifscCode!="" && (ifscCode.length < 11 || ifscCode.length > 11)){
//		     alert("IFSC Code Should Be 11 Character.")
//			 $("#ifscCode").focus();
//			 return false;
// 	}
	 
// 	if(ifscCode!="" && !checkAlphaNumeric(ifscCode)){
//		     alert("IFSC Code Should be AlphaNumeric Character.")
//			 $("#ifscCode").focus();
//			 return false;
// 	}
 	
// 	if(ifscCode!="" && $("#ifscCode").val()!=$("#reIFSCCode").val()){
// 		alert("Enter And Re-Enter IFSC Code Not Match.")
//		$("#ifscCode").focus();
//		return false;
// 	}
 	
//	if(null!=ifscCode && ifscCode!=""){
//	 	let ifsccode = $("#ifscCode").val();
//	 	//alert(ifsccode);
//		var reg = /[A-Z]{4}[0][A-Z0-9]{6}$/;
//		if (ifsccode.match(reg)) {    
//	        //return true;    
//	    }    
//	    else
//	    {    
//	        $("#ifscCode").val("");
//	        $("#reIFSCCode").val("");    
//	        alert("Please enter valid IFSC code");    
//	        $("#ifscCode").focus();  
//	        return false;    
//	    }
// 	}
	 
 	 if(sprNrs!='' || sprNrs!=undefined || sprNrs!=' '){
    if(dutyStnNrs==sprNrs){
	   alert("Nearest Railway Station (Duty Station) And Nearest Railway Station (SPR) cannot be same");
	  return false;
  }
   }
	 
	 	 if(sprNrsResrv!='' || sprNrsResrv!=undefined || sprNrsResrv!=' '){
    if(dutyStnNrs==sprNrsResrv){
	   alert("Nearest Railway Station (Duty Station) And Nearest Railway Station (SPR) cannot be same");
	  return false;
  }
   }
	//document.getElementById("flag").value="false";
  	
  	var noOfFilyDtls=memIndex;


	var memberName;	var memGender; var memdob;	var memRelation;	var memMaritalStatus;
  	var doIIPartNo;	var doIIDate;	var memReson; var isDepen; var countChildDep=0;
  	
     
  	var isFnameDuplicate     = false;
    var isMnameDuplicate     = false;
    var isLnameDuplicate     = false;
    var isDobDuplicate       = false;
	var tbl = document.getElementById('tblGrid');
	var lastRow = tbl.rows.length;
  	$("#lastRowIndex").val(lastRow);

  	var childDOB=[];

	if (lastRow!=0) {
	for(var i=1;i<=lastRow ; i++) {

//		console.log(memIndex + "mem");
//		console.log(i + "idx");

		for(var count=lastRow-1;count>0; count--) {
			if($("#firstmemberName"+i).val() == $("#firstmemberName"+count).val() && count!=i ){
			   isFnameDuplicate=true;
			}
			if($("#middlememberName"+i).val() == $("#middlememberName"+count).val() && count!=i ){
			   isMnameDuplicate=true;
			}
			if($("#lastmemberName"+i).val() == $("#lastmemberName"+count).val() && count!=i ){
			  isLnameDuplicate=true;
			}
			if($("#dob"+i).val() == $("dob"+count).val() && count!=i ){
			  isDobDuplicate=true;
			}

	}
  	    isDepen = $("#isDepen"+i).val();
  		memberName = $("#firstmemberName"+i).val();

  		if(memberName=='') {
  			alert("Please fill the Family member name");
  			$("#memberName"+i).focus();
  			return false;
  		}
  		
  		if(!checkSplChar($("#firstmemberName"+i)))
	     	return false; 

  		memGender=$("#memGender"+i).val();
  		if(memGender=='Select'){
  			alert("Please select the Gender");
  			$("#memGender"+i).focus();
  			return false;
  		}

  		memdob = $("#dob"+i).val();
  		if(memdob==''){
  			alert("Please select the member's date of birth");
  			$("#dob"+i).focus();
  			return false;
  		}
  		if(!isGreaterThanTodayDate($("#dob"+i)))
        return false;

  		memRelation=$("#memRelation"+i).val();
  		if(memRelation==''){
  			alert("Please select the Relation");
  			$("#memRelation"+i).focus();
  			return false;
  		}
  		
  		if(memRelation==1)
	  	{
  			if(!validateSpouseDob($("#dob"+i),memGender))
  			return false;
  		}
	  		
  		
  		if(memRelation==10 && $("#fatherCheck").val()=='true' && isDepen==''){
  			alert("You have already entered Father Information");
  			return false;
  		}
  		
  		if(memRelation==11 && $("#motherCheck").val()=='true' && isDepen==''){
	  		alert("You have already entered Mother Information");
	  		return false;
  		}
  		
  		memMaritalStatus = $("#memMaritalStatus"+i).val();
  		if(memMaritalStatus=='Select'||memMaritalStatus==''){
  			alert("Please select the Marital Status");
  			$("#memMaritalStatus"+i).focus();
  			return false;
  		}
  		
  		ersPrintName=$("#ersPrintName"+i).val();
  		
  		if(ersPrintName==''){
  			alert("Please fill the ERS Print Name");
  			return false;
  		}else
  		{
  			if(ersPrintName.length>15)
  			{
  				alert("ERS Name can't be greater than 14 letters");
  				$("#ersPrintName"+i).focus();
  				return false;
  			}if(!checkNoneChar(ersPrintName)){
  				alert("ERS Name should be between a-z Or A-Z"); 
  				$("#ersPrintName"+i).focus();
  				return false;
  			}
  		}

  		doIIPartNo=$("#doIIPartNo"+i).val();
  		doIIDate=$("#doIIDate"+i).val();
  		memReson=$("#memReson"+i).val();

  		if(doIIPartNo != ""){
			if(!validateDoIIPartNo(doIIPartNo)){
				alert("Special characters !#$%^*' not allowed"); 
				document.getElementById("doIIPartNo" + i).focus();
				return false;
			}
		}

  		if((doIIPartNo=="" || doIIDate=="") &&  memReson==""){
	  		alert("Either enter ( DOII Part No and DOII Date ) OR Reason . Do not fill all three fields.");
	  		$("#memReson"+i).removeAttr('disabled');
	  		return false;
  		}
  		if(doIIDate!=null && doIIDate.length>0) {
  			var dateJoin = $("#dateOfCom_join").val();
  			if(new Date(doIIDate).getTime()<=new Date(dateJoin).getTime()){
		  		 alert("DOII Date Should Be Greater Than Traveler Date Of Joining");
		  		 return false;
	  		}
	  		
	  		var dateOfRetirement=$("#dateOfRetirement").val();
	  		if(new Date(doIIDate).getTime()>new Date(dateOfRetirement).getTime()){
		  		 alert("DOII Date Should Be Less Than Traveler Date Of Retirement");
		  		 return false;
	  		}
	  		var dateOfBirth =trim($("#dob").val());
	  		if(new Date(doIIDate).getTime()<=new Date(dateOfBirth).getTime()){
		  		 alert("DOII Date Should Be Greater Than Traveler Date Of Birth");
		  		 return false;
	  		}
  		}
	  		
  		
  		  //-------------------- DOII AND DEPENDENT DOB VALIDATION START  --------//
  var do2date = doIIDate.split("/");
  var dateformatDate2 = new Date(do2date[2]+","+(do2date[1])+","+do2date[0]);
  var memberDob  = memdob.split("/");
  var memberFormatDob = new Date(memberDob[2]+","+(memberDob[1])+ ","+memberDob[0]);
  
  
  if(memberFormatDob > dateformatDate2){
	  alert("DOII date should not be prior to Dependent's DOB");
	  return false;
  }
  //-----------------VALIDATION ENDS ------------------------------------//
  		
  		
  		
  		
  		
  		
	  		
  		if(memRelation==4 || memRelation==5 || memRelation==3 || memRelation==2){
	  		 var dob = $("dob").val();
	  		 if(new Date(memdob).getTime()<=new Date(dob).getTime()){
		  		 alert("Child date of birth should be less than Traveler date of birth");
		  		 return false;
  		 	}
  		 }
  		 if(memRelation==8 || memRelation==9 || memRelation==10 || memRelation==11){
	  		 var dob = $("dob").val();
	  		 if(new Date(memdob).getTime()>new Date(dob).getTime()){
		  		 alert("Father/Mother date of birth should be greater than Traveler date of birth");
		  		 return false;
  		 	}
  		}
  		
  		
  		
  		var question="";
  		if(isDepen=='')
  		{
  		
	  		if((memRelation==4 || memRelation==5)  && memMaritalStatus==0 )
	  		{
		  		 $("input[name^=incomeRange1]").prop('checked',false);
		  		 $("input[name^=isSept1]").prop('checked',false);
		  		 $("input[name^=isDep1]").prop('checked',false);
		  		 $("input[name^=isDepAsTR1]").prop('checked',false);
		         $('#isSeprt').hide();
		         $('#whlyDep1').hide();
		         $('#DepPerTR1').hide();
		  		 $('#depQues').show();
		  		 $('#incmRang1').show();
		  		 $('#daughMarried').show();
		  		 $('#famIndex').val(i);
		  		 return false;
	  		}
	  		else if(memRelation==7 && memMaritalStatus==1)
	  		{
	  			$("input[name^=isParAliv3]").prop('checked',false);
	  		 	$("input[name^=isUnm3]").prop('checked',false);
	  		 	$("input[name^=isPartDep3]").prop('checked',false);
	  		 	$("input[name^=isResPer3]").prop('checked',false);
	  		  	$("input[name^=incomeRange3]").prop('checked',false);
	  		  	$('#isUnmarr3').hide();
	          	$('#isParAlv3').hide();
	  		  	$('#isParDep3').hide();
	         	$('#isResPers3').hide();
	  		 	$('#incmRange3').show();
	  		 	$('#sister').show();
	  		 	$('#depQues').show();
	  		 	$('#famIndex').val(i);
	  		 	return false;
	  		}
	  		else if(memRelation==6 && memMaritalStatus==1)
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
	  		 	$('#famIndex').val(i);
	  		 	return false;
  			}
  			else if(memRelation==6 && memMaritalStatus==0)
  			{
		  		 closeWindow("","no")
		  		 return false;
  			}
  			else if(memRelation==8 || memRelation==9 || memRelation==10 || memRelation==11)
  			{
  		 		$("input[name^=incomeRange4]").prop('checked',false);
  		 		$("input[name^=isResPer4]").prop('checked',false);
         		$("input[name^=depAsPerTR4]").prop('checked',false);
  		   		$('#isResPers4').hide();
          		$('#depPerTR4').hide();
  	  		    $('#incmRange4').show();
	  		    $('#parStepPar').show();
	  		    $('#depQues').show();
	  		    $('#famIndex').val(i);
  		 		return false;
  			}
  			else if((memRelation==2 || memRelation==3) && memMaritalStatus==0){
  		 		closeWindow("","no")
  		 		return false;
  			}
  		
  			else if(memRelation!=='1'){
  		
  		 		$("input[name^=incomeRange6]").prop('checked',false);
         		$("input[name^=depAsPerTR6]").prop('checked',false);
          		$('#depPerTR6').hide();
          		$('#incmRange6').show();
  		   		$('#depQues').show();
  		   		$('#famIndex').val(i);
  		 		return false;
  			}
  		}
  		
  		if((memRelation==2 || memRelation==3 || memRelation==4 ||memRelation==5) && $.inArray(memdob,childDOB)=== -1){
				childDOB.push(memdob);
			}
			
			if(childDOB.length > 2){
				alert("Children relation should not be more than 2.");
				return false;
			}
  		
  		if(memRelation==1 && spouseCheck==true){
   	 		alert("Spouse relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation==10 && fatherCheck==true){
   	 		alert("Father relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation==11 && motherCheck==true){
   	 		alert("Mother relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation==8 && stepMotherCheck==true){
   	 		alert("Step Mother relation should be inserted only once");
   	 		return false;
   	 	}
   	 	
   	 	if(memRelation==9 && stepFatherCheck==true){
   	 		alert("Step Father relation should be inserted only once");
   	 		return false;
   	 	}
  		
  		if(memRelation==1)
  			spouseCheck=true;
  		
  		if(memRelation==10)
  			fatherCheck=true;
  		
  		if(memRelation==11)
  			motherCheck=true;
  		
  		if(memRelation==8)
  			stepMotherCheck=false;
  		
  		if(memRelation==9)
  			stepFatherCheck=false;
  			
  	}
  		
  	}// member for loop

    if(isFnameDuplicate &&  isMnameDuplicate  && isLnameDuplicate  &&  isDobDuplicate) {
	    alert("Dependent details are duplicate");
	    return false;
    }

	event.currentTarget.submit();

//	var formAction=document.saveCivilianTraveller.action;
//	document.saveCivilianTraveller.action=formAction.replace('/page', "");
//	document.saveCivilianTraveller.submit();
//	return true;

}

/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

function trim(n) {
	return n.replace(/^\s+|\s+$/g,'');
}

function chkForGovtAccom(val) {

   	var sprPlace = $("[name='sprPlace']")[0];
	var sprNrs = $("[name='sprNrs']")[0];
	var sprNa = $("[name='sprNa']")[0];

	if(val=='NO'){
		
		sprPlace.value="";
		sprNrs.value="";
		sprNa.value="";
	   	sprPlace.disabled=true;
	   	sprNrs.disabled=true;
	   	sprNa.disabled=true;
	   	
	}else{
		
	   	sprPlace.disabled=false;
	   	sprNrs.disabled=false;
	   	sprNa.disabled=false;
	}
}

function setAbandonedRow(value)
{
	if(value=='Y'){
	   document.getElementById("abandonedRow").style.display='';
	}
	if(value=='N'){
		document.getElementById("abandonedRow").style.display='none';
	}
	
}
   
function setSuspensionRow(value)
{
	if(value=='Y'){
	   document.getElementById("suspensionRow").style.display='';
	}
	if(value=='N'){
		document.getElementById("suspensionRow").style.display='none';
	}
	
}

function setSpousePannoRow(value)
{
	if(value=='Y'){
	   document.getElementById("spousePannoRow").style.display='';
	}
	if(value=='N'){
		document.getElementById("spousePannoRow").style.display='none';
	}
	
}   
   
   
function setBlockYearScheme(value)
{
	if(value=='Y'){
	   document.getElementById("isLtcAvailCurrentSubBlockY").checked=false;
	   document.getElementById("isLtcAvailCurrentSubBlockY").disabled=true;
       document.getElementById("isLtcAvailCurrentSubBlockN").checked=true;
       document.getElementById("isLtcAvailCurrentSubBlockN").disabled=true;
	}
	if(value=='N')
	{
	   document.getElementById("isLtcAvailCurrentSubBlockY").checked=false;
	   document.getElementById("isLtcAvailCurrentSubBlockY").disabled=false;
       document.getElementById("isLtcAvailCurrentSubBlockN").checked=true;
       document.getElementById("isLtcAvailCurrentSubBlockN").disabled=false;
	}
	
}    


function setYearScheme(value)
{
	if(value=='Y'){
	   document.getElementById("isLtcAvailCurrentYearY").checked=false;
	   document.getElementById("isLtcAvailCurrentYearY").disabled=true;
       document.getElementById("isLtcAvailCurrentYearN").checked=true;
       document.getElementById("isLtcAvailCurrentYearN").disabled=true;
	}
	if(value=='N')
	{
	   document.getElementById("isLtcAvailCurrentYearY").checked=false;
	   document.getElementById("isLtcAvailCurrentYearY").disabled=false;
       document.getElementById("isLtcAvailCurrentYearN").checked=true;
       document.getElementById("isLtcAvailCurrentYearN").disabled=false;
	}
	
}
   
function checkemail(str)
{
 	var filter=/^.+@.+\..{2,3}$/
	if (filter.test(str))
    	return true
 	else {
    	alert("Please enter a valid email address")
    	return false
	}

}
function isValidMobileNumber(str)
{
	if (/^\d{10}$/.test(str)) {
		return true
	}
 	else {
    	alert("Please enter a valid mobile number")
    	return false
}	
}
	

//------------------method for coast guard service -----------------

function getBrowserInfo()
{
	var bName='';
	var useragent = navigator.userAgent;
	bName = (useragent.indexOf('Opera') > -1) ? 'Opera' : navigator.appName;
	return bName;
}

function testForCharacterKey(e) { // KEYPRESS event

	var myBrow=getBrowserInfo();
	var validatePersonalNo = $("#validatePersonalNo");

	if (validatePersonalNo != null) {
		$(validatePersonalNo).val("");
	}

	if(myBrow.indexOf('Netscape')> -1)
	{
		var k;
		if ((e.which >=65 && e.which <=90) || (e.which >=97 && e.which <=122) || (e.which == 8) || (e.which == 0))
		{
		  	return true;
		}else{
			alert("Please Enter Characters Only.");
			e.preventDefault();
		}
	}else{
		return true;	
	}
}

function testForCharacterKeyNavyCivilian(e) { // KEYPRESS event

	var myBrow = getBrowserInfo();


	if (myBrow.indexOf('Netscape') > -1) {
		var k;
		if ((e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122) || (e.which == 8) || (e.which == 0)) {
			return true;
		} else {
			alert("Please Enter Characters Only.");
			e.preventDefault();
		}
	} else {
		return true;
	}
}

function testForNumericKey(e) { // KEYPRESS event
	var validatePersonalNo = $("#validatePersonalNo");

	if (validatePersonalNo != null) {
		$(validatePersonalNo).val("");
	}

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

function testForNumericKeyNaviCivilian(e) { // KEYPRESS event


	var myBrow = getBrowserInfo();

	if (myBrow.indexOf('Netscape') > -1) {
		if ((e.which >= 48 && e.which <= 57) || (e.which == 8) || (e.which == 0)) {
			return true;
		} else {
			alert("Please Enter Only Numerals");
			e.preventDefault();
		}
	} else {
		return true;
	}
}
//-----------------------------

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
	
	var filter="!#$%^*'";
	if (doIIPartNo != "") {
		for (var i = 0; i < doIIPartNo.length; i++) {
			if (filter.indexOf(doIIPartNo.charAt(i)) > -1) {
				return false;
			}
		}
	}
	return true;
}

/* Function to check special char based on event */
function checkSplChar1(event) {
    const alphabetRegEx = /^[A-Za-z ]*$/;
    const key = event.key;
    if (!alphabetRegEx.test(key)) {
        alert("Special characters are not allowed.");
        event.preventDefault();
    }
}


$(document).ready(function() {

 $(".bindCPC").bind('copy paste cut',function(e) { 
 e.preventDefault(); //disable cut,copy,paste
 });

});	
