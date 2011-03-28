// Copyright distributed.net 1997-2002 - All Rights Reserved
// For use in distributed.net projects only.
// Any other distribution or use of this source violates copyright.
//

package net.distributed.javavis;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFrame;

class AboutDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private Image Cow;

    class OKButton extends JButton implements ActionListener
    {
		private static final long serialVersionUID = 1L;

		public OKButton()
        {
            super("OK");
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e)
        {
            AboutDialog.this.setVisible(false);
        }
    }

    AboutDialog(JFrame parent)
    {
        super(parent, "About this program", true);
        setSize(380,300);
        setLocation(50,50);
        setResizable(false);
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.left = 60;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(new JLabel("distributed.net Log Visualizer v1.6"), gbc);
        add(new JLabel("programmed by:"), gbc);
        add(new JLabel("  Jeff \"Bovine\" Lawson <jlawson@bovine.net>"), gbc);
        add(new JLabel("  William Goo <wgoo@hmc.edu>"), gbc);
        add(new JLabel("  Yves Hetzer <aetsch@gmx.de>"), gbc);
        add(new JLabel("  Greg Hewgill <greg@hewgill.com>"), gbc);
        add(new JLabel("  Jason Townsend <townsend@cs.stanford.edu>"), gbc);
        add(new JLabel("  Andy Hedges <andy@hedges.net>"), gbc);
        add(new JLabel("  Stanley Appel <s.appel@bigfoot.com>"), gbc);
        add(new JLabel(), gbc);
        gbc.insets.left = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(new OKButton(), gbc);

        URL res = getClass().getResource("cowhead.gif");
        if (res != null) {
            Cow = getToolkit().getImage(res);
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if (Cow != null) {
            g.drawImage(Cow, 15, 32, this);
        }
    }
}
