<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html> 
<body>
  <h2>Plantaciones</h2>
  <table>
    <tr bgcolor="#CC2EFA">
      <th style="text-align:left">Último regado</th>
      <th style="text-align:left">Hectáreas</th>
    </tr>
    <xsl:for-each select="plantacion">
    <tr>
      <td><xsl:value-of select="ultimo_regado"/></td>
      <td><xsl:value-of select="hectareas"/></td>
    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
