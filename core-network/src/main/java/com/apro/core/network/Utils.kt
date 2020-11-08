package com.apro.core.network

import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*


object Utils {


  /**
   * Returns MAC address of the given interface name.
   * @param interfaceName eth0, wlan0 or NULL=use first interface
   * @return  mac address or empty string
   */
  fun getMACAddress(interfaceName: String?): String {
    try {
      val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
      for (intf in interfaces) {
        if (interfaceName != null) {
          if (!intf.name.equals(interfaceName, ignoreCase = true)) continue
        }
        val mac = intf.hardwareAddress ?: return ""
        val buf = StringBuilder()
        for (idx in mac.indices) buf.append(String.format("%02X:", mac[idx]))
        if (buf.isNotEmpty()) buf.deleteCharAt(buf.length - 1)
        return buf.toString()
      }
    } catch (ex: Exception) {
    } // for now eat exceptions
    return ""
    /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
  }

  /**
   * Get IP address from first non-localhost interface
   * @param useIPv4  true=return ipv4, false=return ipv6
   * @return  address or empty string
   */
  fun getIPAddress(useIPv4: Boolean): String {
    try {
      val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
      for (intf in interfaces) {
        val addrs = Collections.list(intf.inetAddresses)
        for (addr in addrs) {
          if (!addr.isLoopbackAddress) {
            val sAddr = addr.hostAddress.toUpperCase(Locale.getDefault())
            val isIPv4 = addr is Inet4Address
            if (useIPv4) {
              if (isIPv4) return sAddr
            } else {
              if (!isIPv4) {
                val delim = sAddr.indexOf('%') // drop ip6 port suffix
                return if (delim < 0) sAddr else sAddr.substring(0, delim)
              }
            }
          }
        }
      }
    } catch (ex: Exception) {
    } // for now eat exceptions
    return ""
  }
}