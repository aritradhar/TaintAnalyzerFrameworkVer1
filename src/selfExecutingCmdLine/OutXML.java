package selfExecutingCmdLine;


import java.io.*;
import java.io.FileWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
/**
 *
 * @author aritra_dhar
 */


public class OutXML 
{
    public File filename;
    public File []filename_arr;
    public String input_dir;
    public String output_dir;
    public String query;
    public String time;
    public int flag;
    
    //experimental, discarded
    public static String []sink_list={"find","parse","executeQuery"};
    
    public void setflag(int flag)
    {
        this.flag=flag;
    }
    
    public void setTime(String time)
    {
        this.time=time;
    }
    
    public OutXML(File filename,String input_dir, String output_dir,String query) 
    {
        this.filename=filename;
        this.input_dir=input_dir;
        this.output_dir=output_dir;
        this.query=query;
    }
    
    public OutXML(File []filename_arr,String input_dir, String output_dir,String query)
    {
        this.filename_arr=filename_arr;
        this.input_dir=input_dir;
        this.output_dir=output_dir;
        this.query=query;
    }
    
    public static String returnDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=new Date();
        return dateFormat.format(date);
    }
    
    
    public void lineAnalysis() throws Exception
    {                
        Integer pattern=0;
        String patternID="";
        String patt="pat";
        FileWriter fwrite=new FileWriter(output_dir.concat("\\datalog\\Report.xml"));
        fwrite.append("<?xml version=\"1.0\"?>\n");
        fwrite.append("<Author>Aritra Dhar</Author>\n");
        fwrite.append("<Version>1.0</Version>\n");
        fwrite.append("<BuildTime>"+returnDate()+"</BuildTime>\n");
        fwrite.append("<Query>\n");
        fwrite.append("     <Query_String><![CDATA["+query+"]]></Query_String>\n");
        fwrite.append("</Query>\n");
        fwrite.append("<Trap>\n");
        //fwrite.append("     <Vunnerability>\n");     
        System.out.println("-----------Vulnerability-------------");
        DatalogRulrGenV2 dlrg=new DatalogRulrGenV2();
        String dlmtr=dlrg.dlmtr();
        String [][][]report=new String[filename_arr.length][][];
        //System.out.println("##"+filename_arr.length);
        for(int i=0;i<filename_arr.length;i++)
        {
            XMLProperties XML=new XMLProperties();
            int count=0;
            BufferedReader br=new BufferedReader(new FileReader(filename_arr[i].toString().substring(0,filename_arr[i].toString().indexOf(".dl")).concat("_output.txt")));
            String []S=new String[5000];
            String st=null;
            while((st=br.readLine())!=null)
                S[count++]=st;
         
            for(int j=0;j<count;j++)
            {
                if(S[j].contains("Total rows:"))
                {
                    Integer row_count=Integer.parseInt(S[j].substring(S[j].indexOf(":")+2,S[j].length()));
                    if(row_count>0)
                    {
                        int last_dlm=0;
                        for(int j1=0;j1<filename_arr[i].toString().length();j1++)
                        {
                            if(filename_arr[i].toString().charAt(j1)=='\\')
                                last_dlm=j1;
                        }
                        
                        //retrive src code name from the dci file generated by javap2
                        SrcFileNameGen src=new SrcFileNameGen();
                        String dci_path=output_dir.concat("\\").concat(filename_arr[i].toString().substring(last_dlm+1, filename_arr[i].toString().indexOf("_datalog.dl")));
                        src.setdirname(dci_path);
                        SinkLineLocate snk=new SinkLineLocate();
                        snk.setdirname(dci_path);
                        
                        String filename=src.nameGen();
                        String src_file_name=input_dir.concat(filename);
                        //System.out.print("%%%%%%%%%%%%%%"+src_file_name);
                        
                        
                        //String src_file_name=input_dir.concat(filename_arr[i].toString().substring(last_dlm+1, filename_arr[i].toString().indexOf("_datalog.dl")).concat(".java"));
                        //String filename=filename_arr[i].toString().substring(last_dlm+1, filename_arr[i].toString().indexOf("_datalog.dl")).concat(".java");
                        String sink_file_name=src_file_name;
                        
                        XML.setTime(time);
                        XML.setSource(input_dir);
                        XML.setIgnore("");
                        XML.setFile(filename);
                        XML.setExtensions("java");
                        XML.setSource_type("java");
                        XML.setAnalyzer("datalog");
                        
                        XMLString xstring=new XMLString();
                        
                        
                        //System.out.println(row_count);
                        String row_temp="";
                        int p=j;
                        for(int k=0;k<row_count;k++)
                            row_temp=row_temp.concat(S[p-row_count+k]);
                        
                        //System.out.println("%%%%%%%%%%%%%%%%%%%%%"+flag);
                        /*
                        if(flag==1)
                        {
                            System.out.println("--Source code name : "+src_file_name+"--");
                            fwrite.append("         <Analysis source=\""+XML.getSource()+"\" ignore=\""+XML.getIgnore()+"\" extensions=\""+XML.getExtensions()+"\" sourceType=\""+XML.getSource_type()+"\" anlyzer=\""+XML.getAnalyzer()+"\" time=\""+XML.getTime()+"\">\n");
                            
                            String []row_split=row_temp.split(", ");
                            for(int j1=0;j1<row_split.length;j1++)
                            {
                                row_split[j1]=row_split[j1].replaceAll("\\[", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\]", "").replaceAll("'", "");
                                String row_method_name=row_split[j1].substring(0, row_split[j1].indexOf("_"));
                                String row_line_no=row_split[j1].substring(row_split[j1].indexOf("_")+1,row_split[j1].length());  
                            
                                XML.setPatternName("");
                                XML.setPatternID("");
                                XML.setType("taint");
                                XML.setAttack("");
                                XML.setSource_file(src_file_name);
                                XML.setSink_file(sink_file_name);
                            
                                patternID=patt.concat((pattern++).toString());
                                XML.setPatternID(patternID.toString());
                            
                                fwrite.append("             <vulnerability patternName=\""+XML.getPatternName()+"\" attack=\""+XML.getAttack()+"\" type=\""+XML.getType()+"\" patternID=\""+XML.getPatternID()+"\">\n");
                                fwrite.append("                 <Source file=\""+XML.getSource_file()+"\" method=\""+row_method_name+"\" line=\""+row_line_no+"\">\n");
                                fwrite.append("                 </Source>\n");
                                fwrite.append("                 <Sink file=\""+XML.getSource_file()+"\" method=\""+row_method_name+"\" line=\""+row_line_no+"\">\n");
                                fwrite.append("                 </Sink>\n");
                            
                                fwrite.append("                 <taintStrings><strins>");
                                fwrite.append("</strins></taintStrings>\n");
                            
                                fwrite.append("                 <preventdetails><prevent>");
                                fwrite.append("</prevent></preventdetails>\n");
                            
                                fwrite.append("                 <causedetails><cause>");
                                fwrite.append("</cause></causedetails>\n");
                                
                                fwrite.append("                 <consequencesDetails><consequences>");
                                fwrite.append("</consequences></consequencesDetails>\n");
                            
                                fwrite.append("                 <platforms><platform>");
                                fwrite.append("</platform></platforms>\n");
                            
                                System.out.println("  @method : "+row_method_name+" @line no. : "+row_line_no);
                                //System.out.println(row_method_name);
                                //System.out.println(row_split[j1]);
                                fwrite.append("             </vulnerability>\n");
                            }
                        }*/
                        
                        if(flag==2 && row_temp.contains("', '"))
                        {
                            HashSet<String> m_name_hashset=new HashSet<String>();
                            int m_count=0;
                            
                            System.out.println("--Source code name : "+src_file_name+"--");
                            fwrite.append("         <Analysis source=\""+XML.getSource()+"\" ignore=\""+XML.getIgnore()+"\" extensions=\""+XML.getExtensions()+"\" sourceType=\""+XML.getSource_type()+"\" anlyzer=\""+XML.getAnalyzer()+"\" time=\""+XML.getTime()+"\">\n");

                            System.out.println(row_temp);
                            String []row_split=row_temp.split("\\), \\(");
                            for(int j1=0;j1<row_split.length;j1++)
                            {
                                row_split[j1]=row_split[j1].replaceAll("\\[", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\]", "").replaceAll("'", "");  
                                if(row_split[j1].contains(","))
                                {
                                    String []row_split_internal=row_split[j1].split(", ");
                                    
                                    String row_method_name=row_split_internal[0].substring(0, row_split_internal[0].indexOf("_"));
                                    String row_line_no=row_split_internal[0].substring(row_split_internal[0].indexOf("_")+1,row_split_internal[0].length());                                                                         
                                    
                                    String row_sink_name=row_split_internal[1];
                                    snk.setSinkMethod(row_sink_name);
                                    
                                    if(!m_name_hashset.contains(row_sink_name))
                                    {
                                        m_name_hashset.add(row_sink_name);
                                        m_count++;
                                    }
                                    else
                                        m_count++;
                                    
                                    snk.setm_count(m_count);
                                    
                                    SinkSourceLineLookup lookup=new SinkSourceLineLookup();
                                    lookup.setSource_file_name(src_file_name);
                                    //System.out.println("###"+snk.lineNoGen());
                                    if(!snk.lineNoGen().equals(""))
                                    {
                                    lookup.setLine_no(Integer.parseInt(snk.lineNoGen()));
                                    
                                    String sink_source_code=lookup.getSoutceCode().trim();
                                    
                                    XML.setPatternName("");
                                    XML.setPatternID("");
                                    XML.setType("taint");
                                    XML.setAttack("");
                                    XML.setSource_file(src_file_name);
                                    XML.setSink_file(sink_file_name);
                            
                                    patternID=patt.concat((pattern++).toString());
                                    XML.setPatternID(patternID.toString());
                                    
                                    XMLString xstr=new XMLString();
                                    xstr.setSinkmethod(row_sink_name);
                                    xstr.setPlatforms();
                            
                                    fwrite.append("             <vulnerability patternName=\""+XML.getPatternName()+"\" attack=\""+XML.getAttack()+"\" type=\""+XML.getType()+"\" patternID=\""+XML.getPatternID()+"\">\n");
                                    fwrite.append("                 <Source file=\""+XML.getSource_file()+"\" method=\""+row_method_name+"\" line=\""+snk.lineNoGen()+"\">\n");
                                    fwrite.append("                 </Source>\n");
                                    fwrite.append("                 <Sink file=\""+XML.getSource_file()+"\" method=\""+row_method_name+"\" line=\""+snk.lineNoGen()+"\">\n");
                                    fwrite.append("                     <Sink_method name=\""+row_sink_name+"\">\n");
                                    fwrite.append("                     <![CDATA["+sink_source_code+"]]>\n");
                                    fwrite.append("                     </Sink_method>\n");
                                    fwrite.append("                 </Sink>\n");
                            
                                    fwrite.append("                 <taintStrings><strins>");
                                    fwrite.append("</strins></taintStrings>\n");
                            
                                    fwrite.append("                 <preventdetails><prevent>");
                                    fwrite.append("</prevent></preventdetails>\n");
                            
                                    fwrite.append("                 <causedetails><cause>");
                                    fwrite.append("</cause></causedetails>\n");
                                
                                    fwrite.append("                 <consequencesDetails><consequences>");
                                    fwrite.append("</consequences></consequencesDetails>\n");
                            
                                    fwrite.append("                 <platforms><platform>");
                                    fwrite.append(xstr.getPlatforms());
                                    fwrite.append("</platform></platforms>\n");
                            
                                    System.out.println("  @method : "+row_method_name+" @line no. : "+row_line_no);
                                    //System.out.println(row_method_name);
                                    //System.out.println(row_split[j1]);
                                    }
                                    fwrite.append("             </vulnerability>\n");
                                }
                              //System.out.println("@@"+row_split[j1].replaceAll("\\[", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\]", ""));  
                            }
                            
                            
                            /*
                             * int flag_fun=0;
                            for(int t=0;t<sink_list.length;t++)
                            {
                                if(row_temp.contains(sink_list[t]))
                                    flag_fun=1;
                            }
                            if(flag_fun==1)
                            {
                                //System.out.println("EEEEEEE");
                            }
                            * 
                            */
                            fwrite.append("         </Analysis>\n");
                        }
                        //System.out.println("   @ :  "+row_temp);
                        
                    }                   
                }
            }
        }
        //fwrite.append("     </Vunnerability>\n"); 
        fwrite.append("</Trap>\n");
        fwrite.close();
        
        
    }
    
}