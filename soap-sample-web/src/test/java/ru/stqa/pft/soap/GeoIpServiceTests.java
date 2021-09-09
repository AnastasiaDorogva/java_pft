package ru.stqa.pft.soap;

import com.lavasoft.GetIpLocation20;
import org.testng.annotations.Test;

public class GeoIpServiceTests {

  @Test
  public void testMyIP() {
   new GetIpLocation20().setSIp("185.127.226.24");

  }
}
