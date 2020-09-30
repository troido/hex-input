package com.troido.hexinput

import com.troido.hexinput.formatter.BytePairsHexFormatter
import com.troido.hexinput.ui.editor.IHexEditView
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class HexEditControllerTest {

    @Test
    fun testLoadValuesFromText() {
        val values = "AB CD 12 34"
        val viewMock = Mockito.mock(IHexEditView::class.java)
        val controller = HexEditController(viewMock)

        controller.loadValuesFromText(values)

        Assert.assertEquals(listOf('A','B','C','D','1','2','3','4'),controller.model.getValues())
    }

    @Test
    fun testLoadValuesFromInvalidText() {
        val values = "AZ CW j2 34 56"
        val viewMock = Mockito.mock(IHexEditView::class.java)
        val controller = HexEditController(viewMock)

        controller.loadValuesFromText(values)

        Assert.assertEquals(0,controller.model.getValues().size)
    }

    @Test
    fun testOnViewContentChanged() {
        val values = "AB CD 12 34"
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn(values)
        val controller = HexEditController(viewMock)

        controller.onViewContentChanged()

        Assert.assertEquals(listOf('A','B','C','D','1','2','3','4'),controller.model.getValues())
    }

    @Test
    fun testOnViewContentChangedToInvalidContent() {
        val values = "AZ CW j2 34 56"
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn(values)
        val controller = HexEditController(viewMock)

        controller.onViewContentChanged()

        Assert.assertEquals(0,controller.model.getValues().size)
        Mockito.verify(viewMock,Mockito.times(1)).updateContent("")
    }

    @Test
    fun testOnViewContentChangedWhenValuesGetInserted() {
        val viewMock = Mockito.mock(IHexEditView::class.java)

        val controller = HexEditController(viewMock)
        controller.model.setValues(listOf('1','2','3','4','5','6'))

        controller.onViewContentChanged(2,0, "ABC")

        Assert.assertEquals(listOf('1','2','A','B','C','3','4','5','6'),controller.model.getValues())
    }

    @Test
    fun testOnViewContentChangedWhenSubstringOfFormattedValuesGetInserted() {
        val viewMock = Mockito.mock(IHexEditView::class.java)

        val controller = HexEditController(viewMock)
        controller.model.setValues(listOf('1','2','3','4','5','6'))

        controller.onViewContentChanged(2,0, "A 0xBC 0xD")

        Assert.assertEquals(listOf('1','2','A','B','C','D','3','4','5','6'),controller.model.getValues())
    }

    @Test
    fun testOnViewContentChangedWhenInvalidStringGetInserted() {
        val insertedString = "j 8K cL"

        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("12${insertedString}34 56")

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(listOf('1','2','3','4','5','6'))

        val changeStartIndex = 2
        controller.onViewContentChanged(changeStartIndex,0, insertedString)

        Assert.assertEquals(listOf('1','2','3','4','5','6'),controller.model.getValues())
        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("1234 56")
        Mockito.verify(viewMock,Mockito.times(1)).setSelection(changeStartIndex,changeStartIndex)
    }


    @Test
    fun testOnValueInserted() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        val insertionIndex = 2
        controller.model.insertValue(insertionIndex,'C')

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("12C3 4506")
        Mockito.verify(viewMock,Mockito.times(1)).setSelection(insertionIndex+1,insertionIndex+1)

    }

    @Test
    fun testOnValuesInserted() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.model.insertValues(2, listOf('A','B','C'))

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("12AB C345 06")
        Mockito.verify(viewMock,Mockito.times(1)).setSelection(6,6)

    }

    @Test
    fun testOnValueRemoved() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.model.removeValue(2)

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("1245 06")
        Mockito.verify(viewMock,Mockito.times(1)).setSelection(2,2)
    }

    @Test
    fun testOnValuesRemoved() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.model.removeRange(2,5)

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("1206")
        Mockito.verify(viewMock,Mockito.times(1)).setSelection(3,3)
    }

    @Test
    fun testOnRemoveKeyDown() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")
        Mockito.`when`(viewMock.getSelectionStart()).thenReturn(6)
        Mockito.`when`(viewMock.getSelectionEnd()).thenReturn(6)

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.onRemoveKeyDown()

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("1234 06")
        Mockito.verify(viewMock,Mockito.atLeast(1)).setSelection(6,6)
    }

    @Test
    fun testOnRemoveKeyDownWhenPartOfTextSelected() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")
        Mockito.`when`(viewMock.getSelectionStart()).thenReturn(2)
        Mockito.`when`(viewMock.getSelectionEnd()).thenReturn(5)

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.onRemoveKeyDown()

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("1256")
        Mockito.verify(viewMock,Mockito.atLeast(1)).setSelection(2,2)
    }

    @Test
    fun testOnValueTyped() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")
        Mockito.`when`(viewMock.getSelectionStart()).thenReturn(3)
        Mockito.`when`(viewMock.getSelectionEnd()).thenReturn(3)

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.onValueTyped('F')

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("123F 4506")
        Mockito.verify(viewMock,Mockito.atLeast(1)).setSelection(5,5)
    }

    @Test
    fun testOnValueTypedWhenPartOfTextSelected() {
        val viewMock = Mockito.mock(IHexEditView::class.java)
        Mockito.`when`(viewMock.getContent()).thenReturn("1234 56")
        Mockito.`when`(viewMock.getSelectionStart()).thenReturn(3)
        Mockito.`when`(viewMock.getSelectionEnd()).thenReturn(6)

        val values = listOf('1','2','3','4','5','6')

        val controller = HexEditController(viewMock)
        controller.formatter = BytePairsHexFormatter()
        controller.model.setValues(values)

        controller.onValueTyped('F')

        Mockito.verify(viewMock,Mockito.atLeast(1)).updateContent("123F 06")
        Mockito.verify(viewMock,Mockito.atLeast(1)).setSelection(6,6)
    }

}