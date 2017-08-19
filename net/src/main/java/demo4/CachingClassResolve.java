package demo4;

import java.util.HashMap;
import java.util.Map;

public class CachingClassResolve implements ClassResolver{
	
	private Map<String, Class<?>> cache;
	private ClassResolver classResolver;
	
	public CachingClassResolve(ClassResolver classResolver) {
		cache = new HashMap<>();
		this.classResolver = classResolver;
	}

	@Override
	public Class<?> resolve(String param) throws ClassNotFoundException {
		Class<?> clazz = cache.get(param);
		if(clazz != null)
			return clazz;
		clazz = classResolver.resolve(param);
		cache.put(param, clazz);
		return clazz;
	}

}
