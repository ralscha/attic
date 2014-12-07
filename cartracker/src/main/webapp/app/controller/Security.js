/**
 * Controller for all security functionality
 */
Ext.define('CarTracker.controller.Security', {
	extend: 'CarTracker.controller.Base',
	models: [ 'security.User' ],
	init: function() {
		this.listen({
			controller: {},
			component: {
				'menu[xtype=layout.menu] menuitem#logout': {
					click: this.doLogout
				}
			},
			global: {
				beforeviewportrender: this.processLoggedIn
			},
			store: {}
		});
	},

	/**
	 * Main method process security check
	 */
	processLoggedIn: function() {
		securityService.checkLogin(function(result, e) {
			if (e.status) {
				if (result) {
					CarTracker.loggedInUser = Ext.create('CarTracker.model.security.User', result);
					// fire global event aftervalidateloggedin
					Ext.globalEvents.fireEvent('aftervalidateloggedin');
				} else {
  				    window.location = 'logout';
				}
			} else {
				Ext.Msg.alert('Attention', 'Sorry, an error occurred during your request. Please try again.');
			}
		});
	},

	/**
	 * Handles logout
	 * 
	 * @param {Ext.button.Button}
	 *            button
	 * @param {Ext.EventObject}
	 *            e
	 * @param {Object}
	 *            eOpts
	 */
	doLogout: function(button, e, eOpts) {
		Ext.Msg.confirm('Attention', 'Are you sure you want to logout of Car Tracker?', function(button) {
			if (button == 'yes') {
				window.location = 'logout';
			}
		});
	}
});