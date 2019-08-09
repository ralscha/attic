Ext.define('Golb.view.main.MainController', {
	extend: 'Ext.app.ViewController',

	// # = application
	// * = any controller
	listen: {
		controller: {
			'#': {
				unmatchedroute: 'handleUnmatchedRoute',
				pwreset: 'onPwReset'
			},
			'*': {
				signedin: 'onSignedIn',
				notsignedin: 'onNotSignedIn',
				logout: 'onLogout',
				navigationChanged: 'onNavigationChanged'
			}
		}
	},

	routes: {
		'auth.signin': {
			before: 'checkExistingSession',
			action: 'routeToAuthSignin'
		},

		'auth.:node': {
			action: 'routeToAuth',
			conditions: {
				':node': '(pwreset|pwresetconfirm|pwresetreq|signout|signin2fa)'
			}
		},

		':node': {
			before: 'checkSession',
			action: 'routeTo',
			conditions: {
				':node': '(?!auth.)([a-z\\.]+)'
			}
		}
	},

	init: function() {
		Ext.create('Golb.view.base.GridCellToolTip', {
			target: this.getView().getEl()
		});
	},
	
	onSignedIn: function(event, user) {
		var me = this;

		if (user.preAuth) {
			me.redirectTo('auth.signin2fa');
			return;
		}

		Golb.app.authUser = user;
		if (localStorage.golb_locale !== 'en') {
			localStorage.golb_locale = 'en';
			window.location.reload();
		}

		me.getViewModel().set('fullName', user.firstName + ' ' + user.lastName);

		if (localStorage.golb_navigation_micro === 'true') {
			this.onToggleNavigationSize();
		}
		
		var navigationStore = Ext.getStore('navigation');
		navigationStore.load({
			callback: function() {
				me.appready = true;
				me.fireEvent('appready', this);
				if (Golb.app.authUser) {
					if (me.savedHash) {
						me.redirectTo(me.savedHash);
						delete me.savedHash;
					}
					else {
						if (Golb.app.authUser.autoOpenView) {
							me.redirectTo(Golb.app.authUser.autoOpenView);
						}
					}

					Ext.direct.Manager.getProvider('heartbeat').connect()
				}
			}
		});

		Ext.direct.Manager.on('exception', function(e) {
			if (e.message === 'accessdenied') {
				me.setCurrentView('main.Error403Window', true);
			}
			else {
				me.setCurrentView('main.Error500Window', true);
			}
		});

		Ext.create('Golb.store.Tags').load();
	},

	onNotSignedIn: function() {
		delete Golb.app.authUser;
		this.appready = true;
		this.fireEvent('appready', this);
		this.redirectTo('auth.signin');
	},

	onLogout: function() {
		var uri = window.location.toString();
		var cleanUri = uri.substring(0, uri.indexOf("?"));
		window.history.replaceState({}, document.title, cleanUri);

		delete Golb.app.authUser;
		this.appready = true;
		this.fireEvent('appready', this);
		this.redirectTo('auth.signout');
	},

	onResetRequestBeforeRender: function(form) {
		var key = 'golb_login';
		var val = sessionStorage.getItem(key);
		if (val) {
			sessionStorage.removeItem(key);
			form.down('textfield[name=email]').setValue(val);
		}
	},	
	
	onPwReset: function(e, token) {
		var uri = window.location.toString();
		var cleanUri = uri.substring(0, uri.indexOf("?"));
		window.history.replaceState({}, document.title, cleanUri);

		Golb.app.pwResetToken = token;

		this.appready = true;
		this.redirectTo('auth.pwreset');
	},

	routeToAuthSignin: function() {
		this.setCurrentView('auth.Signin', true);
	},

	routeToAuth: function(node) {
		switch (node) {
		case 'pwreset':
			this.setCurrentView('auth.PwReset', true);
			break;
		case 'pwresetconfirm':
			this.setCurrentView('auth.PwResetConfirm', true);
			break;
		case 'pwresetreq':
			this.setCurrentView('auth.PwResetReq', true);
			break;
		case 'signout':
			this.setCurrentView('auth.Signout', true);
			break;
		case 'signin2fa':
			this.setCurrentView('auth.SigninTwoFa', true);
			break;
		}
	},

	routeTo: function(node) {
		if (node === 'userconfig') {
			this.setCurrentView('userconfig.Panel', true);
		}
		else {
			this.setCurrentView(node);
		}
	},

	checkExistingSession: function(action) {
		if (!this.appready) {
			this.on('appready', Ext.Function.bind(this.checkExistingSession, this, arguments), this, {
				single: true
			});
		}
		else {
			if (Golb.app.authUser) {
				if (Golb.app.authUser.autoOpenView) {
					action.stop();
					this.redirectTo(Golb.app.authUser.autoOpenView);
				}
				else {
					action.resume();
				}
			}
			else {
				action.resume();
			}
		}
	},

	checkSession: function(node, action) {
		if (!this.appready) {
			this.savedHash = node;
			this.on('appready', Ext.Function.bind(this.checkSession, this, arguments), this, {
				single: true
			});
		}
		else {
			if (Golb.app.authUser) {
				action.resume();
			}
			else {
				this.savedHash = node;
				action.stop();
				this.redirectTo('auth.signin');
			}
		}
	},

	handleUnmatchedRoute: function(node) {
		if (node !== '') {
			this.setCurrentView('main.Error404Window', true);
		}
	},

	onNavigationChanged: function(source, title) {
		this.getViewModel().set('navigationTitle', title);
	},
	
	setCurrentView: function(hashTag, internal) {
		var view;
		var navigationTreeList = this.lookup('navigationTreeList');

		if (!internal) {
			var navigationStore = Ext.getStore('navigation');
			var node = navigationStore.findNode('routeId', hashTag);

			if (node) {
				navigationTreeList.suspendEvent('selectionchange');
				navigationTreeList.setSelection(node);
				navigationTreeList.resumeEvent('selectionchange');
				view = node.get('view');
				this.getViewModel().set('navigationTitle', node.get('text'));
			}
			else {
				this.getViewModel().set('navigationTitle', null);
				navigationTreeList.setSelection(null);
				view = null;
			}
		}
		else {
			if (hashTag === 'userconfig.Panel') {
				this.getViewModel().set('navigationTitle', i18n.userconfig);
			}
			else {
				this.getViewModel().set('navigationTitle', null);
			}
			navigationTreeList.setSelection(null);
			view = hashTag;
		}

		// Kill any previously routed window
		var lastView = this.getViewModel().get('currentView');
		if (lastView && lastView.isWindow) {
			lastView.destroy();
		}

		var mainCardPanel = this.lookup('mainCardPanel');
		var mainLayout = mainCardPanel.getLayout();

		var existingItem = mainCardPanel.child('component[routeId=' + hashTag + ']');
		var newView;

		if (!existingItem) {
			newView = Ext.create('Golb.view.' + (view || 'main.Error404Window'), {
				hideMode: 'offsets',
				routeId: hashTag
			});

			if (!newView.isWindow) {
				Ext.suspendLayouts();
				mainLayout.setActiveItem(mainCardPanel.add(newView));
				Ext.resumeLayouts(true);
			}
		}
		else {
			if (existingItem !== mainLayout.getActiveItem()) {
				mainLayout.setActiveItem(existingItem);
			}
			newView = existingItem;
		}

		if (newView.isFocusable(true)) {
			newView.focus();
		}

		this.getViewModel().set('currentView', newView);

	},

	onLogoutClick: function() {
		Ext.Ajax.request({
			url: serverUrl + 'logout',
			method: 'POST'
		}).then(function(r) {
			sessionStorage.clear();
			var currentLocation = window.location.toString();
			var hashPos = currentLocation.indexOf("#");
			if (hashPos > 0) {
				currentLocation = currentLocation.substring(0, hashPos) + '?logout';
			}
			else {
				currentLocation += '?logout';
			}
			window.location.replace(currentLocation);
		});

	},

	onNavigationTreeSelectionChange: function(tree, node) {
		if (node && node.get('view')) {
			// cleanup mainCardPanel
			var mainCardPanel = this.lookup('mainCardPanel');
			mainCardPanel.removeAll();

			this.redirectTo(node.get("routeId"));
		}
	},

	onToggleNavigationSize: function() {
		var refs = this.getReferences();
		var navigationList = refs.navigationTreeList;
		var wrapContainer = refs.mainContainer;
		var collapsing = !navigationList.getMicro();
		var newWidth = collapsing ? 64 : 250;

		localStorage.golb_navigation_micro = collapsing;
		
			Ext.suspendLayouts();

		refs.appLogo.setWidth(newWidth);

		navigationList.setWidth(newWidth);
			navigationList.setMicro(collapsing);

		Ext.resumeLayouts();
		wrapContainer.updateLayout();
	},
		
	onShowCheatsheetClick: function() {
        new Ext.window.Window({
            title: 'Markdown Cheatsheet',
            closeable: true,
            autoShow: true,
            maximizeable: true,
            height: 700,
            width: 900,
            border: true,
            layout: 'fit',
            items: {
            	html: '<object data="resources/markdown-cheatsheet-online.pdf" type="application/pdf" width="100%" height="100%" typemustmatch></object>'
            }
        });
	}

});
