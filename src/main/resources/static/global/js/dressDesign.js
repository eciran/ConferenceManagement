$(document).ready(function(){
	if(localStorage.token==null){
		window.location.replace("../login.html");
	}
});
	//Uzun Etek Secilirse
	function long_skirt(skirt_value){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("skirt-lace.html?"+url[1]+"&skirt="+skirt_value);	
		}else{
			window.location.replace("skirt-lace.html?"+"skirt="+skirt_value);	
		}		
	};
	//Kısa Etek Secilirse
	function short_skirt(skirt_value){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("skirt-shape.html?"+url[1]+"&skirt="+skirt_value);	
		}else{
			window.location.replace("skirt-shape.html?"+"skirt="+skirt_value);
		}		
	};
	function short_skirtType(shape_value){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("skirt-lace.html?"+url[1]+"&skirt_shape="+shape_value);	
		}else{
			window.location.replace("skirt-lace.html?"+"skirt_shape="+shape_value);
		}		
	};
	//Dantel Secilirse
	function skirt_lace(lace_value){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("skirt-back.html?"+url[1]+"&skirtlace="+lace_value);	
		}else{
			window.location.replace("skirt.html");
		}		
	};
	//Arka etek tipi
	function skirt_back(value){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			const slace=url[1].split("&");
			var _lace;
			if(slace[1].includes("skirtlace=")){
				_lace=slace[1].substring(10,11)
			}
			else if(slace[2].includes("skirtlace=")){
				_lace=slace[2].substring(10,11)
			}
			switch(_lace){
				case "2":
					window.location.replace("lace-type.html?"+url[1]+"&skirtback="+value);	
					break;
				case "1":
					window.location.replace("illision.html?"+url[1]+"&skirtback="+value);	
					break;
			}
		}else{	
			window.location.replace("skirt.html");	
		}		
	};
	//Dantel Tipi secimi
	function lacetype(lacetype){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("illision.html?"+url[1]+"&lacetype="+lacetype);	
		}else{
			window.location.replace("skirt.html");
		}		
	};
	// Yaka icin tul secimi
	function illision(illision){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("illision-type.html?"+url[1]+"&illision="+illision);	
		}else{
			window.location.replace("skirt.html");
		}		
	};
	function illision1(illision){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("illision-type1.html?"+url[1]+"&illision="+illision);	
		}else{
		window.location.replace("skirt.html");
		}		
	};
	// Yaka tul icin tip secimi
	function illisiontype(illisiontype){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			window.location.replace("sleeves.html?"+url[1]+"&illisiontype="+illisiontype);	
		}else{
			window.location.replace("skirt.html");	
		}		
	};
	function designFinish(sleevesvalue){
		if((document.URL).includes("?")){
			let url=(document.URL).split("?");
			var designText=url[1]+"&sleeves="+sleevesvalue;
			$('#hidden-section').attr("hidden",true);
			$('#builder-gif').attr("hidden",false);
			var decoded = jwt_decode(localStorage.token);
			const sub=decoded.sub.split("_");
			var username= sub[0];
			designText=designText+"-"+username;
			sendAjax(designText);
		}	
	};
	function returnBack(){
		let text="Değişikleriniz Kayıt Edilmeyecek.Başa Dönmek İstiyor musunuz?";
		 if (confirm(text) == true) {
		    window.location.replace("skirt.html");	
		  } 
		else{
			 window.location.replace(document.URL);
		}
	};
	
	function sendAjax(designText){
		 $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/designDress",
	        data: JSON.stringify(designText),
	        cache: false,
	        timeout: 300000,
			headers: {"Authorization": localStorage.getItem('token')},
	        success: function (data) {
						var origin   = window.location.origin;
	        			window.location.replace(origin +"/my/myDress.html");     	
	        },
	  
	    });
	}