package model.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.ComputerUtil;


public class CommandExecutorThread extends Thread {
	private final String TAG = getClass().getSimpleName();
	private Command mCommand;
	private Logger mLogger;
	private CommandResponse mCommandResponse;

	public CommandExecutorThread(Command command) {
		mLogger = Logger.getLogger(TAG);
		mLogger.log(Level.INFO, "CommandExecutorTread");
		mCommand = command;
		mCommandResponse = new CommandResponse();
		mCommandResponse.setmCommand(command);
	}

	public CommandResponse getmCommandResponse() {
		return mCommandResponse;
	}

	public Command getmCommand() {
		return mCommand;
	}

	public void setmCommandResponse(CommandResponse mCommandResponse) {
		this.mCommandResponse = mCommandResponse;
	}

	public void setmCommand(Command mCommand) {
		this.mCommand = mCommand;
	}

	@Override
	public void run() {
		super.run();
		Process process = null;
		try {
		    if (ComputerUtil.isUnix()) {
				process = Runtime.getRuntime().exec(new String[] {"bash", "-c", mCommand.getmCommandString()});
			} else if (ComputerUtil.isWindows()) {
				process = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", mCommand.getmCommandString()});
			}

			InputStream processInputStream = process.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(processInputStream));
			String line;
			StringBuilder responseStringBuilder = new StringBuilder();

			while ((line = bufferReader.readLine()) != null) {
				responseStringBuilder.append(line);
				responseStringBuilder.append("\n");
			}
			String response = responseStringBuilder.toString();
			mCommandResponse.setmResponse(response);

			mCommandResponse.setmIsError(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print(e.getStackTrace());
			mCommandResponse.setmIsError(true);
			mCommandResponse.setmException(e);
		}
	}
}