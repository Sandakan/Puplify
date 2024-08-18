package com.adsandakannipunajith.puplify.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adsandakannipunajith.puplify.App;
import com.adsandakannipunajith.puplify.Models.EducationalItemModel;
import com.adsandakannipunajith.puplify.Models.ProductModel;

import java.util.ArrayList;

public class EducationalContentDAO {
    private final SQLiteDatabase database;

    public EducationalContentDAO() {
        database = App.getDatabase();
    }

    public ArrayList<EducationalItemModel> getEducationItems() {

        Cursor cursor = database.rawQuery("SELECT * FROM educational_content", null);
        ArrayList<EducationalItemModel> educationalItems = new ArrayList<>();

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            EducationalItemModel educationalItem = EducationalItemModel.fromCursor(cursor);
            educationalItems.add(educationalItem);
        }

        cursor.close();
        return educationalItems;
    }

}
