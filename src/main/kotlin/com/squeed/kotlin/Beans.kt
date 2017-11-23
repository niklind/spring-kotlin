package com.squeed.kotlin

import com.samskivert.mustache.Mustache
import com.samskivert.mustache.Mustache.compiler
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

fun beans() = beans {

    bean {
        val loader = ref<Mustache.TemplateLoader>()
        compiler().escapeHTML(false).withLoader(loader)
    }

}

class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(context: GenericApplicationContext) =
            beans().initialize(context)

}