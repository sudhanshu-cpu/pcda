function getRetirementDate()
{
	   $("#dorBtnClick").val();
	   
 	   var now = new Date();
       var currentdate = (now.getDate() < 10 ? "0" + now.getDate().toString() : now.getDate().toString()) + "-" +
       (now.getMonth() < 10 ? "0" + (now.getMonth()+1).toString() : now.getMonth().toString()) + "-" + now.getFullYear().toString();
  		
  		var cdate=currentdate.split("-");
    	var currdate=new Date(cdate[2],cdate[1]-1,cdate[0]);
    	var currYr=cdate[2];
    	currYr=parseInt(currYr)+0;


		if($("#dob").val()!="")
    	{
    		var dob=$("#dob").val().split("/");
    		var year=dob[2];
    		year=parseInt(year);
    		if(year>=currYr){
	    		alert("Please select valid date of birth");
	    		$("#dob").focus();
	    		return false;
    		}else
    		{
    			var serviceId=$("#loginVisitorServiceId").val();
    			if(serviceId=="100001")
    			{
    				if((currYr-year)>62||(currYr-year)<16)
	    			{
		    			alert("Date Of Birth should not be less than 16 and more than 62 years");
	    				$("#dob").focus();
	    				return false;
	    			}
	    			if((currYr-year)==61||(currYr-year)==62)
	    			{
		    			if(confirm("Are You On Extention Period?")){
			    			 document.getElementById("extensionRow").style.display='';
			    			 new Epoch('cal2','popup',document.getElementById('authorityLetterIssueDate'),false);
			    			 new Epoch('cal2','popup',document.getElementById('extensionStartDate'),false);
			    			 new Epoch('cal2','popup',document.getElementById('extensionEndDate'),false);
							 $("#isOnExtention").val("YES");
		    			}else
		    			{
		    				document.getElementById("extensionRow").style.display='none';
		    				$("#isOnExtention").val("NO");
			    			alert("Date Of Birth should not more than 60 years");
		    				$("#dob").focus();
	    					return false;
		    			}
	    				
	    			}
    			}else
    			{
	    			if((currYr-year)>62||(currYr-year)<16)
	    			{
		    			alert("It should not be less than 16 and not more than 62 years");
	    				$("#dob").focus();
	    				return false;
	    			}
	    		}
     		}
   		}
   
	   
	   if($("#dob").val()!="")
	   {
	   		var rankId=$("#rankId").val();
	   		if(rankId!=null && rankId=="")
	   		{
	   			alert("Please Select Rank.");
				$("#rankId").focus();
    			$("#dorBtnClick").val('NO');
				return false;
	   		}
	   		
     		if($("#retireAge").val()==""){
				 alert("Retirement age not define for selected level.");
				 $("#dorBtnClick").val('NO');
				 return false;
			 }
    		
		    	var age=$("#retireAge").val();
		    	
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
       			
       			var qqq=birthDate+'/'+birthMonth+'/'+retireYear;
       			
       			$("#dateOfRetirement").val(qqq);
     			$("#dorBtnClick").val('YES');
      }

      return true;
}

