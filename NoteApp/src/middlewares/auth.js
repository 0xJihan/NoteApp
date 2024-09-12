const jwt = require('jsonwebtoken');

const auth = (req,res,next) =>{
    try {

        let token = req.headers.authorization;

        if (token){
        
            token = token.split(" ")[1];
            let user = jwt.verify(token,process.env.SECRET_KEY);
            req.userId = user.id;
        }
        
        // if token is not found or invalid
        else {
            res.status(401).json({"message" : "Unauthorized user"});
        }

        next();

    } catch (error) {
        res.status(401).json({"message" : "Unauthorized user"});
        console.log(error);
    }
}

module.exports = auth;