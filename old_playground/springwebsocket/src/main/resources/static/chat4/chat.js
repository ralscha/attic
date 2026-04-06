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

	// Connect to the WebSocket server!
	var socket = new WebSocket("ws://"+window.location.host+"/chat4");

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

			// Get the raw data and create an image element.
			var blob = event.data;

			window.URL = window.URL || window.webkitURL;
			var source = window.URL.createObjectURL(blob);

			var image = document.createElement("img");
			image.src = source;
			image.alt = "Image generated from blob";

			document.body.appendChild(image);
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

			//try to open a new connection
			socket = new WebSocket("ws://localhost:8080/chat4");
		}
	}

	/**
	 * WebSocket onerror event.
	 */
	socket.onerror = function(event) {
		label.innerHTML = "Error: " + event;
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
	 * Handle the drop event.
	 */
	document.ondrop = function(event) {
		var file = event.dataTransfer.files[0];
		socket.send(file);

		return false;
	}

	/**
	 * Prevent the default behaviour of the dragover event.
	 */
	document.ondragover = function(event) {
		event.preventDefault();
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