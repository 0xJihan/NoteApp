package com.jihan.noteapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.jihan.noteapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding?  = null
    private val binding : ActivityMainBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
//===================================== onCreate Methods ends here  ===========================


    // prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}