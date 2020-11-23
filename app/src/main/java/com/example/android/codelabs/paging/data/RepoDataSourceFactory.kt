package com.example.android.codelabs.paging.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.android.codelabs.paging.db.GithubLocalCache
import com.example.android.codelabs.paging.model.Repo


class RepoDataSourceFactory(
        private val cache: GithubLocalCache,
        private val name: String
): DataSource.Factory<Int, Repo>() {

    private val _dataSourceLiveData = MutableLiveData<DataSource<Int, Repo>>()
    val dataSourceLiveData: LiveData<DataSource<Int, Repo>> = _dataSourceLiveData

    override fun create(): DataSource<Int, Repo> {
        val dataSource = RepoKeyedDataSource(cache, name)
        _dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}