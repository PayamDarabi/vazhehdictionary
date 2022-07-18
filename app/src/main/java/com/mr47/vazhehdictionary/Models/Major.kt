package com.mr47.vazhehdictionary.Models

class Major{

    var Id : Int
    var EnglishTitle : String
    var PersianTitle: String

    constructor (id : Int, englishTitle: String, persianTitle: String) {
        Id= id
        EnglishTitle= englishTitle
        PersianTitle = persianTitle
    }

    override fun toString(): String {
        return this.PersianTitle // What to display in the Spinner list.
    }

}