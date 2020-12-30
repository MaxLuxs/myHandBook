package by.tms.myhandbook.View.UI

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.tms.myhandbook.R
import by.tms.myhandbook.Section
import by.tms.myhandbook.View.MainActivity
import by.tms.myhandbook.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.add_section_fragment.*

class addSection : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.add_section_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

//        viewModel.list.value = mutableListOf()
        navController = findNavController()

        button3.setOnClickListener(View.OnClickListener {


            Log.e("!!!", viewModel.secId.toString())
            viewModel.secList.value!!.add(Section(
                viewModel.secId++,
                    textEd1.text.toString(),
                textEd2.text.toString(),
                textEd3.text.toString(),
                viewModel.selectRef
            ))
            viewModel.db.value?.referencesDao()?.insertSection(viewModel.secList.value!!.last())
//            Log.e("!!!", viewModel.list.value.toString())

            navController.popBackStack()
//            activity?.onBackPressed()

        })
    }

}