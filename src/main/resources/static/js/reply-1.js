/*************************************************************
파일명: reply-1.js
기능: jquery 응답안할시 script 포함된지 확인
작성자: 진영서
*************************************************************/

console.log("reply-1");
if (typeof jQuery == 'undefined') {
    var script = document.createElement('script');
    script.type = "text/javascript";
    script.src = "https://code.jquery.com/jquery-3.5.1.min.js";
    document.getElementsByTagName('head')[0].appendChild(script);
} else {
    console.log("jQuery Ready");
}
