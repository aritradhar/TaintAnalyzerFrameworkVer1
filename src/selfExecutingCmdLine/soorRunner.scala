package selfExecutingCmdLine


// import trap.TrapBodyTransformer
// import trap.TrapUtil

import scala.collection.JavaConversions._
import soot.options.Options
import soot.Scene
import soot.PackManager
import soot.Transform;
import soot.options.Options;
import soot.PhaseOptions;
import soot.PackManager;
import trap.file.Util._
import org.apache.commons.io.FileUtils
import java.io._
import trap._


class JavaAnalysis {

	def getAllFiles(dir:String, extensions:Array[String], recursive:Boolean) = try {
    FileUtils.listFiles(new File(dir), extensions, recursive).toArray.map(_.toString)
	} 
	catch {
    case _ : Throwable=> Array[String]()
  }
  
  def doAnalysis(inputDir:String, outputDir:String, libDir:String,output_format:String) = {
    // TrapUtil.reset
    try {

      
      val libFiles = getAllFiles(libDir, Array("jar"), true)
      soot.G.reset
      //PackManager.v().getPack("jap").add(new Transform("jap.myTransform", TrapBodyTransformer));
      PhaseOptions.v().setPhaseOption("tag.ln", "on");
      Options.v.set_process_dir(List(inputDir))
      
      Options.v.set_output_dir(outputDir)
      if(output_format.equalsIgnoreCase("xml"))
        Options.v.set_output_format(soot.options.Options.output_format_xml)
      if(output_format.equalsIgnoreCase("class"))
        Options.v.set_output_format(soot.options.Options.output_format_class)
      
      Options.v.set_allow_phantom_refs(true)
      Options.v.set_src_prec(Options.src_prec_java)
      //Options.v.set_whole_program(true)
      Options.v.set_whole_program(false)
      //Options.v.set_main_class("test.Main")
      Options.v.set_exclude(List("java","sun", "java.lang"))
      Options.v.set_keep_line_number(true)
      val classPath = libFiles.foldLeft(inputDir)((x, y) => x+";"+y)
      Options.v.set_soot_classpath(classPath)
      Options.v.set_xml_attributes(true)
      soot.options.Options.v.set_omit_excepting_unit_edges(true)
      //Options.v.set_soot_classpath(inputDir+";C:\\scala\\lib\\servlet-api-2.5-20081211.jar;c:\\scala\\lib\\mongodb\\mongo-2.7.3.jar")
      Options.v.set_prepend_classpath(true)
      Options.v.setPhaseOption("jb", "use-original-names:true")
      Options.v.set_no_bodies_for_excluded(true)
      Options.v.keep_line_number()
      Options.v.set_print_tags_in_output(true)
      //PhaseOptions.v().setPhaseOption("cg", "enabled:true");
      //PhaseOptions.v().setPhaseOption("cg", "implicit-entry:false");
      //PhaseOptions.v().setPhaseOption("cg", "verbose:true");

      //Options.v().set_verbose(true);
      //PhaseOptions.v().setPhaseOption("jap.npc", "on"); // ???
      //CallGraphExample.main(null)
      Scene.v().loadNecessaryClasses()
      PackManager.v().runPacks()
      PackManager.v().writeOutput()
    } catch {
      case ce:soot.CompilationDeathException => println("TRAP error: Error during compilation of Java code")
        ce.printStackTrace
      case any : Throwable => any.printStackTrace
    }
    // TrapUtil.getVulnerabilities
  }
}
