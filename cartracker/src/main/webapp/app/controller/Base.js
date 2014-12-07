/**
 * Base controller from which all others will extend
 */
Ext.define('CarTracker.controller.Base', {
	extend: 'Ext.app.Controller',

	/**
	 * Common way to retrieve full data record from the server before performing another action
	 * 
	 * @param {Ext.data.Record}
	 *            record
	 * @param {String}
	 *            scope
	 * @param {Function}
	 *            callbackFn
	 * @param {Object}
	 *            extraData
	 */
	loadDetail: function(record, scope, callbackFn, extraData) {
		// first, reject any changes currently in the store so we don't build up
		// an array of records to save by viewing the records
		record.store.rejectChanges();

		// make request for detail record
		record.self.load(record.internalId, {
			success: function(resultrecord, operation) {
				record.set(resultrecord.data);
				callbackFn.call(scope, record, extraData);
			}
		});
	}
});