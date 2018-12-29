package com.lhhs.loan.common.enums;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.MethodUtils;
/**
 *  * <p>Title: </p>
   * <p>Description:解析枚举所需的工具类 </p>
   * <p>Company: lhhs</p>
   * @author xuejinxiong, 
   * @date 2017年6月16日 下午5:42:35
 */
public class EnumUtil {  
  
    public static <T>  List<EnumVo> parseEnum(Class<T> ref){  
    	List<EnumVo> list = new ArrayList<EnumVo>();
        if(ref.isEnum()){  
            T[] ts = ref.getEnumConstants() ;   
            for(T t : ts){  
                String text = getInvokeValue(t, "getText") ;   
                Enum<?> tempEnum = (Enum<?>) t ;  
                if(text == null){  
                    text = tempEnum.name() ;  
                }  
                String code = getInvokeValue(t, "getCode") ;   
                if(code == null){  
                    code = String.valueOf( tempEnum.ordinal() ) ;  
                }  
                EnumVo enumVo = new EnumVo(code , text);
                list.add(enumVo);
            }  
        }  
        return list ;  
    }  
      
    static <T> String getInvokeValue(T t , String methodName){  
        try {  
            Method method = MethodUtils.getAccessibleMethod( t.getClass() , methodName);   
            String text = String.valueOf(method.invoke( t )) ;   
            return text ;  
        } catch (Exception e) {  
            return null ;  
        }  
    }  
}  