/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class SpinnerDemo extends Tab
{
	SpinnerDemo()
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel center = new JPanel(new GridLayout(3,1,0,0));
		center.add(SpinnerDemo.createDefaultSpinnerPanel());
		center.add(SpinnerDemo.createColorShowPanel());
		center.add(SpinnerDemo.createDisabledPanel());
		this.add(center);
	}
	
	static JPanel createDefaultSpinnerPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Default"));
		panel.add(new JLabel("Intergers:"));
		panel.add(new JSpinner(new SpinnerNumberModel(20,0,100,1)));
		panel.add(new JLabel("   Decimals:"));
		panel.add(new JSpinner(new SpinnerNumberModel(12.3,0,100,0.1)));
		return panel;
	}
	
	static JPanel createColorShowPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Color chooser"));
		final JSpinner red = new JSpinner(new SpinnerNumberModel(255,0,255,1));
		final JSpinner green = new JSpinner(new SpinnerNumberModel(255,0,255,1));
		final JSpinner blue = new JSpinner(new SpinnerNumberModel(255,0,255,1));
		panel.add(new JLabel("(R,G,B)=("));
		panel.add(red);
		panel.add(new JLabel(","));
		panel.add(green);
		panel.add(new JLabel(","));
		panel.add(blue);
		panel.add(new JLabel(")"));
		final JPanel colored = new JPanel();
		colored.setPreferredSize(new Dimension(100,40));
		colored.setBackground(Color.WHITE);
		colored.setBorder(new TitledBorder("Color preview"));
		panel.add(colored);
		ChangeListener listener = new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent ev)
			{
				try
				{
					((JSpinner)(ev.getSource())).commitEdit();
				}
				catch (Exception ex)
				{
				}
				finally
				{
					int r = Integer.parseInt(red.getValue().toString());
					int g = Integer.parseInt(green.getValue().toString());
					int b = Integer.parseInt(blue.getValue().toString());
					colored.setBackground(new Color(r,g,b));
				}
			}
		};
		red.addChangeListener(listener);
		green.addChangeListener(listener);
		blue.addChangeListener(listener);
		return panel;
	}
	
	static JPanel createDisabledPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Disabled"));
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(79,0,10000,1));
		spinner.setEnabled(false);
		panel.add(spinner);
		return panel;
	}
}
