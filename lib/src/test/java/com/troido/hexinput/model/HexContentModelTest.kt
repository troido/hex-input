package com.troido.hexinput.model

import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException

class HexContentModelTest {

    @Test
    fun testSetLimitWhenListStateExceedsTheLimit() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3','4','5'))
        model.setValuesLimit(3)

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }




    @Test
    fun testInsertOneValueIntoEmptyList() {
        val model = HexContentModel()
        model.insertValue(0,'A')

        Assert.assertEquals(listOf('A'),model.getValues())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertOneInvalidValueIntoEmptyList() {
        val model = HexContentModel()
        model.insertValue(0,'z')
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertOneValueIntoEmptyListAtInvalidIndex() {
        val model = HexContentModel()
        model.insertValue(2,'A')
    }

    @Test
    fun testInsertOneValueAtEndOfFilledList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.insertValue(3,'A')

        Assert.assertEquals(listOf('1','2','3','A'),model.getValues())
    }

    @Test
    fun testInsertOneValueAtStartOfFilledList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.insertValue(0,'A')

        Assert.assertEquals(listOf('A','1','2','3'),model.getValues())
    }

    @Test
    fun testInsertOneValueInTheMiddleOfFilledList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.insertValue(2,'A')

        Assert.assertEquals(listOf('1','2','A','3'),model.getValues())
    }

    @Test
    fun testInsertOneValueAtEndOfFilledListWhenLimitReached() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(3)
        model.insertValue(3,'A')

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }

    @Test
    fun testInsertOneValueAtStartOfFilledListWhenLimitReached() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(3)
        model.insertValue(0,'A')

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }

    @Test
    fun testInsertOneValueInTheMiddleOfFilledListWhenLimitReached() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(3)
        model.insertValue(2,'A')

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }




    @Test
    fun testInsertMultipleValuesIntoEmptyList() {
        val model = HexContentModel()
        model.insertValues(0, listOf('A','B','C'))

        Assert.assertEquals(listOf('A','B','C'),model.getValues())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInsertMultipleValuesIntoEmptyListAtInvalidIndex() {
        val model = HexContentModel()
        model.insertValues(2,listOf('A','B','C'))
    }

    @Test
    fun testInsertMultipleValuesAtEndOfFilledList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.insertValues(3,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','3','A','B','C'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesAtStartOfFilledList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.insertValues(0,listOf('A','B','C'))

        Assert.assertEquals(listOf('A','B','C','1','2','3'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesInTheMiddleOfFilledList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.insertValues(2,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','A','B','C','3'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesAtEndOfFilledListWhenLimitReached() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(3)
        model.insertValues(3,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesAtStartOfFilledListWhenLimitReached() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(3)
        model.insertValues(0,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesInTheMiddleOfFilledListWhenLimitReached() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(3)
        model.insertValues(2,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }

    @Test
    fun testInsertingMultipleValuesAtEndOfFilledListWhenNotAllValuesCanBeInsertedBecauseOfLimit() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(5)
        model.insertValues(3,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','3','A','B'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesAtStartOfFilledListWhenNotAllValuesCanBeInsertedBecauseOfLimit() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(5)
        model.insertValues(0,listOf('A','B','C'))

        Assert.assertEquals(listOf('A','B','1','2','3'),model.getValues())
    }

    @Test
    fun testInsertMultipleValuesInTheMiddleOfFilledListWhenNotAllValuesCanBeInsertedBecauseOfLimit() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.setValuesLimit(5)
        model.insertValues(2,listOf('A','B','C'))

        Assert.assertEquals(listOf('1','2','A','B','3'),model.getValues())
    }




    @Test
    fun testSetValues() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))

        Assert.assertEquals(listOf('1','2','3'),model.getValues())
    }

    @Test
    fun testRemoveFirstValue() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.removeValue(0)

        Assert.assertEquals(listOf('2','3'),model.getValues())
    }

    @Test
    fun testRemoveLastValue() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.removeValue(2)

        Assert.assertEquals(listOf('1','2'),model.getValues())
    }

    @Test
    fun testRemoveValueInTheMiddle() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3'))
        model.removeValue(1)

        Assert.assertEquals(listOf('1','3'),model.getValues())
    }

    @Test
    fun testRemoveRangeOfValuesInTheMiddleOfList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3','4','5','6'))
        model.removeRange(1,4)

        Assert.assertEquals(listOf('1','5','6'),model.getValues())
    }

    @Test
    fun testRemoveRangeOfValuesAtStartOfList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3','4','5','6'))
        model.removeRange(0,3)

        Assert.assertEquals(listOf('4','5','6'),model.getValues())
    }

    @Test
    fun testRemoveRangeOfValuesAtEndOfList() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3','4','5','6'))
        model.removeRange(4,6)

        Assert.assertEquals(listOf('1','2','3','4'),model.getValues())
    }

    @Test
    fun testRemoveEmptyRangeOfValues() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3','4','5','6'))
        model.removeRange(2,2)

        Assert.assertEquals(listOf('1','2','3','4','5','6'),model.getValues())
    }




    @Test
    fun testGetValuesAsBytes() {
        val model = HexContentModel()
        model.setValues(listOf('1','2','3','4','5','6'))
        val bytes = model.getValuesAsBytes()

        Assert.assertEquals(3,bytes.size)
        Assert.assertEquals(0x12.toByte(),bytes[0])
        Assert.assertEquals(0x34.toByte(),bytes[1])
        Assert.assertEquals(0x56.toByte(),bytes[2])
    }

    @Test
    fun testSetValuesUsingByteArray() {
        val model = HexContentModel()
        model.setValues(byteArrayOf(0xF3.toByte(),0xC4.toByte(),0x23.toByte(),0x44.toByte()))

        Assert.assertEquals(listOf('F','3','C','4','2','3','4','4'),model.getValues())
    }
}