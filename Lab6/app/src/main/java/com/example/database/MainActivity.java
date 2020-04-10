package com.example.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int zap = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, weight INTEGER, height INTEGER, age INTEGER)");
        db.execSQL("INSERT INTO users VALUES ('Мартасов Илья', 84, 194, 20);");
        db.execSQL("INSERT INTO users VALUES ('Панченко Дарья', 44, 167, 20);");
        db.execSQL("INSERT INTO users VALUES ('Щерба Артем', 80, 184, 20);");
        db.execSQL("INSERT INTO users VALUES ('Скирда Андрей', 60, 168, 20);");
        db.execSQL("INSERT INTO users VALUES ('Ланг Артем', 73, 175, 20);");
        db.execSQL("INSERT INTO users VALUES ('Киселев Денис', 84, 176, 20);");

        Cursor query = db.rawQuery("SELECT * FROM users ORDER BY weight;", null);

        TextView[] textView = new TextView[(zap + 1) * 4];
        for(int i = 0; i < (zap + 1) * 4; i++)
        {
            textView[i] = new TextView(this);
        }
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);

        TableRow[] tableRow = new TableRow[zap + 1];

        int k = 0;
        int p = 0;

        if(query.moveToFirst())
        {
            if(k == 0)
            {
                tableRow[k] = new TableRow(this);
                textView[0].setText("Имя");
                textView[1].setText("Вес");
                textView[2].setText("Рост");
                textView[3].setText("Возраст");
                for(int j = 0; j < 4; j++)
                {
                    tableRow[k].addView(textView[j]);
                }
                tableLayout.addView(tableRow[k]);
                k++;
                p += 4;
            }
            do {
                tableRow[k] = new TableRow(this);
                String name = query.getString(0);
                textView[0 + p].setText(name);
                int weight = query.getInt(1);
                textView[1 + p].setText(Integer.toString(weight));
                int height = query.getInt(2);
                textView[2 + p].setText(Integer.toString(height));
                int age = query.getInt(3);
                textView[3 + p].setText(Integer.toString(age));
                for(int j = 0; j < 4; j++)
                {
                    tableRow[k].addView(textView[j + p]);
                }
                tableLayout.addView(tableRow[k]);
                k++;
                p += 4;
            }
            while(query.moveToNext());
        }
        setContentView(tableLayout);
        query.close();
        db.close();
    }
}