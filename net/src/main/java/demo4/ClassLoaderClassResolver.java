package demo4;

public class ClassLoaderClassResolver implements ClassResolver{

	private ClassLoader classloader;
	
	public ClassLoaderClassResolver(ClassLoader loader) {
		this.classloader = loader;
	}
	
	
	@Override
	public Class<?> resolve(String param) throws ClassNotFoundException {
		try {
			return classloader.loadClass(param);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Class.forName(param, false, classloader);
	}

}
