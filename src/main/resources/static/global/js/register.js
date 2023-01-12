	$(document).ready(function () {
  		
  	  $("#register_btn").click(function() {  
		if($("#firstName").val()!="" && $("#lastName").val()!="" &&  $("#email").val()!=""
			&& $("#register_password").val()!=""){
	     		register_password();
	     }
	     else{
		alert("Lütfen Gerekli Bilgileri Giriniz");
	}
      });
      
  
  	function register_password() {
  		var search = {}
  	    search["firstName"] = $("#firstName").val();
  		search["lastName"] = $("#lastName").val();
		search["role"] = $("#register_role").val();
  		search["email"] = $("#email").val();
  		search["password"] = $("#register_password").val();
  		sendAjax(search);
  	}
  	 $("#txtPassword").click(function () {
		if(document.getElementById("register_password").type=="password"){
			$('#register_password').attr("type","text");
		}else{
			$('#register_password').attr("type","password");
		}
		
	})
	  	
  	function sendAjax(jsonreq){ 
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/register",
	        data: JSON.stringify(jsonreq),
	        cache: false,
	        timeout: 600000, 
	        success: function (data) {
	        	if(data!=null){
	        		if(data=="Success"){	
	        				 $('#result').css("display","");
		       				 $('#result').css("color","green");
			                 $('#result').text("Kayıt Başarılı.Giriş Sayfasına Yönlendiriliyorsunuz...");
			                 var millisecondsToWait = 2000;
			                 setTimeout(function() {
			                     window.location.replace("../login.html");
			                 }, millisecondsToWait);
	        		}
					else if(data=="regexCntrl"){
						 $('#result').css("display","");
			   			 $('#result').css("color","red");
			   			 $('#result').css("font-size","small");
			             $('#result').text("Şifre:8-16 karakter uzunluğunda olmalıdır.En az bir büyük harf,kücük harf,sayı ve özel karakter içermelidir.");
					}
	        		else{
	        			 $('#result').css("display","");
	       				 $('#result').css("color","red");
		                 $('#result').text("Kayıt Başarısız...");
	        		}
	        	}     	
	        },
	        error: function (e) {
	        	 $('#result').css("display","");
   				 $('#result').css("color","red");
                 $('#result').text("Kayit Basarisiz");
            }
	    });
  	}
  });