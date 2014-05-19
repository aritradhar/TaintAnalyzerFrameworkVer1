/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selfExecutingCmdLine;

/**
 *
 * @author aritra_dhar
 */
class test_runner {
    
    public static void main(String []a)
    {
        new JavaAnalysis().doAnalysis("C:\\test\\taintsource","C:\\test\\output","C:\\test\\lib", "xml");
        new JavaAnalysis().doAnalysis("C:\\test\\taintsource","C:\\test\\output","C:\\test\\lib", "class");
    }
}
