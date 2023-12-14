
package com.example.suitcase;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SendMessage extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    static final int PICK_CONTACT=1;
    ImageButton sendmsg, contact;
    EditText number, smscontent;
    String permission[] = {"android.permission.SEND_SMS", "android.permission.READ_CONTACTS"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        number = findViewById(R.id.number);
        sendmsg = findViewById(R.id.sendmsg);
        contact = findViewById(R.id.contact);



        String cont = "Title: "+getIntent().getStringExtra("title")+"\nPrice: Rs."+getIntent().getStringExtra("price")+"\nDescription: "+getIntent().getStringExtra("content");

        smscontent = findViewById(R.id.smscontent);
        smscontent.setText(cont);
        requestPermissions(permission, 100);

        contact.setOnClickListener(v-> {
            requestPermissions(permission, 100);
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        });
        sendmsg.setOnClickListener(v-> sendSMS());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }else{
                Utility.showToast(SendMessage.this, "Please provide the permissions to continue");
                return;
            }
        }
    }

    void sendSMS(){
        requestPermissions(permission, 100);
        String con = smscontent.getText().toString();
        String numb = number.getText().toString().trim();
        if(numb.length()>10 || numb.length()<10){
            number.setError("Enter valid mobile number");
            return;
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numb, null, con, null, null);
            startActivity(new Intent(SendMessage.this, HomePage.class));
            finish();
            Toast.makeText(this, "Sent Successsfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        @SuppressLint("Range") String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            number.setText(cNumber);

                        }
                    }
                }
                break;
        }
    }


}