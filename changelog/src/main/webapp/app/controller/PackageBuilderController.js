Ext.define('Changelog.controller.PackageBuilderController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated',
			removed: 'onViewClose'
		},
		customer: {
			select: 'onCustomerSelect'
		},
		buildButton: {
			click: 'onBuildClick'
		},
		buildOutput: true,
		createSetupPackage: true,
		internalBuild: true,
		busyImage: true,
		info: true
	},
	
	scrollBodyTask: null,
	
	init: function() {
		var me = this;
		var panelBody = this.getBuildOutput().up('panel').body;
		me.scrollBodyTask = new Ext.util.DelayedTask(function(){
			panelBody.scroll('b', 2000, true);
		});
		
		
		var path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/')+1);
		var sock = new SockJS(path + 'msgbus');
		me.stompClient = Stomp.over(sock);
		me.stompClient.debug = function() {};

		me.stompClient.connect({}, function(frame) {
			me.stompClient.subscribe("/queue/build", function(msg) {
				me.onMessage(JSON.parse(msg.body));
			});
		});		
				
	},
	
	onCustomerSelect: function(combo, selected) {
		this.getBuildButton().enable();
	},

	onBuildClick: function() {
		this.disableUI();
		this.clearOutput();
		packageBuilderService.startBuild(this.getCustomer().getValue(), this.getCreateSetupPackage().getValue(), this.getInternalBuild().getValue());
	},
	
	onMessage: function(msgs) {
		for (var i = 0; i < msgs.length; i++) {
			var msg = msgs[i];

			if (msg === 'THE_END') {
				this.enableUI();

				this.getBuildOutput().getStore().add({
					l: 'THE END THE END THE END THE END THE END THE END'
				});
				this.scrollBodyTask.delay(1);
			} else {
				this.getBuildOutput().getStore().add({l: msg});		   	    
			}
		}
		
		this.scrollBodyTask.delay(500);

	},

	clearOutput: function() {
		this.getBuildOutput().getStore().removeAll();
	},

	disableUI: function() {
		this.getBuildButton().disable();
		this.getCustomer().disable();
		this.getCreateSetupPackage().disable();
		this.getInternalBuild().disable();
		this.getBusyImage().show();
	},

	enableUI: function() {
		this.getBuildButton().enable();
		this.getCustomer().enable();
		this.getCreateSetupPackage().enable();
		this.getInternalBuild().enable();
		this.getBusyImage().hide();
	},

	onViewClose: function() {		
		if (this.stompClient) {
			this.stompClient.disconnect();
			this.stompClient = null;
		}
	},

	onActivated: function() {
		var me = this;

		me.getCustomer().getStore().load({
			params: {
				build: true
			}
		});

		configService.getValue('buildEnabled', function(result) {
			if (result == 'false') {
				me.disableUI();
				me.getInfo().show();
				Ext.Msg.alert(i18n.config_alert, i18n.config_builddisabled);
			} else {
				me.getInfo().hide();
				me.enableUI();
			}
			
		});
		
		packageBuilderService.isBuilding(function(result) {
			if (result.customer) {
				me.getCustomer().setValue(result.customer);
				me.disableUI();
			}
			Ext.Array.forEach(result.output, function(line) {
				me.getBuildOutput().getStore().add({
					l: line
				});
			});
			me.getBuildOutput().up('panel').body.scroll('b', Infinity, true);
		});
	}
});
