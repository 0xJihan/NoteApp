const noteModel = require('../models/note');


const createNote = async (req,res) =>{
    const {title,description} = req.body;

    const newNote = new noteModel({
        title : title,
        description : description,
        userId : req.userId
    });

    try {
        await newNote.save();
        res.status(201).json(newNote);
    } catch (error) {
        console.log(error)
        res.status(500).json({
            "message" : "Something went wrong"
        });
    }

};



const updateNote = async (req, res) => {
    const id = req.params.id;  // Note ID from request parameters
    const { title, description } = req.body;  // New data for the note

    try {
        // Update the note with the given ID
        const updatedNote = await noteModel.findByIdAndUpdate(
            id,  // Note ID
            { title, description },  // Fields to update
            { new: true }  // Return the updated note
        );

        if (!updatedNote) {
            return res.status(404).json({ message: "Note not found" });
        }

        // Return the updated note
        res.status(200).json(updatedNote);

    } catch (error) {
        console.log(error);
        res.status(500).json({
            "message": "Something went wrong"
        });
    }
};



const deleteNote = async (req,res) =>{
    const id = req.params.id;

    try {
        // deleting note
        const note = await noteModel.findByIdAndDelete(id);
        // returing deleted notes as object
        res.status(202).json(note)
    } catch (error) {
        console.log(error)
        res.status(500).json({
            "message" : "Something went wrong"
        });
    }

};


const getNotes = async (req,res) =>{

     try {
        const notes = await noteModel.find({userId : req.userId});
        res.status(200).json(notes);

     } catch (error) {
        console.log(error);
        res.status(500).json({
            "message" : "Something went wrong"
        });
     }
};


module.exports = {
    createNote,
    updateNote,
    deleteNote,
    getNotes
}