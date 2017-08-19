package demo4;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

public class CompactObjectInputStream extends ObjectInputStream{

	private final ClassResolver classResolver ;
	
	public CompactObjectInputStream(InputStream in, ClassResolver classResolver) throws IOException {
		super(in);
		this.classResolver = classResolver;
	}
	
	@Override
	protected void readStreamHeader() throws IOException, StreamCorruptedException {
		// TODO Auto-generated method stub
		int type = readByte() & 0xff;
		if(type != 5)
			throw new StreamCorruptedException("Unsupported version: " + type	);
	}
	
	
	@Override
	protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		int type = read();
		if(type < 0)
			throw new EOFException();
		switch (type) {
		case 0:
			return super.readClassDescriptor();
		case 1:
			String name = readUTF();
			Class<?> clazz = this.classResolver.resolve(name);
			return ObjectStreamClass.lookup(clazz);
		default:
			break;
		}
		throw new StreamCorruptedException("Unexpected class descriptor type: " + type);
	}
	
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Class<?> clazz;
		try {
			clazz = this.classResolver.resolve(desc.getName());
		} catch (Exception e) {
			// TODO: handle exception
			clazz = super.resolveClass(desc);
		}
		return clazz;
	}
	
	
	
	

}
