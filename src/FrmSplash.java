import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class FrmSplash extends JFrame {
	ImageIcon imgIcon;
	JLabel lblImage;
	JProgressBar jpb;
	FrmSplash(){
		setUndecorated(true);
		imgIcon=new ImageIcon("images/splash.png");
		imgIcon=new ImageIcon(imgIcon.getImage().getScaledInstance(390, 270, Image.SCALE_DEFAULT));
		lblImage=new JLabel(imgIcon);
		add(lblImage);
		jpb=new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		jpb.setBackground(Color.cyan);
		jpb.setForeground(Color.blue);
		add(jpb,"South");
		setSize(400,300);
		setLocationRelativeTo(null);
		setVisible(true);
		for(int i=0;i<=100;i++) {
			jpb.setValue(i);
			try {
				Thread.sleep(50);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		CommonMethods.createAllTables();
		try {
			dispose();
			DConnection dc=new DConnection();
			ResultSet rst=dc.executeSelectQuery("select count(*) from users");
			rst.next();
			int cnt=rst.getInt(1);
			dc.close();
			if(cnt==0)
				new FrmSignUp();
			else
				new FrmLogin();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new FrmSplash();
	}

}
