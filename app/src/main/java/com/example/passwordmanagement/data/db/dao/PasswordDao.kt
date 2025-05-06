package com.example.passwordmanagement.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanagement.data.db.entity.PasswordEntry

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): LiveData<List<PasswordEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(entry: PasswordEntry)

    @Update
    suspend fun updatePassword(entry: PasswordEntry)

    @Delete
    suspend fun deletePassword(entry: PasswordEntry)
}