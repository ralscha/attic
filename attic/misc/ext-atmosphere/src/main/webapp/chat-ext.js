Ext.onReady(function() {
    "use strict";

    var content = Ext.get('content');
    var input = Ext.get('input');
    var status = Ext.get('status');
    var myName = false;
    var author = null;
    var logged = false;

    var request = { url: 'chat',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'sse' ,
                    fallbackTransport: 'long-polling'};

    request.onOpen = function(response) {
        content.setHTML('<p>Atmosphere connected using ' + response.transport + '</p>');
        input.set({disabled: ''}, false);
        input.focus();
        status.setHTML('Choose name:');
    };

    request.onReconnect = function (request, response) {
    	if (window.console) {
    		console.log("Reconnecting");
    	}
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = Ext.decode(message);
        } catch (e) {
        	if (window.console) {
        		console.log('This doesn\'t look like a valid JSON: ', message.data);
        	}
            return;
        }

        if (!logged) {
            logged = true;
            status.setHTML(myName + ': ').setStyle('color', 'blue');
            input.set({disabled: ''}, false);
            input.focus();
        } else {
        	input.set({disabled: ''}, false);

            var me = json.author == author;
            var date =  typeof(json.time) == 'string' ? parseInt(json.time) : json.time;
            addMessage(json.author, json.text, me ? 'blue' : 'black', new Date(date));
        }
    };

    request.onClose = function(response) {
        logged = false;
    };

    request.onError = function(response) {
        content.setHTML('<p>Sorry, but there\'s some problem with your '
            + 'socket or the server is down</p>' );
    };

    var subSocket = Ext.ux.Atmosphere.subscribe(request);

    input.on('keydown', function(e, t) {
        if (e.keyCode === 13) {
            var msg = Ext.get(t).getValue();

            // First message is always the author's name
            if (author == null) {
                author = msg;
            }

            subSocket.push(Ext.encode({ author: author, message: msg }));
            Ext.get(t).dom.value = '';

            input.set({disabled: 'disabled'}, false);
            if (myName === false) {
                myName = msg;
            }
        }
    });

    function addMessage(author, message, color, datetime) {
        content.createChild('<p><span style="color:' + color + '">' + author + '</span> @ ' +
            + (datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours()) + ':'
            + (datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes())
            + ': ' + message + '</p>');
    }
});

