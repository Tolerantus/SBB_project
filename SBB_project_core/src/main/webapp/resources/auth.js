var correctData = false;
	
		$(document).ready(function(){
			$('#username').keyup(function(){
				
				var username = $('#username');
				var login_status = $('#login-status');
				username.removeClass('error-box');
				login_status.addClass('error-mes');
				var data = $('#username').val();
				var patt = new RegExp("^[_A-Za-z0-9-]+(\\." +
						"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
						"(\\.[A-Za-z]{2,3})$");
				
				
					if (!patt.test(data)) {
						username.addClass('error-box');
						login_status.text("invalid e-mail address, it should be like user@user.org");
						login_status.slideDown('slow');
						correctData = false;
					} else
						{
							login_status.slideUp('slow');
							correctData = true;
						}
			});
		
			$('#password').keyup(function(){
				var password = $('#password');
				var password_status = $('#password-status');
				password.removeClass('error-box');
				password_status.addClass('error-mes');
				var data = $('#password').val();
				var patt = new RegExp("^[A-Za-z0-9_]{4,15}$");
				
				if (data.length < 4||data.length > 15) {
					password.addClass('error-box');
					password_status.text("password should be longer than 3 and shorter than 16 symbols");
					password_status.slideDown('slow');
					correctData = false;
				} else
					if (!patt.test(data)) {
						password.addClass('error-box');
						password_status.text("password contains invalid symbols");
						password_status.slideDown('slow');
						correctData = false;
					} else {
						password_status.slideUp('slow');
						correctData = true;
					}
			});
			$('#password2').keyup(function(){
				
				var pas1 = $('#password');
				var pas2 =  $('#password2');
				var pas2_status = $('#password-status2');
				pas2.removeClass('error-box');
				pas2_status.addClass('error-mes');
				if (pas2.val() != null && pas1.val() != pas2.val()) {
					pas2.addClass('error-box');
					pas2_status.text("passwords should match");
					pas2_status.slideDown('slow');
				} else {
					pas2_status.slideUp('slow');
				}
			});
		});
	