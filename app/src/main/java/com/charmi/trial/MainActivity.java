package com.charmi.trial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {

    public String FILE_NAME="civil.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openPDF(View v)
    {
        CopyReadAssets();
    }

    private void CopyReadAssets()
    {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), FILE_NAME);
        try
        {
            in = assetManager.open(FILE_NAME);
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            if(copyFile(in, out))
            {
                Toast.makeText(MainActivity.this, "copied", Toast.LENGTH_SHORT).show();

            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "file not copied", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/"+ FILE_NAME),
                "application/pdf");
        Toast.makeText(this,"file://" + getFilesDir() + "/"+ FILE_NAME , Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private boolean copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
        return  true;
    }

}



