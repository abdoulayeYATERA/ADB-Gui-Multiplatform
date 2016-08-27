package model;

public class AndroidApk {
	private final String TAG = getClass().getName();
	private String mPackageName;
	private String mApkPath;
	
	public String getmPackageName() {
		return mPackageName;
	}
	public String getmApkPath() {
		return mApkPath;
	}
	public void setmPackageName(String mPackageName) {
		this.mPackageName = mPackageName;
	}
	public void setmApkPath(String mApkPath) {
		this.mApkPath = mApkPath;
	}
	
	
}
