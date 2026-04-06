const Prism = require('prismjs');
const loadLanguages = require('prismjs/components/');
const fs = require('fs');
const components = require("prismjs/components");

const aliases = new Map();
aliases.set("js", "javascript");
aliases.set("py", "python");
aliases.set("rb", "ruby");
aliases.set("ps1", "powershell");
aliases.set("psm1", "powershell");
aliases.set("sh", "bash");
aliases.set("bat", "batch");
aliases.set("h", "c");
aliases.set("tex", "latex");
aliases.set("ts", "typescript");
aliases.set("kt", "kotlin");
aliases.set("proto", "protobuf");

in_file = process.argv[2];
out_file = process.argv[3];
language = process.argv[4];

let lang = aliases.get(language);
if (!lang) {
    lang = language;
}

if (lang in components.languages) {
    loadLanguages([lang]);
} else {
    lang = "markup";
}

const code = fs.readFileSync(in_file, 'utf8');
const highlight = Prism.highlight(code, Prism.languages[lang], lang);

fs.writeFileSync(out_file, highlight);
