$(document).ready(function(){
	var token = localStorage.token;
	if(token==null){
		window.location.replace("../login.html");
	}else{
		getUserInfo();
	}
	$("#password_save").click(function(){
		$("#result").css("display", "none");
		if($('#newPassword').val()==$('#reNewPassword').val()){
			var passChange={}
			passChange["email"]=$user;
			passChange["oldPassword"]=$('#oldPassword').val();
			passChange["newPassword"]=$('#newPassword').val();
			sendAjaxUserPasswordChange(passChange);
		}
		else{
		  $("#result").css("display", "");
	      $("#result").css("color", "red");
	      $("#result").css("font-size", "medium");
		  $("#result").text("Şifreler Uyuşmamaktadır.");
		}
	});
	
	$("#profile_save").click(function(){
			if($('#uyelik_phone').val().length>9){
				$("#result").css("display", "none");			
				var profilChange={}
				profilChange["email"]=$user;
				profilChange["firstName"]=$('#uyelik_isim').val();
				profilChange["lastName"]=$('#uyelik_soyad').val();
				profilChange["phoneNumber"]=$('#uyelik_phone').val();
				sendAjaxUserChange(profilChange);
			}
			else{
			 $('#result1').css("display","");
			 $('#result1').css("color","red");
			 $('#result1').css("font-size","small");
	         $('#result1').text("Telefon numarası başında '0' olmadan 10 haneli olarak giriniz");
			}
		});
		
	});

	 
	 $("#toApplyBtn").click(function(){
	 	 var title=$("#evaluation_title").val();
		 var uni=$("#evaluation_uni").val();
		 var key=$("#evaluation_key").val();
		 var upfiles=$("#btnUpload").text();
		 var desc=$("#evaluation_description").val();
		 if((title!=""&&uni!=""&&key!=""&&upfiles!="Dosya Yükleme"&&desc!="")){
				var toApply={}
					toApply["email"]=$user;
					toApply["title"]=title;
					toApply["uniName"]=uni;
					toApply["key"]=key;
					toApply["filePath"]=upfiles;
					toApply["description"]=desc;
					$('.loading-spinner').css("display","");
					sendAjaxToApply(toApply);
			}
			else{
			    $('.required ').css('border-color','#f5365c');
			    $('#btnUpload ').css('background-color','#f5365c');
			    errorAlert("Başvuru");
				var millisecondsToWait = 3000;
             	setTimeout(function() {
                $('.required ').css('border-color','none');
                $('#btnUpload ').css('background-color','#5e72e4');
             }, millisecondsToWait);
			}
		});
		
