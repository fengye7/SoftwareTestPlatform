const fs = require('fs');
const path = require('path');

const readFilesFromDir = (dir, ext = '') => {
  return new Promise((resolve, reject) => {
    const dirPath = path.resolve(__dirname, `../${dir}`);
    fs.readdir(dirPath, (err, files) => {
      if (err) {
        reject(err);
      } else {
        const filteredFiles = ext ? files.filter(file => file.endsWith(ext)) : files;
        resolve(filteredFiles);
      }
    });
  });
};

module.exports = {
  readFilesFromDir
};
