package selfExecutingCmdLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author aritra_dhar
 */
public class SrcFileNameGen 
{
    public String filename;
    public void setdirname(String filename)
    {
        this.filename=filename.concat(".dci");
    }
    
    public String nameGen() throws Exception
    {
        BufferedReader br=new BufferedReader(new FileReader(filename));
        String s="";
        String src_name=new String("");
        while((s=br.readLine())!=null)
        {
            if(s.contains("// SourceFile ="))
            {
                src_name=s.substring(s.indexOf("=")+2,s.length());
                break;
            }
        }
        return src_name;
    }
    
}