function passwordSection(){
    $('#passwordSection').css("display","");
    $('#subSection').css("display","none");
/*	$('#mail_dogrulama').css("display","none");*/
}
function subSection(){
    $('#subSection').css("display","");
    $('#passwordSection').css("display","none");
/*	$('#mail_dogrulama').css("display","none");*/
}
function mail_dogrulama(){
/*    $('#mail_dogrulama').css("display","");*/
    $('#passwordSection').css("display","none");
	$('#subSection').css("display","none");
}
function getUserInfo(){
	var decoded = jwt_decode(localStorage.token);
	const sub=decoded.sub.split("_");
	var email=sub[0];
	sendAjaxUserInfo(email);
}

	function sendAjaxToApply(toApply){
		   $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/toApply",
	        data: JSON.stringify(toApply),
	        cache: false,
	        timeout: 600000,	      
	        success: function (data) {
				if(data=="Success"){
					$('.loading-spinner').css("display","");
					$("#result1").css("display", "none");
					successAlert("Başvuru");
					window.location.replace("../pendingApplications.html");
				}else{
					Swal.fire({
   						  title:'HATA',
   					      text:'Başvuru Yapılamadı',
   						  icon:'error',
   						  confirmButtonColor: "#696cff" 
   						});
				}
	        },
	        error: function () {
              	Swal.fire({
   						  title:'HATA',
   					      text:'Başvuru Yapılamadı',
   						  icon:'error',
   						  confirmButtonColor: "#696cff" 
   						});
            }
	    });
	}
	 $('#btnUpload').click(function(){
		var who="author";
		upControlFiles(who);
	 });
	  $('#btnUploadScoring').click(function(){
		var who="ref";
		upControlFiles(who);
	 });
	 $('#btnAssigmentFinish').click(function(){
		 var desc=$("#assignment_description").val();
		 var score=$("#assignment_point").val();
		 var upfiles=$("#btnUploadScoring").text();
		 if(desc=="" || score=="" ||  upfiles=="Puanlamayı Tamamla"){			
			    $('.required ').css('border-color','#f5365c');
			    $('#btnUploadScoring ').css('background-color','#f5365c');
			    errorAlert("Puanlama");
				var millisecondsToWait = 3000;
             	setTimeout(function() {
                $('.required ').css('border-color','none');
                if( upfiles=="Dosya Yükle"){
					$('#btnUploadScoring ').css('background-color','#5e72e4');
				}
				else{
					$('#btnUploadScoring ').css('background-color','#2dce89');
				}
                
             }, millisecondsToWait);
		}else{
			var obj={};
			obj["user_email"]=$user;
			obj["evaluation_id"]=$('#evaluation_id').val();
			obj["score"]=score;
			obj["description"]=desc;
			obj["scoreFilePath"]=upfiles;
			$('.loading-spinner').css("display","");
			setAssignmentScoreByRef(obj);	
	 	}
	 });
	 
	 function setAssignmentScoreByRef(jsonreq){ 
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/setAssignmentScoreByRef",
	        data: JSON.stringify(jsonreq),
	        cache: false,
	        timeout: 600000,	      
	        success: function (data) {
				$("#assignment_description").val("");
				$("#assignment_point").val("");
				$("#btnUploadScoring").text("Dosya Yükle");
				$('#btnUploadScoring ').css('background-color','#2dce89');
				$('.loading-spinner').css("display","none");		
				if(data=="Success"){
					Swal.fire({
						icon:'success',
    			        title: "Puanlama Başarıyla Yapıldı",
    			        position: "center",
    			        timer: 1500,
    			        showConfirmButton: !1,
    			    });
			    $('#exampleModalMessage').modal('hide');
			       	 if($user!=null){
			    		 var op={};
						 op["email"]=$user;
						 getAssignmentTable(op);
			    	 }
				}
				
	        },
	        error: function () {
			  $('.loading-spinner').css("display","none");
              Swal.fire({
						icon:'error',
    			        title: "Puanlama Başarısız Oldu",
    			        position: "center",
    			        timer: 1500,
    			        showConfirmButton: !1,
    			    });
            }
	    });
}
	 
	 async function upControlFiles(who){
			
		 const { value: file } = await Swal.fire({
		  title: 'Dosya Seçiniz',
		  confirmButtonText:'Yükle',
		  confirmButtonColor: "#696cff",
		  input: 'file',
		  inputAttributes: {
		    'accept': 'application/pdf',
		    'aria-label': 'Dosya yükle'
		  }
		})

		if (file) {
			const fileType=file.name.split(".");
			if(file.type=="application/pdf" && fileType[1]=="pdf"){
				$('.loading-spinner').css("display","");
				uploadFile(file,who);
			}
		}
		 if(file==null){
			 const element=null;
			try{
			  element= document.getElementById("btnUpload");
			}
			catch(err){
			  element= document.getElementById("btnUploadScoring");
			}
		   
		    element.innerHTML="Dosya Yükle"; 
		    element.style.backgroundColor="#5e72e4";
		    errorAlert("Dosya Yükleme");
			Swal.fire({
			  title:'Dosya Seçilmedi!',
			  icon:'error',
			  showConfirmButton: false,
			  timer: 700
			})
		}				 
	}
	 async function uploadFile(file,who) {
		  let formData = new FormData(); 
		  formData.append('file', file,file.name);
		  formData.append('who', who);
		  let response = await fetch('/upload', {
		    method: "POST", 
		    credentials: 'same-origin',
		    body: formData
		  }); 
		  
		  if (response.status == 200) {			
			try{
				const element=document.getElementById("btnUpload");
				 element.innerHTML=file.name;   
				 element.style.backgroundColor="#2dce89";
			}catch(err){
				const element= document.getElementById("btnUploadScoring");
				 element.innerHTML=file.name;   
				 element.style.backgroundColor="#2dce89";
			}
			 successAlert("Dosya Yükleme");
			 $('.loading-spinner').css("display","none");
		  }
		  else{
			  $('.loading-spinner').css("display","none");
			  element.innerHTML="Dosya Yükle";   
			  element.style.backgroundColor="#5e72e4";
			  errorAlert("Dosya Yükleme");
		  }
		}
	 async function successAlert(alert){
		  const Toast = Swal.mixin({
			  toast: true,
			  position: 'top-end',
			  showConfirmButton: false,
			  timer: 3000,
			  timerProgressBar: true,
			  didOpen: (toast) => {
			    toast.addEventListener('mouseenter', Swal.stopTimer)
			    toast.addEventListener('mouseleave', Swal.resumeTimer)
			  }
			})

			Toast.fire({
			  icon: 'success',
			  title: alert + ' Başarılı'
			})
	  }
	 async function errorAlert(alert){
		  const Toast = Swal.mixin({
			  toast: true,
			  position: 'top-end',
			  showConfirmButton: false,
			  timer: 5000,
			  timerProgressBar: true,
			  didOpen: (toast) => {
			    toast.addEventListener('mouseenter', Swal.stopTimer)
			    toast.addEventListener('mouseleave', Swal.resumeTimer)
			  }
			})

			Toast.fire({
			  icon: 'error',
			  title: alert +' Başarısız'
			})
	  }
  	function sendAjaxUserInfo(jsonreq){ 
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/getUserInfo",
	        data: JSON.stringify(jsonreq),
	        cache: false,
	        timeout: 600000,	      
	        success: function (data) {
					$("#userOnline").text( data.firstName+" "+data.lastName );
					$("#evaluation_name").val( data.firstName+" "+data.lastName );
					$("#evaluation_email").val($user);
				}
	    });
}
function sendAjaxUserPasswordChange(jsonreq){ 
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/updatePasswordByUser",
	        data: JSON.stringify(jsonreq),
	        cache: false,
	        timeout: 600000,	      
	        success: function (data) {
				if(data=="Success"){
					Swal.fire({
								icon:'success',
	        			        title: "Şifre Başarıyla Değiştirildi",
	        			        position: "center",
	        			        timer: 1500,
	        			        showConfirmButton: !1,
	        			    });
				}
				if(data=="regexCntrl"){
					$("#result").css("display", ""),
	              	$("#result").css("color", "red"),
	                $("#result").css("font-size", "small"),
	                $("#result").text("Şifre:8-16 karakter uzunluğunda olmalıdır.En az bir büyük harf,kücük harf,sayı ve özel karakter içermelidir.");
				}
				if(data=="Red"){
					$("#result").css("display", ""),
	              	$("#result").css("color", "red"),
	                $("#result").css("font-size", "medium"),
	                $("#result").text("Girilen mevcut şifre hatalı.");
				}
				if(data=="Failed"){
					Swal.fire({
								icon:'error',
	        			        title: "Şifre Değiştirilemedi",
	        			        position: "center",
	        			        timer: 1500,
	        			        showConfirmButton: !1,
	        			    });
				}
				$('#oldPassword').val("");
				$('#newPassword').val("");
				$('#reNewPassword').val("");
	        },
	        error: function (e) {
              
            }
	    });
}
function sendAjaxUserChange(jsonreq){ 
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/updateUserByUser",
	        data: JSON.stringify(jsonreq),
	        cache: false,
	        timeout: 600000,	      
	        success: function (data) {
				if(data=="Success"){
					$("#result1").css("display", "none");
					Swal.fire({
						icon:'success',
    			        title: "Değişiklikler Başarılı",
    			        position: "center",
    			        timer: 1500,
    			        showConfirmButton: !1,
    			    });
				}
				if(data=="Failed"){
					Swal.fire({
						icon:'error',
    			        title: "Değişiklikler Başarısız",
    			        position: "center",
    			        timer: 1500,
    			        showConfirmButton: !1,
    			    });
				}
	        },
	        error: function (e) {
              
            }
	    });
	    
}
