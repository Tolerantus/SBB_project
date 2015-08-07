/**
 * 
 */

	var listSize = $('sessionScope.newRoute').length;
	$('#steps').append("<option disabled>step</option>")
	for (i=0;i<listSize;i++){
		$('#steps').append("<option>"+i+"</option>");
	}
