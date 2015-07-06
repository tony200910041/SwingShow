/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.io.*;
import java.util.*;

public class SwingShow extends JFrame
{
	//constants:
	private static final String VERSION_NO = "1.0";
	private static final String TEST_NO = "alpha";
	private static final String[] keyword = SwingShow.getKeywords();
	//component collection:
	private HashMap<String, Tab> tabMap = new HashMap<>();
	private HashMap<String, JButton> buttonMap = new HashMap<>();
	private ArrayList<MyLAFItem> LAFItemlist = new ArrayList<>();
	private String initialLAF = UIManager.getLookAndFeel().getClass().getName();
	//main components:
	private JMenuBar menuBar = new JMenuBar();
	private JToolBar toolBar = new JToolBar("Demo",JToolBar.HORIZONTAL);
	private JComponent centerPane;
	private JTextField textField = new JTextField();
	private static SwingShow w;
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				UIManager.put("swing.boldMetal", false);
				ToolTipManager.sharedInstance().setInitialDelay(100);
				ToolTipManager.sharedInstance().setDismissDelay(10000);
				w = new SwingShow();
				w.setVisible(true);
			}
		});
	}
	
	SwingShow()
	{
		super("SwingShow " + VERSION_NO);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(menuBar);
		this.setLayout(new BorderLayout());
		this.createMenuStructure();
		//
		textField.setEditable(false);
		textField.setFocusable(false);
		textField.setText(" Welcome to SwingShow!");
		this.add(toolBar, BorderLayout.PAGE_START);
		this.add(textField, BorderLayout.PAGE_END);		
		//
		this.loadAllDemos();
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	void createMenuStructure()
	{
		JMenu menu1 = new JMenu("SwingShow");
		menu1.add(new MyMenuItem("About",1));
		menu1.addSeparator();
		menu1.add(new MyMenuItem("Exit",2));
		menuBar.add(menu1);
		//
		JMenu menu2 = new JMenu("Look and Feel");
		for (UIManager.LookAndFeelInfo laf: UIManager.getInstalledLookAndFeels())
		{
			menu2.add(new MyLAFItem(laf));
		}
		menuBar.add(menu2);
	}
	
	class MyMenuItem extends JMenuItem implements ActionListener
	{
		private int index;
		MyMenuItem(String text, int index)
		{
			super(text);
			this.addActionListener(this);
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			switch (this.index)
			{
				case 1: //about
				JOptionPane.showMessageDialog(SwingShow.this, "SwingShow " + VERSION_NO + TEST_NO + " -- an application written to show off Java Swing.\nBy tony200910041.\nDistributed under MPL 2.0.");
				break;
				
				case 2: //exit
				System.exit(0);
				break;
			}
		}
	}
	
	class MyLAFItem extends JRadioButtonMenuItem implements ActionListener
	{
		private UIManager.LookAndFeelInfo laf;
		MyLAFItem(UIManager.LookAndFeelInfo laf)
		{
			super(laf.getName());
			this.addActionListener(this);
			this.setSelected(laf.getClassName().equals(initialLAF));
			//
			LAFItemlist.add(this);
			this.laf = laf;
		}
		
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			try
			{
				UIManager.setLookAndFeel(laf.getClassName());
				SwingUtilities.updateComponentTreeUI(SwingShow.this);
				// remove old cache
				tabMap.clear();
				// now reload tab
				if (centerPane instanceof MyInternalTabbedPane)
				{
					//has source code
					SwingShow.this.loadDemo(((MyInternalTabbedPane)centerPane).getTab().getSourceFileName(),false);
				}
				else if (centerPane instanceof Tab)
				{
					//no source code, centerPane is a Tab instance
					SwingShow.this.loadDemo(((Tab)centerPane).getSourceFileName(),false);
				}
				//now reselect menu items
				for (MyLAFItem item: LAFItemlist)
				{
					item.setSelected(false);
				}
				this.setSelected(true);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	void loadAllDemos()
	{
		/*
		 * classNameList: class name that has to be loaded
		 */
		ArrayList<String> classNameList = new ArrayList<>(16);
		classNameList.add("Welcome");
		classNameList.add("ButtonDemo");
		classNameList.add("TextFieldDemo");
		classNameList.add("EditorPaneDemo");
		classNameList.add("ComboBoxDemo");
		classNameList.add("SplitPaneDemo");
		classNameList.add("SliderDemo");
		classNameList.add("SpinnerDemo");
		classNameList.add("ProgressBarDemo");
		classNameList.add("ColorChooserDemo");
		classNameList.add("FileChooserDemo");
		classNameList.add("OptionPaneDemo");
		classNameList.add("InternalFrameDemo");
		classNameList.add("ToolTipDemo");
		classNameList.add("ScrollPaneDemo");
		classNameList.add("TreeDemo");
		//
		for (String name: classNameList)
		{
			try
			{
				loadDemo(name,true);
			}
			catch (Exception ex)
			{
				System.out.println(ex);
			}
		}
		centerPane = tabMap.get("Welcome");
		this.add(centerPane, BorderLayout.CENTER);
	}
	
	void loadDemo(String name, boolean useCache) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		boolean hasCache;
		final Tab[] tab = new Tab[1]; //use array to allow mutable "final" variable
		//now load tab:
		if (tabMap.containsKey(name)&&useCache)
		{
			tab[0] = tabMap.get(name);
			hasCache = true;
		}
		else
		{
			tab[0] = (Tab)(Class.forName(name).newInstance());
			tabMap.put(name,tab[0]);
			hasCache = false;
		}
		/*
		 * load button
		 * will not reload duplicate buttons to toolbar
		 */
		JButton button;
		if (buttonMap.containsKey(name))
		{
			button = buttonMap.get(name);
		}
		else
		{
			button = new JButton();
			toolBar.add(button);
			buttonMap.put(name,button);
		}
		if (!useCache)
		{
			//now remove old listener
			for (ActionListener listener: button.getActionListeners())
			{
				button.removeActionListener(listener);
			}
		}
		if ((!useCache)||(!hasCache))
		{
			//get extra Runnable
			final Runnable runnable = tab[0].getExtraRunnable();
			//add ActionListener
			button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent ev)
				{
					try
					{
						SwingShow.this.loadDemo(tab[0].getSourceFileName(),true);
						if (runnable != null)
						SwingUtilities.invokeLater(runnable);
					}
					catch (Exception ex)
					{
					}
				}
			});
		}
		button.setIcon(tab[0].getIcon());
		button.setToolTipText(tab[0].getTitle());
		button.setOpaque(false);
		//finally, set tab to display
		this.display(tab[0]);
	}
	
	void display(Tab tab)
	{
		try
		{
			this.remove(centerPane);
		}
		catch (Exception ex)
		{
		}
		if (tab.hasSourceCode())
		{
			centerPane = new MyInternalTabbedPane(tab);
		}
		else
		{
			centerPane = tab;
		}
		this.add(centerPane, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	class MyInternalTabbedPane extends JTabbedPane implements ChangeListener
	{
		private DefaultStyledDocument doc = new DefaultStyledDocument();
		private JTextPane textPane = new JTextPane(doc);
		private String srcFile;
		private boolean hasLoaded = false;
		//
		private SimpleAttributeSet attrBlue = new SimpleAttributeSet();
		private SimpleAttributeSet attrRed = new SimpleAttributeSet();
		private SimpleAttributeSet attrOrange = new SimpleAttributeSet();
		private Tab tab;
		MyInternalTabbedPane(Tab tab)
		{
			super();
			String title = tab.getTitle();
			if (!title.startsWith("J")) title = "J" + title;
			this.addTab(title,null,tab,tab.getToolTip());
			JPanel tab2 = new JPanel(new BorderLayout());
			tab2.add(new JScrollPane(textPane), BorderLayout.CENTER);
			this.addTab("Source code",null,tab2,"Source code for reference");
			this.srcFile = tab.getSourceFileName();
			this.addChangeListener(this);
			//
			StyleConstants.setForeground(attrBlue,new Color(0,0,170));
			StyleConstants.setFontFamily(attrBlue,"Serif");
			StyleConstants.setForeground(attrOrange,new Color(255,144,30));
			StyleConstants.setFontFamily(attrOrange,"Serif");
			StyleConstants.setForeground(attrRed,new Color(200,0,0));
			StyleConstants.setFontFamily(attrRed,"Serif");
			//
			this.tab = tab;
		}
		
		@Override
		public void stateChanged(ChangeEvent ev)
		{
			int index = this.getSelectedIndex();
			if ((index == 1)&&(!hasLoaded))
			{
				//textPane activated
				try
				{
					textPane.setText(loadCode());
					textPane.setCaretPosition(0);
				}
				catch (IOException ex)
				{
				}
				finally
				{
					textPane.setEditable(false);
					//now highlight keyword
					for (String str: keyword)
					{
						String key = str + " ";
						int length = str.length()+1;
						for (int i=0; i<doc.getLength()-length; i++)
						{
							try
							{
								if (key.equals(doc.getText(i,length)))
								{
									doc.setCharacterAttributes(i,length,attrBlue,false);
								}
							}
							catch (Exception ex)
							{
							}
						}
					}
					//now highlight comment, string and char, override "highlighe keyword"
					for (int i=0; i<doc.getLength()-2; i++)
					{
						try
						{
							if (("/*").equals(doc.getText(i,2)))
							{
								int start = i;
								while (!("*/").equals(doc.getText(i,2)))
								{
									i++;
								}
								doc.setCharacterAttributes(start,i+2-start,attrRed,false);
							}
							else if (("//").equals(doc.getText(i,2)))
							{
								int start = getLineStart(i);
								int end = getLineEnd(i);
								doc.setCharacterAttributes(start,end-start,attrRed,false);
							}
							else if (("\"").equals(doc.getText(i,1)))
							{
								int start = i;
								while ((!("\"").equals(doc.getText(i+1,1)))||((("\\").equals(doc.getText(i,1)))&&(("\\\\").equals(doc.getText(i-1,1)))))
								{
									i++;
								}
								doc.setCharacterAttributes(start,i+2-start,attrOrange,false);
								i+=2;
							}
							else if (("\'").equals(doc.getText(i,1)))
							{
								doc.setCharacterAttributes(i,3,attrOrange,false);
								i+=3;
							}
						}
						catch (Exception ex)
						{
						}
					}
					hasLoaded = true;
				}
			}
		}
		
		int getLineStart(int caret)
		{
			for (int i=caret-1; i>=0; i--)
			{
				try
				{
					if (("\n").equals(doc.getText(i,1)))
					{
						return i;
					}
				}
				catch (Exception ex)
				{
				}
			}
			return 0;
		}
		
		int getLineEnd(int caret)
		{
			for (int i=caret; i<doc.getLength()-1; i++)
			{
				try
				{
					if (("\n").equals(doc.getText(i,1)))
					{
						return i;
					}
				}
				catch (Exception ex)
				{
				}
			}
			return doc.getLength();
		}
		
		String loadCode() throws IOException
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(SwingShow.class.getResourceAsStream("/" + srcFile + ".java")));
			StringBuilder builder = new StringBuilder();
			String buffer;
			while ((buffer = reader.readLine()) != null)
			{
				builder.append(buffer.replace("\t","          ")+"\n");
			}
			return builder.toString();
		}
		
		Tab getTab()
		{
			return tab;
		}
	}
	
	static String[] getKeywords()
	{
		return new String[]{"abstract","assert","boolean","break","byte",
							"case","catch","char","class","const",
							"continue","default","do","double","else",
							"enum","extends","final","finally","float",
							"for","goto","if","implements","import",
							"instanceof","int","interface","long","native",
							"new","package","private","protected","public",
							"return","short","static","strictfp","super",
							"switch","synchronized","this","throw","throws",
							"transient","try","void","volatile","while"};
	}
}
