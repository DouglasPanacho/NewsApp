package com.doug.newsapp.di.module

import com.doug.newsapp.di.component.NewsComponent
import dagger.Module

/**
 * Module that contains all subcomponents from appcomponent
 */
@Module(subcomponents = [NewsComponent::class])
abstract class SubComponentsModule
