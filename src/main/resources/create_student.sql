CREATE TABLE "student" (
                           "id"	INTEGER NOT NULL UNIQUE,
                           "surname"	TEXT NOT NULL,
                           "name"	TEXT NOT NULL,
                           "lastname"	TEXT NOT NULL,
                           "phone"	TEXT,
                           "tg"	TEXT,
                           "email"	TEXT,
                           "git"	TEXT,
                           PRIMARY KEY("id" AUTOINCREMENT)
)