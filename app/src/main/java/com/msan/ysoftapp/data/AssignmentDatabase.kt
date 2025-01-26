package com.msan.ysoftapp.data


import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.msan.ysoftapp.data.entity.AssignmentEntity

@Database(
    entities = [AssignmentEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = AssignmentDatabase.AutoMigration::class)
    ]
)
@TypeConverters(Converters::class)
abstract class AssignmentDatabase : RoomDatabase() {

    abstract val dao: AssignmentDao

    class AutoMigration : AutoMigrationSpec
}