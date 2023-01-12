function user_logOut(e) {
    $(".userOnline").attr("hidden", !0),
        $(".userOffline").attr("hidden", !1),
        $(".userNameDashboard").text(""),
        (token = null),
        (decoded = null),
        localStorage.removeItem("token"),
        null == e && logOut_alert();
}
function logOut_alert() {
    Swal.fire({
        title: "Güvenli şekilde çıkış yapıldı",
        position: "top",
        timer: 700,
        showClass: { popup: "\n\t      animate__animated\n\t      animate__fadeInDown\n\t      animate__faster\n\t    " },
        hideClass: { popup: "\n\t      animate__animated\n\t      animate__fadeOutUp\n\t      animate__faster\n\t    " },
        grow: "row",
        showConfirmButton: !1,
    });
}
$(document).ready(function () {
    var e = localStorage.token;
    if (null != e) {	
        var t = Date.now() / 1e3,
            n = jwt_decode(e);
        const sub=n.sub.split("_");
        null != n && n.exp > t
            ? ($(".userOffline").attr("hidden", !0),
              $("#userOnline").text(sub[0]),
              ($user = sub[0]),($role = sub[1]))
            : user_logOut(t);
    }
    else{
	    window.location.replace("../login.html");
	}

});
