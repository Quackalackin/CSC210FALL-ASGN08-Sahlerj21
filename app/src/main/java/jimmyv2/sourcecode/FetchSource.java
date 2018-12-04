package jimmyv2.sourcecode;
import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchSource extends AsyncTask<String, Void, String> {

    WeakReference<TextView> resultTextView;

    FetchSource(TextView displayResult){
        this.resultTextView = new WeakReference<>(displayResult);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getUrlResponse(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        resultTextView.get().setText(s);
    }
}