package com.devapps.germanwitharthur.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.devapps.germanwitharthur.Data.Model.Remainder
import kotlinx.coroutines.flow.Flow

@Dao
interface RemainderDao {

    @Upsert
    suspend fun upsertRemainder(remainder: Remainder)

    @Delete
    suspend fun deleteRemainder(remainder: Remainder)

    @Query("SELECT * FROM remainders ORDER BY remId DESC")
    suspend fun getAllRemainders() : Flow<List<Remainder>>

    @Query("SELECT * FROM remainders WHERE remId = remId")
    suspend fun getRemainder(remId: Int) : Flow<Remainder>
}