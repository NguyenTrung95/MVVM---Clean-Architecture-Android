package com.nencer.thesieure.ext

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmQuery

/**
 * Created by nguyentrung on 2019-07-24
 */

fun <E:RealmModel>Realm.nextID(clazz:Class<E>):Int {
    val max = where(clazz).max("id")
    return 1 + (max?.toInt() ?: 0)
}


fun <E:RealmObject> E.persist() {
    Realm.getDefaultInstance().executeTransaction {
        it.insertOrUpdate(this)
    }
}

fun <E:RealmObject> E.delete() {
    realm?.executeTransaction {
        this.deleteFromRealm()
    }
}

fun singleTransaction(block: (Realm) -> Unit) {
    Realm.getDefaultInstance().use {
        it.executeTransaction { realm ->
            block(realm)
        }
    }
}

fun singleTransactionAsync(block: (Realm) -> Unit) {
    Realm.getDefaultInstance().use {
        it.executeTransactionAsync { realm ->
            block(realm)
        }
    }
}

fun Realm.truncate(vararg clazz: Class<out RealmModel>) {
    clazz.forEach {
        delete(it)
    }
}

fun <T : RealmObject> findFirstCopied(block: (Realm) -> RealmQuery<T>): T? {
    return Realm.getDefaultInstance().use {
        val result = block(it).findFirst() ?: return@use null
        it.copyFromRealm(result)
    }
}

fun <T : RealmObject> findAllCopied(block: (Realm) -> RealmQuery<T>): List<T> {
    return Realm.getDefaultInstance().use {
        it.copyFromRealm(block(it).findAll())
    }
}

fun <E> RealmQuery<E>.notIn(fieldName: String, values: Collection<Long?>): RealmQuery<E> {
    if (values.size > 1) beginGroup()
    for (item in values) {
        notEqualTo(fieldName, item)
    }
    if (values.size > 1) endGroup()
    return this
}
