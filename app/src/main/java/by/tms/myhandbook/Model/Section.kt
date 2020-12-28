package by.tms.myhandbook


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import by.tms.myhandbook.View.MainActivity
import by.tms.myhandbook.View.UI.ReferencesViewModel

//Class:-----------------------------------------------------------------------
@Entity(tableName = "section")
data class Section(
    @PrimaryKey
    val id: Int,
    var name: String,
    var theme: String,
    var text: String,
    val refId:Int
)

//Database:-----------------------------------------------------------------------
//@Database(entities = [Section::class], version = 1)
//abstract class SectionsDatabase : RoomDatabase() {
//    abstract fun sectionDao(): SectionDao?
//}

//DAO:-----------------------------------------------------------------------
//@Dao
//interface SectionDao {
//    @Query("INSERT INTO section (name) VALUES ('New section')")
//    fun create()
//    @get:Query("SELECT * FROM section")
//    val getAll: MutableList<Section>?
//    @Query("UPDATE section SET name = :content WHERE id = :id")
//    fun save(content: String?, id: Int)
//    @Query("DELETE FROM section WHERE id LIKE :id")
//    fun delete(id: Int)
//    @Query("DELETE from section")
//    fun deleteAll()
//    @Insert
//    fun insertSection(newSection: Section)
//    @Delete
//    fun deleteSection(section: Section)
//}
//Adapter:------------------------------------------------------------------------------------------------------------------
class SectionAdapter(private var sectionList: MutableList<Section>, val navController: NavController, val mainActivity: MainActivity) : RecyclerView.Adapter<SectionAdapter.ViewHolder>() {

    lateinit var viewModel:ReferencesViewModel
    lateinit var sectionViewModel: SectionViewModel

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTW: TextView = itemView.findViewById(R.id.name)
        val descrTW: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout,
            parent,
            false
        )
        val holder = ViewHolder(itemView)

        itemView.setOnClickListener(View.OnClickListener {
            Log.e("!!!", "1")
            viewModel = ViewModelProvider(mainActivity).get(ReferencesViewModel::class.java)
            sectionViewModel = ViewModelProvider(mainActivity).get(SectionViewModel::class.java)

            val position = holder.adapterPosition
//            Log.e("!!!", "1")
//            val fragmentManager :FragmentManager = mainActivity.supportFragmentManager
//            Log.e("!!!", "2")
//            val ft : FragmentTransaction = fragmentManager.beginTransaction()
//            Log.e("!!!", "3")
//            val sectionFragment = viewModel.list.value?.get(position)?.name?.let { it1 ->
//                viewModel.list.value?.get(position)?.theme?.let { it2 ->
//                    viewModel.list.value?.get(position)?.text?.let { it3 ->
//                        SectionFragment.newInstance(
//                            it1,
//                            it2,
//                            it3
//                        )
//                    }
//                }
//            }
//            Log.e("!!!", "4")
////            if (sectionFragment != null) {
////                ft.replace(R.id.nav_host, sectionFragment)
////            }
//            Log.e("!!!", "5")
//            fragmentManager.let {
//                if (sectionFragment != null) {
//                    addFragment(
//                        it, sectionFragment, R.id.nav_host, false,  true
//                    )
//                }
//            }
//            ft.commit()
            sectionViewModel.section.value = viewModel.list.value?.get(position)
            navController.navigate(R.id.sectionFragment)

//            notifyDataSetChanged()
        })

        return holder
    }

    //
    fun addFragment(
        fm: FragmentManager,
        fragment: SectionFragment,
        container: Int,
        replace: Boolean,
        addToBackStack: Boolean,
    ) {
        val fragmentTransaction = fm.beginTransaction()
        if (replace)
            fragmentTransaction.replace(container, fragment, fragment.javaClass.name)
        else
            fragmentTransaction.add(container, fragment, fragment.javaClass.name)
        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.name)
        fragmentTransaction.commit()
    }
    //

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTW.text = sectionList[position].name
        holder.descrTW.text = sectionList[position].theme
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    fun reload() {
        notifyDataSetChanged()
    }

}


