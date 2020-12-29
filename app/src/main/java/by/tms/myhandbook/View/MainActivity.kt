package by.tms.myhandbook.View

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import by.tms.myhandbook.Model.HandbookDatabase
import by.tms.myhandbook.Model.RefAndSec
import by.tms.myhandbook.Model.References
import by.tms.myhandbook.R
import by.tms.myhandbook.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MyHandbook v 0.01
 * by Maksim Lukashevich
 * It`s dirty but it can live
 * */

class MainActivity : AppCompatActivity(){

    private lateinit var viewModel: MainViewModel
//    lateinit var refViewModel: ReferencesViewModel
    lateinit var navController: NavController

    private var itemid = 0

    private lateinit var database: HandbookDatabase

//    val database by lazy { Room.databaseBuilder(
//        applicationContext,
//        HandbookDatabase::class.java, "handb_db"
//    ).allowMainThreadQueries().build() }

//    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    //database init:
//    database = Room.databaseBuilder(
//        applicationContext,
//        HandbookDatabase::class.java,
//        "handb_db",
//    ).allowMainThreadQueries().build()
    database = Room.databaseBuilder(
        applicationContext,
        HandbookDatabase::class.java, "handook_db"
    ).allowMainThreadQueries()
        .build()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        refViewModel = ViewModelProvider(this).get(ReferencesViewModel::class.java)

        //db load:
        viewModel.refAndSecList.value = database.referencesDao().getAllRefs()
    Log.e("!!!", viewModel.refAndSecList.value.toString())
    viewModel.refList.value = mutableListOf()
    viewModel.secList.value = mutableListOf()

        viewModel.refAndSecList.observe(this, Observer {
            viewModel.itemid = viewModel.refAndSecList.value!!.size
            itemid = viewModel.itemid
            Log.e("!!!", itemid.toString())
            var i = 0
            for (ras in it){
                Log.e("!!!", "1")

                viewModel.secList.value = ras.sections
                viewModel.refList.value?.add(ras.references)
                Log.e("!!!", ras.references.toString() + viewModel.refList.value.toString())
                i++
            }

            viewModel.refList.observe(this, Observer {
                nav_view.menu.add(R.id.group123, i, Menu.NONE, "Ref $i")
                nav_view.menu.setGroupEnabled(R.id.group123, true)
                nav_view.menu.setQwertyMode(true)
                nav_view.menu.setGroupCheckable(R.id.group123, true, true)
                nav_view.setNavigationItemSelectedListener {
                    viewModel.secList.value = viewModel.refAndSecList.value!![it.itemId-1].sections
//                refViewModel.referencesId = 0
                    navController.navigate(R.id.referencesFragment)
                    it.isChecked = true;
                    drawer.closeDrawers();
                    true
                }
            })

        })
//        viewModel.refAndSecList.value = mutableListOf()

        //Navigation controller:
        navController = findNavController(R.id.nav_host)
//        supportActionBar?.title = "12345"
        setupActionBarWithNavController(navController, drawer)
        toolbar.setupWithNavController(navController, drawer)
        nav_view.setupWithNavController(navController)

        /**Обработка нажатий кнопок в меню дравера :
         * add -> добавить item в menu
         * delete -> удалить item из меню
         * */
        add.setOnClickListener(View.OnClickListener {
//            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show()
//            nav_view.menu.add(R.id.group123, itemid, Menu.NONE, "Ref $itemid")
//            nav_view.menu.setGroupEnabled(R.id.group123, true)
//            nav_view.menu.setQwertyMode(true)
//            nav_view.menu.setGroupCheckable(R.id.group123, true, true)
//            nav_view.menu.setGroupDividerEnabled(true)
            //AddDB:
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show()
            database.referencesDao().insertReferences(References(id = itemid, "Ref $itemid"))
//            viewModel.refAndSecList.value!!.add(RefAndSec(References(itemid, "Ref $itemid"), mutableListOf()))
            //
            viewModel.refAndSecList.value = database.referencesDao().getAllRefs()
            //
            itemid = viewModel.refAndSecList.value!!.size
        })
        delete.setOnClickListener(View.OnClickListener {
            if (itemid >= 0){
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                //
                database.referencesDao().deleteReferencesById(itemid-1)
                nav_view.menu.removeItem(itemid-1)
                viewModel.refAndSecList.value!!.removeAt(itemid-1)
                itemid = viewModel.refAndSecList.value!!.size
            }else{
                Toast.makeText(this, "no item", Toast.LENGTH_SHORT).show()
            }
            Log.e("!!!", viewModel.refAndSecList.value.toString())
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
    }

}