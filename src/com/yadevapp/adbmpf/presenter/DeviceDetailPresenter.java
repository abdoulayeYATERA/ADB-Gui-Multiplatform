package com.yadevapp.adbmpf.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yadevapp.adbmpf.model.AndroidApp;
import com.yadevapp.adbmpf.model.AndroidDevice;
import com.yadevapp.adbmpf.model.command.Command;
import com.yadevapp.adbmpf.model.command.CommandManager;
import com.yadevapp.adbmpf.model.command.CommandResponse;
import com.yadevapp.adbmpf.model.command.CommandManager.CommandManagerCallBack;
import com.yadevapp.adbmpf.view.DeviceDetailView;
import com.yadevapp.adbmpf.view.MainView;

public class DeviceDetailPresenter {

	private DeviceDetailView mView;
	private List<AndroidDevice> mAndroidDeviceList;
	private List<AndroidApp> mAppList;
	private CommandManager mCommandManager;

	
	public DeviceDetailPresenter(DeviceDetailView view, List<AndroidDevice> androidDeviceList) {
		mView = view;
		mAndroidDeviceList = androidDeviceList;
		mCommandManager = new CommandManager();
		mAppList = new ArrayList<AndroidApp>();
		
		for (AndroidDevice androidDevice : mAndroidDeviceList) {
			List<AndroidApp> androidAppList = androidDevice.getmAppsSet();
			
			for (AndroidApp app : androidAppList) {
				if (contains(mAppList, app.getmPackageName()) == false) {
					mAppList.add(app);
				}
			}
		}
		//order by packagename
		Collections.sort(mAppList);

	}
	
	public void onButtonClicked(ActionEvent e, int[] selectedDeviceRow) {
		String actionCommand = e.getActionCommand();
		List<AndroidApp> selecteAppList = new ArrayList<AndroidApp>();
		
		for (int i = 0; i < selectedDeviceRow.length; i++) {
			selecteAppList.add(mAppList.get(selectedDeviceRow[i]));
		}
		
		if (actionCommand.equals(DeviceDetailView.UNINSTALL_APP_BUTTON_COMMAND)) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					mView.showProgressDialog("Uninstalling app(s)...");
				}
			});
			t.start();
			uninstallApp(mAndroidDeviceList, selecteAppList);
			openDeviceListJFrame();
		} else if (actionCommand.equals(DeviceDetailView.BACK_TO_DEVICE_LIST_COMMAND)) {
			openDeviceListJFrame();
		}
		
		mView.hideProgressDialog();
	}
	
	public void onSelectedDeviceNumberChanged(int selectedDeviceNumber) {
		mView.updateButtonsState(selectedDeviceNumber);
	}
	
	public boolean contains(List<AndroidApp> androidApp, String packageName) {
		boolean contains = false;
		
		for (AndroidApp app : androidApp) {
			if (app.getmPackageName().equals(packageName)) {
			      contains = true;
			      break;
			}
		}
		return contains;
	}
	
	public void uninstallApp(List<AndroidDevice> androidDeviceList, List<AndroidApp> mAppList) {
		for (AndroidApp appToUninstall : mAppList) {
			
			Command uninstallAppCommand = new Command(); 
			StringBuilder uninstallAppCommandStringBuilder = new StringBuilder();
			
			for (AndroidDevice device : androidDeviceList) {
				if (device.contains(appToUninstall.getmPackageName())) {
					uninstallAppCommandStringBuilder.append(
							"adb -s " + device.getmId() + " uninstall " + appToUninstall.getmPackageName() + " & "
							);
				}
			}
			uninstallAppCommandStringBuilder.delete(
					uninstallAppCommandStringBuilder.length() - 2,
					uninstallAppCommandStringBuilder.length()
					);
			uninstallAppCommand.setmCommandString(uninstallAppCommandStringBuilder.toString());
			
			mCommandManager.addCommandToQueue(uninstallAppCommand);
			mCommandManager.executeQueue(new CommandManagerCallBack() {
				@Override
				public void onCommandStart(Command command) {
					System.out.println("onCommandStart " + command.getmCommandString());
					
				}
				
				@Override
				public void onCommandExecuted(CommandResponse commandResponse) {
					System.out.println("onCommandExecuted");
					System.out.println("response = " + commandResponse.getmResponse());
					
				}
				
				@Override
				public void onCommandError(CommandResponse commandResponse) {
					System.out.println("onCommandError");
					System.out.println("response = " + commandResponse.getmResponse());
					
				}
			});
			
		}
	}
	
	public void openDeviceListJFrame() {
		//you can't see me!
				mView.setVisible(false); 
				//Destroy the JFrame object
				mView.dispose(); 
		MainView mainView = new MainView();
	}
	
	//gettters
	
	public List<AndroidApp> getmAppList() {
		return mAppList;
	}
	
	public List<AndroidDevice> getmAndroidDeviceList() {
		return mAndroidDeviceList;
	}
}
