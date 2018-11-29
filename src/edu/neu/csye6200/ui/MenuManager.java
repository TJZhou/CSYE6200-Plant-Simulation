package edu.neu.csye6200.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class MenuManager implements ActionListener {

	private String id = "";
	private String text = "";
	static int counter=0;
	private JMenuBar menuBar = null;
	private BGApp application = null;

	private HashMap<String, AbstractButton> buttonMap = new HashMap<String, AbstractButton>();

	/**
	 * Constructor
	 * The menu manager ID is auto generated
	 * @param aapplication the owning parent UI Application
	 */
	public MenuManager(BGApp application) {
		this.application = application;
		this.id = "menu-" + Integer.toString(counter++);
		init();
	}

	/**
	 * Constructor
	 * The menu manager ID is auto generated
	 * @param aapplication the owning parent UI Application
	 * @param text descriptive text for display purposes
	 */
	public MenuManager(BGApp application, String text) {
		this.application = application;
		this.text=text;
		this.id = "menu-" + Integer.toString(counter++);
		init();
	}

	/**
	 * Constructor
	 * @param aapplication the owning parent UI Application
	 * @param text descriptive text for display purposes
	 * @param id the menu manager ID
	 */
	public MenuManager(BGApp application, String text, String id) {
		this.application = application;
		this.text = text;
		this.id = id;
		init();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/*
	 * Initialize the menu manager
	 */
	private void init() {
		getMenuBar();
	}

	/*
	 * Default menu items routines
	 */
	public void createDefaultActions() {

		JMenu fileMenu = addMenu(menuBar,"core.file", "File", 'F', "File Menu Actions");
		fileMenu.addSeparator();
		createMenuItem(fileMenu, "Exit", 'x', "Exit the application", new ExitAction(application));

		JMenu editMenu = addMenu(menuBar, "core.edit", "Edit", 'E', "Edit Menu Actions");
		createMenuItem(editMenu, "Undo", 'U', "Undo", this);
		createMenuItem(editMenu, "Redo", 'U', "Undo", this);
		editMenu.addSeparator();
		createMenuItem("core.edit", "Cut", KeyEvent.VK_X, ActionEvent.CTRL_MASK, "Cut", this);
		createMenuItem("core.edit", "Copy", KeyEvent.VK_C, ActionEvent.CTRL_MASK, "Copy", this);
		createMenuItem("core.edit", "Paste", KeyEvent.VK_V, ActionEvent.CTRL_MASK, "Paste", this);
		editMenu.addSeparator();
		createMenuItem(editMenu, "Delete", 'D', "Delete", this);

		JMenu windowMenu = addMenu(menuBar, "core.window", "Window", 'W', "PanelWindow Menu Actions");

		createMenuItem(windowMenu, "Close window", '*', "Close the active window", this);
		createMenuItem(windowMenu, "Maximize window", '*', "Expand the active window", this);
		createMenuItem("core.window", "Undock window", 
				KeyEvent.VK_D, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK,
				"Move the active window pane into a detached window", this);
		windowMenu.addSeparator();

		JMenu helpMenu = addMenu(menuBar, "core.help", "Help", 'H', "Help Menu Actions");
		createMenuItem(helpMenu, "About...", '*', "About...", new AboutAction(application));
	}

	/**
	 * Get the top menu bar
	 * @return the JMenuBar instance
	 */
	public JMenuBar getMenuBar() {
		if (menuBar == null)
			menuBar = new JMenuBar();

		return menuBar;
	}

	/**
	 * Locate a Menu using its pathname
	 * @param menuPath the key menu pathname
	 * @return a located JMenu or null if none found
	 */
	public JMenu getMenu(String menuPath) {
		return (JMenu) buttonMap.get(menuPath);
	}

	/**
	 * Creates a named menu in the standard menu bar
	 * @return the generated JMenu instance
	 */
	public JMenu addMenu(String menuPath, String menuName, char menuMnemonic, String desc) {
		return (createMenu(menuBar,menuPath,menuName,menuMnemonic,desc, -1));
	}

	/**
	 * Creates a named menu in the standard menu bar
	 * @return the generated JMenu instance
	 */
	public JMenu createMenu(String menuPath, String menuName, char menuMnemonic, String desc, int pos) {
		return (createMenu(menuBar,menuPath,menuName,menuMnemonic,desc, pos));
	}

	/**
	 * Creates a named menu and set text values based on resource values
	 * @return the generated JMenu instance
	 */
	private JMenu addMenu(JMenuBar mBar, String menuPath, String menuName, char menuMnemonic, String desc) {
		return( createMenu(mBar, menuPath, menuName, menuMnemonic, desc, -1));
	}

	/**
	 * Creates a named menu and set text values based on resource values
	 * @return the generated JMenu instance
	 */
	public JMenu createMenu(JMenuBar mBar, String menuPath, String menuName, char menuMnemonic, String desc) {
		return( createMenu(mBar, menuPath, menuName, menuMnemonic, desc, 2));
	}


	/**
	 * Creates a named menu and set text values based on resource values
	 * @return the generated JMenu instance
	 */
	public JMenu createMenu(JMenuBar mBar, String menuPath, String menuName, char menuMnemonic, String desc, int pos) {
		//System.out.println("MenuManager:: Adding menu bar - " + menuPath + "/" + menuName);

		String fullMenuPath = menuPath;
		if (menuPath.length() == 0)
			fullMenuPath = "core." + menuName.toLowerCase();

		AbstractButton bmenu = buttonMap.get(fullMenuPath);
		if (bmenu != null)
			return ((JMenu) bmenu);

		JMenu menu = new JMenu(menuName);

		if (menuMnemonic != ' ')
			menu.setMnemonic(menuMnemonic);

		menu.getAccessibleContext().setAccessibleDescription(desc);

		// Add this menu to a Hashtable
		buttonMap.put(fullMenuPath, menu);

		if (pos < 0)
			menu = menuBar.add(menu);
		else
			menu = (JMenu) menuBar.add(menu,pos);

		return menu;
	}

	/**
	 * Add a menu bar separator
	 * @param menuPath The menu name
	 */
	public void addSeparator(String menuPath) {
		JMenu menu = (JMenu) buttonMap.get(menuPath);
		if (menu == null) return;
		menu.addSeparator();
	}

	/**
	 * Add a menu bar separator at the specified index
	 * @param menuPath The menu name
	 * @param index the insertion offset location beginning with 0
	 */
	public void insertSeparator(String menuPath, int index) {
		JMenu menu = (JMenu) buttonMap.get(menuPath);
		if (menu == null) return;
		menu.insertSeparator(index);
	}

	/**
	 * Creates a generic menu item
	 * @return the generated JMenuItem instance
	 */
	public JMenuItem createMenuItem(String menuPath, String label, int mnemonic, 
			String accessibleDescription, ActionListener action) {
		//System.out.println("MenuManager:: Adding menu item - path: " + menuPath + " label: " + label);
		JMenu menu = (JMenu) buttonMap.get(menuPath);
		if (menu == null){
			System.err.println("MenuManager:: Unable to locate a menu with path: " + menuPath);
			return null;
		}
		return( createMenuItem(menu, label, mnemonic, accessibleDescription, action));
	}

	private JMenuItem mi = null;

	/**
	 * Creates a generic menu item
	 * @return the generated JMenuItem instance
	 */
	public JMenuItem createMenuItem(final JMenu menu, final String label, final int mnemonic,
			final String accessibleDescription,final ActionListener action) {
		//System.out.println("MenuManager:: Adding menu item - " + label);

		String buttonPath = menu.getName() + "." + label;
		buttonMap.put(buttonPath, menu);

		mi = (JMenuItem) menu.add(new JMenuItem(label));

		if (mnemonic != ' ') {
			mi.setMnemonic(mnemonic);
		}

		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.setToolTipText(accessibleDescription);
		mi.addActionListener(action);
		if (action == null) {
			mi.setEnabled(false);
		}

		return mi;
	}

	/**
	 * Creates a generic menu item
	 * @return the generated JMenuItem instance
	 */
	public JMenuItem createMenuItem(String menuPath, String label, int keyevent, int modifier, 
			String accessibleDescription, ActionListener action) {
		return createMenuItem(menuPath, label, keyevent, modifier, accessibleDescription, action, -1);
	}

	/**
	 * Creates a generic menu item
	 * @return the generated JMenuItem instance
	 */
	public JMenuItem createMenuItem(String menuPath, String label, int keyevent, int modifier, 
			String accessibleDescription, ActionListener action, int pos) {

		JMenu menu = (JMenu) buttonMap.get(menuPath);
		if (menu == null) {
			System.err.println("MenuManager:: Unable to locate a menu with path: " + menuPath);
			return null;
		}
		return( createMenuItem(menu, label, keyevent, modifier, accessibleDescription, action, pos));
	}


	/**
	 * Creates a generic menu item
	 * @return the generated JMenuItem instance
	 */
	public JMenuItem createMenuItem(JMenu menu, String label, int keyevent, int modifier,
			String accessibleDescription, ActionListener action) {
		return(createMenuItem(menu,label,keyevent,modifier,accessibleDescription,action,-1));
	}


	/**
	 *  Creates a generic menu item
	 * @param menu the parent menu that this item should be added to
	 * @param label the display label text for the menu item
	 * @param keyevent an accelerator key definition (i.e. KeyEvent.VK_N for 'N'), or 0 if none defined
	 * @param modifier an action event modifier (i.e. ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK for Alt-shift)
	 * @param accessibleDescription a popup descriptor for this menu item
	 * @param action the ActionListner to call when this menu is chosen
	 * @param pos Insert after the index position if pos >= 0
	 * @return the generated JMenuItem instance
	 */
	public JMenuItem createMenuItem(JMenu menu, String label, int keyevent, int modifier,
			String accessibleDescription, ActionListener action, int pos) {
		//System.out.println("MenuManager:: Adding menu item - " + label);

		JMenuItem mi;
		if (pos < 0) // just add the menu item
			mi = (JMenuItem) menu.add(new JMenuItem(label));
		else
			mi = (JMenuItem) menu.insert(new JMenuItem(label),pos);

		if (keyevent != 0) {
			mi.setAccelerator(KeyStroke.getKeyStroke(keyevent, modifier));
			//KeyEvent.VK_N, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
		}
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.setToolTipText(accessibleDescription);
		mi.addActionListener(action);
		if (action == null) {
			mi.setEnabled(false);
		}
		String buttonPath = menu.getName() + "." + label;
		buttonMap.put(buttonPath, menu);
		return mi;
	}


	/**
	 * Creates a generic CheckBox menu item
	 * @return the generated JChackBoxMenuItem instance
	 */
	public JCheckBoxMenuItem createCheckBoxMenuItem(String menuPath, String label, int mnemonic, 
			String accessibleDescription, ActionListener action) {

		JMenu menu = (JMenu) buttonMap.get(menuPath);
		if (menu == null) return null;
		return( createCheckBoxMenuItem(menu, label, mnemonic, accessibleDescription, action));
	}

	/**
	 * Creates a generic CheckBox menu item
	 */
	public JCheckBoxMenuItem createCheckBoxMenuItem(JMenu menu, String label, int mnemonic,
			String accessibleDescription, ActionListener action) {
		JCheckBoxMenuItem mi = (JCheckBoxMenuItem) menu.add(new JCheckBoxMenuItem(label));
		mi.setMnemonic(mnemonic);
		mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
		mi.addActionListener(action);
		if (action == null) {
			mi.setEnabled(false);
		}
		return (mi);
	}

	@SuppressWarnings("unused")
	private int menuNum = 0;

	/**
	 * Return either a JMenu or a JMenuItem as an AbstractButton
	 * @param menuPath the pathname to the button (i.e. "menuName.menuItemName")
	 * @return an AbstractButton or null if not found
	 */
	public AbstractButton getMenuButton(String menuPath) {
		return(buttonMap.get(menuPath));
	}

	/**
	 * Add an external ActionListener to the specified Menu item
	 * @param buttonId the ID supplied as part of a Extension Plug
	 * @param listener the ActionListener class where ActionEvents will be sent
	 */
	public void addActionListener(String buttonId, ActionListener listener) {
		AbstractButton button = getMenuButton(buttonId);
		if (button == null) return;
		button.addActionListener(listener);
	}


	public void update() {
		menuBar.repaint();
	}

	public void updateMenuEnableStatus() {

	}

	public void actionPerformed(ActionEvent arg0) {

		System.out.println("MenuManager:: Received an Action: " + arg0.getActionCommand() +
				" param " + arg0.paramString());
		application.actionPerformed(arg0);
	}

	/**
	 * A default Exit action for the MenuManager
	 */
	class ExitAction extends AbstractAction {

		private static final long serialVersionUID = -9197382694558803756L;
		private BGApp application;

		protected ExitAction(BGApp application) {
			super("ExitAction");
			this.application = application;
		}

		public void actionPerformed(ActionEvent e) {
			application.exit();
		}
	}	

	/**
	 * A Default About action for the MenuManager
	 * @author MMUNSON
	 *
	 */
	class AboutAction extends AbstractAction {

		private static final long serialVersionUID = -9197382694558803756L;
		private BGApp application;

		protected AboutAction(BGApp application) {
			super("AboutAction");
			this.application = application;
		}

		public void actionPerformed(ActionEvent e) {
			application.showHelp();
		}
	}		

}
