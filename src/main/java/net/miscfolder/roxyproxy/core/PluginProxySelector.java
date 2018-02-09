package net.miscfolder.roxyproxy.core;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class PluginProxySelector extends ProxySelector{
	private final ProxySelector fallback;
	private final Map<SocketAddress,ProxyPlugin> plugins = Collections.synchronizedMap(new HashMap<>());

	public PluginProxySelector(ProxySelector fallback){
		this.fallback = fallback;
	}

	@Override
	public List<Proxy> select(URI uri){
		List<Proxy> proxies = plugins.values().stream()
				.filter(p->p.test(uri))
				.map(ProxyPlugin::getProxy)
				.collect(Collectors.toCollection(LinkedList::new));
		proxies.addAll(fallback.select(uri));
		return proxies;
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe){
		Optional.ofNullable(plugins.get(sa)).ifPresent(p->p.connectFailed(uri, ioe));
	}

	public void add(ProxyPlugin... plugins){
		for(ProxyPlugin plugin : plugins)
			this.plugins.put(Objects.requireNonNull(plugin.getProxy()).address(), plugin);
	}

	public void remove(ProxyPlugin plugin){
		new HashSet<>(this.plugins.entrySet()).forEach(e->{
			if(e.getValue().equals(plugin))
				this.plugins.remove(e.getKey(),e.getValue());
		});
	}

	public Collection<ProxyPlugin> getPlugins(){
		return Collections.unmodifiableCollection(plugins.values());
	}
}
