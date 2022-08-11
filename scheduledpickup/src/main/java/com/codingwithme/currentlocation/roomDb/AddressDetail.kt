package com.codingwithme.currentlocation.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class AddressDetail(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "house_no") val houseno: String?,
    @ColumnInfo(name = "contact_no")val contactno:Int?,
    @ColumnInfo(name = "street")val street :String,
    @ColumnInfo(name="city")val city:String,
    @ColumnInfo(name="state")val state:String,
    @ColumnInfo(name="pin_code")val pincode:Int,



)