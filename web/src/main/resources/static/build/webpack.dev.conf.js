var config = require('../config');
var webpack = require('webpack');
var merge = require('webpack-merge');
var utils = require('./utils');
var baseWebpackConfig = require('./webpack.base.conf');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var HtmlWebpackPlugin = require('html-webpack-plugin');
// add hot-reload related code to entry chunks
Object.keys(baseWebpackConfig.entry).forEach(function (name) {
  baseWebpackConfig.entry[name] = ['./build/dev-client'].concat(baseWebpackConfig.entry[name])
});
module.exports = merge(baseWebpackConfig, {
  output: {
    path: config.build.assetsRoot,
    filename: utils.assetsPath('js/[name].js'),
    chunkFilename: utils.assetsPath('js/[id].js')
  },
  // eval-source-map is faster for development
  devtool: 'eval-source-map',
  plugins: [
    new webpack.DefinePlugin({
      'process.env': config.dev.env
    }),
    // https://github.com/glenjamin/webpack-hot-middleware#installation--usage
    new webpack.optimize.OccurrenceOrderPlugin(),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoErrorsPlugin(),
    new webpack.optimize.CommonsChunkPlugin({
        name: 'manifest',
        chunks: ['vendor']
    }),
    new ExtractTextPlugin(utils.assetsPath('css/[name].[contenthash].css')),
    // https://github.com/ampedandwired/html-webpack-plugin
    new HtmlWebpackPlugin({
      filename: config.build.index,
      template: 'src/template/index.html',
      inject: true,
/*      minify: {
        removeComments: true,
        collapseWhitespace: true,
        removeAttributeQuotes: true
        // more options:
        // https://github.com/kangax/html-minifier#options-quick-reference
      },*/
      chunks: ['vendor','manifest','index'],
      // necessary to consistently work with multiple chunks via CommonsChunkPlugin
        chunksSortMode: function (chunk1, chunk2) {
            var orders = ['manifest', 'vendor', 'index'];
            var order1 = orders.indexOf(chunk1.names[0]);
            var order2 = orders.indexOf(chunk2.names[0]);
            if (order1 > order2) {
                return 1;
            } else if (order1 < order2) {
                return -1;
            } else {
                return 0;
            }
        }
    }),
    new HtmlWebpackPlugin({
        filename: config.build.genTable,
        template: 'src/template/gen/genTableForm.html',
        inject: false,
      /*      minify: {
       removeComments: true,
       collapseWhitespace: true,
       removeAttributeQuotes: true
       // more options:
       // https://github.com/kangax/html-minifier#options-quick-reference
       },*/
        chunks: ['vendor','manifest','genTable'],
        // necessary to consistently work with multiple chunks via CommonsChunkPlugin
        chunksSortMode: function (chunk1, chunk2) {
            var orders = ['manifest', 'vendor', 'gen'];
            var order1 = orders.indexOf(chunk1.names[0]);
            var order2 = orders.indexOf(chunk2.names[0]);
            if (order1 > order2) {
                return 1;
            } else if (order1 < order2) {
                return -1;
            } else {
                return 0;
            }
        }
    }),
    new HtmlWebpackPlugin({
        filename: config.build.genSchema,
        template: 'src/template/gen/genSchemaForm.html',
        inject: false,
        /*      minify: {
         removeComments: true,
         collapseWhitespace: true,
         removeAttributeQuotes: true
         // more options:
         // https://github.com/kangax/html-minifier#options-quick-reference
         },*/
        chunks: ['vendor','manifest','genSchema'],
        // necessary to consistently work with multiple chunks via CommonsChunkPlugin
        chunksSortMode: function (chunk1, chunk2) {
            var orders = ['manifest', 'vendor', 'genSchema'];
            var order1 = orders.indexOf(chunk1.names[0]);
            var order2 = orders.indexOf(chunk2.names[0]);
            if (order1 > order2) {
                return 1;
            } else if (order1 < order2) {
                return -1;
            } else {
                return 0;
            }
        }
    })
  ]
});

