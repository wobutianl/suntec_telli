package com.example.fragmentTest.android.DB;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class GtdTable {
	//
	public static final String TABLE_NAME = "GTDTable";
	// ID
	public static final String SMS_ID = "smsID";
	// start time
	public static final String START_TIME = "startTime";
	// end time
	public static final String END_TIME = "endTime";
	// GTD Place
	public static final String PLACE = "place";
	// GTD content
	public static final String CONTENT = "content";
	// GTD status(finish, canceled, delay, todo)
	public static final String GTDSTATUS = "gtdStatus";
	// GTD type (home, work, finish)
	public static final String GTDTYPE = "gtdType";
	//
	public static final String ITEM_WRITTEN_MILLSECECOND = "MyDayRecordTableWrittenTime";

	//
	private final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " ( " + SMS_ID
			+ " integer primary key autoincrement, " + START_TIME
			+ " varchar , " + END_TIME + " varchar , " + PLACE + " varchar , "
			+ CONTENT + " varchar not null , " + GTDSTATUS
			+ " varchar not null , " + GTDTYPE + " varchar not null , "
			+ ITEM_WRITTEN_MILLSECECOND + " bigint not null  ) ";

	// sqlhelper
	DBHelper helper = null;
	private smsDataModule sms ;

    /**
     * create gtd Table
     * @param context
     */
	public GtdTable(Context context) {
		helper = new DBHelper(context);
		//
		if (!helper.isTableExits(TABLE_NAME)) {
			//
			helper.createTable(CREATE_SQL);
		}
	}

    /**
     * insert Data
     * @param om smsDataModule
     * @return
     */
	public long addData(smsDataModule om) {
		try {
			ContentValues values = new ContentValues();
			Log.d("SQL_ADD", GtdTable.TABLE_NAME + " ~~ " + om.getContent());
			values.put(GtdTable.START_TIME,	om.getStartTime());
			values.put(GtdTable.END_TIME, om.getEndTime());
			values.put(GtdTable.PLACE, om.getPlace());
			values.put(GtdTable.CONTENT, om.getContent());
			values.put(GtdTable.GTDSTATUS, om.getGtdStatus());
			values.put(GtdTable.GTDTYPE, om.getGtdType());
			values.put(GtdTable.ITEM_WRITTEN_MILLSECECOND, om.getWrite_time());
			return helper.save(GtdTable.TABLE_NAME, values);
		} catch (Exception e) {
			Log.d("addData Exception", e.toString());
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * find data from db
	 * 
	 * @param findSql
	 * @param obj
	 * @return
	 */
	public Cursor find(String findSql, String obj[]) {
		Cursor cursor = helper.find(findSql, obj);
		return cursor;
	}
	/**
	 * data follow by recordTime
	 */
	public List<smsDataModule> retrieveData(smsDataModule om) {
		StringBuilder bd = new StringBuilder();
		bd.append("select * from ").append(GtdTable.TABLE_NAME)
				.append(" order by ")
				.append(GtdTable.ITEM_WRITTEN_MILLSECECOND).append(" desc");
		Log.d("SQL_RETRIEVE", bd.toString());

		Cursor cursor = helper.find(bd.toString(), null);
		List<smsDataModule> list = new ArrayList<smsDataModule>(cursor.getCount());

		while (cursor.moveToNext()) {
            smsDataModule _om = new smsDataModule();
			_om.setStartTime(cursor.getString(cursor.getColumnIndex(GtdTable.START_TIME)));
			_om.setEndTime(cursor.getString(cursor.getColumnIndex(GtdTable.END_TIME)));
			_om.setPlace(cursor.getString(cursor.getColumnIndex(GtdTable.PLACE)));
			_om.setContent(cursor.getString(cursor.getColumnIndex(GtdTable.CONTENT)));
			_om.setGtdStatus(cursor.getString(cursor.getColumnIndex(GtdTable.GTDSTATUS)));
			_om.setGtdType(cursor.getString(cursor.getColumnIndex(GtdTable.GTDTYPE)));
			_om.setWrite_time(cursor.getLong(cursor.getColumnIndex(GtdTable.ITEM_WRITTEN_MILLSECECOND)));
			Log.d("SQL_RETRIEVE", _om.getContent());
			list.add(_om);
		}
		return list;
	}

	public boolean delData(smsDataModule om) {
		StringBuilder bd = new StringBuilder();
		bd.append(GtdTable.SMS_ID).append(" = ?");
		return helper.delete(GtdTable.TABLE_NAME, bd.toString(),
				new String[] { String.valueOf(om.getSmsID()) });
	}
}
