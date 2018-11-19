package com.example.pesti.pdfviewer;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PDF_CODE= 1000;
    Button btn_open_assets,btn_open_storage,btn_open_from_internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // olvasas telefonbol
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }
                }).check();


        btn_open_assets = (Button)findViewById(R.id.btn_open_assets);
        btn_open_storage = (Button)findViewById(R.id.btn_open_storage);
        btn_open_from_internet = (Button)findViewById(R.id.btn_open_from_internet);


        btn_open_from_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ViewActivity.class);
                intent.putExtra("ViewType", "internet");
                startActivity(intent);
            }
        });


        btn_open_assets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("ViewType","assets");
                startActivity(intent);
            }
        });

        btn_open_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserPDF= new Intent(Intent.ACTION_GET_CONTENT);
                browserPDF.setType("application=pdf");
                browserPDF.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(browserPDF,"Select PDF"),PICK_PDF_CODE);
            }
        });
    }


    // ctrl + o

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data !=null)
        {
            Uri selectedPDF = data.getData();
            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri", selectedPDF.toString());
            startActivity(intent);
        }
    }
}
