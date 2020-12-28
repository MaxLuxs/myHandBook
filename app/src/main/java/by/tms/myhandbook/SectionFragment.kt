package by.tms.myhandbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.tms.myhandbook.View.MainActivity
import by.tms.myhandbook.View.UI.ReferencesViewModel
import kotlinx.android.synthetic.main.fragment_section.*

/**
 * A simple [Fragment] subclass.
 */
class SectionFragment : Fragment() {
    lateinit var sectionViewModel: SectionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionViewModel = ViewModelProvider(activity as MainActivity).get(SectionViewModel::class.java)
        name.setText(sectionViewModel.section.value?.name)
        theme.setText(sectionViewModel.section.value?.theme)
        text.setText(sectionViewModel.section.value?.text)

    }


}