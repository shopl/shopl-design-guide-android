package com.shopl.sdg_common.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf


fun <T> emptyPersistentList(): PersistentList<T> = persistentListOf()
