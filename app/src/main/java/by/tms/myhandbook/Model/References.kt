package by.tms.myhandbook.Model

import androidx.room.*
import by.tms.myhandbook.Section
//
@Entity(tableName = "refs")
data class References(
        val sections: MutableList<Section>,
        @PrimaryKey
        val id:Int,
        val ReferencesName:String)

//@Dao
//interface ReferencesDao{
//    @Query("SELECT * FROM refs")
//    fun getAllRef():List<References>
//    @Insert
//    fun insertRef(ref:References)
//    @Delete
//    fun deleteRef(ref:References)
//    @Query("DELETE FROM refs")
//    fun deleteAll()
//}