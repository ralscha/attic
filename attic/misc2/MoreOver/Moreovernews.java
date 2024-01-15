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
import Article;
import java.util.List;
import java.io.Serializable;

public interface Moreovernews extends Serializable {

    public static final String ZEUS_XML_NAME = "moreovernews";
    public static final String[] ZEUS_ATTRIBUTES = {};
    public static final String[] ZEUS_ELEMENTS = {"article"};

    public List getArticleList();

    public void setArticleList(List articleList);

    public void addArticle(Article article);

    public void removeArticle(Article article);

    public void marshal(File file) throws IOException;

    public void marshal(OutputStream outputStream) throws IOException;

    public void marshal(Writer writer) throws IOException;

}
