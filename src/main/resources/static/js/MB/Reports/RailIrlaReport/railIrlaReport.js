function doGenerate(event){
         var prsnlNo = $("#personalNumber").val().trim();
         var pnrNo = $("#pnrNo").val();
         
        if(prsnlNo == '' && pnrNo == ''){
         alert("Please enter at least one search parameter");
        preventDefault();
        }else {

	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNumber").val(encryptPNo);
	}

            $("#irlaReport").submit();
           }
        }