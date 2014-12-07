var lang = localStorage ? (localStorage.getItem('user-lang') || 'en') : 'en';
var file = 'resources/i18n/v1/' + lang + '.js';
var extjsFile = 'resources/extjs-gpl/4.2.2/locale/ext-lang-' + lang + '.js';

document.write('<script src="' + file + '"></script>');
document.write('<script src="' + extjsFile + '"></script>');
