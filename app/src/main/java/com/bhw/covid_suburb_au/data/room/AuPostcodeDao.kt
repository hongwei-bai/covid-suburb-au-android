package com.bhw.covid_suburb_au.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AuPostcodeDao {
    @Query("SELECT * FROM au_postcode WHERE postcode=:postcode")
    suspend fun findPostcode(postcode: Long): AuPostcodeEntity?

    @Query("SELECT * FROM au_postcode WHERE postcode>=:low AND postcode<=:high")
    suspend fun findPostcodesInRange(low: Long, high: Long): List<AuPostcodeEntity>

    @Query("DELETE FROM au_postcode")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(list: List<AuPostcodeEntity>)

    @Query("SELECT * FROM au_postcode")
    suspend fun getAllPostcodes(): List<AuPostcodeEntity>

    @Query("SELECT * FROM au_postcode")
    fun getPostcodesPagingSource(): PagingSource<Int, AuPostcodeEntity>

    @Query("SELECT * FROM au_postcode")
    fun findAllPostcodesFlow(): Flow<List<AuPostcodeEntity>>
}