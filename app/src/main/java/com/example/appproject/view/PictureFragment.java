package com.example.appproject.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appproject.R;
import com.example.appproject.model.Picture;
import com.example.appproject.presenter.RxPresenter;
import com.example.appproject.view.dummy.DummyContent;
import com.example.appproject.view.dummy.DummyContent.DummyItem;
import com.yalantis.phoenix.PullToRefreshView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PictureFragment extends Fragment {

    private static final String TAG = "PictureFragment";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public static final int REFRESH_DELAY = 500;

    private SwipeRecyclerView mRecyclerView;
    private PictureRecyclerViewAdapter mAdapter;
    private RxPresenter mRxPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PictureFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PictureFragment newInstance(int columnCount) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mRxPresenter = new RxPresenter(getContext());
        mRxPresenter.subscribePicture();
        //mRxPresenter.requestPicture();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_list, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        // Set the adapter
        Context context = view.getContext();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
        mRecyclerView = (SwipeRecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        List<Picture> pictures = mRxPresenter.getPictures();
        mAdapter = new PictureRecyclerViewAdapter(getContext(), pictures, mListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.useDefaultLoadMore();
        mRecyclerView.loadMoreFinish(false, true);
        mRecyclerView.setLoadMoreListener(mLoadMoreListener);
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.d(TAG, "onRefresh: ");
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000); // 延时模拟请求服务器。
            mRxPresenter.requestPicture();
            mRxPresenter.setListener(new RxPresenter.PictureListener() {
                @Override
                public void onPictureDataUpdated(List list) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.updateDataSet(list);
                        }
                    });
                }
            });
        }
    };

    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            Log.d(TAG, "onLoadMore: ");
            mRxPresenter.requestPicture();
            mRxPresenter.setListener(new RxPresenter.PictureListener() {
                @Override
                public void onPictureDataUpdated(List list) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.updateDataSet(list);
                            mRecyclerView.loadMoreFinish(false, true);
                        }
                    });
                }
            });
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        mRxPresenter.destroy();
        super.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Picture item, int position);
    }
}
