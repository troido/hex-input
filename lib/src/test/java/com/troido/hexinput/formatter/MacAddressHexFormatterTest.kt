package com.troido.hexinput.formatter

import org.junit.Assert
import org.junit.Test

class MacAddressHexFormatterTest {

    //////////////////////////////////////////////////////////////////////////////
    // F O R M A T T I N G     T E S T S ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testFormatEmptyValuesList() {
        val formatter = MacAddressHexFormatter()
        val formattedContent = formatter.format(listOf())

        Assert.assertEquals("", formattedContent)
    }

    @Test
    fun testFormatSingleValue() {
        val formatter = MacAddressHexFormatter()
        val formattedContent = formatter.format(listOf('1'))

        Assert.assertEquals("1", formattedContent)
    }

    @Test
    fun testFormatTwoValues() {
        val formatter = MacAddressHexFormatter()
        val formattedContent = formatter.format(listOf('1','2'))

        Assert.assertEquals("12", formattedContent)
    }

    @Test
    fun testFormatThreeValues() {
        val formatter = MacAddressHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3'))

        Assert.assertEquals("12:3", formattedContent)
    }

    @Test
    fun testFormatFourValues() {
        val formatter = MacAddressHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4'))

        Assert.assertEquals("12:34", formattedContent)
    }

    @Test
    fun testFormatManyValues() {
        val formatter = MacAddressHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6','7','8','9','A','B'))

        Assert.assertEquals("12:34:56:78:9A:B", formattedContent)
    }



    //////////////////////////////////////////////////////////////////////////////
    // P A R S I N G    T E S T S ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testParseEmptyString() {
        val hex = ""
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSingleValue() {
        val hex = "A"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A'), chars)
    }

    @Test
    fun testParseTwoValues() {
        val hex = "A3"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3'), chars)
    }

    @Test
    fun testParseThreeValues() {
        val hex = "A3:4"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','4'), chars)
    }

    @Test
    fun testParseFourValues() {
        val hex = "A3:4B"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','4','B'), chars)
    }

    @Test
    fun testParseManyValues() {
        val hex = "A3:4B:58:32:4F:CA:9"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','4','B','5','8','3','2','4','F','C','A','9'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSeparatorAfterFirstValue() {
        val hex = "3:54:28"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('3','5','4','2','8'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSeparatorAtTheBeginning() {
        val hex = ":35:42:8"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('3','5','4','2','8'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSeparatorAtTheEnd() {
        val hex = "5:42:8C:"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(listOf('5','4','2','8','C'), chars)
    }

    @Test
    fun testParseSubstringContainingSeparatorOnly() {
        val hex = ":"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseContentWithOneInvalidCharacter() {
        val hex = "25:G8"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithMultipleInvalidCharacters() {
        val hex = "2R:GQ:67:j8"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithSeparatorsInWrongPlaces() {
        val hex = "F8:934:59:824D"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithSeparatorsInWrongPlacesAndInvalidCharacters() {
        val hex = "F8:9g4:5z:8H4D"
        val chars = MacAddressHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }


    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    S O U R C E    V A L U E    T E S T S /////////////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateSourceValue(values : List<Char>,formattedValueToSourceValueMap : Map<Int,Int>) {
        val formatter = MacAddressHexFormatter()
        for(formattedValueToSourceValue in formattedValueToSourceValueMap) {
            val expectedResult = formattedValueToSourceValue.value
            val actualResult = formatter.locateSourceValue(values,formattedValueToSourceValue.key)
            Assert.assertEquals("Formatted value index: ${formattedValueToSourceValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateSourceValueWhenEvenNumberOfValues() {
        val values = listOf('A','B','C','D','E','F','1','2','3','4','5','6')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 2,
            4 to 3,
            5 to 4,
            6 to 4,
            7 to 5,
            8 to 6,
            9 to 6,
            10 to 7,
            11 to 8,
            12 to 8,
            13 to 9,
            14 to 10,
            15 to 10,
            16 to 11,
            17 to 12
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOddNumberOfValues() {
        val values = listOf('A','B','C','D','2')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 2,
            4 to 3,
            5 to 4,
            6 to 4,
            7 to 5,
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOnlyOneValue() {
        val values = listOf('A')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOnlyTwoValues() {
        val values = listOf('A','2')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenNoValues() {
        val values = listOf<Char>()

        val formattedValueToSourceValueMap = mapOf(
            0 to 0
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }



    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    F O R M A T T E D    V A L U E    T E S T S ///////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateFormattedValue(values : List<Char>,sourceValueToFormattedValueMap : Map<Int,Int>) {
        val formatter = MacAddressHexFormatter()
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
            2 to 3,
            3 to 4,
            4 to 6,
            5 to 7,
            6 to 9,
            7 to 10,
            8 to 11
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun testLocateFormattedValueWhenOddNumberOfValues() {
        val values = listOf('A','B','C','D','E','F','1')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 3,
            3 to 4,
            4 to 6,
            5 to 7,
            6 to 9,
            7 to 10
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

}