Ext.define('E4ds.ux.form.field.FilterField', {
	extend: 'Ext.form.field.Trigger',
	alias: 'widget.filterfield',
	
	trigger1Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    trigger2Cls: Ext.baseCSSPrefix + 'form-clear-trigger',

	initComponent: function() {
		this.on('specialkey', function(f, e) {
			if (e.getKey() === e.ENTER) {
				this.fireEvent('filter', this, this.getValue());
			}
		}, this);
		
		this.callParent(arguments);
	},

	onTrigger1Click: function() {
		this.fireEvent('filter', this, this.getValue());
	},
	
	onTrigger2Click: function() {
		this.setValue('');
		this.fireEvent('filter', this, this.getValue());
	}
});
