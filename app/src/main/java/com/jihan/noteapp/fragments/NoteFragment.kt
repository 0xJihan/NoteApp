package com.jihan.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jihan.noteapp.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater,container, false)




        return binding.root
    }

    // ========================================== onCreateView() =================================================
    // prevent memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}