function getExtensionRetirementDate()
{
	var authorityDetails=document.getElementById("authorityDetails");
	var authorityLetterNo=document.getElementById("authorityLetterNo");
	var authorityLetterIssueDate=document.getElementById("authorityLetterIssueDate");
	var extensionStartDate=document.getElementById("extensionStartDate");
	var extensionEndDate=document.getElementById("extensionEndDate");
	var dateOfRetirement=document.getElementById("dateOfRetirement");
	
	
	if(document.getElementById("dorBtnClick").value=="NO")
	{
	   alert("Please Click Calculate DOR Button.");
	   return false;
	}
	
	if(authorityDetails!=null && authorityDetails.value=="")
	{
		alert("Please Enter Authority Details.");
		authorityDetails.focus();
		return false;
	}
	
	if(authorityLetterNo!=null && authorityLetterNo.value=="")
	{
		alert("Please Enter Authority Letter Number.");
		authorityLetterNo.focus();
		return false;
	}
	
	if(authorityLetterIssueDate!=null && (authorityLetterIssueDate.value=="" || authorityLetterIssueDate.value=="dd/mm/yyyy"))
	{
		alert("Please Select Authority Letter Issue Date.");
		authorityLetterIssueDate.focus();
		return false;
	}else
	{
			var dateALD = authorityLetterIssueDate.value.substring(0, 2);
            var monthALD = authorityLetterIssueDate.value.substring(3, 5);
            var yearALD = authorityLetterIssueDate.value.substring(6, 10);
            
            var ALD = new Date(yearALD, monthALD - 1, dateALD);
            var today = new Date();

            if (ALD > today) 
            {
                alert("Authority Letter Issue Date Should Be Less Then Today Date");
                authorityLetterIssueDate.focus();
				return false;
            }
    }
	
	
	if(extensionStartDate!=null && (extensionStartDate.value=="" || extensionStartDate.value=="dd/mm/yyyy"))
	{
		alert("Please Select Extension Start Date.");
		extensionStartDate.focus();
		return false;
	}else
	{
			 var esd=extensionStartDate.value.split("/");
			 var ESD = new Date(esd[2], esd[1]-1,esd[0]);
			    
             var dor=dateOfRetirement.value.split("/");
	    	 var DOR = new Date(dor[2], dor[1] - 1, dor[0]);
			
			 //alert("ESD="+ESD+"||DOR="+DOR);
             if (ESD < DOR) 
             {
                alert("Extension Start Date Should Be Greater Then or Equal To Retirement Date");
                extensionStartDate.focus();
				return false;
             }
    }
	
	
	if(extensionEndDate!=null && (extensionEndDate.value=="" || extensionEndDate.value=="dd/mm/yyyy"))
	{
		alert("Please Select Extension End Date.");
		extensionEndDate.focus();
		return false;
	}else
	{
			 var esd=extensionStartDate.value.split("/");
			 var ESD = new Date(esd[2], esd[1]-1,esd[0]);
			  
             var dor=dateOfRetirement.value.split("/");
	    	 var DOR = new Date(dor[2], dor[1] - 1, dor[0]);
    
		     var eed=extensionEndDate.value.split("/");
             var EED = new Date(eed[2],eed[1]-1,eed[0]);

             var today = new Date();
            // alert("ESD="+ESD+"||DOR="+DOR+"||EED="+EED+"||today="+today);
           
             if (EED < today ) 
             {
                alert("Extension End Date Should Be Greater Then Today Date");
                extensionEndDate.focus();
				return false;
             }
            
             if (EED < DOR) 
             {
                alert("Extension End Date Should Be Greater Then Retirement Date");
                extensionEndDate.focus();
				return false;
             }
             
             if (EED < ESD)
             {
                alert("Extension End Date Should Be Greater Then Extension Start Date");
                extensionEndDate.focus();
				return false;
             }
    }
   
    return true;    			
}

//---  Coast Guard personal number validation  method -- 
var coast_Enroll_Officer = new Array("X","C","D","E","J","L","M","P","Q","S","V");
var coast_Enroll_Sub_Officer = new Array("Z","H","L","M","P","Q","R","S","T","W","Y");

//FOR NAVYCIVILIAN
var  cadre_code_Num = new Array("A","B","F","H","K","N","R","T","W","Y","Z");

	function validateCivilianServiceNo() {
	
	var serviceId = $("#loginVisitorServiceId").val().trim(); 
	 if(serviceId !="1000019" ){
		return true;
	 }
	
	var prnslNoChar = $("#civilianServiceNoAlpha").val().trim();
	var serviceNo = $("#civilianServiceNo").val().trim();
	var prefixNo = $("#civilianServiceNoPrefix").val().trim();
	 
	
	if(serviceNo!="" && prnslNoChar!="" && prefixNo!=""){
		
		if(prefixNo.length > 2 || prefixNo.length < 2){
			alert("Only Two Digits Are Allowed.");
			 $("#civilianServiceNoPrefix").focus();
			return false;
		}
		
		if(serviceNo.length > 5 || serviceNo.length < 5){
			alert("Only Five Digits Are Allowed.");
			 $("#civilianServiceNo").focus();
			return false;
		}
	
		var navyCivilianPersonalNo = prefixNo+serviceNo+prnslNoChar;
		var sum = 0;
		for (var i = 0; i < navyCivilianPersonalNo.length-1; i++) {
			sum +=   parseInt(navyCivilianPersonalNo.charAt(i)) ;
			
		}
		
		
		var cadreNoArrayIndex = sum % 11;
	
		if (cadre_code_Num[cadreNoArrayIndex] != prnslNoChar) {
			alert("Please Enter Correct Service No.");
			$("#civilianServiceNoAlpha").value = "";
	        $("#civilianServiceNoAlpha").focus();
	        return false;
			
		}
	
		chkForDuplicateNavyCivilianPersonalNo(navyCivilianPersonalNo)
	
   }else{
		alert("Please Enter Service No.");
		 $("#civilianServiceNo").focus();
		return false;
		
	}
		

	}
