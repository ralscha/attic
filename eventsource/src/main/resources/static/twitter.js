function initialize() {

	eventSource = new EventSource('twittersse');

	eventSource.onmessage = function(event) {
		console.dir(event);
		var feeds = event.data.toString();
		//console.log(feeds);
		var theNewParagraph = document.createElement('p');

		var theBreak = document.createElement('br');
		theNewParagraph.setAttribute('title', 'The feeds');
		var data = JSON.parse(feeds);
		var theText;

		var divTag;
		var tweetInfo;

		// Before we continue we check that we got data
		if (data !== undefined) {
			for ( var k = 0; k < data.length; k++) {
				tweetInfo = data[k];

				divTag = document.createElement("div");
				divTag.id = "div" + k;
				divTag.setAttribute("align", "left");
				divTag.style.margin = "0px auto";
				divTag.className = "outerDiv";

				divTag.innerHTML = tweetInfo.text + ' Created on ' + (new Date(tweetInfo.createdAt)).toUTCString();

				document.body.appendChild(divTag);
				document.body.appendChild(theBreak);

			}

		}
		document.body.appendChild(theNewParagraph);

	};

}

window.onload = initialize;