package by.tms.myhandbook.Model

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import by.tms.myhandbook.Section

interface ReferencesDao {
    @Transaction
    @Query("SELECT * FROM refs")
    fun getAllRefs():List<RefAndSec>

    @Transaction
    @Query("SELECT * FROM refs WHERE id LIKE :id")
    fun getById(id:Int):RefAndSec

    @Insert
    fun insertReferences(newReferences: References)

    @Insert
    fun insertSection(newSection: Section)

    @Query("DELETE FROM refs")
    fun deleteAllReferences()

    @Query("DELETE FROM section")
    fun deleteAllSections()


}