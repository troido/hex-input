package com.troido.hexinput.formatter

import org.junit.Assert
import org.junit.Test

class SingleByteHexFormatterTest {

    //////////////////////////////////////////////////////////////////////////////
    // F O R M A T T I N G     T E S T S ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testFormatEmptyValuesList() {
        val formatter = SingleByteHexFormatter()
        val formattedContent = formatter.format(listOf())

        Assert.assertEquals("", formattedContent)
    }

    @Test
    fun testFormatSingleValue() {
        val formatter = SingleByteHexFormatter()
        val formattedContent = formatter.format(listOf('1'))

        Assert.assertEquals("01", formattedContent)
    }

    @Test
    fun testFormatTwoValues() {
        val formatter = SingleByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2'))

        Assert.assertEquals("12", formattedContent)
    }

    @Test
    fun testFormatEvenNumberOfValues() {
        val formatter = SingleByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6'))

        Assert.assertEquals("12 34 56", formattedContent)
    }

    @Test
    fun testFormatOddNumberOfValues() {
        val formatter = SingleByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','6'))

        Assert.assertEquals("12 34 06", formattedContent)
    }


    //////////////////////////////////////////////////////////////////////////////
    // P A R S I N G    T E S T S ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testParseEmptyString() {
        val hex = ""
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSingleByte() {
        val hex = "A1"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','1'), chars)
    }

    @Test
    fun testParseTwoBytes() {
        val hex = "A1 B2"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','1','B','2'), chars)
    }

    @Test
    fun testParseManyBytes() {
        val hex = "A1 B2 C3 D4 E5"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','1','B','2','C','3','D','4','E','5'), chars)
    }

    @Test
    fun testParseSubstringStartingWithSecondValueOfByte() {
        val hex = "1 B2 C3"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('1','B','2','C','3'), chars)
    }

    @Test
    fun testParseSubstringStartingWithSpace() {
        val hex = " B2 C3"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('B','2','C','3'), chars)
    }

    @Test
    fun testParseSubstringEndingWithSpace() {
        val hex = "B2 C3 "
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('B','2','C','3'), chars)
    }

    @Test
    fun testParseSubstringEndingWithFirstValueOfByte() {
        val hex = "B2 C"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('B','2','C'), chars)
    }

    @Test
    fun testParseSubstringStartingWithFirstValueOfByteAndEndingWithFirstValueOfByte() {
        val hex = "2 C"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('2','C'), chars)
    }

    @Test
    fun testParseSubstringStartingWithSpaceAndEndingWithSpace() {
        val hex = " 12 C4 "
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('1','2','C','4'), chars)
    }

    @Test
    fun testParseContentWithOneInvalidCharacter() {
        val hex = "8G"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }

    @Test
    fun testParseContentWithMultipleInvalidCharacter() {
        val hex = "8G"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }

    @Test
    fun testParseContentWithSpacesAtWrongPlaces() {
        val hex = "8 C 9 ABC  34"
        val chars = SingleByteHexFormatter().parse(hex)
        Assert.assertEquals(null,chars)
    }



    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    S O U R C E    V A L U E    T E S T S /////////////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateSourceValue(values : List<Char>,formattedValueToSourceValueMap : Map<Int,Int>) {
        val formatter = SingleByteHexFormatter()
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
            2 to 1
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenTwoValues() {
        val values = listOf('A','B')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenEvenNumberOfValues() {
        val values = listOf('A','B','1','2','3','4')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 2,
            4 to 3,
            5 to 4,
            6 to 4,
            7 to 5,
            8 to 6
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOddNumberOfValues() {
        val values = listOf('A','B','1','2','3')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 2,
            4 to 3,
            5 to 4,
            6 to 4,
            7 to 4,
            8 to 5
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    F O R M A T T E D    V A L U E    T E S T S ///////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateFormattedValue(values : List<Char>,sourceValueToFormattedValueMap : Map<Int,Int>) {
        val formatter = SingleByteHexFormatter()
        for(sourceValueToFormattedValue in sourceValueToFormattedValueMap) {
            val expectedResult = sourceValueToFormattedValue.value
            val actualResult = formatter.locateFormattedValue(values,sourceValueToFormattedValue.key)
            Assert.assertEquals("Source value index: ${sourceValueToFormattedValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateFormattedValueWhenEvenNumberOfValues() {
        val values = listOf('A','B','1','2','3','4')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 3,
            3 to 4,
            4 to 6,
            5 to 7,
            6 to 8
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun locateFormattedValueWhenOddNumberOfValues() {
        val values = listOf('A','B','1','2','3')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 3,
            3 to 4,
            4 to 7,
            5 to 8,
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }
}