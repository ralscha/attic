Ext.define('Weather.view.About', {
	extend: 'Ext.Container',
	requires: [ 'Ext.TitleBar' ],
	xtype: 'about',

	/* Configure the tab here */
	config: {
		title: 'About',
		iconCls: 'settings',

		/* create a title bar for the tab panel */

		items: {
			docked: 'top',
			xtype: 'titlebar',
			title: 'About'
		},

		html: 'The about page'
	}	

});