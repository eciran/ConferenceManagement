$(document).ready(function () {
			var url=window.location.pathname;
			var search=window.location.search;
			url=url+search;
			$("#btnGuncelle").click(function(){
				$('#result').css("display","none");
				$('#btnGuncelle').attr("disabled",true);
				if(($("#newPassword").val())==($("#reNewPassword").val())){
					updatePassword_submit();
				}
				else{
					 $('#result').css("display","");
		   			 $('#result').css("color","red");
		             $('#result').text("Şifreler uyuşmamaktadır");
		             $('#btnGuncelle').attr("disabled",false);
				}
				
			});
			
	  	  	function updatePassword_submit() {
	  		var search = {}
	  		search["newPassword"] = $("#newPassword").val();
	  		search["reNewPassword"] = $("#reNewPassword").val();
	  		sendAjax(search);
	  	}
	  	function sendAjax(jsonreq){ 
		    $.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: url,
		        data: JSON.stringify(jsonreq),
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
					 $('#btnGuncelle').attr("disabled",false);
		        	if(data!=null){
		        		if(data.includes("Succesfully")){
	        				 Swal.fire({
	        				 	icon:'success',
	        			        title: "Başarılı",
	        			        text:"Şifre değişikliği başarıyla gerçekleşti",
	        			        position: "center",
	        			        timer:2000,
	        			        showConfirmButton: !1,
	        			    });
							  var millisecondsToWait =2000;
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
		        			  Swal.fire({
	        				 	icon:'error',
	        			        title: "Başarısız",
	        			        text:"Şifre değişikliği başarısız oldu",
	        			        position: "center",
	        			        timer:2000,
	        			        showConfirmButton: !1,
	        			    });
		        		}
		        	}
		        },
		        error: function (e) {
	                var json = "Ajax Hata : "
	                    + e.responseText;
	                alert(json);
	            }
		    });
	  	}
	  });