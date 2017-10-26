package com.tcsrmobile.thecaosieure.fragments.phonerechargefragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tcsrmobile.thecaosieure.MyApplication;
import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.dialogs.LoadingDialog;
import com.tcsrmobile.thecaosieure.fragments.BaseFragment;
import com.tcsrmobile.thecaosieure.models.CardObject;
import com.tcsrmobile.thecaosieure.models.CardRequest;
import com.tcsrmobile.thecaosieure.models.OtherRequest;
import com.tcsrmobile.thecaosieure.models.User;
import com.tcsrmobile.thecaosieure.retrofit.IRetrofitAPI;
import com.tcsrmobile.thecaosieure.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lequy on 4/26/2017.
 */

public class PrepayFragment extends BaseFragment implements View.OnClickListener {
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private EditText editPhone;
    private ImageView contactButton;
    private Spinner spinnerName, spinnerPrice;
    private ArrayAdapter<String> nameAdapter, priceAdapter;
    private ArrayList<String> listName, listPrice;
    private ArrayList<String> listInfo;
    private ArrayList<CardObject> listCard;
    private EditText editMk2;
    private TextView textThanhToan;
    private Button buttonContinue;
    private LoadingDialog loadingDialog;

    private int curPos = 0;
    private static int PICK_CONTACT = 1;

    private Call<CardRequest> getCardInfoAPI;
    private Call<OtherRequest> topupMobileAPI;

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
        mGson = MyApplication.getGson();
        mRetrofit = MyApplication.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = MyApplication.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        loadingDialog = new LoadingDialog(getActivity());

        editPhone = (EditText) rootView.findViewById(R.id.edit_phone);
        contactButton = (ImageView) rootView.findViewById(R.id.contact_button);
        spinnerName = (Spinner) rootView.findViewById(R.id.spinner_name);
        spinnerPrice = (Spinner) rootView.findViewById(R.id.spinner_price);
        editMk2 = (EditText) rootView.findViewById(R.id.edit_mk2);
        textThanhToan = (TextView) rootView.findViewById(R.id.text_thanh_toan);
        buttonContinue = (Button) rootView.findViewById(R.id.button_continue);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listName = new ArrayList<>();
        listPrice = new ArrayList<>();
        listInfo = new ArrayList<>();
        getListCard();

        nameAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, listName);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerName.setAdapter(nameAdapter);
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curPos = position;

                spinnerPrice.setSelection(0);
                textThanhToan.setText(String.format("%.0f", getThanhToan(0)) + " VNĐ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        priceAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, listPrice);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(priceAdapter);
        spinnerPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textThanhToan.setText(String.format("%.0f", getThanhToan(position)) + " VNĐ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Constant.increaseHitArea(contactButton);
        contactButton.setOnClickListener(this);
        buttonContinue.setOnClickListener(this);
    }

    private void getListCard() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("channel", "telco");

        getCardInfoAPI = mRetrofitAPI.getCardInfo(body);
        getCardInfoAPI.enqueue(new Callback<CardRequest>() {
            @Override
            public void onResponse(Call<CardRequest> call, Response<CardRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    listCard = response.body().getData();

                    for (int i = 0; i < listCard.size(); i++) {
                        listName.add(listCard.get(i).getBankName());
                    }
                    nameAdapter.notifyDataSetChanged();

                    listInfo.addAll(Arrays.asList(listCard.get(0).getInfo().split("\\|")));
                    for (int i = 0; i < listInfo.size(); i++) {
                        listPrice.add(listInfo.get(i) + " VNĐ");
                    }
                    priceAdapter.notifyDataSetChanged();

                    textThanhToan.setText(String.format("%.0f", getThanhToan(0)) + " VNĐ");
                } else {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardRequest> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void topupMobile() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("sodienthoai", editPhone.getText().toString());
        body.put("menhgia", listInfo.get(spinnerPrice.getSelectedItemPosition()));
        body.put("mk2", editMk2.getText().toString());

        loadingDialog.show();
        topupMobileAPI = mRetrofitAPI.topupMobile(user.getToken(), body);
        topupMobileAPI.enqueue(new Callback<OtherRequest>() {
            @Override
            public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();

                editPhone.setText("");
                editMk2.setText("");

                Intent i = new Intent(Constant.UPDATE_INFO);
                getActivity().sendBroadcast(i);
            }

            @Override
            public void onFailure(Call<OtherRequest> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }

    private float getThanhToan(int spinnerPos) {
        return Integer.valueOf(listInfo.get(spinnerPos)) * listCard.get(curPos).getDiscount() / 100;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_button:
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT);
                break;
            case R.id.button_continue:
                if (sharedPreferences.getBoolean(Constant.IS_LOGIN, false)) {
                    topupMobile();
                } else {
                    Toast.makeText(getActivity(), "Bạn chưa đăng nhập, vui lòng đăng nhập để có thể sử dụng tính năng này", Toast.LENGTH_SHORT).show();
                }
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
