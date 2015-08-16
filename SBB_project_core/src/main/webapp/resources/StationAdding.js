/**
 * 
 */
function validate(){
	var text = $('#typed').val();
	var patt = new RegExp("^([A-Z])([a-z\\s]{1,20})[-]?[A-Z]?[a-z\\s]{0,19}[a-z0-9]$");
	var err = false;
	var isAnyStations = $('#stations').length?true:false;
	$('#status').removeClass('error-mes');
	$('#status').empty();
	$('#typed').removeClass('error-box');
	if (text==""&&!isAnyStations){
		err=true;
		$('#status').addClass('error-mes');
		$('#status').text('invalid station name');
		$('#typed').addClass('error-box');
	}
	if (text!=""&&!patt.test(text)){
		err=true;
		$('#status').addClass('error-mes');
		$('#status').text('invalid station name');
		$('#typed').addClass('error-box');
	}
	if (!err){
		$('#addForm').submit();
	}
}