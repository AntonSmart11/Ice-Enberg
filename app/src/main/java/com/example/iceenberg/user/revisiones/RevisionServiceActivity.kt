package com.example.iceenberg.user.revisiones

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityRevisionServiceBinding
import java.util.Calendar

class RevisionServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRevisionServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revision_service)
        binding = ActivityRevisionServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        //binding.fechaRevision.init(year, month, day, null)


    }


}