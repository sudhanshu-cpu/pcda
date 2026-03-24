var k = 1;

function testPersonalValidationCheck() {

	if ($("#validatePersonalNo") != null) {
		if ($("#validatePersonalNo").val() == '') {
			alert("Please Click On Validate Personal Number Button");
			$("#validatebutton").focus();
			return false;
		}
	}

	return true;
}

function addRow(){
var services = $("#alternateService option:selected").text();
var dateOfCom_join = trim($("#dateOfCom_join").val());
 var spouseCount=0;
 
  	var tbl = document.getElementById('tblGrid');
    
  	var lastRow = tbl.rows.length;
  	document.getElementById("flag").value="true";

  	for(var c=0;c<lastRow;c++)
  	{
  		if(document.getElementById("memRelation"+c)!=null)
  		{
  		if(document.getElementById("memRelation"+c).value==1)
  		spouseCount++;
  		}
  	}
	
	var firstmemberName; var middlememberName; var lastmemberName;

  	if(k>1){
  	var k1=k-1
    	var memGender; var memdob;	var memRelation;	var memMaritalStatus;
  	var doIIPartNo;	var doIIDate;	var memReson; var isDepen; var countChildDep=0;
   firstmemberName=document.getElementById("firstmemberName"+k1).value;
   
   lastmemberName=document.getElementById("lastmemberName"+k1).value;
   
  	   isDepen=document.getElementById("isDepen"+k1).value;
  	   if(firstmemberName==''){
  			alert("Please fill the Family member First name");
  			document.getElementById("firstmemberName"+k1).focus();
  			return false;
  		}
  		
  		
  		
  	/*	if(lastmemberName==''){
  			alert("Please fill the Family member Last name");
  			document.getElementById("lastmemberName"+k1).focus();
  			return false;
  		} */
  		
  		if(!checkSplChar($("#firstmemberName"+k1)))
	     return false; 
	     
	     if(!checkSplChar($("#lastmemberName"+k1)))
	     return false; 
	     
  	    memGender=document.getElementById("memGender"+k1).value;
  		if(memGender=='Select'){
  			alert("Please select the Gender");
  			document.getElementById("memGender"+k1).focus();
  			return false;
  		}
  		memdob=document.getElementById("dob"+k1).value;
  		if(memdob==''){
  			alert("Please select the member Date of Birth");
  			document.getElementById("dob"+k1).focus();
  			return false;
  		}
  		if(!isGreaterThanTodayDate(document.getElementById("dob"+k1)))
        return false;
  		memRelation=document.getElementById("memRelation"+k1).value;
  		//alert("memrelation-->"+memRelation)
  		if(memRelation=='Select' || memRelation==""){
  			alert("Please select the Relation");
  			document.getElementById("memRelation"+k1).focus();
  			return false;
  		}
  		
  			
  		
  		if(memRelation==1 && document.getElementById("spouseCheck").value=='true' && spouseCount>1 && isDepen==''){
  		alert("You have already entered Spouse Information");
  		$('#tblGrid tr:last').remove();
		 k--;
		return false;
  		}
  		
  		if(memRelation==10 && document.getElementById("fatherCheck").value=='true' && isDepen==''){
  		alert("You Have Already Entered Father Information");
  		$('#tblGrid tr:last').remove();
		 k--;
  		return false;
  		}
  		
  		if(memRelation==11 && document.getElementById("motherCheck").value=='true' && isDepen==''){
  		alert("You Have Already Entered Mother Information");
  		$('#tblGrid tr:last').remove();
		 k--;
  		return false;
  		}
  		
  		if(memRelation==9 && document.getElementById("stepFatherCheck").value=='true' && isDepen==''){
  		alert("You have already entered Step Father Information");
  		$('#tblGrid tr:last').remove();
		 k--;
  		return false;
  		}
  		
  		if(memRelation==8 && document.getElementById("stepMotherCheck").value=='true' && isDepen==''){
  		alert("You have already entered Step Mother Information");
  		$('#tblGrid tr:last').remove();
		 k--;
  		return false;
  		}
  		
  		memMaritalStatus=document.getElementById("memMaritalStatus"+k1).value;
  		if(memMaritalStatus=='Select'){
  			alert("Please select the Marital Status");
  			document.getElementById("memMaritalStatus"+k1).focus();
  			return false;
  		}
  		
  		doIIPartNo=document.getElementById("doIIPartNo"+k1).value;
  		doIIDate=document.getElementById("doIIDate"+k1).value;
  		memReson=document.getElementById("memReson"+k1).value;
  		
  		
  		if((doIIPartNo=="" || doIIDate=="") &&  memReson==""){
		//	  alert("in tra prof add row "+services);
			  if(services=="Navy"){
					alert("Enter either ( GX NO & GX Date) OR Reason. Do not fill all three fields.");
				}else if(services=="AirForce"){
					alert("Enter either (POR No & POR Date) OR Reason. Do not fill all three fields.");
				}else{
				alert("Either enter (DOII Part No & DOII Date) OR Reason. Do not fill all three fields.");
				}
  	
  		$("#memReson"+k1).removeAttr('readonly');
  		return false;
  		}
  	
  		
  		var do2date = doIIDate.split("/");
  	    var dateformatDate2 = new Date(do2date[2]+","+(do2date[1])+","+do2date[0]);
  	    if(dateformatDate2>=new Date()){
		  if(services=="Navy"){
					alert("GX Date should be less than or equal to today's date");
				}else if(services=="AirForce"){
					alert("POR Date should be equal to or less than today's  date");
				}else{	  
		alert("DOII Date should be equal to or less than today's  date");
		        }
  		$("#memReson"+k1).removeAttr('readonly');
  		return false;
  		}
  		
  		  	var docdate=dateOfCom_join.split("/");
         var docDateFormat=new Date(docdate[2]+","+(docdate[1])+","+docdate[0]);
  if(docDateFormat>dateformatDate2){
	  
	  
	   if(services=="Navy"){
					alert("GX Date should be greater than Date of Enrollment date");
				}else if(services=="AirForce"){
					alert("POR Date should be greater than Date of Enrollment date");
				}else{	  
	  alert("DOII Date should be greater than Date of Enrollment date");
		        }
	
  		$("#memReson"+k1).removeAttr('readonly');
  		return false;
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
  
  
  
  
  
  
                var dateOfBirth =trim($("#dob").val());
	  		if(new Date(doIIDate).getTime()<=new Date(dateOfBirth).getTime()){
				  
				     if(services=="Navy"){
					alert("GX Date should be greater than Traveler Date Of Birth");
				}else if(services=="AirForce"){
					alert("POR Date should be greater than Traveler Date Of Birth");
				}else{	  
	             alert("DOII Date should be greater than Traveler Date Of Birth");
		           }
				  
		  		
		  		 return false;
	  		}
  
  

  		if(memRelation==4 || memRelation==5 || memRelation==3 || memRelation==2){
  		 var dob=document.getElementById("dob").value;
  		 if(new Date(memdob).getTime()<=new Date(dob).getTime()){
  		 alert("Child Date Of Birth Should Be Less Than Traveler Date Of Birth");
  		 return false;
  		 }
  		 }
  		 if(memRelation==8 || memRelation==9 || memRelation==10 || memRelation==11){
  		 var dob=document.getElementById("dob").value;
  		 
  		  var dob2 = dob.split("/");
          var dateformatdob2 = new Date(dob2[2]+","+(dob2[1])+","+dob2[0]);
          var memberDob  = memdob.split("/");
          var memberFormatDob = new Date(memberDob[2]+","+(memberDob[1])+ ","+memberDob[0]);
          if(memberFormatDob > dateformatdob2){
  		 alert("Father/Mother Date Of Birth Should Be Greater Than Traveler Date Of Birth");
  		 return false;
  		 }
  		
  		}
  		
  		if(memRelation==1) 
 	  			$('#spouseCheck').val('true');
 	  			
  		var question="";
  		//alert("is dependant--"+isDepen);
  		
  		if(isDepen==''){
  		//alert("memreltn>>>>>"+memRelation);
  		if((memRelation==4 || memRelation==5)  && memMaritalStatus==0 )
  		{
  		
  		 $("input[name^=incomeRange1]").prop('checked',false);
  		 $("input[name^=isSept1]").prop('checked',false);
  		 $("input[name^=isDep1]").prop('checked',false);
  		 $("input[name^=isDepAsTR1]").prop('checked',false);
  		// $('#incomeRange1').removeAttr("checked");
//  		 $('#isSept1').removeAttr("checked");
//  		 $('#isDep1').removeAttr("checked");
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
  		 $("input[name^=isPartDep3]").prop('checked',false);;
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
  		
     	//$('.incomeRange2').removeAttr("checked");
// 		 $('.isParAlive2').removeAttr("checked");
//  		 $('.isParsDep2').removeAttr("checked");
//        $('.isResPer2').removeAttr("checked");
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
  		//alert("memreltn>>>>>"+memRelation);
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
	 el.id = 'firstmemberName'+k;
	 el.setAttribute("placeholder","First");
	 el.setAttribute("onkeydown","checkSplChar1(event)")
	 el.setAttribute("onblur","setDepPrintERSName("+k+")")
//	 el.onblur=function setPrintERSForDependents(){alert(k);document.getElementById("ersPrintName1").value=document.getElementById("memberName1").value;};
	 el.size = 10;
	 $(el).attr("autocomplete", "off");
 	 

	 
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(1);
	 cellRight.className = 'testClass';
	 var e2 = document.createElement('input');
	 e2.type = 'text';
	 e2.className = "txtfldauto"; 
	 e2.name = 'middlememberName';
	 e2.id = 'middlememberName'+k;
	 e2.setAttribute("onkeydown","checkSplChar1(event)")
	 e2.setAttribute("placeholder","Middle");
	 e2.setAttribute("onblur","setDepPrintERSName("+k+")")

	 e2.size = 10;
	 $(e2).attr("autocomplete", "off");
 	 
	 
	 cellRight.appendChild(e2); 
	 
	  var cellRight = row.insertCell(2);
	 cellRight.className = 'testClass';
	 var e3 = document.createElement('input');
	 e3.type = 'text';
	 e3.className = "txtfldauto"; 
	 e3.name = 'lastmemberName';
	 e3.id = 'lastmemberName'+k;
	 e3.setAttribute("onkeydown","checkSplChar1(event)")
	 e3.setAttribute("placeholder","Last");
	 e3.setAttribute("onblur","setDepPrintERSName("+k+")")

	 e3.size = 10;
	 $(e3).attr("autocomplete", "off");
	 
	 
	 cellRight.appendChild(e3);
	 
	  var cellRight = row.insertCell(3);	  
	  cellRight.className = 'testClass';	  
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
	  textNode.id = 'memGender'+k; 
	  textNode.className = "comboauto";  
	  textNode.setAttribute("onchange","setRelationAccToGender(this.value,'"+k+"');");
	  cellRight.appendChild(textNode);
	  
	 var cellRight = row.insertCell(4);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'memdob';
	 el.id = 'dob'+k;
	 el.setAttribute("readonly","readonly")
	 el.setAttribute("placeholder","DOB");
	 el.size = 10;
	 $(e1).attr("autocomplete", "off");
	 cellRight.appendChild(el);
	  
	  var cellRight = row.insertCell(5);	  
	  cellRight.className = 'testClass';	  
	  var textNodeDiv=document.createElement('div')
	   textNodeDiv.id="relDiv"+k
	   var textNode = document.createElement('select');
	  textNode.options[0]=new Option("Select","Select"); 
	  var enumRel = document.getElementById('relationFamilyEnum').value; 
	  var relCode; 
	  //alert(enumRel);
	  var spltVals=enumRel.split(',');
	  if(spltVals.length>0){
	  for(var i=0;i<spltVals.length-1;i++)
  		  		{
  		  		var relArr=spltVals[i].split("::");
  		  	     textNode.options[i+1]=new Option(relArr[0],relArr[1]);
  		  	    }
  	  }	  	    
	  textNode.name ='memRelation';
	  textNode.id ='memRelation'+k; 
	  textNode.className = "comboauto";  
	  textNode.setAttribute("onchange","setMarStatAccToRelation(this.value,'"+k+"');");
	  textNodeDiv.appendChild(textNode);
	  cellRight.appendChild(textNodeDiv);
	  
	  var cellRight = row.insertCell(6);	
	  cellRight.className = 'testClass';	
	  var marStatDivNode=document.createElement('div')
	  marStatDivNode.id="marStatDiv"+k  
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
	  textNode.id = 'memMaritalStatus'+k; 
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
	 el.id = 'doIIPartNo'+k;
	 el.setAttribute('onclick',"disableTextBox(" +k +");");
	 el.setAttribute("placeholder","PartNo");
	 el.size = 10;
	 $(el).attr("autocomplete", "off");

	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(8);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.setAttribute("readonly","readonly")
	 el.name = 'doIIDate';
	 el.id = 'doIIDate'+k;
	 el.setAttribute('onclick',"disableTextBox(" +k +");");
	 
	 el.size = 10;
	 $(e1).attr("autocomplete", "off");
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(9);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'memReson';
	 el.id = 'memReson'+k;
	 el.setAttribute('readonly','readonly');  
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(10);
	 cellRight.className = 'testClass';
	 //cellRight.width=55;
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.className = "txtfldauto"; 
	 el.name = 'ersPrintName';
	 el.setAttribute("readonly","readonly")
	 el.size = 14;
	 el.id = 'ersPrintName'+k; 
	 $(e1).attr("autocomplete", "off");
	 cellRight.appendChild(el);
	
	 // Hostel NRS Start
	 
	 var cellRight = row.insertCell(11);
     //cellRight.width=55;
	cellRight.className = 'testClass';
	 var e1 = document.createElement('input');
	 e1.type = 'text';
	 e1.className = "txtfldauto"; 
	 e1.name = 'childHostelNRS';
	 e1.setAttribute("autocomplete","off")
	 e1.size = 10;
	 e1.value = "";
	 e1.setAttribute("onblur","fillChildHostelNRS('','"+k+"');");
	 e1.setAttribute("onkeyup","getRailStationChild(this, '"+k+"');");
	 e1.setAttribute("placeholder","NRS");
	 e1.setAttribute('readonly','readonly');  
	 e1.id = 'childHostelNRS'+k; 
	 $(e1).attr("autocomplete", "off");

	 var e2 = document.createElement('div');
	 e2.appendChild(e1);

	 var e3 = document.createElement('div');
	 e3.className = "suggestionList";
	 e3.id = 'autoSuggestionsListRailStationChild'+k;

	 var e4 = document.createElement('div');
	 e4.className = "suggestionsBox";
	 e4.id = 'suggestionsRailStationChild'+k;
	 e4.style = 'display: none;';
	 e4.appendChild(e3);

	 var e5 = document.createElement('div');
	 e5.appendChild(e2);
	 e5.appendChild(e4);
	
	 cellRight.appendChild(e5);
     
     // Hostel NRS End
     
     // Hostel NAP Start
	 
	 var cellRight = row.insertCell(12);
     //cellRight.width=55;
	cellRight.className = 'testClass';
	 var e1 = document.createElement('input');
	 e1.type = 'text';
	 e1.className = "txtfldauto"; 
	 e1.name = 'childHostelNAP';
	 e1.setAttribute("autocomplete","off")
	 e1.size = 10;
	 e1.value = "";
	 e1.setAttribute("onblur","fillChildHostelNAP('','"+k+"');");
	 e1.setAttribute("onkeyup","getAirportChild(this, '"+k+"');");
	 e1.setAttribute("placeholder","NAP");
	 e1.setAttribute('readonly','readonly');  
	 e1.id = 'childHostelNAP'+k; 
	 $(e1).attr("autocomplete", "off");

	 var e2 = document.createElement('div');
	 e2.appendChild(e1);

	 var e3 = document.createElement('div');
	 e3.className = "suggestionList";
	 e3.id = 'autoSuggestionsListAirportChild'+k;

	 var e4 = document.createElement('div');
	 e4.className = "suggestionsBox";
	 e4.id = 'suggestionsAirportChild'+k;
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
	 el.id = 'isDepen'+k;
	 cellRight.appendChild(el);
	 	  
	 	  
	 	  $("#dob"+k).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		yearStart:1930,
		onShow: function () {
			  $("#dob"+k).val("");
		}
	 
	});

	$("#doIIDate"+k).datetimepicker({
	lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#doIIDate"+k).val("");
		}
	 
	});
//	new Epoch('cal2','popup',document.getElementById('dob'+k),false);
//    new Epoch('cal2','popup',document.getElementById('doIIDate'+k),false);
    k++;
}
   
    function set2Date(k){

     $("#doIIDate"+k).datetimepicker('reset');
}

function disableTextBox(k)
{
 set2Date(k);
if(document.getElementById('memReson'+k)!=null) 
	document.getElementById('memReson'+k).readonly="readonly";

}



/*

function addRow()
   {
   	
   	
   	//new Epoch('cal2','popup',document.getElementById('doIIDate'+i+"'"),false);
    //add a row to the rows collection and get a reference to the newly added row
	var tbl = document.getElementById('tblGrid');	
	//alert(lastRow);
    var newRow = document.all("tblGrid").insertRow();     
    //add 3 cells (<td>) to the new row and set the innerHTML to contain text boxes    
    var oCell = newRow.insertCell();
    oCell.innerHTML = "<input type='text' class='txtfld' name='memberName"+i+"' size='15' id='memberName"+i+"'/>";
    
    oCell = newRow.insertCell();
    oCell.innerHTML = "<select name='memGender"+i+"' class='combo' id='memGender"+i+"'><option value='0'>Male</option><option value='1'>Female</option></select>";
    
    oCell = newRow.insertCell();
    oCell.innerHTML = "<input type='text' class='txtfld' name='dob"+i+"' size='12' id='dob"+i+"' value='DD/MM/YYYY'/>";
    
    oCell = newRow.insertCell();
    oCell.innerHTML = getRealationDtlsStr(i);
    
    oCell = newRow.insertCell();
    oCell.innerHTML = getMaritalStatus(i);
    
    oCell = newRow.insertCell();
    oCell.innerHTML = "<input type='text' class='txtfld' name='doIIPartNo"+i+"' maxlength='7' size='10' id='doIIPartNo"+i+"'/>";
    
    oCell = newRow.insertCell();
    oCell.innerHTML = "<input type='text' class='txtfld' name='doIIDate"+i+"' maxlength='7' size='10' id='doIIDate"+i+"' value='DD/MM/YYYY'/>";
    
    oCell = newRow.insertCell();
    oCell.innerHTML = "<input type='text' class='txtfld' name='memReson"+i+"' maxlength='7' size='10' id='memReson"+i+"'/>";
    
    new Epoch('cal2','popup',document.getElementById('dob'+i),false);
    new Epoch('cal2','popup',document.getElementById('doIIDate'+i),false);
    i++;
   }
   */
   //deletes the specified row from the table

  
 function removeRow()
{
$('#tblGrid tr:last').remove();
		 k--;
}

  /* 
   function removeRow(src)
   {   	
    var oRow = src.parentElement.parentElement; 
	if(i > 2){
    	document.all("tblGrid").deleteRow(i-1);  
    	i--;
	}
   }
 */ 
   function getMaritalStatus(rwIndex){
   	var mairtalStaStr;
   	mairtalStaStr="<select name='memMaritalStatus"+"' class='combo' id='memMaritalStatus"+rwIndex+"'>" +
    		"<option value=''>Select</option>" + 
    		"<repeat ref='MARITAL_STATUS/Enum'/>"+ 
    		"<option value='${EnumCode}'><output ref='EnumValue'/></option>" + 
    		"</repeat>" + 
    		"</select>";
    return mairtalStaStr;
   }
   
   function getRealationDtlsStr(rwIndex){
   	var relStr;
   	relStr="<select name='memRelation"+"' class='combo' id='memRelation"+rwIndex+"'>" +
   			"<option value=''>Select</option>" +
   			"<repeat ref='RELATION_TYPE/Enum'/>"+ 
    		"<option value='${EnumCode}'><output ref='EnumValue'/></option>" + 
    		"</repeat>" + 
   		"</select>";
   		return relStr;
   }
   
   
   
   function chkForPeaceLoc(isPeace){
   	//alert("isPeace - "+isPeace);
   	var isPeaceLoc = $("#isPeaceLoc");
   	var isShowSpr=document.getElementById("isShowSpr");

   	if(!isPeace){
   		$(isPeaceLoc).show();
   	}else{
   		$(isPeaceLoc).hide();
   	}
   }

   function chkForGovtAccom(val){//alert("val - "+val);
   	var sprPlace=document.getElementById("sprPlaceID");
	var sprNrs=document.getElementById("sprNrsPlaceID");
	var sprNa=document.getElementById("sprNa");
	   if(val=='YES'){
	   	sprPlace.disabled=true;
	   	sprNrs.disabled=true;
	   	sprNa.disabled=true;
	   }else{
	   	sprPlace.disabled=false;
	   	sprNrs.disabled=false;
	   	sprNa.disabled=false;
	   }
   }
   
function setAirPayAccountOffice(railPao)
{
	 var categoryId=$('#categoryId :selected').val();
	 var categoryName=$('#categoryId :selected').text();
  
	 var serviceId=$('#serviceId :selected').val();
	 var serviceName=$('#serviceId :selected').text();
	
	 var alternateServiceId=$('#alternateService :selected').val();
	 var alternateServiceName=$('#alternateService :selected').text();
  
	 if(alternateServiceName!="select"){
	 	serviceId=alternateServiceId;
	 	serviceName=alternateServiceName.toUpperCase();
	 }
	 if (serviceName.indexOf('ARMY')>-1) {
		$('#airAcOff').val(railPao); 
	 }
	
}  



 function validateDob(DOB,serviceId,categoryId)
		{
 	var intYear=16;
 	if (serviceId.indexOf('AIRFORCE') > -1 && categoryId.indexOf('PBOR')>-1) {
 		intYear=15;
 	}
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
    	if((currYr-year)>62||(currYr-year)<intYear)
    	{
    	alert("Date of Birth Should Be Between "+intYear+" To 62 Years");
    	document.getElementById("dob").focus();
    	return false;
    	}
    	}
    	}
			return true;
	    }  
   
   
    function validateDobAndDateOfComm(DOB,DOC,SERVICEID,CATEGORYID)
		{
		var intYear=16;
	 	if (SERVICEID.indexOf('AIRFORCE') > -1 && CATEGORYID.indexOf('PBOR')>-1) {
	 		intYear=15;
	 	}
 	
		  var dateofbirth=DOB.split("/");
    	var year=dateofbirth[2];
    	year=parseInt(year);
    	var dateofComm=DOC.split("/");
    	var yearCom=dateofComm[2];
    	yearCom=parseInt(yearCom);
    	if(year+intYear>yearCom)
    	{
    	alert("Date Of Commissioning should be In between Minimum "+intYear+" years & Maximum 62 Years from Date Of Birth");
    	$("#dob").focus();
    	return false;
    	}
	   
    			return true;
	    }
	      
	
   
