var http;
var respText;
var isChkforCategoryId=false;
var isChkforRankId=false;
var isChkforUnitList=false;
var typeOfUser="";








function getHttpResponse(url)
{
 	if (window.XMLHttpRequest) {
        http = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        http = new ActiveXObject("Microsoft.XMLHTTP");
    }
 
    http.onreadystatechange=getResponse;
    http.open("GET", url, true);
    http.onreadystatechange=getResponse;
 	http.send(null);
}

function getResponse()
{	
	if (http.readyState == 4) 
	{
	    if (http.status == 200) 
	    {
	    	if(isChkforCategoryId){
	    		getCategoryDtl(http);
	    	}
	    	if(isChkforRankId){
	    			getRankDtl(http);
	    	}
	    	if(isChkforUnitList){
	    			getUnitDtl(http);
	    	}
	    	
	    
		}
	}
}



function getUnitDtl(http)
{
	
	var respText=http.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	
	var unitListDoc =doc.documentElement;	
	
	var unitIdDoc=unitListDoc.getElementsByTagName('unitCode');
	var unitNameDoc=unitListDoc.getElementsByTagName('unitName');
	
	var unitTdIdObj=document.getElementById('unitTdId');
	
		
	var unitStr="";
	unitStr+='<select name=\"unitNo\" class=\"combo\" id=\"unitNo\" >';
	unitStr+='<option value=\"-1\">Select</option>';
	for(var i=0;i < unitNameDoc.length ; i++){
		if(unitNameDoc[i].firstChild!=null && unitIdDoc[i].firstChild!=null)
		unitStr+='<option value=\"'+unitNameDoc[i].firstChild.nodeValue+'\">'+unitNameDoc[i].firstChild.nodeValue+'</option>';
	}
	unitStr+='</select>';	
	if(unitTdIdObj!=null)
		unitTdIdObj.innerHTML=unitStr;
		
}

function getCategoryDtl(http)
{
	
	var respText=http.responseText;	
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
	categoryStr+='<select name=\"category\" class=\"combo\" id=\"categoryId\" onchange=\"getRankId();\">';
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
	var rankStr="";
	rankStr+='<select name=\"rank\" class=\"combo\" id=\"rankId\">';
	rankStr+='<option value=\"-1\">Select</option>';	
	rankStr+='</select>';
	var rankIdStr=document.getElementById("rankIdTd");
	rankIdStr.innerHTML=rankStr;
		
	getUnitList();
}


function getRankDtl(http)
{
	var respText=http.responseText;
	respText = respText.replace(/&/g,"and");
	
	var doc=getXML(respText);
	var rankDoc =doc.documentElement;	
	
	if(document.getElementById('RankName')!=null){
		var RankName=document.getElementById('RankName').value;
	}

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
			if(RankName==rankName[i].firstChild.nodeValue){
				rankStr+='<option value=\"'+rankId[i].firstChild.nodeValue+','+retireAge[i].firstChild.nodeValue+'\" selected="selected">'+rankName[i].firstChild.nodeValue+'</option>';
			}
			else{
				rankStr+='<option value=\"'+rankId[i].firstChild.nodeValue+','+retireAge[i].firstChild.nodeValue+'\">'+rankName[i].firstChild.nodeValue+'</option>';
			}
		}
	}
	
	rankStr+='</select>';
	
	var rankIdStr=document.getElementById("rankIdTd");
    rankIdStr.innerHTML=rankStr;
    
    /* Code Block To Set Personal Number Prefix Start */
    var prefixNameArr=rankDoc.getElementsByTagName("prefix");
    var prefixSelectEleStr="";
	if(prefixNameArr.length>0)
	{
		prefixSelectEleStr+='<option value=\"\">NA</option>';
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
    
    /* Code to show pao office on the basis of service and category*/
    
    if(typeOfUser=='traveller')
    {
    	setPaoOffice();
    }
	
	
}

