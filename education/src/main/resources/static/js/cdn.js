$.ajaxSetup({cache: false});
function bToM(value){
    if(value){
        var sizel = value / 1048576.0;
        var sizes = sizel.toFixed(2);
        return sizes;
    }
    return '';
}
function sToTime(value){
	if(value){
		var h = value/3600;
		var m = value%3600/60;
		var s = value%60;
		h = Math.floor(h);
        m = Math.floor(m);
        s = Math.floor(s);
		return (h<10?'0'+h:h)+":"+(m<10?'0'+m:m)+":"+(s<10?'0'+s:s);
	}
	return "00:00:00";
}
var ctx = '';

function openNewWindow(url, params){
	if(params){
		for(var el in params){
			if(url.indexOf('?')!= -1){
				url = url + '&';
			}else{
				url = url + '?';
			}
			url = url + el + '=' + params[el];
		}
	}
	window.open(url, "_blank");
}