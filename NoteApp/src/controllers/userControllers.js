const userModel = require('../models/user');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

const signup = async (req,res) =>{
    // getting information from request body
    const {username,email,password} = req.body;

    try {
        // checking for exisiting user
        const isUserExist = await userModel.findOne({email : email})
        if (isUserExist){
            return res.status(200).json({"message" : "User already exists"})
        }
        
        // hashing password
        const hashedPassword = await bcrypt.hash(password,10);
        
        // inseting  new UserModel to database
        const result = await userModel.create({
            email : email,
            password : hashedPassword,
            username : username
        });

        // generating token 
        const token = jwt.sign({email : result.email , id : result._id},process.env.SECRET_KEY)
        // returning the results and the token
        res.status(201).json({
            user : result,
            token : token
        });

    } catch (error) {
        console.log(error);
        res.json({"message" : "Something went wrong in the server"})
    }

}

// ===========================================       LOGIN    ============================================================

const login = async (req,res) => {
    const {email,password} = req.body;

    try {

   // checking if user doesn't exists
   const isUserExist = await userModel.findOne({email : email});
   if (!isUserExist){
       return res.status(500).json({"message" : "User doesn't exists"})
   }

   // checking if password is same or not
   const isPasswordSame = await bcrypt.compare(password,isUserExist.password)

   if (!isPasswordSame){
    return res.status(400).json({"message" : "Invalid credentials"})
   }


     // generating token 
     const token = jwt.sign({email : isUserExist.email , id : isUserExist._id},process.env.SECRET_KEY)

     return res.status(201).json({
        user : isUserExist , token : token
     });

        
    } catch (error) {
        console.log(error);
        res.json({"message" : "Something went wrong in the server"})
    }
}

module.exports = {signup,login};