function chkForDuplicateNavyCivilianPersonalNo(navyCivilianPersonalNo) {
	
	$.ajax(
		{
			url: $("#context_path").val() + "mb/checkDuplicateNavyCivilianServiceNo",
			type: "post",
			data: "NavyCivilianPersonalNo=" + navyCivilianPersonalNo,
			dataType: "text",
			async: false,

			success: function(msg) {
				if ("NOT" != msg) {
					alert("Service Number entered already exist.\nPlease contact DTS at helpdesk@pcdatravel.gov.in or 011-26700300.");
					document.getElementById("civilianServiceNoAlpha").value = "";
					document.getElementById("civilianServiceNo").value = "";
					document.getElementById("civilianServiceNo").focus();
					return false;
				}
				else {
					alert("Service Number Is Valid.");
					document.getElementById("validateServiceNo").value = "true";
					$("#civilianPersonalNo").val(navyCivilianPersonalNo);
					
					
					document.getElementById("civilianServiceNoPrefix").setAttribute("readonly", true);
					document.getElementById("civilianServiceNoPrefix").style.backgroundColor = "#eeeedd";
					
					document.getElementById("civilianServiceNo").setAttribute("readonly", true);
					document.getElementById("civilianServiceNo").style.backgroundColor = "#eeeedd";
					
					document.getElementById("civilianServiceNoAlpha").setAttribute("readonly", true);
					document.getElementById("civilianServiceNoAlpha").style.backgroundColor = "#eeeedd";
					return true;
				}
			}

		});

}



var chkForNo;	
function checkAlphaNumber1(){

	chkForService = $("#loginVisitorServiceName").val();

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
		
//	var alphaNo = $("#alphaNoId option:selected").text();
	var alphaNo = "";
	var alphaNum = $("#alphaNoId option:selected").text();

	if (alphaNum == 'NA') {
		alphaNo = "";
	} else {
		alphaNo = alphaNum;
	}
	
	var personalNo = $("#personalNo").val();
	var chkAlpha = $("#chkAlpha").val();

	if(chkForService.indexOf('COAST GUARD') > -1)
	{
		
		if(personalNo==''){
			alert("Please fill the personal field");
			$("#personalNo").focus();
		return false;
		}
		
		if(categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1 || categoryNameVal.indexOf('Civilian Gazetted Officers') > -1)
		{
			if(categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1)
			{
				
				if(alphaNo==''){
					alert("Please fill Prefix");
					document.saveCivilianTraveller.alphaNo.focus();
		return false;
				}
				if(alphaNo!='S'){
					alert("Please fill Suffix only 'S' character");
					document.saveCivilianTraveller.alphaNo.focus();
		return false;
				}
			  if (isNaN(personalNo) || personalNo < 00001 || personalNo > 17000) {
			  		alert("Please Enter Personal No. From 00001 To 17000");
			        document.saveCivilianTraveller.personalNo.focus();
		return false;
				}

				chkForNo=alphaNo+personalNo;

			}
			else if(categoryNameVal.indexOf('Civilian Gazetted Officers') > -1)
			{
				if(chkAlpha != ''){
					alert("Please do not fill Suffix");
					document.saveCivilianTraveller.chkAlpha.focus();
		return false;
				}
				if (isNaN(personalNo) || personalNo < 17001 || personalNo > 99999) {
			  		alert("Please Enter Personal No. From 17001 To 99999");
			        $("#personalNo").focus();
		return false;
			    }

				chkForNo=personalNo;
			}

		}
		else if(categoryNameVal.indexOf('Enrolled Personnel') > -1)
			{
				if(alphaNo == ''){
					alert("Please fill Alpha Prefix");
					document.saveCivilianTraveller.alphaNo.focus();
		return false;
				}
				if(chkAlpha == ''){
					alert("Please fill Alpha Suffix");
					$("#chkAlpha").focus();
		return false;
				}
				chkForNo=alphaNo+personalNo+chkAlpha;
			}
			else if(categoryNameVal.indexOf('Coast Guard Officers') > -1)
			{
				if(alphaNo == ''){
					alert("Please fill Alpha Prefix");
					document.saveCivilianTraveller.alphaNo.focus();
		return false;
				}
				if(chkAlpha == ''){
					alert("Please fill Alpha Suffix");
					$("#chkAlpha").focus();
		return false;
				}
				chkForNo=alphaNo+personalNo+chkAlpha;
			}
			else
		{
			if(alphaNo != ''){
				alert("Please do not fill Alpha Prefix");
				document.saveCivilianTraveller.alphaNo.focus();
		return false;
			}
			chkForNo=personalNo+chkAlpha;
		}
	}

	if(chkForService.indexOf('COAST GUARD') > -1 || chkForService.indexOf('COASTGUARD') > -1)
	{
		/*
		if(chkAlpha==''){
			alert("Please fill the alpha no field");
			document.saveCivilianTraveller.chkAlpha.focus();
			return false;
		}*/
		if(!chkForCoastGaurd(personalNo,chkAlpha,categoryNameVal))
		return false;
	}

	chkForDuplicateTraveller(chkForNo);

}





