package com.example.iceenberg.revision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Adapters.RevisionAdapter
import com.example.iceenberg.Controllers.RevisionController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Revision
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityRevisionBinding

class RevisionActivity : AppCompatActivity(), RevisionAdapter.OnItemClickListener {

    private lateinit var binding: ActivityRevisionBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var revisionController: RevisionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRevisionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarRevisiones)

        supportActionBar?.apply {
            title = getString(R.string.main_revision_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarRevisiones.setNavigationOnClickListener{
            onBackPressed()
        }

        binding.addRevision.setOnClickListener {
            val intent = Intent(this,FormRevisionActivity::class.java)
            startActivity(intent)
        }

        //conexión con la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        revisionController = RevisionController(dbHelper)

        //RecyclerView
        val revisions = revisionController.getRevision()

        val recyclerView: RecyclerView = findViewById(R.id.revisiones_recyclerView)
        val adapter = RevisionAdapter(revisions,this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()

        //Actualizar la lista de revisiones en el recyclerView
        val revisions = revisionController.getRevision()
        (binding.revisionesRecyclerView.adapter as? RevisionAdapter)?.updateList(revisions)
    }

    override fun onItemClick(revision: Revision){
        val intent = Intent(this, FormRevisionActivity::class.java)
        intent.putExtra("revision",revision)
        startActivity(intent)
    }

}