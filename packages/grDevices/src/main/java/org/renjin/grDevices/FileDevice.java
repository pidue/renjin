/*
 * Renjin : JVM-based interpreter for the R language for the statistical analysis
 * Copyright © 2010-2019 BeDataDriven Groep B.V. and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, a copy is available at
 *  https://www.gnu.org/licenses/gpl-2.0.txt
 *
 */

package org.renjin.grDevices;

import org.renjin.eval.Session;
import org.renjin.sexp.ListVector;

import java.awt.*;

public class FileDevice extends GraphicsDevice {

  private final Session session;
  private final String filename;
  private final String format;
  private final Color backgroundColor;

  public FileDevice(Session session, ListVector deviceOptions) {
    this.session = session;
    this.filename = deviceOptions.getElementAsString("filename");
    this.format = deviceOptions.getElementAsString("format");
    this.backgroundColor = Color.WHITE;
  }

  @Override
  public void open(double w, double h) {
    if(format.equals("svg")) {
      container = new SvgContainer(session, filename, (int) w, (int) h, backgroundColor);
    } else {
      container = new ImageContainer(session, filename, format, backgroundColor, (int) w, (int) h);
    }
  }
}
