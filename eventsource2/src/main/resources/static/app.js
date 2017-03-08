function initialize() {

	var eventSource = new EventSource('simplesse');

	eventSource.addEventListener('message', function(e) {
		var msg = JSON.parse(e.data);		
		document.getElementById("timestamp").innerHTML = new Date(e.timeStamp);
		document.getElementById("heap").innerHTML = msg.heap
		document.getElementById("nonheap").innerHTML = msg.nonheap
	}, false);

	eventSource.addEventListener('open', function(e) {
		console.log('open');
	}, false);

	eventSource.addEventListener('error', function(e) {
		if (e.readyState == EventSource.CLOSED) {
			console.log('close');
		}
	}, false);

}

window.onload = initialize;