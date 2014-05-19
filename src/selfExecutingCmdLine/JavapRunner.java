/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selfExecutingCmdLine;

import java.io.File;

/**
 *
 * @author aritra_dhar
 */
public class JavapRunner
{
    public String dir;

    public JavapRunner(String dir) 
    {
        this.dir=dir;
    }
    
    public void processRunner() throws Exception
    {
        File []file_array=new File(dir).listFiles();
        int class_count=0,ind=0;
        for(int i=0;i<file_array.length;i++)
        {
            if(file_array[i].toString().contains(".class"))
                class_count++;
        }
        String []class_file_array=new String[class_count];
        
        for(int i=0;i<file_array.length;i++)
        {
            if(file_array[i].toString().contains(".class"))
                class_file_array[ind++]=file_array[i].toString();
        }
        

        String cmd_str=new String("");
        for(int i=0;i<class_count;i++)
        {
            cmd_str=cmd_str.concat("javap2.exe ").concat(class_file_array[i]).concat(" > ").concat(class_file_array[i].replaceAll(".class", ".dci"));
            if(i!=class_count-1)
                cmd_str=cmd_str.concat(" & ");
        }
        //System.out.println(cmd_str);
        Process p = Runtime.getRuntime().exec("cmd.exe /c cd \""+dir+"\" & start cmd.exe /k \""+cmd_str+"\"");
        /*
        for(int i=0;i<class_count;i++)
        {
            
            Process p = Runtime.getRuntime().exec("cmd.exe /c cd \""+dir+"\" & start cmd.exe /k \"javap2.exe "+class_file_array[i]+" > "+class_file_array[i].replaceAll(".class", ".dmp") +"\"");
            //p.exec("exit");
            p.waitFor();
            //p.destroy();
            //p.exit(1);
        }
        * 
        */
    }
}
