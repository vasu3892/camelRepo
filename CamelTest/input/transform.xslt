<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="xml" indent="yes"
		omit-xml-declaration="yes" />
	<xsl:variable name="newline" select="'&#xA;'" />

	<xsl:template match="Fruit">
		<xsl:text>Name,NumberEaten,Weight</xsl:text>
		<xsl:value-of select="$newline" />
		<xsl:for-each select="Customer">
			<xsl:value-of select="Name" />
			<xsl:text>,</xsl:text>
			<xsl:value-of select="NumberEaten" />
			<xsl:text>,</xsl:text>
			<xsl:value-of select="Weight" />
			<xsl:value-of select="$newline" />
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>