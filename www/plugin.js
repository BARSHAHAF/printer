
var exec = require('cordova/exec');

var PLUGIN_NAME = 'BarPrinter';

var BarPrinter = {
   PrintPdf: function(phrase, cb,ecb) {
    exec(cb, ecb, PLUGIN_NAME, 'PrintPdf', [phrase]);
  },
  echo: function(phrase, cb,ecb) {
    exec(cb, ecb, PLUGIN_NAME, 'echo', [phrase]);
  },
  getDate: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'getDate', []);
  }
};

module.exports = BarPrinter;
