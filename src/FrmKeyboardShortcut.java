import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;
class FrmKeyboardShortcut extends JInternalFrame{
	JTextArea jtaFile;
	JScrollPane jsp;
	
	FrmKeyboardShortcut(){
		super("Help",true,true,true,true);
		Font f1=new Font(Font.SERIF,Font.BOLD+Font.ITALIC,20);
		setLayout(new GridLayout(1,1,5,5));		
		jtaFile=new JTextArea();
		jsp=new JScrollPane(jtaFile);
			
		jtaFile.setText("Add Users\t\t  Ctrl+A\n Hotel Details\t\t  Ctrl+H\n Settings\t\t  Ctrl+S\n Exit\t\t  Ctrl+X\n\n Room Category\t  Shift+R\n Room\t\t  Shift+F5\n Guest\t\t  Shift+G\n Staff\t\t  Shift+S\n State Master\t\t  Shift+M\n City Master\t\t  Shift+C\n\n Booking\t\t  Alt+B\n CheckIn\t\t  Alt+C\n AddAdditional\t\t  Alt+A\n Bill\t\t  Alt+ENTER\n KeyboardShourtcut\t Alt+F1\n");
        jtaFile.setFont(f1);
		
		getContentPane().setBackground(Color.BLUE);
	  
	    jtaFile.setForeground(Color.blue);
	    jtaFile.setBackground(Color.orange);
	       
	    add(jsp);
	    jtaFile.setEditable(false);
		setResizable(false);
		setBounds(100,100,400,550);
		setVisible(true);
	}
	public static void main(String args[])
	{
		new FrmKeyboardShortcut();
	}
}
