package gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.Popup;

import utils.ProgramUtils;

public class Tray {
	public Properties pro = MainWindow.pro;
	private MainWindow window = null;
	private static SystemTray tray = null;
	private TrayIcon icon = null;
	private URL imgDefault = this.getClass().getResource("/img/appbar.png");
	private URL imgActive = this.getClass().getResource("/img/appbar.png");
	private PopupMenu popup;
	private MenuItem clear;
	private MenuItem start;
	private MenuItem showMain;
	private Image imageDefault;
	private Image imageActive;
	private boolean isIconSet = false;

	public Tray(MainWindow window) {
		if (tray != null)
			return;
		if (!SystemTray.isSupported()) {
			System.err.println("��֧�����̣�");
		}
		if (tray == null) {
			tray = SystemTray.getSystemTray();
		}
		if (window == null)
			throw new NullPointerException();
		this.window = window;

		try {
			setTray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		addIcon();
		// window.setVisible(false);
	}

	private void setTray() throws Exception {

		imageActive = new ImageIcon(imgActive).getImage();
		imageDefault = new ImageIcon(imgDefault).getImage();
		
		popup = new PopupMenu();

		showMain = new MenuItem("��ʾ���˵�");
		start = new MenuItem("ֹͣ��Ļ");
		clear = new MenuItem("�����Ļ");
		MenuItem exit = new MenuItem("�˳�");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ProgramUtils.exit(0, MainWindow.pro);
			}
		});

		showMain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window._window.setVisible(true);
			}
		});

		popup.add(showMain);
		popup.addSeparator();
		popup.add(start);
		popup.add(clear);
		popup.add(exit);
		
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Tray.this.stopOrStart();
			}
		});
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Tray.this.clear();
			}
		});
		//�۲��߳�
		Thread mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String status= null;
				while(true){
					status = start.getLabel();
					if (window.isLaunched) {
						start.setLabel("ֹͣ��Ļ");
					} else if(window.isLaunched == false && "ֹͣ��Ļ".equals(status)) {
						start.setLabel("��ʼ��Ļ");
						setRunIcon();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		mThread.setDaemon(true);
		mThread.start();
		

		icon = new TrayIcon(imageActive, "��Ļ��", popup);
		
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				icon.displayMessage("��Ļ��С��ʾ", "��Ļ���Ѿ�����ϵͳ����~~", MessageType.INFO);
			}
		}, 500);
		if(window.isLaunched){
			setRunIcon();
		}
	}

	private boolean addIcon() {
		if (tray == null || tray.getTrayIcons().length > 0)
			return false;
		else {
			try {
				tray.add(icon);
			} catch (AWTException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	}

	private void stopOrStart() {
		window.runActionPerformed(null);
		if (window.isLaunched) {
			start.setLabel("��ʼ��Ļ");
			setRunIcon();
		} else {
			start.setLabel("ֹͣ��Ļ");
			setStopIcon();
		}
	}
	private void clear(){
		if (window.isLaunched) {
			window.runActionPerformed(null);
			window.runActionPerformed(null);
		} 
		
	}

	private void setRunIcon() {
		if(!window.isLaunched || isIconSet)
			return ;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				isIconSet = true;
				while(window.isLaunched){
					try {
						Thread.sleep(500);
						icon.setImage(new ImageIcon("/ll.png").getImage());
						Thread.sleep(500);
						icon.setImage(imageActive);
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}).start();
	}

	private void setStopIcon() {
		if(window.isLaunched)
			return;
		isIconSet = false;
		icon.setImage(imageDefault);
	}

}
