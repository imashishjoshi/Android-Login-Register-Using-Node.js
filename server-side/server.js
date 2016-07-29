var express =require('express');
var bodyParser = require('body-parser');
var passwordHash = require('password-hash');
var fun = require('./function');

var app = express();
app.use(bodyParser.json());

app.post('/register',function(req,res){

	var user = req.body.user;

		if(user.name && user.email && user.password && user.username){

		var name = user.name;
		var password = passwordHash.generate(user.password);
		var email = user.email.toLowerCase();
		var username = user.username.toLowerCase();

		var usr = {
			'name' : name,
			'password' : password,
			'email' : email,
			'username' : username
			}
		
			fun.register(usr,res);

		}else{
			res.send('Error While Registering User !');
		}

});

app.listen('80');
console.log('Server Started');