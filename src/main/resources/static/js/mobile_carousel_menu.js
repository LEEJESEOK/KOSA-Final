var MOBILE_CAROUSEL_MENU = function($carousel_wrap_obj){
	var init = function($carousel_wrap_obj){

		var $carousel_objs =  $carousel_wrap_obj.find('._mobile_navigation_menu');

		$carousel_objs.each(function(e){
			makeCarousel($(this));
		});
	};

	var makeCarousel = function($carousel_obj){
		var target = 0;
		var left_num  = 0;
		var right_num = 0;
		var width = $carousel_obj.width();
		var active_width = 0;
		var right_width = 0;
		var left_width = 0;
		var item_width = [];
		var i = 0;
		$carousel_obj.find('._item').each(function(e){
			i++;
			var left_margin = parseInt($(this).css('margin-left').replace('px' ,''));
			var right_margin = parseInt($(this).css('margin-right').replace('px' ,''));
			var _item_width = $(this).outerWidth()+left_margin+right_margin;
			item_width.push(_item_width);
			if(target > 0){
				right_width += _item_width;
				right_num++;
			}
			if(target < 1){
				left_width += _item_width;
				left_num++;
			}

			if($(this).hasClass('active')){
				target = i;
				active_width = _item_width;
				if(width <= left_width ){
					$carousel_obj.scrollLeft(left_width)
				}
			}

		});
	};
	init($carousel_wrap_obj);
};