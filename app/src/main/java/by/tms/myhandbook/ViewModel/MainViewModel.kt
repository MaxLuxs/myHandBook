package by.tms.myhandbook.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import by.tms.myhandbook.Model.References
import by.tms.myhandbook.Section

class MainViewModel() : ViewModel() {

    var itemid = 0

//    val list:MutableLiveData<MutableList<Section>> by lazy {
//        MutableLiveData<MutableList<Section>>()
//    }
    val refList:MutableLiveData<MutableList<References>> by lazy {
        MutableLiveData<MutableList<References>>()
    }


}