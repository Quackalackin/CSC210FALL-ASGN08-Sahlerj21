package jimmyv2.sourcecode;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Variables for the search input field and results TextViews.
    TextView textView, displayResult;
    EditText enterUrl;
    Button search;
    Spinner spinner;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView1);
        displayResult = findViewById(R.id.result_display);
        enterUrl = findViewById(R.id.enter_url);
        search = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.http));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
        spinner.setAdapter(adapter);
    }

    public void getSourceCode(View view) {
        // Get the search string from the input field.
        String urlText = enterUrl.getText().toString();
        String queryString = type + urlText;

        //Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager != null){
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // Check the status of the network connection.
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        if (connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        // If the network is available, connected, and the search field
        // is not empty, start a FetchSource AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && urlText.length() != 0) {
            new FetchSource(displayResult).execute(queryString);
            displayResult.setText(R.string.fetching);
            // Otherwise update the TextView to tell the user there is no
            // connection, or no search term.
        }else {
            if (urlText.length() == 0){
                displayResult.setText(R.string.enter_url);
            }else {
                displayResult.setText(R.string.no_network);
            }
        }

    }
}
