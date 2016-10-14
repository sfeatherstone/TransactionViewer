package com.simonfea.transactionviewer;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by simonfea on 14/10/2016.
 */

public class Util {

    static String readFileFromDownloads(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloads,filename);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();

        }
        return text.toString();
    }
}
