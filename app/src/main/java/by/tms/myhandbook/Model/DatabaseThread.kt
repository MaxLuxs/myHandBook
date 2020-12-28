package by.tms.myhandbook.Model

import by.tms.myhandbook.Section

class DatabaseThread(val database: HandbookDatabase) {

    inner class InsertSection(private var section: Section):Thread(){

        override fun run() {
            super.run()
            database.referencesDao().insertSection(section)
        }
    }

    inner class InsertAllSections(private val sections:List<Section>, private val database:HandbookDatabase):Thread(){
        override fun run() {
            super.run()
            for (element in sections){
                val insertThrow = InsertSection(element)
                insertThrow.start()
            }
        }
    }

    inner class DeleteAllSections():Thread(){
        override fun run() {
            super.run()
            database.referencesDao().deleteAllSections()
        }
    }

    /**Sections database command:*/
}
