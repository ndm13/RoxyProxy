package net.miscfolder.roxyproxy.implementations;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import net.miscfolder.roxyproxy.core.ProxyPlugin;

public abstract class I2PProxyPlugin extends ProxyPlugin{
	private final List<String> protocols;

	private I2PProxyPlugin(SocketAddress socketAddress, String... protocols){
		super(new Proxy(Proxy.Type.HTTP, socketAddress));
		this.protocols = Arrays.asList(protocols);
	}

	@Override
	public boolean test(URI uri){
		return uri.getHost() != null && uri.getHost().endsWith(".i2p")
				&& protocols.contains(uri.getScheme());
	}

	public static class HTTP extends I2PProxyPlugin{
		public static final HTTP DEFAULT = new HTTP(new InetSocketAddress("localhost", 4444));
		public HTTP(SocketAddress socketAddress){
			super(socketAddress, "http", "ftp");
		}
	}
	public static class HTTPS extends I2PProxyPlugin{
		public static final HTTPS DEFAULT = new HTTPS(new InetSocketAddress("localhost", 4445));
		public HTTPS(SocketAddress socketAddress){
			super(socketAddress, "https");
		}
	}
	public static class IRC extends I2PProxyPlugin{
		public static final IRC DEFAULT = new IRC(new InetSocketAddress("localhost", 6668));
		public IRC(SocketAddress socketAddress){
			super(socketAddress, "irc");
		}
	}
}
