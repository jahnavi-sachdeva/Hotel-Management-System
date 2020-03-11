
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FrmMainFrame extends JFrame implements ActionListener{
	JToolBar jtb;
	JButton btnBooking,btnAddAditional,btnBill;
	JMenuBar mbar;
	JMenu mnuFile,mnuMasters,mnuTransaction,mnuReports,mnuHelp;
	JMenuItem mitAddUsers,mitHotelDetails,mitSettings,mitExit;
	JMenuItem mitRoomCategory,mitRoom,mitGuest,mitStaff,mitState,mitCity;
	JMenuItem mitBooking,mitCheckIn,mitAddAdditional,mitCheckOut;
	JMenuItem mitGuestList,mitBillTotal,mitTarrif;
	JMenuItem mitKeyboardShourtcut,mitAboutUs;
		
	JPanel statusBar;
	JLabel lblMessage;
	JDesktopPane jdp;
	FrmMainFrame(String userId,String userType){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Hotel Management System: Welcome "+userId);
		//MenuBar
		mbar=new JMenuBar();
		mitAddUsers=new JMenuItem("Add Users");
		mitAddUsers.addActionListener(this);
		mitHotelDetails=new JMenuItem("Hotel Details");
		mitHotelDetails.addActionListener(this);
		mitSettings=new JMenuItem("Settings");
		mitSettings.addActionListener(this);
		mitExit=new JMenuItem("Exit");
		mitExit.addActionListener(this);
		
		mitRoomCategory=new JMenuItem("Room Category");
		mitRoomCategory.addActionListener(this);
		mitRoom=new JMenuItem("Room");
		mitRoom.addActionListener(this);
		mitGuest=new JMenuItem("Guest");
		mitGuest.addActionListener(this);
		mitStaff=new JMenuItem("Staff");
		mitStaff.addActionListener(this);
		mitState=new JMenuItem("State");
		mitState.addActionListener(this);
		mitCity=new JMenuItem("City");
		mitCity.addActionListener(this);
			
		mitBooking=new JMenuItem("Booking");
		mitBooking.addActionListener(this);
		mitCheckIn=new JMenuItem("CheckIn");
		mitCheckIn.addActionListener(this);
		mitAddAdditional=new JMenuItem("Add Additional Charges");
		mitAddAdditional.addActionListener(this);
		mitCheckOut=new JMenuItem("Check Out");
		mitCheckOut.addActionListener(this);

		//Help Menu Item
		mitKeyboardShourtcut=new JMenuItem("Keyboard Shortcut");
		mitKeyboardShourtcut.addActionListener(this);
		mitAboutUs=new JMenuItem("About Us");
		mitAboutUs.addActionListener(this);
		
		
		mnuFile=new JMenu("File");
		if(userType.equalsIgnoreCase("admin")) {
			mnuFile.add(mitAddUsers);
			mnuFile.add(mitHotelDetails);
		}
		mnuFile.add(mitSettings);
		mnuFile.addSeparator();
		mnuFile.add(mitExit);
		
		//Masters Menu
		mnuMasters=new JMenu("Masters");
		mnuMasters.add(mitRoomCategory);
		mnuMasters.add(mitRoom);
		mnuMasters.addSeparator();
		mnuMasters.add(mitGuest);
		mnuMasters.add(mitStaff);
		mnuMasters.add(mitState);
		mnuMasters.add(mitCity);
		
		//Transaction Menu
		mnuTransaction=new JMenu("Transaction");
		mnuTransaction.add(mitBooking);
		mnuTransaction.add(mitCheckIn);
		mnuTransaction.add(mitAddAdditional);
		mnuTransaction.addSeparator();
		mnuTransaction.add(mitCheckOut);
		
		//Reports Menu
		mnuReports=new JMenu("Reports");
		mitGuestList=new JMenuItem("Guest List");
		mitTarrif=new JMenuItem("Tarrif Plan");
		mitBillTotal=new JMenuItem("Total Bill");
		mnuReports.add(mitGuestList);
		mnuReports.add(mitBillTotal);
		mnuReports.add(mitTarrif);
		mitGuestList.addActionListener(this);
		mitTarrif.addActionListener(this);
		mitBillTotal.addActionListener(this);
				
		//Help Menu
		mnuHelp=new JMenu("Help");
		mnuHelp.add(mitKeyboardShourtcut);
		mnuHelp.add(mitAboutUs);
		
	    //Keyboard shortcut
	    mitAddUsers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
	    mitExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
	    mitHotelDetails.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
	    mitSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
	    mitRoomCategory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
	    mitRoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,ActionEvent.CTRL_MASK));
	    mitGuest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
	    mitStaff.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
	    mitBooking.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.ALT_MASK));
	    mitCheckOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,ActionEvent.ALT_MASK));
        mitCheckIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
	    mitAddAdditional.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK));
	    mitKeyboardShourtcut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,ActionEvent.ALT_MASK));
        mitState.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.CTRL_MASK));
	    mitCity.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
	
		//Mnemonic
		mnuFile.setMnemonic(KeyEvent.VK_F);
		mnuMasters.setMnemonic(KeyEvent.VK_M);
		mnuTransaction.setMnemonic(KeyEvent.VK_T);
		mnuReports.setMnemonic(KeyEvent.VK_R);
		mnuHelp.setMnemonic(KeyEvent.VK_H);
				
		mbar.add(mnuFile);
		mbar.add(mnuMasters);
		mbar.add(mnuTransaction);
		mbar.add(mnuReports);
		mbar.add(mnuHelp);
		setJMenuBar(mbar);
		
		//ToolBar 
		jtb=new JToolBar();
		btnBooking=new JButton("Booking");
		btnAddAditional=new JButton("Add Charges");
		btnBill=new JButton("Final Bill");
		jtb.add(btnBooking);
		jtb.add(btnAddAditional);
		jtb.add(btnBill);
		jtb.setFloatable(true);
		add(jtb,"North");
		
		//statusbar
		statusBar=new JPanel();
		statusBar.setBackground(Color.lightGray);
		lblMessage=new JLabel(".");
		statusBar.setLayout(new GridLayout(1,1));
		statusBar.add(lblMessage);
		statusBar.setBackground(Color.lightGray);
		add(statusBar,"South");
		
		jdp=new MyJDesktopPane();
		add(jdp);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	public static void main(String[] args) {
		new FrmMainFrame("admin","admin");
	}
	public void actionPerformed(ActionEvent ae) {
			String s1=ae.getActionCommand();
			if(s1.equalsIgnoreCase("Add Users")){
				jdp.add(new FrmUsers(jdp));
			}
			else if(s1.equalsIgnoreCase("hotel details")){
				jdp.add(new FrmHotelDetails(jdp));
			}			
			else if(s1.equalsIgnoreCase("settings")){
				//jdp.add(new FrmSettings(jdp));
			}			
			else if(s1.equalsIgnoreCase("exit")){
				System.exit(0);
			}
			else if(s1.equalsIgnoreCase("room category")){
				jdp.add(new FrmRoomcat(jdp));
			}
			else if(s1.equalsIgnoreCase("room")){
				jdp.add(new FrmRoom(jdp));
			}
			else if(s1.equalsIgnoreCase("guest")){
				jdp.add(new FrmGuest(jdp));
			}
			else if(s1.equalsIgnoreCase("staff")){
				jdp.add(new FrmEmployee(jdp));
			}
			else if(s1.equalsIgnoreCase("city")){
				jdp.add(new FrmCity(jdp));
			}
			else if(s1.equalsIgnoreCase("state")){
				jdp.add(new FrmState(jdp));
			}
			else if(s1.equalsIgnoreCase("Booking")){
				jdp.add(new FrmBooking(jdp));
			}
			else if(s1.equalsIgnoreCase("CheckIn")){
				jdp.add(new FrmCheckIn(jdp));
			}
			else if(s1.equalsIgnoreCase("Add Additional Charges")){
				jdp.add(new FrmAdditional(jdp));
			}
			else if(s1.equalsIgnoreCase("Check Out")){
				jdp.add(new FrmCheckOut(jdp));
			}
			else if(s1.equalsIgnoreCase("Guest List")){
				jdp.add(new FrmGuestList(jdp));
			}
			else if(s1.equalsIgnoreCase("Tarrif Plan")){
				jdp.add(new FrmRoomTarrif(jdp));
			}
			else if(s1.equalsIgnoreCase("Total Bill")){
				jdp.add(new FrmBillingReport(jdp));
			}
			else if(s1.equalsIgnoreCase("Keyboard Shortcut")){
				jdp.add(new FrmKeyboardShortcut());
			}
			else if(s1.equalsIgnoreCase("About Us")){
				jdp.add(new FrmAboutUs());
			}
			

	}
}
class MyJDesktopPane extends JDesktopPane{
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon ii=new ImageIcon("images/desktop.jpg");
		g.drawImage(ii.getImage(),0,0,getSize().width,getSize().height,null);
	}
}
