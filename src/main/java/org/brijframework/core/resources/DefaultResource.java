package org.brijframework.core.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.brijframework.resources.Resource;

public interface DefaultResource extends Resource{
	
	public default ClassLoader getClassLoader() {
		return this.getClass().getClassLoader();
	}
	
	public default long contentLength() {
		return new String(this.getContent()).length();
	}

	public default long lastModified() {
		return this.getFile().lastModified();
	}

	public default String getFilename() {
		return this.getFile().getName();
	}
	
	public default byte[] getContent() {
		try {
			InputStream stream = (InputStream) this.getInputStream();
			byte[] bs = new byte[stream.available()];
			stream.read(bs);
			stream.close();
			return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public default boolean exists() {
		return this.getFile().exists();
	}
	
	public default URI getURI() throws URISyntaxException {
		return this.getFile().toURI();
	}
	
	@Override
	default InputStream getInputStream() throws IOException {
		return new FileInputStream(this.getFile());
	}
	
	@Override
	default String getExtension() {
		return getFilename().substring(getFilename().lastIndexOf(".")+1, getFilename().length());
	}
	
}