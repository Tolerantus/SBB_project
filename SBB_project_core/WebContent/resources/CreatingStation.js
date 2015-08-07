/**
 * 
 */
function validate(){
	var name = $('#station').val();
	var patt = new RegExp("^[A-Za-z0-9\-\\s]{3,25}$");
	var err = false;
	$('#station').removeClass('error-box');
	$('#status').removeClass('error-mes');
	$('#status').empty();
	if (!patt.test(name)){
		$('#station').addClass('error-box');
		$('#status').addClass('error-mes');
		$('#status').text('Invalid station name');
		err=true;
	}
	if(!err){
		$('#creator').submit();
	}
}