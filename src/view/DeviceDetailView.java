package view;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import presenter.DeviceDetailPresenter;
import model.AndroidDevice;
import model.tableModel.AndroidDeviceTableModel;
import model.tableModel.DeviceDetailModel;

public class DeviceDetailView extends JFrame implements ActionListener {
	private final String TAG = getClass().getSimpleName();
	public final static String UNINSTALL_APP_BUTTON_COMMAND = "uninstall";
	public final static String BACK_TO_DEVICE_LIST_COMMAND = "backToDeviceList";

	private DeviceDetailPresenter mDeviceDetailPresenter;
	private JPanel mJPanel;
	private DeviceDetailModel mDeviceDetailModel;
	private JTable mDeviceDetailTable;
	private JPanel mButtonJPanel;
	private JButton mUninstallAppJButton;
	private JButton mBackJButton;
	private JDialog mProgressDialog;
	private JLabel mProgressDialogJLablel;
	private AndroidDeviceTableModel mAndroidDeviceTableModel;
	private JTable mAndroidDeviceTable;
	
	
	public DeviceDetailView(List<AndroidDevice> androidDeviceList, String jframeTitle) {
		super(jframeTitle);
		System.out.println("DeviceDetailView");
		mDeviceDetailPresenter = new DeviceDetailPresenter(this, androidDeviceList);
		mDeviceDetailModel = new DeviceDetailModel(mDeviceDetailPresenter.getmAndroidDeviceList(), mDeviceDetailPresenter.getmAppList());
		mUninstallAppJButton = new JButton("Uninstall App(s)");
		mBackJButton = new JButton("Back to devices list");
		mAndroidDeviceTableModel = new AndroidDeviceTableModel(mDeviceDetailPresenter.getmAndroidDeviceList());
		mAndroidDeviceTable = new JTable(mAndroidDeviceTableModel);
		mJPanel = new JPanel();
		mJPanel.setLayout(new BoxLayout(mJPanel, BoxLayout.Y_AXIS));
		mButtonJPanel = new JPanel();
		mButtonJPanel.setLayout(new BoxLayout(mButtonJPanel, BoxLayout.X_AXIS));
		mButtonJPanel.add(mUninstallAppJButton);
		mButtonJPanel.add(mBackJButton);
		mDeviceDetailTable = new JTable(mDeviceDetailModel);
		
		mDeviceDetailTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] selectedRows = mDeviceDetailTable.getSelectedRows();
				mDeviceDetailPresenter.onSelectedDeviceNumberChanged(selectedRows.length);
			}
		});
		
		mJPanel.add(new JLabel("Selected devices"));
		mJPanel.add(new JScrollPane(mAndroidDeviceTable));
		mJPanel.add(new JLabel("Installed apps on selected devices"));
		mJPanel.add(mButtonJPanel);
		mJPanel.add(mDeviceDetailTable);
		mJPanel.add(new JScrollPane(mDeviceDetailTable));

		mProgressDialogJLablel = new JLabel();
		mProgressDialog = new JDialog(this, "progress dialog", Dialog.ModalityType.APPLICATION_MODAL);
		mProgressDialog.setBounds(350, 350, 200, 200);
		mProgressDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mProgressDialog.setUndecorated(true);
		mProgressDialog.add(mProgressDialogJLablel);
		
		mUninstallAppJButton.setActionCommand(UNINSTALL_APP_BUTTON_COMMAND);
		mBackJButton.setActionCommand(BACK_TO_DEVICE_LIST_COMMAND);

		mUninstallAppJButton.addActionListener(this);
		mBackJButton.addActionListener(this);

		add(mJPanel);
		setSize(1000, 800);
		setResizable(true);
		setVisible(true);
		

		updateButtonsState(0);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerform");
		Thread t = new Thread(new Runnable() {
		      public void run() {
		    	  mDeviceDetailPresenter.onButtonClicked(e, mDeviceDetailTable.getSelectedRows());
		      }
		    });
		    t.start();
	}

	public void updateButtonsState(int androidDeviceSeletedNumber) {
		System.out.println("updateButtonsState " + androidDeviceSeletedNumber);
		switch (androidDeviceSeletedNumber) {
		case 0:
			mUninstallAppJButton.setEnabled(false);
			break;
		default:
			mUninstallAppJButton.setEnabled(true);
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
}

