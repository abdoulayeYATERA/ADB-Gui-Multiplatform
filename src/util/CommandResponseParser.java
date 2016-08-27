package util;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AndroidApp;
import model.AndroidDevice;

public class CommandResponseParser {
	private final static String TAG = CommandResponseParser.class.getClass().getSimpleName();


	public static List<String> getConnectedDeviceIdArrayList(String adbDevicesResponse) {
		ArrayList<String> devicesIdArrayList = new ArrayList<>();
		Scanner scanner = new Scanner(adbDevicesResponse);
		scanner.nextLine();
		String line;

		while ((line = scanner.nextLine()) != null && line.isEmpty() == false) {
			Scanner lineScanner = new Scanner(line);
			
			try {
				String deviceID = lineScanner.next();
				String deviceStatus = lineScanner.next();
				
				if (deviceStatus.equals("device")) {
					devicesIdArrayList.add(deviceID);
				}
			} catch (Exception e) {

			}
		}
		scanner.close();
		return devicesIdArrayList;
	}

	public static AndroidDevice parseGetPropResponse(String getPropResponse) {
		AndroidDevice androidDevice = new AndroidDevice();
		Scanner scanner = new Scanner(getPropResponse);
		String line;

		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			//System.out.println(line);
			String deviceProp = line.substring(line.lastIndexOf("[") + 1 , line.length() - 1);
			//System.out.println(deviceProp);
			if (line.contains("ro.product.name")) {
				androidDevice.setmName(deviceProp);
			} else if (line.contains("ro.product.model")) {
				androidDevice.setmModel(deviceProp);
			} else if (line.contains("ro.product.brand")) {
				androidDevice.setmBrand(deviceProp);
			} else if (line.contains("ro.build.version.release")) {
				androidDevice.setmVersion(deviceProp);
			} else if (line.contains("ro.boot.serialno")) {
				androidDevice.setmSerialNumber(deviceProp);
			} else if (line.contains("ro.product.cpu.abi")) {
				androidDevice.setmCpuType(deviceProp);
			} 
		}
		return androidDevice;
	}

	public static List<String> parsePmListPackagesResponse(String pmListPackagesResponse) {
		List<String> installedAppPackageList = new ArrayList<>();
		Scanner scanner = new Scanner(pmListPackagesResponse);
		String line;

		while(scanner.hasNextLine()) {
			line = scanner.nextLine();

			if (line.startsWith("package:/data/app/")) { 
				String packageName = line.substring(line.indexOf("=") + 1);
				installedAppPackageList.add(packageName);
			}
		}
		return installedAppPackageList;
	}

	public static AndroidApp parseDumpsysPackageResponse(String dumpsysPackageResponse) {
		AndroidApp androidApp = new AndroidApp();
		Scanner scanner = new Scanner(dumpsysPackageResponse);
		String line;

		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("codePath")) {
				String codePath = line.replace("codePath=", "");
				androidApp.setmCodePath(codePath);
			} else if (line.contains("resourcePath")) {
				String resourcePath = line.replace("resourcePath=", "");
				androidApp.setmResourcePath(resourcePath);
			} else if (line.contains("versionName=")) {
				String versionName = line.replace("versionName=", "");
				androidApp.setmVersionName(versionName);
			} else if (line.contains("versionCode=")) {
				Scanner lineScanner = new Scanner(line);
				String versionCode = lineScanner.next().replace("versionCode=", "");
				androidApp.setmVersionCode(versionCode);
				/*String targetSdk = lineScanner.next().replace("targetSdk=", "");
				androidApp.setmTargetSdk(targetSdk);*/
			} else if (line.contains("Package [")) {
				String packageName = line.substring(
						line.indexOf("[") + 1,
						line.indexOf("]")
						);
			androidApp.setmPackageName(packageName);
			}
		}
		return androidApp;
	}

	//
}
