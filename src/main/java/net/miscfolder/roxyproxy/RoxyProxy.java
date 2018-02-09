package net.miscfolder.roxyproxy;

import java.net.ProxySelector;

import net.miscfolder.roxyproxy.core.PluginProxySelector;
import net.miscfolder.roxyproxy.core.ProxyPlugin;

public class RoxyProxy{
	private static PluginProxySelector selector = null;

	public synchronized static void install(ProxyPlugin... plugins){
		if(selector == null){
			selector = new PluginProxySelector(ProxySelector.getDefault());
			ProxySelector.setDefault(selector);
		}
		selector.add(plugins);
		ProxySelector.setDefault(selector);
	}


}
