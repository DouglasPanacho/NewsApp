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
import com.doug.newsapp.helpers.FRAG_OPERATION
import com.doug.newsapp.helpers.ItemDecoratorRecyclerView
import com.doug.newsapp.helpers.PaginationScrollControl
import com.doug.newsapp.helpers.constants.Constants
import com.doug.newsapp.helpers.executeFragOperation
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
    private lateinit var paginationScrollControl: PaginationScrollControl<NewsAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity?.application as App).appComponent.provideNewsComponent().create().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelProvider).get(NewsViewModel::class.java)
    }

    override fun onItemClicked(t: Any) {
        fragmentManager?.beginTransaction()?.executeFragOperation(
            FRAG_OPERATION.ADD,
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
        setupObservers()
        loadMoreItems(0)
    }

    private fun setupObservers() {
        viewModel.getNewsLiveData().observe(viewLifecycleOwner, Observer {
            newsAdapter.addNewsItems(it, isRefreshed)
            if (isRefreshed) isRefreshed = false
        })
        viewModel.getStatus().observe(viewLifecycleOwner, Observer { handleStatus(it) })
    }

    /**
     * Used to handle view status, updating all the necessary controllers
     */
    private fun handleStatus(status: Constants.VIEW_STATUS) {
        when (status) {
            Constants.VIEW_STATUS.LOADING -> {
                if (emptyView.visibility == View.VISIBLE) emptyView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            Constants.VIEW_STATUS.EMPTY -> {
                if (newsAdapter.itemCount == 0) {
                    emptyView.visibility =
                        View.VISIBLE
                }
                progressBar.visibility = View.GONE
            }
            Constants.VIEW_STATUS.SUCCESS -> {
                emptyView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
        // used to control the refresh, to avoid adding the same items again on the view
        swipeToRefresh.isRefreshing = false
    }

    private fun setupRecyclerView() {
        val orientation = resources.configuration.orientation
        val layoutManager = CustomGridLayoutManager(context!!, orientation)
        val decorator =
            ItemDecoratorRecyclerView(layoutManager.spanCount)
        newsAdapter.setOnClickListener(this)
        paginationScrollControl =
            PaginationScrollControl(
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

    /**
     * Load more items
     * @param pageCount next page to be requested
     */
    override fun loadMoreItems(pageCount: Int) {
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
        loadMoreItems(0)
    }
}
