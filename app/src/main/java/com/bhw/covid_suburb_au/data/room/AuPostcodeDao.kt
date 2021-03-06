package com.bhw.covid_suburb_au.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AuPostcodeDao {
    @Query("SELECT * FROM au_postcode WHERE postcode=:postcode")
    suspend fun findPostcode(postcode: Int): AuPostcodeEntity?

    @Query("SELECT * FROM au_postcode WHERE postcode>=:low AND postcode<=:high")
    suspend fun findPostcodesInRange(low: Int, high: Int): List<AuPostcodeEntity>

    @Query("SELECT * FROM au_postcode WHERE indexingString LIKE '%' || :search || '%'")
    fun findPostcodeByName(search: String?): List<AuPostcodeEntity>

    @Query("DELETE FROM au_postcode")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(list: List<AuPostcodeEntity>)

    @Query("SELECT * FROM au_postcode")
    suspend fun getAllPostcodes(): List<AuPostcodeEntity>

    @Query("SELECT * FROM au_postcode")
    fun findAllPostcodesFlow(): Flow<List<AuPostcodeEntity>>
}