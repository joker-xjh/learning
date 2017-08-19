package demo4;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

public class CompactObjectOutputStream extends ObjectOutputStream{
	
	static final int TYPE_FAT_DESCRIPTOR = 0;
	static final int TYPE_THIN_DESCRIPTOR = 1;
	
	public CompactObjectOutputStream(OutputStream outputStream) throws IOException {
		super(outputStream);
	}
	
	@Override
	protected void writeStreamHeader() throws IOException {
		// TODO Auto-generated method stub
		writeByte(5);
	}
	
	@Override
	protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
		// TODO Auto-generated method stub
		Class<?> clazz = desc.forClass();
		if(clazz.isArray() || clazz.isPrimitive()
		   || clazz.isInterface() || desc.getSerialVersionUID() == 0L) {
			write(0);
			super.writeClassDescriptor(desc);
			return;
		}
		write(1);
		writeUTF(desc.getName());
	}

}
