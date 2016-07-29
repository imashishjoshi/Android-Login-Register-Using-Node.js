var MongoDB 	= require('mongodb').Db;
var Server 		= require('mongodb').Server;

var dbName = 'login-register';
var dbHost = 'localhost'
var dbPort = 27017;

var db = new MongoDB(dbName, new Server(dbHost, dbPort, {auto_reconnect: true}), {w: 1});

db.open(function(err, doc){

	if(err){
		console.log(err);		
	}else{
		console.log('Database connected to '+ dbName);
	}	
});

var accounts = db.collection('accounts');



module.exports = {
	register: function(user,res){				
		accounts.findOne({email: user.email },function (err, docs) {
			
			if(!err){
				if(!docs){
					accounts.findOne({username: user.username },function (err, docs) {
						if(!docs){

							accounts.insert(user,function(err,doc){

								if(!err){

									res.send('User Registered');


								}else{

									res.send('Problem While User Registered');
								}

							});	

						}else{
							res.send('Username Already Taken');
						}
					});



				}else{
					res.send('Email Already Registered');
				}

			}else{

				res.send('Database Error' + err);
			}
		});	

	}
}	