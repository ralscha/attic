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
qx.Class.define("rpcexample.RemoteDataModel", {
	extend: qx.ui.table.model.Remote,

	construct: function() {
		this.base(arguments);
		this.setColumnIds([ "year", "leap" ]);

		this.rpc = new qx.io.remote.Rpc(qx.io.remote.Rpc.makeServerURL(), 'tableTestService');
		this.rpc.setTimeout(10000);
	},

	members: {
		rpc: null,

		_loadRowCount: function() {
			this.rpc.callAsync(this._onRowCountCompleted.bind(this), "getRowCount");
		},

		_onRowCountCompleted: function(result, ex, id) {
			if (ex !== null) {
				console.log("Async(" + id + ") exception: " + ex + "<p>");
			}
			else {
				this._onRowCountLoaded(result);
			}
		},

		_loadRowData: function(firstRow, lastRow) {
			var sortColumn = this.getColumnId(this.getSortColumnIndex());
			var sortOrder = this.isSortAscending() ? "ASC" : "DESC";
			this.rpc.callAsync(this._onLoadRowDataCompleted.bind(this), "getRowData", firstRow, lastRow, sortColumn, sortOrder);
		},

		_onLoadRowDataCompleted: function(result, ex, id) {
			if (ex !== null) {
				console.log("Async(" + id + ") exception: " + ex + "<p>");
			}
			else {
				this._onRowDataLoaded(result);
			}
		}
	}
});