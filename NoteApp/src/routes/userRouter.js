const { signup, login } = require('../controllers/userControllers');

const userRouter = require('express').Router();


userRouter.post('/login',login);

userRouter.post('/signup',signup);




module.exports = userRouter;