package com.gorrotowi.booklisting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gorrotowi.booklisting.adapters.AdapterBooks;
import com.gorrotowi.booklisting.entitys.ItemBook;
import com.gorrotowi.booklisting.utils.BooksLoader;
import com.gorrotowi.booklisting.utils.CheckNetworkStatus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ItemBook>> {

    private ListView lvBooks;
    private EditText edtxtSearchBooks;
    private Button btnSearch;
    private TextView txtEmptyView;
    private ProgressBar progressBar;

    private AdapterBooks adapterBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvBooks = (ListView) findViewById(R.id.lvBooks);
        edtxtSearchBooks = (EditText) findViewById(R.id.edtxtBook);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtEmptyView = (TextView) findViewById(R.id.txtEmptyView);
        progressBar = (ProgressBar) findViewById(R.id.loadingIndicator);

        CheckNetworkStatus.init(this);

        lvBooks.setEmptyView(txtEmptyView);

        adapterBooks = new AdapterBooks(this, new ArrayList<ItemBook>());
        lvBooks.setAdapter(adapterBooks);

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ItemBook book = adapterBooks.getItem(position);
                Uri bookUri = Uri.parse(book.getUrlBook());
                Intent intent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isConnected()) {
                    String text = edtxtSearchBooks.getText().toString();
                    if (!text.isEmpty() && !text.trim().isEmpty()) {
                        txtEmptyView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        searchBook(String.format(getString(R.string.baseurl), text));
                    } else {
                        edtxtSearchBooks.setText(null);
                        Toast.makeText(MainActivity.this, R.string.error_empty_book_field, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    txtEmptyView.setVisibility(View.VISIBLE);
                    txtEmptyView.setText(R.string.error_networkstatus_no_connected);
                }
            }
        });
    }

    private void searchBook(String text) {
        LoaderManager loaderManager = getSupportLoaderManager();
        Bundle args = new Bundle();
        args.putString("url", text);
        loaderManager.initLoader(1, args, this);
    }

    @Override
    public Loader<List<ItemBook>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, args.getString("url"));
    }

    @Override
    public void onLoadFinished(Loader<List<ItemBook>> loader, List<ItemBook> data) {
        progressBar.setVisibility(View.GONE);
        txtEmptyView.setText(R.string.empty_txt_message);
        adapterBooks.clear();
        if (data != null && !data.isEmpty()) {
            adapterBooks.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ItemBook>> loader) {
        adapterBooks.clear();
    }
}
