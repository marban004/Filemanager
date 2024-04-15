/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filemanager;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author marek
 */
public class Mainwindow implements ActionListener, ComponentListener{
    
    boolean warunek=false;
    boolean warunek2=true;
    boolean warunek3=false;
    boolean warunek4;
    String auxfilepathmove="";
    String filepathmove="";
    String currentfilepathmove="";
    String newname="";
    String newextension="";
    String auxfilepath="";
    String filepath="";
    String currentfilepath="";
    String content=null;
    JFrame frame = new JFrame("File manager");
    JButton listcontents = new JButton("Open (ENT)");
    JButton returntohigherdirectory = new JButton("Return to upper directory (BCS)");
    JButton rename = new JButton("Rename (F2)");
    JButton delete = new JButton("Delete (F8)");
    JButton createdir = new JButton("Create directory(F7)");
    JButton move = new JButton("Move (F6)");
    JButton copy = new JButton("Copy (F5)");
    JLabel pathtextbox = new JLabel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();
    JScrollPane pathtextboxscroll = new JScrollPane(pathtextbox);
    JScrollPane listscroll;
    JList<String> list = new JList<>();
    JList<String> listtomove = new JList<>();
    JScrollPane listtomovescroll;
    JMenuBar menuBar = new JMenuBar();
    File[] aux3=File.listRoots();
    JMenu leftPanel = new JMenu(aux3[0].getPath().replaceAll("\\\\","/"));
    JMenu rightPanel = new JMenu(aux3[0].getPath().replaceAll("\\\\","/"));
    JMenuItem drive;
    JMenuItem drive2;
    DefaultListModel<String> model=new DefaultListModel<>();
    DefaultListModel<String> model2=new DefaultListModel<>();
    String[] aux;
    String[] aux2;
    Action enterPressed;
    Action backspacePressed;
    Action F2Pressed;
    Action F8Pressed;
    Action F7Pressed;
    Action F6Pressed;
    Action TABPressed;
    Action F5Pressed;
    
    Mainwindow( String[] b, String c)
    {   
        this.filepath=c;
        this.currentfilepath=c;
        this.aux=b;
        this.filepathmove=this.filepath;
        this.auxfilepathmove=this.auxfilepathmove;
        this.currentfilepathmove=this.currentfilepath;
        this.warunek3=false;
        
        this.leftPanel.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent e) {
                if(e.getSource()==leftPanel)
                {
                warunek4=true;
                }
                if(e.getSource()==rightPanel)
                {
                warunek4=false;
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        }
        );
        
