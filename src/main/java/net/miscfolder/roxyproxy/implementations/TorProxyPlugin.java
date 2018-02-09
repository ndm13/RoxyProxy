package net.miscfolder.roxyproxy.implementations;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;

import net.miscfolder.roxyproxy.core.ProxyPlugin;

public class TorProxyPlugin extends ProxyPlugin{
	public static final TorProxyPlugin DEFAULT = new TorProxyPlugin(new InetSocketAddress("localhost", 9050));

	private final boolean proxyAllTraffic;

	public TorProxyPlugin(SocketAddress socketAddress){
		this(socketAddress, false);
	}

	public TorProxyPlugin(SocketAddress socketAddress, boolean proxyAllTraffic){
		super(new Proxy(Proxy.Type.SOCKS, socketAddress));
		this.proxyAllTraffic = proxyAllTraffic;
	}

	@Override
	public boolean test(URI uri){
		return proxyAllTraffic || (uri.getHost() != null && uri.getHost().endsWith(".onion"));
	}
}
