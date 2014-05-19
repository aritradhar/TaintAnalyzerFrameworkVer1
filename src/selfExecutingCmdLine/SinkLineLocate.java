/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selfExecutingCmdLine;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author aritra_dhar
 */

public class SinkLineLocate 
{
    public String filename;
    public String sink_method;
    public int m_count;
    
    public void setm_count(int m_count)
    {
        this.m_count=m_count;
    }
    
    public void setdirname(String filename)
    {
        this.filename=filename.concat(".dci");
    }
    
    public void setSinkMethod(String sink_method)
    {
        this.sink_method=sink_method;
    }
    
    public String lineNoGen() throws Exception
    {
        BufferedReader br=new BufferedReader(new FileReader(filename));
        String s="";
        String line_no=new String("");
        int count=0;
        while((s=br.readLine())!=null)
        {
            if(s.contains(sink_method))
            {
                count++;
                if(count==m_count)
                    line_no=s.substring(0, s.indexOf("/"));
            }
        }
        return line_no;
    }   
}
