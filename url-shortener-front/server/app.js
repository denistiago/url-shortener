const express = require('express');
const morgan = require('morgan');
const path = require('path');
const axios = require('axios');
const URL = require('url');

const app = express();

const API_URL = process.env.API_URL || 'http://localhost:8080/api/urls' ;

// Setup logger
app.use(morgan(':remote-addr - :remote-user [:date[clf]] ":method :url HTTP/:http-version" :status :res[content-length] :response-time ms'));

// Serve static assets
app.use(express.static(path.resolve(__dirname, '..', 'build')));

app.get('/:hash', (req, res) => {	
	
	axios.get(`${API_URL}/${req.params.hash}`)
	  .then(function (response) {
	  	var url = URL.parse(response.data.url);	  	
	    res.redirect(url.protocol ? url.href : 'http://' + url.href)
	  })
	  .catch(function (error) {
	  	res.redirect('/404.html')
	  });
});


app.get('/', (req, res) => {
  res.sendFile(path.resolve(__dirname, '..', 'build', 'index.html'));
});

app.get('/404.html', (req, res) => {
  res.sendFile(path.resolve(__dirname, '..', 'build', '404.html'));
});


module.exports = app;