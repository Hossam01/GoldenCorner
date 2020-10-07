package com.golden.goldencorner.data.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 2;
    private int dy = -1;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        this.dy = dy;
    }
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int totalItemCount   = recyclerView.getLayoutManager().getItemCount();
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int lastCompletelyVisibleItemPosition = -1;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView
                    .getLayoutManager()).findLastCompletelyVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            lastCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView
                    .getLayoutManager()).findLastCompletelyVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager =  ((StaggeredGridLayoutManager)
                    recyclerView.getLayoutManager());

            totalItemCount = staggeredGridLayoutManager.getItemCount();
            int[] spanCount = new int[staggeredGridLayoutManager.getSpanCount()];
            int[] lastVisibleItems = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(spanCount);

            if (staggeredGridLayoutManager.getSpanCount() == 1) {
                lastCompletelyVisibleItemPosition = lastVisibleItems[0];
            } else if (staggeredGridLayoutManager.getSpanCount() == 2) {
                lastCompletelyVisibleItemPosition = Math.max(lastVisibleItems[0], lastVisibleItems[1]);
            } else if (staggeredGridLayoutManager.getSpanCount() == 3) {
                lastCompletelyVisibleItemPosition = Math.max(Math.max(lastVisibleItems[0], lastVisibleItems[1]), lastVisibleItems[2]);
            }
        }
        // (dy > 0) means RecyclerView Scrolling up
        if(lastCompletelyVisibleItemPosition >= 0
                /*&& (getTotalPageCount() > totalItemCount)*/
                && newState == RecyclerView.SCROLL_STATE_IDLE && dy >= 0
                && (lastCompletelyVisibleItemPosition+visibleThreshold) >= totalItemCount) {
            loadMoreItems();
        }
    }
    public abstract int getTotalPageCount();
    protected abstract void loadMoreItems();
    public abstract int getPageIndex();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();

}
