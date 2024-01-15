/*
 * Copyright 2014-2014 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
qx.Class.define("rpcexample.Application", {
	extend: qx.application.Standalone,

	properties: {
		url: {
			init: "/services",
			event: "changeUrl"
		}
	},

	/*
	 * ****************************************************************************
	 * MEMBERS
	 * ****************************************************************************
	 */

	members: {
		/**
		 * This method contains the initial application code and gets called during
		 * startup of the application
		 * 
		 * @lint ignoreDeprecated(alert)
		 */
		main: function() {
			// Call super class
			this.base(arguments);

			// Enable logging in debug variant
			if (qx.core.Environment.get("qx.debug")) {
				// support native logging capabilities, e.g. Firebug for Firefox
				qx.log.appender.Native;
				// support additional cross-browser console. Press F7 to toggle visibility
				qx.log.appender.Console;
			}

			// Create the list of tests
			var tests = [ {
				name: "Echo Test",
				func: this.echo,
				desc: ("This test calls a simple echo-style service " + "on the server. The server method accepts a string " + "and sends back a string that says " + "'Client said: [input string]'.")
			},

			{
				name: "Multiple Async Calls",
				func: this.multipleAsyncCalls,
				desc: ("This tests the ability to issue multiple " + "asynchronous RPC calls to the same service/method, " + "and determine from which request we have received a " + "response.  We issue multiple 'sleep' calls, " + "for decreasing amounts of time, and ensure that we " + "can associate the resonses from the later-issued " + "requests to the earlier-received responses." + "<p>" + "Both IE and Firefox follow (too strictly) RFC2616 " + "and limit the number of simultaneous asyncronous HTTP " + "requests to 2.  We'll allow testing just 2 " + "simultaneous requests or issuing 6 simultaneous " + "requests.  In the former case, we'll get expected " + "results.  In the latter, we'll see two at a time " + "being processed." + "<p>" + "Note that this applies to both XmlHTTPTransport " + "and IframeTransport. It is an HTTP limitation, not " + "a limitation of a particular method of issuing " + "a request.")

			},

			{
				name: "RPC Server Functionality (sync)",
				func: this.rpcServerFunctionalitySync,
				desc: ("This test calls a whole set of functions to test " + "each of the primitive data types. " + "<span style='color:blue;'>Results are " + "displayed in the debug console.</span>  The comparison " + "results should all end with ': true', and the " + "last test generates an Application Error (#1000).  " + "No other test generates that error, so receiving " + "it means the complete set of tests was run." + "<p>" + "These functions all use the synchronous interface. " + "You should not use the synchronous interface " + "because with some browsers, the entire browser " + "environment locks up during a synchronous call.  " + "If the server hangs for a minute or two, so will " + "the browser!  You have been warned.")
			},

			{
				name: "RPC Server Functionality (async)",
				func: this.rpcServerFunctionalityAsync,
				desc: ("This test calls a whole set of functions to test " + "each of the primitive data types.  " + "<span style='color:blue;'>Results are " + "displayed in the debug console.</span>  The comparison " + "results should all end with ': true', and the " + "last test generates an Application Error (#1000).  " + "No other test generates that error, so receiving " + "it means the complete set of tests was run." + "<p>" + "These functions all use the synchronous interface. " + "You should not use the synchronous interface " + "because with some browsers, the entire browser " + "environment locks up during a synchronous call.  " + "If the server hangs for a minute or two, so will " + "the browser!  You have been warned.")
			},

			{
				name: "Demonstrate Remote Table usage",
				func: this.remoteTable,
				desc: ("This is an example of using the Remote table model " + "by extending qx.ui.table.model.Remote.  It retrieves " + "its table data via Remote Procedure Call to the " + "service called <i>tableTestService</i>.")
			},
			
			{
				name: "Refresh Session",
				func: this.refreshSession,
				desc: ("Example of a refreshSession call")
			} ];

			// Create tabs for each of our tests
			var tabs = new qx.ui.tabview.TabView();
			this.getRoot().add(tabs, {
				edge: 10
			});

			// For each test...
			for (var i = 0; i < tests.length; i++) {
				var test = tests[i];

				// Create a page for this test
				var page = new qx.ui.tabview.Page(test.name);

				// Give it a layout
				page.setLayout(new qx.ui.layout.VBox(4));

				// Add this page to the tab set
				tabs.add(page);

				// Add the description to the page
				var label = new qx.ui.basic.Label().set({
					value: test.desc,
					decorator: "main",
					rich: true,
					padding: 10,
					margin: 20,
					width: 500
				});
				page.add(label);

				// Call the function to generate this page
				test.func.call(this, page, test.desc);
			}
		},

		echo: function(page, description) {
			page.add(new qx.ui.basic.Label("URL:"));
			var defaultURL = qx.io.remote.Rpc.makeServerURL();
			var url = new qx.ui.basic.Label('<strong>' + defaultURL + '</strong>');
			url.setRich(true);
			url.setMarginBottom(10);
			page.add(url);

			page.add(new qx.ui.basic.Label("Service:"));
			var service = new qx.ui.basic.Label("<strong>testService</strong>");
			service.setRich(true);
			service.setMarginBottom(10);
			page.add(service);

			page.add(new qx.ui.basic.Label("Method:"));
			var methodLabel = new qx.ui.basic.Label("<strong>echo</strong>");
			methodLabel.setRich(true);
			methodLabel.setMarginBottom(20);
			page.add(methodLabel);

			var async = new qx.ui.form.CheckBox("Asynchronous Request");
			async.setMarginBottom(20);
			page.add(async);

			var hBox = new qx.ui.container.Composite(new qx.ui.layout.HBox(4));
			hBox.set({
				width: 60
			});
			page.add(hBox);

			var message = new qx.ui.form.TextField("Hello");
			message.setWidth(200);
			hBox.add(message);

			var send = new qx.ui.form.Button("Send to server");
			hBox.add(send);

			var abort = new qx.ui.form.Button("Abort");
			abort.setEnabled(false);
			hBox.add(abort);

			var responseLabel = new qx.ui.basic.Label("Server Response:");
			page.add(responseLabel);
			var response = new qx.ui.basic.Label();
			response.setRich(true);
			page.add(response);

			var method = 'echo';
			var rpc = new qx.io.remote.Rpc();
			rpc.setUrl(defaultURL);
			rpc.setServiceName('testService');

			rpc.setTimeout(10000);
			var mycall = null;

			send.addListener("execute", function() {
				// Allow the user to reset the URL and Service on each call

				if (async.getValue()) {
					send.setEnabled(false);
					abort.setEnabled(true);
					mycall = rpc.callAsync(function(result, ex, id) {
						mycall = null;
						responseLabel.setValue('Server Response ASYNC:');
						if (ex == null) {
							response.setValue('<strong>' + result + '</strong>');
						}
						else {
							response.setValue('<strong> Exception: ' + ex + '</strong>');
						}
						send.setEnabled(true);
						abort.setEnabled(false);
					}, method, message.getValue());
				}
				else {
					responseLabel.setValue('Server Response SYNC:');
					try {
						var result = rpc.callSync(method, message.getValue());
						response.setValue('<strong>' + result + '</strong>');
					}
					catch (ex) {
						response.setValue('<strong> Exception: ' + ex + '</strong>');
					}
				}
			});

			abort.addListener("execute", function() {
				rpc.abort(mycall);
			});
		},

		multipleAsyncCalls: function(page, description) {
			/*
			 * Sigh. Both IE and Firefox follow (too strictly) RFC2616 and limit the
			 * number of simultaneous asyncronous HTTP requests to 2. We'll allow testing
			 * just 2 simultaneous requests or issuing 6 simultaneous requests. In the
			 * former case, we'll get expected results. In the latter, we'll see two at a
			 * time being processed.
			 * 
			 * Note that this applies to both XmlHTTPTransport and IframeTransport. It is
			 * an HTTP limitation, not a limitation of a particular method of issuing a
			 * request.
			 */
			var tooMany = new qx.ui.form.CheckBox("Issue more requests than IE's and Firefox's implementations " + "of HTTP will process simultaneously");
			tooMany.setMarginBottom(10);
			page.add(tooMany);

			page.add(new qx.ui.basic.Label("URL:"));
			var defaultURL = qx.io.remote.Rpc.makeServerURL();
			var url = new qx.ui.basic.Label('<strong>' + defaultURL + '</strong>');
			url.setRich(true);
			url.setMarginBottom(10);
			page.add(url);

			page.add(new qx.ui.basic.Label("Service:"));
			var service = new qx.ui.basic.Label("<strong>testService</strong>");
			service.setRich(true);
			service.setMarginBottom(20);
			page.add(service);

			var hBox = new qx.ui.container.Composite(new qx.ui.layout.HBox(4));
			hBox.set({
				width: 60
			});
			page.add(hBox);

			var start = new qx.ui.form.Button("Start Test");
			hBox.add(start);

			var abort = new qx.ui.form.Button("Abort");
			hBox.add(abort);

			var requestLabels = [];
			var responseLabels = [];
			var rrLabels = [];

			var grid = new qx.ui.container.Composite(new qx.ui.layout.Grid(5, 5));

			for (var r = 0; r < 5; r++) {
				rrLabels[r] = new qx.ui.basic.Label();
				grid.add(rrLabels[r], {
					row: r,
					column: 0
				});

				requestLabels[r] = new qx.ui.basic.Label();
				grid.add(requestLabels[r], {
					row: r,
					column: 1
				});

				responseLabels[r] = new qx.ui.basic.Label();
				grid.add(responseLabels[r], {
					row: r,
					column: 2
				});
			}
			page.add(grid);

			// ensure there's room in the queue for all of our requests
			qx.io.remote.RequestQueue.getInstance().setMaxConcurrentRequests(8);

			// We'll be setting url and service upon execute; no need to do it now.
			var rpc = new qx.io.remote.Rpc();
			rpc.setUrl(defaultURL);
			rpc.setServiceName('testService');
			rpc.setTimeout(60000);
			var mycall;
			var mycalls = [];

			start.addListener("execute", function() {
				var t0 = new Date().getTime();

				for (var i = 0; i < 5; i++) {
					rrLabels[i].setValue('');
					requestLabels[i].setValue('');
					responseLabels[i].setValue('');
				}

				var seqnum;
				for (var i = (tooMany.getValue() ? 5 : 2); i > 0; i--) {
					var labelIx = (tooMany.getValue() ? 5 : 2) - i;
					rrLabels[labelIx].setValue(labelIx + ': ');
					/*
					 * Always issue an asynchronous request! Issuing a synchronous request
					 * can lock up the entire browser until a response is received. Bad
					 * browser developers! Bad!
					 */
					mycall = rpc.callAsync(function(result, ex, seqnum) {
						mycalls[seqnum] = null;
						var t = new Date().getTime() - t0;

						if (ex == null) {
							var s = t + " ms: response " + seqnum + ": " + result;
							page.debug(s);
							responseLabels[(tooMany.getValue() ? 5 : 2) - result].setValue(s);
						}
						else {
							var e = t + " ms: exception " + seqnum + ": " + ex;
							page.debug(e);
							responseLabels[(tooMany.getValue() ? 5 : 2) - result].setValue(e);
						}
					}, "sleep", i);

					var t = new Date().getTime() - t0;
					seqnum = mycall.getSequenceNumber();
					mycalls[seqnum] = mycall;

					var r = t + " ms: request " + seqnum + " = " + i.toString();
					page.debug(r);
					requestLabels[labelIx].setValue(r);
				}
			});

			abort.addListener("execute", function() {
				for ( var seqnum in mycalls) {
					if (mycalls[seqnum] !== null) {
						rpc.abort(mycalls[seqnum]);
						mycalls[seqnum] = null;
					}
				}
				mycalls = [];
			});
		},

		rpcServerFunctionalitySync: function(page, description) {

			var dateMethod = 'getDate';
			var convertDates = new qx.ui.form.CheckBox("Old-style 'qooxdoo' dates?");
			convertDates.setValue(qx.io.remote.Rpc.CONVERT_DATES);
			convertDates.addListener("changeValue", function(e) {
				if (e.getData()) {
					dateMethod = 'getOldDate';
					qx.io.remote.Rpc.CONVERT_DATES = true;
				}
				else {
					dateMethod = 'getDate';
					qx.io.remote.Rpc.CONVERT_DATES = null;
				}
			});
			page.add(convertDates);

			page.add(new qx.ui.basic.Label("URL:"));
			var defaultURL = qx.io.remote.Rpc.makeServerURL();
			var url = new qx.ui.basic.Label('<strong>' + defaultURL + '</strong>');
			url.setRich(true);
			url.setMarginBottom(10);
			page.add(url);

			page.add(new qx.ui.basic.Label("Service:"));
			var service = new qx.ui.basic.Label("<strong>testService</strong>");
			service.setRich(true);
			service.setMarginBottom(20);
			page.add(service);

			var hBox = new qx.ui.container.Composite(new qx.ui.layout.HBox(4));
			hBox.set({
				width: 60
			});
			page.add(hBox);

			var start = new qx.ui.form.Button("Start Test");
			hBox.add(start);

			var textArea = new qx.ui.form.TextArea();
			textArea.append = function(newText) {
				var text = textArea.getValue();
				text = (text ? text + "\n" : "") + newText;
				textArea.setValue(text);
			};
			page.add(textArea, {
				flex: 1
			});

			var rpc;
			var mycall = null;
			var test;

			start.addListener("execute", function() {
				try {
					// clear the results area
					textArea.setValue(null);

					var rpc = new qx.io.remote.Rpc(defaultURL, 'testService');
					rpc.setTimeout(10000);

					test = "getCurrentTimestamp";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: now=" + result.now);
					textArea.append("result: jsonDate=" + result.json.toString());

					test = "getInteger";
					textArea.append("Calling '" + test + "'");
					var result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a number, got " + typeof (result) + ": " + (typeof (result) == "number" && isFinite(result) ? "true" : "false"));

					test = "isInteger";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, 1);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an integer: " + result);

					test = "getString";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a string: " + (typeof (result) == "string"));

					test = "isString";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, "Hello World");
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a string: " + result);

					test = "getNull";
					textArea.append("Calling '" + test + "'");
					var result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns null: " + (typeof (result) == "object" && result === null ? "true" : "false"));

					test = "isNull";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, null);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns null: " + result);

					test = "getArrayInteger";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an array: " + ((typeof (result) == "object") && (result instanceof Array)));

					test = "getArrayString";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an array: " + ((typeof (result) == "object") && (result instanceof Array)));

					var dataArray = new Array(5);

					for (var i = 0; i < 5; i++) {
						dataArray[i] = i;
					}

					test = "isArray";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, dataArray);
					textArea.append("result: {" + result + "}");

					dataArray = new Array(5);

					for (i = 0; i < 5; i++) {
						dataArray[i] = "Element " + i;
					}

					test = "isArray";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, dataArray);
					textArea.append("result: {" + result + "}");

					test = "getFloat";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a float: " + (typeof (result) == "number"));

					test = "getObject";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an object: " + (typeof (result) == "object"));

					test = "isObject";
					textArea.append("Calling '" + test + "'");
					var obj = new Object();
					obj.s = "Hi there.";
					obj.n = 23;
					obj.o = new Object();
					obj.o.s = "This is a test.";
					result = rpc.callSync(test, obj);
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns an object: " + result);

					test = "getTrue";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean = true: " + (typeof (result) == "boolean"));

					test = "getFalse";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean = false: " + (typeof (result) == "boolean"));

					test = "isBoolean";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, true);
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean: " + result);

					test = "isBoolean";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, false);
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean: " + result);

					Date.prototype.classname = "Date";
					var date = new Date();
					test = dateMethod;
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, date);
					textArea.append("result: {" + result + "}");
					console.log(result);
					// textArea.append("Returns a date object, got " + (result.classname
					// == date.classname));
					// textArea.append("Returns matching time " + date.getTime() + " = " +
					// result.getTime() + " :" + (result.getTime() == date.getTime()));

					dataArray = new Array();
					dataArray[0] = true;
					dataArray[1] = false;
					dataArray[2] = 1;
					dataArray[3] = 1.1;
					dataArray[4] = "Hello World";
					dataArray[5] = new Array(5);
					dataArray[6] = new Object();

					test = "getParams";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test, dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4], dataArray[5], dataArray[6]);
					textArea.append("result: {" + result + "}");

					for (i = 0; i < dataArray.length; i++) {
						var matches;
						if (typeof (dataArray[i]) === 'object') {
							matches = (result[i].toString() === dataArray[i].toString());
						}
						else {
							matches = (result[i] === dataArray[i]);
						}
						textArea.append("Returned parameter (" + i + ") value '" + result[i] + "' matches '" + dataArray[i] + "': " + matches);
						textArea.append("Returned parameter (" + i + ") type '" + typeof (result[i]) + "' matches '" + typeof (dataArray[i]) + "': " + (typeof (result[i]) === typeof (dataArray[i])));
					}

					test = "getError";
					textArea.append("Calling '" + test + "'");
					result = rpc.callSync(test);
					// should never get here; we should receive an exception
					textArea.append("ERROR: Should have received an exception!  Got: " + result);

				}
				catch (ex) {
					console.log("Exception on test " + test + ": " + ex);
				}
			});
		},

		rpcServerFunctionalityAsync: function(page, description) {
			page.add(new qx.ui.basic.Label("URL:"));
			var defaultURL = qx.io.remote.Rpc.makeServerURL();
			var url = new qx.ui.basic.Label('<strong>' + defaultURL + '</strong>');
			url.setRich(true);
			url.setMarginBottom(10);
			page.add(url);

			page.add(new qx.ui.basic.Label("Service:"));
			var service = new qx.ui.basic.Label("<strong>testService</strong>");
			service.setRich(true);
			service.setMarginBottom(20);
			page.add(service);

			var hBox = new qx.ui.container.Composite(new qx.ui.layout.HBox(4));
			hBox.set({
				width: 60
			});
			page.add(hBox);

			var start = new qx.ui.form.Button("Start Test");
			hBox.add(start);

			var textArea = new qx.ui.form.TextArea();
			textArea.append = function(newText) {
				var text = textArea.getValue();
				text = (text ? text + "\n" : "") + newText;
				textArea.setValue(text);
			};
			page.add(textArea, {
				flex: 1
			});

			var mycall = null;
			var test;
			var testNum;

			start.addListener("execute", function() {
				textArea.setValue(null);

				var obj;
				var date;
				var dataArray;

				/*
				 * Create an array of each of the tests. Each array element is itself an
				 * array of two function: the first to issue the test request, and the
				 * second to validate the result.
				 */
				var tests = [ [ function() {
					test = "getCurrentTimestamp";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: now=" + result.now);
					textArea.append("result: jsonDate=" + result.json.toString());
				} ],

				[ function() {
					test = "getInteger";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a number, got " + typeof (result) + ": " + (typeof (result) == "number" && isFinite(result) ? "true" : "false"));
				} ],

				[ function() {
					test = "isInteger";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, 1);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an integer: " + result);
				} ],

				[ function() {
					test = "getString";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a string: " + (typeof (result) == "string"));
				} ],

				[ function() {
					test = "isString";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, "Hello World");
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a string: " + result);
				} ],

				[ function() {
					test = "getNull";
					textArea.append("Calling '" + test + "'");
					var mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns null: " + (typeof (result) == "object" && mycall === null ? "true" : "false"));
				} ],

				[ function() {
					test = "isNull";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, null);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns null: " + result);
				} ],

				[ function() {
					test = "getArrayInteger";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an array: " + ((typeof (result) == "object") && (result instanceof Array)));
				} ],

				[ function() {
					test = "getArrayString";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an array: " + ((typeof (result) == "object") && (result instanceof Array)));
				} ],

				[ function() {
					dataArray = new Array(5);

					for (var i = 0; i < 5; i++) {
						dataArray[i] = i;
					}
					;

					test = "isArray";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, dataArray);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an array: " + result);
				} ],

				[ function() {
					dataArray = new Array(5);

					for (var i = 0; i < 5; i++) {
						dataArray[i] = "Element " + i;
					}
					;

					test = "isArray";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, dataArray);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an array: " + result);
				} ],

				[ function() {
					test = "getFloat";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a float: " + (typeof (result) == "number"));
				} ],

				[ function() {
					test = "getObject";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns an object: " + (typeof (result) == "object"));
				} ],

				[ function() {
					test = "isObject";
					textArea.append("Calling '" + test + "'");
					obj = new Object();
					obj.s = "Hi there.";
					obj.n = 23;
					obj.o = new Object();
					obj.o.s = "This is a test.";
					mycall = rpc.callAsync(handler, test, obj);
				},

				function(result) {
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns an object: " + result);
				} ],

				[ function() {
					test = "isBoolean";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, false);
				},

				function(result) {
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean: " + result);
				} ],

				[ function() {
					test = "isBoolean";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, true);
				},

				function(result) {
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean: " + result);
				} ],

				[ function() {
					test = "getTrue";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean = true: " + (typeof (result) == "boolean"));
				} ],

				[ function() {
					test = "getFalse";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					textArea.append("result: {" + result.toString() + "}");
					textArea.append("Returns a boolean = false: " + (typeof (result) == "boolean"));
				} ],

				[ function() {
					Date.prototype.classname = "Date";
					date = new Date();
					test = "getParam";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, date);
				}, function(result) {
					textArea.append("result: {" + result + "}");
					textArea.append("Returns a date object, got " + (result.classname == date.classname));
					textArea.append("Returns matching time " + date.getTime() + " = " + result.getTime() + " :" + (result.getTime() == date.getTime()));
				}

				],

				[ function() {
					dataArray = new Array();
					dataArray[0] = true;
					dataArray[1] = false;
					dataArray[2] = 1;
					dataArray[3] = 1.1;
					dataArray[4] = "Hello World";
					dataArray[5] = new Array(5);
					dataArray[6] = new Object();

					test = "getParams";
					textArea.append("Calling '" + test + "'");
					mycall = rpc.callAsync(handler, test, dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4], dataArray[5], dataArray[6]);
				},

				function(result) {
					textArea.append("result: {" + result + "}");

					for (var i = 0; i < dataArray.length; i++) {
						textArea.append("Returned parameter (" + i + ") value '" + result[i] + "' matches '" + dataArray[i] + "': " + (result[i].toString() == dataArray[i].toString()));
						textArea.append("Returned parameter (" + i + ") type '" + typeof (result[i]) + "' matches '" + typeof (dataArray[i]) + "': " + (typeof (result[i]) == typeof (dataArray[i])));
					}
					;
				} ],

				[ function() {
					test = "getError";
					textArea.append("Calling '" + test + " (method 1)'");
					mycall = rpc.callAsync(handler, test);
				},

				function(result) {
					// should never get here; we should receive an exception
					textArea.append("ERROR: Should have received an exception!  " + "Got: " + result);
				} ],

				];

				/*
				 * This is the generic handler, used by each of the tests. It ascertains
				 * whether an exception occured and alert()s with the exception if so;
				 * otherwise it calls the result validation function and then starts the
				 * next test.
				 */
				var handler = function(result, ex, id) {
					mycall = null;
					if (ex !== null) {
						textArea.append("Async(" + id + ") exception: " + ex);
					}
					else {
						// display results of the completed test
						console.log(testNum);
						console.log(tests[testNum][1]);
						tests[testNum][1](result); // [][1] = validate response
					}

					// start the next test
					++testNum;

					// Are we done?
					if (testNum < tests.length) {
						// Nope. Run the next test.
						tests[testNum][0]();
					}
				};

				// Determine which transport to use
				var rpc = new qx.io.remote.Rpc(defaultURL, 'testService');
				rpc.setTimeout(10000);

				// start the first test
				testNum = 0;
				tests[testNum][0](); // [][0] = request
			});
		},

		remoteTable: function(page, description) {
			// Instantiate an instance of our local remote data model
			var dm = new rpcexample.RemoteDataModel();

			// Set the column headings
			dm.setColumns([ "Year", "Leap Year" ], [ "year", "leap" ]);

			// Instantiate a table
			var table = new qx.ui.table.Table(dm);
			table.set({
				margin: 20
			});

			// Get the table column model
			var tcm = table.getTableColumnModel();

			var nc = new qx.ui.table.cellrenderer.Number();
			tcm.setDataCellRenderer(0, nc);

			// Show leap year as a boolean (checkbox)
			tcm.setDataCellRenderer(1, new qx.ui.table.cellrenderer.Boolean());

			page.add(table, {
				flex: 1
			});
		},
		
		refreshSession: function(page, description) {
			var hBox = new qx.ui.container.Composite(new qx.ui.layout.HBox(4));
			hBox.set({
				width: 120
			});
			page.add(hBox);			
			
			var sendRefreshButton = new qx.ui.form.Button("Call refreshSession");
			hBox.add(sendRefreshButton);

			var responseLabel = new qx.ui.basic.Label("Response:");
			page.add(responseLabel);
			var response = new qx.ui.basic.Label();
			response.setRich(true);
			page.add(response);
			
			var rpc = new qx.io.remote.Rpc();
			rpc.setUrl(qx.io.remote.Rpc.makeServerURL());
			rpc.setServiceName('testService');
			rpc.setTimeout(60000);
			
			sendRefreshButton.addListener('execute', function() {
				rpc.refreshSession(function(flag) {		
				  response.setValue('refreshSession returned: <strong>' + flag + '</strong>');
				});
			});
		}
	}
});