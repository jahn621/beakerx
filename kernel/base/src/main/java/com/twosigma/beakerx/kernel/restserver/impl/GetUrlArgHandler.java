/*
 *  Copyright 2018 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.twosigma.beakerx.kernel.restserver.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twosigma.beakerx.BeakerXClient;

import java.io.IOException;

import static com.twosigma.beakerx.BeakerXClient.URL_ARG;

public class GetUrlArgHandler {


  private ObjectMapper objectMapper;
  private BeakerXClient beakerXClient;

  public GetUrlArgHandler(BeakerXClient beakerXClient) {
    this.beakerXClient = beakerXClient;
    objectMapper = new ObjectMapper();
  }

  public void handle(String data) {
    try {
      String value = getBeakerArgValue(data);
      this.beakerXClient.getMessageQueue(URL_ARG).put((value == null) ? "" : value);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private String getBeakerArgValue(String json) {
    try {
      UrlArgBody value = objectMapper.readValue(json, UrlArgBody.class);
      return value.argValue;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static class UrlArgBody {
    public String argName;
    public String argValue;
    public String url;

    public UrlArgBody() {
    }
  }


}
