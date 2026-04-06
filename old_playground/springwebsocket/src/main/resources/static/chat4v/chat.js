/**
 * Executed when the page has finished loading.
 */
window.onload = function() {

	// Create a reference for the required DOM elements.
	var nameView = document.getElementById("name-view");
	var textView = document.getElementById("text-view");
	var buttonSend = document.getElementById("send-button");
	var buttonStop = document.getElementById("stop-button");
	var label = document.getElementById("status-label");
	var chatArea = document.getElementById("chat-area");
	var buttonVideo = document.getElementById("video-button");
	var video = document.getElementById("video");

	// Connect to the WebSocket server!
	var socket = new WebSocket("ws://" + window.location.host + "/chat4");

	/**
	 * WebSocket onopen event.
	 */
	socket.onopen = function(event) {
		label.innerHTML = "Connection open";
	}

	/**
	 * WebSocket onmessage event.
	 */
	socket.onmessage = function(event) {
		if (typeof event.data === "string") {

			// Create a JSON object.
			var jsonObject = JSON.parse(event.data);

			// Extract the values for each key.
			var userName = jsonObject.name;
			var userMessage = jsonObject.message;

			// Display message.
			chatArea.innerHTML = chatArea.innerHTML + "<p>" + userName + " says: <strong>" + userMessage + "</strong>" + "</p>";

			// Scroll to bottom.
			chatArea.scrollTop = chatArea.scrollHeight;
		} else if (event.data instanceof Blob) {

			// 1. Get the raw data.
			var blob = event.data;

			// 2. Create a new URL for the blob object.
			window.URL = window.URL || window.webkitURL;

			var source = window.URL.createObjectURL(blob);

			// 3. Update the image source.
			video.src = source;

			// 4. Release the allocated memory.
			window.URL.revokeObjectURL(source);
		}
	}

	/**
	 * WebSocket onclose event.
	 */
	socket.onclose = function(event) {
		var code = event.code;
		var reason = event.reason;
		var wasClean = event.wasClean;

		if (wasClean) {
			label.innerHTML = "Connection closed normally.";
		} else {
			label.innerHTML = "Connection closed with message: " + reason + " (Code: " + code + ")";
		}
	}

	/**
	 * WebSocket onerror event.
	 */
	socket.onerror = function(event) {
		label.innerHTML = "Error: " + event;
	}

	buttonVideo.onclick = function(event) {
		if (socket.readyState == WebSocket.OPEN) {
			socket.send("get-video");
		}
	}

	/**
	 * Disconnect and close the connection.
	 */
	buttonStop.onclick = function(event) {
		if (socket.readyState == WebSocket.OPEN) {
			socket.close();
		}
	}

	/**
	 * Send the message and empty the text field.
	 */
	buttonSend.onclick = function(event) {
		sendText();
	}

	/**
	 * Send the message and empty the text field.
	 */
	textView.onkeypress = function(event) {
		if (event.keyCode == 13) {
			sendText();
		}
	}

	/**
	 * Send a text message using WebSocket.
	 */
	function sendText() {
		if (socket.readyState == WebSocket.OPEN) {
			var msg = {
				name: nameView.value,
				message: textView.value
			};
			socket.send(JSON.stringify(msg));
			textView.value = "";
		}
	}
}