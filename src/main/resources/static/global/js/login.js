 	$(document).ready(function () {
  		var email;
  		var login_pass;
  	  $("#login_button").click(function () {
		 email=$("#email").val();
		 login_pass=$("#login_password").val();
         $('#login_button').attr("disabled",true);
         if(email!=null && email.length>0 && login_pass!=null && login_pass.length>0){
			 login_submit();
		 }
		 else{alert("Zorunlu Alanları Doldurunuz");}
                
      });
  	
  	function login_submit() {
  		var search = {}
  	    search["username"] = email;
  		search["password"] = login_pass;		
  		sendAjax(search);
  	} 
  	function sendAjax(jsonreq){ 
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/login",
	        data: JSON.stringify(jsonreq),
	        cache: false,
	        timeout: 600000,	      
	        success: function (data) {
	        	if(data.token!=null && data.token.length>0){
	        		localStorage.setItem('token', JSON.stringify(data.token)); 
        			 $('#login_button').attr("disabled",false);
   			 		 $('#result').css("display","");
   				     $('#result').css("color","green");
                   	 $('#result').text("Success.Yönlendiriliyorsunuz...");
                     window.location.replace("../dashboard.html");
	        	}
	        	else{
	        			 $('#result').css("display","");
	       				 $('#result').css("color","red");
		                 $('#result').text("Kullanıcı Adı ve ya Şifre Yanlış");
	        		}
	        	 $('#login_button').attr("disabled",false);
	        },
	        error: function (e) {
                $('#login_button').attr("disabled",false);
                $('#result').css("display","");
                $('#result').css("color","red");
                $('#result').text("Kullanıcı Adı ve ya Şifre Yanlış");
            }
	    });
  	}  	
  });