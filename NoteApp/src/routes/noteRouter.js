const { deleteNote, updateNote, createNote, getNotes } = require('../controllers/noteControllers');
const auth = require('../middlewares/auth');

const noteRouter = require('express').Router();

noteRouter.get('/',auth,getNotes);

noteRouter.post('/',auth,createNote);


noteRouter.put('/:id',auth,updateNote);


noteRouter.delete('/:id',auth,deleteNote);


module.exports = noteRouter;


