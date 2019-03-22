package org.ibase4j.core.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseModel;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.sql.Timestamp;
import java.util.*;

/**
 * 常见的辅助类
 * 
 * @author ShenHuaJie
 * @since 2011-11-08
 */
public final class DataUtil {
	private DataUtil() {
	}

	/**
	 * 十进制字节数组转十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static final String byte2hex(byte[] b) { // 一个字节数，转成16进制字符串
		StringBuilder hs = new StringBuilder(b.length * 2);
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1){
				hs.append("0").append(stmp);
			}else{
				hs.append(stmp);
			}
		}
		return hs.toString(); // 转成大写
	}

	/**
	 * 十六进制字符串转十进制字节数组
	 * 
	 * @param hs
	 * @return
	 */
	public static final byte[] hex2byte(String hs) {
		byte[] b = hs.getBytes();
		if ((b.length % 2) != 0){
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个十进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 这个方法可以通过与某个类的class文件的相对路径来获取文件或目录的绝对路径。 通常在程序中很难定位某个相对路径，特别是在B/S应用中。
	 * 通过这个方法，我们可以根据我们程序自身的类文件的位置来定位某个相对路径。
	 * 比如：某个txt文件相对于程序的Test类文件的路径是../../resource/test.txt，
	 * 那么使用本方法Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
	 * 得到的结果是txt文件的在系统中的绝对路径。
	 * 
	 * @param relatedPath 相对路径
	 * @param cls 用来定位的类
	 * @return 相对路径所对应的绝对路径
	 * @throws IOException 因为本方法将查询文件系统，所以可能抛出IO异常
	 */
	public static final String getFullPathRelateClass(String relatedPath, Class<?> cls) {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		try {
			path = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 获取class文件所在绝对路径
	 * 
	 * @param cls
	 * @return
	 * @throws IOException
	 */
	public static final String getPathFromClass(Class<?> cls) {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
					System.err.println(e.getMessage());
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			try {
				path = file.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj 待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static final boolean isEmpty(Object pObj) {
		if (pObj == null){
			return true;
		}
		if (pObj == ""){
			return true;
		}
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection<?>) {
			if (((Collection<?>) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map<?, ?>) {
			if (((Map<?, ?>) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj 待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static final boolean isNotEmpty(Object pObj) {
		if (pObj == null){
			return false;
		}
		if (pObj == ""){
			return false;
		}
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection<?>) {
			if (((Collection<?>) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map<?, ?>) {
			if (((Map<?, ?>) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * JS输出含有\n的特殊处理
	 * 
	 * @param pStr
	 * @return
	 */
	public static final String replace4JsOutput(String pStr) {
		pStr = pStr.replace("\r\n", "<br/>&nbsp;&nbsp;");
		pStr = pStr.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		pStr = pStr.replace(" ", "&nbsp;");
		return pStr;
	}

	/**
	 * 分别去空格
	 * 
	 * @param paramArray
	 * @return
	 */
	public static final String[] trim(String[] paramArray) {
		if (ArrayUtils.isEmpty(paramArray)) {
			return paramArray;
		}
		String[] resultArray = new String[paramArray.length];
		for (int i = 0; i < paramArray.length; i++) {
			String param = paramArray[i];
			resultArray[i] = StringUtils.trim(param);
		}
		return resultArray;
	}

	/**
	 * 获取类的class文件位置的URL
	 * 
	 * @param cls
	 * @return
	 */
	private static URL getClassLocationURL(final Class<?> cls) {
		if (cls == null){
			throw new IllegalArgumentException("null input: cls");
		}
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			if (cs != null){
				result = cs.getLocation();
			}
			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip")){
							result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
						}else if (new File(result.getFile()).isDirectory()){
							result = new URL(result, clsAsResource);
						}
					} catch (MalformedURLException ignore) {
						System.err.println(ignore.getMessage());
					}
				}
			}
		}
		if (result == null) {
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}

	/** 初始化设置默认值 */
	public static final <K> K ifNull(K k, K defaultValue) {
		if (k == null) {
			return defaultValue;
		}
		return k;
	}

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @param strs 属性名
     * @param isContains true只校验strs中的字段，false排除校验strs指定的属性
     * @return  e.g {name={newValue=2, oldValue=1}}
     */
	@SuppressWarnings("rawtypes")
	public static Map<String, Map<String,Object>> compareEntityFields(Object oldObject, Object newObject ,String [] strs,boolean isContains) {
		Map<String, Map<String, Object>> map = null;

		try{
			/**
			 * 只有两个对象都是同一类型的才有可比性
			 */
			if (oldObject.getClass() == newObject.getClass()) {
				map = new HashMap<String, Map<String,Object>>();

				Class clazz = oldObject.getClass();
				//获取object的所有属性
				PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,Object.class).getPropertyDescriptors();

				for (PropertyDescriptor pd : pds) {
					//遍历获取属性名
					String name = pd.getName();

					if((isContains && !Arrays.asList(strs).contains(name))||(!isContains && Arrays.asList(strs).contains(name))){
						continue;
					}

					//获取属性的get方法
					Method readMethod = pd.getReadMethod();

					// 在oldObject上调用get方法等同于获得oldObject的属性值
					Object oldValue = readMethod.invoke(oldObject);
					// 在newObject上调用get方法等同于获得newObject的属性值
					Object newValue = readMethod.invoke(newObject);

					if(oldValue instanceof List){
						continue;
					}

					if(newValue instanceof List){
						continue;
					}

					if(oldValue instanceof Timestamp){
						oldValue = new Date(((Timestamp) oldValue).getTime());
					}

					if(newValue instanceof Timestamp){
						newValue = new Date(((Timestamp) newValue).getTime());
					}

					if(oldValue == null && newValue == null){
						continue;
					}else if(oldValue == null && newValue != null){
						Map<String,Object> valueMap = new HashMap<String,Object>();
						valueMap.put("oldValue",oldValue);
						valueMap.put("newValue",newValue);

						map.put(name, valueMap);

						continue;
					}

					// 比较这两个值是否相等,不等就可以放入map了
					if (!oldValue.equals(newValue)) {
						Map<String,Object> valueMap = new HashMap<String,Object>();
						valueMap.put("oldValue",oldValue);
						valueMap.put("newValue",newValue);

						map.put(name, valueMap);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return map;
	}

    /**
     * 判断两个对象是否相等
     * @param oldObject 对象1
     * @param newObject 对象2
     * @param strs 属性名
     * @param isContains true只校验strs中的字段，false排除校验strs指定的属性
     * @return true 两实体认为一致
     */
    public static boolean equalsEximEntity(Object oldObject, Object newObject,String [] strs,boolean isContains) {
        if(null == oldObject || null == newObject){
            return false;
        }
        Map<String, Map<String,Object>> resultMap=compareEntityFields(oldObject,newObject,strs,isContains);
        if(resultMap.size()>0) {
            return false;
        }else {
            return true;
        }
    }

    /**
     * 两个对象集合比较，做差集
     * @param newList 变更后的实体List
     * @param oldList 原库中的实体List
     * @param strs 比较的参数列表
     * @return map.get("addEntityList") 为list1存在，list2不存在，map.get("updateEntityList") 需要update的entityList，map.get("delIDList")为需要remove的id List;
     */
    public static Map getMinusMap(List newList,List oldList,String [] strs,boolean isContains){

        Map map = new HashMap();
        List<String> idlst1 = new ArrayList<String>();
        Set<String> idset2 = new HashSet<String>();
        List addList = new ArrayList();
        List addEntityList = new ArrayList();
        List ameList = new ArrayList();
        List delList = new ArrayList();
        //去除null元素
		newList.removeAll(Collections.singleton(null));
		oldList.removeAll(Collections.singleton(null));
        for(Object o1 : newList){

            idlst1.add(((BaseModel)o1).getId_());

            for(Object o2 : oldList){

                idset2.add(((BaseModel)o2).getId_());

                if(equalsEximEntity(o1,o2,strs,isContains)){
                    break;
                }else{
                    if(((BaseModel)o1).getId_().equals(((BaseModel)o2).getId_())){
                        ameList.add(o1);
                    }
                }

            }
        }
        List<String> idlst2 = new ArrayList<String>(idset2);
        addList.addAll(idlst1);
        addList.removeAll(idlst2);
        delList.addAll(idlst2);
        delList.removeAll(idlst1);
        for(Object o1 : newList){
            if(addList.contains(((BaseModel)o1).getId_())){
                addEntityList.add(o1);
            }
        }
		map.put("addEntityList",addEntityList);
		map.put("updateEntityList",ameList);
		map.put("delIDList",delList);
        return map;
    }
	public static boolean copyFile(File sourcefile,File targetFile){
        FileInputStream input = null;
        BufferedInputStream inbuff = null;
        FileOutputStream out = null;
        BufferedOutputStream outbuff = null;

        try {
            //新建文件输入流并对它进行缓冲
            input = new FileInputStream(sourcefile);
            inbuff = new BufferedInputStream(input);

            //新建文件输出流并对它进行缓冲
            out = new FileOutputStream(targetFile);
            outbuff = new BufferedOutputStream(out);

            //缓冲数组
            byte[] b=new byte[1024*5];
            int len=0;
            while((len=inbuff.read(b))!=-1){
                outbuff.write(b, 0, len);
            }

            //刷新此缓冲的输出流
            outbuff.flush();

            //关闭流
            inbuff.close();
            outbuff.close();
            out.close();
            input.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inbuff != null) {
                try {
                    inbuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outbuff != null) {
                try {
                    outbuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}