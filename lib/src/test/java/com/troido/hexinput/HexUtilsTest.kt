package com.troido.hexinput

import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException

class HexUtilsTest {

    @Test
    fun testHexToBytesWhenEvenNumberOfValues() {
        val bytes = HexUtils.hexToBytes(listOf('A','B','C','D'))

        Assert.assertEquals(2,bytes.size)
        Assert.assertEquals(0xAB.toByte(),bytes[0])
        Assert.assertEquals(0xCD.toByte(),bytes[1])
    }

    @Test
    fun testHexToBytesWhenOddNumberOfValues() {
        val bytes = HexUtils.hexToBytes(listOf('A','B','C','D','E'))

        Assert.assertEquals(3,bytes.size)
        Assert.assertEquals(0xAB.toByte(),bytes[0])
        Assert.assertEquals(0xCD.toByte(),bytes[1])
        Assert.assertEquals(0x0E.toByte(),bytes[2])
    }

    @Test(expected = IllegalArgumentException::class)
    fun testHexToBytesWhenInvalidHexValues() {
        HexUtils.hexToBytes(listOf('g','2','r','4'))
    }

    @Test
    fun testBytesToHex() {
        val values = HexUtils.bytesToHex(byteArrayOf(0xAB.toByte(),0xCD.toByte(),0x0E.toByte()))
        Assert.assertEquals(listOf('A','B','C','D','0','E'),values)
    }

    @Test
    fun testVerifyHexValuesWhenValidValues() {
        HexUtils.verifyHexValues(listOf('A','B','C','D'))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testVerifyHexValuesWhenInvalidValues() {
        HexUtils.verifyHexValues(listOf('g','2','r','4'))
    }


}