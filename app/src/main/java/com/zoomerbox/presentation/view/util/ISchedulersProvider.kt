package com.zoomerbox.presentation.view.util

import io.reactivex.Scheduler

/**
 * Интерфейс, позволяющий работать с потоками
 */
interface ISchedulersProvider {


    /**
     * Предоставляет доступ к бэкграунд потоку
     */
    fun io(): Scheduler

    /**
     * Предоставляет доступ к главному потоку
     */
    fun ui(): Scheduler
}
