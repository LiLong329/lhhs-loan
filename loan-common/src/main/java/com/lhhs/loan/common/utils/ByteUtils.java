package com.lhhs.loan.common.utils;

import java.util.Arrays;

public class ByteUtils {
	/**
	 * convert string to byte array,if in is null��then return new byte[0]
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] stringToBytes(String in) {
		if (in == null)
			return new byte[0];
		return in.getBytes();

	}

	/**
	 * convert string to byte[],if in is null,then return length is len and fill
	 * with ASIIC 0
	 * 
	 * @param in
	 * @param len
	 *            length of return byte[],if array real length is less len then
	 *            make for a loss with byte=0
	 * @return
	 */
	public static byte[] strToBytes(String in, int len) {
		byte[] tempBuff = null;
		if (in == null) {
			tempBuff = new byte[len];
			Arrays.fill(tempBuff, (byte) 0);
			return tempBuff;
		}
		byte[] inBuff = in.getBytes();
		if (len == 0)
			return inBuff;
		else {
			tempBuff = new byte[len];
			Arrays.fill(tempBuff, (byte) 0);

			int iCount = Math.min(len, inBuff.length);
			for (int i = 0; i < iCount; i++) {
				tempBuff[i] = inBuff[i];
			}
		}
		return tempBuff;
	}

	/**
	 * append len byte of dest byte[] to src��if count of byte in dest is less
	 * len�� then make for a loss with ASIIC 0
	 * 
	 * @param src
	 * @param dest
	 * @param len
	 * @return
	 */

	public static byte[] bytesAppend(byte[] src, byte[] dest, int len) {
		if (dest == null)
			return src;
		int srcLen = 0;
		if (src != null) {
			srcLen = src.length;
		}

		int iCount = Math.min(len, dest.length);
		if (len == 0)
			iCount = dest.length;
		byte[] outBuff = new byte[srcLen + iCount];
		for (int i = 0; i < srcLen; i++)
			outBuff[i] = src[i];

		for (int i = 0; i < iCount; i++)
			outBuff[i + srcLen] = dest[i];
		return outBuff;

	}

	/**
	 * replace the bytes starting from iStart in src with subBytes��
	 * 
	 * @param src
	 * @param subBytes
	 * @param iStart
	 * @return
	 */
	public static byte[] bytesReplace(byte[] src, byte[] subBytes, int iStart) {
		if (src == null || subBytes == null)
			return src;
		if (iStart >= src.length) {
			return src;
		}
		int iCount = 0;
		iCount = Math.min(src.length - iStart, subBytes.length);
		for (int i = 0; i < iCount; i++) {
			src[iStart + i] = subBytes[i];

		}
		return src;

	}

	/**
	 * convert byte[] to string
	 * 
	 * @param in
	 * @return
	 */
	public static String bytesToString(byte[] in) {
		String s = null;
		if (in == null)
			s = null;
		try {
			s = (new String(in)).trim();
		} catch (Exception e) {

		}
		return s;
	}

	/**
	 * get sub byte[] from source byte[]
	 * 
	 * @param inBuff
	 *            source byte[]
	 * @param offset
	 * @param len
	 *            intercept length,if length of inBuff is less len, then,make
	 *            for a loss with byte=0 in end
	 * @return
	 */
	public static byte[] subBytes(byte[] inBuff, int offset, int len) {

		if (inBuff == null || offset >= inBuff.length) {
			return new byte[0];
		}

		int iCount = Math.min(len, inBuff.length - offset);
		if (len == 0)
			iCount = inBuff.length - offset;
		byte[] b = new byte[iCount];
		Arrays.fill(b, (byte) 0);
		for (int i = 0; i < iCount; i++) {
			b[i] = inBuff[i + offset];
		}
		return b;
	}

	/**
	 * replace char filterChar with string replaceStr
	 */
	public static String filter(String src, char filterChar, String replaceStr) {

		if (src == null)
			return (null);

		char content[] = new char[src.length()];
		src.getChars(0, src.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			if (content[i] == filterChar) {
				if (replaceStr != null && replaceStr.length() != 0)
					result.append(replaceStr);
			} else
				result.append(content[i]);

		}
		return (result.toString());

	}
}
