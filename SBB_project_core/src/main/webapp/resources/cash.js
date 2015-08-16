function checkCash(){
		$.ajax({
			url:"/SBB_project_core/cash",
			type:"get",
			
			success:function(cash){
				$('#cash').text(cash);
			}
		});
	}