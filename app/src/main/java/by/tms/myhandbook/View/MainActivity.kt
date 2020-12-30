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
 *
 * Надо:
 * Настроить все id (section and references), сделать одни экземпляры во view model.
 * Настроить liveData во viewModel.
 * Удалить все лишнее.
 *
 * Добавить настройки в appbarmenu
 * Настроить удаление
 *
 * [AppCompatActivity]
 * */

class MainActivity : AppCompatActivity(){

    private lateinit var viewModel: MainViewModel

    lateinit var navController: NavController

    private var itemid = 0

    private lateinit var database: HandbookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    //database init:
    database = Room.databaseBuilder(
        applicationContext,
        HandbookDatabase::class.java, "handook_db"
    ).allowMainThreadQueries()
        .build()

        /**View model init : MainViewModel
         * 1.Load all references
         * 2.3.Create lists for ref and sec
         * 4.Load db link in VM
         * 5.Get last section ID
         * 6.Set Observe -> Update drawer.menu and set navItemListener for menu item.
         * */
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.refAndSecList.value = database.referencesDao().getAllRefs()
        viewModel.refList.value = mutableListOf()
        viewModel.secList.value = mutableListOf()
        viewModel.db.value = database
        viewModel.secId = database.referencesDao().getAllSections().last().id + 1
        viewModel.refAndSecList.observe(this, Observer { it ->
            nav_view.menu.clear()
            viewModel.itemid = viewModel.refAndSecList.value!!.size
            itemid = viewModel.itemid
            Log.e("!!!", viewModel.refAndSecList.value.toString())
            for ((i, ras) in it.withIndex()){
                viewModel.refList.value?.add(ras.references)
                nav_view.menu.add(R.id.group123, i, Menu.NONE, ras.references.ReferencesName)
                nav_view.menu.setGroupEnabled(R.id.group123, true)
                nav_view.menu.setQwertyMode(true)
                nav_view.menu.setGroupCheckable(R.id.group123, true, true)
            }
            nav_view.setNavigationItemSelectedListener {
                Log.e("!!!", viewModel.refAndSecList.value.toString())
                viewModel.secList.value = viewModel.refAndSecList.value!![it.itemId].sections
                viewModel.referencesId = 0
                viewModel.selectRef = it.itemId
                navController.navigate(R.id.referencesFragment)
                it.isChecked = true;
                drawer.closeDrawers();
                true
            }
        })

        /**Navigation controller:
         * nav_host -> graph
         * */
        navController = findNavController(R.id.nav_host)
        setupActionBarWithNavController(navController, drawer)
        toolbar.setupWithNavController(navController, drawer)
        nav_view.setupWithNavController(navController)

        /**Listners for buttons in drawer menu :
         * add -> add item to menu
         * delete -> delete item
         * */
        add.setOnClickListener(View.OnClickListener {
            //AddDB:
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show()
            database.referencesDao().insertReferences(References(id = itemid, "Ref $itemid"))
            viewModel.refAndSecList.value = database.referencesDao().getAllRefs()
            itemid = viewModel.refAndSecList.value!!.size
        })
        delete.setOnClickListener(View.OnClickListener {
            Log.e("!!!", itemid.toString())
            if (itemid >= 0){
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                database.referencesDao().deleteReferencesById(itemid-1)
                nav_view.menu.removeItem(itemid-1)
                viewModel.refAndSecList.value!!.removeAt(itemid-1)
                itemid = viewModel.refAndSecList.value!!.size
            }else{
                Toast.makeText(this, "no item", Toast.LENGTH_SHORT).show()
            }
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