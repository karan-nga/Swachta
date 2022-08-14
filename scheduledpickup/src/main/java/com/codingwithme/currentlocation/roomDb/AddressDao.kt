package com.codingwithme.currentlocation.roomDb

import androidx.room.*

@Dao
interface AddressDao {
    @Query("SELECT * FROM address_table")
    fun getAll(): List<AddressDetail>

    /* @Query("SELECT * FROM student_table WHERE uid IN (:userIds)")
     fun loadAllByIds(userIds: IntArray): List<Student>*/

//    @Query("SELECT * FROM address_table WHERE id =1")
//     fun findByRoll(isd: Int): AddressDetail

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(address: AddressDetail)

    @Delete
     fun delete(address: AddressDetail)

    @Query("DELETE FROM address_table")
     fun deleteAll()
}