function checkSplChar(val) {
		value=val.val();
		var flag=true;
   	var alphabetRegEx = /^[A-Za-z ]*$/;
	if(!alphabetRegEx.test(value)){
		alert("Special Characters are not Allowed for Input "+value);
		flag =false;
	}
    	return flag;
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


function checkSplCharIncludingSpace(val) {

	
		value=val.val();
		var flag=true;

 	var alphabetRegEx = /^[A-Za-z ]*$/;
	if(!alphabetRegEx.test(value)){
		alert("Special Characters are not Allowed for Input "+value);
		flag =false;
	}
    	return flag;
	} 
	
   
 function clearText(thefield)
		{
			thefield.select();
		}  


function isGreaterThanTodayDate(ctl) {

// enforce date later than sysdate
//
//
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
   var effDateChar = ctl.value; // P_EFFECTIVE_DATE
   // translate date entered by user into JS format
   var bufArray = effDateChar.split("/");
   var effDateMonth = bufArray[1];
   var effDateDay = bufArray[0];
   var effDateYear = bufArray[2];
   var effDateDate = new Date(effDateMonth+" "+ effDateDay +
                       ", "+effDateYear+" 23:59:59");

   // compare the 2 dates
  // alert("effc date--"+effDateDate+" current date--"+sysDate)
   if (effDateDate > sysDate)
     {
   		alert("Date of Birth should be less than Today's Date");
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

 function closeWindow(divValue,check,headDiv){
     var idx=$('#famIndex').val();
   
     
     
     
     if(check=='no'){
     alert("This Dependant will Not Allow To Do Journey");
     $('#isDepen'+idx).val('0');
         
  	 
     var k1=k-1;
     document.getElementById("tblGrid").deleteRow(k1);
     k--;
     
     }
     if(check=='yes'){
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
     if(headDiv!="" && headDiv!=undefined){
     
     $('#'+headDiv).hide();
     
     }
     var flag=$('#flag').val();
    
     if(flag=="false")
     validateTravellerProfile()
     else
     addRow();
     
         
     
 }
 
 
 
 
 
 function doDateCheckStarting(sdate, edate) {


    if (Date.parse(sdate) <= Date.parse(edate)) {

	return true;
	}
	else {
	return false;
	}
	 return true;

}

function setRelationAccToGender(value,k){
  var marStat=$("input[name='maritalStatus']:checked").val();
  var gender=$("input[name='gender']:checked").val();
  var options="<select id='memRelation"+k+"' class='comboauto' name='memRelation"+"' onchange='setMarStatAccToRelation(this.value,"+k+")'>"
  options+="<option value=''>Select</option>"
      var enumRel = document.getElementById('relationFamilyEnum').value; 
	  var relCode; 
	  //alert(enumRel);
	  var spltVals=enumRel.split(',');
	  if(spltVals.length>0){
	  for(var i=0;i<spltVals.length-1;i++)
  		  		{
  		  		var relArr=spltVals[i].split("::");
  		  		if(value==0 && marStat==0){
  		  		if((relArr[1]==1 && gender==1) ||relArr[1]==2 || relArr[1]==3 || relArr[1]==6 || relArr[1]==9 || relArr[1]==10)
  		  	     options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	     if(value==0 && marStat==1) // male - unmarried
  		  	     {
  		  		  if(relArr[1]==9 ||relArr[1]==10 || relArr[1]==6 || relArr[1] == 2 ) //Adding son as dependent
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	     if(value==1 && marStat==0){
  		  		  if((relArr[1]==1 && gender==0) || relArr[1]==4 || relArr[1]==5 || relArr[1]==7 || relArr[1]==8 || relArr[1]==11)
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	     if(value==1 && marStat==1){ // female - unmarried
  		  		  if(relArr[1]==7 || relArr[1]==8 || relArr[1]==11 || relArr[1] == 4) // adding female son as dependent
  		  	      options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	    }
  	  }	  	
  	  options+='</select>'    
  	  $('#relDiv'+k).html(options);
     
}
 
function setMarStatAccToRelation(value,id)
{
	//alert("setMarStatAccToRelation-value-"+value)
	if(value==2 || value==3 || value==4 || value==5){
		fillChildHostelNAP(id,"");
	    fillChildHostelNRS(id,"");
		$("#childHostelNRS"+id).removeAttr('readonly');
	    $("#childHostelNAP"+id).removeAttr('readonly');
	}else{
		fillChildHostelNAP(id,"");
	    fillChildHostelNRS(id,"");

             $("#childHostelNRS"+id).val('');
		$("#childHostelNRS"+id).attr('readonly','readonly');
	    $("#childHostelNAP"+id).val('');
	    $("#childHostelNAP"+id).attr('readonly','readonly');

	}
	
	var k1=k-1;
	var selectBoxText="<select id='memMaritalStatus"+k1+"' class='comboauto' name='memMaritalStatus"+"'>";
    var defaultOption="<option value=''>Select</option>";
    var options="";
    var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	var optionCount=0;
	var marCode; 
	var spltedVals=enumMar.split(',');
	if(spltedVals.length>0){
		for(var i=0;i<spltedVals.length-1;i++)
  		{
  			var marArr=spltedVals[i].split("::");
  		  		 
  		  	if(value==1 && marArr[1]==0){
  		  		options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  		optionCount++;
  		  	}
  		  	if ((marArr[1]==0 || marArr[1]==2 || marArr[1]==3 || marArr[1]==4) && (value==8 || value==9 || value==10 || value==11 )){
  		  	    options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  	    optionCount++;
  		  	}
  		  	if(value==4 || value==2 || value==3 || value==5 ||  value==6 || value==7){
  		  	    options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
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
	//alert("setMarStatAccToRelation-value-"+value)
      var k1=k-1;
       var options="<select id='memMaritalStatus"+k1+"' class='comboauto' name='memMaritalStatus"+"'>"
       options+="<option value=''>Select</option>"
       var enumMar = document.getElementById('marStatusFamilyEnum').value; 
	   var marCode; 
	   var spltedVals=enumMar.split(',');
	   if(spltedVals.length>0){
	     for(var i=0;i<spltedVals.length-1;i++)
  		  		{
  		  		 var marArr=spltedVals[i].split("::");
  		  		 
  		  		 if(value==1 && marArr[1]==0)
  		  	      options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  	      if ((marArr[1]==0 || marArr[1]==2 || marArr[1]==3 || marArr[1]==4) && (value==8 || value==9 || value==10 || value==11 ))
  		  	      options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  	      if(value==4 || value==2 || value==3 || value==5 ||  value==6 || value==7)
  		  	       options+='<option value="'+marArr[1]+'">'+marArr[0]+'</option>'
  		  	    }
  	      }	  	    
       
       options+='</select>'  
       
        $('#marStatDiv'+k1).html(options);  
       
}

function setRelationFields(value){
       var k1=k-1;
   	   var options="<select id='memRelation"+k1+"' class='comboauto' name='memRelation"+"' onchange='setMarStatAccToRelation(this.value,"+k1+")'>"
       options+="<option value=''>Select</option>";
      var enumRel = document.getElementById('relationFamilyEnum').value; 
	  var relCode; 
	  //alert(enumRel);
	  var spltVals=enumRel.split(',');
	  if(spltVals.length>0){
	  for(var i=0;i<spltVals.length-1;i++)
  		  		{
  		  		var relArr=spltVals[i].split("::");
  		  		if(value==1){
  		  		if(relArr[1]==6 ||relArr[1]==7 || relArr[1]==8 || relArr[1]==9 || relArr[1]==10 || relArr[1]==11)
  		  	     options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     }
  		  	     else
  		  	     options+='<option value="'+relArr[1]+'">'+relArr[0]+'</option>'
  		  	     
  		  	    }
  	  }	  	
  	  options+='</select>'    
  	  $('#relDiv'+k1).html(options);
  	  
  	  var memGender = $("#saveArmyTravelerForm select[name=memGender]");
  	  if (memGender.length > 0) {
			for (var i = 0; i < memGender.length; i++) {
				memGender[i].value = 'Select';
				
			}
		}
   }

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


function setDoIINo(){

	 var serviceId=$('#serviceId :selected').text().toUpperCase();
	 var alternateService=$('#alternateService :selected').text();
		
	 if(alternateService!="select" || alternateService!=""){
	 serviceId=alternateService.toUpperCase();
	}
	 if (serviceId.indexOf('AIRFORCE') > -1 || serviceId.indexOf('AIR FORCE') > -1) {
	  $("#DoIINo").html('POR NO');
	  $("#DoIIDate").html('POR Date');
	 }
	 else if (serviceId.indexOf('NAVY')>-1) {
	  $("#DoIINo").html('GX NO');
	  $("#DoIIDate").html('GX Date');
	 }
 	 else{
 	  $("#DoIINo").html('DOII Part NO');
	  $("#DoIIDate").html('DOII Date');
 	 }
}

function setPaoOffice(){

     var categoryId=$('#categoryId :selected').val();
	 var categoryName=$('#categoryId :selected').text();
	
	 var serviceId=$('#serviceId :selected').val();
	 var serviceName=$('#serviceId :selected').text();
	
	 var alternateServiceId=$('#alternateService :selected').val();
	 var alternateServiceName=$('#alternateService :selected').text();
	
	 var paoOffId=$('#payAcOff');
	 var airOffId=$('#airAcOff');
	 
	 if(alternateServiceName!="select"){
	 	serviceId=alternateServiceId;
	 	serviceName=alternateServiceName.toUpperCase();
	 }
	 	
	
	 var rail_options="";var air_options="";
	

	 var tempChkForCategory=categoryName.toUpperCase();

        //alert("ServiceId="+serviceId+"||serviceName="+serviceName+"alternateServiceId="+alternateServiceId+"||alternateServiceName="+alternateServiceName+"categoryId="+categoryId+"||categoryName="+categoryName+"|tempChkForCategory-"+tempChkForCategory); 
	 
	 if (serviceName.indexOf('AIRFORCE') > -1 || serviceName.indexOf('AIR FORCE') > -1) 
	 {
	   	$("#DoIINo").html('POR NO');
	  	$("#DoIIDate").html('POR Date');
	 }
	 if (serviceName.indexOf('NAVY')>-1) 
	 {
	 	$("#DoIINo").html('GX NO');
	  	$("#DoIIDate").html('GX Date');
	 }
 
	 if(tempChkForCategory=='OFFICER' || tempChkForCategory=='NCC WTLO')
	 {
		$("#isDateOfCommission").html('Date of Commissioning <span class="mandatory">*</span>');
		
		if(serviceName.indexOf('MNS')>-1){
			$("#isFormD").html('Form G already used this year <span class="mandatory">*</span>');
		}else{
		$("#isFormD").html('Form D already used this year <span class="mandatory">*</span>');
		}
		
	 
		if (serviceName.indexOf('ARMY')>-1 || serviceName.indexOf('MNS')>-1) {
			$("#cdaAccNo").show(); 
		}
		else{
			$("#cdaAccNo").hide(); 
		}
	 }else{
		$("#isDateOfCommission").html('Date of Enrollment <span class="mandatory">*</span>');
		$("#isFormD").html('Concession Voucher Used <span class="mandatory">*</span>');
		$("#cdaAccNo").hide(); 
	 }
	
	//Check for Airforce	 
	$(paoOffId).find('option').not(':first').remove();
	$(airOffId).find('option').not(':first').remove();

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

    $.each(obj, function (i, v) {
					if (i == 0) {
						railCount = $(v).length;
						RailVisitorGroup = $(v);
					}
					if (i == 1) {
						airCount = $(v).length;
						AirVisitorGroup = $(v);
					}
				});

    if (railCount != 1) {
					rail_options += '<option value="-1">select<\/option>';
  				}

    $(RailVisitorGroup).each(function (index) {
					var id = RailVisitorGroup[index].acuntoficeId;
					var name = RailVisitorGroup[index].name;

        rail_options += '<option value="' + id + '">' + name + '<\/option>';
		 		});

    if (airCount != 1) {
		 			air_options += '<option value="-1">select<\/option>';
		 		}

    $(AirVisitorGroup).each(function (index) {
					var id = AirVisitorGroup[index].acuntoficeId;
					var name = AirVisitorGroup[index].name;

        air_options += '<option value="' + id + '">' + name + '<\/option>';
		 		});

		 		$("#payAcOff").html(rail_options);
		  		$("#airAcOff").html(air_options);
			}//END SUCCESS
	    });
	}

}

function getBrowserInfo()
{
	var bName='';
	var useragent = navigator.userAgent;
	bName = (useragent.indexOf('Opera') > -1) ? 'Opera' : navigator.appName;
	return bName;
}

function testForCharacterKey(e) { // KEYPRESS event
	var validatePersonalNo = $("#validatePersonalNo");

	var myBrow=getBrowserInfo();

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


function testForAlphanumericKey(e) { // KEYPRESS event
	var validatePersonalNo = $("#validatePersonalNo");

	if (validatePersonalNo != null) {
		$(validatePersonalNo).val("");
	}

	var myBrow=getBrowserInfo();

	if(myBrow.indexOf('Netscape')> -1)
	{
		
		 var k = e.which;
		 var ok = k >= 65 && k <= 90 ||  k >= 97 && k <= 122 || k == 8 || k == 0 || (!e.shiftKey && k >= 48 && k <= 57);
        
	    if (!ok){
	    	alert("Please Enter Only Alpha Numeric");
	        e.preventDefault();
	    }else{
	    	return true;
	    }
	 } else{
		return true;	
	}
}

function validateDateOfCommAndDateOfRetire(DOC, DOR) {
	var dateOfRetire = DOR.split("/");
	var dateOfRetirement = new Date(dateOfRetire[2], dateOfRetire[0] - 1, dateOfRetire[1]);
	var dateOfComm = DOC.split("/");
	var dateOfCommission = new Date(dateOfComm[2], dateOfComm[0] - 1, dateOfComm[1]);
	var yearRetire = dateOfRetire[2];
	yearRetire = parseInt(yearRetire);
	var yearCom = dateOfComm[2];
	yearCom = parseInt(yearCom);
	
	if (yearCom > yearRetire) {
    	alert("Year Of Retirement should be greater than Year Of Commissioning");
		document.saveTraveller.dateOfRetirement.focus();
		return false;
	}

	if (dateOfCommission >= dateOfRetirement) {
		alert("Invalid sequence: Date of Commissioning should be prior to Date of Retirement");
    	return false;
    	}

   return true;
}





function validateDateOfRetirement(DOR){
	
	    var dateOfRetire=calculateRetirementDate();
	   	var retireDate=dateOfRetire.split("/");
	   	var month=parseInt(retireDate[1])-1;
	   	if(month.length ==1){
	   		month="0"+month
	   	}
	   	var RetirementDate = new Date(retireDate[2],month,retireDate[0]);
	   	
        var inputDOR=DOR.split("/");
        var dorMonth=parseInt(inputDOR[1])-1;
	   	if(dorMonth.length ==1){
	   		dorMonth="0"+dorMonth
	   	}
		var inputDate = new Date(inputDOR[2],dorMonth,inputDOR[0]);
    	
    	
    	if(inputDate>RetirementDate){
    	alert("Date Of Retirement should not be greater than - "+dateOfRetire);
    	$("#dateOfRetirement").focus();
    	return false;
    	}
	   
    return true;
}


function validateDateOfRetirementOnEdit(DOR){
	
	    var dateOfRetire=calculateEditRetirementDate();
	   	var retireDate=dateOfRetire.split("/");
	   	var month=parseInt(retireDate[1])-1;
	   	if(month.length ==1){
	   		month="0"+month
	   	}
	   	var RetirementDate = new Date(retireDate[2],month,retireDate[0]);
	   	
        var inputDOR=DOR.split("/");
        var dorMonth=parseInt(inputDOR[1])-1;
	   	if(dorMonth.length ==1){
	   		dorMonth="0"+dorMonth
	   	}
		var inputDate = new Date(inputDOR[2],dorMonth,inputDOR[0]);
    	
    	
    	if(inputDate>RetirementDate){
    	alert("Date Of Retirement should not be greater than - "+dateOfRetire);
    	$("#dateOfRtirement").focus();
    	return false;
    	}
	   
    return true;
}



function validateDateOfCommAndDateOfRetireOnEdit(DOC, DOR) {
	var dateOfRetire = DOR.split("/");
	var dateOfRetirement = new Date(dateOfRetire[2], dateOfRetire[0] - 1, dateOfRetire[1]);
	var dateOfComm = DOC.split("/");
	var dateOfCommission = new Date(dateOfComm[2], dateOfComm[0] - 1, dateOfComm[1]);
	var yearRetire = dateOfRetire[2];
	yearRetire = parseInt(yearRetire);
	var yearCom = dateOfComm[2];
	yearCom = parseInt(yearCom);
	
	if (yearCom > yearRetire) {
    	alert("Year Of Retirement should be greater than Year Of Commissioning");
		document.saveTraveller.dateOfRetirement.focus();
		return false;
	}

	if (dateOfCommission >= dateOfRetirement) {
		alert("Invalid sequence: Date of Commissioning should be prior to Date of Retirement");
    	return false;
    	}

   return true;
}






var allUserId;
function selectAllPnoForMove(){
allUserId='';
$('#unitMoveUserIdStr').val('');
var selectAll=$('#selectAll').prop("checked");
$('input:checkbox').prop('checked', $('#selectAll').prop("checked"));

$("input:checkbox:checked").each(function () 
{
   var str = $(this).attr("id");
   if(str.startsWith('user')){
	   str = str.split("_");
	   var index=str[1];
	   var valu=$('#userId_'+index).val();
	   allUserId=allUserId+valu+"##";
   }
   
});
$('#unitMoveUserIdStr').val(allUserId);
  	//alert(allUserId);
 }
 
 function selectPnoForMove()
{
allUserId='';
$('#unitMoveUserIdStr').val('');
$("input:checkbox:checked").each(function () 
{
   var str = $(this).attr("id");
   if(str.startsWith('user')){
	   str = str.split("_");
	   var index=str[1];
	   var valu=$('#userId_'+index).val();
	   allUserId=allUserId+valu+"##";
   }
   
});
$('#selectAll').prop('checked', true);

$("input:checkbox").each(function () 
{
   var str = $(this).attr("id");
   if(str.startsWith('user')){
	var falg=$('#'+str).is(":checked")   
	if(!falg){
		$('#selectAll').prop('checked', false);
	}

   }
   
});

  	$('#unitMoveUserIdStr').val(allUserId);
 
	
}
 
 
 
