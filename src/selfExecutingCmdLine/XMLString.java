
package selfExecutingCmdLine;
/**
 *
 * @author aritra_dhar
 */
public class XMLString 
{
    String sinkmethod;

    public String getSinkmethod() {
        return sinkmethod;
    }

    public void setSinkmethod(String sinkmethod) {
        this.sinkmethod = sinkmethod;
    }
    
    String taintStrings;
    String preventdetails;
    String causedetails;
    String consequencesDetails;
    String platforms;

    public String getCausedetails() {
        return causedetails;
    }

    public void setCausedetails(String causedetails) {
        this.causedetails = causedetails;
    }

    public String getConsequencesDetails() {
        return consequencesDetails;
    }

    public void setConsequencesDetails(String consequencesDetails) {
        this.consequencesDetails = consequencesDetails;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms() {
        if(sinkmethod.equalsIgnoreCase("executeQuery"))
            platforms="Java, SQL";
        if(sinkmethod.equalsIgnoreCase("insert"))
            platforms="Java, SQL, NOSQL, Mongodb";
        if(sinkmethod.equalsIgnoreCase("find"))
            platforms="Java, SQL, NOSQL, Mongodb";
        if(sinkmethod.equalsIgnoreCase("createDocument"))
            platforms="Java, SQL, NOSQL, couchdb";
        if(sinkmethod.equalsIgnoreCase("setMap"))
            platforms="Java, SQL, NOSQL, couchdb";
        if(sinkmethod.equalsIgnoreCase("setViews"))
            platforms="Java, SQL, NOSQL, couchdb";
        
    }

    public String getPreventdetails() {
        return preventdetails;
    }

    public void setPreventdetails(String preventdetails) {
        this.preventdetails = preventdetails;
    }

    public String getTaintStrings() {
        return taintStrings;
    }

    public void setTaintStrings(String taintStrings) {
        this.taintStrings = taintStrings;
    }
    
    
    
}
