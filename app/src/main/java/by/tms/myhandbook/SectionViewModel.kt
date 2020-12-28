package by.tms.myhandbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.tms.myhandbook.Model.References

class SectionViewModel() : ViewModel() {

    val section: MutableLiveData<Section> by lazy {
        MutableLiveData<Section>()
    }


}