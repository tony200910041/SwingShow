/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Tab extends JPanel
{
	Tab()
	{
		super();
		this.setPreferredSize(new Dimension(670,490));
	}
	
	String getTitle()
	{
		return this.getClass().getSimpleName().replace("Demo"," Demo");
	}
	
	String getSourceFileName()
	{
		return this.getClass().getSimpleName();
	}
	
	ImageIcon getIcon()
	{
		return new ImageIcon(this.getClass().getResource(this.getClass().getSimpleName().replace("Demo","").toUpperCase()+"_ICON.PNG"));
	}
	
	String getToolTip()
	{
		return this.getTitle();
	}
	
	boolean hasSourceCode()
	{
		return true;
	}
	
	/*
	 * Provide an extra Runnable
	 * The Runnable will run on the Event Dispatch Thread when tab is loaded
	 */
	Runnable getExtraRunnable()
	{
		return null;
	}
	
	static ImageIcon getIcon(String name)
	{
		return new ImageIcon(Tab.class.getResource(name+".PNG"));
	}
}
