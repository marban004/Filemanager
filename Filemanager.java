/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filemanager;

import java.io.File;

/**
 *
 * @author marek
 */
public class Filemanager {

 
    public static void main(String[] args) 
    {
        File[] drives=File.listRoots();
        String filepath=drives[0].getPath().replaceAll("\\\\","/");
        Currentfoldercontents filecontents = new Currentfoldercontents(filepath);
        Mainwindow mainwindow = new Mainwindow(filecontents.getContentsList(),filepath);
        //System.out.println(filecontents.getContents());
        
    }
    
}
