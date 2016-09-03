package com.yadevapp.adbmpf.model;

public class AndroidApp implements Comparable<AndroidApp> {
	private final String TAG = getClass().getSimpleName();

	private String mName;
	private String mVersionName;
	private String mVersionCode;
	private String mPackageName;
	private String mCodePath;
	private String mResourcePath;
	private String mTargetSdk;
	private String mAppType;


	// setters 
	public void setmResourcePath(String mResourcePath) {
		this.mResourcePath = mResourcePath;
	}

	public void setmTargetSdk(String mTargetSdk) {
		this.mTargetSdk = mTargetSdk;
	}

	public void setmVersionCode(String mVersionCode) {
		this.mVersionCode = mVersionCode;
	}
	public void setmCodePath(String mCodePath) {
		this.mCodePath = mCodePath;
	}

	public void setmAppType(String mAppType) {
		this.mAppType = mAppType;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public void setmVersionName(String mVersionName) {
		this.mVersionName = mVersionName;
	}

	public void setmPackageName(String mPackageName) {
		this.mPackageName = mPackageName;
	}

	//getters

	public String getmResourcePath() {
		return mResourcePath;
	}
	
	public String getmTargetSdk() {
		return mTargetSdk;
	}
	
	public String getmVersionCode() {
		return mVersionCode;
	}
	public String getmCodePath() {
		return mCodePath;
	}

	public String getmAppType() {
		return mAppType;
	}

	public String getmName() {
		return mName;
	}

	public String getmVersionName() {
		return mVersionName;
	}


	public String getmPackageName() {
		return mPackageName;
	}

	@Override
	public int compareTo(AndroidApp androidApp) {
		return mPackageName.compareTo(androidApp.getmPackageName());
	}
}
