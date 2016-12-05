;!function(pkg, undefined){
	var STATE = 'x-back';
	var element;
	var popCount = 0;

	var onPopState = function(event){
		var isDoAdd = sessionStorage.getItem('add');
		if(isDoAdd && isDoAdd == '1') {
			WeixinJSBridge.call('closeWindow');
		}
	    popCount ++;
		record(STATE);
		if(popCount > 1 && event.state == STATE) {
		  WeixinJSBridge.call('closeWindow');
		}
		event.state === STATE && fire();
	}

	var record = function(state){
		history.pushState(state, null, location.href);
	}

	var fire = function(){
		var event = document.createEvent('Events');
		event.initEvent(STATE, false, false);
		element.dispatchEvent(event);
	}

	var listen = function(listener){
		element.addEventListener(STATE, listener, false);
	}

	!function(){
		element = document.createElement('span');
		window.addEventListener('popstate', onPopState);
		this.listen = listen;
		record(STATE);
	}.call(window[pkg] = window[pkg] || {});

}('XBack');