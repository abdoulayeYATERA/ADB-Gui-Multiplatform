package com.yadevapp.adbmpf.model.command;

public class CommandResponse {
	private final String TAG = getClass().getSimpleName();
	private Command mCommand;
	private String mResponse;
	private boolean mIsError;
	private Exception mException;


	public String getmResponse() {
		return mResponse;
	}

	public Command getmCommand() {
		return mCommand;
	}
	public void setmCommand(Command mCommand) {
		this.mCommand = mCommand;
	}

	public boolean getmIsError() {
		return mIsError;
	}

	public void setmIsError(boolean mIsError) {
		this.mIsError = mIsError;
	}

	public Exception getmException() {
		return mException;
	}

	public void setmException(Exception mException) {
		this.mException = mException;
	}

	public void setmResponse(String mResponse) {
		this.mResponse = mResponse;
	}

}
