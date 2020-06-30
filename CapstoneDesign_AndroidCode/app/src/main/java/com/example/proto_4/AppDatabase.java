package com.example.proto_4;

        import androidx.annotation.NonNull;
        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;
        import androidx.room.migration.Migration;
        import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Client.class/*, Scrap.class*/}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract ClientDao clientDao();

//    public abstract ScrapDao scrapDao();

//    Room.databaseBuilder(getApplicationContext(), MyDb.class, "database-name").fallbackToDestructiveMigration().build();

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };
//
//    Database database = Room.databaseBuilder(context, Database.class, appDatabase)
//            .fallbackToDestructiveMigration()
//            .build();
}
