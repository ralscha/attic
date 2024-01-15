Ext.define('Todo.view.todo.MainController', {
	extend: 'Ext.app.ViewController',

	onItemclick: function(grid, record) {
		record.beginEdit();
	},

	onImageClick: function() {
		var todo = this.getViewModel().get('selectedTodo');
		var imgName = todo.get('imageName');
		console.log(imgName);
		Ext.create('Ext.window.Window', {
		    title: 'Original Image',
		    height: 700,
		    width: 1000,
		    scrollable: true,
		    autoScroll: true,
		    maximizable: true,
		    modal: true,
		    items: [{ 
		    	xtype: 'image',
		    	src : "todo/images/"+imgName,
		    	autoScroll: true 
		    }]
		}).show();
	},

	onNewClick: function() {
		var newTodo = new Todo.model.Todo({
			type: 'PRIVATE'
		});
		newTodo.beginEdit();
		this.getViewModel().set('selectedTodo', newTodo);
	},

	onDeleteClick: function() {
		var todo = this.getViewModel().get('selectedTodo');

		Ext.Msg.confirm('Attention', 'Do you really want to delete: ' + todo.get('title'), function(choice) {
			if (choice === 'yes') {
				todo.erase({
					success: function() {
						this.getStore('todos').reload();
					},
					scope: this
				});
			}
		}, this);
	},

	onSearchTitle: function() {
		this.getViewModel().set('searchButtonText', 'Search Title');
	},
	onSearchDescription: function() {
		this.getViewModel().set('searchButtonText', 'Search Description');
	},
	onSearchFulltext: function() {
		this.getViewModel().set('searchButtonText', 'Fulltextsearch');
	},
	
	onSearchClick: function() {
		this.getViewModel().set('selectedTodo', null);
		var store = this.getStore('todos');
		var value = this.lookup('filterTf').getValue();
		
		if (value) {
			var sbtext = this.getViewModel().get('searchButtonText');
			var type = 'title';
			if (sbtext === 'Search Description') {
				type = 'description';
			}
			else if (sbtext === 'Fulltextsearch') {
				type = 'fulltext';
			}
			else if (sbtext === 'Search Label') {
				type = 'label';
			}			
			store.filter('filterType', type);
			store.filter('filterValue', value);
		} else {
			store.removeFilter('filterType');
			store.removeFilter('filterValue');
		}
	},
	
	onSpecialKey: function(f,e) {
        if (e.getKey() == e.ENTER) {
        	this.onSearchClick();
        }
    },
    
	onGridSpecialKey: function(me, record, item, index, e, eOpts) {
        if (e.getKey() == e.F1) {
        	this.onImageClick();
        }
    },
	
	onSaveClick: function() {
		var todo = this.getViewModel().get('selectedTodo');
		this.getView().mask('Saving...');
		todo.save({
			success: function() {
				todo.endEdit();
				this.getStore('todos').reload();
				todo.beginEdit();
			},
			failure: function(record, operation) {
				var fieldErrors = JSON.parse(operation.getResponse().responseText).fieldErrors;
				var form = this.lookup('todoForm').getForm();
				fieldErrors.forEach(function(v) {
					var field = form.findField(v.field);
					if (field) {
						field.markInvalid(v.defaultMessage);
					}
				});
			},
			callback: function(record, operation, success) {
				this.getView().unmask();
			},			
			scope: this
		});
	},

	markInvalidFields: function(validations) {
		validations.forEach(function(validation) {
			var field = form.findField(validation.field);
			if (field) {
				field.markInvalid(validation.messages);
			}
		});
	},

	onCancelClick: function() {
		var todo = this.getViewModel().get('selectedTodo');
		todo.cancelEdit();
		this.getViewModel().set('selectedTodo', null);
	}

});
