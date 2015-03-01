package test.jun.touchtouch;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by user on 15. 2. 27.
 */
public class PackageListDialog extends Dialog implements View.OnClickListener {

    private ListView list;
    private EditText filterText = null;
    ArrayAdapter<String> adapter = null;
    private static final String TAG = "PackageList";

    private String index;

    public PackageListDialog(Context context, String[] PackageList) {
        super(context);


        setContentView(R.layout.pack_list_view);
        this.setTitle("실행할 어플을 선택하시오");

        filterText = (EditText) findViewById(R.id.EditBox);
        filterText.addTextChangedListener(filterTextWatcher);
        list = (ListView) findViewById(R.id.List);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, PackageList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                index = (String) list.getItemAtPosition(position);
                dismiss();

            }

        });

    }

    @Override
    public void onClick(View v) {

    }

    public String getPosition(){
        return index;
    }

private TextWatcher filterTextWatcher = new TextWatcher() {

    public void afterTextChanged(Editable s) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }
};
    @Override
    public void onStop(){
        filterText.removeTextChangedListener(filterTextWatcher);
    }
}