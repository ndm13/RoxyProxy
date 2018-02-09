package net.miscfolder.roxyproxy.i2p;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;

import net.miscfolder.roxyproxy.core.PluginProxySelector;
import net.miscfolder.roxyproxy.core.ProxyPlugin;
import net.miscfolder.roxyproxy.implementations.I2PProxyPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class I2PHttpProxyPluginTest{
	private static ProxyPlugin plugin = I2PProxyPlugin.HTTP.DEFAULT;

	@BeforeAll
	static void setUp(){
		PluginProxySelector selector = new PluginProxySelector(ProxySelector.getDefault());
		selector.add(plugin);
		ProxySelector.setDefault(selector);
	}

	@Test
	void connectFailed(){
		try{
			new URL("http://junk.i2p").openConnection().getInputStream().read();
		}catch(IOException ignore){}
		assertTrue(plugin.getFailedConnections().isEmpty(), "Failed connections exist");
		plugin.clearFailedConnections();
	}

	@Test
	void test(){
		assertTrue(plugin.test(URI.create("http://test.i2p/")), "Failed for test URI");
		assertFalse(plugin.test(URI.create("https://secure.i2p")), "Passed https");
		assertFalse(plugin.test(URI.create("ftp://site.net")), "Passed non-i2p");
		assertTrue(plugin.test(URI.create("ftp://test.i2p")), "Failed FTP");
	}
}