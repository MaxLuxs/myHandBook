package by.tms.myhandbook.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import by.tms.myhandbook.Model.HandbookDatabase
import by.tms.myhandbook.Model.RefAndSec
import by.tms.myhandbook.Model.References
import by.tms.myhandbook.Section

class MainViewModel() : ViewModel() {

    var itemid = 0

    //References list:
    val refList:MutableLiveData<MutableList<References>> by lazy {
        MutableLiveData<MutableList<References>>()
    }

    val refAndSecList:MutableLiveData<MutableList<RefAndSec>> by lazy {
        MutableLiveData<MutableList<RefAndSec>>()
    }

    var referencesId = 0

//    var selectRefAndListId = 0

    //SectionList
    val secList:MutableLiveData<MutableList<Section>> by lazy {
        MutableLiveData<MutableList<Section>>()
    }

    val section: MutableLiveData<Section> by lazy {
        MutableLiveData<Section>()
    }

    val db: MutableLiveData<HandbookDatabase> by lazy {
        MutableLiveData<HandbookDatabase>()
    }

    var selectRef = 0
    var secId = 0


}