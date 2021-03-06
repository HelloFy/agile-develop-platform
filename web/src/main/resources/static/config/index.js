// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path');
module.exports = {
  build: {
    env: require('./prod.env'),
    index: path.resolve(__dirname, '../../templates/index.ftl'),
    genTable: path.resolve(__dirname, '../../templates/gen/genTableForm.ftl'),
      genSchema: path.resolve(__dirname, '../../templates/gen/genSchemaForm.ftl'),
      docList: path.resolve(__dirname, '../../templates/gen/genDocList.ftl'),
      genUML: path.resolve(__dirname, '../../templates/gen/genGenUmlClassDiagramForm.ftl'),
      genCode: path.resolve(__dirname, '../../templates/gen/genCodeTemplateForm.ftl'),
      assetsRoot: path.resolve(__dirname, '../../static'),
    assetsSubDirectory: 'assets',
    assetsPublicPath: '/',
    productionSourceMap: false,
    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css']
  },
  dev: {
    env: require('./dev.env'),
    port: 8081,
    assetsRoot: path.resolve(__dirname, '../../static'),
    assetsSubDirectory: 'assets',
    assetsPublicPath: '/',
    proxyTable: {},
    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    cssSourceMap: false
  },
  buildDev:{
    env: require('./dev.env'),
    index: path.resolve(__dirname, '../../templates/index.ftl'),
      genTable: path.resolve(__dirname, '../../templates/gen/genTableForm.ftl'),
      genSchema: path.resolve(__dirname, '../../templates/gen/genSchemaForm.ftl'),
      port: 8081,
    assetsRoot: path.resolve(__dirname, '../../static'),
    assetsSubDirectory: 'assets',
    assetsPublicPath: '/',
    proxyTable: {},
    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    cssSourceMap: false
  }
};

