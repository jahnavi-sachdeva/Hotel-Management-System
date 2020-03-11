import java.awt.*;
import java.awt.event.*;            //******//
import javax.swing.*;
class FrmAboutUs extends JInternalFrame {
	JLabel lblImage;
	JTextArea jtaHotelName,jtaAboutHotel;
	FrmAboutUs(){
	    super("About Us",true,true,true,true);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLayout(new FlowLayout());
	    Font f1=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,45);
	    Font f2=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
	    ImageIcon ii=new ImageIcon("images/hotel.jpg");
        ImageIcon ii2=new ImageIcon(ii.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
		lblImage=new JLabel(ii2);
		jtaHotelName=new JTextArea();
		jtaAboutHotel=new JTextArea();                                                                                       
		jtaHotelName.setText("  Hotel Ratan Vilas");
		jtaAboutHotel.setText("Welcome!!  to my new project that is Hotel Managment System and here follows some of its Description\n which explain its uses in daily life.\n\n ABOUT SOFTWARE :  The project, Hotel Management System is a web-based application\nthat allows the hotel manager to handle all hotel activities online.Interactive GUI and the \nability to manage various hotel bookings and rooms make this system very flexible and convenient.\nThe hotel manager is a very busy person and does not have the time to sit and \nobserve the entire activities manually on paper. This application gives him the power and fle\nxibility to manage the entire system from a single online system. Hotel management project\nprovides room booking,staff management and other necessary hotel management feature. \n\n\n\nDeveloped By : AKSHI TAK \nDate : 30-06-2019");
 
      	jtaHotelName.setEditable(false);
      	jtaAboutHotel.setEditable(false);
		jtaHotelName.setFont(f1);
		jtaAboutHotel.setFont(f2);
	    
	     // color 
	    Color color=new Color(225,218,187);   
        jtaHotelName.setBackground(color);
	    jtaAboutHotel.setBackground(color);
	    jtaHotelName.setForeground(Color.red);
	    getContentPane().setBackground(color);
	  
	    add(lblImage);add(jtaHotelName);
		add(jtaAboutHotel);
		setTitle("About Software  ");
		setSize(1000,700);
		setVisible(true);
	  
}	
	 public static void main(String args[])
       {
       	  new FrmAboutUs();
       } 	
}