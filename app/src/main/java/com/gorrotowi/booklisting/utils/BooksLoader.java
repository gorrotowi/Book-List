package com.gorrotowi.booklisting.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.gorrotowi.booklisting.entitys.ItemBook;

import java.util.List;

/**
 * Created by Gorro on 14/01/17.
 */

public class BooksLoader extends AsyncTaskLoader<List<ItemBook>> {

    private String url;

    public BooksLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<ItemBook> loadInBackground() {
        if (url == null) {
            return null;
        } else {
            return NetWorkUtils.fetchBooksData(url);
        }
    }
}
