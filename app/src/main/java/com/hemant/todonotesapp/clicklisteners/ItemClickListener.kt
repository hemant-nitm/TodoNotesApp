package com.hemant.todonotesapp.clicklisteners

import com.hemant.todonotesapp.db.Notes


interface ItemClickListener {
    fun onClick(notes: Notes?)
    fun onUpdate(notes: Notes?)



}