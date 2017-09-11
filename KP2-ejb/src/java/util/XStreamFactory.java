package util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamFactory {

	public static Object get(Class<?> class1, String jsString){
		XStream xstream = new XStream(new JettisonMappedXmlDriver()){
	        protected MapperWrapper wrapMapper(MapperWrapper next) {
	            return new MapperWrapper(next) {
	                public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn, String fieldName) {
	                    try {
	                        return definedIn != Object.class || realClass(fieldName) != null;
	                    } catch(CannotResolveClassException cnrce) {
	                        return false;
	                    }
	                }
	            };
	        }
	    };
		xstream.alias("myname", class1);
		return xstream.fromXML("{myname:"+jsString+"}");
	}
}
