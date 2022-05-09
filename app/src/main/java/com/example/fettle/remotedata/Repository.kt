package com.example.fettle.remotedata

import com.example.fettle.roomdatabase.LocalData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Scope

@ViewModelScoped
//Class that stores all data.
class Repository @Inject constructor(remoteData: RemoteData, localData: LocalData) {
    val remote = remoteData
    val local = localData
}