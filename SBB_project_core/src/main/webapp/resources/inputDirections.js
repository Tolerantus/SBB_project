/**
 * 
 */
function validate(){
	var route = $('#route').val();
	
	var err = false;
	var patt = new RegExp("^[A-Za-z0-9-\s]{4,40}$");
	var costPatt = new RegExp("^[0-9]{1,10}(\\.\\d\\d)?$")
	$('#route').removeClass('error-box');
	$('#status').removeClass('error-mes');
	$('#status').empty();
	$('[name$=cost]').each(function(){
		$(this).removeClass('error-box');
		$(this.cost-status).removeClass('error-mes');
		$(this.cost-status).empty();
		$(this).parent().parent().find('.cost-status').empty();
	});
	$('tr').each(function(){
		$(this).find('[name$=hours]').removeClass('error-box');
		$(this).find('[name$=minutes]').removeClass('error-box');
		
	})
	if (!patt.test(route)){
		err = true;
		$('#route').addClass('error-box');
		$('#status').addClass('error-mes');
		$('#status').text('invalid route name');
	}
	$('[name$=cost]').each(function(){
		var cost = $(this).val();
		if (!costPatt.test(cost)){
			$(this).addClass('error-box');
			$(this).parent().parent().find('.cost-status').addClass('error-mes');
			$(this).parent().parent().find('.cost-status').text('invalid cost (should be ###...##.##)');
			err=true;
		}
	});
	$('tr').each(function(){
		var hours = $(this).find('[name$=hours]').val();
		var minutes = $(this).find('[name$=minutes]').val();
		if (hours==0&&minutes==0){
			$(this).find('[name$=hours]').addClass('error-box');
			$(this).find('[name$=minutes]').addClass('error-box');
			
			err = true;
		}
	})
	
	if (!err){
		$('#build').submit();
	}
	
}