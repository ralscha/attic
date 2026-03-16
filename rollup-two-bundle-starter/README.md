# Rollup Babel Two Bundle Build

Rollup and Babel configuration and build script to deploy ES2015+ code to production (via `<script type="module">`) 
with legacy browser fallback support via `<script nomodule>`.

This starter app is an implementation of techniques described in Philip Walton's 
blog post [Deploying ES2015+ Code in Production Today](https://philipwalton.com/articles/deploying-es2015-code-in-production-today/) and Jason Miller's blog post [Modern Script Loading](https://jasonformat.com/modern-script-loading/)


## Usage
To view the site locally, run the following command:

```
npm run watch
```

This will build all the source files, watch for changes, and serve them from [`http://localhost:5000`](http://localhost:5000). 

To build the source files without watching for changes or starting a local server, run:
```
npm run build
```


### Production build
To generate a minified build start a production build with:
```
npm run dist
```
