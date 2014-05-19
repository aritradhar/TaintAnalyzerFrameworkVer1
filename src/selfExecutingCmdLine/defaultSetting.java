package selfExecutingCmdLine;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class defaultSetting 
{
	public static void main(String []arg) throws Exception
	{
                String rule_file="";
		if(arg.length==1 && arg[0].equalsIgnoreCase("-help"))
		{
			System.out.println("java -jar <jar_name> -<actor/thread/tpool> " +
					"<input_dir> <output_dir> <lib_dir> <rule_file> <query_file> <knowledgebase_file>" +
					"\n actor - execution in scala actor" +
					"\n thread - execution in java thread" +
					"\n tpool - execution in java thread pool ");
			System.exit(0);
		}
		if(arg.length<7 || arg.length>7)
		{
			System.out.println("Wrong no. of arguments, enter -help option for help");
			System.exit(0);
		}
		if(arg.length==7 && (arg[0].equalsIgnoreCase("-actor")||
		   arg[0].equalsIgnoreCase("-thread")||arg[0].equalsIgnoreCase("-tpool")))
		{
                    
                        String inputDir=arg[1];
			String outputDir=arg[2];
			String libDir=arg[3];	
                        
			//JavaAnalysis jn=new JavaAnalysis();
                        new DirClear().clear(outputDir);
                        
			String knowledgebase_path=arg[6];
                        int dl=outputDir.length();
                        for(int i=0;i<outputDir.length();i++)
                        {
                            if(outputDir.charAt(i)=='\\')
                                dl=i;
                        }
                        rule_file="tmp_rule_file.txt";
                        copyFile(arg[4], rule_file,arg[6]);                                  			                        
                        
                        File javap2=new File(outputDir+"//javap2.exe");
                        long timeout=1000;
			System.out.println("------ old cache cleared waiting for "+timeout+" ms. timeout ------");
			try
			{
				Thread.sleep(timeout);
			}
			catch(Exception e)
                        {
				System.out.println(e.getMessage());
                        }
                        
                        if(javap2.exists())
                            System.out.println("----javap2.exe found------");
                        else
                        {
                           System.out.println("----javap2.exe not found program abort, put javap2.exe in destination folder------"); 
                           System.exit(1);
                        }
			
                        
                        new JavaAnalysis().doAnalysis(inputDir, outputDir, libDir,"xml");
                        new JavaAnalysis().doAnalysis(inputDir, outputDir, libDir, "class");
                        //jn.doAnalysis(inputDir, outputDir, libDir, "dava");
			
                        System.out.println("#########"+rule_file);
			String []ag=new String[6];
			ag[0]="-xml-dir";
			ag[1]=outputDir;
			ag[2]="-rule";
			ag[3]=rule_file;
			ag[4]="-query";
			ag[5]=arg[5];
                        
                        inputDir=inputDir.concat("\\");
                        
                        
                        System.out.println("----deassembler start-----");
			new JavapRunner(outputDir).processRunner();
                        System.out.println("----deassembler finished successfully-----");
                        
                        if(arg[0].equalsIgnoreCase("-actor"))
                        {
                            caller_analyzer_Actor_DIR.setDir(inputDir,outputDir);
                            caller_analyzer_Actor_DIR.main(ag);
                        }
			if(arg[0].equalsIgnoreCase("-thread"))
                        {
                            caller_analyzer_MT_DIR.setDir(inputDir,outputDir);
                            caller_analyzer_MT_DIR.main(ag);                             
                        }
			if(arg[0].equalsIgnoreCase("-tpool"))
                        {
                            caller_analyzer_ThreadPool_DIR.setDir(inputDir,outputDir);
			    caller_analyzer_ThreadPool_DIR.main(ag);                    
                        }
		}
	}
        
        public static void copyFile(String in, String out,String kbase) throws Exception
        {
            BufferedReader br=new BufferedReader(new FileReader(in));
            BufferedReader br1=new BufferedReader(new FileReader(kbase));
            String S="",f_cont="",f_cont1="";
            while((S=br.readLine())!=null)
                f_cont=f_cont.concat(S).concat("\n");
            
            String S1="";
            while((S1=br1.readLine())!=null)
                f_cont1=f_cont1.concat(S1).concat("\n");
            
            br.close();
            br1.close();
            FileWriter fwrite=new FileWriter(out);
            fwrite.append(f_cont);
            fwrite.append(f_cont1);
            fwrite.close();
        }
}