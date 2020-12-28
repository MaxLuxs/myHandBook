package by.tms.myhandbook.View.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.tms.myhandbook.Model.References
import by.tms.myhandbook.R
import by.tms.myhandbook.Section
import by.tms.myhandbook.ViewModel.MainViewModel

import kotlinx.android.synthetic.main.fragment_add_ref.*

class AddRefFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ref, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        buttonAdd.setOnClickListener(View.OnClickListener {
            viewModel.refList.value!!.add(
                References(
                mutableListOf<Section>(),
                viewModel.itemid,
                    nameRefEd.text.toString())
            )
        })

        viewModel.refList.observe(viewLifecycleOwner, Observer {
            viewModel.itemid++
        })


    }
}