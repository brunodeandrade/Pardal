package helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

/**
 * Created by luisresende on 28/05/15.
 */
public class ListViewSearch {

    public static TextWatcher searchListView(final ArrayAdapter arrayAdapter){

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        return textWatcher;
    }
}
