var express =require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());

app.get('/',function(req,res){
	res.send('Hello Server !');
});

app.listen('80');
console.log('Server Started');