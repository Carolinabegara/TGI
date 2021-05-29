<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html> 
<body>
  <h2>Animales</h2>
  <table border="1">
    <tr bgcolor="#2ECCFA">
      <th style="text-align:left">Especie</th>
      <th style="text-align:left">Fecha Nacimiento</th>
    </tr>
    <xsl:for-each select="animal">
    <tr>
      <td><xsl:value-of select="especie"/></td>
      <td><xsl:value-of select="fecha_Nac"/></td>
    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>