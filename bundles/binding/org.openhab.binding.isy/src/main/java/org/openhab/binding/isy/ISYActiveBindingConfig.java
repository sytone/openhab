package org.openhab.binding.isy;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains the runtime configuration for the binding as defined in the openhab
 * configuration file.
 * 
 * @author Jon Bullen
 * 
 */
public class ISYActiveBindingConfig {

	private long refreshInterval = 60000;
	private String ipAddress;
	private int port;
	private boolean useUpnp = false;
	private String uuid;
	private String user;
	private String password;
	private final Logger logger = LoggerFactory
			.getLogger(ISYActiveBinding.class);
	
	/**
	 * Creates a new instance of the Active Binding configuration for work with
	 * a ISY unit.
	 * 
	 * @param config
	 *            map of configuration items.
	 */
	public ISYActiveBindingConfig(Map<Object, Object> config) {

		this.logger.info("OpenHab Binding Configuration(refresh='{}',upnp='{}',uuid='{}',ip='{}',port='{}',user='{}',password='{}')",
					config.get("refresh"), config.get("upnp"),config.get("uuid"),config.get("ip"),config.get("port"),config.get("user"),"xxxxxx");

		if (StringUtils.isNotBlank((String) config.get("refresh"))) {
			refreshInterval = Long.parseLong((String) config.get("refresh"));
		}

		if (StringUtils.isNotBlank((String) config.get("user"))
				&& StringUtils.isNotBlank((String) config.get("password"))) {
			this.user = (String) config.get("user");
			this.password = (String) config.get("password");
		} else {
			this.logger.warn("No user/password specified");
		}

		if (StringUtils.isNotBlank((String) config.get("upnp"))) {
			useUpnp = Boolean.parseBoolean((String) config.get("upnp"));
		}

		if (!useUpnp && StringUtils.isNotBlank((String) config.get("uuid"))
				&& StringUtils.isNotBlank((String) config.get("ip"))
				&& StringUtils.isNotBlank((String) config.get("port"))) {

			this.uuid = (String) config.get("uuid");
			this.ipAddress = (String) config.get("ip");

			try {
				this.port = Integer.parseInt((String) config.get("port"));
			} catch (NumberFormatException ne) {
				this.logger.error("Not a valid port number");
				throw new RuntimeException("Not a valid port number", ne);
			}

			if (this.port < 0 || this.port > 65535) {
				this.logger.error("Port out of range (0-65535)");
				throw new NumberFormatException("Invalid port");
			}

		} else {
			this.logger.info("Using UPNP for ISY connection.");
		}
	}

	/**
	 * 
	 * @return the refresh interval which is used to poll values from the isy
	 *         server
	 */
	public long getRefreshInterval() {
		return refreshInterval;
	}

	/**
	 * 
	 * @return the IP address of the ISY router
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * 
	 * @return the port of the REST API of the ISY router
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * @return If true, use UPNP without credentials
	 */
	public boolean isUseUpnp() {
		return useUpnp;
	}

	/**
	 * 
	 * @return UUID of the ISY
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 
	 * @return User name for authentication with the ISY 994i
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @return User password for authentication with the ISY 994i
	 */
	public String getPassword() {
		return password;
	}
}
