package com.example.fettle.remotedata

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Scope

@ViewModelScoped
class Repository @Inject constructor(remoteData: RemoteData) {
    val remote = remoteData
}