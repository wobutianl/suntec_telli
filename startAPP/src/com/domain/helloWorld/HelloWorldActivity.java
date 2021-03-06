package com.domain.helloWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class HelloWorldActivity extends Activity {

	ArrayList<String> list;
	private List<ResolveInfo> mApps;
	private static HashMap hashmap = new HashMap();
	private ResolveInfo info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btn = (Button) findViewById(R.id.button1);
		ListView listView = (ListView) findViewById(R.id.listView1);
		final EditText eText = (EditText) findViewById(R.id.editText1);
		list = new ArrayList<String>();
		getList();
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
				HelloWorldActivity.this, android.R.layout.simple_list_item_1,
				list);
		listView.setAdapter(mAdapter);

		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String packageStr = eText.getText().toString();
				Log.d("package", packageStr);
				for (Iterator<String> i = hashmap.keySet().iterator(); i
						.hasNext();) {
					String key = i.next();
					String value = hashmap.get(key).toString();
					System.out.println(key);
					System.out.println(value);
					// sum += value;
				}
				String PacStr = hashmap.get(packageStr).toString();
				Log.d("package name", PacStr);
				if (packageStr != null) {

					startOtherApp(PacStr);
				}
			}
		});
	}

	private void getList() {
		list.clear();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
		for (int i = 0; i < mApps.size(); i++) {
			info = mApps.get(i);
			String appLabel = info.loadLabel(getPackageManager()).toString();
			String packagename = info.activityInfo.packageName;
			String appname = info.activityInfo.name;
			hashmap.put(appLabel.toLowerCase(), packagename);
			list.add("appLabel:" + appLabel + " ||  packagename:" + packagename
					+ " ||  appname:" + appname);
		}
	}

	private void startOtherApp(String AppPackageStr) {
		Log.d("app", "begin app");
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(
				AppPackageStr);
		startActivity(LaunchIntent);
	}
}
