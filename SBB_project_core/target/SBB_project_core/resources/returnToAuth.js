/**
 * 
 */
function checkUser(){var user = "${sessionScope.user}";
	if (user==null){
		$('#returnToAuth').submit();
	}
}
