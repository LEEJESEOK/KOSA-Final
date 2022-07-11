var SITE_ANIMATION = function(){
	var $body = $('body');
	var $widget = $body.find('div[doz_type="widget"]');
	var ani_class = 'ds_animated';


	var init = function(ds_animation, load){
		$(document).ready(function(){
			startAnimation(ds_animation, load);
		});
	};

	var startAnimation = function(ds_animation, load){
		$widget.each( function(i) {
			var $this = $(this);
			var $widget_data = $this.find('._widget_data');
			if($widget_data.hasClass('_ds_animated_except')){
				return true;
			}
			if( ds_animation === 'Y'){
				$widget_data.addClass(ani_class);
			}else if(!$widget_data.hasClass('wg_animated')){
				return true;
			}

			var widget_key = $this.attr('id');

			if(ds_animation === 'Y' && load === 'Y'){
				// 위젯 전체 애니메이션 적용 및 첫 로드시에만 애니메이션 실행 시 첫 로드된 상태에서 애니메이션 준비
				scrollAnimation($this, ds_animation, "N");
			}

			$(window).on('load.'+widget_key+' resize.'+widget_key+' scroll.'+widget_key, function(){
				// resize(y축 스크롤 축소 -> 확장되면서 위치 계산 비정상으로 되는것 방지하기 위함)
				// scroll(스크롤시 화면에 보여지는 위젯의 위치에 따라 이벤트 실행시키기 위함)
				setTimeout(function(){
					scrollAnimation($this, ds_animation, load);
				}, 100);
			});
		});
	};

	var scrollAnimation = function($widget, ds_animation, load){
		var window_width = $(window).width();
		var window_height = $(window).height();
		var bottom_of_window = $(window).scrollTop() + window_height;
		var bottom_of_object = $widget.offset().top;
		var device_type = '';
		if ( window_width < 992 ) {
			device_type = 'mobile';
		} else {
			device_type = 'pc';
		}
		if(window_width <= 1024 && window_width >= 767){
			// css zoom 사용 시 jquery offset에 오류가 있어 별도로 계산
			var offset = $widget[0].getBoundingClientRect().top - document.body.getBoundingClientRect().top;
			bottom_of_object = offset * 0.85;
		}

		var $obj = $widget.find('._widget_data[data-widget-parent-is-mobile="N"]');
		if ( device_type == 'mobile' ) {
			$obj = $widget.find('._widget_data');
		}
		var wg_exception_type = $obj.attr('data-widget-type');
		var wg_ani_class = $obj.attr('data-widget-anim');
		var wg_ani_duration = $obj.attr('data-widget-anim-duration');
		var wg_ani_delay = $obj.attr('data-widget-anim-delay');

		if ( wg_exception_type === 'icon' || wg_exception_type === 'button' ) {
			$obj = $widget.find('div[data-widget-parent-is-mobile="N"] .wg_animated');
			if( device_type == 'mobile' ) {
				$obj = $widget.find('.wg_animated');
			}
		}

		if ( $obj.hasClass('wg_animated') ){
			$obj.removeClass(ani_class);
			$obj.css('opacity', 0);
			if ( bottom_of_window > bottom_of_object ) { // 객체가 화면에 보여지면 실행
				$obj.addClass(wg_ani_class);
				$obj.css({
					'animation-duration': wg_ani_duration+'s',
					'-webkit-animation-duration': wg_ani_duration+'s',
					'animation-delay': wg_ani_delay+'s',
					'opacity': 1
				});
			} else {
				if ( device_type === 'mobile' || load === 'Y' ){
					return;
				} else {
					$obj.removeClass(wg_ani_class);
				}
			}
		} else {
			if ( ds_animation != 'Y' || $body.hasClass('shop_mypage') ) {
				return;
			}
			if ( bottom_of_window > bottom_of_object ) {
				$obj.addClass(ani_class);
				if ( wg_exception_type === 'code' ) {
					$obj.removeClass(ani_class);
				}
			} else {
				if ( device_type === 'mobile' || load === 'Y' ){
					return;
				} else {
					$obj.removeClass(ani_class);
					$obj.css('opacity','0');
					if ( wg_exception_type === 'code' ) {
						$obj.css('opacity', 1);
					}
				}
			}
		}
		$obj.css('visibility', 'visible');
	};

	return {
		'init' : function(ds_animation, load){
			init(ds_animation, load);
		}
	}
}();