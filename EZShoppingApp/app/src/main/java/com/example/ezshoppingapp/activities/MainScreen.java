package com.example.ezshoppingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ezshoppingapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        View qrGenerator = findViewById(R.id.qr);
        View logOut = findViewById(R.id.logOut);
        ImageView qrImg = (ImageView) findViewById(R.id.QrcodeImg);

        qrGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qrCodeContent = "test";
                try {
                    generateQRCode_general(qrCodeContent, qrImg);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void generateQRCode_general(String data, ImageView img)throws WriterException {
        com.google.zxing.Writer writer = new QRCodeWriter();
        String finalData = Uri.encode(data, "utf-8");
        int size = 512;

        BitMatrix bm = writer.encode(finalData, BarcodeFormat.QR_CODE,size, size);
        Bitmap ImageBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < size; i++) {//width
            for (int j = 0; j < size; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
            }
        }

        if (ImageBitmap != null) {
            img.setImageBitmap(ImageBitmap);
        } else {
            Toast.makeText(getApplicationContext(), "Error",
                    Toast.LENGTH_SHORT).show();
        }
    }
}