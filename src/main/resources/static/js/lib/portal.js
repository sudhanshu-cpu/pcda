$(document).ready(function() {

	document.oncontextmenu = function() { return false; };
	$(document).mousedown(function(e) {
		if (e.button == 2) {
			alert('Right click is not allowed!');
			return false;
		}
		return true;
	});

});
