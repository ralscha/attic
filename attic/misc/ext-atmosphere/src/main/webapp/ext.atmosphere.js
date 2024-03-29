Ext.define('Ext.ux.Atmosphere', function() {

	var version = "1.0";
	var requests = [];
	var callbacks = [];
	
	return {
		constructor: function() {
			Ext.EventManager.onWindowUnload(function() {
				Ext.ux.Atmosphere.unsubscribe();
			});
		},
		
		singleton: true,

		onError: function(response) {
		},
		onClose: function(response) {
		},
		onOpen: function(response) {
		},
		onMessage: function(response) {
		},
		onReconnect: function(request, response) {
		},
		onMessagePublished: function(response) {
		},
		onTransportFailure: function(response) {
		},
		onLocalMessage: function(response) {
		},

		AtmosphereRequest : function(options) {

			/**
			 * {Object} Request parameters.
			 * 
			 * @private
			 */
			var _request = {
				timeout: 300000,
				method: 'GET',
				headers: {},
				contentType: '',
				callback: null,
				url: '',
				data: '',
				suspend: true,
				maxRequest: 60,
				reconnect: true,
				maxStreamingLength: 10000000,
				lastIndex: 0,
				logLevel: 'info',
				requestCount: 0,
				fallbackMethod: 'GET',
				fallbackTransport: 'streaming',
				transport: 'long-polling',
				webSocketImpl: null,
				webSocketUrl: null,
				webSocketPathDelimiter: "@@",
				enableXDR: false,
				rewriteURL: false,
				attachHeadersAsQueryString: true,
				executeCallbackBeforeReconnect: false,
				readyState: 0,
				lastTimestamp: 0,
				withCredentials: false,
				trackMessageLength: false,
				messageDelimiter: '|',
				connectTimeout: -1,
				reconnectInterval: 0,
				dropAtmosphereHeaders: true,
				uuid: 0,
				shared: false,
				readResponsesHeaders: true,
				onError: function(response) {
				},
				onClose: function(response) {
				},
				onOpen: function(response) {
				},
				onMessage: function(response) {
				},
				onReconnect: function(request, response) {
				},
				onMessagePublished: function(response) {
				},
				onTransportFailure: function(reason, request) {
				},
				onLocalMessage: function(request) {
				}
			};

			/**
			 * {Object} Request's last response.
			 * 
			 * @private
			 */
			var _response = {
				status: 200,
				responseBody: '',
				expectedBodySize: -1,
				headers: [],
				state: "messageReceived",
				transport: "polling",
				error: null,
				request: null,
				id: 0
			};

			/**
			 * {websocket} Opened web socket.
			 * 
			 * @private
			 */
			var _websocket = null;

			/**
			 * {SSE} Opened SSE.
			 * 
			 * @private
			 */
			var _sse = null;

			/**
			 * {XMLHttpRequest, ActiveXObject} Opened ajax request (in case of http-streaming or long-polling)
			 * 
			 * @private
			 */
			var _activeRequest = null;

			/**
			 * {Object} Object use for streaming with IE.
			 * 
			 * @private
			 */
			var _ieStream = null;

			/**
			 * {Object} Object use for jsonp transport.
			 * 
			 * @private
			 */
			var _jqxhr = null;

			/**
			 * {boolean} If request has been subscribed or not.
			 * 
			 * @private
			 */
			var _subscribed = true;

			/**
			 * {number} Number of test reconnection.
			 * 
			 * @private
			 */
			var _requestCount = 0;

			/**
			 * {boolean} If request is currently aborded.
			 * 
			 * @private
			 */
			var _abordingConnection = false;

			/**
			 * A local "channel' of communication.
			 * 
			 * @private
			 */
			var _localSocketF = null;

			/**
			 * The storage used.
			 * 
			 * @private
			 */
			var _storageService;

			/**
			 * Local communication
			 * 
			 * @private
			 */
			var _localStorageService = null;

			/**
			 * A Unique ID
			 * 
			 * @private
			 */
			var guid = Ext.Date.now();

			// Automatic call to subscribe
			_subscribe(options);

			/**
			 * Initialize atmosphere request object.
			 * 
			 * @private
			 */
			function _init() {
				_subscribed = true;
				_abordingConnection = false;
				_requestCount = 0;

				_websocket = null;
				_sse = null;
				_activeRequest = null;
				_ieStream = null;
			}

			/**
			 * Re-initialize atmosphere object.
			 * 
			 * @private
			 */
			function _reinit() {
				_clearState();
				_init();
			}

			/**
			 * Subscribe request using request transport. <br>
			 * If request is currently opened, this one will be closed.
			 * 
			 * @param {Object}
			 *            Request parameters.
			 * @private
			 */
			function _subscribe(options) {
				_reinit();

				_request = Ext.apply(_request, options);

			}

			/**
			 * Check if web socket is supported (check for custom implementation provided by request object or browser implementation).
			 * 
			 * @returns {boolean} True if web socket is supported, false otherwise.
			 * @private
			 */
			function _supportWebsocket() {
				return _request.webSocketImpl != null || window.WebSocket || window.MozWebSocket;
			}

			/**
			 * Check if server side events (SSE) is supported (check for custom implementation provided by request object or browser
			 * implementation).
			 * 
			 * @returns {boolean} True if web socket is supported, false otherwise.
			 * @private
			 */
			function _supportSSE() {
				return window.EventSource;
			}

			/**
			 * Open request using request transport. <br>
			 * If request transport is 'websocket' but websocket can't be opened, request will automatically reconnect using fallback transport.
			 * 
			 * @private
			 */
			function _execute() {
				// Shared across multiple tabs/windows.
				if (_request.shared && !Ext.isOpera) {
					//avoid duplicate local storage
					if(!!_localStorageService){
						return;
					}
					_localStorageService = _local(_request);
					if (_localStorageService != null) {
						if (_request.logLevel == 'debug') {
							Ext.log("Storage service available. All communication will be local");
						}

						if (_localStorageService.open(_request)) {
							// Local connection.
							return;
						}
					}

					if (_request.logLevel == 'debug') {
						Ext.log("No Storage service available.");
					}
					// Wasn't local or an error occurred
					_localStorageService = null;
				}

				if (_request.transport != 'websocket' && _request.transport != 'sse') {
					_open('opening', _request.transport, _request);
					_executeRequest();

				} else if (_request.transport == 'websocket') {
					if (!_supportWebsocket()) {
						_reconnectWithFallbackTransport("Websocket is not supported, using request.fallbackTransport ("
								+ _request.fallbackTransport + ")");
					} else {
						_executeWebSocket(false);
					}
				} else if (_request.transport == 'sse') {
					if (!_supportSSE()) {
						_reconnectWithFallbackTransport("Server Side Events(SSE) is not supported, using request.fallbackTransport ("
								+ _request.fallbackTransport + ")");
					} else {
						_executeSSE(false);
					}
				}
			}

			function _local(request) {
				var connector, orphan, name = "atmosphere-" + request.url, connectors = {
					storage: function() {
						if (!supportStorage()) {
							return;
						}

						var storage = window.localStorage, get = function(key) {
							return Ext.decode(storage.getItem(name + "-" + key));
						}, set = function(key, value) {
							storage.setItem(name + "-" + key, Ext.encode(value));
						};

						var onStorage = function(event) {
							if (event.key === name && event.newValue) {
								listener(event.newValue);
							}							
						};
						
						return {
							init: function() {
								set("children", get("children").concat([ guid ]));
								window.addEventListener("storage", onStorage, false);
								return get("opened");
							},
							signal: function(type, data) {
								storage.setItem(name, Ext.encode({
									target: "p",
									type: type,
									data: data
								}));
							},
							close: function() {
								var index, children = get("children");

								window.removeEventListener('storage', onStorage, false);
								if (children) {
									index = Ext.Array.indexOf(children, request.id);
									if (index > -1) {
										children.splice(index, 1);
										set("children", children);
									}
								}
							}						
						};
											
					},
					windowref: function() {
						var win = window.open("", name.replace(/\W/g, ""));

						if (!win || win.closed || !win.callbacks) {
							return;
						}

						return {
							init: function() {
								win.callbacks.push(listener);
								win.children.push(options.id);
								return win.opened;
							},
							signal: function(type, data) {
								if (!win.closed && win.fire) {
									win.fire(Ext.encode({
										target: "p",
										type: type,
										data: data
									}));
								}
							},
							close: function() {
								function remove(array, e) {
									var index = Ext.Array.indexOf(array, e);
									if (index > -1) {
										array.splice(index, 1);
									}
								}

								// Removes traces only if the parent is alive
								if (!orphan) {
									remove(win.callbacks, listener);
									remove(win.children, options.id);
								}
							}

						};
					}
				};

				// Receives open, close and message command from the parent
				function listener(string) {
					var command = Ext.decode(string), data = command.data;

					if (command.target === "c") {
						switch (command.type) {
						case "open":
							_open("opening", 'local', _request);
							break;
						case "close":
							orphan = true;
							if (data.reason === "aborted") {
								_close();
							} else {
								_prepareCallback("", "closed", 200, _request.transport);
								// Gives the heir some time to reconnect
								if (data.heir === guid) {
									_close();
								} else {
									setTimeout(function() {
										_close();
									}, 100);
								}
							}
							break;
						case "message":
							_prepareCallback(data, "messageReceived", 200, request.transport);
							break;
						case "localMessage":
							_localMessage(data);
							break;
						}
					}
				}

				// Finds the parent socket's traces from the cookie
				if (!new RegExp("(?:^|; )(" + encodeURIComponent(name) + ")=([^;]*)").test(document.cookie)) {
					return;
				}

				// Chooses a connector
				connector = connectors.storage() || connectors.windowref();
				if (!connector) {
					return;
				}

				return {
					open: function() {
						var parentOpened;

						parentOpened = connector.init();
						if (parentOpened) {
							// Firing the open event without delay robs the user of the opportunity to bind connecting event handlers
							setTimeout(function() {
								_open("opening", 'local', request);
							}, 50);
						}
						return parentOpened;
					},
					send: function(event) {
						connector.signal("send", event);
					},
					localSend: function(event) {
						connector.signal("localSend", Ext.encode({
							id: guid,
							event: event
						}));
					},
					close: function() {
						// Do not signal the parent if this method is executed by the unload event handler
						if (!_abordingConnection) {
							connector.signal("close");
							connector.close();
						}
					}
				};
			}

			function share() {
				var storageService, name = "atmosphere-" + _request.url, servers = {
					// Powered by the storage event and the localStorage
					// http://www.w3.org/TR/webstorage/#event-storage
					storage: function() {
						if (!supportStorage()) {
							return;
						}

						var storage = window.localStorage;

						var onStorage = function(event) {
							// When a deletion, newValue initialized to null
							if (event.key === name && event.newValue) {
								listener(event.newValue);
							}
						};
						
						return {
							init: function() {
								window.addEventListener("storage", onStorage, false);
							},
							signal: function(type, data) {
								storage.setItem(name, Ext.encode({
									target: "c",
									type: type,
									data: data
								}));
							},
							get: function(key) {
								return Ext.decode(storage.getItem(name + "-" + key));
							},
							set: function(key, value) {
								storage.setItem(name + "-" + key, Ext.encode(value));
							},
							close: function() {
								window.removeEventListener('storage', onStorage, false);
								storage.removeItem(name);
								storage.removeItem(name + "-opened");
								storage.removeItem(name + "-children");
							}

						};
					
					},
					// Powered by the window.open method
					// https://developer.mozilla.org/en/DOM/window.open
					windowref: function() {
						// Internet Explorer raises an invalid argument error
						// when calling the window.open method with the name containing non-word characters
						var neim = name.replace(/\W/g, "");
						var iframe = Ext.select("iframe[name=" + neim + "]");
						var win;
						if (iframe) {
							win = iframe.first().dom.contentWindow;
						} else {
							var d = document.createElement('iframe');
							var e = new Ext.dom.Element(d);
							e.set({name: neim});
							e.appendTo(Ext.getBody());
							e.hide();
							win = e.dom.contentWindow;
						}

						return {
							init: function() {
								// Callbacks from different windows
								win.callbacks = [ listener ];
								// In IE 8 and less, only string argument can be safely passed to the function in other window
								win.fire = function(string) {
									var i;

									for (i = 0; i < win.callbacks.length; i++) {
										win.callbacks[i](string);
									}
								};
							},
							signal: function(type, data) {
								if (!win.closed && win.fire) {
									win.fire(Ext.encode({
										target: "c",
										type: type,
										data: data
									}));
								}
							},
							get: function(key) {
								return !win.closed ? win[key] : null;
							},
							set: function(key, value) {
								if (!win.closed) {
									win[key] = value;
								}
							},
							close: function() {
							}
						};
					}
				};

				// Receives send and close command from the children
				function listener(string) {
					var command = Ext.decode(string), data = command.data;

					if (command.target === "p") {
						switch (command.type) {
						case "send":
							_push(data);
							break;
						case "localSend":
							_localMessage(data);
							break;
						case "close":
							_close();
							break;
						}
					}
				}

				_localSocketF = function propagateMessageEvent(context) {
					storageService.signal("message", context);
				};

				// Leaves traces
				document.cookie = encodeURIComponent(name) + "=" + Ext.Date.now();

				// Chooses a storageService
				storageService = servers.storage() || servers.windowref();
				storageService.init();

				if (_request.logLevel == 'debug') {
					Ext.log("Installed StorageService " + storageService);
				}

				// List of children sockets
				storageService.set("children", []);

				if (storageService.get("opened") != null && !storageService.get("opened")) {
					// Flag indicating the parent socket is opened
					storageService.set("opened", false);
				}

				_storageService = storageService;
			}

			/**
			 * @private
			 */
			function _open(state, transport, request) {
				if (_request.shared && transport != 'local' && !Ext.isOpera) {
					share();
				}

				if (_storageService != null) {
					_storageService.set("opened", true);
				}

				request.close = function() {
					_close();
					request.reconnect = false;
				};

				_response.request = request;
				var prevState = _response.state;
				_response.state = state;
				_response.status = 200;
				var prevTransport = _response.transport;
				_response.transport = transport;
				_invokeCallback();
				_response.state = prevState;
				_response.transport = prevTransport;
			}

			/**
			 * Execute request using jsonp transport.
			 * 
			 * @param request
			 *            {Object} request Request parameters, if undefined _request object will be used.
			 * @private
			 */
			function _jsonp(request) {
				var rq = _request;
				if ((request != null) && (typeof (request) != 'undefined')) {
					rq = request;
				}

				var url = rq.url;
				var data = rq.data;
				if (rq.attachHeadersAsQueryString) {
					url = _attachHeaders(rq);
					if (data != '') {
						url += "&X-Atmosphere-Post-Body=" + data;
					}
					data = '';
				}

				_jqxhr = jQuery.ajax({
					url: url,
					type: rq.method,
					dataType: "jsonp",
					error: function(jqXHR, textStatus, errorThrown) {
						if (jqXHR.status < 300 && rq.requestCount++ < rq.maxRequest) {
							_reconnect(_jqxhr, rq);
						} else {
							_prepareCallback(textStatus, "error", jqXHR.status, rq.transport);
						}
					},
					jsonp: "jsonpTransport",
					success: function(json) {

						if (rq.requestCount++ < rq.maxRequest) {
							if (!rq.executeCallbackBeforeReconnect) {
								_reconnect(_jqxhr, rq);
							}

							var msg = json.message;
							if (msg != null && typeof msg != 'string') {
								try {
									msg = Ext.encode(msg);
								} catch (err) {
									// The message was partial
								}
							}

							_prepareCallback(msg, "messageReceived", 200, rq.transport);

							if (rq.executeCallbackBeforeReconnect) {
								_reconnect(_jqxhr, rq);
							}
						} else {
							Ext.log({ level: _request.logLevel }, "JSONP reconnect maximum try reached " + _request.requestCount);
							_onError();
						}
					},
					data: rq.data,
					beforeSend: function(jqXHR) {
						_doRequest(jqXHR, rq, false);
					}
				});
			}

			/**
			 * Execute request using ajax transport.
			 * 
			 * @param request
			 *            {Object} request Request parameters, if undefined _request object will be used.
			 * @private
			 */
			function _ajax(request) {
				var rq = _request;
				if ((request != null) && (typeof (request) != 'undefined')) {
					rq = request;
				}

				var url = rq.url;
				var data = rq.data;
				if (rq.attachHeadersAsQueryString) {
					url = _attachHeaders(rq);
					if (data != '') {
						url += "&X-Atmosphere-Post-Body=" + data;
					}
					data = '';
				}
				
				// Prevent Android to cache request
				var url = _attachHeaders(rq);
				url = prepareURL(url);

				var headers = {};
				if (!_request.dropAtmosphereHeaders) {
					headers["X-Atmosphere-Framework"] = version;
					headers["X-Atmosphere-Transport"] = rq.transport;
					if (rq.lastTimestamp != undefined) {
						headers["X-Cache-Date"] = rq.lastTimestamp;
					} else {
						headers["X-Cache-Date"] = 0;
					}

					if (rq.trackMessageLength) {
						headers["X-Atmosphere-TrackMessageSize"] = "true";
					}

					if (rq.contentType != '') {
						headers["Content-Type"] = rq.contentType;
					}
					headers["X-Atmosphere-tracking-id"] = rq.uuid;
				}
				Ext.iterate(rq.headers, function(name, value) {
					var h = Ext.isFunction(value) ? value.call(this, ajaxRequest, rq, create, _response) : value;
					if (h != null) {
						headers[name] = h;
					}
				});

				Ext.Ajax.request({
				    url: url,
				    method: rq.method,
				    data: rq.data,
				    headers: headers,
				    withCredentials: _request.withCredentials,
				    success: function(xhr, opts) {
				    	_jqxhr = xhr;
						if (rq.requestCount++ < rq.maxRequest) {
							if (!rq.executeCallbackBeforeReconnect) {
								_reconnect(_jqxhr, rq);
							}

							_prepareCallback(_jqxhr.responseText, "messageReceived", 200, rq.transport);

							if (rq.executeCallbackBeforeReconnect) {
								_reconnect(_jqxhr, rq);
							}
						} else {
							Ext.log({ level: _request.logLevel }, "AJAX reconnect maximum try reached " + _request.requestCount);
							_onError();
						}
				    },
				    failure: function(xhr, opts) {
				    	_jqxhr = xhr;
						if (_jqxhr.status < 300 && rq.requestCount++ < rq.maxRequest) {
							_reconnect(_jqxhr, rq);
						} else {
							_prepareCallback(_jqxhr.statusText, "error", _jqxhr.status, rq.transport);
						}
				    }
				});
				
			}

			/**
			 * Build websocket object.
			 * 
			 * @param location
			 *            {string} Web socket url.
			 * @returns {websocket} Web socket object.
			 * @private
			 */
			function _getWebSocket(location) {
				if (_request.webSocketImpl != null) {
					return _request.webSocketImpl;
				} else {
					if (window.WebSocket) {
						return new WebSocket(location);
					} else {
						return new MozWebSocket(location);
					}
				}
			}

			/**
			 * Build web socket url from request url.
			 * 
			 * @return {string} Web socket url (start with "ws" or "wss" for secure web socket).
			 * @private
			 */
			function _buildWebSocketUrl() {
				var url = _attachHeaders(_request);
				return decodeURI(_createDummyElement('<a href="' + url + '"/>').href.replace(/^http/, "ws"));
			}

			function _createDummyElement(html) {
				var div = document.createElement('div');
				div.innerHTML = html;  
				return div.children[0];
			}
			
			/**
			 * Build SSE url from request url.
			 * 
			 * @return a url with Atmosphere's headers
			 * @private
			 */
			function _buildSSEUrl() {
				var url = _attachHeaders(_request);
				return url;
			}

			/**
			 * Open SSE. <br>
			 * Automatically use fallback transport if SSE can't be opened.
			 * 
			 * @private
			 */
			function _executeSSE(sseOpened) {

				_response.transport = "sse";

				var location = _buildSSEUrl(_request.url);

				if (_request.logLevel == 'debug') {
					Ext.log("Invoking executeSSE");
					Ext.log("Using URL: " + location);
				}

				if (sseOpened) {
					_open('re-opening', "sse", _request);
				}

				if (!_request.reconnect) {
					if (_sse != null) {
						_sse.close();
					}
					return;
				}
				_sse = new EventSource(location, {
					withCredentials: _request.withCredentials
				});

				if (_request.connectTimeout > 0) {
					_request.id = setTimeout(function() {
						if (!sseOpened) {
							_sse.close();
						}
					}, _request.connectTimeout);
				}

				_sse.onopen = function(event) {
					if (_request.logLevel == 'debug') {
						Ext.log("SSE successfully opened");
					}

					if (!sseOpened) {
						_open('opening', "sse", _request);
					}
					sseOpened = true;

					if (_request.method == 'POST') {
						_response.state = "messageReceived";
						_sse.send(_request.data);
					}
				};

				_sse.onmessage = function(message) {
					if (message.origin != "http://" + window.location.host) {
						Ext.log({ level: _request.logLevel }, "Origin was not " + "http://" + window.location.host);
						return;
					}

					_response.state = 'messageReceived';
					_response.status = 200;

					var message = message.data;
					var skipCallbackInvocation = _trackMessageSize(message, _request, _response);

					if (Ext.String.trim(message).length == 0) {
						skipCallbackInvocation = true;
					}

					if (!skipCallbackInvocation) {
						_invokeCallback();
						_response.responseBody = '';
					}
				};

				_sse.onerror = function(message) {

					clearTimeout(_request.id);
					_response.state = 'closed';
					_response.responseBody = "";
					_response.status = !sseOpened ? 501 : 200;
					_invokeCallback();

					if (_abordingConnection) {
						Ext.log({ level: _request.logLevel }, "SSE closed normally");
					} else if (!sseOpened) {
						_reconnectWithFallbackTransport("SSE failed. Downgrading to fallback transport and resending");
					} else if (_request.reconnect && (_response.transport == 'sse')) {
						if (_requestCount++ < _request.maxRequest) {
							_request.requestCount = _requestCount;
							_response.responseBody = "";
							_executeSSE(true);
						} else {
							_sse.close();
							Ext.log({ level: _request.logLevel }, "SSE reconnect maximum try reached " + _request.requestCount);
							_onError();
						}
					}
				};
			}

			/**
			 * Open web socket. <br>
			 * Automatically use fallback transport if web socket can't be opened.
			 * 
			 * @private
			 */
			function _executeWebSocket(webSocketOpened) {

				_response.transport = "websocket";

				var location = _buildWebSocketUrl(_request.url);
				var closed = false;

				if (_request.logLevel == 'debug') {
					Ext.log("Invoking executeWebSocket");
					Ext.log("Using URL: " + location);
				}

				if (webSocketOpened) {
					_open('re-opening', "websocket", _request);
				}

				if (!_request.reconnect) {
					if (_websocket != null) {
						_websocket.close();
					}
					return;
				}

				_websocket = _getWebSocket(location);

				if (_request.connectTimeout > 0) {
					_request.id = setTimeout(function() {
						if (!webSocketOpened) {
							var _message = {
								code: 1002,
								reason: "",
								wasClean: false
							};
							_websocket.onclose(_message);
							// Close it anyway
							try {
								_websocket.close();
							} catch (e) {
							}
						}
					}, _request.connectTimeout);
				}

				_websocket.onopen = function(message) {
					if (_request.logLevel == 'debug') {
						Ext.log("Websocket successfully opened");
					}

					if (!webSocketOpened) {
						_open('opening', "websocket", _request);
					}

					webSocketOpened = true;

					if (_request.method == 'POST') {
						_response.state = "messageReceived";
						_websocket.send(_request.data);
					}
				};

				_websocket.onmessage = function(message) {
					if (message.data.indexOf("parent.callback") != -1) {
						Ext.log({ level: _request.logLevel }, "parent.callback no longer supported with 0.8 version and up. Please upgrade");
					}

					_response.state = 'messageReceived';
					_response.status = 200;

					var message = message.data;
					var skipCallbackInvocation = _trackMessageSize(message, _request, _response);

					if (!skipCallbackInvocation) {
						_invokeCallback();
						_response.responseBody = '';
					}
				};

				_websocket.onerror = function(message) {
					clearTimeout(_request.id);
				};

				_websocket.onclose = function(message) {
					if (closed) return;

					var reason = message.reason;
					if (reason === "") {
						switch (message.code) {
						case 1000:
							reason = "Normal closure; the connection successfully completed whatever purpose for which " + "it was created.";
							break;
						case 1001:
							reason = "The endpoint is going away, either because of a server failure or because the "
									+ "browser is navigating away from the page that opened the connection.";
							break;
						case 1002:
							reason = "The endpoint is terminating the connection due to a protocol error.";
							break;
						case 1003:
							reason = "The connection is being terminated because the endpoint received data of a type it "
									+ "cannot accept (for example, a text-only endpoint received binary data).";
							break;
						case 1004:
							reason = "The endpoint is terminating the connection because a data frame was received that " + "is too large.";
							break;
						case 1005:
							reason = "Unknown: no status code was provided even though one was expected.";
							break;
						case 1006:
							reason = "Connection was closed abnormally (that is, with no close frame being sent).";
							break;
						}
					}

					Ext.log({ level: 'warn' }, "Websocket closed, reason: " + reason);
					Ext.log({ level: 'warn' }, "Websocket closed, wasClean: " + message.wasClean);

					_response.state = 'closed';
					_response.responseBody = "";
					_response.status = !webSocketOpened ? 501 : 200;
					_invokeCallback();
					clearTimeout(_request.id);

					closed = true;

					if (_abordingConnection) {						 
						Ext.log({ level: _request.logLevel }, "Websocket closed normally");

					} else if (!webSocketOpened) {
						_reconnectWithFallbackTransport("Websocket failed. Downgrading to Comet and resending");

					} else if (_request.reconnect && _response.transport == 'websocket') {
						if (_request.reconnect && _requestCount++ < _request.maxRequest) {
							_request.requestCount = _requestCount;
							_response.responseBody = "";
							_executeWebSocket(true);
						} else {
							Ext.log({ level: _request.logLevel }, "Websocket reconnect maximum try reached " + _request.requestCount);
							Ext.log({ level: 'warn' }, "Websocket error, reason: " + message.reason);
							_onError();
						}
					}
				};
			}

			function _onError() {
				_response.state = 'error';
				_response.responseBody = "";
				_response.status = 500;
				_invokeCallback();
			}

			/**
			 * Track received message and make sure callbacks/functions are only invoked when the complete message has been received.
			 * 
			 * @param message
			 * @param request
			 * @param response
			 */
			function _trackMessageSize(message, request, response) {
				if (request.trackMessageLength) {
					// The message length is the included within the message
					var messageStart = message.indexOf(request.messageDelimiter);

					var length = response.expectedBodySize;
					if (messageStart != -1) {
						length = message.substring(0, messageStart);
						message = message.substring(messageStart + 1);
						response.expectedBodySize = length;
					}

					if (messageStart != -1) {
						response.responseBody = message;
					} else {
						response.responseBody += message;
					}

					if (response.responseBody.length != length) {
						return true;
					}
				} else {
					response.responseBody = message;
				}
				return false;
			}

			/**
			 * Reconnect request with fallback transport. <br>
			 * Used in case websocket can't be opened.
			 * 
			 * @private
			 */
			function _reconnectWithFallbackTransport(errorMessage) {
				Ext.log({ level: _request.logLevel }, errorMessage);

				if (typeof (_request.onTransportFailure) != 'undefined') {
					_request.onTransportFailure(errorMessage, _request);
				} else if (typeof (Ext.ux.Atmosphere.onTransportFailure) != 'undefined') {
					Ext.ux.Atmosphere.onTransportFailure(errorMessage, _request);
				}

				_request.transport = _request.fallbackTransport;
				if (_request.reconnect && _request.transport != 'none' || _request.transport == null) {
					_request.method = _request.fallbackMethod;
					_response.transport = _request.fallbackTransport;
					_request.id = setTimeout(function() {
						_execute();
					}, _request.reconnectInterval);
				}
			}

			/**
			 * Get url from request and attach headers to it.
			 * 
			 * @param request
			 *            {Object} request Request parameters, if undefined _request object will be used.
			 * 
			 * @returns {Object} Request object, if undefined, _request object will be used.
			 * @private
			 */
			function _attachHeaders(request) {
				var rq = _request;
				if ((request != null) && (typeof (request) != 'undefined')) {
					rq = request;
				}

				var url = rq.url;

				// If not enabled
				if (!rq.attachHeadersAsQueryString)
					return url;

				// If already added
				if (url.indexOf("X-Atmosphere-Framework") != -1) {
					return url;
				}

				url += (url.indexOf('?') != -1) ? '&' : '?';
				url += "X-Atmosphere-tracking-id=" + rq.uuid;
				url += "&X-Atmosphere-Framework=" + version;
				url += "&X-Atmosphere-Transport=" + rq.transport;

				if (rq.trackMessageLength) {
					url += "&X-Atmosphere-TrackMessageSize=" + "true";
				}

				if (rq.lastTimestamp != undefined) {
					url += "&X-Cache-Date=" + rq.lastTimestamp;
				} else {
					url += "&X-Cache-Date=" + 0;
				}

				if (rq.contentType != '') {
					url += "&Content-Type=" + rq.contentType;
				}

				Ext.iterate(rq.headers, function(name, value) {
					var h = Ext.isFunction(value) ? value.call(this, rq, request, _response) : value;
					if (h != null) {
						url += "&" + encodeURIComponent(name) + "=" + encodeURIComponent(h);
					}
				});

				return url;
			}

			/**
			 * Build ajax request. <br>
			 * Ajax Request is an XMLHttpRequest object, except for IE6 where ajax request is an ActiveXObject.
			 * 
			 * @return {XMLHttpRequest, ActiveXObject} Ajax request.
			 * @private
			 */
			function _buildAjaxRequest() {
				var ajaxRequest;
				if (Ext.isIE) {
					var activexmodes = [ "Msxml2.XMLHTTP", "Microsoft.XMLHTTP" ];
					for ( var i = 0; i < activexmodes.length; i++) {
						try {
							ajaxRequest = new ActiveXObject(activexmodes[i]);
						} catch (e) {
						}
					}

				} else if (window.XMLHttpRequest) {
					ajaxRequest = new XMLHttpRequest();
				}
				return ajaxRequest;
			}

			/**
			 * Execute ajax request. <br>
			 * 
			 * @param request
			 *            {Object} request Request parameters, if undefined _request object will be used.
			 * @private
			 */
			function _executeRequest(request) {
				var rq = _request;
				if ((request != null) || (typeof (request) != 'undefined')) {
					rq = request;
				}

				//TODO
				// CORS fake using JSONP
				if ((rq.transport == 'jsonp') || ((rq.enableXDR) && (checkCORSSupport()))) {
					_jsonp(rq);
					return;
				}

				//TODO
				if (rq.transport == 'ajax') {
					_ajax(request);
					return;
				}

				if ((rq.transport == 'streaming') && (Ext.isIE)) {
					rq.enableXDR && window.XDomainRequest ? _ieXDR(rq) : _ieStreaming(rq);
					return;
				}

				if ((rq.enableXDR) && (window.XDomainRequest)) {
					_ieXDR(rq);
					return;
				}

				if (rq.reconnect && rq.requestCount++ < rq.maxRequest) {
					var ajaxRequest = _buildAjaxRequest();
					_doRequest(ajaxRequest, rq, true);

					if (rq.suspend) {
						_activeRequest = ajaxRequest;
					}

					if (rq.transport != 'polling') {
						_response.transport = rq.transport;
					}

					if (!Ext.isIE) {
						ajaxRequest.onerror = function() {
							try {
								_response.status = XMLHttpRequest.status;
							} catch (e) {
								_response.status = 404;
							}

							_response.state = "error";
							_reconnect(ajaxRequest, rq, true);
						};
					}

					ajaxRequest.onreadystatechange = function() {
						if (_abordingConnection) {
							return;
						}

						var skipCallbackInvocation = false;
						var update = false;

						// Remote server disconnected us, reconnect.
						if (rq.transport == 'streaming' && (rq.readyState > 2 && ajaxRequest.readyState == 4)) {

							rq.readyState = 0;
							rq.lastIndex = 0;

							_reconnect(ajaxRequest, rq, true);
							return;
						}

						rq.readyState = ajaxRequest.readyState;

						if (ajaxRequest.readyState == 4) {
							if (Ext.isIE) {
								update = true;
							} else if (rq.transport == 'streaming') {
								update = true;
							} else if (rq.transport == 'long-polling') {
								update = true;
								clearTimeout(rq.id);
							}

						} else if (!Ext.isIE && ajaxRequest.readyState == 3 && ajaxRequest.status == 200 && rq.transport != 'long-polling') {
							update = true;
						} else {
							clearTimeout(rq.id);
						}

						try {
							if (rq.readResponsesHeaders) {
								var tempUUID = ajaxRequest.getResponseHeader('X-Atmosphere-tracking-id');
								if (tempUUID != null || tempUUID != undefined) {
									_request.uuid = tempUUID.split(" ").pop();
								}
							}
						} catch (e) {
						}

						if (update) {
							var responseText = ajaxRequest.responseText;

							// Do not fail on trying to retrieve headers. Chrome migth fail with
							// Refused to get unsafe header
							// Let the failure happens later with a better error message
							try {
								if (rq.readResponsesHeaders) {
									var tempDate = ajaxRequest.getResponseHeader('X-Cache-Date');
									if (tempDate != null || tempDate != undefined) {
										_request.lastTimestamp = tempDate.split(" ").pop();
									}
								}
							} catch (e) {
							}

							if (rq.transport == 'streaming') {
								var text = responseText.substring(rq.lastIndex, responseText.length);
								_response.isJunkEnded = true;

								if (rq.lastIndex == 0 && text.indexOf("<!-- Welcome to the Atmosphere Framework.") != -1) {
									_response.isJunkEnded = false;
								}

								if (!_response.isJunkEnded) {
									var endOfJunk = "<!-- EOD -->";
									var endOfJunkLength = endOfJunk.length;
									var junkEnd = text.indexOf(endOfJunk) + endOfJunkLength;

									if (junkEnd > endOfJunkLength && junkEnd != text.length) {
										_response.responseBody = text.substring(junkEnd);
									} else {
										skipCallbackInvocation = true;
									}
								} else {
									var message = responseText.substring(rq.lastIndex, responseText.length);
									skipCallbackInvocation = _trackMessageSize(message, rq, _response);
								}
								rq.lastIndex = responseText.length;

								if (Ext.isOpera) {
									iterate(function() {
										if (ajaxRequest.responseText.length > rq.lastIndex) {
											try {
												_response.status = ajaxRequest.status;
												_response.headers = parseHeaders(ajaxRequest.getAllResponseHeaders());

												// HOTFIX for firefox bug: https://bugzilla.mozilla.org/show_bug.cgi?id=608735
												if (_request.readResponsesHeaders && _request.headers) {
													Ext.iterate(_request.headers, function(name) {
														var v = ajaxRequest.getResponseHeader(name);
														if (v) {
															_response.headers[name] = v;
														}
													});
												}
											} catch (e) {
												_response.status = 404;
											}
											_response.state = "messageReceived";
											_response.responseBody = ajaxRequest.responseText.substring(rq.lastIndex);
											rq.lastIndex = ajaxRequest.responseText.length;

											_invokeCallback();
											if ((rq.transport == 'streaming') && (ajaxRequest.responseText.length > rq.maxStreamingLength)) {
												// Close and reopen connection on large data received
												ajaxRequest.abort();
												_doRequest(ajaxRequest, rq, true);
											}
										}
									}, 0);
								}

								if (skipCallbackInvocation) {
									return;
								}
							} else {
								skipCallbackInvocation = _trackMessageSize(responseText, rq, _response);
								rq.lastIndex = responseText.length;
							}

							try {
								_response.status = ajaxRequest.status;
								_response.headers = parseHeaders(ajaxRequest.getAllResponseHeaders());

								// HOTFIX for firefox bug: https://bugzilla.mozilla.org/show_bug.cgi?id=608735
								if (_request.headers) {
									Ext.iterate(_request.headers, function(name) {
										var v = ajaxRequest.getResponseHeader(name);
										if (v) {
											_response.headers[name] = v;
										}
									});
								}
							} catch (e) {
								_response.status = 404;
							}

							if (rq.suspend) {
								_response.state = _response.status == 0 ? "closed" : "messageReceived";
							} else {
								_response.state = "messagePublished";
							}

							if (!rq.executeCallbackBeforeReconnect) {
								_reconnect(ajaxRequest, rq, false);
							}

							// For backward compatibility with Atmosphere < 0.8
							if (_response.responseBody.indexOf("parent.callback") != -1) {
								Ext.log({ level: rq.logLevel }, "parent.callback no longer supported with 0.8 version and up. Please upgrade");
							}
							_invokeCallback();

							if (rq.executeCallbackBeforeReconnect) {
								_reconnect(ajaxRequest, rq, false);
							}

							if ((rq.transport == 'streaming') && (responseText.length > rq.maxStreamingLength)) {
								// Close and reopen connection on large data received
								ajaxRequest.abort();
								_doRequest(ajaxRequest, rq, true);
							}
						}
					};
					ajaxRequest.send(rq.data);

					if (rq.suspend) {
						rq.id = setTimeout(function() {
							if (_subscribed) {
								ajaxRequest.abort();
								_subscribe(rq);
								_execute();
							}
						}, rq.timeout);
					}
					_subscribed = true;

				} else {
					Ext.log({ level: rq.logLevel }, "Max re-connection reached.");
					_onError();
				}
			}

			/**
			 * Do ajax request.
			 * 
			 * @param ajaxRequest
			 *            Ajax request.
			 * @param request
			 *            Request parameters.
			 * @param create
			 *            If ajax request has to be open.
			 */
			function _doRequest(ajaxRequest, request, create) {
				// Prevent Android to cache request
				var url = _attachHeaders(request);
				url = prepareURL(url);

				if (create) {
					ajaxRequest.open(request.method, url, true);
					if (request.connectTimeout > -1) {
						request.id = setTimeout(function() {
							if (request.requestCount == 0) {
								ajaxRequest.abort();
								_prepareCallback("Connect timeout", "closed", 200, request.transport);
							}
						}, request.connectTimeout);
					}
				}

				if (_request.withCredentials) {
					if ("withCredentials" in ajaxRequest) {
						ajaxRequest.withCredentials = true;
					}
				}

				if (!_request.dropAtmosphereHeaders) {
					ajaxRequest.setRequestHeader("X-Atmosphere-Framework", version);
					ajaxRequest.setRequestHeader("X-Atmosphere-Transport", request.transport);
					if (request.lastTimestamp != undefined) {
						ajaxRequest.setRequestHeader("X-Cache-Date", request.lastTimestamp);
					} else {
						ajaxRequest.setRequestHeader("X-Cache-Date", 0);
					}

					if (request.trackMessageLength) {
						ajaxRequest.setRequestHeader("X-Atmosphere-TrackMessageSize", "true");
					}

					if (request.contentType != '') {
						ajaxRequest.setRequestHeader("Content-Type", request.contentType);
					}
					ajaxRequest.setRequestHeader("X-Atmosphere-tracking-id", request.uuid);
				}
				Ext.iterate(request.headers, function(name, value) {
					var h = Ext.isFunction(value) ? value.call(this, ajaxRequest, request, create, _response) : value;
					if (h != null) {
						ajaxRequest.setRequestHeader(name, h);
					}
				});
			}

			function _reconnect(ajaxRequest, request, force) {
				if (force || (request.suspend && ajaxRequest.status == 200 && request.transport != 'streaming' && _subscribed)) {
					if (request.reconnect) {
						_open('re-opening', request.transport, request);
						request.id = setTimeout(function() {
							_executeRequest();
						}, request.reconnectInterval);
					}
				}
			}

			// From jquery-stream, which is APL2 licensed as well.
			function _ieXDR(request) {
				_ieStream = _configureXDR(request);
				_ieStream.open();
			}

			// From jquery-stream
			function _configureXDR(request) {
				var rq = _request;
				if ((request != null) && (typeof (request) != 'undefined')) {
					rq = request;
				}

				var lastMessage = "";
				var transport = rq.transport;
				var lastIndex = 0;

				var xdrCallback = function(xdr) {
					var responseBody = xdr.responseText;
					var isJunkEnded = false;

					if (responseBody.indexOf("<!-- Welcome to the Atmosphere Framework.") != -1) {
						isJunkEnded = true;
					}

					if (isJunkEnded) {
						var endOfJunk = "<!-- EOD -->";
						var endOfJunkLenght = endOfJunk.length;
						var junkEnd = responseBody.indexOf(endOfJunk);
						if (junkEnd !== -1) {
							responseBody = responseBody.substring(junkEnd + endOfJunkLenght + lastIndex);
							lastIndex += responseBody.length;
						}
					}

					_prepareCallback(responseBody, "messageReceived", 200, transport);
				};

				var xdr = new window.XDomainRequest();
				var rewriteURL = rq.rewriteURL || function(url) {
					// Maintaining session by rewriting URL
					// http://stackoverflow.com/questions/6453779/maintaining-session-by-rewriting-url
					var match = /(?:^|;\s*)(JSESSIONID|PHPSESSID)=([^;]*)/.exec(document.cookie);

					switch (match && match[1]) {
					case "JSESSIONID":
						return url.replace(/;jsessionid=[^\?]*|(\?)|$/, ";jsessionid=" + match[2] + "$1");
					case "PHPSESSID":
						return url.replace(/\?PHPSESSID=[^&]*&?|\?|$/, "?PHPSESSID=" + match[2] + "&").replace(/&$/, "");
					}
					return url;
				};

				// Handles open and message event
				xdr.onprogress = function() {
					xdrCallback(xdr);
				};
				// Handles error event
				xdr.onerror = function() {
					// If the server doesn't send anything back to XDR will fail with polling
					if (rq.transport != 'polling') {
						_prepareCallback(xdr.responseText, "error", 500, transport);
					}
				};
				// Handles close event
				xdr.onload = function() {
					if (lastMessage != xdr.responseText) {
						xdrCallback(xdr);
					}
					if (rq.transport == "long-polling") {
						_executeRequest();
					}
				};

				return {
					open: function() {
						if (rq.method == 'POST') {
							rq.attachHeadersAsQueryString = true;
						}
						var url = _attachHeaders(rq);
						if (rq.method == 'POST') {
							url += "&X-Atmosphere-Post-Body=" + rq.data;
						}
						xdr.open(rq.method, rewriteURL(url));
						xdr.send();
						if (rq.connectTimeout > -1) {
							rq.id = setTimeout(function() {
								if (rq.requestCount == 0) {
									xdr.abort();
									_prepareCallback("Connect timeout", "closed", 200, rq.transport);
								}
							}, rq.connectTimeout);
						}
					},
					close: function() {
						xdr.abort();
						_prepareCallback(xdr.responseText, "closed", 200, transport);
					}
				};
			}

			// From jquery-stream, which is APL2 licensed as well.
			function _ieStreaming(request) {
				_ieStream = _configureIE(request);
				_ieStream.open();
			}

			function _configureIE(request) {
				var rq = _request;
				if ((request != null) && (typeof (request) != 'undefined')) {
					rq = request;
				}

				var stop;
				var doc = new window.ActiveXObject("htmlfile");

				doc.open();
				doc.close();

				var url = rq.url;

				if (rq.transport != 'polling') {
					_response.transport = rq.transport;
				}

				return {
					open: function() {
						var iframe = doc.createElement("iframe");

						url = _attachHeaders(rq);
						if (rq.data != '') {
							url += "&X-Atmosphere-Post-Body=" + rq.data;
						}

						// Finally attach a timestamp to prevent Android and IE caching.
						url = prepareURL(url);

						iframe.src = url;
						doc.body.appendChild(iframe);

						// For the server to respond in a consistent format regardless of user agent, we polls response text
						var cdoc = iframe.contentDocument || iframe.contentWindow.document;

						stop = iterate(function() {
							try {
								if (!cdoc.firstChild) {
									return;
								}

								// Detects connection failure
								if (cdoc.readyState === "complete") {
									try {
										Ext.emptyFn(cdoc.fileSize);
									} catch (e) {
										_prepareCallback("Connection Failure", "error", 500, rq.transport);
										return false;
									}
								}

								var res = cdoc.body ? cdoc.body.lastChild : cdoc;
								var readResponse = function() {
									// Clones the element not to disturb the original one
									var clone = res.cloneNode(true);

									// If the last character is a carriage return or a line feed, IE ignores it in the innerText property
									// therefore, we add another non-newline character to preserve it
									clone.appendChild(cdoc.createTextNode("."));

									var text = clone.innerText;
									var isJunkEnded = true;

									if (text.indexOf("<!-- Welcome to the Atmosphere Framework.") == -1) {
										isJunkEnded = false;
									}

									if (isJunkEnded) {
										var endOfJunk = "<!-- EOD -->";
										var endOfJunkLength = endOfJunk.length;
										var junkEnd = text.indexOf(endOfJunk) + endOfJunkLength;

										text = text.substring(junkEnd);
									}
									return text.substring(0, text.length - 1);
								};

								// To support text/html content type
								if (!(res.nodeName && res.nodeName.toUpperCase() === 'PRE')) {
									// Injects a plaintext element which renders text without interpreting the HTML and cannot be stopped
									// it is deprecated in HTML5, but still works
									var head = cdoc.head || cdoc.getElementsByTagName("head")[0] || cdoc.documentElement || cdoc;
									var script = cdoc.createElement("script");

									script.text = "document.write('<plaintext>')";

									head.insertBefore(script, head.firstChild);
									head.removeChild(script);

									// The plaintext element will be the response container
									res = cdoc.body.lastChild;
								}

								// Handles open event
								_prepareCallback(readResponse(), "opening", 200, rq.transport);

								// Handles message and close event
								stop = iterate(function() {
									var text = readResponse();
									if (text.length > rq.lastIndex) {
										_response.status = 200;
										_prepareCallback(text, "messageReceived", 200, rq.transport);

										// Empties response every time that it is handled
										res.innerText = "";
										rq.lastIndex = 0;
									}

									if (cdoc.readyState === "complete") {
										_prepareCallback("", "re-opening", 200, rq.transport);
										_ieStreaming(rq);
										return false;
									}
								}, null);

								return false;
							} catch (err) {
								Ext.log({ level: 'error' }, err);
							}
						});
					},

					close: function() {
						if (stop) {
							stop();
						}

						doc.execCommand("Stop");
						_prepareCallback("", "closed", 200, rq.transport);
					}
				};
			}

			/**
			 * Send message. <br>
			 * Will be automatically dispatch to other connected.
			 * 
			 * @param {Object,
			 *            string} Message to send.
			 * @private
			 */
			function _push(message) {

				if (_localStorageService != null) {
					_pushLocal(message);
				} else if (_activeRequest != null || _sse != null) {
					_pushAjaxMessage(message);
				} else if (_ieStream != null) {
					_pushIE(message);
				} else if (_jqxhr != null) {
					_pushJsonp(message);
				} else if (_websocket != null) {
					_pushWebSocket(message);
				}
			}

			function _pushLocal(message) {
				_localStorageService.send(message);
			}

			function _intraPush(message) {
				try {
					if (_localStorageService) {
						_localStorageService.localSend(message);
					} else {
						_storageService.signal("localMessage", Ext.encode({
							id: guid,
							event: message
						}));
					}
				} catch (err) {
					Ext.log({ level: 'error' }, err);
				}
			}

			/**
			 * Send a message using currently opened ajax request (using http-streaming or long-polling). <br>
			 * 
			 * @param {string,
			 *            Object} Message to send. This is an object, string message is saved in data member.
			 * @private
			 */
			function _pushAjaxMessage(message) {
				var rq = _getPushRequest(message);
				_executeRequest(rq);
			}

			/**
			 * Send a message using currently opened ie streaming (using http-streaming or long-polling). <br>
			 * 
			 * @param {string,
			 *            Object} Message to send. This is an object, string message is saved in data member.
			 * @private
			 */
			function _pushIE(message) {
				if (_request.enableXDR && checkCORSSupport()) {
					var rq = _getPushRequest(message);
					// Do not reconnect since we are pushing.
					rq.reconnect = false;
					_jsonp(rq);
				} else {
					_pushAjaxMessage(message);
				}
			}

			/**
			 * Send a message using jsonp transport. <br>
			 * 
			 * @param {string,
			 *            Object} Message to send. This is an object, string message is saved in data member.
			 * @private
			 */
			function _pushJsonp(message) {
				_pushAjaxMessage(message);
			}

			function _getStringMessage(message) {
				var msg = message;
				if (typeof (msg) == 'object') {
					msg = message.data;
				}
				return msg;
			}

			/**
			 * Build request use to push message using method 'POST' <br>. Transport is defined as 'polling' and 'suspend' is set to false.
			 * 
			 * @return {Object} Request object use to push message.
			 * @private
			 */
			function _getPushRequest(message) {
				var msg = _getStringMessage(message);

				var rq = {
					connected: false,
					timeout: 60000,
					method: 'POST',
					url: _request.url,
					contentType: _request.contentType,
					headers: {},
					reconnect: true,
					callback: null,
					data: msg,
					suspend: false,
					maxRequest: 60,
					logLevel: 'info',
					requestCount: 0,
					transport: 'polling',
					attachHeadersAsQueryString: true,
					enableXDR: _request.enableXDR,
					uuid: _request.uuid
				};

				if (typeof (message) == 'object') {
					rq = Ext.apply(rq, message);
				}

				return rq;
			}

			/**
			 * Send a message using currently opened websocket. <br>
			 * 
			 * @param {string,
			 *            Object} Message to send. This is an object, string message is saved in data member.
			 */
			function _pushWebSocket(message) {
				var msg = _getStringMessage(message);
				var data;
				try {
					if (_request.webSocketUrl != null) {
						data = _request.webSocketPathDelimiter + _request.webSocketUrl + _request.webSocketPathDelimiter + msg;
					} else {
						data = msg;
					}

					_websocket.send(data);

				} catch (e) {
					_websocket.onclose = function(message) {
					};
					_websocket.close();

					_reconnectWithFallbackTransport("Websocket failed. Downgrading to Comet and resending " + data);
					_pushAjaxMessage(message);
				}
			}

			function _localMessage(message) {
				var m = Ext.decode(message);
				if (m.id != guid) {
					if (typeof (_request.onLocalMessage) != 'undefined') {
						_request.onLocalMessage(m.event);
					} else if (typeof (Ext.ux.Atmosphere.onLocalMessage) != 'undefined') {
						Ext.ux.Atmosphere.onLocalMessage(m.event);
					}
				}
			}

			function _prepareCallback(messageBody, state, errorCode, transport) {

				if (state == "messageReceived") {
					if (_trackMessageSize(messageBody, _request, _response))
						return;
				}

				_response.transport = transport;
				_response.status = errorCode;

				// If not -1, we have buffered the message.
				if (_response.expectedBodySize == -1) {
					_response.responseBody = messageBody;
				}
				_response.state = state;

				_invokeCallback();
			}

			function _invokeFunction(response) {
				_f(response, _request);
				// Global
				_f(response, Ext.ux.Atmosphere);
			}

			function _f(response, f) {
				switch (response.state) {
				case "messageReceived":
					if (typeof (f.onMessage) != 'undefined')
						f.onMessage(response);
					break;
				case "error":
					if (typeof (f.onError) != 'undefined')
						f.onError(response);
					break;
				case "opening":
					if (typeof (f.onOpen) != 'undefined')
						f.onOpen(response);
					break;
				case "messagePublished":
					if (typeof (f.onMessagePublished) != 'undefined')
						f.onMessagePublished(response);
					break;
				case "re-opening":
					if (typeof (f.onReconnect) != 'undefined')
						f.onReconnect(_request, response);
					break;
				case "unsubscribe":
				case "closed":
					if (typeof (f.onClose) != 'undefined')
						f.onClose(response);
					break;
				}
			}

			/**
			 * Invoke request callbacks.
			 * 
			 * @private
			 */
			function _invokeCallback() {
				var call = function(index, func) {
					func(_response);
				};

				if (_localStorageService == null && _localSocketF != null) {
					_localSocketF(_response.responseBody);
				}

				var messages = typeof (_response.responseBody) == 'string' ? _response.responseBody.split(_request.messageDelimiter) : new Array(
						_response.responseBody);
				for ( var i = 0; i < messages.length; i++) {

					if (messages.length > 1 && messages[i].length == 0) {
						continue;
					}
					_response.responseBody = Ext.String.trim(messages[i]);

					// Ugly see issue 400.
					if (_response.responseBody.length == 0 && _response.transport == 'streaming' && _response.state == "messageReceived") {
						var ua = navigator.userAgent.toLowerCase();
						var isAndroid = ua.indexOf("android") > -1;
						if (isAndroid) {
							continue;
						}
					}

					_invokeFunction(_response);

					// Invoke global callbacks
					if (callbacks.length > 0) {
						if (_request.logLevel == 'debug') {
							Ext.log("Invoking " + callbacks.length + " global callbacks: " + _response.state);
						}
						try {
							Ext.iterate(callbacks, call);
						} catch (e) {
							Ext.log({ level: _request.logLevel }, "Callback exception" + e);
						}
					}

					// Invoke request callback
					if (typeof (_request.callback) == 'function') {
						if (_request.logLevel == 'debug') {
							Ext.log("Invoking request callbacks");
						}
						try {
							_request.callback(_response);
						} catch (e) {
							Ext.log({ level: _request.logLevel }, "Callback exception" + e);
						}
					}
				}

			}

			/**
			 * Close request.
			 * 
			 * @private
			 */
			function _close() {
				_request.reconnect = false;
				_response.request = _request;
				_subscribed = false;
				_abordingConnection = true;
				_response.state = 'unsubscribe';
				_response.responseBody = "";
				_response.status = 408;
				_invokeCallback();

				_clearState();

				// Are we the parent that hold the real connection.
				if (_localStorageService == null && _localSocketF != null) {
					// The heir is the parent unless _abordingConnection
					_storageService.signal("close", {
						reason: "",
						heir: !_abordingConnection ? guid : _storageService.get("children")[0]
					});
					document.cookie = encodeURIComponent("atmosphere-" + _request.url) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
				}

				if (_storageService != null) {
					_storageService.close();
				}

				if (_localStorageService != null) {
					_localStorageService.close();
				}
			}

			function _clearState() {
				if (_ieStream != null) {
					_ieStream.close();
					_ieStream = null;
				}
				if (_jqxhr != null) {
					_jqxhr.abort();
					_jqxhr = null;
				}
				if (_activeRequest != null) {
					_activeRequest.abort();
					_activeRequest = null;
				}
				if (_websocket != null) {
					_websocket.close();
					_websocket = null;
				}
				if (_sse != null) {
					_sse.close();
					_sse = null;
				}
			}

			this.subscribe = function(options) {
				_subscribe(options);
				_execute();
			};

			this.execute = function() {
				_execute();
			};

			this.invokeCallback = function() {
				_invokeCallback();
			};

			this.close = function() {
				_close();
			};

			this.getUrl = function() {
				return _request.url;
			};

			this.push = function(message) {
				_push(message);
			};

			this.pushLocal = function(message) {
				_intraPush(message);
			};

			this.response = _response;
						
		},		
		
		subscribe: function(url, callback, request) {
			if (typeof (callback) == 'function') {
				this.addCallback(callback);
			}

			if (typeof (url) != "string") {
				request = url;
			} else {
				request.url = url;
			}

			var rq = new this.AtmosphereRequest(request);
			rq.execute();
			requests[requests.length] = rq;
			return rq;
		},

		addCallback: function(func) {
			if (!Ext.Array.contains(callbacks, func)) {
				callbacks.push(func);
			}
		},

		removeCallback: function(func) {
			var index = Ext.Array.indexOf(callbacks, func);
			if (index != -1) {
				callbacks.splice(index, 1);
			}
		},

		unsubscribe: function() {
			if (requests.length > 0) {
				for ( var i = 0; i < requests.length; i++) {
					requests[i].close();
					clearTimeout(requests[i].id);
				}
			}
			requests = [];
			callbacks = [];
		},

		unsubscribeUrl: function(url) {
			var idx = -1;
			if (requests.length > 0) {
				for ( var i = 0; i < requests.length; i++) {
					var rq = requests[i];

					// Suppose you can subscribe once to an url
					if (rq.getUrl() == url) {
						rq.close();
						clearTimeout(rq.id);
						idx = i;
						break;
					}
				}
			}
			if (idx >= 0) {
				requests.splice(idx, 1);
			}
		},

		publish: function(request) {
			if (typeof (request.callback) == 'function') {
				this.addCallback(callback);
			}
			request.transport = "polling";

			var rq = new this.AtmosphereRequest(request);
			requests[requests.length] = rq;
			return rq;
		}
	};
		
	function parseHeaders(headerString) {
		var match, rheaders = /^(.*?):[ \t]*([^\r\n]*)\r?$/mg, headers = {};
		while (match = rheaders.exec(headerString)) {
			headers[match[1]] = match[2];
		}
		return headers;
	}
	
	function checkCORSSupport() {
		if (Ext.isIE && !window.XDomainRequest) {
			return true;
		} else if (Ext.isOpera) {
			return true;
		}

		// Force Android to use CORS as some version like 2.2.3 fail otherwise
		var ua = navigator.userAgent.toLowerCase();
		var isAndroid = ua.indexOf("android") > -1;
		if (isAndroid) {
			return true;
		}
		return false;
	}

	function S4() {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	}

	function guid() {
		return (this.S4() + this.S4() + "-" + this.S4() + "-" + this.S4() + "-" + this.S4() + "-" + this.S4() + this.S4() + this.S4());
	}

	function prepareURL(url) {
		// Attaches a time stamp to prevent caching
		var ts = Ext.Date.now();
		var ret = url.replace(/([?&])_=[^&]*/, "$1_=" + ts);

		return ret + (ret === url ? (/\?/.test(url) ? "&" : "?") + "_=" + ts : "");
	}

	// From jQuery-Stream
	function param(data) {
		return Ext.Object.toQueryString(data);
	}

	function supportStorage() {
		var storage = window.localStorage;
		if (storage) {
			try {
				storage.setItem("t", "t");
				storage.removeItem("t");
				// Internet Explorer 9 has no StorageEvent object but supports the storage event
				return !!window.StorageEvent || Object.prototype.toString.call(storage) === "[object Storage]";
			} catch (e) {
			}
		}

		return false;
	}

	function iterate(fn, interval) {
		var timeoutId;

		// Though the interval is 0 for real-time application, there is a delay between setTimeout calls
		// For detail, see https://developer.mozilla.org/en/window.setTimeout#Minimum_delay_and_timeout_nesting
		interval = interval || 0;

		(function loop() {
			timeoutId = setTimeout(function() {
				if (fn() === false) {
					return;
				}

				loop();
			}, interval);
		})();

		return function() {
			clearTimeout(timeoutId);
		};
	}
	
});
