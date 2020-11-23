package com.example.android.codelabs.paging.data

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.example.android.codelabs.paging.db.GithubLocalCache
import com.example.android.codelabs.paging.model.Repo

class RepoKeyedDataSource(
        private val cache: GithubLocalCache,
        private val name: String
) : ItemKeyedDataSource<Int, Repo>() {

    override fun getKey(item: Repo): Int = item.stars

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Repo>) {
        Log.d("RepoKeyedDataSource", "loadInitial: ${params.requestedInitialKey}")
        val items = cache.reposByName(name, params.requestedLoadSize)
        callback.onResult(items)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Repo>) {
        Log.d("RepoKeyedDataSource", "loadAfter: ${params.key}")
        val items = cache.reposByName(name, params.requestedLoadSize, params.key)
        callback.onResult(items)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Repo>) {}
}