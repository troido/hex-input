package com.troido.hexinput.formatter

import org.junit.Assert
import org.junit.Test

class PlainValuesHexFormatterTest {

    //////////////////////////////////////////////////////////////////////////////
    // F O R M A T T I N G     T E S T S ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testFormatEmptyValuesList() {
        val formatter = PlainValuesHexFormatter()
        val formattedContent = formatter.format(listOf())

        Assert.assertEquals("", formattedContent)
    }

    @Test
    fun testFormatSingleValue() {
        val formatter = PlainValuesHexFormatter()
        val formattedContent = formatter.format(listOf('1'))

        Assert.assertEquals("1", formattedContent)
    }

    @Test
    fun testFormatTwoValues() {
        val formatter = PlainValuesHexFormatter()
        val formattedContent = formatter.format(listOf('1','2'))

        Assert.assertEquals("12", formattedContent)
    }

    @Test
    fun testFormatManyValues() {
        val formatter = PlainValuesHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','6','4','F','B','1'))

        Assert.assertEquals("1264FB1", formattedContent)
    }



    //////////////////////////////////////////////////////////////////////////////
    // P A R S I N G    T E S T S ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testParseEmptyString() {
        val hex = ""
        val chars = PlainValuesHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSingleValue() {
        val hex = "A"
        val chars = PlainValuesHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A'), chars)
    }

    @Test
    fun testParseTwoValues() {
        val hex = "A3"
        val chars = PlainValuesHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3'), chars)
    }

    @Test
    fun testParseManyValues() {
        val hex = "A35478"
        val chars = PlainValuesHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4','7','8'), chars)
    }

    @Test
    fun testParseContentWithOneInvalidCharacter() {
        val hex = "25G8"
        val chars = PlainValuesHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithMultipleInvalidCharacters() {
        val hex = "2RGQ67j8"
        val chars = PlainValuesHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    S O U R C E    V A L U E    T E S T S /////////////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateSourceValue(values : List<Char>,formattedValueToSourceValueMap : Map<Int,Int>) {
        val formatter = PlainValuesHexFormatter()
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
            1 to 1
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenTwoValues() {
        val values = listOf('A','3')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenManyValues() {
        val values = listOf('A','3','4','9','C','5')

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



    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    F O R M A T T E D    V A L U E    T E S T S ///////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateFormattedValue(values : List<Char>,sourceValueToFormattedValueMap : Map<Int,Int>) {
        val formatter = PlainValuesHexFormatter()
        for(sourceValueToFormattedValue in sourceValueToFormattedValueMap) {
            val expectedResult = sourceValueToFormattedValue.value
            val actualResult = formatter.locateFormattedValue(values,sourceValueToFormattedValue.key)
            Assert.assertEquals("Source value index: ${sourceValueToFormattedValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateFormattedValue() {
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

}