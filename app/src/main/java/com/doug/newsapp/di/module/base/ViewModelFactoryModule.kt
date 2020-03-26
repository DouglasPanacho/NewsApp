package com.doug.newsapp.di.module.base


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doug.newsapp.ui.base.BaseViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

/**
 * Factory used to provide injection of different ViewModels from the default one(no parameters)
 */
@Module
interface  ViewModelFactoryModule {

    @Binds
     fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

}
