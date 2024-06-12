const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    resolve: {
      fallback: {
        "path": require.resolve("path-browserify")
      }
    },
    module: {
      rules: [
        {
          test: /\.csv$/,
          use: [
            {
              loader: 'csv-loader',
              options: {
                dynamicTyping: true,
                header: true,
                skipEmptyLines: true
              }
            }
          ]
        }
      ]
    }
  },
  devServer: {
    port: 8888,
    client: {
        overlay: false
    },
  }
})
