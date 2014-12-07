Ext.define('Packt.Application', {
	name: 'Packt',

	extend: 'Ext.app.Application',

	requires: [ 'overrides.AbstractMixedCollection', 'Packt.util.SessionMonitor', 'Packt.view.MyViewport', 'Packt.util.Util', 'Packt.util.Alert',
	            'Packt.controller.Login', 'Packt.controller.TranslationManager', 'Packt.controller.Menu', 'Packt.controller.security.Groups', 'Packt.controller.security.Users', 
	            'Packt.controller.staticData.AbstractController', 'Packt.controller.cms.Films',
				'Packt.controller.reports.SalesFilmCategory', 'Packt.controller.mail.Mail'
	            ],
	controllers: [ 'Login', 'TranslationManager', 'Menu', 'security.Groups', 'security.Users', 'staticData.AbstractController', 'cms.Films',
			'reports.SalesFilmCategory', 'mail.Mail' ],

	init: function() {
		var circularG = Ext.fly('circularG');
		if (circularG) {
			circularG.destroy();
		}
		splashscreen = Ext.getBody().mask('Loading application', 'splashscreen');
		splashscreen.addCls('splashscreen');

		Ext.DomHelper.insertFirst(Ext.query('.x-mask-msg')[0], {
			cls: 'x-splash-icon'
		});
	},

	launch: function() {
		Ext.draw.engine.ImageExporter.defaultUrl = "svg2png";
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

		var task = new Ext.util.DelayedTask(function() {

			loginService.getStatus(function(detail) {
				if (!detail.success) {
					splashscreen.fadeOut({
						duration: 1000,
						remove: true
					});
					splashscreen.next().fadeOut({
						duration: 1000,
						remove: true,
						listeners: {
							afteranimate: function(el, startTime, eOpts) {
								Ext.widget('login');
							}
						}
					});
				} else {
					splashscreen.next().remove();
					splashscreen.remove();
					Ext.create('Packt.view.MyViewport');
					Ext.globalEvents.fireEvent('aftersuccessfullogin');
				}
			});

		});

		task.delay(1);

	}
});
