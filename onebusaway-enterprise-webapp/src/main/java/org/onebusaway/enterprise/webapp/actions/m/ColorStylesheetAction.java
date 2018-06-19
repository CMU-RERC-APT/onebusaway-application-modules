/**
 * Copyright (C) 2011 Metropolitan Transportation Authority
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.enterprise.webapp.actions.m;

import org.onebusaway.enterprise.webapp.actions.OneBusAwayEnterpriseActionSupport;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ColorStylesheetAction extends OneBusAwayEnterpriseActionSupport {

  private static final long serialVersionUID = 1L;

  private List<String> _colors = new ArrayList<String>();
  
  public void setColors(String c) {
    if(c == null) {
      return;
    }
    
    String[] colors = c.split(",");
    for(String color : colors) {
      if(StringUtils.isAlphanumeric(color)) {
        _colors.add(color);
      }
    }
  }
  
  /**
   * METHODS FOR VIEWS
   */

  public List<String> getColors() {
    return _colors;
  }

}
