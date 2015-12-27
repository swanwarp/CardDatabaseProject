package com.card_database.mtg.carddatabasemtg;

import android.app.IntentService;
import android.content.Intent;

/**
 * Сервис для скачивания базы данных карт.
 */
public class DownloadService extends IntentService implements ProgressCallback {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void onProgressChanged(int progress) {

    }

}
