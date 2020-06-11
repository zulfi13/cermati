package com.test.cermati.core.ui

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH: RecyclerView.ViewHolder, M> : RecyclerView.Adapter<VH>() {

    protected var models: MutableList<M> = mutableListOf()

    open fun loadData(models: MutableList<M>){
        this.models = models
        notifyDataSetChanged()
    }

    open fun addData(models: MutableList<M>){
        this.models.addAll(models)
        notifyDataSetChanged()
    }

    open fun clearData(){
        this.models.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = models.size

}