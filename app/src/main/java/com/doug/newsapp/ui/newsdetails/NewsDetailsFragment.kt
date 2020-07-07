package com.doug.newsapp.ui.newsdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.doug.newsapp.R
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.helpers.FragOperation
import com.doug.newsapp.helpers.executeFragOperation
import com.doug.newsapp.ui.base.BaseFragment
import com.doug.newsapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Responsible for showing the news details
 */
class NewsDetailsFragment : BaseFragment() {

    private var article: Article? = null

    companion object {
        const val TAG = "NewsDetailsFragment"
        const val ARTICLE_ARGUMENT = "ARTICLE_ARGUMENT"
        fun newInstance(article: Article): NewsDetailsFragment {

            val frag = NewsDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARTICLE_ARGUMENT, article)
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_news_detail,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        article = getArticleArgument()
        setupInfo(article)
        urlTextView.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(article?.url)
            startActivity(intent)
        }
    }

    /**
     * Sets all the article info to the view
     * @param article The selected article
     */
    private fun setupInfo(article: Article?) {
        Glide.with(this).load(article?.urlToImage)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_news_placeholder)
            .into(newsImage)
        titleTextView.text = article?.title
        descriptionTextView.text = article?.description
        sourceTextView.text = getString(R.string.from_label, article?.source?.name)
        urlTextView.text = getString(R.string.read_full_news, article?.url)
    }


    private fun getArticleArgument(): Article? {
        return arguments?.getParcelable(ARTICLE_ARGUMENT)
    }

    override fun setupToolbar() {
        toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_back)
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationOnClickListener {
            fragmentManager?.beginTransaction()?.executeFragOperation(FragOperation.RemoveOperation(), this,TAG)
        }
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}
