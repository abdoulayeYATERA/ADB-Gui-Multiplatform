package model.tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.AndroidApp;
import model.AndroidDevice;

public class DeviceDetailModel extends AbstractTableModel {
	private String[] mColumnNameArray = { "count", "package name", "version name", "version code", "on device number"};
	private List<AndroidDevice> mAndroidDeviceList;
	private List<AndroidApp> mAppList;

	public  DeviceDetailModel(List<AndroidDevice> androidDeviceList, List<AndroidApp> appList) {
		mAndroidDeviceList = androidDeviceList;
		mAppList = appList;
		}

	@Override
	public int getColumnCount() {
		return mColumnNameArray.length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		//the table only contains String
		return String.class;
	}

	@Override
	public String getColumnName(int column) {
		return mColumnNameArray[column];
	}

	@Override
	public int getRowCount() {
		return mAppList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//get the android device on this row
		AndroidApp app = mAppList.get(rowIndex);
		String value = null;

		switch (columnIndex) {
		case 0:
			value = String.valueOf(rowIndex + 1);
			break;
		case 1:
			value = app.getmPackageName();
			break;
		case 2:
			value = app.getmVersionName();
			break;
		case 3:
			value = app.getmVersionCode();
			break;
		case 4:
			int installOnDeviceNumber = 0;
			for (AndroidDevice androidDevice : mAndroidDeviceList) {
				if (androidDevice.contains(app.getmPackageName())) {
					installOnDeviceNumber++;
				}
			}
			
			if (installOnDeviceNumber == mAndroidDeviceList.size()) {
				value = "on all selected devices";
			} else {
				value = "on " + installOnDeviceNumber + " selected devices";
			}
			
			break;
		default:
			break;
		}
		return value;
	}

	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		//nothing is editable in device table
		return false;
	}



	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

	}
}
