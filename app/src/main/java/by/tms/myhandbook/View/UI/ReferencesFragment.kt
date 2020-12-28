package by.tms.myhandbook.View.UI

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.tms.myhandbook.Model.HandbookDatabase
import by.tms.myhandbook.R
import by.tms.myhandbook.Section
import by.tms.myhandbook.SectionAdapter
import by.tms.myhandbook.View.MainActivity
import by.tms.myhandbook.ViewModel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.references_fragment.*

class ReferencesFragment(/*val database: HandbookDatabase*/) : Fragment() {

    var sectionId = 0
    lateinit var adapter: SectionAdapter
    lateinit var viewModel: ReferencesViewModel
    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(activity as MainActivity).get(ReferencesViewModel::class.java)
        Log.e("!!!", "Start!")
        return inflater.inflate(R.layout.references_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        adapter = SectionAdapter(viewModel.list.value!!,navController, activity as MainActivity)
        this.rcview.adapter = adapter
        this.rcview.layoutManager = LinearLayoutManager(context)
//        viewModel.list.value = mutableListOf()
        Log.e("!!!", "Section list: " + viewModel.list.value!!.toString() )

        //update section id
//        if (viewModel.list.value!!.isNotEmpty()){
//            sectionId = viewModel.list.value!!.last().id + 1
//        }
        adapter.reload()

        fab.setOnClickListener { view ->

//            viewModel.list.value!!.add(Section(++sectionId, "newSection" + sectionId, "asdadads", "adsadsadasdadasdasdasdasdadsad"))

            navController.navigate(R.id.addSection)
//---            database.sectionDao().insertSection(sectionList.last())
            adapter.reload()


        viewModel.list.observe(viewLifecycleOwner, Observer {
//            adapter.reload()
//            Snackbar.make(view, "Добавлена новая скция", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}