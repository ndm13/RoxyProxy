package net.miscfolder.roxyproxy.tor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;

import net.miscfolder.roxyproxy.core.PluginProxySelector;
import net.miscfolder.roxyproxy.core.ProxyPlugin;
import net.miscfolder.roxyproxy.implementations.TorProxyPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TorProxyPluginTest{
	static ProxyPlugin torProxy = new TorProxyPlugin(new InetSocketAddress("localhost",9050));


	@BeforeAll
	static void setUp(){
		PluginProxySelector selector = new PluginProxySelector(ProxySelector.getDefault());
		selector.add(torProxy);
		ProxySelector.setDefault(selector);
	}

	@Test
	void connectFailed(){
		try{
			new URL("http://zqktlwi4fecvo6ri.onion/wiki/index.php/Main_Page")
					.openConnection().getInputStream().read();
		}catch(IOException ignore){}
		assertTrue(torProxy.getFailedConnections().isEmpty(), "Failed connections present");
	}

	@Test
	void test(){
		assertTrue(torProxy.test(URI.create("protocol://domain.onion/path?query#fragment")), "Dummy URI doesn't match");
		assertFalse(torProxy.test(URI.create("http://proxied.onion.to/")), "Clearnet proxy matches");
	}
}