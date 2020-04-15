package com.lucers.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * FileDownloadService
 *
 * @author Lucers
 * @date 2020/4/15
 */
public class FileDownloadService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
