package com.example.fragmentTest.android.DB;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * database for GTD data
 * 
 * @author zhuxiaolin
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	private static int DB_VISION = 1;
	private SQLiteDatabase db;
	//
	private static String DATABASE_NAME = "smsgtd.db";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VISION);
		init(context);
	}

	private void init(Context context) {
		try {
			db = getWritableDatabase();
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	/**
	 * openConnect
	 * 
	 * @return
	 */
	public SQLiteDatabase openConnection() {
		try {
			if (!db.isOpen()) {
				db = getWritableDatabase();
			}
		} catch (Exception e) {
		}
		return db;
	}

	/**
	 * close connection
	 */
	public void closeConnection() {
		try {
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * create Table
	 * 
	 * @param createTableSql
	 * @return
	 */
	public boolean createTable(String createTableSql) {
		try {
			openConnection();
			db.execSQL(createTableSql);
			Log.d("MySqlHelper", "execSQL createTable " + createTableSql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * save data to DB (insert)
	 * 
	 * @param tableName
	 * @param values
	 * @return
	 */
	public long save(String tableName, ContentValues values) {
		try {
			openConnection();
			long id = db.insert(tableName, null, values);
			Log.d("MySqlHelper", this + " save rowId " + id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			closeConnection();
		}
	}

	/**
	 * delete data from db
	 * 
	 * @param table  table
	 * @param whereClause  query String
	 * @param whereArgs
	 * @return
	 */
	public boolean delete(String table, String whereClause, String[] whereArgs) {
		try {
			openConnection();
			db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * Update data
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public boolean update(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		try {
			openConnection();
			db.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * find data from DB
	 * 
	 * @param findSql
	 * @param obj
	 * @return
	 */
	public Cursor find(String findSql, String obj[]) {
		try {
			openConnection();
			// Log.d("MySqlHelper", this + " find() " + findSql + " " + obj);
			Cursor cursor = db.rawQuery(findSql, obj);
			cursor.getCount();
			return cursor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	/**
	 * is table exits , if not then create it
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isTableExits(String tableName) {
		try {
			openConnection();
			String str = "select count(*) xcount from " + tableName;
			db.rawQuery(str, null);
		} catch (Exception e) {
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	// -------Override-------
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

//	/**
//	 *
//	 *
//	 * @author apple
//	 *
//	 */
//	public interface SqlInterface {
//		/**
//		 *
//		 *
//		 * @param m
//		 * @return
//		 */
//		public long addData(ObjectMap<String, Object> om);
//
//		/**
//		 *
//		 *
//		 * @param om
//		 * @return
//		 */
//		public List<ObjectMap<String, Object>> retrieveData(
//				ObjectMap<String, Object> om);
//
//		/**
//		 *
//		 *
//		 * @param om
//		 * @return
//		 */
//		public boolean updateData(ObjectMap<String, Object> om);
//
//		/**
//		 *
//		 *
//		 * @param om
//		 * @return
//		 */
//		public boolean delData(ObjectMap<String, Object> om);
//	}
}
