
 
function fileToUpload(actionVal)
{
	var fileName = document.getElementById('userVerificationFileToUpload').value;
	var extName = fileName.substring(fileName.lastIndexOf('.') + 1);
	
	if(actionVal == 'fileToUpload')
	{
		if(extName != "csv")
		{
		  alert("Upload CSV file only");
		  return false;
		}
		else
		{
		 	document.userVerificationFileUploaad.submit();	
			return true;
		}
	}
	else{
		return false;
	}
	
}
function doSelectedAction() {
	$("#verifiedFile").submit();
		return true;

}

function  doBack() {
	document.userVerificationFileUploaad.submit();
	return true;
  
  }
