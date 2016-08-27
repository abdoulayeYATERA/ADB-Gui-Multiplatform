package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AndroidDevice {
	private final String TAG = getClass().getSimpleName();

	private String mVersion;
	private String mId;
	private String mSerialNumber;
	private String mAdbState;
	private String mBrand;
	private String mModel;
	private String mName;
	private String mCpuType;

	private List<AndroidApp> mAppsSet = new ArrayList<AndroidApp>();

	//getters
	public String getmCpuType() {
		return mCpuType;
	}

	public String getmName() {
		return mName;
	}

	public String getmBrand() {
		return mBrand;
	}

	public String getmModel() {
		return mModel;
	}

	public String getmVersion() {
		return mVersion;
	}
	public String getmId() {
		return mId;
	}
	public String getmSerialNumber() {
		return mSerialNumber;
	}
	public List<AndroidApp> getmAppsSet() {
		return mAppsSet;
	}

	public String getmAdbState() {
		return mAdbState;
	}

	//setters

	public void setmCpuType(String mCpuType) {
		this.mCpuType = mCpuType;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}
	public void setmBrand(String mBrand) {
		this.mBrand = mBrand;
	}

	public void setmModel(String mModel) {
		this.mModel = mModel;
	}

	public void setmVersion(String mVersion) {
		this.mVersion = mVersion;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public void setmSerialNumber(String mSerialNumber) {
		this.mSerialNumber = mSerialNumber;
	}
	public void setmAppsSet(List<AndroidApp> mAppsSet) {
		this.mAppsSet = mAppsSet;
	}
	public void setmAdbState(String mAdbState) {
		this.mAdbState = mAdbState;
	}

	//adders

	public void addApp(AndroidApp androidApp) {
		mAppsSet.add(androidApp);
	}

	public boolean contains(String packageName) {
		boolean contains = false;

		for (AndroidApp app : mAppsSet) {
			if (app.getmPackageName().equals(packageName)) {
				contains = true;
				break;
			}
		}
		return contains;
	}
}
