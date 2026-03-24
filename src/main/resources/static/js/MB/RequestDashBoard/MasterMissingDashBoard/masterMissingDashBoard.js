	
	function submitRequest(personalNo) {
	
		var comments = $("#comments").val();
		if (comments.length == 0) {
		alert("Comments can not be blank.");
			return false;
		} else {
			$("#personal_No").val(personalNo);
			$("#comments").val(comments);
			$("#commentForm").submit();
  }



	}


	



