package com.mr47.vazhehdictionary.Models

class Vocab {
    var Id: Int
    var Persian: String
    var English: String

    constructor (id: Int, persian:String, english: String) {
        Id = id
        Persian = persian
        English = english
    }
}