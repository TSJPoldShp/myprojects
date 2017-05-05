/*
 * 多选带回
 */
/**
 * dialog关闭事件
 * @param bringNames 数组,需bring的input->name
 */
function bringClose(bringNames) {
	for (var i = 0, len = bringNames.length; i < len; i++) {
		$('.bring_' + bringNames[i]).val($('.' + bringNames[i]).val());
	}
	return true;
}
/**
 * 页面加载事件
 * @param bringName 需匹配的input->name
 * @param checkedName 需匹配的checkbox->name
 * @param separator 分隔符
 */
function load(bringName, checkedName, separator) {
	var values = $('.bring_' + bringName).val();
	if (values != null && values != '' && values != 'undefined') {
		var count = 0;
		var arrs = new Array();
		var items = $('.' + checkedName);
		arrs = values.split(separator);
		for (var i = 0, len = arrs.length; i < len; i++) {
			for (var j = 0, itemLen = items.length; j < itemLen; j++) {
				if (arrs[i] == items[j].value) {
					items[j].checked = true;
					count++;
				}
			}
		}
		if (count == items.length) {
			$('#checkAll').attr('checked', true);
		}
	}
}
/**
 * bring隐藏域
 * @param params 二维数组,[['需bring的input->name','需bring的value','分隔符'],['','','']]
 * @param flag 是否选中
 */
function bring(params, flag) {
	for (var i = 0; i < params.length; i++) {
		var param = params[i];
		var value = '';
		if (flag) {
			value = $('.bring_' + param[0]).val();
			value = value + param[2] + param[1];
			if (value.startsWith(param[2])) {
				value = value.substring(1);
			}
			$('.bring_' + param[0]).val(value);
		} else {
			value = param[2] + $('.bring_' + param[0]).val() + param[2];
			var str = param[2] + param[1] + param[2];
			if (value.indexOf(str) != -1) {
				value = value.replace(str, param[2]);
			}
			if (value == param[2]) {
				value = '';
			} else {
				value = value.substring(1, value.length - 1);
			}
			$('.bring_' + param[0]).val(value);
		}
	}
}
/**
 * 选择带回按钮事件
 * @param bringNames 数组,需bring的input->name
 */
function bringBack(bringNames) {
	for (var i = 0, len = bringNames.length; i < len; i++) {
		$('.' + bringNames[i]).val($('.bring_' + bringNames[i]).val());
	}
}
function checkAll(checkedName, flag) {
	$('.' + checkedName).each(function(i, t){
		if(flag) {
			if(!t.checked) {
				$(t).click();
			}
		} else {
			if(t.checked) {
				$(t).click();
			}
		}
	});
}