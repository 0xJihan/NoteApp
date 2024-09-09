package com.jihan.noteapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jihan.noteapp.adapter.NotesAdapter
import com.jihan.noteapp.databinding.FragmentMainBinding
import com.jihan.noteapp.retrofit.NoteApi
import com.jihan.noteapp.utils.Constants.TAG
import com.jihan.noteapp.utils.NetworkResult
import com.jihan.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    private  var _binding : FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container, false)
        notesAdapter = NotesAdapter()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel.getNotes()
        binding.noteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = notesAdapter

        bindObservers()
        

    }


    private fun bindObservers() {
        noteViewModel.notesLiveData.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = false

            when (it){
                is NetworkResult.Loading -> binding.progressBar.isVisible = true
                is NetworkResult.Success -> {
                    notesAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    // prevent memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}