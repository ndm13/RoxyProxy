# RoxyProxy
RoxyProxy is essentially a lightweight `ProxySelector` that allows for
custom "proxy plugins": a pair of `Proxy` and `Predicate` with some
error-logging code tacked on.  It's still in development, but certainly
useable.

The current code contains plugins for I2P (HTTP/HTTPS/IRC) and Tor
Hidden Services, either with the default ports or on custom ports.

The easiest way to get started is to use `RoxyProxy.install(...)` for
the plugins you want.  This handles the `ProxySelector` hooks and keeps
track of the specific reference for you.  You could also manually
install the selector using
`ProxySelector.setDefault(new PluginProxySelector(...))` for more
finite control.

## Current Implementations
 - `I2PProxyPlugin.HTTP`: proxies HTTP and FTP traffic for *.i2p domains
 - `I2PProxyPlugin.HTTPS`: proxies HTTPS traffic for *.i2p domains
 - `I2PProxyPlugin.IRC`: proxies IRC traffic for *.i2p domains
   NOTE: this will probably require a special library for full support
 - `TorProxyPlugin`: proxies all traffic for *.onion domains
 
 Each of these classes has a `.DEFAULT` instance that represents the
 default configuration out of the box for each of these.  If you need
 add another plugin, you can always extend `ProxyPlugin` and set up
 your own rules.  Feel free to submit a pull request if you make
 something you think others would find useful.