function getRankDtl_OLD(http)
{
	var respText=http.responseText;
	respText = respText.replace(/&/g,"and");
	
	var doc=getXML(respText);
	var rankDoc =doc.documentElement;	
	
	if(document.getElementById('RankName')!=null){
		var RankName=document.getElementById('RankName').value;
	}

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
			if(RankName==rankName[i].firstChild.nodeValue){
				rankStr+='<option value=\"'+rankId[i].firstChild.nodeValue+','+retireAge[i].firstChild.nodeValue+'\" selected="selected">'+rankName[i].firstChild.nodeValue+'</option>';
			}
			else{
				rankStr+='<option value=\"'+rankId[i].firstChild.nodeValue+','+retireAge[i].firstChild.nodeValue+'\">'+rankName[i].firstChild.nodeValue+'</option>';
			}
		}
	}
	
	rankStr+='</select>';
	
	var rankIdStr=document.getElementById("rankIdTd");
    if(typeOfUser=='traveller')
    {
    	 var serviceId=$('#alternateService :selected').text().toUpperCase();
    	 var unitServiceName=document.getElementById("serviceNameId").value;
    	 
    	 if (unitServiceName.indexOf('COAST GUARD') > -1)
    	 {  //coast guard service login 
    	 	
    	 	/*if (serviceId.indexOf('COAST GUARD') > -1 || serviceId.indexOf('NAVY') > -1 || serviceId.indexOf('ARMY') > -1 || serviceId.indexOf('AIRFORCE') > -1 || serviceId.indexOf('AIR FORCE') > -1)  */
	    	
    	 	if (serviceId.indexOf('COAST GUARD') > -1 ) 
	    	{
			 	var groupString=document.getElementById("groupString").value;
		 		var groupArr=groupString.split(",");
		 		var payAccOpt="";
		 		for(var n=0;n<=groupArr.length;n++)
		 		{
		 			if(groupArr[n]!=undefined)
		 			{
		    			if(	groupArr[n].indexOf('PCDA(Navy)Mumbai')>-1 		|| 
			    			groupArr[n].indexOf('CDA(N)Chennai')>-1 		||
			    			groupArr[n].indexOf('CDA(N)Kochi')>-1 			||
			    			groupArr[n].indexOf('CDA(N)Goa')>-1 			||
			    			groupArr[n].indexOf('CDA(N)Kolkatta')>-1 		||
			    			groupArr[n].indexOf('CDA(N)Portblair')>-1 		||
			    			groupArr[n].indexOf('CDA(N)Visakhapattanam')>-1 ||
			    			groupArr[n].indexOf('CDA(N)Delhi')>-1 			||
			    			groupArr[n].indexOf('CDA(N)Karwar')>-1 			    						    						    						    						    						    						    						    						    			
		    			  )
		    			{
			      			var spltArr=groupArr[n].split("::")
		      				payAccOpt+='<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
		    			}
		    		} 
				}
				$("#payAcOff").html(payAccOpt);
				
			 }else if (serviceId.indexOf('DSC') > -1)
			 {
				var groupString=document.getElementById("groupString").value;
		 		var groupArr=groupString.split(",");
		 		var payAccOpt="";
		 		for(var n=0;n<=groupArr.length;n++)
		 		{
		 			if(groupArr[n]!=undefined)
		 			{
		    			if(groupArr[n].indexOf('DSC')>-1){
			      			var spltArr=groupArr[n].split("::")
		      				payAccOpt+='<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
		    			}
		    		} 
				}
				$("#payAcOff").html(payAccOpt);
			 }else{
				setPaoOffice();
			 }
    	 }else
    	 {
    	 	// When login Unit service is other then coast gaurd
    	 	if (serviceId.indexOf('COAST GUARD') > -1) 
	    	{
			 	var groupString=document.getElementById("groupString").value;
		 		var groupArr=groupString.split(",");
		 		var payAccOpt="";
		 		for(var n=0;n<=groupArr.length;n++)
		 		{
		 			if(groupArr[n]!=undefined)
		 			{
		    			if(	groupArr[n].indexOf('PCDA(Navy)Mumbai')>-1 		|| 
			    			groupArr[n].indexOf('CDA(N)Chennai')>-1 		||
			    			groupArr[n].indexOf('CDA(N)Kochi')>-1 			||
			    			groupArr[n].indexOf('CDA(N)Goa')>-1 			||
			    			groupArr[n].indexOf('CDA(N)Kolkatta')>-1 		||
			    			groupArr[n].indexOf('CDA(N)Portblair')>-1 		||
			    			groupArr[n].indexOf('CDA(N)Visakhapattanam')>-1 ||
			    			groupArr[n].indexOf('CDA(N)Delhi')>-1 			||
			    			groupArr[n].indexOf('CDA(N)Karwar')>-1 			    						    						    						    						    						    						    						    						    			
		    			  )
		    			{
			      			var spltArr=groupArr[n].split("::")
		      				payAccOpt+='<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
		    			}
		    		} 
				}
				$("#payAcOff").html(payAccOpt);
				
			}else if (serviceId.indexOf('DSC') > -1)
			{
				var groupString=document.getElementById("groupString").value;
		 		var groupArr=groupString.split(",");
		 		var payAccOpt="";
		 		for(var n=0;n<=groupArr.length;n++)
		 		{
		 			if(groupArr[n]!=undefined)
		 			{
		    			if(groupArr[n].indexOf('DSC')>-1){
			      			var spltArr=groupArr[n].split("::")
		      				payAccOpt+='<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
		    			}
		    		} 
				}
				$("#payAcOff").html(payAccOpt);
			}else{
				setPaoOffice();
			}
    	 }
    	 
		
	}
	rankIdStr.innerHTML=rankStr;
	/*
	var s=rankStr.split(",");
	var a=s[1].split("<");
	var z=a[0].split('"');
	document.getElementById("retireAge").value=z[0];
	*/

}



