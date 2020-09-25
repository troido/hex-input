package com.troido.hexinput.formatter

import org.junit.Assert
import org.junit.Test

class PlainByteHexFormatterTest {

    //////////////////////////////////////////////////////////////////////////////
    // F O R M A T T I N G     T E S T S ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testFormatEmptyValuesList() {
        val formatter = PlainByteHexFormatter()
        val formattedContent = formatter.format(listOf())

        Assert.assertEquals("", formattedContent)
    }

    @Test
    fun testFormatSingleValue() {
        val formatter = PlainByteHexFormatter()
        val formattedContent = formatter.format(listOf('1'))

        Assert.assertEquals("01", formattedContent)
    }

    @Test
    fun testFormatTwoValues() {
        val formatter = PlainByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2'))

        Assert.assertEquals("12", formattedContent)
    }

    @Test
    fun testFormatEvenNumberOfValues() {
        val formatter = PlainByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6'))

        Assert.assertEquals("123456", formattedContent)
    }

    @Test
    fun testFormatOddNumberOfValues() {
        val formatter = PlainByteHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5'))

        Assert.assertEquals("123405", formattedContent)
    }


    //////////////////////////////////////////////////////////////////////////////
    // P A R S I N G    T E S T S ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testParseEmptyString() {
        val hex = ""
        val chars = PlainByteHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSingleValue() {
        val hex = "A"
        val chars = PlainByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A'), chars)
    }

    @Test
    fun testParseTwoValues() {
        val hex = "A3"
        val chars = PlainByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3'), chars)
    }

    @Test
    fun testParseManyValues() {
        val hex = "A35478"
        val chars = PlainByteHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4','7','8'), chars)
    }

    @Test
    fun testParseContentWithOneInvalidCharacter() {
        val hex = "25G8"
        val chars = PlainByteHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithMultipleInvalidCharacters() {
        val hex = "2RGQ67j8"
        val chars = PlainByteHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    S O U R C E    V A L U E    T E S T S /////////////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateSourceValue(values : List<Char>,formattedValueToSourceValueMap : Map<Int,Int>) {
        val formatter = PlainByteHexFormatter()
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
    fun testLocateSourceValueWhenOnlyTwoValues() {
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
        val values = listOf('A','B','C','D','4','2')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 5,
            6 to 6
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOddNumberOfValues() {
        val values = listOf('A','B','C','D','4')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 4,
            6 to 5
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    F O R M A T T E D    V A L U E    T E S T S ///////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateFormattedValue(values : List<Char>,sourceValueToFormattedValueMap : Map<Int,Int>) {
        val formatter = PlainByteHexFormatter()
        for(sourceValueToFormattedValue in sourceValueToFormattedValueMap) {
            val expectedResult = sourceValueToFormattedValue.value
            val actualResult = formatter.locateFormattedValue(values,sourceValueToFormattedValue.key)
            Assert.assertEquals("Source value index: ${sourceValueToFormattedValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateFormattedValueWhenEvenNumberOfValues() {
        val values = listOf('A','B','C','D','E','F','1','2')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 5,
            6 to 6,
            7 to 7,
            8 to 8
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun locateFormattedValueWhenOddNumberOfValues() {
        val values = listOf('A','B','C','D','E','F','2')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 5,
            6 to 7,
            7 to 8
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }
}