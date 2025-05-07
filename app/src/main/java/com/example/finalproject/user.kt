package com.example.finalproject

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class User(
    @PrimaryKey val user_no: Int,
    val userName: String?,
    val ex_type_no: Int,
    val from_date: Date
)
@Entity
data class muscle_group(
    @PrimaryKey val muscle_group_no:Int,
    val muscle_group_name: String?,
    val upper:Boolean
)
@Entity
data class exercise(
    @PrimaryKey val ex_no: Int,
    val ex_title: String?,
    val ex_desc: String?,
    val ex_type:String?,
    val muscle_group_name: String?,
    val equipment:String?,
    val level:String?,
    val rating:String?,
    val rating_desc:String?
)

@Entity
data class ex_type(
    @PrimaryKey val ex_type_no:Int,
    val ex_type_name:String
)
//@Dao
//interface UserDao{
//    @Insert
//    fun insert
//
// }