        this.rightPanel.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent e) {
                if(e.getSource()==leftPanel)
                {   
                warunek4=true;
                }
                if(e.getSource()==rightPanel)
                {
                warunek4=false;
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        }
        );
        
        this.menuBar.add(this.leftPanel);
        this.menuBar.add(this.rightPanel);
        
        
        File[] paths = File.listRoots();
        for(File path:paths)
        {
            String name = path.getPath();
            //name=name.replaceAll("\\\\","/");
            this.drive= new JMenuItem(new MenuItemSelected(name.replaceAll("\\\\","/")));
            
            this.leftPanel.add(this.drive);   
            this.drive= new JMenuItem(new MenuItemSelected(name.replaceAll("\\\\","/")));
            
            this.rightPanel.add(this.drive);
        }

        this.list.setModel(model);
        
        this.model.addElement("..");
        for(String plik:b)
        {
            this.model.addElement(plik);
        }
        this.list.getSelectionModel().addListSelectionListener(e -> 
        {
        if(!e.getValueIsAdjusting()){
        String plik=list.getSelectedValue();
        warunek2=true;
        
        if(this.filepath.equals(this.currentfilepath))
                {
                    if(new File(this.filepath).isFile())
                    {
                        this.filepath=this.filepath+plik;
                    }
                    else
                    {
                        this.filepath=this.filepath+plik+"/";
                    }
                }
                else
                {
                    StringBuilder input1 = new StringBuilder();
                    input1.append(this.filepath);
                    input1.reverse();
                    this.filepath=input1.toString();
                    if(this.filepath.charAt(0)=='/'){
                        this.filepath=this.filepath.substring(filepath.indexOf('/')+1);
                        this.filepath=this.filepath.substring(filepath.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.filepath);
                        input1.reverse();
                        this.filepath=input1.toString();
                    }
                    else
                    {
                        this.filepath=this.filepath.substring(filepath.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.filepath);
                        input1.reverse();
                        this.filepath=input1.toString();
                        
                    }
                        if(new File(this.filepath).isFile())
                        {
                            this.filepath=this.filepath+plik;
                        }
                        else
                        {
                            this.filepath=this.filepath+plik+"/";
                        }
                }
        
                if(new File(this.filepath).isFile())
                {
                    this.filepath=this.filepath.substring(0,(filepath.length())-1);
                }

                this.auxfilepath=this.filepath;
                if(!new File(this.auxfilepath).isDirectory() && !new File(this.auxfilepath).isFile())
                {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                this.auxfilepath=this.auxfilepath.substring(this.auxfilepath.indexOf('/')+1);
                if(this.filepath.indexOf('/')>-1)
                {
                this.auxfilepath=this.auxfilepath.substring(this.auxfilepath.indexOf('/'));
                }
                input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                }

                
                this.auxfilepath=this.auxfilepath.replaceAll("/", "\\\\");
                pathtextbox.setText(this.auxfilepath); 
                pathtextbox.repaint();
                this.auxfilepath=this.filepath;
                warunek=false;

        }
        }
        );
        
        this.listtomove.setModel(model2);
        
        this.model2.addElement("..");
        for(String plik:b)
        {
            this.model2.addElement(plik);
        }
        
        this.listtomove.getSelectionModel().addListSelectionListener(e -> 
        {
        if(!e.getValueIsAdjusting()){
        warunek2=false;
            
        String plik=listtomove.getSelectedValue();
        
        if(this.filepathmove.equals(this.currentfilepathmove))
                {
                    if(new File(this.filepathmove).isFile())
                    {
                        this.filepathmove=this.filepathmove+plik;
                    }
                    else
                    {
                        this.filepathmove=this.filepathmove+plik+"/";
                    }
                }
                else
                {
                    StringBuilder input1 = new StringBuilder();
                    input1.append(this.filepathmove);
                    input1.reverse();
                    this.filepathmove=input1.toString();
                    if(this.filepathmove.charAt(0)=='/'){
                        this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/')+1);
                        if(this.filepathmove.indexOf('/')>-1)
                        {
                        this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/'));
                        }
                        input1 = new StringBuilder();
                        input1.append(this.filepathmove);
                        input1.reverse();
                        this.filepathmove=input1.toString();
                    }
                    else
                    {
                        this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.filepathmove);
                        input1.reverse();
                        this.filepathmove=input1.toString();
                        
                    }
                        if(new File(this.filepathmove).isFile())
                        {
                            this.filepathmove=this.filepathmove+plik;
                        }
                        else
                        {
                            this.filepathmove=this.filepathmove+plik+"/";
                        }
                }
        
                if(new File(this.filepathmove).isFile())
                {
                    this.filepathmove=this.filepathmove.substring(0,(this.filepathmove.length())-1);
                }
                
                
                this.auxfilepathmove=this.filepathmove;
                if(!new File(this.auxfilepathmove).isDirectory() && !new File(this.auxfilepathmove).isFile())
                {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                this.auxfilepathmove=this.auxfilepathmove.substring(this.auxfilepathmove.indexOf('/')+1);
                if(this.auxfilepathmove.indexOf('/')>-1)
                {
                this.auxfilepathmove=this.auxfilepathmove.substring(this.auxfilepathmove.indexOf('/'));
                }
                input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                }
                
                this.auxfilepathmove=this.auxfilepathmove.replaceAll("/", "\\\\");
                this.pathtextbox.setText(this.auxfilepathmove); 
                this.pathtextbox.repaint();
                this.auxfilepathmove=this.filepathmove;
                this.warunek=false;
                this.warunek3=false;
                
        }
        }
        );
        
        this.listtomovescroll=new JScrollPane(listtomove);
        this.pathtextbox.setText(this.filepath);
        this.pathtextbox.setBackground(Color.white);
        this.pathtextbox.setOpaque(true);
        this.pathtextboxscroll.setBounds(0, 800, 250, 40);
        
        this.listscroll = new JScrollPane(list);
        this.splitPane.setLeftComponent(listscroll);
        
        this.splitPane.setRightComponent(listtomovescroll);
        //listtomovescroll.setEnabled(false);
        //listtomove.setEnabled(false);
        this.splitPane.setBounds(0, 0, 400, 800);
        this.splitPane.setDividerLocation(0.5);
        
        this.listcontents.setBounds(520,0,250,40);
        this.listcontents.setFocusable(false);
        this.listcontents.addActionListener(this);
        
        this.rename.setBounds(520,80,250,40);
        this.rename.setFocusable(false);
        this.rename.addActionListener(this);
        
        this.returntohigherdirectory.setBounds(520,40,250,40);
        this.returntohigherdirectory.setFocusable(false);
        this.returntohigherdirectory.addActionListener(this);
        
        this.delete.setBounds(520,240,250,40);
        this.delete.setFocusable(false);
        this.delete.addActionListener(this);
        
        this.createdir.setBounds(520,200,250,40);
        this.createdir.setFocusable(false);
        this.createdir.addActionListener(this);
        
        this.move.setBounds(520,160,250,40);
        this.move.setFocusable(false);
        this.move.addActionListener(this);
        
        this.copy.setBounds(520,120,250,40);
        this.copy.setFocusable(false);
        this.copy.addActionListener(this);
        
        this.enterPressed = new enterPressed();
        this.backspacePressed = new backspacePressed();
        this.F2Pressed = new F2Pressed();
        this.F8Pressed = new F8Pressed();
        this.F7Pressed = new F7Pressed();
        this.F6Pressed = new F6Pressed();
        this.TABPressed = new TABPressed();
        this.F5Pressed = new F5Pressed();
        
        this.list.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterKeyPressed");
        this.list.getActionMap().put("enterKeyPressed",this.enterPressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "backspaceKeyPressed");
        this.list.getActionMap().put("backspaceKeyPressed",this.backspacePressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("F2"), "F2KeyPressed");
        this.list.getActionMap().put("F2KeyPressed",this.F2Pressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("F8"), "F8KeyPressed");
        this.list.getActionMap().put("F8KeyPressed",this.F8Pressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("F7"), "F7KeyPressed");
        this.list.getActionMap().put("F7KeyPressed",this.F7Pressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("F6"),"F6KeyPressed");
        this.list.getActionMap().put("F6KeyPressed",this.F6Pressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("TAB"),"TABKeyPressed");
        this.list.getActionMap().put("TABKeyPressed",this.TABPressed);
        this.list.getInputMap().put(KeyStroke.getKeyStroke("F5"),"F5KeyPressed");
        this.list.getActionMap().put("F5KeyPressed",this.F5Pressed);
        
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterKeyPressed");
        this.listtomove.getActionMap().put("enterKeyPressed",this.enterPressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "backspaceKeyPressed");
        this.listtomove.getActionMap().put("backspaceKeyPressed",this.backspacePressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("F2"), "F2KeyPressed");
        this.listtomove.getActionMap().put("F2KeyPressed",this.F2Pressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("F8"), "F8KeyPressed");
        this.listtomove.getActionMap().put("F8KeyPressed",this.F8Pressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("F7"), "F7KeyPressed");
        this.listtomove.getActionMap().put("F7KeyPressed",this.F7Pressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("F6"),"F6KeyPressed");
        this.listtomove.getActionMap().put("F6KeyPressed",this.F6Pressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("TAB"),"TABKeyPressed");
        this.listtomove.getActionMap().put("TABKeyPressed",this.TABPressed);
        this.listtomove.getInputMap().put(KeyStroke.getKeyStroke("F5"),"F5KeyPressed");
        this.listtomove.getActionMap().put("F5KeyPressed",this.F5Pressed);
        
        this.frame.setJMenuBar(menuBar);
        this.frame.add(this.copy);
        this.frame.add(this.move);
        this.frame.add(this.createdir);
        this.frame.add(this.delete);
        this.frame.add(this.rename);
        this.frame.add(this.pathtextboxscroll);
        this.frame.add(this.listcontents);
        this.frame.add(this.returntohigherdirectory);
        this.frame.add(this.splitPane);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800,1000);
        this.frame.setLayout(null);
        this.frame.setVisible(true);
        
        //System.out.println(this.frame.getSize());
        this.copy.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.12), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.pathtextboxscroll.setBounds((int) (this.frame.getWidth()*0), (int) (this.frame.getHeight()*0.8), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.splitPane.setBounds((int) (this.frame.getWidth()*0.0), (int) (this.frame.getHeight()*0.0), (int) (this.frame.getWidth()*0.5), (int) (this.frame.getHeight()*0.8));
        this.listcontents.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.rename.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.08), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.returntohigherdirectory.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.04), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.delete.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.24), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.createdir.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.2), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.move.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.16), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        
        this.frame.getContentPane().addComponentListener(this);
    }
    /*class MenuItemSelected extends AbstractAction{
     
    public MenuItemSelected(String text){
        super(text);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Works");
    }   
     
}*/

    @Override
    public void componentResized(ComponentEvent e) {
        this.copy.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.12), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.pathtextboxscroll.setBounds((int) (this.frame.getWidth()*0), (int) (this.frame.getHeight()*0.8), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.splitPane.setBounds((int) (this.frame.getWidth()*0.0), (int) (this.frame.getHeight()*0.0), (int) (this.frame.getWidth()*0.5), (int) (this.frame.getHeight()*0.8));
        this.listcontents.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.rename.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.08), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.returntohigherdirectory.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.04), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.delete.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.24), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.createdir.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.2), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.move.setBounds((int) (this.frame.getWidth()*0.65), (int) (this.frame.getHeight()*0.16), (int) (this.frame.getWidth()*0.3125), (int) (this.frame.getHeight()*0.04));
        this.splitPane.setDividerLocation(0.5);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
    
    
    class MenuItemSelected extends AbstractAction{
     
    public MenuItemSelected(String text){
        super(text);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(warunek4)
        {
        Currentfoldercontents filecontents = new Currentfoldercontents(e.getActionCommand().replaceAll("\\\\", "/"));
        aux=filecontents.getContentsList();
        model=new DefaultListModel<>();
            list.setModel(model);
            model.addElement("..");
            for(String plik:aux)
            {
            model.addElement(plik);
            }
        filepath=e.getActionCommand().replaceAll("\\\\", "/");
        currentfilepath=e.getActionCommand().replaceAll("\\\\", "/");
        leftPanel.setText(e.getActionCommand());
        }
        else if(!warunek4)
        {
        Currentfoldercontents filecontents = new Currentfoldercontents(e.getActionCommand().replaceAll("\\\\", "/"));
        aux2=filecontents.getContentsList();
        model2=new DefaultListModel<>();
            listtomove.setModel(model2);
            model2.addElement("..");
            for(String plik1:aux2)
            {
            model2.addElement(plik1);
            }
        filepathmove=e.getActionCommand().replaceAll("\\\\", "/");
        currentfilepathmove=e.getActionCommand().replaceAll("\\\\", "/");
        rightPanel.setText(e.getActionCommand());
        }
    }   
     
}

    
    public class enterPressed extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            listcontents.doClick();
        }
    }
    
    public class backspacePressed extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            returntohigherdirectory.doClick();
        }
    }
    
    public class F2Pressed extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            rename.doClick();
        }
    }
    
    public class F8Pressed extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            delete.doClick();
        }
    }
    
    public class F7Pressed extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            createdir.doClick();
        }
    }
    
    public class F6Pressed extends AbstractAction
    {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            move.doClick();
        }
    }
    
    public class TABPressed extends AbstractAction
    {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            if(frame.getFocusOwner()==list)
            {
                listtomove.requestFocusInWindow();
            }
            else
            {
                list.requestFocusInWindow();
            }
        }
    }
    
    public class F5Pressed extends AbstractAction
    {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            copy.doClick();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==listcontents)
        {
            if(frame.getFocusOwner()==list && list.getSelectedValue()=="..")
            {
                returntohigherdirectory.doClick();
            }
            else if (frame.getFocusOwner()==listtomove && listtomove.getSelectedValue()=="..")
            {
                 returntohigherdirectory.doClick();
            }
            else if(frame.getFocusOwner()==list)
            {
                try {
                    this.auxfilepath=this.currentfilepath;
                    this.currentfilepath=this.filepath;
                    if(!new File(this.currentfilepath).toPath().toRealPath().toFile().isFile())
                    {
                    this.currentfilepath=new File(this.currentfilepath).toPath().toRealPath().toString().replaceAll("\\\\", "/")+"/";
                    }
                    else
                    {
                    this.currentfilepath=new File(this.currentfilepath).toPath().toRealPath().toString().replaceAll("\\\\", "/");
                    }
                    if(!new File(this.filepath).toPath().toRealPath().toFile().isFile())
                    {
                    this.filepath=new File(this.filepath).toPath().toRealPath().toString().replaceAll("\\\\", "/")+"/";
                    }
                    else
                    {
                    this.filepath=new File(this.filepath).toPath().toRealPath().toString().replaceAll("\\\\", "/");
                    }
                    //System.out.println(this.filepath+"1");
                    //System.out.println(this.currentfilepath+"1");
                    if(new File(this.filepath).isFile() || warunek)
                    {
                        try {
                            Desktop desktop = Desktop.getDesktop();
                            if(!warunek)
                            {
                                desktop.open(new File(this.filepath));
                            }
                            if(warunek)
                            {
                                JOptionPane.showMessageDialog(frame,"File cannot be opened","Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame,"File cannot be opened","Error", JOptionPane.ERROR_MESSAGE);
                            warunek=true;
                        }
                        //System.out.println(this.filepath+"2");
                        //System.out.println(this.currentfilepath+"2");
                        if(this.currentfilepath.matches("[A-Z]:/[^/]+") && this.filepath.matches("[A-Z]:/[^/]+"))
                        {
                            StringBuilder input1 = new StringBuilder();
                            input1.append(this.filepath);
                            input1.reverse();
                            this.filepath=input1.toString();
                            this.filepath=this.filepath.substring(this.filepath.indexOf('/'));
                            input1 = new StringBuilder();
                            input1.append(this.filepath);
                            input1.reverse();
                            this.filepath=input1.toString();
                            
                            input1 = new StringBuilder();
                            input1.append(this.currentfilepath);
                            input1.reverse();
                            this.currentfilepath=input1.toString();
                            this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                            input1 = new StringBuilder();
                            input1.append(this.currentfilepath);
                            input1.reverse();
                            this.currentfilepath=input1.toString();
                        }
                        //System.out.println(this.filepath+"3");
                        //System.out.println(this.currentfilepath+"3");
                    }
                    else if(new File(this.filepath).isDirectory() && !this.auxfilepath.equals(this.filepath))
                    {
                        Currentfoldercontents filecontents = new Currentfoldercontents(this.filepath);
                        this.aux=filecontents.getContentsList();
                        this.model=new DefaultListModel<>();
                        this.list.setModel(model);
                        this.model.addElement("..");
                        for(String plik:aux)
                        {
                            this.model.addElement(plik);
                        }
                        //System.out.println(this.filepath+"4");
                        //System.out.println(this.currentfilepath+"4");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame,"Nothing selected","Info", JOptionPane.INFORMATION_MESSAGE);
                        if(!new File(this.filepath).isDirectory() && !new File(this.filepath).isFile())
                        {
                            StringBuilder input1 = new StringBuilder();
                            input1.append(this.filepath);
                            input1.reverse();
                            this.filepath=input1.toString();
                            this.filepath=this.filepath.substring(this.filepath.indexOf('/')+1);
                            this.filepath=this.filepath.substring(this.filepath.indexOf('/'));
                            input1 = new StringBuilder();
                            input1.append(this.filepath);
                            input1.reverse();
                            this.filepath=input1.toString();
                        }
                        //System.out.println(this.filepath+"5");
                        //System.out.println(this.currentfilepath+"5");
                    }
                    if(!new File(this.filepath).isDirectory() && !new File(this.filepath).isFile())
                    {
                        StringBuilder input1 = new StringBuilder();
                        input1.append(this.filepath);
                        input1.reverse();
                        this.filepath=input1.toString();
                        this.filepath=this.filepath.substring(this.filepath.indexOf('/')+1);
                        this.filepath=this.filepath.substring(this.filepath.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.filepath);
                        input1.reverse();
                        this.filepath=input1.toString();
                    }
                    //System.out.println(this.filepath+"6");
                    //System.out.println(this.currentfilepath+"6");
                    this.currentfilepath=this.filepath;
                    if(new File(this.currentfilepath).isFile())
                    {
                        StringBuilder input1 = new StringBuilder();
                        input1.append(this.currentfilepath);
                        input1.reverse();
                        this.currentfilepath=input1.toString();
                        this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.currentfilepath);
                        input1.reverse();
                        this.currentfilepath=input1.toString();
                    }
                    //System.out.println(this.filepath+"7");
                    //System.out.println(this.currentfilepath+"7");
                    Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                    this.aux=filecontents.getContentsList();
                    this.model= new DefaultListModel<>();
                    this.list.setModel(this.model);
                    this.model.addElement("..");
                    for(String plik:this.aux)
                    {
                        this.model.addElement(plik);
                    }
                    filecontents = new Currentfoldercontents(this.currentfilepathmove);
                    this.aux2=filecontents.getContentsList();
                    this.model2= new DefaultListModel<>();
                    this.listtomove.setModel(model2);
                    this.model2.addElement("..");
                    for(String plik1:this.aux2)
                    {
                        this.model2.addElement(plik1);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Mainwindow.class.getName()).log(Level.SEVERE, null, ex);
                } finally
                {}
                //System.out.println(this.filepath+"8");
                //System.out.println(this.currentfilepath+"8");
           }
            else if(frame.getFocusOwner()==listtomove)
            {   
                try {
                    this.auxfilepathmove=this.currentfilepathmove;
                    this.currentfilepathmove=this.filepathmove;
                    if(!new File(this.currentfilepathmove).toPath().toRealPath().toFile().isFile())
                    {
                        this.currentfilepathmove=new File(this.currentfilepathmove).toPath().toRealPath().toString().replaceAll("\\\\", "/")+"/";
                    }
                    else
                    {
                        this.currentfilepathmove=new File(this.currentfilepathmove).toPath().toRealPath().toString().replaceAll("\\\\", "/");
                    }
                    if(!new File(this.filepathmove).toPath().toRealPath().toFile().isFile())
                    {
                        this.filepathmove=new File(this.filepathmove).toPath().toRealPath().toString().replaceAll("\\\\", "/")+"/";
                    }
                    else
                    {
                        this.filepathmove=new File(this.filepathmove).toPath().toRealPath().toString().replaceAll("\\\\", "/");
                    }
                    if(new File(this.filepathmove).isFile() || warunek)
                    {
                        try {
                            Desktop desktop = Desktop.getDesktop();
                            if(!warunek)
                            {
                                desktop.open(new File(this.filepathmove));
                            }
                            if(warunek)
                            {
                                JOptionPane.showMessageDialog(frame,"File cannot be opened","Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame,"File cannot be opened","Error", JOptionPane.ERROR_MESSAGE);
                            warunek=true;
                        }
                        if(this.currentfilepathmove.matches("[A-Z]:/[^/]+") && this.filepathmove.matches("[A-Z]:/[^/]+"))
                        {
                            StringBuilder input1 = new StringBuilder();
                            input1.append(this.filepathmove);
                            input1.reverse();
                            this.filepathmove=input1.toString();
                            this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/'));
                            input1 = new StringBuilder();
                            input1.append(this.filepathmove);
                            input1.reverse();
                            this.filepathmove=input1.toString();
                            
                            input1 = new StringBuilder();
                            input1.append(this.currentfilepathmove);
                            input1.reverse();
                            this.currentfilepathmove=input1.toString();
                            this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                            input1 = new StringBuilder();
                            input1.append(this.currentfilepathmove);
                            input1.reverse();
                            this.currentfilepathmove=input1.toString();
                        }
                    }
                    else if(new File(this.filepathmove).isDirectory() && !this.auxfilepathmove.equals(this.filepathmove))
                    {
                        Currentfoldercontents filecontents = new Currentfoldercontents(this.filepathmove);
                        this.aux2=filecontents.getContentsList();
                        this.model2=new DefaultListModel<>();
                        this.listtomove.setModel(this.model2);
                        this.model2.addElement("..");
                        for(String plik1:this.aux2)
                        {
                            this.model2.addElement(plik1);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame,"Nothing selected","Info", JOptionPane.INFORMATION_MESSAGE);
                        if(!new File(this.filepathmove).isDirectory() && !new File(this.filepathmove).isFile())
                        {
                            StringBuilder input1 = new StringBuilder();
                            input1.append(this.filepathmove);
                            input1.reverse();
                            this.filepathmove=input1.toString();
                            this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/')+1);
                            this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/'));
                            input1 = new StringBuilder();
                            input1.append(this.filepathmove);
                            input1.reverse();
                            this.filepathmove=input1.toString();
                        }
                    }
                    if(!new File(this.filepathmove).isDirectory() && !new File(this.filepathmove).isFile())
                    {
                        StringBuilder input1 = new StringBuilder();
                        input1.append(this.filepathmove);
                        input1.reverse();
                        this.filepathmove=input1.toString();
                        this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/')+1);
                        this.filepathmove=this.filepathmove.substring(this.filepathmove.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.filepathmove);
                        input1.reverse();
                        this.filepathmove=input1.toString();
                    }
                    this.currentfilepathmove=this.filepathmove;
                    if(new File(this.currentfilepathmove).isFile())
                    {
                        StringBuilder input1 = new StringBuilder();
                        input1.append(this.currentfilepathmove);
                        input1.reverse();
                        this.currentfilepathmove=input1.toString();
                        this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                        input1 = new StringBuilder();
                        input1.append(this.currentfilepathmove);
                        input1.reverse();
                        this.currentfilepathmove=input1.toString();
                    }
                    Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                    this.aux=filecontents.getContentsList();
                    this.model= new DefaultListModel<>();
                    this.list.setModel(this.model);
                    this.model.addElement("..");
                    for(String plik:this.aux)
                    {
                        this.model.addElement(plik);
                    }
                    filecontents = new Currentfoldercontents(this.currentfilepathmove);
                    this.aux2=filecontents.getContentsList();
                    this.model2= new DefaultListModel<>();
                    this.listtomove.setModel(model2);
                    this.model2.addElement("..");
                    for(String plik1:this.aux2)
                    {
                        this.model2.addElement(plik1);
                    }
                } catch (IOException ex) {
                }
            }
        }
        if(e.getSource()==returntohigherdirectory)
        {
            
            if(frame.getFocusOwner()==list)
            {
                
            this.auxfilepath=this.currentfilepath;
            StringBuilder input1 = new StringBuilder();
            input1.append(this.auxfilepath);
            input1.reverse();
            this.auxfilepath=input1.toString();
            //usuwanie null/
            if(!new File(this.currentfilepath).isDirectory() && !new File(this.currentfilepath).isFile())
                {
                input1 = new StringBuilder();
                input1.append(this.currentfilepath);
                input1.reverse();
                this.currentfilepath=input1.toString();
                this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/')+1);
                this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                input1 = new StringBuilder();
                input1.append(this.currentfilepath);
                input1.reverse();
                this.currentfilepath=input1.toString();
                }
            if(!new File(this.currentfilepath).isFile() && !new File(this.currentfilepath).isDirectory())
            {
            this.auxfilepath=this.auxfilepath.substring(5);
            }
            input1 = new StringBuilder();
            input1.append(this.auxfilepath);
            input1.reverse();
            this.auxfilepath=input1.toString();
            
            if(!this.currentfilepath.matches("[A-Z]:/"))
            {
            input1 = new StringBuilder();
            input1.append(this.currentfilepath);
            input1.reverse();
            this.currentfilepath=input1.toString();
            if(this.currentfilepath.charAt(0)=='/')
                    {
                        this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/')+1);
                        if(this.currentfilepath.indexOf('/')>-1)
                        {
                        this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                        }
                        input1 = new StringBuilder();
                        input1.append(this.currentfilepath);
                        input1.reverse();
                        this.currentfilepath=input1.toString();
                    }
                    else
                    {
                        this.currentfilepath=this.currentfilepath.substring( this.currentfilepath.indexOf('/')+1);
                        if(currentfilepath.indexOf('/')>-1)
                        {
                        this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                        }
                        else
                        {
                        this.currentfilepath=this.currentfilepath+"/";
                        }
                        input1 = new StringBuilder();
                        input1.append(this.currentfilepath);
                        input1.reverse();
                        this.currentfilepath=input1.toString();
                    }
            this.filepath=this.currentfilepath;
            }
            else
            {
                JOptionPane.showMessageDialog(this.frame,"You are in the main directory","Info", JOptionPane.INFORMATION_MESSAGE);
            }
            this.filepath=this.auxfilepath;
        }
            else if(frame.getFocusOwner()==listtomove)
        {   
            this.auxfilepathmove=this.currentfilepathmove;
            StringBuilder input1 = new StringBuilder();
            input1.append(this.auxfilepathmove);
            input1.reverse();
            this.auxfilepathmove=input1.toString();
            //usuwanie null/
            if(!new File(this.currentfilepathmove).isDirectory() && !new File(this.currentfilepathmove).isFile())
                {
                input1 = new StringBuilder();
                input1.append(this.currentfilepathmove);
                input1.reverse();
                this.currentfilepathmove=input1.toString();
                this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/')+1);
                this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                input1 = new StringBuilder();
                input1.append(this.currentfilepathmove);
                input1.reverse();
                this.currentfilepathmove=input1.toString();
                }
            
            if(!new File(this.currentfilepathmove).isFile() && !new File(this.currentfilepathmove).isDirectory())
            {
            this.auxfilepathmove=this.auxfilepathmove.substring(5);
            }
            input1 = new StringBuilder();
            input1.append(this.auxfilepathmove);
            input1.reverse();
            this.auxfilepathmove=input1.toString();
            
            if(!this.currentfilepathmove.matches("[A-Z]:/"))
            {
            input1 = new StringBuilder();
            input1.append(this.currentfilepathmove);
            input1.reverse();
            this.currentfilepathmove=input1.toString();
            if(this.currentfilepathmove.charAt(0)=='/')
                    {
                        this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/')+1);
                        if(this.currentfilepathmove.indexOf('/')>-1)
                        {
                        this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                        }
                        input1 = new StringBuilder();
                        input1.append(this.currentfilepathmove);
                        input1.reverse();
                        this.currentfilepathmove=input1.toString();
                    }
                    else
                    {
                        this.currentfilepathmove=this.currentfilepathmove.substring( this.currentfilepathmove.indexOf('/')+1);
                        if(currentfilepathmove.indexOf('/')>-1)
                        {
                        this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                        }
                        else
                        {
                        this.currentfilepathmove=this.currentfilepathmove+"/";
                        }
                        input1 = new StringBuilder();
                        input1.append(this.currentfilepathmove);
                        input1.reverse();
                        this.currentfilepathmove=input1.toString();
                    }
            this.filepathmove=this.currentfilepathmove;
            Currentfoldercontents filecontents = new Currentfoldercontents(this.filepathmove);
            this.aux2=filecontents.getContentsList();
            this.model2=new DefaultListModel<>();
            this.listtomove.setModel(this.model2);
            this.model2.addElement("..");
        for(String plik1:aux2)
        {
            this.model2.addElement(plik1);
        }
        }
        else
        {
            JOptionPane.showMessageDialog(frame,"You are in the main directory","Info", JOptionPane.INFORMATION_MESSAGE);
        }

        }

            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        }
        
        if(e.getSource()==rename)
        {
        
        if(frame.getFocusOwner()==list && !(list.getSelectedValue()==".."))
        {
        this.listtomove.setEnabled(false);
        this.list.setEnabled(false);
        this.listcontents.setEnabled(false);
        this.returntohigherdirectory.setEnabled(false);
        this.rename.setEnabled(false);
        this.delete.setEnabled(false);
        this.createdir.setEnabled(false);
        this.move.setEnabled(false);
        this.copy.setEnabled(false);
        try
        {
            this.auxfilepath=this.filepath;
            if(this.auxfilepath.matches(".+null/")&&!new File(this.auxfilepath).exists())
            {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                this.auxfilepath=this.auxfilepath.substring(5);
                input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                
            }
            if(this.auxfilepath.equals(this.currentfilepath))
            {
                throw new IOException();
            }
            this.newname=JOptionPane.showInputDialog("Please write new name");
            
            if(this.auxfilepath.matches(".+\\.lnk") && new File(this.auxfilepath).isFile())
            {
            this.newextension=JOptionPane.showInputDialog("Please write new extension");       
            if(this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+")&&this.newextension.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                this.newname=this.newname+"."+this.newextension+".lnk";
            }
            else
            {
                throw new Exception();
            }
            }
            else if(!this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                throw new Exception();
            }
            else if(new File(this.auxfilepath).isFile())
            {
            this.newextension=JOptionPane.showInputDialog("Please write new extension");       
            if(this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+")&&this.newextension.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                this.newname=this.newname+"."+this.newextension;
            }
            else
            {
                throw new Exception();
            }
            }
            else if(!this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                throw new Exception();
            }
            
            
     
            if(new File(this.currentfilepath+this.newname).exists())
            {
                throw new Exception();
            }
            boolean result=new File(this.auxfilepath).renameTo(new File(this.currentfilepath+this.newname));
            if(result)
            {            
            JOptionPane.showMessageDialog(frame,"Name has been changed","Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
            JOptionPane.showMessageDialog(frame,"Name cannot be changed","Error", JOptionPane.ERROR_MESSAGE);
            }
            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
            this.aux=filecontents.getContentsList();
            this.model=new DefaultListModel<>();
            this.list.setModel(this.model);
            this.model.addElement("..");
            for(String plik:this.aux)
            {
                this.model.addElement(plik);
            }
        }
        catch(IOException exception)
        {
            JOptionPane.showMessageDialog(frame,"Please select directory or file to change name","Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(frame,"Please provide a suitable name","Error", JOptionPane.ERROR_MESSAGE);
        }
        
        this.listtomove.setEnabled(true);
        this.list.setEnabled(true);
        this.listcontents.setEnabled(true);
        this.returntohigherdirectory.setEnabled(true);
        this.rename.setEnabled(true);
        this.delete.setEnabled(true);
        this.createdir.setEnabled(true);
        this.move.setEnabled(true);
        this.copy.setEnabled(true);
        Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        this.list.requestFocusInWindow();
        }
        else if(frame.getFocusOwner()==listtomove && !(listtomove.getSelectedValue()==".."))
        {
        this.listtomove.setEnabled(false);
        this.list.setEnabled(false);
        this.listcontents.setEnabled(false);
        this.returntohigherdirectory.setEnabled(false);
        this.rename.setEnabled(false);
        this.delete.setEnabled(false);
        this.createdir.setEnabled(false);
        this.move.setEnabled(false);
        this.copy.setEnabled(false);
            try
        {
            this.auxfilepathmove=this.filepathmove;
            if(this.auxfilepathmove.matches(".+null/")&&!new File(this.auxfilepathmove).exists())
            {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                this.auxfilepathmove=this.auxfilepathmove.substring(5);
                input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                
            }
            if(this.auxfilepathmove.equals(this.currentfilepathmove))
            {
                throw new IOException();
            }
            this.newname=JOptionPane.showInputDialog("Please write new name");
            
            if(this.auxfilepathmove.matches(".+\\.lnk") && new File(this.auxfilepathmove).isFile())
            {
            this.newextension=JOptionPane.showInputDialog("Please write new extension");       
            if(this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+")&&this.newextension.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                this.newname=this.newname+"."+this.newextension+".lnk";
            }
            else
            {
                throw new Exception();
            }
            }
            else if(!this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                throw new Exception();
            }
            else if(new File(this.auxfilepathmove).isFile())
            {
            this.newextension=JOptionPane.showInputDialog("Please write new extension");       
            if(this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+")&&this.newextension.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                this.newname=this.newname+"."+this.newextension;
            }
            else
            {
                throw new Exception();
            }
            }
            else if(!this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                throw new Exception();
            }
     
            if(new File(this.currentfilepathmove+this.newname).exists())
            {
                throw new Exception();
            }
            boolean result=new File(this.auxfilepathmove).renameTo(new File(this.currentfilepathmove+this.newname));
            if(result)
            {            
            JOptionPane.showMessageDialog(frame,"Name has been changed","Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
            JOptionPane.showMessageDialog(frame,"Name cannot be changed","Error", JOptionPane.ERROR_MESSAGE);
            }
            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepathmove);
            this.aux2=filecontents.getContentsList();
            this.model2=new DefaultListModel<>();
            this.listtomove.setModel(this.model2);
            this.model2.addElement("..");
            for(String plik1:this.aux2)
            {
                this.model2.addElement(plik1);
            }
        }
        catch(IOException exception)
        {
            JOptionPane.showMessageDialog(frame,"Please select directory or file to change name","Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(frame,"Please provide a suitable name","Error", JOptionPane.ERROR_MESSAGE);
        }
        
        this.listtomove.setEnabled(true);
        this.list.setEnabled(true);
        this.listcontents.setEnabled(true);
        this.returntohigherdirectory.setEnabled(true);
        this.rename.setEnabled(true);
        this.delete.setEnabled(true);
        this.createdir.setEnabled(true);
        this.move.setEnabled(true);
        this.copy.setEnabled(true);
        Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        this.listtomove.requestFocusInWindow();
        }
        }
        
        if(e.getSource()==delete)
        {
        
        if(frame.getFocusOwner()==list && !(list.getSelectedValue()==".."))
        {
        this.listtomove.setEnabled(false);
        this.list.setEnabled(false);
        this.listcontents.setEnabled(false);
        this.returntohigherdirectory.setEnabled(false);
        this.rename.setEnabled(false);
        this.delete.setEnabled(false);
        this.createdir.setEnabled(false);
        this.move.setEnabled(false);
        this.copy.setEnabled(false);
        try
        {
            this.auxfilepath=this.filepath;
            if(this.auxfilepath.matches(".+null/")&&!new File(this.auxfilepath).exists())
            {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                this.auxfilepath=this.auxfilepath.substring(5);
                input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                
            }
            if(this.auxfilepath.equals(this.currentfilepath))
            {
                throw new IOException();
            }
            int answear;
            answear=JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this? (If you are deleting directory all data inside will be deleted too)", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(answear==0)
            {
            deleteDirectoryRecursion(Paths.get(this.filepath));
            JOptionPane.showMessageDialog(frame,"Deleted succesfully","Info", JOptionPane.INFORMATION_MESSAGE);
            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
            this.aux=filecontents.getContentsList();
            this.model=new DefaultListModel<>();
            this.list.setModel(this.model);
            this.model.addElement("..");
            for(String plik:this.aux)
            {
                this.model.addElement(plik);
            }
            }
            else
            {
                JOptionPane.showMessageDialog(frame,"Deleting aborted","Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(IOException exception)
        {
        JOptionPane.showMessageDialog(frame,"Please select directory or file to delete","Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(frame,"Failed to delete","Error", JOptionPane.ERROR_MESSAGE);
        }
        this.listtomove.setEnabled(true);
        this.list.setEnabled(true);
        this.listcontents.setEnabled(true);
        this.returntohigherdirectory.setEnabled(true);
        this.rename.setEnabled(true);
        this.delete.setEnabled(true);
        this.createdir.setEnabled(true);
        this.move.setEnabled(true);
        this.copy.setEnabled(true);
        
        Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        this.list.requestFocusInWindow();
        }
        else if(frame.getFocusOwner()==listtomove && !(listtomove.getSelectedValue()==".."))
        {
        this.listtomove.setEnabled(false);
        this.list.setEnabled(false);
        this.listcontents.setEnabled(false);
        this.returntohigherdirectory.setEnabled(false);
        this.rename.setEnabled(false);
        this.delete.setEnabled(false);
        this.createdir.setEnabled(false);
        this.move.setEnabled(false);
        this.copy.setEnabled(false);
        try
        {
            this.auxfilepathmove=this.filepathmove;
            if(this.auxfilepathmove.matches(".+null/")&&!new File(this.auxfilepathmove).exists())
            {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                this.auxfilepathmove=this.auxfilepathmove.substring(5);
                input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                
            }
            if(this.auxfilepathmove.equals(this.currentfilepathmove))
            {
                throw new IOException();
            }
            int answear;
            answear=JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this? (If you are deleting directory all data inside will be deleted too)", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(answear==0)
            {
            deleteDirectoryRecursion(Paths.get(this.filepathmove));
            JOptionPane.showMessageDialog(frame,"Deleted succesfully","Info", JOptionPane.INFORMATION_MESSAGE);
            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepathmove);
            this.aux2=filecontents.getContentsList();
            this.model2=new DefaultListModel<>();
            this.listtomove.setModel(this.model2);
            this.model2.addElement("..");
            for(String plik1:this.aux2)
            {
                this.model2.addElement(plik1);
            }
            }
            else
            {
                JOptionPane.showMessageDialog(frame,"Deleting aborted","Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(IOException exception)
        {
        JOptionPane.showMessageDialog(frame,"Please select directory or file to delete","Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(frame,"Failed to delete","Error", JOptionPane.ERROR_MESSAGE);
        }
        this.listtomove.setEnabled(true);
        this.list.setEnabled(true);
        this.listcontents.setEnabled(true);
        this.returntohigherdirectory.setEnabled(true);
        this.rename.setEnabled(true);
        this.delete.setEnabled(true);
        this.createdir.setEnabled(true);
        this.move.setEnabled(true);
        this.copy.setEnabled(true);
        
        Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        this.listtomove.requestFocusInWindow();
        }
        }
        
        if(e.getSource()==createdir)
        {
        if(frame.getFocusOwner()==list && !(list.getSelectedValue()==".."))
        {
        this.listtomove.setEnabled(false);
        this.list.setEnabled(false);
        this.listcontents.setEnabled(false);
        this.returntohigherdirectory.setEnabled(false);
        this.rename.setEnabled(false);
        this.delete.setEnabled(false);
        this.createdir.setEnabled(false);
        this.move.setEnabled(false);
        this.copy.setEnabled(false);
        try
        {
            this.auxfilepath=this.currentfilepath;
            if(this.auxfilepath.matches(".+null/")&&!new File(this.auxfilepath).exists())
            {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                this.auxfilepath=this.auxfilepath.substring(5);
                input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();  
            }
            this.newname=JOptionPane.showInputDialog("Please write new name");
            /*if(new File(this.auxfilepath).isFile())
            {
            this.newextension=JOptionPane.showInputDialog("Please write new extension");       
            if(this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+")&&this.newextension.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                this.newname=this.newname+"."+this.newextension;
            }
            else
            {
                throw new Exception();
            }
            }*/
            if(!this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                throw new Exception();
            }
     
            if(new File(this.auxfilepath+this.newname).exists())
            {
                throw new Exception();
            }
            boolean result=new File(this.auxfilepath+this.newname).mkdirs();
            if(result)
            {            
            JOptionPane.showMessageDialog(frame,"Directory has been created","Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
            JOptionPane.showMessageDialog(frame,"Directory cannot be created","Error", JOptionPane.ERROR_MESSAGE);
            }
            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
            this.aux=filecontents.getContentsList();
            this.model=new DefaultListModel<>();
            this.list.setModel(this.model);
            this.model.addElement("..");
            for(String plik:this.aux)
            {
                this.model.addElement(plik);
            }
        }
        catch(IOException exception)
        {
            JOptionPane.showMessageDialog(frame,"Please select directory or file to change name","Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(frame,"Please provide a suitable name","Error", JOptionPane.ERROR_MESSAGE);
        }
        this.listtomove.setEnabled(true);
        this.list.setEnabled(true);
        this.listcontents.setEnabled(true);
        this.returntohigherdirectory.setEnabled(true);
        this.rename.setEnabled(true);
        this.delete.setEnabled(true);
        this.createdir.setEnabled(true);
        this.move.setEnabled(true);
        this.copy.setEnabled(true);
        this.list.requestFocusInWindow();
                Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        }
        else if(frame.getFocusOwner()==listtomove && !(listtomove.getSelectedValue()==".."))
        {
        this.listtomove.setEnabled(false);
        this.list.setEnabled(false);
        this.listcontents.setEnabled(false);
        this.returntohigherdirectory.setEnabled(false);
        this.rename.setEnabled(false);
        this.delete.setEnabled(false);
        this.createdir.setEnabled(false);
        this.move.setEnabled(false);
        this.copy.setEnabled(false);
        try
        {
            this.auxfilepathmove=this.currentfilepathmove;
            if(this.auxfilepathmove.matches(".+null/")&&!new File(this.auxfilepathmove).exists())
            {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                this.auxfilepathmove=this.auxfilepathmove.substring(5);
                input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();  
            }
            this.newname=JOptionPane.showInputDialog("Please write new name");
            /*if(new File(this.auxfilepath).isFile())
            {
            this.newextension=JOptionPane.showInputDialog("Please write new extension");       
            if(this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+")&&this.newextension.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                this.newname=this.newname+"."+this.newextension;
            }
            else
            {
                throw new Exception();
            }
            }*/
            if(!this.newname.matches("[^\\/\\\\\\?\\%\\*\\:\\|\\\"\\<\\>\\.\\,\\;\\=\\ ]+"))
            {
                throw new Exception();
            }
     
            if(new File(this.auxfilepath+this.newname).exists())
            {
                throw new Exception();
            }
            boolean result=new File(this.auxfilepathmove+this.newname).mkdirs();
            if(result)
            {            
            JOptionPane.showMessageDialog(frame,"Directory has been created","Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
            JOptionPane.showMessageDialog(frame,"Directory cannot be created","Error", JOptionPane.ERROR_MESSAGE);
            }
            Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepathmove);
            this.aux2=filecontents.getContentsList();
            this.model2=new DefaultListModel<>();
            this.listtomove.setModel(this.model2);
            this.model2.addElement("..");
            for(String plik1:this.aux2)
            {
                this.model2.addElement(plik1);
            }
        }
        catch(IOException exception)
        {
            JOptionPane.showMessageDialog(frame,"Please select directory or file to change name","Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exception)
        {
            JOptionPane.showMessageDialog(frame,"Please provide a suitable name","Error", JOptionPane.ERROR_MESSAGE);
        }
        this.listtomove.setEnabled(true);
        this.list.setEnabled(true);
        this.listcontents.setEnabled(true);
        this.returntohigherdirectory.setEnabled(true);
        this.rename.setEnabled(true);
        this.delete.setEnabled(true);
        this.createdir.setEnabled(true);
        this.move.setEnabled(true);
        this.copy.setEnabled(true);
        this.list.requestFocusInWindow();
                Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        }
        }
        
        if(e.getSource()==move)
        {
            if(frame.getFocusOwner()==list && !(list.getSelectedValue()==".."))
            {
            try
            {
            this.listtomove.setEnabled(false);
            this.listtomove.setEnabled(false);
            this.list.setEnabled(false);
            this.listcontents.setEnabled(false);
            this.returntohigherdirectory.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.move.setEnabled(false);
            this.copy.setEnabled(false);
            int answear;
            if(this.auxfilepath.equals(this.currentfilepath))
            {
                throw new IOException();
            }
            answear=JOptionPane.showConfirmDialog(frame, "Are you sure you want to move this? (If you are moving directory all data inside will be moved too)", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(answear!=0)
            {
            throw new ZipException();
            }
            this.auxfilepath=this.filepath;
            if(this.auxfilepath.matches(".+null/")&&!new File(this.auxfilepath).exists())
            {
            StringBuilder input1 = new StringBuilder();
            input1.append(this.auxfilepath);
            input1.reverse();
            this.auxfilepath=input1.toString();
            this.auxfilepath=this.auxfilepath.substring(5);
            input1 = new StringBuilder();
            input1.append(this.auxfilepath);
            input1.reverse();
            this.auxfilepath=input1.toString();
                
            }
            
            /*this.list.setEnabled(false);
            this.listscroll.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.listtomove.setEnabled(true);
            this.listtomovescroll.setEnabled(true);*/
            this.warunek2=false;
            
            
                if(!new File(this.currentfilepathmove).isDirectory() && !new File(this.currentfilepathmove).isFile() && !new File(this.currentfilepathmove).exists())
                {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.currentfilepathmove);
                input1.reverse();
                this.currentfilepathmove=input1.toString();
                this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/')+1);
                this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                input1 = new StringBuilder();
                input1.append(this.currentfilepathmove);
                input1.reverse();
                this.currentfilepathmove=input1.toString();
                }
                this.auxfilepath=this.filepath;
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                this.auxfilepath=this.auxfilepath.substring(0, this.auxfilepath.indexOf('/',1));
                input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                
                   if(new File(this.filepath).isDirectory())
                {
                   moveFolder(Paths.get(this.filepath),Paths.get(this.currentfilepathmove+this.auxfilepath));
                }
                else if(new File(this.filepath).isFile())
                {
                    moveFile(new File(this.filepath),new File(this.currentfilepathmove+this.auxfilepath));
                }
                   JOptionPane.showMessageDialog(frame,"Succesfully moved","Info", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(ZipException exception)
                {
                JOptionPane.showMessageDialog(frame,"Moving aborted","Info", JOptionPane.INFORMATION_MESSAGE);
                }            
                catch(IOException exception)
                {
                JOptionPane.showMessageDialog(frame,"Please select directory or file to move","Info", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
                }
                catch(Exception exception)
                {
                JOptionPane.showMessageDialog(frame,"Failed to move file/directory","Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
                }
            this.listtomove.setEnabled(true);
                this.list.setEnabled(true);
                this.listcontents.setEnabled(true);
                this.returntohigherdirectory.setEnabled(true);
                this.rename.setEnabled(true);
                this.delete.setEnabled(true);
                this.createdir.setEnabled(true);
                this.move.setEnabled(true);
                this.copy.setEnabled(true);
                Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        }
            else if(frame.getFocusOwner()==listtomove && !(listtomove.getSelectedValue()==".."))
            {
                try
            {
            this.listtomove.setEnabled(false);
            this.list.setEnabled(false);
            this.listcontents.setEnabled(false);
            this.returntohigherdirectory.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.move.setEnabled(false);
            this.copy.setEnabled(false);
            int answear;
            if(this.auxfilepathmove.equals(this.currentfilepathmove))
            {
                throw new IOException();
            }
            answear=JOptionPane.showConfirmDialog(frame, "Are you sure you want to move this? (If you are moving directory all data inside will be moved too)", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(answear!=0)
            {
            throw new ZipException();
            }
            this.auxfilepathmove=this.filepathmove;
            if(this.auxfilepathmove.matches(".+null/")&&!new File(this.auxfilepathmove).exists())
            {
            StringBuilder input1 = new StringBuilder();
            input1.append(this.auxfilepathmove);
            input1.reverse();
            this.auxfilepathmove=input1.toString();
            this.auxfilepathmove=this.auxfilepathmove.substring(5);
            input1 = new StringBuilder();
            input1.append(this.auxfilepathmove);
            input1.reverse();
            this.auxfilepathmove=input1.toString();
                
            }
            
            /*this.list.setEnabled(false);
            this.listscroll.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.listtomove.setEnabled(true);
            this.listtomovescroll.setEnabled(true);*/
            this.warunek2=false;
            
            
                if(!new File(this.currentfilepath).isDirectory() && !new File(this.currentfilepath).isFile() && !new File(this.currentfilepath).exists())
                {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.currentfilepath);
                input1.reverse();
                this.currentfilepath=input1.toString();
                this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/')+1);
                this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                input1 = new StringBuilder();
                input1.append(this.currentfilepath);
                input1.reverse();
                this.currentfilepath=input1.toString();
                }
                this.auxfilepathmove=this.filepathmove;
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                this.auxfilepathmove=this.auxfilepathmove.substring(0, this.auxfilepathmove.indexOf('/',1));
                input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                
                   if(new File(this.filepathmove).isDirectory())
                {
                   moveFolder(Paths.get(this.filepathmove),Paths.get(this.currentfilepath+this.auxfilepathmove));
                }
                else if(new File(this.filepathmove).isFile())
                {
                    moveFile(new File(this.filepathmove),new File(this.currentfilepath+this.auxfilepathmove));
                }
                   JOptionPane.showMessageDialog(frame,"Succesfully moved","Info", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(ZipException exception)
                {
                JOptionPane.showMessageDialog(frame,"Moving aborted","Info", JOptionPane.INFORMATION_MESSAGE);
                }            
                catch(IOException exception)
                {
                JOptionPane.showMessageDialog(frame,"Please select directory or file to move","Info", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
                }
                catch(Exception exception)
                {
                JOptionPane.showMessageDialog(frame,"Failed to move file/directory","Error", JOptionPane.ERROR_MESSAGE);
                
                }
                this.listtomove.setEnabled(true);
                this.list.setEnabled(true);
                this.listcontents.setEnabled(true);
                this.returntohigherdirectory.setEnabled(true);
                this.rename.setEnabled(true);
                this.delete.setEnabled(true);
                this.createdir.setEnabled(true);
                this.move.setEnabled(true);
                this.copy.setEnabled(true);
                Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
            }
                /*this.list.setEnabled(true);
                this.listscroll.setEnabled(true);
                this.rename.setEnabled(true);
                this.delete.setEnabled(true);
                this.createdir.setEnabled(true);
                this.listtomove.setEnabled(false);
                this.listtomovescroll.setEnabled(false);*/
                
            
            
        }
        
        if(e.getSource()==copy)
        {
            if(frame.getFocusOwner()==list && !(list.getSelectedValue()==".."))
            {
            try
            {
            this.listtomove.setEnabled(false);
            this.listtomove.setEnabled(false);
            this.list.setEnabled(false);
            this.listcontents.setEnabled(false);
            this.returntohigherdirectory.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.move.setEnabled(false);
            this.copy.setEnabled(false);
            int answear;            
            if(this.auxfilepath.equals(this.currentfilepath))
            {
                throw new IOException();
            }
            answear=JOptionPane.showConfirmDialog(frame, "Are you sure you want to copy this? (If you are copying directory all data inside will be copied too)", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(answear!=0)
            {
            throw new ZipException();
            }
            this.auxfilepath=this.filepath;
            if(this.auxfilepath.matches(".+null/")&&!new File(this.auxfilepath).exists())
            {
            StringBuilder input1 = new StringBuilder();
            input1.append(this.auxfilepath);
            input1.reverse();
            this.auxfilepath=input1.toString();
            this.auxfilepath=this.auxfilepath.substring(5);
            input1 = new StringBuilder();
            input1.append(this.auxfilepath);
            input1.reverse();
            this.auxfilepath=input1.toString();
                
            }

            /*this.list.setEnabled(false);
            this.listscroll.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.listtomove.setEnabled(true);
            this.listtomovescroll.setEnabled(true);*/
            this.warunek2=false;
            
            
                if(!new File(this.currentfilepathmove).isDirectory() && !new File(this.currentfilepathmove).isFile() && !new File(this.currentfilepathmove).exists())
                {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.currentfilepathmove);
                input1.reverse();
                this.currentfilepathmove=input1.toString();
                this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/')+1);
                this.currentfilepathmove=this.currentfilepathmove.substring(this.currentfilepathmove.indexOf('/'));
                input1 = new StringBuilder();
                input1.append(this.currentfilepathmove);
                input1.reverse();
                this.currentfilepathmove=input1.toString();
                }
                this.auxfilepath=this.filepath;
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                this.auxfilepath=this.auxfilepath.substring(0, this.auxfilepath.indexOf('/',1));
                input1 = new StringBuilder();
                input1.append(this.auxfilepath);
                input1.reverse();
                this.auxfilepath=input1.toString();
                if(new File(this.filepath).isDirectory())
                {
                   copyFolder(Paths.get(this.filepath),Paths.get(this.currentfilepathmove+this.auxfilepath));
                }
                else if(new File(this.filepath).isFile())
                {
                    copyFile(new File(this.filepath),new File(this.currentfilepathmove+this.auxfilepath));
                }
                   JOptionPane.showMessageDialog(frame,"Succesfully copied","Info", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(ZipException exception)
                {
                JOptionPane.showMessageDialog(frame,"Copying aborted","Info", JOptionPane.INFORMATION_MESSAGE);
                }            
                catch(IOException exception)
                {
                JOptionPane.showMessageDialog(frame,"Please select directory or file to copy","Info", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
                }
                catch(Exception exception)
                {
                JOptionPane.showMessageDialog(frame,"Failed to copy file/directory","Error", JOptionPane.ERROR_MESSAGE);
                
                }
            this.listtomove.setEnabled(true);
                this.list.setEnabled(true);
                this.listcontents.setEnabled(true);
                this.returntohigherdirectory.setEnabled(true);
                this.rename.setEnabled(true);
                this.delete.setEnabled(true);
                this.createdir.setEnabled(true);
                this.move.setEnabled(true);
                this.copy.setEnabled(true);
                Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
        }
            else if(frame.getFocusOwner()==listtomove && !(listtomove.getSelectedValue()==".."))
            {
                try
            {
            this.listtomove.setEnabled(false);
            this.list.setEnabled(false);
            this.listcontents.setEnabled(false);
            this.returntohigherdirectory.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.move.setEnabled(false); 
            this.copy.setEnabled(false);
            int answear;
            if(this.auxfilepathmove.equals(this.currentfilepathmove))
            {
                throw new IOException();
            }
            answear=JOptionPane.showConfirmDialog(frame, "Are you sure you want to copy this? (If you are copying directory all data inside will be copied too)", "Confirmation", JOptionPane.YES_NO_OPTION);
            if(answear!=0)
            {
            throw new ZipException();
            }
            this.auxfilepathmove=this.filepathmove;
            if(this.auxfilepathmove.matches(".+null/")&&!new File(this.auxfilepathmove).exists())
            {
            StringBuilder input1 = new StringBuilder();
            input1.append(this.auxfilepathmove);
            input1.reverse();
            this.auxfilepathmove=input1.toString();
            this.auxfilepathmove=this.auxfilepathmove.substring(5);
            input1 = new StringBuilder();
            input1.append(this.auxfilepathmove);
            input1.reverse();
            this.auxfilepathmove=input1.toString();
                
            }
            
            /*this.list.setEnabled(false);
            this.listscroll.setEnabled(false);
            this.rename.setEnabled(false);
            this.delete.setEnabled(false);
            this.createdir.setEnabled(false);
            this.listtomove.setEnabled(true);
            this.listtomovescroll.setEnabled(true);*/
            this.warunek2=false;
            
            
                if(!new File(this.currentfilepath).isDirectory() && !new File(this.currentfilepath).isFile() && !new File(this.currentfilepath).exists())
                {
                StringBuilder input1 = new StringBuilder();
                input1.append(this.currentfilepath);
                input1.reverse();
                this.currentfilepath=input1.toString();
                this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/')+1);
                this.currentfilepath=this.currentfilepath.substring(this.currentfilepath.indexOf('/'));
                input1 = new StringBuilder();
                input1.append(this.currentfilepath);
                input1.reverse();
                this.currentfilepath=input1.toString();
                }
                this.auxfilepathmove=this.filepathmove;
                StringBuilder input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                this.auxfilepathmove=this.auxfilepathmove.substring(0, this.auxfilepathmove.indexOf('/',1));
                input1 = new StringBuilder();
                input1.append(this.auxfilepathmove);
                input1.reverse();
                this.auxfilepathmove=input1.toString();
                
                   if(new File(this.filepathmove).isDirectory())
                {
                   copyFolder(Paths.get(this.filepathmove),Paths.get(this.currentfilepath+this.auxfilepathmove));
                }
                else if(new File(this.filepathmove).isFile())
                {
                    copyFile(new File(this.filepathmove),new File(this.currentfilepath+this.auxfilepathmove));
                }
                   JOptionPane.showMessageDialog(frame,"Succesfully copied","Info", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(ZipException exception)
                {
                JOptionPane.showMessageDialog(frame,"Copying aborted","Info", JOptionPane.INFORMATION_MESSAGE);
                }            
                catch(IOException exception)
                {
                JOptionPane.showMessageDialog(frame,"Please select directory or file to copy","Info", JOptionPane.INFORMATION_MESSAGE);
                //exception.printStackTrace();
                }
                catch(Exception exception)
                {
                JOptionPane.showMessageDialog(frame,"Failed to copy file/directory","Error", JOptionPane.ERROR_MESSAGE);
                
                }
                this.listtomove.setEnabled(true);
                this.list.setEnabled(true);
                this.listcontents.setEnabled(true);
                this.returntohigherdirectory.setEnabled(true);
                this.rename.setEnabled(true);
                this.delete.setEnabled(true);
                this.createdir.setEnabled(true);
                this.move.setEnabled(true);
                this.copy.setEnabled(true);
                Currentfoldercontents filecontents = new Currentfoldercontents(this.currentfilepath);
                this.aux=filecontents.getContentsList();
                this.model= new DefaultListModel<>();
                this.list.setModel(this.model);
                this.model.addElement("..");
                for(String plik:this.aux)
                {
                    this.model.addElement(plik);
                }
                filecontents = new Currentfoldercontents(this.currentfilepathmove);
                this.aux2=filecontents.getContentsList();
                this.model2= new DefaultListModel<>();
                this.listtomove.setModel(this.model2);
                this.model2.addElement("..");
                for(String plik1:this.aux2)
                {
                    this.model2.addElement(plik1);
                }
            }
                /*this.list.setEnabled(true);
                this.listscroll.setEnabled(true);
                this.rename.setEnabled(true);
                this.delete.setEnabled(true);
                this.createdir.setEnabled(true);
                this.listtomove.setEnabled(false);
                this.listtomovescroll.setEnabled(false);*/
                
            
            
        }
        
    }
    
  public static void deleteDirectoryRecursion(Path path) throws IOException 
  {
  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) 
  {
    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) 
    {
      for (Path entry : entries) {
        deleteDirectoryRecursion(entry);
      }
    }
  }
  Files.delete(path);
}
  
  public  void copyFolder(Path src, Path dest) throws IOException {
    try (Stream<Path> stream = Files.walk(src)) {
        stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        stream.close();
    }
}

private void copy(Path source, Path dest) {
    try {
        Files.copy(source, dest, REPLACE_EXISTING);
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage(), e);
    }
}

public static void copyFile(File sourceFile, File destFile) throws IOException {
    if(!destFile.exists()) {
        destFile.createNewFile();
    }

    FileChannel source = null;
    FileChannel destination = null;
    try {
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();

        // previous code: destination.transferFrom(source, 0, source.size());
        // to avoid infinite loops, should be:
        long count = 0;
        long size = source.size();              
        while((count += destination.transferFrom(source, count, size-count))<size);
    }
    finally {
        if(source != null) {
            source.close();
        }
        if(destination != null) {
            destination.close();
        }
    }
}

public  void moveFolder(Path src, Path dest) throws IOException {
    try (Stream<Path> stream = Files.walk(src)) {
        stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        deleteDirectoryRecursion(src);
        stream.close();
    }
}

public static void moveFile(File sourceFile, File destFile) throws IOException {
    if(!destFile.exists()) {
        destFile.createNewFile();
    }

    FileChannel source = null;
    FileChannel destination = null;
    try {
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();

        // previous code: destination.transferFrom(source, 0, source.size());
        // to avoid infinite loops, should be:
        long count = 0;
        long size = source.size();              
        while((count += destination.transferFrom(source, count, size-count))<size);
    }
    finally {
        if(source != null) {
            source.close();
            deleteDirectoryRecursion(sourceFile.toPath());
            
        }
        if(destination != null) {
            destination.close();
        }
    }
}
}