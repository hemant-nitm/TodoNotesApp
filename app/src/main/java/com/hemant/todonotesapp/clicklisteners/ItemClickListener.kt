package com.hemant.todonotesapp.clicklisteners

import com.hemant.todonotesapp.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes?)

}