/* <debug> */
Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Packt': 'app',
		'Ext.ux': 'resources/extjs-gpl/4.2.2/ux'
	}
});
/* </debug> */

Ext.require('overrides.AbstractMixedCollection');

Ext.application({
    name: 'Packt',

    extend: 'Packt.Application',
    
    autoCreateViewport: false
});