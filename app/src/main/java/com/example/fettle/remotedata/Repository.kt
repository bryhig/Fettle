package com.example.fettle.remotedata

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Scope

@ActivityRetainedScoped
class Repository @Inject constructor(remoteData: RemoteData) {
    val remote = remoteData
}