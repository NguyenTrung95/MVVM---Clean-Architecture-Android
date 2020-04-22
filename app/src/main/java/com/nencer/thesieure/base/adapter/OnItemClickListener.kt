package com.nencer.thesieure.base.adapter

interface OnItemClickListener<T> {
    fun onClick(item:T, position: Int)
}