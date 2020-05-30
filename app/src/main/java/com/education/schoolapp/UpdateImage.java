package com.education.schoolapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateImage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value);
    }
    @NonNull
    public MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = null;
        file = com.education.schoolapp.FileUtils.getFile(UpdateImage.this,fileUri);
        RequestBody requestFile = null;
        requestFile = RequestBody.create(
                MediaType.parse(Objects.requireNonNull(UpdateImage.this.getContentResolver().getType(fileUri))),
                file
        );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