function chkForCoastGaurd(personalNo,chkAlpha,categoryNameVal) {

	var coastGaurdIndex;
	coastGaurdIndex=personalNo%11;

	if(categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1 || categoryNameVal.indexOf('Civilian Gazetted Officers') > -1)
	{
		if(categoryNameVal.indexOf('Civilian Gazetted Officers') > -1)
		{
			var minVal =  17001; //Minimum value
			var maxVal =  99999; //Maximum value
			
			if(personalNo.length < 5){
				alert("Please enter correct personal number");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");

				$("#personalNo").focus();
				return false;
			}
			else if(parseInt(personalNo) < minVal || (parseInt(personalNo) > maxVal))
			{
				alert("Please enter correct personal number");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");

				$("#personalNo").focus();
				return false;
			}

			return true;

		} //End of Civilian Gazetted Officers

		if(categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1)
		{
			var minVal =  1; //Minimum value
			
			if(personalNo.length < 5)
			{
				alert("Please enter correct personal number");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;
			}
			else if(parseInt(personalNo) < minVal)
			{
				
				alert("Please enter personal number in between valid range");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;
			}
			
			return true;
			
		} //End of Civilian NonGazetted Personnel
		
	}// End of Civilian NonGazetted Personnel and Civilian Gazetted Officers
	else if(categoryNameVal.indexOf('Coast Guard Officers') > -1 || categoryNameVal.indexOf('Enrolled Personnel') > -1)
	{
		
		if(categoryNameVal.indexOf('Coast Guard Officers') > -1)
		{
			
			var minVal =  1; //Minimum value
			var maxVal =  5999; //Maximum value
			
			if(personalNo.length < 4 || personalNo.length > 4){
				alert("Please enter correct personal number of 4 digit.");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;
			}
			
			if((parseInt(personalNo) == 4000 ) || (parseInt(personalNo) == 5000)){
				
				alert("Please enter personal number in between valid range");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;
				
			}
			
			if((parseInt(personalNo) < minVal) || (parseInt(personalNo) > maxVal)){
				
				alert("Please enter personal number in between valid range");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;
				
			}
			
			if(coast_Enroll_Officer[coastGaurdIndex] != chkAlpha)
			{
				alert("Please enter correct check alpha number");
				$("#chkAlpha").val("");
				$("#chkAlpha").focus();

				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
				
				return false;
			}
			
			return true;
	
		} //End of Enroll Officer or new name Coast Guard Officers
		
		
		if(categoryNameVal.indexOf('Enrolled Personnel') > -1)
		{
			
			var minVal =  1; //Minimum value
			var maxVal =  99999; //Maximum value
			
			if(personalNo.length < 5 || personalNo.length > 5){
				alert("Please enter correct personal number of 5 digit.");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;
			}
			
			if((parseInt(personalNo) < minVal) || (parseInt(personalNo) > maxVal)){
				
				alert("Please enter personal number in between valid range");
				
				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");
					
				$("#personalNo").focus();
				return false;

			}

			if(coast_Enroll_Sub_Officer[coastGaurdIndex] != chkAlpha)
			{
				alert("Please enter correct check alpha number");
				$("#chkAlpha").val("");
				$("#chkAlpha").focus();

				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");

				return false;
			}

			return true;

		} //End of Enroll Sub Officer
	}// End of Enrolled Personnel and Coast Guard Officers
	else
	{
		//For Others
		if(personalNo.length < 4){
			alert("Personal number length can not be less then 4 digit.");

				if ($("#validatePersonalNo") != null)
					$("#validatePersonalNo").val("false");

				$("#personalNo").focus();
			return false;
		}

		return true;
	}

}
