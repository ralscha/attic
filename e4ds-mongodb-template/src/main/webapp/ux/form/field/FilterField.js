Ext.define('Ext.ux.form.field.FilterField', {
	extend: 'Ext.form.field.Trigger',
	alias: 'widget.filterfield',
	triggerCls: Ext.baseCSSPrefix + 'form-search-trigger',
	requires: [ 'Ext.ux.form.field.ClearButton' ],
	plugins: [ 'clearbutton' ],

	initComponent: function() {
		this.callParent(arguments);
		this.on('specialkey', function(f, e) {
			if (e.getKey() == e.ENTER) {
				this.fireEvent('filter', this, this.getValue());
			}
		}, this);
		this.on('change', function(f, val) {
			if (!val) {
				this.fireEvent('filter', this, this.getValue());
			}
		}, this);
	},

	onTriggerClick: function() {
		this.fireEvent('filter', this, this.getValue());
	},
});
