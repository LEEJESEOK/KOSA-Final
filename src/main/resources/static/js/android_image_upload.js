var ANDROID_IMAGE_UPLOAD = function(){
	var android_que = [];
	var $type = '';
	var $image_list_obj = {};

	var init = function(type){
		$type = type;
	}

	//이미지 임시저장 리스트 추가
	if(!($image_list_obj.length > 0)) {
		var image_list_html = $("<ul id='image_list' style='display: none'></ul>");
		$("body").append(image_list_html);
		$image_list_obj = image_list_html;
	}

	var androidAppPostImageUploadComplete = function(image){
		if(image.tmp_idx > 0){
			android_que.push(image);
			if(image.is_last == "Y") androidAppPostImageUploadAllComplete();
		}
	};

	var androidAppPostImageUploadAllComplete = function(){
		androidAppPostImageInsert(android_que[0]);
	};

	var androidAppPostImageInsert = function(image){
		if(FROALA_VERSION >= 300){
			FroalaEditor('#'+$type).image.insert(CDN_UPLOAD_URL+image.url, true);
		}else{
			$('#'+$type).froalaEditor('image.insert', CDN_UPLOAD_URL+image.url, true);
			$('#'+$type).off('froalaEditor.image.inserted').on('froalaEditor.image.inserted', function (e, editor, $img, response) {
				if(IS_ANDROID_APP == 'Y') {
					var img = $('#'+$type).find('img[src="'+CDN_UPLOAD_URL+image.url+'"]');
					img.data(image);
					postAddImage(image.tmp_idx,image.size);
					android_que.splice(0,1);
					if (android_que.length > 0) androidAppPostImageUploadAllComplete();
				}
			});
		}
	};

	var postAddImage = function(tmp_idx,size){
		var uniq_id = makeUniq('image_');
		var hidden_input = $('<input name="temp_images[]" value="' + tmp_idx + '" type="hidden" />');
		var li 	= $('<li>').attr('id',uniq_id).data({'item':uniq_id,size:size});
		li.append(hidden_input);
		$image_list_obj.append(li);
	};

	return {
		'androidAppPostImageUploadComplete':function(image){
			androidAppPostImageUploadComplete(image);
		},
		'init':function(type){
			init(type);
		}
	}
}();