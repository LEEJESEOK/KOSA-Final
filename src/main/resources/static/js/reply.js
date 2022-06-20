/*************************************************************
파일명: reply.js
기능: Reply 처리 관련 함수 및 ajax 처리
작성자: 진영서
*************************************************************/

console.log("Reply.js");

var replyService = (function() {

	//해당 게시물의 댓글 리스트 받아오기
	function getList(param, callback, error) {
		var qid = param.qid;
		console.log(qid+"reply.js");
		$.getJSON("/replies/qid/" + qid +" .json",
				function(data) {
					if (callback) {
						callback(data);
					}
				}).fail(function(xhr, status, err) {
			if (error) {
				error()
			}
			;
		}); 
	}

	//댓글 조회
	function get(rid,callback,error) {
		$.get("/replies/" + rid +".json",function(result){
			if(callback){
				callback(result);				
			}//end if			
		}).fail( function(xhr,status,err) {
			if(error){
				error();
			}
		  }				
		); 	
	}

	//댓글 추가
	function add(reply, callback, error) {
		console.log("add reply...............");
		$.ajax({
			type : 'post',
			url : '/replies/new',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		})
	}

	//댓글 삭제
	function remove(rid, callback, error) {
		$.ajax({
			type: "delete",
			url : "/replies/" + rid,
			success: function(deleteResult,status,xhr) {
				if(callback){
					callback(deleteResult);
				}; 			
			}
		,
		error: function(xhr,status,er) {
			if(error){ error(er)};	
			}
		})			
	}
	
	//댓글 수정
	function update(reply, callback, error) {		
		console.log("rid: " + reply.rid);		
		$.ajax({
			type: "put",
			url: "/replies/" + reply.rid,
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			success: function(result,status,xhr) {
				if(callback){
					callback(result);
				}			
			}
		    ,
		    error: function(xhr,status,er) {
				if(error){error(er)};
			}					
		})		
	}
	
	//댓글 날짜(rdate) 수정하기
	function displayTime(timevalue) {
		var today = new Date();
		var gap = today.getTime() - timevalue;
		var dateObj = new Date(timevalue);
		var str ="";
		if (gap < (1000 * 60 * 60 * 24)) { //댓글이 하루안이면
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi,
					':', (ss > 9 ? '' : '0') + ss ].join('');

		} else { //댓글이 하루 지났으면
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
			var dd = dateObj.getDate();
			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
					(dd > 9 ? '' : '0') + dd ].join('');
		}
		
	}


	return {
		getList : getList,
		get : get,
		add : add,
		remove : remove,
		update : update,
	    displayTime : displayTime
	}; 
})();
