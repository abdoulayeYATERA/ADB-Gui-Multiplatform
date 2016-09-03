package com.yadevapp.adbmpf.model.command;

import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CommandManager {
	private final String TAG = getClass().getSimpleName();
	private ArrayDeque<Command> mCommandArrayDeque = new ArrayDeque<Command>();
	private Logger mLogger;

	public CommandManager() {
		mLogger = Logger.getLogger(TAG);
		mLogger.log(Level.INFO, getClass().getSimpleName());
	}
	
	public void addCommandToQueue(Command command) {
		mCommandArrayDeque.add(command);
	}
	
	public void executeQueue(CommandManagerCallBack commandManagerCallBack) {
		Command command;
		
		while((command = mCommandArrayDeque.poll()) != null) {
			
			CommandExecutorThread commandExecutorThread = new CommandExecutorThread(command);		
			commandManagerCallBack.onCommandStart(command);
			commandExecutorThread.start();
			try {
				commandExecutorThread.join();
				CommandResponse commandResponse = commandExecutorThread.getmCommandResponse();
				if (commandResponse.getmIsError() == false) {
					commandManagerCallBack.onCommandExecuted(commandResponse);	
				} else {
					commandManagerCallBack.onCommandError(commandResponse);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mLogger.log(Level.INFO, e.getStackTrace().toString());
			}
			
		}
	}


	public interface CommandManagerCallBack {
		public void onCommandStart(Command command);
		public void onCommandError(CommandResponse commandResponse);
		public void onCommandExecuted(CommandResponse commandResponse);
	}

}
