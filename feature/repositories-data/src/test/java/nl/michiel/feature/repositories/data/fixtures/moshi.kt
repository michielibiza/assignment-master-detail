package nl.michiel.feature.repositories.data.fixtures

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import nl.michiel.feature.repositories.data.api.GithubEvent
import nl.michiel.feature.repositories.data.api.GithubRepo

private val moshi = Moshi.Builder().build()
val repoAdapter: JsonAdapter<List<GithubRepo>> = moshi.adapter(Types.newParameterizedType(List::class.java, GithubRepo::class.java))
val eventAdapter: JsonAdapter<List<GithubEvent>> = moshi.adapter(Types.newParameterizedType(List::class.java, GithubEvent::class.java))
