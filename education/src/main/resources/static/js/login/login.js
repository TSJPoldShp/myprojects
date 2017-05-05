window.onload = function() {
		document.getElementById('shadow').style.visibility = 'visible';
		var user = document.getElementById('userId');
		if (user.value.length <= 0)
			user.focus();
		else
			document.getElementById('pass').focus();
	}
