import resolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import replace from '@rollup/plugin-replace';
import babel from '@rollup/plugin-babel';
import terser from '@rollup/plugin-terser';

const production = (process.env.NODE_ENV === 'production');

const plugins = createPluginConfig([
	// The last two versions of each browser, excluding versions
	// that don't support <script type="module">.
	'last 2 Chrome versions', 'not Chrome < 60',
	'last 2 Safari versions', 'not Safari < 10.1',
	'last 2 iOS versions', 'not iOS < 10.3',
	'last 2 Firefox versions', 'not Firefox < 54',
	'last 2 Edge versions', 'not Edge < 15',
  ]);

const legacyPlugins = createPluginConfig([
	'> 1%',
	'last 2 versions',
	'Firefox ESR',
  ]);

function createPluginConfig(browsers) {
	return [
		babel({
			babelHelpers: 'bundled',
			babelrc: false,
			exclude: 'node_modules/**',
			presets: [
				['@babel/preset-env', {
				  debug: false,
				  loose: true,
				  modules: false,
				  useBuiltIns: 'usage',
				  corejs: 3,
				  targets: {
					browsers: browsers,
				  },
				}],
			  ],
			  plugins: ['@babel/plugin-syntax-dynamic-import']
		}),	
		resolve({
			mainFields: ['module', 'main', 'jsnext'],
			browser: true
		}),
		commonjs(),
		replace({
			exclude: 'node_modules/**',
			preventAssignment: true,
			ENV: JSON.stringify(process.env.NODE_ENV || 'development'),
		}),
		terser()
	];
}

export default [{
	input: 'src/js/main.js',
	output: {
	  file: 'dist/bundle.js',
      format: 'iife',
      name: 'starter',
      sourcemap: !production
    },
	plugins
}, {
	input: 'src/js/main-legacy.js',
	output: {
      file: 'dist/bundle-legacy.js',
      format: 'iife',
      name: 'starter',
      sourcemap: !production
    },
	plugins: legacyPlugins
}];