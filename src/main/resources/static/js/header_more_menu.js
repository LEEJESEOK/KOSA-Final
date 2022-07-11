var HEADER_MORE_MENU = function(){
	var uniq_id = '';
	var $menu_wrap;
	var $menu_grid;
	var $menu;
	var $menu_obj_clone, $menu_obj_clone2;
	var more_width;
	var width_adj = 1;
	var prev_more_width = 60;
	var $more_menu;
	var $col_group;
	var use_mega_dropdown = false;
	var resize_time = {};
	var more_menu_html = '<li class="dropdown _more_menu"><a data-toggle="dropdown" class="fixed_transform dropdown-toggle disabled dropdown-more _header_dropdown" data-toggle="dropdown" aria-expanded="false"><i class="icon-options vertical-middle" aria-hidden="true"></i></a><ul class="dropdown-menu more_list _more_list"></ul></li>';

	var getAdjWidth = function(){
		width_adj = width_adj;
		return width_adj;
	};

	var resetAdjWidth = function(){
		width_adj = 1;
	};

	var init = function($_obj,_use_mega_dropdown,callback){
		uniq_id = makeUniq();
		$menu_wrap = $_obj;
		use_mega_dropdown = _use_mega_dropdown;


		$menu = $menu_wrap.find('._main_menu');
		$menu_wrap.css({'visibility' : 'hidden'});

		$menu_obj_clone = $menu.clone(true);
		$menu_obj_clone.removeClass('_main_menu');
		$menu_obj_clone.addClass('_main_clone_menu main_clone_menu');
		$menu_wrap.find('._main_clone_menu').remove();
		$menu_wrap.append($('<div/>').css({
			position : 'absolute',
			top : -9999,
			left : -9999
		}).append($menu_obj_clone));

		$menu_obj_clone2 = $menu.clone(true);
		$menu_obj_clone2.empty();
		$menu_obj_clone2.removeClass('_main_menu');
		$menu_obj_clone2.addClass('main_clone_menu');
		$more_menu = $(more_menu_html);

		setTimeout(function() {
			$menu_obj_clone2.append($more_menu);
			$menu_wrap.append($('<div/>').css({
				position : 'absolute',
				top : -9999,
				left : -9999
			}).append($menu_obj_clone2));
			more_width = Math.ceil($more_menu.outerWidth())+width_adj;

			calculateMenuWidth(callback);
		}, 500);


		$(window).off('resize.'+uniq_id).on('resize.'+uniq_id,function(){
			if(resize_time) {
				clearTimeout(resize_time);
			}
			resize_time = setTimeout(function() {
				$(this).trigger('resizeEnd.'+uniq_id);
			}, 500);
		});

		$(window).off('resizeEnd.'+uniq_id).on('resizeEnd.'+uniq_id,function(){
			calculateMenuWidth(callback);
		});
		$('body').on('section_change.'+uniq_id,function(e,event_section_code){
			var $section = $menu.closest('div[data-type=section]');
			var section_code = $section.attr('section-code');
			if(section_code != event_section_code)
				return false;
			calculateMenuWidth(callback);
		});
	};

	var allowDropdownHover = function(){
		if($(window).width() > 991){
			$menu_wrap.find('._header_dropdown').dropdownHover();
		}
	};

	var calculateMenuWidth = function(callback){
		resetAdjWidth();
		$col_group = $menu_wrap.closest('div[data-type=col-group]');
		$menu_grid = $menu_wrap.closest('div[data-type=grid]');
		$col_group.css('visibility', 'hidden');
		var menu_cnt = 0;
		$menu.children("li").filter(function(){
			return $(this).css('display') != 'none';
		}).each(function(index){
			$(this).data('width', Math.ceil($(this).outerWidth(true)));
			menu_cnt++;
		});
		prev_more_width = more_width;
		more_width = Math.ceil($more_menu.outerWidth())+getAdjWidth();
		if(more_width <= 50)
			more_width = prev_more_width;


		var col_group_width = Math.floor($col_group.width());
		var menu_width = 0;

		var $menu_grid_siblings = $menu_grid.siblings().not('div[data-type=line-guide]');
		$menu_grid_siblings.each(function(){
			var _s_width = Math.ceil($(this).outerWidth(true));
			menu_width += _s_width;
		});


		var is_split = false;

		var calc_list = [];
		$menu_obj_clone.children("li").filter(function(){
			return $(this).css('display') != 'none';
		}).each(function(index){
			var _width_adj = getAdjWidth();
			var _width_data = {'obj':$(this), 'width' : Math.ceil($(this).outerWidth())+_width_adj};
			calc_list.unshift(_width_data);
			menu_width += Math.ceil($(this).outerWidth(true));
			menu_width += _width_adj;
			if(menu_width >= col_group_width){
				is_split = true;
				return false;
			}
		});

		if(is_split){
			$.each(calc_list, function(e, _calc_data){
				menu_width = menu_width - _calc_data.width;
				var more_contain_width = menu_width + more_width+40;
				if(more_contain_width >= col_group_width){
					if(calc_list.length == 1){
						splitMoreMenu(_calc_data.obj, callback);
						return false;
					}
					return true;
				}else{
					splitMoreMenu(_calc_data.obj, callback);
					return false;
				}
			});
		}else{
			splitMoreMenu(false, callback);
		}
	};

	var splitMoreMenu = function($split_item, callback){
		if(!$split_item){
			$menu.empty();
			$menu_obj_clone.children("li").each(function(index){
				$menu.append($(this).clone(true));
			});
		}else{
			var $menu_items = $split_item.prevAll();
			$menu.empty();
			$menu_items.each(function(){
				$menu.prepend($(this).clone(true));
			});
			var $more_items = $split_item.nextAll();
			$more_menu = $(more_menu_html);
			$menu.append($more_menu);

			if(!use_mega_dropdown){
				$more_menu.find('._more_list').append($split_item.clone(true).removeClass('dropdown').addClass('dropdown-submenu'));
				$more_items.each(function(){
					var $_clone_item = $(this).clone(true);
					$_clone_item.removeClass('dropdown').addClass('dropdown-submenu');
					$more_menu.find('._more_list').append($_clone_item);
				});
			}

		}

		if(!use_mega_dropdown){
			$menu.find("li.dropdown").each(function(index){
				$(this).find("li.dropdown-submenu").each(function(index){
					if(!$(this).hasClass('pulldown-hide')){
						if($(this).find(".dropdown-menu > li").length > 0) $(this).addClass("sub-active");
					}else{
						$(this).find('ul').removeClass('dropdown-menu');
						$(this).find('ul li').hide();
					}
				});
			});
			$more_menu.find("li").each(function(index){
				$(this).find("li.dropdown-submenu").each(function(index){
					if(!$(this).hasClass('pulldown-hide')){
						if($(this).find(".dropdown-menu > li").length > 0) $(this).addClass("sub-active");
					}else{
						$(this).find('ul').removeClass('dropdown-menu');
						$(this).find('ul li').hide();
					}
				});
			});
		}
		$menu_wrap.css('visibility', 'visible');
		$col_group.css('visibility', 'visible');

		allowDropdownHover();

		if(typeof callback == 'function'){
			callback();
		}

		$menu_wrap.trigger('more_menu_complete');


	};



	return {
		'init' : function($obj,callback){
			init($obj,callback);
		},
		'calculateMenuWidth' : function(callback){
			calculateMenuWidth(callback);
		}
	}

};

