package org.c4c.tiny;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AbstractSSMConfiguration;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.xmemcached.XMemcachedConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;

import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.utils.AddrUtil;

@Configuration
@EnableCaching
@PropertySource("classpath:application.properties")
public class CacheConfig extends AbstractSSMConfiguration {

	@Value("${memcached.servers}")
	private String _servers;

	@Value("${memcached.servers.user}")
	private String _userName;
	
	@Value("${memcached.servers.password}")
	private String _password;
	@Bean
	@Override
	public CacheFactory defaultMemcachedClient() {
		String serverString = _servers;
		List<InetSocketAddress> servers = AddrUtil.getAddresses(serverString);
		AuthInfo authInfo = AuthInfo.plain(_userName, _password);
		Map<InetSocketAddress, AuthInfo> authInfoMap = new HashMap<InetSocketAddress, AuthInfo>();
		for (InetSocketAddress server : servers) {
			authInfoMap.put(server, authInfo);
		}

		final XMemcachedConfiguration conf = new XMemcachedConfiguration();
		conf.setUseBinaryProtocol(true);
		conf.setAuthInfoMap(authInfoMap);

		final CacheFactory cf = new CacheFactory();
		cf.setCacheClientFactory(new MemcacheClientFactoryImpl());
		cf.setAddressProvider(new DefaultAddressProvider(serverString));
		cf.setConfiguration(conf);
		return cf;
	}

}
