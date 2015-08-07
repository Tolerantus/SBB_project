/**
 * 
 */
function validate(){
	var time = $('#time').val();
	var date = $('#date').val();
	var err = false;
	$('#time').removeClass('error-box');
	
	$('#date').removeClass('error-box');
	
	if (time==""){
		$('#time').addClass('error-box');
		
		err = true;
	}
	if (date==""){
		$('#date').addClass('error-box');
		
		err = true;
	}
	if (!err){
		$('#new').submit();
	}
}