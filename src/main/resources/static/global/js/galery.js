	$(document).ready(function () {
		if(localStorage.token!=null){
			loadPage();	
		}
		else{
			window.location="../login.html";
		}
			
	});
	
	function loadPage(){
		 $.ajax({
                  type: "POST",
			        contentType: "application/json",
			        url: "/galery"+window.location.search,
			        cache: false,
			        timeout: 600000,	     
                    data: JSON.stringify(),
                    success: function(data){
						document.getElementById("ulItem").innerHTML="";
                 		if(data.length>0){
							for(var x=0;x<data.length;x++){								
								const arr=data[x].split(",");								
								cnt=1;
								if(x>30){
									cnt=2;
								}									
								rnd_height=Math.floor(Math.random() * 200) + 100,
								document.getElementById("ulItem").innerHTML+="<li data-id='"+arr[0]+"' data-page='"+cnt+"'><a><div class='item-photo lazy' data-src='"+arr[1]+"' style='background-color:#1d1c18;' data-size='440x660'><img src='"+arr[1]+"' id='"+arr[0]+"' class='image-modal' data-aspect='1.5'><div class='item-watchlist-base'><i class='fas fa-heart'></i></div><div class='item-watchlist'  data-id='"+arr[0]+"' title='Favorilere ekle'><i class='fal fa-heart'></i></div></div></a></li>";
								}							
								
                        		 $(".shop-result").removeClass("loading", "fast");
								 $(".shop-result-preloader").attr("hidden", "true");
                       			arr=null;
							
							
						}	
						
                       		//	document.getElementById("ulItem").innerHTML+="<li class='items-more items-more-auto'><a href='"+window.location.href+"&page=2'class='btn btn-outline-secondary' data-page='2'>Load More</a></li> <div class='spinner hide'><div class='dot1'></div><div class='dot2'></div><div class='dot3'></div></div>";
							 $(".shop-result-preloader").attr("hidden", "true");
							 $.doIsotope($("#ulItem"));
                    }
                })
	}
    $(function(){

        
        var $requests = Array();
        var $total = 323;
        var $symbol = "₺";

   //     shopActionLabels();

        
        $(document).on("click", ".mobile-menu-trigger", function(e) {

            $(".popover .close-popover").each(function() {
                popoverClose($(this));
            });

        });

        /* Overlay */

        $(".overlay-toggle").click(function(e){
            e.preventDefault();

            var $overlay = $(".overlay-" + $(this).attr("data-overlay"));

            if( $overlay.hasClass("active") ){

                $("body").removeClass("modal-open");
                $(".overlay-background").removeClass("active");
                $overlay.removeClass("active");

            } else {

                $(".popover .close-popover").each(function() {
                    popoverClose($(this));
                });

                $("body").addClass("modal-open");
                $(".overlay-background").addClass("active");

                // Open search tab
                $overlay.find(".btn-group-search").click();

                $overlay.find(".overlay-content").scrollTop(0);
                $overlay.addClass("active");

                if($total == null){
                    $("form.shop").change();
                }

            }

        });

        $(".overlay.overlay-filter .overlay-filter-reset").click(function(e){
            e.preventDefault();

            $("form.shop").find("input[type='checkbox']").prop("checked", false);
            $("form.shop").find("input[type='radio'][value='']").prop("checked", true);
            $("form.shop").find("input[type='hidden']").val("");

            $(".shop .slider-price").slider("values", [0, 4000]);
            $(".shop .slider-height").slider("values", [$(".shop .slider-height").slider("option", "min"), $(".shop .slider-height").slider("option", "max")]);

            if($(".shop input[name='store']").val()){
                $(".shop input[name='store']").prop("checked", true).change();
            }

        });

        $(".overlay.overlay-sort .overlay-content .list-group a").click(function(e){
            e.preventDefault();

            var $overlay = $(".overlay-sort");

            $(".overlay.overlay-sort .list-group-item").removeClass("active");
            $(this).addClass("active");

            $("body").removeClass("modal-open");
            $(".overlay-background").removeClass("active");
            $overlay.removeClass("active");

            $(".shop input[name='sort']").val($(this).data("sort"));

            setTimeout(function() {
                loadSearch();
            }, 500);

        });

        $(".overlay.overlay-filter .btn-search").click(function(e){
            e.preventDefault();
            setTimeout(function() {
                loadSearch();
            }, 500);

        });


        //.data("uiSlider")._slide();

        function heightFormat($value, $format) {
            if($format == 'i') {
                let $feet = Math.floor($value / 12);
                $value %= 12;
                return $feet + "'" + ($value ? $value + "\"" : "");
            } else {
                return $value + 'cm';
            }
        }


        /* Listing click */

        
        $highlight = $("li[data-id='271240']");

        if($highlight.length > 0) {

            setTimeout(function() {

                $.scrollTo($highlight, 400, {offset:-150, onAfter: function() {
                    $highlight.find("a").addClass("hover");
                    $highlight.effect("shake", {direction: "right", distance: 10, times: 2, easing: "easeOutSine"}, 400);
                }});

            }, 1000);

        }
        
        $(document).on("click",".items li a", function(e) {

            var $element = $(this).parents("li");

            if(!$element.hasClass("items-more")) {

                var $vSerialize = $("form.shop :input[value!='']").serialize();
                var $vPage = $(this).parents("li").attr("data-page");
                var $vURL = ($vSerialize.length > 0) ? "?" + $vSerialize : "/galery";

                if ($vPage == undefined) {
                    $vPage = 1;
                }

                if ($vSerialize == '') {
                    $vURL = "?page=" + $vPage + "&listing=" + $element.attr("data-id");
                } else {
                    $vURL = "?" + $vSerialize + "&page=" + $vPage + "&listing=" + $element.attr("data-id");
                }

                if (history.pushState) {
                    history.pushState(null, null, $vURL);
                } else {
                    location.hash = $vURL;
                }
            }

        });

        /* Popovers */

 

        $(document).on('click', '.close-popover', function(e) {
            e.preventDefault();

            popoverClose($(this));

            loadSearch();

        });

       
        function popoverClose($element){

            $source = $element.closest(".popover-body").find(".placeholder-source");
            $destination = $($element.attr("data-element"));

            $destination.empty().append($source);

            $(".shop-action .shop-action-popover").popover('dispose');
            $(".shop-action .shop-action-popover.nav-link").removeClass("active");

            $(".popover-background").removeClass("active");

        }

        $(document).on('change', 'form.shop', function() {

            $total = null;
      //      $(".btn-search").html(buttonSearchText());

            for(var i = 0; i < $requests.length; i++) {
                $requests[i].abort();
            }

            var $serialize = $("form.shop :input[value!='']").serialize();

        });

        $(".btn-group-search").click(function(){
            $(".overlay-content .tab-content .tab-pane").removeClass("active");
            $(".overlay-content #search").addClass("active");
        //    $(".overlay-footer, .overlay-filter-reset").removeClass("hide");
            $(".overlay-content").removeClass("tall");
        });

        $(".overlay-filter input[type='text']").focus(function(){
            $(".overlay-filter .overlay-content").scrollTo($(this), 0, {offset:-10});
        });

        /* Store */

        $(".shop input[name='store']").change(function(e){

            if($(this).prop("checked")){
                $("input[name='location']").val([""]);
                $(".shop .filter-location").addClass("hide");
            } else {
                $(".shop .filter-location").removeClass("hide");
            }

        });

        /* Site search */

        $(".navbar-nav-search .search-input .search-input-clear").click(function(e) {
			
            e.preventDefault();
            $("form.shop input[name='q']").val("");
            loadSearch();
        });

        function loadSearch() {
            //$total = null;

            for(var i = 0; i < $requests.length; i++) {
                $requests[i].abort();
            }

            var $serialize = $("form.shop :input[value!='']").serialize();
            var $url = ($serialize.length > 0) ? "?" + $serialize : "/galery.html";

          //  shopActionLabels();

            $(".shop-result").addClass("loading");

            if(history.pushState) {
                history.pushState(null, null, $url);
            }

            $.scrollTo($(".shop-result"), 0, {offset:-200});

            $requests.push(
                $.ajax({
                  type: "POST",
			        contentType: "application/json",
			        url: "/galery"+window.location.search,
			        cache: false,
			        timeout: 600000,	     
                    data: $serialize,
                    success: function(data){
						document.getElementById("ulItem").innerHTML="";
                 		if(data.length>0){
							for(var x=0;x<data.length;x++){
								const arr=data[x].split(",");								
								cnt=1;
								if(x>30){
									cnt=2;
								}									
								rnd_height=Math.floor(Math.random() * 200) + 100,
								document.getElementById("ulItem").innerHTML+="<li data-id='"+arr[0]+"' data-page='"+cnt+"'><a href='' target='_blank'><div class='item-photo lazy' data-src='"+arr[1]+"' style='background-color:#1d1c18;' data-size='440x660'><img src='"+arr[1]+"' data-aspect='1.5'><div class='item-watchlist-base'><i class='fas fa-heart'></i></div><div class='item-watchlist' title='Add to watchlist'><i class='fal fa-heart'></i></div></div></a></li>";
								}							
								
                        		 $(".shop-result").removeClass("loading", "fast");
								 $(".shop-result-preloader").attr("hidden", "true");
                       			arr=null;
							
							
						}	
						
                       		//	document.getElementById("ulItem").innerHTML+="<li class='items-more items-more-auto'><a href='"+window.location.href+"&page=2'class='btn btn-outline-secondary' data-page='2'>Load More</a></li> <div class='spinner hide'><div class='dot1'></div><div class='dot2'></div><div class='dot3'></div></div>";
							 $(".shop-result-preloader").attr("hidden", "true");
							 $.doIsotope($("#ulItem"));
                    }
                })
            );

        }

       /*  function buttonSearchText(number) {

            if(number == undefined){

                return 'See <div class="spinner"><div class="dot1"></div><div class="dot2"></div><div class="dot3"></div></div> dresses';

            } else {

                if(number == 0){
                    return 'No dresses found';
                } else if(number == 1) {
                    return 'See 1 dress';
                } else {
                    return 'See ' + numberWithCommas(number) + ' dresses';
                }

            }

        } */

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

    /*     function shopActionLabels() {

            var sizes = [];
            var max = $("form.shop input[name='size']:last").val();

            $("form.shop input[name='size']:checked").each(function() {

                if($(this).val() == max) {
                    suffix = "+";
                } else {
                    suffix = "";
                }
                sizes.push($(this).val() + suffix);
            });

            if (sizes.length == 0) {
                $labelSize = "Size";
            } else if(sizes.length == 1) {
                $labelSize = "Size " + sizes;
            } else if(sizes.length == 2) {
                $labelSize = "Sizes " + sizes;
            } else {
                $labelSize = sizes.length + " sizes";
            }

            var price = $("input[name='price']").val().split("-");

            if (price.length == 2) {
                if (price[0] == 0) {
                    $labelPrice = $symbol + price[1];
                } else {
                    $labelPrice = $symbol + price[0] + " to " + $symbol + price[1];
                }
            } else {
                if (price[0].length > 0) {
                    $labelPrice = "Over " + $symbol + price[0];
                } else {
                    $labelPrice = "Price";
                }
            }

            var designer = $("#designers input[type='checkbox']:checked").length;

            if (designer == 0) {
                $labelDesigner = "Designer";
            } else if(designer == 1) {
                $labelDesigner = "1 designer";
            } else {
                $labelDesigner = designer + " designers";
            }

            var $filters = 0;

            $filters += parseInt($("form.shop input[name!=''][value!='']:checked").length);
            $filters += parseInt($("form.shop input[name='price'][value!='']").length);
            $filters += parseInt($("form.shop input[name='height'][value!='']").length);

         //   $(".shop-action .nav-link[data-element='#popover-size'] span").text($labelSize);
        //    $(".shop-action .nav-link[data-element='#popover-price'] span").text($labelPrice);
         //   $(".shop-action .nav-link[data-element='#popover-designer'] span").text($labelDesigner);


            if($filters > 0) {
                $(".shop-action .nav-link[data-overlay='filter'] .badge").text($filters).removeClass("hide");
            } else {
                $(".shop-action .nav-link[data-overlay='filter'] .badge").addClass("hide");
            }

        }

        $(".modal").on("show.bs.modal", function() {

            $(".popover .close-popover").each(function() {
                popoverClose($(this));
            });

        }); */


        var $subnavbar = $(".subnavbar");
        var $window = $(window);

        $window.bind('scrollEnd', function() {

            if($(this).scrollTop() > 0){
                $subnavbar.addClass("shadow-sm");
            } else {
                $subnavbar.removeClass("shadow-sm");
            }

        });


        /* Saved searches */

        /* Open filter panel */

        $(".btn-group-saved").click(function(){
            //alert("saved clicked");
            $(".overlay-content .tab-content .tab-pane").removeClass("active");
            $(".overlay-content #saved").addClass("active");
        //    $(".overlay-footer, .overlay-filter-reset").addClass("hide");
            $(".overlay-content").addClass("tall");


            if($("#saved").hasClass("empty")){
                $("#saved").removeClass("empty").html('<div class="spinner"><div class="dot1"></div><div class="dot2"></div><div class="dot3"></div></div>').load($base+"/api/save?format=html");
            }

        });
        $(".overlay-filter").on("click", ".open-modal", function(e) {

            $("body").removeClass("modal-open");
            $(".overlay-background").removeClass("active");
            $(".overlay-filter").removeClass("active");

        });


        

        

        
    });
