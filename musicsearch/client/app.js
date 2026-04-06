/*
 * This file launches the application by asking Ext JS to create
 * and launch() the Application class.
 */
Ext.application({
    extend: 'MusicSearch.Application',

    name: 'MusicSearch',

    requires: [
        // This will automatically load all classes in the MusicSearch namespace
        // so that application classes do not need to require each other.
        'MusicSearch.*'
    ],

    // The name of the initial view to create.
    mainView: 'MusicSearch.view.main.Main'
});
