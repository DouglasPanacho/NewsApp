package com.doug.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doug.newsapp.App
import com.doug.newsapp.R
import com.doug.newsapp.data.remote.models.Article
import com.doug.newsapp.helpers.*
import com.doug.newsapp.helpers.layoutManagers.CustomGridLayoutManager
import com.doug.newsapp.ui.base.BaseAdapter
import com.doug.newsapp.ui.base.BaseFragment
import com.doug.newsapp.ui.base.BaseViewModelFactory
import com.doug.newsapp.ui.newsdetails.NewsDetailsFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * This fragment is responsible for showing all the news received from the api
 */
class NewsFragment : BaseFragment(), BaseAdapter.OnItemClickListener,
    PaginationScrollControl.PaginationController, SwipeRefreshLayout.OnRefreshListener {

    companion object {

        const val TAG = "HomeFragment"
        const val SAVE_STATE = "SaveState"

        fun getInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    @Inject
    lateinit var viewModelProvider: BaseViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private var isRefreshed = false
    private lateinit var viewModel: NewsViewModel
    private var recoveredState: Boolean = false
    private lateinit var paginationScrollControl: PaginationScrollControl<NewsAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity?.application as App).appComponent.provideNewsComponent().create().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelProvider).get(NewsViewModel::class.java)
    }

    override fun onItemClicked(t: Any) {
        fragmentManager?.beginTransaction()?.executeFragOperation(
            FragOperation.AddOperation(),
            NewsDetailsFragment.newInstance(t as Article),
            NewsDetailsFragment.TAG
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, isRefreshed)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeToRefresh.setOnRefreshListener(this)
        setupRecyclerView()
        if (isViewRestoration(savedInstanceState)) {
            recoveredState = true
            newsAdapter.addNewsItems(viewModel.savedNews, true)
        } else {
            getMoreItems()
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getViewStatus().observe(viewLifecycleOwner, Observer {
            if (!recoveredState)
                updateView(it)
            else recoveredState = false
        })
    }

    private fun updateView(viewStatus: ViewStatus<MutableList<Article>>) {
        when (viewStatus) {
            is ViewStatus.SuccessStatus -> {
                newsAdapter.addNewsItems(
                    viewStatus.items,
                    isRefreshed
                )
            }
            is ViewStatus.LoadingStatus -> {
                if (emptyView.visibility == View.VISIBLE) emptyView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is ViewStatus.ErrorStatus -> {
                emptyView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
        if (isRefreshed) isRefreshed = false
    }

    private fun setupRecyclerView() {
        val orientation = resources.configuration.orientation
        val layoutManager = CustomGridLayoutManager(context!!, orientation)
        val decorator = ItemDecoratorRecyclerView(layoutManager.spanCount)
        newsAdapter.setOnClickListener(this)
        paginationScrollControl = PaginationScrollControl(
            newsAdapter,
            layoutManager,
            this
        )
        newsRecyclerView.also {
            it.addItemDecoration(decorator)
            it.layoutManager = layoutManager
            it.adapter = newsAdapter
            it.addOnScrollListener(paginationScrollControl)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(SAVE_STATE, true)
        super.onSaveInstanceState(outState)
    }

    /**
     * Load more items
     * @param pageCount next page to be requested
     */
    override fun getMoreItems(pageCount: Int) {
        viewModel.getNews(pageCount)
    }

    override fun setupToolbar() {
        toolbar.title = getString(R.string.app_name)
    }

    /**
     * Refreshes the content
     */
    override fun onRefresh() {
        paginationScrollControl.clear()
        isRefreshed = true
        getMoreItems()
    }
}
