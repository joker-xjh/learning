package demo4;

public interface ClassResolver {
	
	Class<?> resolve(String param) throws ClassNotFoundException;

}
