var httpRes;
var isCallForCategoryId=false;
var isCallForLevelId=false;
var isCallForGradePay=false;


function getHttpURLResponse(url){
 	if (window.XMLHttpRequest) {
        httpRes = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        httpRes = new ActiveXObject("Microsoft.XMLHTTP");
    }
 
    httpRes.onreadystatechange=getURLResponse;
    httpRes.open("GET", url, true);
    httpRes.onreadystatechange=getURLResponse;
 	httpRes.send(null);
}

function getURLResponse()
{	
	if (httpRes.readyState == 4) 
	{
	    if (httpRes.status == 200) 
	    {
	    	if(isCallForCategoryId){
	    		getCategoryResponse(httpRes);
	    	}
	    	if(isCallForLevelId){
	    			getLevelResponse(httpRes);
	    	}
	    	 if(isCallForGradePay){
	    			getGradePayResponse(httpRes);
	    	}
	    	
	    
		}
	}
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
	alphaNoId.children('option:not(:first)').remove();
	$(doc).find("PREFIX_DTLS").each(function(){
 	   var prefixOption = document.createElement("option");
 		prefixOption.value = $(this).find("PREFIX").text();
 		prefixOption.text = $(this).find("PREFIX").text();
 	    alphaNoId.append(prefixOption);
    });
    /* Code Block To Set Personal Number Prefix End */
    
    /* Code to show pao office on the basis of service and category*/
    
    if(typeOfUser=='traveller')
    {
    	setPaoOffice();
    }
}

function getCategoryResponse(httpRes)
{
	$("#rankNameInner").html("");
	$("#rankId").val("");
	$("#retireAge").val("");
	
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
	categoryStr+='<select name=\"category\" class=\"combo\" id=\"categoryId\" onchange=\"getLevelDetails();\" style=\"width: 80%;\">';
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
	
	$("#levelId").children('option:not(:first)').remove();
	
}


