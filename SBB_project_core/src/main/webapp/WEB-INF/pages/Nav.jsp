<div class="logo">
	<img alt="" src="/SBB_project_core/resources/images/Sbb.png">
	<a href="/SBB_project_core/">Swiss Federal Railways</a>
</div>
<div class="drop">
	<ul class="drop_menu">
		<li><a href='/SBB_project_core/schedule'>Schedule</a></li>
		<li><a href='/SBB_project_core/stationsChoosing'>Buy ticket</a></li>
		<li><a href='/SBB_project_core/myTickets'>My tickets</a></li>
		<sec:authorize access="hasRole('ADMIN')">
			<li><a href='/SBB_project_core/'>Create ... </a>
				<ul>
					<li><a href='/SBB_project_core/creatingTrain'>Create train</a></li>
					<li><a href='/SBB_project_core/creatingTrain'>Create station</a></li>
					<li><a href='/SBB_project_core/newRoute'>Create route</a></li>
					<li><a href='/SBB_project_core/routesInfo'>Create journey</a></li>
				</ul>
		        </li>
		        <li><a href='/SBB_project_core/'>Database ...</a>
				<ul>
					<li><a href='/SBB_project_core/resetDB'>Reset database</a></li>
					<li><a href='/SBB_project_core/initDB'>Init database</a></li>
				</ul>
			</li>
			<li><a href='/SBB_project_core/journeyList'>Passengers</a></li>
			<li><a href='/SBB_project_core/manager'>Manager</a></li>
		</sec:authorize>
		<li id="user">${pageContext.request.userPrincipal.name} | <a  id="balance">Balance :<span id="cash">0</span></a> | <a href='/SBB_project_core/logout'>LogOut</a></li>        
	</ul> 
</div>
  <script>
  $(function() {
    var dialog, form,
 	numberRegex = new RegExp("^[1-9][0-9]{0,5}(\.[0-9]{1,2})?$"),
      // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
      money = $( "#money" ),
      allFields = $( [] ).add( money ),
      tips = $( ".validateTips" );
 
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
 
    function checkRegexp( o, regexp, n ) {
      if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
      } else {
        return true;
      }
    }
 
    function putMoney() {
      var valid = true;
      allFields.removeClass( "ui-state-error" ); 
      valid = valid && checkRegexp( money, numberRegex, "Value be be more 1.00 less than 999999.99" );
 
      if ( valid ) {
    	  $.ajax({
    		  url:"/SBB_project_core/putMoney",
    		  type:"post",
    		  data:{money:money.val(),
    			  "${_csrf.parameterName}" : "${_csrf.token}"
    			  },
    			  
    		  success:function(resp){
    			  checkCash();
    		  }
    	  });
        dialog.dialog( "close" );
      }
      return valid;
    }
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 180,
      width: 340,
      modal: true,
      buttons: {
        "Put money": putMoney,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      addUser();
    });
 
    $( "#balance" ).on( "click", function() {
      dialog.dialog( "open" );
    });
  });
  </script>
</head>
<body>
 
<div id="dialog-form" title="Put money into your account">
  <p class="validateTips">How much money do you want to put?</p>
 
  <form>
      <input type="text" name="money" id="money" value="5000" class="text ui-widget-content ui-corner-all">
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  </form>
</div>