function showOldPersonalNumber(){
	
	var transferFlag=true;
	var formObj=$("#transferInFormId").val();
	if(formObj){
		transferFlag=false;
	}
	$("#navy_airforce_cadit").hide();
	$("#alphaNoId").val("");
	if(transferFlag){
	$("#personalNo").val("");
	}
	$("#chkAlpha").val("");
	$("#old_personal_number").show();
	
}

function showNavyAirforceCadit(){
	$("#old_personal_number").hide();
	$("#cadetNo").val("");
	$("#cadetChkAlpha").val("");
	$("#courseSerialNo").val("");
	$("#airForceCadetNo").val("");
	
	if(checkAirforceCadet()){
		$("#showcadetNo").hide();
		$("#showCadetChkAlpha").hide();
		$("#showCourseSerialNo").hide();
		$("#showAirForceCadets").show();
		
	}
	
	/*
	else if(checkNavyArchitectOffice()){
		$("#showcadetNo").show();
		$("#showCadetChkAlpha").hide();
		$("#showCourseSerialNo").hide();
		$("#showAirForceCadets").hide();
		
	} */
	else{
		$("#showcadetNo").show();
		$("#showCadetChkAlpha").show();
		$("#showCourseSerialNo").show();
		$("#showAirForceCadets").hide();
		
	}
	
	$("#navy_airforce_cadit").show();
}

function checkNavyAirforceCadets(){

	var serviceNameText=$("#alternateService option:selected").text();
	var catNameText=$("#categoryId option:selected").text();
	
	serviceNameText = serviceNameText.toUpperCase();
	catNameText = catNameText.toUpperCase();  

	if(serviceNameText.indexOf('NAVY') > -1 && (catNameText.indexOf('INAC CADETS') > -1 ||catNameText.indexOf('NOC CADETS') > -1 || catNameText.indexOf('EX-NDA CADETS') > -1 ||
	catNameText.indexOf('ARCHITECT CADETS') > -1 ) ){
		return true;
	}else if((serviceNameText.indexOf('AIRFORCE') > -1 || serviceNameText.indexOf('AIR FORCE') > -1) && catNameText.indexOf('CADET') > -1){
		return true;
	}
	
	return false;
}

function checkNavyArchitectOffice(){
	
	var serviceNameText=$("#alternateService option:selected").text();
	var catNameText=$("#categoryId option:selected").text();
	
	serviceNameText = serviceNameText.toUpperCase();
	catNameText = catNameText.toUpperCase();  
	
	if(serviceNameText.indexOf('NAVY') > -1 && catNameText.indexOf('ARCHITECT CADETS')> -1 ){
		return true;
	}
	
	return false;
}

function checkAirforceCadet(){
	
	var serviceNameText=$("#alternateService option:selected").text();
	var catNameText=$("#categoryId option:selected").text();

	serviceNameText = serviceNameText.toUpperCase();
	catNameText = catNameText.toUpperCase();  
	
	if((serviceNameText.indexOf('AIRFORCE') > -1 || serviceNameText.indexOf('AIR FORCE') > -1) && catNameText.indexOf('CADET') > -1){
		return true;
	}
	
	return false;
}

