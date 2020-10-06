package com.troido.hexinput.formatter

import org.junit.Assert
import org.junit.Test

class BytePairsHexFormatterTest {

    //////////////////////////////////////////////////////////////////////////////
    // F O R M A T T I N G     T E S T S ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testFormatEmptyValuesList() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf())

        Assert.assertEquals("", formattedContent)
    }

    @Test
    fun testFormatSingleValue() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1'))

        Assert.assertEquals("01", formattedContent)
    }

    @Test
    fun testFormatTwoValues() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2'))

        Assert.assertEquals("12", formattedContent)
    }

    @Test
    fun testFormatThreeValues() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3'))

        Assert.assertEquals("1203", formattedContent)
    }

    @Test
    fun testFormatFourValues() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4'))

        Assert.assertEquals("1234", formattedContent)
    }

    @Test
    fun testFormatValuesWhenNumberOfValuesModuloFourEqualsOne() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5'))

        Assert.assertEquals("1234 05", formattedContent)
    }

    @Test
    fun testFormatValuesWhenNumberOfValuesModuloFourEqualsTwo() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6'))

        Assert.assertEquals("1234 56", formattedContent)
    }

    @Test
    fun testFormatValuesWhenNumberOfValuesModuloFourEqualsThree() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6','7'))

        Assert.assertEquals("1234 5607", formattedContent)
    }

    @Test
    fun testFormatValuesWhenNumberOfValuesDivisibleByFour() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6','7','8'))

        Assert.assertEquals("1234 5678", formattedContent)
    }

    @Test
    fun testFormatManyValues() {
        val formatter = BytePairsHexFormatter()
        val formattedContent = formatter.format(listOf('1','2','3','4','5','6','7','8','9','A','B','C'))

        Assert.assertEquals("1234 5678 9ABC", formattedContent)
    }



    //////////////////////////////////////////////////////////////////////////////
    // P A R S I N G    T E S T S ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Test
    fun testParseEmptyString() {
        val hex = ""
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseSingleValue() {
        val hex = "A"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A'), chars)
    }

    @Test
    fun testParseTwoValues() {
        val hex = "A3"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3'), chars)
    }

    @Test
    fun testParseThreeValues() {
        val hex = "A35"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5'), chars)
    }

    @Test
    fun testParseFourValues() {
        val hex = "A354"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4'), chars)
    }

    @Test
    fun testParseValuesWhenNumberOfValuesModuloFourEqualsOne() {
        val hex = "A354 2"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4','2'), chars)
    }

    @Test
    fun testParseValuesWhenNumberOfValuesModuloFourEqualsTwo() {
        val hex = "A354 28"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4','2','8'), chars)
    }

    @Test
    fun testParseValuesWhenNumberOfValuesModuloFourEqualsThree() {
        val hex = "A354 289"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4','2','8','9'), chars)
    }

    @Test
    fun testParseValuesWhenNumberOfValuesDivisibleByFour() {
        val hex = "A354 289C"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('A','3','5','4','2','8','9','C'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSpaceAfterThirdValue() {
        val hex = "354 28"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('3','5','4','2','8'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSpaceAfterSecondValue() {
        val hex = "54 286"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('5','4','2','8','6'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSpaceAfterFirstValue() {
        val hex = "5 2864"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('5','2','8','6','4'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSpaceAtTheBeginning() {
        val hex = " 2864 5"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('2','8','6','4','5'), chars)
    }

    @Test
    fun testParseSubstringOfFiveValuesWithSpaceAfterForthValue() {
        val hex = "2864 5"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('2','8','6','4','5'), chars)
    }

    @Test
    fun testParseSubstringOfManyValues() {
        val hex = "234 5678 9ABC DE"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(listOf('2','3','4','5','6','7','8','9','A','B','C','D','E'), chars)
    }

    @Test
    fun testParseSubstringContainingSpaceOnly() {
        val hex = " "
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(0, chars?.size)
    }

    @Test
    fun testParseContentWithOneInvalidCharacter() {
        val hex = "25 G8"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithMultipleInvalidCharacters() {
        val hex = "2R GQ67 j8"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithSpacesInWrongPlaces() {
        val hex = "F8 934 59 824D"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }

    @Test
    fun testParseContentWithSpacesInWrongPlacesAndInvalidCharacters() {
        val hex = "F8 9T4 5h 8rOD"
        val chars = BytePairsHexFormatter().parse(hex)
        Assert.assertEquals(null, chars)
    }



    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    S O U R C E    V A L U E    T E S T S /////////////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateSourceValue(values : List<Char>,formattedValueToSourceValueMap : Map<Int,Int>) {
        val formatter = BytePairsHexFormatter()
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
    fun testLocateSourceValueWhenOnlyThreeValues() {
        val values = listOf('A','B','C')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 2,
            4 to 3
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenOnlyFourValues() {
        val values = listOf('A','B','C','D')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenNumberOfValuesDivisibleByFour() {
        val values = listOf('A','B','C','D','E','F','1','2','3','4','5','6')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 4,
            6 to 5,
            7 to 6,
            8 to 7,
            9 to 8,
            10 to 8,
            11 to 9,
            12 to 10,
            13 to 11,
            14 to 12
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenNumberOfValuesModuloFourEqualsThree() {
        val values = listOf('A','B','C','D','E','F','2')

        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 4,
            6 to 5,
            7 to 6,
            8 to 6,
            9 to 7
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenNumberOfValuesModuloFourEqualsTwo() {
        val values = listOf('A','B','C','D','E','F')
        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 4,
            6 to 5,
            7 to 6
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }

    @Test
    fun testLocateSourceValueWhenNumberOfValuesModuloFourEqualsOne() {
        val values = listOf('A','B','C','D','E')
        val formattedValueToSourceValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 4,
            6 to 4,
            7 to 5
        )

        testLocateSourceValue(values,formattedValueToSourceValueMap)
    }




    //////////////////////////////////////////////////////////////////////////////
    // L O C A T I N G    F O R M A T T E D    V A L U E    T E S T S ///////////
    ////////////////////////////////////////////////////////////////////////////

    private fun testLocateFormattedValue(values : List<Char>,sourceValueToFormattedValueMap : Map<Int,Int>) {
        val formatter = BytePairsHexFormatter()
        for(sourceValueToFormattedValue in sourceValueToFormattedValueMap) {
            val expectedResult = sourceValueToFormattedValue.value
            val actualResult = formatter.locateFormattedValue(values,sourceValueToFormattedValue.key)
            Assert.assertEquals("Source value index: ${sourceValueToFormattedValue.key}",expectedResult, actualResult)
        }
    }

    @Test
    fun testLocateFormattedValueWhenNumberOfValuesDivisibleByFour() {
        val values = listOf('A','B','C','D','E','F','1','2')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 5,
            5 to 6,
            6 to 7,
            7 to 8,
            8 to 9
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun locateFormattedValueWhenNumberOfValuesModuloFourEqualsThree() {
        val values = listOf('A','B','C','D','E','F','2')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 5,
            5 to 6,
            6 to 8,
            7 to 9
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun locateFormattedValueWhenNumberOfValuesModuloFourEqualsTwo() {
        val values = listOf('A','B','C','D','E','F')
        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 5,
            5 to 6,
            6 to 7
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }

    @Test
    fun locateFormattedValueWhenNumberOfValuesModuloFourEqualsOne() {
        val values = listOf('A','B','C','D','E')

        val sourceValueToFormattedValueMap = mapOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 6,
            5 to 7
        )

        testLocateFormattedValue(values,sourceValueToFormattedValueMap)
    }
}