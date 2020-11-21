package com.apro.paraflight.ui.logbook.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apro.core.ui.adapter.ListItem
import com.apro.core.ui.inflater
import com.apro.core.ui.onClick
import com.apro.paraflight.databinding.ItemLogbookBinding
import com.apro.paraflight.ui.logbook.item.LogbookListItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class LogbookDelegate(val onItemClick: (LogbookListItem) -> Unit) : AbsListItemAdapterDelegate<LogbookListItem, ListItem, LogbookDelegate.VH>() {


  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int) = item is LogbookListItem

  override fun onCreateViewHolder(parent: ViewGroup) = VH(ItemLogbookBinding.inflate(parent.inflater(), parent, false))

  override fun onBindViewHolder(listItem: LogbookListItem, holder: VH, payloads: MutableList<Any>) {
    holder.bind(listItem)
  }

  inner class VH(val binding: ItemLogbookBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(listItem: LogbookListItem) {
      with(binding) {
        title = listItem.title
        duration = listItem.duration
        root.onClick { onItemClick.invoke(listItem) }
      }
    }
  }
}

