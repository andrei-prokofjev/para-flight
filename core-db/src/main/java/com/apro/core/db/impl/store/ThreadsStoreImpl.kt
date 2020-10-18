package com.apro.core.db.impl.store

import com.apro.core.db.api.data.DatabaseClientApi
import com.apro.core.db.api.data.store.FlightsStore
import com.apro.core.db.entity.FlightPointEntity
import com.apro.core.db.entity.model
import com.apro.core.model.FlightPointModel
import javax.inject.Inject

class ThreadsStoreImpl @Inject constructor(
  private val db: DatabaseClientApi
) : FlightsStore {

  override fun insertFlightPoints(points: List<FlightPointModel>) {
    db.inTransaction {
      val entities = points.map { FlightPointEntity.from(it) }
      db.flightDao().insertAll(entities)
    }

  }

  override fun getFlightPoints(): List<FlightPointModel> = db.flightDao().getAll().map { it.model() }

//  override fun insertThread(thread: ThreadModel) { // Common threads api: Don't use for now
//    insertThreads(listOf(thread))
//  }
//
//  override fun insertThreads(threads: List<ThreadModel>) { // Common threads api: Don't use for now
//    db.inTransaction {
//      val existentThreads = db.threadDao().threads(threads.map { it.id }).associate { it.id to it.position }
//
//      val creators = threads.map { it.creator }.map { UserEntity.from(it) }.distinctBy { it.id }
//      db.userDao().insertUsers(creators)
//
//      val entities = threads.map { model -> ThreadEntity.from(model, existentThreads[model.id] ?: 0) }
//      db.threadDao().insertThreads(entities)
//
//      threads.forEach { threadModel ->
//        val users = threadModel.members.map { UserEntity.from(it) }
//        db.userDao().insertUsers(users)
//
//        val threadMembers = users.map { ThreadMemberEntity(threadId = threadModel.id, userId = it.id) }
//        db.threadDao().insertThreadMembers(threadMembers)
//      }
//    }
//  }
//
//  override fun getThread(threadId: Long): ThreadModel? {
//    return db.threadDao().threadWithMembers(threadId)?.model()
//  }
//
//  override fun threadData(threadId: Long): Flow<ThreadModel> {
//    return db.threadDao().threadWithMembersData(threadId).mapNotNull { it?.model() }
//  }
//
//  override fun threadsData(threadIds: List<Long>): Flow<List<ThreadModel>> {
//    return db.threadDao().threadsWithMembersData(threadIds).map { it.map { entity -> entity.model() } }
//  }
//
//  override fun threadMembersCountData(threadId: Long): Flow<Long> {
//    return db.threadDao().threadMembersCountData(threadId)
//  }
//
//  override fun updateThreadTitle(threadId: Long, title: String) {
//    db.threadDao().updateThreadTitle(threadId, title)
//  }
//
//  override fun lockThread(threadId: Long) {
//    db.threadDao().lockThread(threadId)
//  }
//
//  // In case of updating pinned threads we need to keep position
//  override fun updatePinThread(thread: ThreadModel) {
//    db.inTransaction {
//      val existentThreadPosition = db.threadDao().thread(thread.id)?.position
//
//      existentThreadPosition?.let { oldPosition ->
//        db.threadDao().deleteThread(thread.id)
//
//        val creator = UserEntity.from(thread.creator)
//        db.userDao().insertUser(creator)
//
//        db.threadDao().insertThread(ThreadEntity.from(thread, oldPosition))
//
//        val users = thread.members.map { UserEntity.from(it) }
//        db.userDao().insertUsers(users)
//
//        val threadMembers = users.map { ThreadMemberEntity(threadId = thread.id, userId = it.id) }
//        db.threadDao().insertThreadMembers(threadMembers)
//      }
//    }
//  }
//
//  // Invalidate pinned threads
//  override fun updatePinnedThreads(threads: List<ThreadModel>) {
//    db.inTransaction {
//      db.threadDao().deletePinThreads()
//
//      val creators = threads.map { it.creator }.map { UserEntity.from(it) }.distinctBy { it.id }
//      db.userDao().insertUsers(creators)
//
//      val entities = threads.mapIndexed { i, model -> ThreadEntity.from(model, i + 1) }
//      db.threadDao().insertThreads(entities)
//
//      threads.forEach { threadModel ->
//        val users = threadModel.members.map { UserEntity.from(it) }
//        db.userDao().insertUsers(users)
//
//        val threadMembers = users.map { ThreadMemberEntity(threadId = threadModel.id, userId = it.id) }
//        db.threadDao().insertThreadMembers(threadMembers)
//      }
//
//      if (threads.find { it.id == feedPreferences.lastSelectedThread } == null) {
//        threads.firstOrNull()?.let { feedPreferences.setSelectedBlocking(it.id) }
//      }
//    }
//  }
//
//  override fun allThreads(): List<ThreadModel> {
//    return db.threadDao().allThreadsWithMembers().map { it.model() }
//  }
//
//  override fun allThreadsData(): Flow<List<ThreadModel>> {
//    return db.threadDao().allThreadsWithMembersData().map { it.map { entity -> entity.model() } }
//  }
//
//  override fun allThreadsCountData(): Flow<Long> {
//    return db.threadDao().allThreadsWithMembersCountData()
//  }
//
//  override fun sharedThreads(userId: Long): List<SharedThreadModel> {
//    val friendsThread = db.threadDao().threadWithMembers(userId)?.sharedModel()
//    val sharedThreads = db.threadDao().sharedThreadsWithMembers(userId).map { it.sharedModel() }
//    return sharedThreads.toMutableList().apply { friendsThread?.let { add(0, it) } }
//  }
//
//  override fun sharedThreadsData(userId: Long): Flow<List<SharedThreadModel>> {
//    return combine(
//        db.threadDao().threadWithMembersData(ThreadModel.FRIENDS).map { it?.sharedModel() },
//        db.threadDao().sharedThreadsWithMembersData(userId).map { it.map { entity -> entity.sharedModel() } }
//    ) { friendsThread, sharedThreads ->
//      sharedThreads.toMutableList().apply { friendsThread?.let { add(0, it) } }
//    }
//  }
//
//  override fun pinnedThreads(): List<ThreadModel> {
//    return db.threadDao().pinnedThreadsWithMembers().map { it.model() }
//  }
//
//  override fun pinnedThreadsData(): Flow<List<ThreadModel>> {
//    return db.threadDao().pinnedThreadsWithMembersData().map { it.map { entity -> entity.model() } }
//  }
//
//  override fun pinThread(threadId: Long) {
//    db.threadDao().pinThread(threadId)
//  }
//
//  override fun unpinThread(threadId: Long) {
//    db.inTransaction {
//      db.threadDao().unpinThread(threadId)
//      if (threadId == feedPreferences.lastSelectedThread) {
//        val pinnedThreads = db.threadDao().pinnedThreads()
//        if (pinnedThreads.isEmpty()) {
//          // Pin and select Friends thread if no other pin threads
//          db.threadDao().pinThread(ThreadModel.FRIENDS)
//          feedPreferences.setSelectedBlocking(ThreadModel.FRIENDS)
//        } else {
//          feedPreferences.setSelectedBlocking(pinnedThreads.first().id)
//        }
//      }
//    }
//  }
//
//  override fun deleteThread(threadId: Long) {
//    db.inTransaction {
//      db.threadDao().deleteThread(threadId)
//      if (threadId == feedPreferences.lastSelectedThread) {
//        val pinnedThreads = db.threadDao().pinnedThreads()
//        if (pinnedThreads.isEmpty()) {
//          // Pin and select Friends thread if no other pin threads
//          db.threadDao().pinThread(ThreadModel.FRIENDS)
//          feedPreferences.setSelectedBlocking(ThreadModel.FRIENDS)
//        } else {
//          feedPreferences.setSelectedBlocking(pinnedThreads.first().id)
//        }
//      }
//    }
//  }

}