package com.msan.ysoftapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.msan.ysoftapp.data.entity.AssignmentEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignmentEntity: AssignmentEntity)

    @Delete
    suspend fun deleteAssignment(assignmentEntity: AssignmentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAssignment(medicationEntity: AssignmentEntity)

    @Query(
        """
            SELECT *
            FROM assignmentEntity
        """
    )
    fun getAllAssignments(): Flow<List<AssignmentEntity>>

    @Query(
        """
            SELECT *
            FROM assignmentEntity
            WHERE strftime('%Y-%m-%d', startDate / 1000, 'unixepoch', 'localtime') = :date
            ORDER BY startDate ASC
        """
    )
    fun getAssignmentsForDate(date: String): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignmentEntity WHERE id = :id")
    suspend fun getAssignmentById(id: Long): AssignmentEntity?
}

