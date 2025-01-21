package com.msan.ysoftapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msan.ysoftapp.data.entity.AssignmentEntity

@Database(
    entities = [AssignmentEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AssignmentDatabase: RoomDatabase() {

    abstract val dao: AssignmentDao
}