Ext.define('Packt.controller.TranslationManager', {
	extend: 'Ext.app.Controller',
	requires: ['Packt.view.Translation'],
	views: [ 'Translation' ],

	refs: [ {
		ref: 'translation',
		selector: 'translation'
	} ],

	init: function(application) {
		this.control({
			"translation menuitem": {
				click: this.onMenuitemClick
			},
			"translation": {
				beforerender: this.onSplitbuttonBeforeRender
			}
		});
	},

	onMenuitemClick: function(item, e, options) {
		var menu = this.getTranslation();

		menu.setIconCls(item.iconCls);
		menu.setText(item.text);

		localStorage.setItem("user-lang", item.iconCls);

		window.location.reload();
	},

	onSplitbuttonBeforeRender: function(abstractcomponent, options) {
		var lang = localStorage ? (localStorage.getItem('user-lang') || 'en') : 'en';
		abstractcomponent.iconCls = lang;

		if (lang === 'en') {
			abstractcomponent.text = 'English';
		} else if (lang === 'de') {
			abstractcomponent.text = 'Deutsch';
		} 
	}

});
