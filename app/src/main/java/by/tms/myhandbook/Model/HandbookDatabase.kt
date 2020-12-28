package by.tms.myhandbook.Model

import androidx.room.Database
import androidx.room.RoomDatabase
import by.tms.myhandbook.Section

@Database(entities = [ References::class, Section::class], version = 1)
abstract class HandbookDatabase() : RoomDatabase() {
//    abstract fun sectionDao() : SectionDao
//    abstract fun referencesDao() : ReferencesDao
}