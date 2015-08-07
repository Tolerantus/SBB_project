/**
 * 
 */
function validate(){
	var st_dep = $('#st_dep').val();
	var st_arr = $('#st_arr').val();
	var patt = new RegExp("^[A-Za-z_-]{1,20}$");
	var err = false;
	var isAnyStations = $('#old_st_dep').length?true:false;
	$('#st_dep').removeClass('error-box');
	$('#depStat').removeClass('error-mes');
	$('#depStat').empty();
	$('#st_arr').removeClass('error-box');
	$('#arrStat').removeClass('error-mes');
	$('#arrStat').empty();
	$('#old_st_dep').removeClass('error-box');
	$('#old_st_arr').removeClass('error-box');
	$('#selDep').removeClass('error-mes');
	$('#selDep').empty();
	
		if (st_dep!=""&&!patt.test(st_dep)){
			err = true;
			$('#st_dep').addClass('error-box');
			$('#depStat').addClass('error-mes');
			$('#depStat').text('invalid station name');
		}
		if (st_arr!=""&&!patt.test(st_arr)){
			err = true;
			$('#st_arr').addClass('error-box');
			$('#arrStat').addClass('error-mes');
			$('#arrStat').text('invalid station name');
		}
		
		if (st_arr==""&&!isAnyStations){
			$('#st_arr').addClass('error-box');
			$('#arrStat').addClass('error-mes');
			$('#arrStat').text('invalid station name');
			err=true;
		}
		if (st_dep==""&&!isAnyStations){
			err = true;
			$('#st_dep').addClass('error-box');
			$('#depStat').addClass('error-mes');
			$('#depStat').text('invalid station name');
		}
		if (st_arr==""&&st_dep==""&&$('#old_st_dep').val()==$('#old_st_arr').val()){
			$('#old_st_dep').addClass('error-box');
			$('#old_st_arr').addClass('error-box');
			$('#selDep').addClass('error-mes');
			$('#selDep').text('start and finish must be different');
			err=true;
		}
	
	if (!err){
		$('#creator').submit();
	}
}