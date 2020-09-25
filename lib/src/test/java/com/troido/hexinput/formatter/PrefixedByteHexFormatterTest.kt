package com.troido.hexinput.formatter

import org.junit.Assert
import org.junit.Test

class PrefixedByteHexFormatterTest {

    //////////////////////////////////////////////////////////////////////////////
    // F O R M A T T I N G     T E S T S ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testFormatEmptyValuesList() {
        val formatter = PrefixedByteHexFormatter()
        val formattedContent = formatter.format(listOf())

        Assert.assertEquals("", formattedContent)
    }

    @Test
    fun testFormatSingleValue() {
        val formatter = PrefixedByteHexFormatter()
        val formattedContent = formatter.format(listOf('1'))

        Assert.assertEquals("0x01", formattedContent)
    }

    @Test
    fun testFormatTwoValues() {
        val formatter = PrefixedByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2'))

        Assert.assertEquals("0x12", formattedContent)
    }

    @Test
    fun testFormatEvenNumberOfValues() {
        val formatter = PrefixedByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6'))

        Assert.assertEquals("0x12 0x34 0x56", formattedContent)
    }

    @Test
    fun testFormatOddNumberOfValues() {
        val formatter = PrefixedByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5'))

        Assert.assertEquals("0x12 0x34 0x05", formattedContent)
    }


    //////////////////////////////////////////////////////////////////////////////
    // P A R S I N G    T E S T S ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testParseEmptyString() {
        val hex = ""
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSingleByte() {
        val hex = "0xA1"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','1'), chars)
    }

    @Test
    fun testParseTwoBytes() {
        val hex = "0xB3 0x45"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('B','3','4','5'), chars)
    }

    @Test
    fun testParseManyBytes() {
        val hex = "0xA3 0xB6 0xAC 0xD3 0xF4"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6','A','C','D','3','F','4'), chars)
    }

    @Test
    fun testParseSubstringEndingWithSpace() {
        val hex = "0xA3 0xB6 "
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6'), chars)
    }

    @Test
    fun testParseSubstringEndingWithZeroFromPrefix() {
        val hex = "0xA3 0xB6 0"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6'), chars)
    }

    @Test
    fun testParseSubstringEndingWithPrefix() {
        val hex = "0xA3 0xB6 0x"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6'), chars)
    }

    @Test
    fun testParseSubstringEndingWithFirstValueOfByte() {
        val hex = "0xA3 0xB6 0x3"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6','3'), chars)
    }

    @Test
    fun testParseSubstringStartingWithSpace() {
        val hex = " 0xA3 0xB6 0x3"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6','3'), chars)
    }

    @Test
    fun testParseSubstringStartingWithX() {
        val hex = "xA3 0xB6 0x3"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6','3'), chars)
    }

    @Test
    fun testParseSubstringStartingWithFirstValueOfByte() {
        val hex = "A3 0xB6"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','B','6'), chars)
    }

    @Test
    fun testParseSubstringStartingWithSecondValueOfByte() {
        val hex = "3 0xB6"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('3','B','6'), chars)
    }

    @Test
    fun testParseSubstringStartingWithSecondValueOfByteAndEndingWithPrefix() {
        val hex = "3 0x"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('3'), chars)
    }

    @Test
    fun testParseSubstringOfOneByteEndingWithFirstValue() {
        val hex = "0x4"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('4'), chars)
    }

    @Test
    fun testParseSubstringOfOneByteEndingWithX() {
        val hex = "0x"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSubstringContainingZeroOnly() {
        val hex = "0"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('0'), chars)
    }

    @Test
    fun testParseSubstringOfOneByteStartingWithX() {
        val hex = "x4F"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('4','F'), chars)
    }

    @Test
    fun testParseSubstringOfOneByteStartingWithFirstValueOfByte() {
        val hex = "4F"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('4','F'), chars)
    }

    @Test
    fun testParseSubstringContainingOnlyOneValue() {
        val hex = "F"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('F'), chars)
    }

    @Test
    fun testParseSubstringOfOneByteStartingWithXAndEndingWithFirstValueOfByte() {
        val hex = "x4"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('4'), chars)
    }

    @Test
    fun testParseSubstringContainingXOnly() {
        val hex = "x"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseContentWithOneInvalidCharacter() {
        val hex = "0x8G"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }

    @Test
    fun testParseContentWithMultipleInvalidCharacters() {
        val hex = "0x8T 0xHJ 0x5J"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }

    @Test
    fun testParseContentWithoutPrefixes() {
        val hex = "8C 9A BC 34"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }

    @Test
    fun testParseContentWithInvalidPrefixes() {
        val hex = "0j8C 0j9A 0jBC 0j34"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }

    @Test
    fun testParseContentWithSpacesAtWrongPlaces() {
        val hex = "0x8 C 0x9 A0xBC 0x 34"
        val chars = PrefixedByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    S O U R C E    V A L U E    T E S T S /////////////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateSourceValue(values : List<Char>,formattedValueToSourceValueMap : Map<Int,Int>) {
        val formatter = PrefixedByteHexFormatter()
        for(formattedValueToSourceValue in formattedValueToSourceValueMap) {
            val expectedResult = formattedValueToSourceValue.value
            val actualResult = formatter.locateSourceValue(values,formattedValueToSourceValue.key)
            Assert.assertEquals("Formatted value index: ${formattedValueToSourceValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateSourceValueWhenNoValues() {
        val values = listOf<Char>()

        val formattedValueToSourceValueMap = mapOf(
            0 to 0
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOnlyOneValue() {
        val values = listOf('A')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 1
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOnlyTwoValues() {
        val values = listOf('A','B')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 1,
            4 to 2
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenEvenNumberOfValues() {
        val values = listOf('A','B','1','5')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 1,
            4 to 2,
            5 to 2,
            6 to 2,
            7 to 2,
            8 to 3,
            9 to 4
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOddNumberOfValues() {
        val values = listOf('A','B','1')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 1,
            4 to 2,
            5 to 2,
            6 to 2,
            7 to 2,
            8 to 2,
            9 to 3
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    F O R M A T T E D    V A L U E    T E S T S ///////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateFormattedValue(values : List<Char>,sourceValueToFormattedValueMap : Map<Int,Int>) {
        val formatter = PrefixedByteHexFormatter()
        for(sourceValueToFormattedValue in sourceValueToFormattedValueMap) {
            val expectedResult = sourceValueToFormattedValue.value
            val actualResult = formatter.locateFormattedValue(values,sourceValueToFormattedValue.key)
            Assert.assertEquals("Source value index: ${sourceValueToFormattedValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateFormattedValueWhenEvenNumberOfValues() {
        val values = listOf('A','B','1','2')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 2,
            1 to 3,
            2 to 7,
            3 to 8,
            4 to 9
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun locateFormattedValueWhenOddNumberOfValues() {
        val values = listOf('A','B','2')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 2,
            1 to 3,
            2 to 8,
            3 to 9
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }
}