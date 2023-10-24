package com.devapps.germanwitharthur.Data.Repository

import com.devapps.germanwitharthur.Data.Dao.RemainderDao
import com.devapps.germanwitharthur.Data.Model.Remainder
import kotlinx.coroutines.flow.Flow

class RemainderRepository(private val remainderDao: RemainderDao)  {

    suspend fun upsertRemainder(remainder: Remainder) {
        remainderDao.upsertRemainder(remainder)
    }

    suspend fun deleteRemainder(remainder: Remainder) {
        remainderDao.deleteRemainder(remainder)
    }

    suspend fun getAllRemainders() : Flow<List<Remainder>> = remainderDao.getAllRemainders()

    suspend fun getRemainder(remId: Int) : Flow<Remainder> = remainderDao.getRemainder(remId)




}