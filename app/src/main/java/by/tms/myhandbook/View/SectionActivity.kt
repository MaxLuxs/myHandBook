package by.tms.myhandbook.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.tms.myhandbook.R
import by.tms.myhandbook.Section
import kotlinx.android.synthetic.main.header_main_menu.*
import kotlinx.android.synthetic.main.reference_layout.*

class SectionActivity(private val mainActivity: MainActivity) : AppCompatActivity() {



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reference_layout)

        val name =  intent.getStringExtra("name")
        if (name != null) {
            Log.e("!!!", name)
        }
//        textView.text = intent.getStringExtra("chapter")
        textView1.text = "Chapter " + intent.getIntExtra("id", 1).toString()
        textEd1.setText(intent.getStringExtra("name"))
        textEd2.setText(intent.getStringExtra("theme"))
        textEd3.setText(intent.getStringExtra("content"))




    }

    override fun onPause() {
        super.onPause()
        mainActivity.database.sectionDao().delete(intent.getIntExtra("id", 1))
        mainActivity.database.sectionDao().insertSection(Section(intent.getIntExtra("id", 1),textEd1.text.toString(), textEd2.text.toString(), textEd3.text.toString()))

        intent.putExtra("name", textEd1.text.toString())
        intent.putExtra("theme", textEd2.text.toString())
        intent.putExtra("content", textEd3.text.toString())
    }

}