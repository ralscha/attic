Ext.define('Packt.util.Util', {

	statics: {
		required: '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
		
        showErrorMsg: function (text) {

            Ext.Msg.show({
                title:'Error!',
                msg: text,
                icon: Ext.Msg.ERROR,
                buttons: Ext.Msg.OK
            });
        }
	}
});