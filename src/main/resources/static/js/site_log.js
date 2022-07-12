var SITE_VISIT_LOG = function(){

	var addVisitLog = function(referer, token, token_key, menu_code){
		$.ajax({
			type : 'POST',
			data : {'referer' : referer, 'token' : token, 'token_key':token_key, 'menu_code':menu_code},
			url : ('/backpg/add_visit_log.cm'),
			dataType : 'json',
			success : function(res){
			}
		});
	};

	var getToken = function(callback){
		$.ajax({
			type : 'POST',
			data : {},
			url : ('/backpg/get_visit_token.cm'),
			dataType : 'json',
			success : function(res){
				if(res.msg == 'SUCCESS'){
					callback(res.log_token, res.log_token_key, res.reffrer);
				}
			}
		});
	};

	return {
		'addVisitLog' : function(referer, token, token_key, menu_code){
			addVisitLog(referer, token, token_key, menu_code);
		},
		'getToken' : function(callback){
			getToken(callback);
		}
	}
}();