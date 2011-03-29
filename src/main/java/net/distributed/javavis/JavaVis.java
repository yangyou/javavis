// Copyright distributed.net 1997-2002- All Rights Reserved
// For use in distributed.net projects only.
// Any other distribution or use of this source violates copyright.
package net.distributed.javavis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;

// Main Frame
public class JavaVis extends JFrame
{
	private static final long serialVersionUID = 1L;
	final GraphPanel graphPanel = createComponents();
    final AboutDialog aboutDialog = new AboutDialog(this);
    final LogFileHistory lfh;
    JMenuItem refreshItem;
    static String[] arguments;

    // Constructor
    public JavaVis(String title)
    {
        // Parent Constructor
        super(title);

        lfh = LogFileHistory.open();
        //for (int i = 0; i < lfh.getFiles().length; i++) {
        //  System.out.println(lfh.getFiles()[i]);
        //}

        // Create Menu
        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Open log file...");
        final JFileChooser fileDialog = new JFileChooser();
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser
                fileDialog.showOpenDialog(menuBar);

                // In response to a button click:
                File file = fileDialog.getSelectedFile();
                if (file != null) {
                    lfh.addFile(file);
                    handleOpenFile(file);
                }

            }
        });
        menuItem.setMnemonic('0');
        menu.add(menuItem);

        refreshItem = new JMenuItem("Refresh");
        refreshItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphPanel.readLogData();
            }
        });
        refreshItem.setMnemonic('R');
        refreshItem.setEnabled(false);
        menu.add(refreshItem);
        menu.addSeparator();

        //add history files (if any)
        File[] files = lfh.getFiles();
        for(int i = 0; i< files.length; i++){
            if(files[i] != null){
                menuItem = new JMenuItem(files[i].toString());
                menu.add(menuItem);
                menu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                            handleOpenFile(new File(((JMenuItem)e.getSource()).getText()));
                    }
                });
            }
        }

        menuItem = new JMenuItem("Quit");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleQuit();
            }
        });
        menu.setMnemonic('Q');
        menu.add(menu);
            menu = new JMenu("Help");

            menuItem = new JMenuItem("About JavaVis...");
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleAbout();
                }
            });
            menu.setMnemonic('A');
            menu.add(menu);
            try {
                menuBar.setHelpMenu(menu);
                // avoid the double Help menu problem on Mac OS 8 and later
            } catch (Throwable thrown) {
                // in case we are on an older JDK which doesn't support this function
                // fall back on old strategy
                menuBar.add(menu);
            }
        
        //getContentPane().add(contents, BorderLayout.CENTER);
        setBackground(Color.lightGray);
        add(graphPanel, BorderLayout.CENTER);
        add("West",new leftPanel());
        add("South",new JLabel("Work Unit completion date",JLabel.CENTER));

        // Finish setting up the frame, and show it.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handleQuit();
            }
        });
        pack();
        setVisible(true);

        if (arguments.length >= 1) {
            handleOpenFile(new File(arguments[0]));
        }
        
    }

    public GraphPanel createComponents()
    {
        // Create an internal panel to hold the graph
        return new GraphPanel()
        {
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize()
            {
                return new Dimension(620,320);
            }
        };
    }

    public static void main(String[] args)
    {
        arguments = args;

		// Create the top-level container and add contents to it.
		new JavaVis("distributed.net Logfile Visualizer");
    }

    public void handleOpenFile(File file) {
        if (file.exists()) {
            graphPanel.currentLogFile = file;
            graphPanel.readLogData();
            refreshItem.setEnabled(true);
        }
    }

    public void handleAbout() {
        aboutDialog.setVisible(true);
    }

    public void handleQuit() {
        lfh.save();
        System.exit(0);
    }
}
