Ext.define('Packt.view.film.SearchActor', {
    extend: 'Packt.view.sakila.SearchWindow',
    alias: 'widget.searchactor',

    width: 600,
    bodyPadding: 10,
    layout: {
        type: 'anchor'
    },
    title: 'Search and Add Actor',

    items: [
        {
            xtype: 'combo',
            store: 'film.SearchActors',
            displayField: 'firstName',
            valueField: 'id',
            typeAhead: false,
            hideLabel: true,
            hideTrigger:true,
            anchor: '100%',
            minChars: 2,

            displayTpl: new Ext.XTemplate(
                '<tpl for=".">' +
                    '{[typeof values === "string" ? values : values["lastName"]]}, ' +
                    '{[typeof values === "string" ? values : values["firstName"]]}' +
                '</tpl>'
            ),

            listConfig: {
                loadingText: 'Searching...',
                emptyText: 'No matching posts found.',

                // Custom rendering template for each item
                getInnerTpl: function() {
                    return '<h3><span>{lastName}, {firstName}</span></h3>' +
                        '{filmInfo}';
                }
            },
            pageSize: true
        }, {
            xtype: 'component',
            style: 'margin-top:10px',
            html: 'Live search requires a minimum of 2 characters.'
        }
    ]
});