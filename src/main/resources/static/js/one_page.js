var ONE_PAGE = function(){
	var $win;
	var window_position;
	var $sections;
	var section_selector = '_one_page_section';
	var sections_length;
	var active_class = 'active';
	var active_real_class = 'active-real';
	var $menu_wrap,$mobile_menu_wrap;
	var $mobile_carousel_wrap,$mobile_carousel_items;
	var $mobile_table_wrap,$mobile_table_items;

	var menu_height;
	var $menu_items, $mobile_menu_items;
	var active_timeout = {};

	var init = function(){
		var init_st = false;
		$(window).off('scroll.one_page').on('scroll.one_page',function(){
			if(init_st)
				menuActivate();
		});
		$(window).off('resize.one_page').on('resize.one_page',function(){
			if(init_st)
			menuActivate();
		});
		$(window).off('load.one_page').on('load.one_page',function(){
			if(init_st)
			menuActivate();
		});

		$sections = $('.' + section_selector);
		sections_length = $sections.length;
		var section_offsets = {};
		for (i = 0; i < sections_length; i++) {
			section_offsets[$sections[i].id] = $sections[i].offsetTop;
		}
		$win = $(window);
		init_st = true;

	};
	var menuActivate = function(){
		clearTimeout(active_timeout);
		active_timeout = setTimeout(function(){
			$menu_wrap = $('#doz_header');
			$mobile_menu_wrap = $('#mobile_slide_menu');
			$mobile_carousel_wrap = $('#mobile_carousel_nav');
			$mobile_table_wrap = $('#mobile_carousel_menu');
			var target = 0;
			var new_target = target-1;

			$menu_items = $menu_wrap.find('._main_menu_wrap a, ._inline_menu_container a');
			$mobile_menu_items = $mobile_menu_wrap.find('._menu_wrap a');
			$mobile_carousel_items = $mobile_carousel_wrap.find('._mobile_navigation_menu ._item');
			$mobile_table_items = $mobile_table_wrap.find('._mobile_nav ._item');


			menu_height = $menu_wrap.outerHeight()+50;
			window_position = $win.scrollTop();
			//TODO 모바일 환경 일때 스크롤 위치에 따라 메뉴 활성화 필요가 없음 예외 처리 필요


			$menu_items.toggleClass(active_class,false);
			$mobile_menu_items.toggleClass(active_class,false);
			$mobile_menu_items.toggleClass(active_real_class,false);
			$mobile_carousel_items.toggleClass(active_class,false);
			$mobile_table_items.toggleClass(active_class,false);

			$sections.each(function(e) {
				var top = $(this).offset().top - menu_height;
				var bottom = $(this).outerHeight(true) + top;
				if ((window_position >= top) && (window_position <= bottom) || e == sections_length-1) {
					$menu_wrap.find('a[data-url="' + $(this).data('url') + '"]').toggleClass(active_class,true);
					$mobile_menu_wrap.find('a[data-url="' + $(this).data('url') + '"]').toggleClass(active_class,true).toggleClass(active_real_class,true);
					$mobile_table_wrap.find('td[data-url="' + $(this).data('url') + '"]').toggleClass(active_class,true);

					if($mobile_carousel_wrap.length > 0){
						$mobile_carousel_wrap.find('div[data-url="' + $(this).data('url') + '"]').toggleClass(active_class,true);
						// var new_target = $mobile_carousel_wrap.find('div[data-url="' + $(this).data('url') + '"]').index();
						// $mobile_carousel_wrap.find('._mobile_navigation_menu').slick('slickGoTo',new_target);
					};
					return false;

				}
			});
		},100);

	};
	return {
		init : function(){
			init();
		}
	}
}();