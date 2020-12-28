package by.tms.myhandbook.View

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import by.tms.myhandbook.Model.References
import by.tms.myhandbook.R
import by.tms.myhandbook.Section
import by.tms.myhandbook.View.UI.ReferencesViewModel
import by.tms.myhandbook.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private lateinit var viewModel: MainViewModel
    lateinit var refViewModel: ReferencesViewModel
    lateinit var navController: NavController

    var itemid = 0

    val database by lazy { Room.databaseBuilder(
        applicationContext,
        HandbookDatabase::class.java, "handb_db"
    ).allowMainThreadQueries().build() }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        //
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        refViewModel = ViewModelProvider(this).get(ReferencesViewModel::class.java)

        viewModel.refList.observe(this, Observer {
            Log.e("!!!", "new data")
            for (references in viewModel.refList.value!!){

            }
        })
        viewModel.refList.value = mutableListOf()

        //Navigation controller:
        navController = findNavController(R.id.nav_host)
//        supportActionBar?.title = "12345"
        setupActionBarWithNavController(navController, drawer)
        toolbar.setupWithNavController(navController, drawer)
        nav_view.setupWithNavController(navController)

        /**ОЛбработка нажатий кнопок в меню дравера :
         * add -> добавить item в menu
         * delte -> удалить item из меню
         * */
        add.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show()
            nav_view.menu.add(R.id.group123, itemid, Menu.NONE, "Ref $itemid")
            nav_view.menu.setGroupEnabled(R.id.group123, true)
            nav_view.menu.setQwertyMode(true)
            nav_view.menu.setGroupCheckable(R.id.group123, true, true)
//            nav_view.menu.setGroupDividerEnabled(true)
            viewModel.refList.value!!.add(References(mutableListOf<Section>(),itemid, "Ref $itemid"))
            Log.e("!!!", viewModel.refList.value!![itemid].sections.toString())
            itemid = viewModel.refList.value!!.size
            nav_view.setNavigationItemSelectedListener {
                refViewModel.list.value = viewModel.refList.value!![it.itemId].sections
                refViewModel.referencesId=0
                navController.navigate(R.id.referencesFragment)
                it.isChecked = true;
                drawer.closeDrawers();
                true
            }

        })
        delete.setOnClickListener(View.OnClickListener {
            if (itemid >= 0){
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                nav_view.menu.removeItem(itemid-1)
                viewModel.refList.value!!.removeAt(itemid-1)
                itemid = viewModel.refList.value!!.size
            }else{
                Toast.makeText(this, "no item", Toast.LENGTH_SHORT).show()
            }
            Log.e("!!!", viewModel.refList.value.toString())
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