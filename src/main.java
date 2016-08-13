import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import gui.*;


/**	
 * ��Ļ�� �����
 * 
 * ��Ļ�������
 * PS:������ʹ�ñ���Ĺ�����̬������ʹ��Java�ķ�����ƻ�ȡ��
 * @author Hafrans
 * @since 1.0
 */
public class main {
	public static Properties pro = null;
	private static File filename = new File("./config.xml");
	private static InputStream fin = null;
	
	/**
	 * Ԥ����
	 */
	static{
		try{
			if(!filename.exists()){
				FileOutputStream fos = new FileOutputStream(filename);
				pro = new Properties();
				pro.setProperty("server-address", "http://api.hafrans.com/api.php?action=barrage");
				pro.setProperty("heartbeat", "3");
				pro.setProperty("font-face", "����");
				pro.setProperty("text-size", "48");
				pro.setProperty("step", "6");
				pro.setProperty("step-time", "36");
				pro.setProperty("queue-length", "5");
				pro.setProperty("random-speed", "0");
				pro.setProperty("updated", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				pro.storeToXML(fos, "created on "+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				fos.close();
			}
			fin = new FileInputStream(filename);
			if(pro == null){
				pro = new Properties();
				pro.loadFromXML(fin);
			}
			fin.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("all")
	/**	
	 * �������������
	 * @author Hafrans 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		new MainWindow();
//		LookAndFeelInfo[] infos=UIManager.getInstalledLookAndFeels();
//		 System.out.println("��֧�ֵ�Ƥ���� ");
//		 for(LookAndFeelInfo info : infos){
//		 System.out.println(info.getName()+" "+info.getClassName());
//		 }

	}

}
