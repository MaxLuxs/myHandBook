package by.tms.myhandbook

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import by.tms.myhandbook.View.MainActivity
import by.tms.myhandbook.ViewModel.MainViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_section.*


/**
 */
class SectionFragment() : Fragment() {

    class MyDialogFragment(val name:String, val theme:String, val text:String) : DialogFragment() {

        lateinit var sectionViewModel : MainViewModel

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            sectionViewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("You are closing the section.")
                        .setMessage("Want to save your changes")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("Yes") {
                            dialog, id -> sectionViewModel.section.value?.let { it1 -> sectionViewModel.db.value?.referencesDao()?.deleteSectionById(it1.id) }
                            sectionViewModel.section.value = Section(sectionViewModel.section.value!!.id,
                                    name,
                                    theme,
                                    text,
                                    sectionViewModel.section.value!!.refId)
                            sectionViewModel.section.value?.let { it1 -> sectionViewModel.db.value?.referencesDao()?.insertSection(it1) }
                            Toast.makeText(context, sectionViewModel.section.value.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("!!!", sectionViewModel.section.value.toString())
                            dialog.cancel()
                        }
                        .setNegativeButton("No"){
                            dialog, id ->  dialog.cancel()
                        }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    lateinit var sectionViewModel : MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionViewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        name.setText(sectionViewModel.section.value?.name)
        theme1.setText(sectionViewModel.section.value?.theme)
        text.setText(sectionViewModel.section.value?.text)

        buttonSave.setOnClickListener(View.OnClickListener {
            dialog(name.text.toString(), theme1.text.toString(), text.text.toString())
        })
    }

    fun dialog(name: String, theme: String, text: String):Boolean {
        val myDialogFragment = MyDialogFragment(name, theme, text)
        val manager = activity?.supportFragmentManager
//        if (manager != null) {
//            myDialogFragment.show(manager, "myDialog")
//        }
        val transaction: FragmentTransaction = manager!!.beginTransaction()
        val show = myDialogFragment.show(transaction, "dialog")
        return true
    }
    
}