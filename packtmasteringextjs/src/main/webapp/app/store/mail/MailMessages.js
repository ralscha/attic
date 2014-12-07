Ext.define('Packt.store.mail.MailMessages', {
    extend: 'Ext.data.Store',

    requires: [
        'Packt.model.mail.MailMessage'
    ],

    model: 'Packt.model.mail.MailMessage',
    pageSize: 20,
    storeId: 'mailMessages'
});