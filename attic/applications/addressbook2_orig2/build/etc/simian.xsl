<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes"/>

  <xsl:template match="check">
    <html>
      <head>
        <link rel="stylesheet" href="simian.css" type="text/css"></link>
      </head>
      <body>
        <a name="top"></a>
        <div class="app">
          <div class="h3">
            <h3>Simian Results</h3>
            <p>The following document contains the results of
              <a href="http://www.redhillconsulting.com.au/products/simian/">Similarity Analyser
                <xsl:value-of select="//simian/@version"/>
              </a>.
            </p>
            <p>Copyright (c) 2003 RedHill Consulting, Pty. Ltd. All rights reserved.</p>
          </div>

          <xsl:apply-templates select="." mode="summary"/>

          <xsl:apply-templates select="." mode="filelist"/>

          <div class="h3">
            <h3>Duplications</h3>
            <xsl:for-each select="set">
              <xsl:sort select="@name"/>
              <xsl:apply-templates select="."/>
            </xsl:for-each>
          </div>
        </div>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="check" mode="filelist">
    <h3>Files</h3>
    <table border="1" width="100%" cellspacing="2" cellpadding="3">
      <tr>
        <th>File Name</th>
        <th>Duplications</th>
      </tr>
      <xsl:for-each select="descendant::block[not(@sourceFile=preceding::block/@sourceFile)]">
        <xsl:sort data-type="number" order="descending" select="sum(//block[@sourceFile=current()/@sourceFile]/parent::set/@lineCount)"/>
        <tr>
          <xsl:call-template name="alternated-row"/>
          <td>
            <xsl:variable name="sourceFile" select="@sourceFile"/>
            <xsl:for-each select="//block[@sourceFile=current()/@sourceFile]/parent::set">
              <xsl:choose>
                <xsl:when test="1 = position()">
                  <a href="#set-{generate-id(.)}">
                    <xsl:value-of select="$sourceFile"/>
                  </a>
                </xsl:when>
                <xsl:otherwise>,
                  <a href="#set-{generate-id(.)}">
                    <xsl:value-of select="position()"/>
                  </a>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
          </td>
          <td>
            <xsl:value-of select="sum(//block[@sourceFile=current()/@sourceFile]/parent::set/@lineCount)"/>
          </td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>

  <xsl:template match="set">
    <div class="h4">
      <a name="set-{generate-id(.)}"></a>
      <h4>Duplicate Lines:
        <xsl:value-of select="@lineCount"/>
      </h4>
      <table border="1" width="100%" cellspacing="2" cellpadding="3">
        <tr>
          <th>File Name</th>
          <th>Lines</th>
        </tr>
        <xsl:for-each select="block">
          <tr>
            <xsl:call-template name="alternated-row"/>
            <td>
              <xsl:value-of select="@sourceFile"/>
            </td>
            <td>
              <xsl:value-of select="@startLineNumber"/> -
              <xsl:value-of select="@endLineNumber"/>
            </td>
          </tr>
        </xsl:for-each>
      </table>
    </div>
  </xsl:template>

  <xsl:template match="check" mode="summary">
    <h3>Summary</h3>
    <table border="1" width="100%" cellspacing="2" cellpadding="3">
      <tr class="a">
        <td>Similarity treshold (lines)</td>
        <td><xsl:value-of select="//check/@lineCount"/></td>
      </tr>
      <tr class="b">
        <td>Total number of duplicate lines</td>
        <td><xsl:value-of select="sum(//set/@lineCount)"/></td>
      </tr>
      <tr class="a">
        <td>Total number of duplicate blocks</td>
        <td><xsl:value-of select="count(//block)"/></td>
      </tr>
      <tr class="b">
        <td>Total number of files with duplicates</td>
        <td>
          <xsl:value-of select="count(//block[not(@sourceFile=preceding::block/@sourceFile)])"/>
        </td>
      </tr>
    </table>
  </xsl:template>

  <xsl:template name="alternated-row">
    <xsl:attribute name="class">
      <xsl:if test="position() mod 2 = 1">a</xsl:if>
      <xsl:if test="position() mod 2 = 0">b</xsl:if>
    </xsl:attribute>
  </xsl:template>

</xsl:stylesheet>


