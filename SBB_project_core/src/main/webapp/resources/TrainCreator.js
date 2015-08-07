/**
 * 
 */
function validate(){
	var name = $('input').val();
	var patt = new RegExp("^[A-Za-z0-9_\\s]{4,15}$");
	var err = false;
	$('#name').removeClass('error-box');
	$('#status').removeClass('error-mes');
	$('#status').empty();
	if (!patt.test(name)){
		$('#name').addClass('error-box');
		$('#status').addClass('error-mes');
		$('#status').text('invalid train name');
		err=true;
	}
	if (!err){
		$('[name=creator]').submit();
	}
}