package by.tms.myhandbook.View.UI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.tms.myhandbook.Section

class ReferencesViewModel() : ViewModel() {

    var referencesId = 0

    val list:MutableLiveData<MutableList<Section>> by lazy {
        MutableLiveData<MutableList<Section>>()
    }

}