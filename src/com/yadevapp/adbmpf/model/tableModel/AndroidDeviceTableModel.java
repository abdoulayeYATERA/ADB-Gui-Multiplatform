package com.yadevapp.adbmpf.model.tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.yadevapp.adbmpf.model.AndroidDevice;

/**
 * 
 * @author salou
 * model for the android device table
 */
public class AndroidDeviceTableModel  extends AbstractTableModel {

	 private String[] mColumnNameArray = { "Count", "Id", "Model", "Name", "Brand", "SDK", "Cpu", "Serial", "Apps Number"};
	 private List<AndroidDevice> mAndroidDeviceList = new ArrayList<AndroidDevice>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	//constructor
    public AndroidDeviceTableModel(List<AndroidDevice> androidDeviceList) {
		mAndroidDeviceList = androidDeviceList;
	}
    
    public AndroidDeviceTableModel() {
    	
    }
    
    public void setmAndroidDeviceList(List<AndroidDevice> mAndroidDeviceList) {
		this.mAndroidDeviceList = mAndroidDeviceList;
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
		return mAndroidDeviceList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//get the android device on this row
		AndroidDevice androidDevice = mAndroidDeviceList.get(rowIndex);
		String value = null;
		
		switch (columnIndex) {
		case 0:
			value = String.valueOf(rowIndex + 1);
			break;
		case 1:
			value = androidDevice.getmId();
			break;
		case 2:
			value = androidDevice.getmModel();
			break;
		case 3:
			value = androidDevice.getmName();
			break;
		case 4:
			value = androidDevice.getmBrand();
			break;
		case 5:
			value = androidDevice.getmVersion();
			break;
		case 6:
			value = androidDevice.getmCpuType();
			break;
		case 7:
			value = androidDevice.getmSerialNumber();
			break;
		case 8:
			value = String.valueOf(androidDevice.getmAppsSet().size());
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
