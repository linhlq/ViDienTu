package com.linhlee.vidientu.fragments.phonerechargefragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lequy on 4/26/2017.
 */

public class PrepayFragment extends BaseFragment implements View.OnClickListener {
    private EditText editPhone;
    private ImageView contactButton;

    private static int PICK_CONTACT = 1;

    public static PrepayFragment newInstance() {

        Bundle args = new Bundle();

        PrepayFragment fragment = new PrepayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_prepay;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        editPhone = (EditText) rootView.findViewById(R.id.edit_phone);
        contactButton = (ImageView) rootView.findViewById(R.id.contact_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        contactButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_button:
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getActivity().getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                editPhone.setText(number);

                // Do something with the phone number...
            }
        }
    }
}
