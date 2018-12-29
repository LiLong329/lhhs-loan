/**
 * 
 */
package com.lhhs.loan.common.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @author zhangpenghong
 *
 */
public class QRCodeGenerator {
	/**
	 * 
	 */
	public QRCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 * @param qrcodeWidth
	 * @param qrcodeHeight
	 * @return
	 * @throws WriterException
	 */
	public BitMatrix generateQRCode(String content, int qrcodeWidth, int qrcodeHeight) throws WriterException {
		HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight,
				hints);
		return bitMatrix;
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 * @param qrcodeWidth
	 * @param qrcodeHeight
	 * @param fileType
	 * @param filePath
	 * @throws WriterException
	 * @throws IOException
	 */
	public void writeQRCodeToFile(String content, int qrcodeWidth, int qrcodeHeight, String fileType, String filePath)
			throws WriterException, IOException {
		BitMatrix bitMatrix = generateQRCode(content, qrcodeWidth, qrcodeHeight);
		Path qrCodeFileRootPath = Paths.get(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, fileType, qrCodeFileRootPath);
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws WriterException
	 */
	public static void main(String[] args) throws WriterException, IOException {
		// TODO Auto-generated method stub
		QRCodeGenerator qrcg = new QRCodeGenerator();
		qrcg.writeQRCodeToFile("pinkuaixian.com", 200, 200, "png", "/Users/zhangpenghong/Documents/testdata/test.png");
	}

}
