function sendAjaxGetDress(e) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/my/myDress",
        data: JSON.stringify(e),
        cache: !1,
        timeout: 3e5,
        success: function (e) {
            var t = new Array();
            (t = e.split(",")), (frontUrl = t[0]), (backUrl = t[1]), (document.getElementById("photoUrl").src = frontUrl);
        },
    });
}
function previewChange() {
    document.getElementById("photoUrl").src == backUrl ? (document.getElementById("photoUrl").src = frontUrl) : (document.getElementById("photoUrl").src = backUrl);
}
function previewBack() {
    $("#backUrl").attr("hidden", !1), $("#frontUrl").attr("hidden", !0);
}
function previewFront() {
    $("#frontUrl").attr("hidden", !1), $("#backUrl").attr("hidden", !0);
}
function designAgain() {
    if (1 == confirm("Oluşan görsel silinecektir . Devam etmek istiyor musunuz?")) {
        var e = window.location.origin;
        window.location.replace(e + "/design/skirt.html");
    } else window.location.replace(document.URL);
}
$(document).ready(function () {
    var e = localStorage.token;
    null == e && window.location.replace("../login.html");
    var t = jwt_decode(e);
	const sub=t.sub.split("_"),
        n = {};
    (n.email = sub[0]), sendAjaxGetDress(n);
});
