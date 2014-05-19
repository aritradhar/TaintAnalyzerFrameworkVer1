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
public class SinkSourceLineLookup 
{
    public String source_file_name;
    public int line_no;
    
    public void setLine_no(int line_no) {
        this.line_no = line_no;
    }

    public void setSource_file_name(String source_file_name) {
        this.source_file_name = source_file_name;
    }
    
    public String getSoutceCode() throws Exception
    {
        String source_code=new String("");
        BufferedReader br=new BufferedReader(new FileReader(source_file_name));
        String s="";
        int count=0;
        while((s=br.readLine())!=null)
        {
            count++;
            if(count==line_no)
                source_code=s;
        }
       
        return source_code;
    }
}
