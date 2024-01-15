/**
 * This class was generated from a set of XML constraints
 *   by the Enhydra Zeus XML Data Binding Framework. All
 *   source code in this file is constructed specifically
 *   to work with other Zeus-generated classes. If you
 *   modify this file by hand, you run the risk of breaking
 *   this interoperation, as well as introducing errors in
 *   source code compilation.
 *
 * * * * * MODIFY THIS FILE AT YOUR OWN RISK * * * * *
 *
 * To find out more about the Enhydra Zeus framework, you
 *   can point your browser at <http://zeus.enhydra.org>
 *   where you can download releases, join and discuss Zeus
 *   on user and developer mailing lists, and access source
 *   code. Please report any bugs through that website.
 */
// Global Interface Import Statements
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

// Local Interface Import Statements
import java.io.Serializable;

public interface Article extends Serializable {

    public static final String ZEUS_XML_NAME = "article";
    public static final String[] ZEUS_ATTRIBUTES = {"id"};
    public static final String[] ZEUS_ELEMENTS = {"url", "headline_text", "source", "media_type", "cluster", "tagline", "document_url", "harvest_time", "access_registration", "access_status"};

    public String getUrl();

    public void setUrl(String url);

    public String getHeadline_text();

    public void setHeadline_text(String headline_text);

    public String getSource();

    public void setSource(String source);

    public String getMedia_type();

    public void setMedia_type(String media_type);

    public String getCluster();

    public void setCluster(String cluster);

    public String getTagline();

    public void setTagline(String tagline);

    public String getDocument_url();

    public void setDocument_url(String document_url);

    public String getHarvest_time();

    public void setHarvest_time(String harvest_time);

    public String getAccess_registration();

    public void setAccess_registration(String access_registration);

    public String getAccess_status();

    public void setAccess_status(String access_status);

    public String getId();

    public void setId(String id);

    public void marshal(File file) throws IOException;

    public void marshal(OutputStream outputStream) throws IOException;

    public void marshal(Writer writer) throws IOException;

}
