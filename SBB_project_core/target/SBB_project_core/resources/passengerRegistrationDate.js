/**
 * 
 */
function getDays(){
	var year = $('#year').val();
	var month = $('#month').val();
	if (month==1){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	
	if (month==2){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=28; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
		if (year%4==0){$('#day').append($("<option>"+29+"</option>"))}
	}
	if (month==3){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==4){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=30; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==5){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==6){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=30; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==7){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==8){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==9){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=30; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==10){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==11){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=30; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	if (month==12){
		$("#day").empty();
		$('#day').append($("<option disabled>day</option>"))
		for (i=1; i<=31; i++){
		$('#day').append($("<option>"+i+"</option>"));
		}
	}
	
	
	

}
$('#year').change(getDays);
$('#month').change(getDays);
getDays();