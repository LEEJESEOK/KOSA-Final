

function ajaxError(request, status, error) {
    let message = "code : " + request + "\n";
    message += "message : " + request.responseText + "\n";
    message += "error : " + error;

    console.log(message);
}

/**
 * 콤마를 찍는다.
 */
var addComma = function (price) {

    price = price + '';
    var num = price.split('.');

    if (num[0] == 0) return price;

    var num1 = num[0];
    var reg = /(^[+-]?\d+)(\d{3})/;
    var num1 = (num1 + '');

    while (reg.test(num1)) num1 = num1.replace(reg, '$1' + ',' + '$2');
    if (num.length > 1) {
        return num1 + "." + num[1];
    }
    return num1;
};