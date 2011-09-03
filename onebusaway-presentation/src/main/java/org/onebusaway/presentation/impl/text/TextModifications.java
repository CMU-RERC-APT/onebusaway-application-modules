/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
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
package org.onebusaway.presentation.impl.text;

import org.onebusaway.presentation.services.text.TextModification;

import java.util.ArrayList;
import java.util.List;

public class TextModifications implements TextModification {

  private List<TextModification> _modifications = new ArrayList<TextModification>();

  public void setModifications(List<TextModification> modifications) {
    _modifications = modifications;
  }

  public void addModification(TextModification modification) {
    _modifications.add(modification);
  }

  public String modify(String input) {
    for (TextModification modification : _modifications)
      input = modification.modify(input);
    return input;
  }
}
