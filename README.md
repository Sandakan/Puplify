# Puplify

**Puplify** is a mobile application designed to help dog owners find and purchase high-quality dog
food and nutrition-related products. The app allows users to browse products, read detailed
descriptions, make purchases, manage their orders, and access educational content on dog nutrition.

## Features

- **User Authentication and Profiles**:
    - Register and log in using an email account.
    - Manage personal information, address, and payment methods.

- **Product Catalog**:
    - Display a list of dog food and nutrition-related products.
    - Filter and sort products based on criteria such as brand, type, age group, etc.
    - View detailed product descriptions, prices, and customer reviews.

- **Shopping Cart**:
    - Add products to the cart.
    - View cart contents, including product details, quantities, and total price.
    - Adjust quantities, remove items, and proceed to checkout.

- **Order Management**:
    - Place orders from the cart.
    - View order history and details.

- **Educational Content**:
    - Access articles, videos, and guides about dog nutrition.
    - Learn about different dog breeds, life stages, dietary requirements, and health tips.

## Tech Stack

- **Mobile Platform**: Android
- **Programming Language**: Java
- **Database**: SQLite
- **Data Storage**: SharedPreferences for session data
- **Development Tools**: Android Studio

## Database Schema

The following is the database structure used in the application:

```sql
CREATE TABLE IF NOT EXISTS "user" (
    "id" INTEGER NOT NULL UNIQUE,
    "first_name" TEXT NOT NULL,
    "last_name" TEXT NOT NULL,
    "email" TEXT NOT NULL UNIQUE,
    "password" TEXT NOT NULL,
    "address" TEXT,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY("id")  
);

CREATE TABLE IF NOT EXISTS "product" (
    "id" INTEGER NOT NULL UNIQUE,
    "name" TEXT NOT NULL,
    "brand" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "age_group" TEXT NOT NULL,
    "description" TEXT NOT NULL,
    "price" REAL NOT NULL,
    "image_url" TEXT NOT NULL,
    PRIMARY KEY("id")  
);

CREATE TABLE IF NOT EXISTS "payment_method" (
    "id" INTEGER NOT NULL UNIQUE,
    "card_number" TEXT NOT NULL,
    "card_holder_name" TEXT NOT NULL,
    "expiration_date" TEXT NOT NULL,
    "cvv" TEXT NOT NULL,
    "user_id" INTEGER NOT NULL,
    PRIMARY KEY("id"),
    FOREIGN KEY ("user_id") REFERENCES "user"("id")
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS "cart" (
    "id" INTEGER NOT NULL UNIQUE,
    "status" TEXT NOT NULL DEFAULT 'PENDING',
    "user_id" INTEGER NOT NULL,
    PRIMARY KEY("id"),
    FOREIGN KEY ("user_id") REFERENCES "user"("id")
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS "cart_item" (
    "id" INTEGER NOT NULL UNIQUE,
    "cart_id" INTEGER NOT NULL,
    "product_id" INTEGER NOT NULL,
    "quantity" INTEGER NOT NULL,
    PRIMARY KEY("id"),
    FOREIGN KEY ("cart_id") REFERENCES "cart"("id")
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS "order" (
    "id" INTEGER NOT NULL UNIQUE,
    "total_price" REAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    PRIMARY KEY("id"),
    FOREIGN KEY ("user_id") REFERENCES "user"("id")
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS "order_item" (
    "id" INTEGER NOT NULL UNIQUE,
    "quantity" INTEGER NOT NULL,
    "product_id" INTEGER NOT NULL,
    "order_id" INTEGER NOT NULL,
    PRIMARY KEY("id"),
    FOREIGN KEY ("order_id") REFERENCES "order"("id")
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS "review" (
    "id" INTEGER NOT NULL UNIQUE,
    "product_id" INTEGER NOT NULL,
    "user_id" INTEGER NOT NULL,
    "rating" INTEGER NOT NULL CHECK(rating BETWEEN 1 AND 5),
    "comment" TEXT NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY("id"),
    FOREIGN KEY ("product_id") REFERENCES "product"("id")
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS "educational_content" (
    "id" INTEGER NOT NULL UNIQUE,
    "title" TEXT NOT NULL,
    "description" TEXT NOT NULL,
    "url" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    PRIMARY KEY("id")  
);
```
