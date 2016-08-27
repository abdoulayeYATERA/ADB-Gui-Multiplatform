package view;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import presenter.MainViewPresenter;
import model.AndroidDevice;

public class MainView  extends JFrame implements ActionListener {

	public final static String DETAILS_BUTTON_COMMAND = "details";
	public final static String CREATE_BACKUP_BUTTON_COMMAND = "createBackup";
	public final static String RESTORE_BACKUP_BUTTON_COMMAND = "restoreBackup";
	public final static String INSTALL_APPS_BUTTON_COMMAND = "installApps";
	public final static String UNINSTALL_APPS_BUTTON_COMMAND = "uninstallApps";
	public final static String UPDATE_DEVICE_LIST_BUTTON_COMMAND = "updateDeviceList";
	public final static String SEND_FILE_BUTTON_COMMAND = "sendFile";

	private final static long serialVersionUID = 1L;

	private final String TAG = getClass().getSimpleName();

	private JPanel mJPanel;
	private AndroidDeviceTableModel mAndroidDeviceTableModel;
	private JTable mAndroidDeviceTable;
	private MainViewPresenter mMainViewPresenter;
	private JPanel mButtonJPanel;
	private JButton mDetailsJButton;
	private JButton mCreateBackupJButton;
	private JButton mRestoreBackupJButton;
	private JButton mInstallAppsJButton;
	private JButton mUninstallAppsJButton;
	private JButton mUpdateDeviceList;
	private JButton mSendFileButton;
	private JDialog mProgressDialog;
	private JLabel mProgressDialogJLablel;
	private JFileChooser mJFileChooser;

	public static void main(String[] args) {
		new MainView();
	}

