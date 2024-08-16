package com.adsandakannipunajith.puplify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "puplify.db";
    private static final int DATABASE_VERSION = 1;
    private static final String createUserSql = "CREATE TABLE IF NOT EXISTS \"user\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"first_name\" TEXT NOT NULL,\n" +
            "\t\"last_name\" TEXT NOT NULL,\n" +
            "\t\"email\" TEXT NOT NULL UNIQUE,\n" +
            "\t\"password\" TEXT NOT NULL,\n" +
            "\t\"address\" TEXT,\n" +
            "\t\"created_at\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";
    private static final String createProductSql = "CREATE TABLE IF NOT EXISTS \"product\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"name\" TEXT NOT NULL,\n" +
            "\t\"description\" TEXT NOT NULL,\n" +
            "\t\"image\" INTEGER NOT NULL,\n" +
            "\t\"brand\" TEXT NOT NULL,\n" +
            "\t\"type\" TEXT NOT NULL,\n" +
            "\t\"age_group\" TEXT NOT NULL,\n" +
            "\t\"price\" REAL NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";
    private static final String createPaymentMethodSql = "CREATE TABLE IF NOT EXISTS \"payment_method\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"card_number\" TEXT NOT NULL,\n" +
            "\t\"card_holder_name\" TEXT NOT NULL,\n" +
            "\t\"expiration_date\" TEXT NOT NULL,\n" +
            "\t\"cvv\" TEXT NOT NULL,\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"user_id\") REFERENCES \"user\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createCartSql = "CREATE TABLE IF NOT EXISTS \"cart\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"status\" TEXT NOT NULL DEFAULT 'PENDING',\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"user_id\") REFERENCES \"user\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createCartItemSql = "CREATE TABLE IF NOT EXISTS \"cart_item\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"cart_id\" INTEGER NOT NULL,\n" +
            "\t\"product_id\" INTEGER NOT NULL,\n" +
            "\t\"quantity\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"cart_id\") REFERENCES \"cart\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";

    private static final String createOrderSql = "CREATE TABLE IF NOT EXISTS \"order\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"total_price\" REAL NOT NULL,\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"user_id\") REFERENCES \"user\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createOrderItemSql = "CREATE TABLE IF NOT EXISTS \"order_item\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"quantity\" INTEGER NOT NULL,\n" +
            "\t\"product_id\" INTEGER NOT NULL,\n" +
            "\t\"order_id\" INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"order_id\") REFERENCES \"order\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createReviewSql = "CREATE TABLE IF NOT EXISTS \"review\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"product_id\" INTEGER NOT NULL,\n" +
            "\t\"user_id\" INTEGER NOT NULL,\n" +
            "\t\"rating\" INTEGER NOT NULL CHECK(rating BETWEEN 1 AND 5),\n" +
            "\t\"comment\" TEXT NOT NULL,\n" +
            "\t\"created_at\" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "\tPRIMARY KEY(\"id\"),\n" +
            "\tFOREIGN KEY (\"product_id\") REFERENCES \"product\"(\"id\")\n" +
            "\tON UPDATE NO ACTION ON DELETE NO ACTION\n" +
            ");";
    private static final String createEducationalContentSql = "CREATE TABLE IF NOT EXISTS \"educational_content\" (\n" +
            "\t\"id\" INTEGER NOT NULL UNIQUE,\n" +
            "\t\"title\" TEXT NOT NULL,\n" +
            "\t\"description\" TEXT NOT NULL,\n" +
            "\t\"url\" TEXT NOT NULL,\n" +
            "\t\"type\" TEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";

    private static final String userSeeds = "INSERT INTO \"user\" (\"first_name\", \"last_name\", \"email\", \"password\") VALUES\n" +
            "('John', 'Doe', 'johndoe@gmail.com', '12345'),\n" +
            "('Jane', 'Doe', 'janedoe@gmailcom', '12345');";

    private static final int kibbles_delight = R.drawable.kibbles_delight;
    private static final int puppy_growth_formula = R.drawable.puppy_growth_formula;
    private static final int senior_wellness_blend = R.drawable.senior_wellness_blend;
    private static final int chicken_and_rice_canned = R.drawable.chicken_and_rice_canned;
    private static final int salmon_and_sweet_potato = R.drawable.salmon_and_sweet_potato;
    private static final int all_natural_puppy_treats = R.drawable.all_natural_puppy_treats;
    private static final int grain_free_chicken_bites = R.drawable.grain_free_chicken_bites;
    private static final int beef_and_veggie_stew = R.drawable.beef_and_veggie_stew;
    private static final int omega_3_fish_oil_supplement = R.drawable.omega_3_fish_oil_supplement;
    private static final int probiotic_digestive_health = R.drawable.probiotic_digestive_health;


    private static final String productSeeds = "INSERT INTO \"product\" (\"id\", \"name\", \"brand\", \"type\", \"age_group\", \"description\", \"price\", \"image\") VALUES\n" +
            "(1, 'Kibble Delight', 'BestBites', 'Dry Food', 'Adult', 'A balanced dry kibble made with chicken and wholesome grains for adult dogs.', 8999.00, " + kibbles_delight + "),\n" +
            "(2, 'Puppy Growth Formula', 'PupGrow', 'Dry Food', 'Puppy', 'A specially formulated dry food for growing puppies, enriched with essential nutrients.', 10499.00, " + puppy_growth_formula + "),\n" +
            "(3, 'Senior Wellness Blend', 'SeniorCare', 'Dry Food', 'Senior', 'A nutrient-rich blend designed to support the health of senior dogs.', 11999.00, " + senior_wellness_blend + "),\n" +
            "(4, 'Chicken & Rice Canned', 'TenderMeal', 'Canned Food', 'All Ages', 'A delicious canned dog food made with real chicken and rice for easy digestion.', 7499.00, " + chicken_and_rice_canned + "),\n" +
            "(5, 'Salmon & Sweet Potato', 'OceanicFeast', 'Dry Food', 'Adult', 'A grain-free formula featuring high-quality salmon and sweet potatoes.', 9699.00, " + salmon_and_sweet_potato + "),\n" +
            "(6, 'All Natural Puppy Treats', 'Treats4Puppies', 'Treats', 'Puppy', 'Soft and tasty treats made from all-natural ingredients, perfect for puppies.', 3999.00, " + all_natural_puppy_treats + "),\n" +
            "(7, 'Grain-Free Chicken Bites', 'GrainFreeDelight', 'Treats', 'All Ages', 'Grain-free chicken bites packed with protein for healthy dogs of all sizes.', 4499.00, " + grain_free_chicken_bites + "),\n" +
            "(8, 'Beef & Veggie Stew', 'HeartilyBeef', 'Canned Food', 'All Ages', 'A hearty stew made with real beef and vegetables for a nutritious meal.', 8399.00, " + beef_and_veggie_stew + "),\n" +
            "(9, 'Omega-3 Fish Oil Supplement', 'OmegaHealth', 'Supplement', 'All Ages', 'Omega-3 fish oil supplement to promote a healthy coat and skin.', 5999.00, " + omega_3_fish_oil_supplement + "),\n" +
            "(10, 'Probiotic Digestive Health', 'DigestWell', 'Supplement', 'All Ages', 'Probiotic supplement for improving digestive health and immune support.', 6599.00, " + probiotic_digestive_health + ");\n";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserSql);
        db.execSQL(createProductSql);
        db.execSQL(createCartSql);
        db.execSQL(createCartItemSql);
        db.execSQL(createOrderSql);
        db.execSQL(createOrderItemSql);
        db.execSQL(createReviewSql);
        db.execSQL(createEducationalContentSql);
        db.execSQL(createPaymentMethodSql);


        // Insert sample data
        db.execSQL(userSeeds);
        db.execSQL(productSeeds);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

}
