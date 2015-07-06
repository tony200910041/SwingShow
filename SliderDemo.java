/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.*;

public class SliderDemo extends Tab
{
	SliderDemo()
	{
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.5;
		c.weighty=1;
		c.fill=SwingConstants.VERTICAL;
		this.add(SliderDemo.createHorizontalSliderPanel(),c);
		c.gridx=1;
		c.gridy=0;
		c.fill=SwingConstants.VERTICAL;
		this.add(SliderDemo.createVerticalSliderPanel(),c);
	}
	
	static JPanel createHorizontalSliderPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1,0,0));
		panel.add(SliderDemo.createStandardSliderDemo(JSlider.HORIZONTAL));
		panel.add(SliderDemo.createTickedSliderDemo(JSlider.HORIZONTAL));
		panel.add(SliderDemo.createLabeledSliderDemo(JSlider.HORIZONTAL));
		panel.add(SliderDemo.createCustomSliderDemo(JSlider.HORIZONTAL));
		panel.add(SliderDemo.createDisabledSliderDemo(JSlider.HORIZONTAL));
		return panel;
	}
	
	static JPanel createVerticalSliderPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,5,0,0));
		panel.add(SliderDemo.createStandardSliderDemo(JSlider.VERTICAL));
		panel.add(SliderDemo.createTickedSliderDemo(JSlider.VERTICAL));
		panel.add(SliderDemo.createLabeledSliderDemo(JSlider.VERTICAL));
		panel.add(SliderDemo.createCustomSliderDemo(JSlider.VERTICAL));
		panel.add(SliderDemo.createDisabledSliderDemo(JSlider.VERTICAL));
		return panel;
	}
	
	static JPanel createStandardSliderDemo(int orientation)
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Default"));
		panel.add(new JSlider(orientation,0,100,30),BorderLayout.CENTER);
		return panel;
	}
	
	static JPanel createTickedSliderDemo(int orientation)
	{
		/*
		 * create JSlider with ticks
		 */
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Ticks"));
		JSlider slider = new JSlider(orientation,0,100,70);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setSnapToTicks(true);
		panel.add(slider,BorderLayout.CENTER);
		return panel;
	}
	
	static JPanel createLabeledSliderDemo(int orientation)
	{
		/*
		 * create JSlider with ticks and labels
		 */
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Labelled"));
		JSlider slider = new JSlider(orientation,0,100,10);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(20);
		slider.setSnapToTicks(true);
		Hashtable<Integer,JLabel> table = new Hashtable<>();
		table.put(40,new JLabel("40"));
		table.put(60,new JLabel("60"));
		table.put(80,new JLabel("80"));
		slider.setLabelTable(table);
		slider.setPaintLabels(true);
		panel.add(slider,BorderLayout.CENTER);
		return panel;
	}
	
	static JPanel createCustomSliderDemo(int orientation)
	{
		/*
		 * create JSlider with changable background (opaque)
		 */
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Custom"));
		final JSlider slider = new JSlider(orientation,130,255,130);
		slider.setOpaque(true);
		slider.setPaintTicks(true);
		slider.setBackground(new Color(130,130,130));
		slider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent ev)
			{
				/*
				 * change slider background
				 */
				int value = slider.getValue();
				slider.setBackground(new Color(value,value,130));
			}
		});
		panel.add(slider,BorderLayout.CENTER);
		return panel;
	}
	
	static JPanel createDisabledSliderDemo(int orientation)
	{
		/*
		 * create disabled JSlider
		 */
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Disabled"));
		JSlider slider = new JSlider(orientation);
		slider.setEnabled(false);
		panel.add(slider);
		return panel;
	}
}
