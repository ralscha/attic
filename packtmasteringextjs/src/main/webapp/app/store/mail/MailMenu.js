Ext.define('Packt.store.mail.MailMenu', {
    extend: 'Ext.data.TreeStore',

    proxy: {
        type: 'direct',
        directFn: 'mailService.readMenu'
    }
});