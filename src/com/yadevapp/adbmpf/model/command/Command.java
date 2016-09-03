package com.yadevapp.adbmpf.model.command;

public class Command {
	private final String TAG = getClass().getSimpleName();
	private String mCommandString;
	private int mId;

	
	//getters 
	
	public String getmCommandString() {
		return mCommandString;
	}
	public int getmId() {
		return mId;
	}
	
	
	//setters
	
	public void setmCommandString(String mCommandString) {
		this.mCommandString = mCommandString;
	}
	public void setmId(int mCommandId) {
		this.mId = mCommandId;
	}
	
}
