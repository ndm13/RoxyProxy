package net.miscfolder.roxyproxy.core;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public abstract class ProxyPlugin implements Predicate<URI>{
	private static final System.Logger LOGGER = System.getLogger(ProxyPlugin.class.getName());

	protected final Proxy proxy;
	protected final Map<URI,IOException> failedConnections = new ConcurrentHashMap<>();

	public ProxyPlugin(Proxy proxy){
		if(proxy == null || proxy.equals(Proxy.NO_PROXY))
			throw new IllegalArgumentException("Proxy must have a socket!");
		this.proxy = proxy;
	}

	public Proxy getProxy(){
		return proxy;
	}

	public void connectFailed(URI destination, IOException exception){
		if((exception instanceof SocketException) &&
				exception.getMessage().startsWith("Connection refused")){
			failedConnections.put(destination, exception);
			LOGGER.log(System.Logger.Level.WARNING,
					()-> this.getClass().getName() + " failed for " + destination.toASCIIString(),
					exception);
		}
	}

	public Map<URI,IOException> getFailedConnections(){
		return Collections.unmodifiableMap(failedConnections);
	}

	public void clearFailedConnections(){
		failedConnections.clear();
	}

	@Override
	public String toString(){
		return this.getClass().getName() + " via " + this.proxy;
	}
}
