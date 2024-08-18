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
            "\t\"image_url\" TEXT NOT NULL,\n" +
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
            "\t\"image_url\" TEXT NOT NULL,\n" +
            "\t\"url\" TEXT NOT NULL,\n" +
            "\t\"type\" TEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\t\n" +
            ");";

    private static final String userSeeds = "INSERT INTO \"user\" (\"first_name\", \"last_name\", \"email\", \"password\") VALUES\n" +
            "('John', 'Doe', 'johndoe@gmail.com', '12345'),\n" +
            "('Jane', 'Doe', 'janedoe@gmailcom', '12345');";


    private static final String productSeeds = "INSERT INTO \"product\" (\"id\", \"name\", \"brand\", \"type\", \"age_group\", \"description\", \"price\", \"image_url\") VALUES\n" +
            "(1, 'Kibble Delight', 'BestBites', 'Dry Food', 'Adult', 'A balanced dry kibble made with chicken and wholesome grains for adult dogs.', 8999.00, 'https://images.freshop.com/00070155124630/a64415bb61f195fb139088910c07aac0_large.png'),\n" +
            "(2, 'Puppy Growth Formula', 'PupGrow', 'Dry Food', 'Puppy', 'A specially formulated dry food for growing puppies, enriched with essential nutrients.', 10499.00, 'https://i.pinimg.com/736x/b0/ed/11/b0ed111c43c2a7f47b1e3a91e1e51091.jpg'),\n" +
            "(3, 'Senior Wellness Blend', 'SeniorCare', 'Dry Food', 'Senior', 'A nutrient-rich blend designed to support the health of senior dogs.', 11999.00, 'https://th.bing.com/th/id/OIP.J2fly4bd_erfXaTQvizRwgHaHa?rs=1&pid=ImgDetMain'),\n" +
            "(4, 'Chicken & Rice Canned', 'TenderMeal', 'Canned Food', 'All Ages', 'A delicious canned dog food made with real chicken and rice for easy digestion.', 7499.00, 'https://i5.walmartimages.com/asr/d6a31d68-43cd-49f6-8cde-4903d84d0882_2.fbf1601ef100cfd7b995327315463069.jpeg?odnWidth=612&odnHeight=612&odnBg=ffffff'),\n" +
            "(5, 'Salmon & Sweet Potato', 'OceanicFeast', 'Dry Food', 'Adult', 'A grain-free formula featuring high-quality salmon and sweet potatoes.', 9699.00, 'https://i5.walmartimages.com/asr/6f5c9e8b-48b8-413a-b88d-4b27ba42dd4b.a3f55c6d927ed8d278e3aa8e4f9581b6.jpeg'),\n" +
            "(6, 'All Natural Puppy Treats', 'Treats4Puppies', 'Treats', 'Puppy', 'Soft and tasty treats made from all-natural ingredients, perfect for puppies.', 3999.00, 'https://th.bing.com/th/id/OIP.bB2wgzL6KIrsGSL1MSYzowHaHa?rs=1&pid=ImgDetMain'),\n" +
            "(7, 'Grain-Free Chicken Bites', 'GrainFreeDelight', 'Treats', 'All Ages', 'Grain-free chicken bites packed with protein for healthy dogs of all sizes.', 4499.00,'https://th.bing.com/th/id/R.f26d0941356d5240f4b5dd336a90ca34?rik=vqJDJQE0wQVrjA&pid=ImgRaw&r=0'),\n" +
            "(8, 'Beef & Veggie Stew', 'HeartilyBeef', 'Canned Food', 'All Ages', 'A hearty stew made with real beef and vegetables for a nutritious meal.', 8399.00, 'https://th.bing.com/th/id/OIP.-usUFwCCjlJ3Kkt93m1eLAHaHa?rs=1&pid=ImgDetMain'),\n" +
            "(9, 'Omega-3 Fish Oil Supplement', 'OmegaHealth', 'Supplement', 'All Ages', 'Omega-3 fish oil supplement to promote a healthy coat and skin.', 5999.00, 'https://i5.walmartimages.com/asr/784f9a72-8a23-482e-8a4f-258e1b032ef6.451c9d186a506f36a0ffbcd7509abb05.jpeg'),\n" +
            "(10, 'Probiotic Digestive Health', 'DigestWell', 'Supplement', 'All Ages', 'Probiotic supplement for improving digestive health and immune support.', 6599.00, 'https://th.bing.com/th/id/R.d6ba2d8a3f475f277bd148ed1d17a2cf?rik=mWT64vGdPYjbXw&pid=ImgRaw&r=0');";

    private static final String reviewSeeds = "INSERT INTO review (product_id, user_id, rating, comment, created_at) VALUES\n" +
            "(1, 1, 5, 'My dog absolutely loves this food! High quality and great nutrition.', CURRENT_TIMESTAMP),\n" +
            "(2, 1, 4, 'Great product but a bit pricey. My dog enjoys it.', CURRENT_TIMESTAMP),\n" +
            "(3, 1, 5, 'Excellent quality treats! Perfect for training sessions.', CURRENT_TIMESTAMP),\n" +
            "(4, 1, 3, 'The kibble size is too big for my small dog, but the ingredients are good.', CURRENT_TIMESTAMP),\n" +
            "(5, 1, 4, 'My puppy loves this! It’s very nutritious and keeps her healthy.', CURRENT_TIMESTAMP),\n" +
            "(6, 1, 5, 'Amazing product! My dog’s coat looks shiny and healthy.', CURRENT_TIMESTAMP),\n" +
            "(7, 1, 4, 'Good product, but the packaging could be better.', CURRENT_TIMESTAMP),\n" +
            "(8, 1, 5, 'My dog has never been happier with a food product! Highly recommend.', CURRENT_TIMESTAMP),\n" +
            "(9, 1, 3, 'It’s okay, but my dog prefers other brands.', CURRENT_TIMESTAMP),\n" +
            "(10, 1, 5, 'Perfect for my senior dog. Easy to digest and nutritious.', CURRENT_TIMESTAMP);\n";

    private static final String educationContentSeeds = "INSERT INTO educational_content (title, description, image_url, url, type) VALUES\n" +
            "('Food allergies in dogs — a plan for change', 'Getting to the bottom of food allergies in your dog requires a comprehensive plan for change that includes an elimination diet made from novel protein and carbohydrate sources.', 'https://awmagazine.wpenginepowered.com/wp-content/uploads/shutterstock_1979238968.jpg', 'https://animalwellnessmagazine.com/food-allergies-in-dogs-a-plan-for-change/', 'ARTICLE'),\n" +
            "('Natural Flea and Tick Prevention (That Really Works!)', 'Pet parents have long been torn between choosing potentially dangerous synthetic flea and tick prevention and natural solutions that don’t always work. Earth Animal has created an array of natural products that protect dogs and cats from both the inside and outside against fleas and ticks.', 'https://awmagazine.wpenginepowered.com/wp-content/uploads/shutterstock_681753241-e1710951972688.jpg', 'https://animalwellnessmagazine.com/natural-flea-and-tick-prevention-that-really-works/', 'ARTICLE'),\n" +
            "('Did You Know Your Dog Needs Canine-Specific Probiotics?', 'The right probiotics can have numerous health benefits for dogs. However, human probiotics that are often relabeled for dogs likely don’t survive the digestive tract. PetCultures has the most comprehensive and effective formulas of canine-specific probiotics around, and they’re design to survive the digestive tract and deliver results!', 'https://awmagazine.wpenginepowered.com/wp-content/uploads/Santiago_Gelvez_With_Dog_Cropped-1-e1711988688577.png', 'https://animalwellnessmagazine.com/did-you-know-your-dog-needs-canine-specific-probiotics/', 'ARTICLE');\n";


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
        db.execSQL(reviewSeeds);
        db.execSQL(educationContentSeeds);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

}
