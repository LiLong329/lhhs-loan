package com.lhhs.loan.common.shared.zip;

import java.io.BufferedInputStream;  
import java.io.DataInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipOutputStream;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
  
public class ZipUtils {  
    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);  
          
    private ZipUtils(){};  
   /** 
     * 创建ZIP文件 
     * @param sourcePath 文件或文件夹路径 
     * @param zipPath 生成的zip文件存在路径（包括文件名） 
     */  
    public static void createZip(String sourcePath, String zipPath) {  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        try {  
            fos = new FileOutputStream(zipPath);  
            zos = new ZipOutputStream(fos);  
            File file = new File(sourcePath);
            //压缩包去除当前根目录
            if(file.exists() && file.isDirectory()){
            	File [] files=file.listFiles();  
                for(File f:files){  
                    writeZip(f, "", zos);  
                }  
            }else{
            	 writeZip(file, "", zos);  
            }
            
        } catch (FileNotFoundException e) {  
            log.error("创建ZIP文件失败",e);  
        } finally {  
            try {  
                if (zos != null) {  
                    zos.close();  
                }  
            } catch (IOException e) {  
                log.error("创建ZIP文件失败",e);  
            }  
  
        }  
    }  
      
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {  
        if(file.exists()){  
            //处理文件夹  
            if(file.isDirectory()){  
                parentPath+=file.getName()+File.separator;  
                File [] files=file.listFiles();  
                for(File f:files){  
                    writeZip(f, parentPath, zos);  
                }  
            }else{  
                FileInputStream fis=null;  
                DataInputStream dis=null;  
                try {  
                    fis=new FileInputStream(file);  
                    dis=new DataInputStream(new BufferedInputStream(fis));  
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());  
                    
                    zos.putNextEntry(ze);  
                    //添加编码，如果不添加，当文件以中文命名的情况下，会出现乱码  
                    // ZipOutputStream的包一定是apache的ant.jar包。JDK也提供了打压缩包，但是不能设置编码  
                    zos.setEncoding("GBK");  
                    byte [] content=new byte[1024];  
                    int len;  
                    while((len=fis.read(content))!=-1){  
                        zos.write(content,0,len);  
                        zos.flush();  
                    }  
                } catch (FileNotFoundException e) {  
                    log.error("创建ZIP文件失败",e);  
                } catch (IOException e) {  
                    log.error("创建ZIP文件失败",e);  
                }finally{  
                    try {  
                        if(dis!=null){  
                            dis.close();  
                        }  
                    }catch(IOException e){  
                        log.error("创建ZIP文件失败",e);  
                    }  
                }  
            }  
        }  
    }
    
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}  