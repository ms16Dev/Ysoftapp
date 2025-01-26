package com.msan.ysoftapp.di


import android.app.Application
import androidx.room.Room
import com.msan.ysoftapp.data.AssignmentDatabase
import com.msan.ysoftapp.data.repository.AssignmentRepositoryImpl
import com.msan.ysoftapp.domain.repository.AssignmentRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AssignmentDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideAssignmentDatabase(app: Application): AssignmentDatabase {
        return Room.databaseBuilder(
            app,
            AssignmentDatabase::class.java,
            "Assignment_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAssignmentRepository(
        db: AssignmentDatabase
    ): AssignmentRepository {
        return AssignmentRepositoryImpl(
            dao = db.dao
        )
    }
}