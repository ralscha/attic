Ext.define('Todo.view.common.LanTextField', {
	extend : 'Ext.form.FieldContainer',
	mixins : [ 'Ext.form.Labelable', 'Ext.form.field.Field' ],
	alias : 'widget.lantextfield',

	defaultBindProperty : 'json',
	twoWayBindable : 'json',

	config : {
		json : null,
		fieldLabel : null,
		languages : [],
		required : null,
		inputType : 'textfield',
		cfg : null
	},

	initComponent : function() {
		this.json = '';
		this.buildField();
		this.callParent();
		this.initLabelable();
		this.initField();
		for (var i = 0; i < this.languages.length; i++) {
			this[this.languages[i] + 'Field'] = this.down('#' + this.languages[i]);
			Ext.apply(this[this.languages[i] + 'Field'], this.cfg);
		}
	},

	onChange : function(ta, v) {
		this[ta.itemId] = v;
		var jsonData = {};
		for (var i = 0; i < this.languages.length; i++) {
			jsonData[this.languages[i]] = this[this.languages[i]];
		}
		this.setJson(JSON.stringify(jsonData));
		this.checkChange();
	},

	onValidate : function(ta, v) {
		if (this.getRequired().indexOf(ta.itemId) > -1 && v.length < 1) {
			return this.getFieldLabel() + ' is required';
		}
		return true;
	},

	updateJson : function(v) {
		try {
			var j = JSON.parse(v);			
			for (var i = 0; i < this.languages.length; i++) {
				var key = this.languages[i];
				if (j.hasOwnProperty(key)) {
					this[key] = j[key];
					this[key + 'Field'].setValue(j[key]);
				}			
			}
		} catch (e) {
			for (var i = 0; i < this.languages.length; i++) {
				this[this.languages[i] + 'Field'].setValue(v);
			}
		}
	},

	// @private
	buildField : function() {
		var me = this;
		if(me.languages.length > 1) {
			var lanFields = [];
			for (var i = 0; i < me.languages.length; i++) {
				lanFields.push(
					{
						iconCls : 'flag-icon flag-icon-' +  me.languages[i],
						layout : 'fit',
						items : [ {
							xtype : me.inputType,
							itemId :  me.languages[i],
							listeners : {
								change : me.onChange.bind(me)
							},
							validator : function(val) {
								if (me.getRequired()) {
									return me.onValidate(this, val);
								}
								return true;
							}
						} ]
					}				
				);
			}
			
			me.items = [ {
				xtype : 'tabpanel',
				plain : true,
				ui : 'lantextfield',
				items : lanFields
			} ]
		} else {
			me.layout = 'fit',
			me.items = [ {
				xtype : me.inputType,
				itemId :  me.languages[0],
				listeners : {
					change : me.onChange.bind(me)
				},
				validator : function(val) {
					if (me.getRequired()) {
						return me.onValidate(this, val);
					}
					return true;
				}
			} ]
		}
			
	}

});