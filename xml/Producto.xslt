<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<h2>Producto</h2>
				<table border="1">
					<tr bgcolor="#2ECCFA">
						<th style="text-align:left">Nombre</th>
						<th style="text-align:left">Cantidad</th>
						<th style="text-align:left">Tipo</th>
						<th style="text-align:left">Unidades</th>
						<th style="text-align:left">Precio</th>
						<th style="text-align:left">Plantacion</th>
					</tr>
					<xsl:for-each select="producto">
						<tr>
							<td>
								<xsl:value-of select="nombre" />
							</td>
							<td>
								<xsl:value-of select="cantidad" />
							</td>
							<td>
								<xsl:value-of select="tipo" />
							</td>
							<td>
								<xsl:value-of select="unidades" />
							</td>
							<td>
								<xsl:value-of select="precio" />
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>