package com.msan.ysoftapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.msan.ysoftapp.data.entity.AssignmentEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignmentEntity: AssignmentEntity) : Long

    @Query("DELETE FROM assignmentEntity WHERE id = :id")
    suspend fun deleteAssignmentById(id: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAssignment(assignmentEntity: AssignmentEntity)

    @Query(
        """
            SELECT *
            FROM assignmentEntity
        """
    )
    fun getAllAssignments(): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignmentEntity WHERE startDate = :date ORDER BY startDate ASC")
    fun getAssignmentsForDate(date: Date): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignmentEntity WHERE id = :id")
    suspend fun getAssignmentById(id: Long): AssignmentEntity?

    @Query("SELECT startDate FROM assignmentEntity WHERE name = :name")
    fun getStartDateForAssignment(name: String): Flow<Long>

}

