package com.example.dbprj.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ListController {
    private companion object {
        const val mapping = "list"
        const val paramPage = "page"
        const val defaultValueForParamPage = "1"
    }

    @GetMapping(mapping)
    fun listPosts(@RequestParam(name = paramPage, required = false, defaultValue = defaultValueForParamPage) page: String,
                  model: Model) {

    }
}