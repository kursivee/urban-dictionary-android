package com.kursivee.urbandictionary.results.presentation.recyclerview

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kursivee.urbandictionary.R
import com.kursivee.urbandictionary.common.util.date.ext.parseDate
import com.kursivee.urbandictionary.databinding.ResultItemBinding
import com.kursivee.urbandictionary.results.domain.entity.ResultEntity

class ResultsViewHolder(
    private val binding: ResultItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(result: ResultEntity, onClick: (String) -> Unit) {
        binding.tvWord.text = result.word
        binding.tvDefinition.movementMethod = LinkMovementMethod.getInstance()
        binding.tvExample.movementMethod = LinkMovementMethod.getInstance()
        binding.tvDefinition.text = result.definition.toSpannable(onClick).trim()
        binding.tvExample.text = result.example.toSpannable(onClick).trim()
        binding.tvAuthor.text = binding.root.context.getString(
            R.string.author, result.author, result.writtenOn.parseDate()
        )
        binding.tvDownvote.text = result.thumbsDownCount.toString()
        binding.tvUpvote.text = result.thumbsUpCount.toString()
    }

    private fun String.toSpannable(onClick: (String) -> Unit): SpannableStringBuilder {
        // This is bad. This assumes that the brackets are balanced which is a bad bad assumption
        // Need to implement some sort of stack to ensure validity
        val builder = SpannableStringBuilder()
        var i = 0
        while (i < length) {
            var appendString = ""
            while (i < length && get(i) != '[') {
                appendString += get(i)
                i++
            }
            builder.append(appendString)
            var linkString = ""
            if (i < length && get(i) == '[') {
                i++
                while (get(i) != ']') {
                    linkString += get(i)
                    i++
                }
                i++
                val startIndex = builder.length
                builder.append(linkString)
                val span = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onClick(linkString)
                    }
                }
                builder.setSpan(span, startIndex, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
        return builder
    }
}
