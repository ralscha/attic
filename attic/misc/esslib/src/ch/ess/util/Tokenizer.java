package ch.ess.util;

import java.io.*;
import java.util.*;

public class Tokenizer {

  private StreamTokenizer st;

  public Tokenizer(String line) {
    st = createStreamTokenizer(line);
  }

  public List getTokens() throws IOException {
    int type;

    List tokenList = new ArrayList();

    while ((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
      if ((type == StreamTokenizer.TT_WORD) || (type == '"')) {
        tokenList.add(blank2null(st.sval));
      }
    }

    return tokenList;
  }
  
  private String blank2null(String value) {
    if ((value == null) || (value.trim().length() == 0)) {
      return null;
    } else {
      return value;
    }
  }  

  private StreamTokenizer createStreamTokenizer(String str) {
    StreamTokenizer st = new StreamTokenizer(new StringReader(str));
    st.resetSyntax();
    st.wordChars('a', 'z');
    st.wordChars('A', 'Z');
    st.wordChars(128 + 32, 255);
    st.wordChars('0', '9');
    st.wordChars('+', '+');
    st.wordChars('-', '-');
    st.wordChars('.', '.');
    st.whitespaceChars(0, ' ');
    //st.commentChar('/');
    st.quoteChar('"');
    st.quoteChar('\'');
    return (st);
  }
}
