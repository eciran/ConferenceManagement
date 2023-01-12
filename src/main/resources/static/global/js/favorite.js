$(document).ready(function () {
		if($user!=null && $user.trim().length>0){
			loadFavorite();	
		}
		else{
			window.location="../login.html";
		}
	});
    function passwordSection(){
        $('#passwordSection').css("display","");
        $('#subSection').css("display","none");
    }
    function subSection(){
        $('#subSection').css("display","");
        $('#passwordSection').css("display","none");
    }