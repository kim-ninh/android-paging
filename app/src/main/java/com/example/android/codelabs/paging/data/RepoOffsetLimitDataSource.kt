package com.example.android.codelabs.paging.data

import androidx.paging.PositionalDataSource
import com.example.android.codelabs.paging.db.GithubLocalCache
import com.example.android.codelabs.paging.model.Repo

class RepoOffsetLimitDataSource(
        private val cache: GithubLocalCache,
        private val name: String
): PositionalDataSource<Repo>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Repo>) {
        val totalCount = cache.countRepos(name)
        val initialPosition = computeInitialLoadPosition(params, totalCount)
        val initialSize = computeInitialLoadSize(params, initialPosition, totalCount)

        val repos = cache.reposByName_(name, initialSize, initialPosition)
        callback.onResult(repos, initialPosition, totalCount)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Repo>) {
        val repos = cache.reposByName_(name, params.loadSize, params.startPosition)
        callback.onResult(repos)
    }
}