	public  MainView() {
		super("Conneted Devices");
		mAndroidDeviceTableModel = new AndroidDeviceTableModel();
		mJFileChooser = new JFileChooser();
		mDetailsJButton = new JButton("Details");
		mCreateBackupJButton = new JButton("Create backup");
		mRestoreBackupJButton = new JButton("Restore backup");
		mInstallAppsJButton = new JButton("Install app(s)");
		mUninstallAppsJButton = new JButton("Uninstall app(s)");
		mUpdateDeviceList = new JButton("Update devices list");
		mSendFileButton = new JButton("Push file(s)");
		mProgressDialogJLablel = new JLabel();
		mProgressDialog = new JDialog(this, "Progress dialog", Dialog.ModalityType.APPLICATION_MODAL);
		mProgressDialog.setBounds(350, 350, 200, 200);
		mProgressDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mProgressDialog.setUndecorated(true);
		mProgressDialog.add(mProgressDialogJLablel);

		updateButtonsState(0);

		mDetailsJButton.setActionCommand(DETAILS_BUTTON_COMMAND);
		mCreateBackupJButton.setActionCommand(CREATE_BACKUP_BUTTON_COMMAND);
		mRestoreBackupJButton.setActionCommand(RESTORE_BACKUP_BUTTON_COMMAND);
		mInstallAppsJButton.setActionCommand(INSTALL_APPS_BUTTON_COMMAND);
		mUninstallAppsJButton.setActionCommand(UNINSTALL_APPS_BUTTON_COMMAND);
		mUpdateDeviceList.setActionCommand(UPDATE_DEVICE_LIST_BUTTON_COMMAND);
		mSendFileButton.setActionCommand(SEND_FILE_BUTTON_COMMAND);

		mDetailsJButton.addActionListener(this);
		mCreateBackupJButton.addActionListener(this);
		mRestoreBackupJButton.addActionListener(this);
		mInstallAppsJButton.addActionListener(this);
		mUninstallAppsJButton.addActionListener(this);
		mSendFileButton.addActionListener(this);
		mUpdateDeviceList.addActionListener(this);

		mButtonJPanel = new JPanel();
		mButtonJPanel.setLayout(new BoxLayout(mButtonJPanel, BoxLayout.X_AXIS));
		mButtonJPanel.add(mDetailsJButton);
		mButtonJPanel.add(mCreateBackupJButton);
		mButtonJPanel.add(mRestoreBackupJButton);
		mButtonJPanel.add(mInstallAppsJButton);
		mButtonJPanel.add(mUninstallAppsJButton);
		mButtonJPanel.add(mSendFileButton);
		mButtonJPanel.add(mUpdateDeviceList);

		mAndroidDeviceTable = new JTable(mAndroidDeviceTableModel);
		mAndroidDeviceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] selectedRows = mAndroidDeviceTable.getSelectedRows();
				mMainViewPresenter.onSelectedDeviceNumberChanged(selectedRows.length);
			}
		});
		JScrollPane jScrollPane = new JScrollPane(mAndroidDeviceTable);

		mJPanel = new JPanel();
		mJPanel.setLayout(new BoxLayout(mJPanel, BoxLayout.Y_AXIS));
		mJPanel.add(mButtonJPanel);
		mJPanel.add(jScrollPane);

		add(mJPanel);
		setSize(1000, 800);
		setResizable(true);
		setVisible(true);
		mMainViewPresenter = new MainViewPresenter(this);
	}

	public void onAndroidDeviceListUpdated(List<AndroidDevice> androidDeviceList) {
		System.out.println("onAndroidDeviceListUpdated");
		mAndroidDeviceTableModel.setmAndroidDeviceList(androidDeviceList);
		mAndroidDeviceTableModel.fireTableDataChanged();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread(new Runnable() {
		      public void run() {
		    	  mMainViewPresenter.onButtonClicked(e, mAndroidDeviceTable.getSelectedRows());
		      }
		    });
		    t.start();
	}

	public void updateButtonsState(int androidDeviceSeletedNumber) {
		switch (androidDeviceSeletedNumber) {
		case 0:
			mUpdateDeviceList.setEnabled(true);
			mDetailsJButton.setEnabled(false);
			mCreateBackupJButton.setEnabled(false);
			mRestoreBackupJButton.setEnabled(false);
			mInstallAppsJButton.setEnabled(false);
			mUninstallAppsJButton.setEnabled(false);
			mSendFileButton.setEnabled(false);
			break;
		case 1:
			mUpdateDeviceList.setEnabled(true);
			mDetailsJButton.setEnabled(true);
			mCreateBackupJButton.setEnabled(true);
			mRestoreBackupJButton.setEnabled(true);
			mInstallAppsJButton.setEnabled(true);
			mUninstallAppsJButton.setEnabled(true);
			mSendFileButton.setEnabled(true);
			break;
		default:
			mUpdateDeviceList.setEnabled(true);
			mDetailsJButton.setEnabled(true);
			mCreateBackupJButton.setEnabled(false);
			mRestoreBackupJButton.setEnabled(true);
			mInstallAppsJButton.setEnabled(true);
			mUninstallAppsJButton.setEnabled(true);
			mSendFileButton.setEnabled(true);
			break;
		}
	}

	public void showProgressDialog(String message) {
		System.out.println("showProgressDialog : " + message);
		mProgressDialogJLablel.setText(message);
		if (mProgressDialog.isVisible() == false) {
			mProgressDialog.setVisible(true);
		}
	}

	public void hideProgressDialog() {
		if (mProgressDialog.isVisible()) {
			mProgressDialog.setVisible(false);
		}
	}

	public void showFileChooser(String actionCommand, FileFilter fileFilter, String FileChooserTitle) {
		System.out.println("showFileChooser : " + actionCommand);

		mJFileChooser.setFileFilter(fileFilter);
		mJFileChooser.setDialogTitle(FileChooserTitle);
		mJFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = mJFileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = mJFileChooser.getSelectedFile();
			//This is where a real application would open the file.
			mMainViewPresenter.onFileChoosed(file, mAndroidDeviceTable.getSelectedRows(), actionCommand); 
		} else {
			System.out.println("file choose cancel");
		}
	}
}
