package ui;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import controller.AppController;

import java.awt.Image;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.InputEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private AppController controller;
    private JMenuBar menuBarWindow;
    private JMenu mnOptions;
    private JMenuItem mntmExit;
    
    private JMenuItem mntmHelp;
    private HelpBroker hb;
    private HelpSet hs;
    private Locale currentLocale;
    private ResourceBundle texts;
    private JMenu mnLanguage;
    private JMenuItem mntmEnglish;
    private JMenuItem mntmSpanish;




    public MainWindow() {
    	try {
    	    UIManager.setLookAndFeel( new FlatLightLaf() );
    	    UIManager.put("Component.hideMnemonics", false);
    	    UIManager.put("Button.showMnemonics", true);
    	    

    	} catch( Exception ex ) {
    	    System.err.println( "Failed to initialize LaF" );
    	}


    	ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/img/logo.png"));
    	Image img = icon.getImage();
    	Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    	setIconImage(scaled);

        controller = new AppController();

        setTitle("OpenWorld Agency");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 722, 500);
        
        setLocationRelativeTo(null);
        setJMenuBar(getMenuBar_1());
        loadHelp();
        currentLocale = new Locale("en", "EN"); 
        loadTexts();
        localize();
        switchPanel(new MainMenuPanel(controller, this), "MainMenu");
        
        
        
    }
    private void loadTexts() {
        texts = ResourceBundle.getBundle("rcs.messages", currentLocale);
    }

    private void localize() {
       
        setTitle(texts.getString("app.title"));

        if (mnOptions != null) {
            mnOptions.setText(texts.getString("menu.options"));
        }
        if (mntmHelp != null) {
            mntmHelp.setText(texts.getString("menu.help"));
        }
        if (mntmExit != null) {
            mntmExit.setText(texts.getString("menu.exit"));
        }
        if (mnLanguage != null) {
        	mnLanguage.setText(texts.getString("menu.language"));
        }
        if (getContentPane() instanceof Localizable panel) {
            panel.localize(texts);
        }
    }
    
    public void changeLanguage(Locale locale) {
        this.currentLocale = locale;
        loadTexts();
        localize();
    }


    
    public void switchPanel(JPanel panel, String helpID) {
        setContentPane(panel);
        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> {
            panel.requestFocusInWindow();
        });
       
        hb.enableHelpKey(panel, helpID, hs);
        hb.enableHelpOnButton(getMntmHelp(), helpID, hs);
        loadTexts();
        localize();
        
    }

    
	private JMenuBar getMenuBar_1() {
		if (menuBarWindow == null) {
			menuBarWindow = new JMenuBar();
			menuBarWindow.add(getMnOptions());
			menuBarWindow.add(getMnLanguage());
		}
		return menuBarWindow;
	}
	private JMenu getMnOptions() {
		if (mnOptions == null) {
			mnOptions = new JMenu("Options");
			mnOptions.setMnemonic('O');
			mnOptions.add(getMntmHelp());
			mnOptions.add(getMntmExit());
		}
		return mnOptions;
	}
	
	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Exit");
			mntmExit.setMnemonic('x');
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);			
				}
			});
			mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		}
		return mntmExit;
	}
	private void loadHelp() {

		   

		    try {
			    	File fichero = new File("help/Help.hs");
			    	URL hsURL = fichero.toURI().toURL();
			    	hs = new HelpSet(null, hsURL);
			    	hb = hs.createHelpBroker();
			      }

		    catch (Exception e){
		      System.out.println("Help not found!");
		      return;
		   }

		  
	}
	
	private JMenuItem getMntmHelp() {
		if (mntmHelp == null) {
			mntmHelp = new JMenuItem("Help");
			mntmHelp.setMnemonic('H');;
		}
		return mntmHelp;
	}
	private JMenu getMnLanguage() {
		if (mnLanguage == null) {
			mnLanguage = new JMenu("Language");
			mnLanguage.setMnemonic('L');
			mnLanguage.add(getMntmEnglish());
			mnLanguage.add(getMntmSpanish());
		}
		return mnLanguage;
	}
	private JMenuItem getMntmEnglish() {
		if (mntmEnglish == null) {
			mntmEnglish = new JMenuItem("English",
					new ImageIcon(MainWindow.class.getResource("/img/flag_english.png")));
			mntmEnglish.setMnemonic('E');
			mntmEnglish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeLanguage(new Locale("en", "EN"));
				}
			});
		}
		return mntmEnglish;
	}
	private JMenuItem getMntmSpanish() {
		if (mntmSpanish == null) {
			mntmSpanish = new JMenuItem("Spanish",
					new ImageIcon(MainWindow.class.getResource("/img/flag_spain.png")));
			mntmSpanish.setMnemonic('S');
			mntmSpanish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeLanguage(new Locale("es", "ES"));
				}
			});
		}
		return mntmSpanish;
	}
}
