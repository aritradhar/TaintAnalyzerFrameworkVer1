package selfExecutingCmdLine;

/**
 *
 * @author aritra_dhar
 */
public class XMLProperties
{
    String ignore;
    String extensions;
    String source_type;
    String time;
    String analyzer;
    String patternName;
    String attack;
    String type;
    String patternID;
    String source;
    String file;
    String source_file;
    String sink_file;

    public String getSink_file() {
        return sink_file;
    }

    public void setSink_file(String sink_file) {
        this.sink_file = sink_file;
    }

    public String getSource_file() {
        return source_file;
    }

    public void setSource_file(String source_file) {
        this.source_file = source_file;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }
    
    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getPatternID() {
        return patternID;
    }

    public void setPatternID(String patternID) {
        this.patternID = patternID;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
