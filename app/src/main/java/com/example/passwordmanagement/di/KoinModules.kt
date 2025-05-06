package com.example.passwordmanagement.di

import androidx.room.Room
import com.example.passwordmanagement.data.db.database.PasswordDatabase
import com.example.passwordmanagement.data.repository.PasswordRepository
import com.example.passwordmanagement.viewModel.PasswordViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PasswordDatabase::class.java,
            "password_db"
        ).build()
    }
    single { get<PasswordDatabase>().passwordDao() }
    single { PasswordRepository(get()) }
    viewModel { PasswordViewModel(get()) }
} 