/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filemanager;

import java.io.File;

/**
 *
 * @author marek
 */
public class Currentfoldercontents {
    private String[] contents;
    private File[] contentsconverted;
    private int i=0;
    
    Currentfoldercontents(String path) 
    {
        i=0;
        File currentfolder = new File(path);
        contents=currentfolder.list();
    }
    
    public String[] getContentsList()
    {

        return this.contents;
        
    }

}
