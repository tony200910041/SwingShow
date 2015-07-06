/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ProgressBarDemo extends Tab
{
	ProgressBarDemo()
	{
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=1;
		c.weighty=0.2;
		c.fill=GridBagConstraints.BOTH;
		this.add(createHorizontal1(),c);
		c.gridx=0;
		c.gridy=1;
		c.fill=GridBagConstraints.BOTH;
		this.add(createHorizontal2(),c);
		c.gridx=0;
		c.gridy=2;
		c.fill=GridBagConstraints.BOTH;
		this.add(createHorizontal3(),c);
		c.gridx=0;
		c.gridy=3;
		c.weighty=0.4;
		c.fill=GridBagConstraints.BOTH;
		this.add(createVerticalPanel(),c);
	}
	
	static JPanel createHorizontal1()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Normal"));
		//normal progressbar
		JProgressBar prog1 = new JProgressBar(0,100);
		prog1.setValue(23);
		panel.add(prog1);
		//painted text
		JProgressBar prog2 = new JProgressBar(0,100);
		prog2.setValue(79);
		panel.add(prog2);
		//indeterminate
		JProgressBar prog3 = new JProgressBar(0,1);
		prog3.setIndeterminate(true);
		panel.add(prog3);
		return panel;
	}
	
	static JPanel createHorizontal2()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("String painted"));
		//29%
		JProgressBar prog1 = new JProgressBar(0,100);
		prog1.setStringPainted(true);
		prog1.setString("29%");
		prog1.setValue(29);
		panel.add(prog1);
		//loading
		JProgressBar prog2 = new JProgressBar(0,100);
		prog2.setStringPainted(true);
		prog2.setString("Loading...");
		prog2.setIndeterminate(true);
		panel.add(prog2);
		//disabled
		JProgressBar prog3 = new JProgressBar(0,100);
		prog3.setStringPainted(true);
		prog3.setString("Disabled");
		prog3.setEnabled(false);
		panel.add(prog3);
		return panel;
	}
	
	static JPanel createHorizontal3()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Loading"));
		final JProgressBar prog1 = new JProgressBar(0,100);
		prog1.setValue(0);
		Timer timer = new Timer(0,new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				int value = prog1.getValue();
				if (value != 100)
				{
					prog1.setValue(value+1);
				}
				else
				{
					prog1.setValue(0);
				}
			}
		});
		timer.setDelay(100);
		timer.start();
		panel.add(prog1);
		return panel;
	}
	
	static JPanel createVerticalPanel()
	{
		JPanel vertical = new JPanel(new FlowLayout(FlowLayout.CENTER));
		vertical.setBorder(new TitledBorder("Vertical"));
		//
		JProgressBar prog1 = new JProgressBar(SwingConstants.VERTICAL,0,100);
		prog1.setValue(10);
		prog1.setForeground(Color.RED);
		prog1.setStringPainted(true);
		prog1.setString("Vertical");
		vertical.add(prog1);
		//
		JProgressBar prog2 = new JProgressBar(SwingConstants.VERTICAL,0,100);
		prog2.setValue(30);
		prog2.setForeground(new Color(255,249,154));
		vertical.add(prog2);
		//
		JProgressBar prog3 = new JProgressBar(SwingConstants.VERTICAL,0,100);
		prog3.setValue(50);
		prog3.setForeground(new Color(234,255,193));
		vertical.add(prog3);
		//
		JProgressBar prog4 = new JProgressBar(SwingConstants.VERTICAL,0,100);
		prog4.setValue(70);
		prog4.setForeground(new Color(10,129,130));
		vertical.add(prog4);
		//
		JProgressBar prog5 = new JProgressBar(SwingConstants.VERTICAL,0,100);
		prog5.setIndeterminate(true);
		vertical.add(prog5);
		return vertical;
	}
}
