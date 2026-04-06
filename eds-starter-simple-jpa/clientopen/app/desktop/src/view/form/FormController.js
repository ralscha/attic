Ext.define('Starter.view.form.FormController', {
    extend: 'Ext.app.ViewController',

    fillRemark() {
        formLoadService.getRemark(result => {
            this.getView().setValues({
                remarks: result
            });
        });
    },

    load() {
        this.getView().load({
            success: () => this.getView().setValues({'date': new Date()})
        });
    },

    submit() {
        const form = this.getView();
        if (form.validate()) {
            form.submit({
                success: (form, action) => {
                    this.getView().setValues({
                        remarks: action.result.response
                    });
                }
            });
        } else {
            Ext.Msg.alert('Form is not valid', 'Please fill in all mandatory fields');
        }
    }

});
