
function isSpecialChar(element)
{
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
	for (var i = 0; i < element.value.length; i++) 
	{
    	if (iChars.indexOf(element.value.charAt(i)) != -1) 
    	{
		    alert ("Special characters are not allowed.");
    		element.value='';
  			element.focus();
  			return false;
        }
    }
}
 
function checkNo(element,iChars)
{
	 for (var i=element.value.length-1; i >=0; i--) 
	 {
	 	if (iChars.indexOf(element.value.charAt(i)) == -1)
		{ 
  				alert("Only numeric characters are allowed.");
  				element.value='';
  				element.focus();
  				return false;
	 	}	
	 }
} 

function checkNoneChar(element)
{
	var flag=true;
	var length=element.value.length;
	for (var j = 0; j < length; j++)
	{
		var ch = element.value.substring(j, j + 1);
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

function checkemail(str)
{
 
	 var filter=/^.+@.+\..{2,3}$/
 	 if (filter.test(str))
 	 {
    	return true
 	 }	
	 else 
	 {
	    alert("Please enter a valid email address!")
    	return false
	 }

}


function trim(n)
{
   		return n.replace(/^\s+|\s+$/g,'');
}
	
function isEmpty(value)
{
		return value==null||""==value;
}
	
function isValidPhoneNumber(element)
{

	var iChars = "-0123456789";
		
	if(element.value!="")
	{

		for (var i = 0; i < element.value.length; i++) 
		{
			if (iChars.indexOf(element.value.charAt(i)) == -1)
			{
				return false;
     		}
     	}
	}
	
	return true;
}
//TADA_CLAIM_REPORT starts
function isNumberKey(evt){
     var charCode = (evt.which) ? evt.which : event.keyCode
	  if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
	  	 	return false;
	  }
     else{
     	 	return true;
   	 }
}

function isNumericOnlyKey(evt){
     var charCode = (evt.which) ? evt.which : event.keyCode
	  if (String.fromCharCode(charCode).match(/[^0-9]/g)){
	  	 	return false;
	  }
     else{
     	 	return true;
   	 }
}
//TADA_CLAIM_REPORT ends

