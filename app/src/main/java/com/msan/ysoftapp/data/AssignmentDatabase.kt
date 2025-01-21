package com.msan.ysoftapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.msan.ysoftapp.data.entity.AssignmentEntity

@Database(
    entities = [AssignmentEntity::class],
    version = 1
)
abstract class AssignmentDatabase: RoomDatabase() {

    abstract val dao: AssignmentDao
}