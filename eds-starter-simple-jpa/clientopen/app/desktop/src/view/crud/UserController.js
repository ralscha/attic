Ext.define('Starter.view.crud.UserController', {
    extend: 'Ext.app.ViewController',

    onNamefilterChange(field, value) {
        const userStore = this.getStore('users');

        if (!Ext.isEmpty(value)) {
            userStore.addFilter({
                id: 'nameFilter',
                filterFn: item => {
                    const filterValue = value.toLowerCase();
                    const firstName = item.get('firstName');
                    const lastName = item.get('lastName');
                    const email = item.get('email');

                    if (!Ext.isEmpty(firstName)) {
                        if (firstName.toLowerCase().indexOf(filterValue) >= 0) {
                            return true;
                        }
                    }
                    if (!Ext.isEmpty(lastName)) {
                        if (lastName.toLowerCase().indexOf(filterValue) >= 0) {
                            return true;
                        }
                    }
                    if (!Ext.isEmpty(email)) {
                        if (email.toLowerCase().indexOf(filterValue) >= 0) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        } else {
            userStore.removeFilter('nameFilter');
        }
    },

    onDepartmentFilterChange(field, value) {
        const userStore = this.getStore('users');

        if (!Ext.isEmpty(value)) {
            userStore.addFilter({
                id: 'departmentFilter',
                property: 'departmentId',
                exactMatch: true,
                value
            });
        } else {
            userStore.removeFilter('departmentFilter');
        }
    },

    departmentRenderer(value) {
        if (value) {
            return Ext.getStore('Departments').getById(value).get('name');
        }
        return value;
    },

    deleteUser() {
        Ext.Msg.confirm('Really delete?', 'Are you sure?', this.onDeleteUserConfirm, this);
    },

    onDeleteUserConfirm(choice) {
        if (choice === 'yes') {
            const selectedUser = this.getViewModel().get('selectedUser');
            selectedUser.erase({
                callback: e => {
                    Ext.toast({
                        message: 'User deleted',
                        title: 'Info'
                    });
                }
            });
        }
    },

    newUser() {
        const newUser = new Starter.model.User({
            lastName: '',
            firstName: '',
            email: '',
            department: ''
        });

        this.getStore('users').insert(0, newUser);
    },

    onEditSubmit() {
        console.log(arguments);
    }

});
