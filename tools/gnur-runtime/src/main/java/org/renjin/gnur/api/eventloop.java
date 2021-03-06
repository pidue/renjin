/*
 * Renjin : JVM-based interpreter for the R language for the statistical analysis
 * Copyright © 2010-2019 BeDataDriven Groep B.V. and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, a copy is available at
 * https://www.gnu.org/licenses/gpl-2.0.txt
 */
// Initial template generated from eventloop.h from R 3.2.2
package org.renjin.gnur.api;

@SuppressWarnings("unused")
public final class eventloop {

  private eventloop() { }



  // InputHandler* initStdinHandler (void)

  // void consoleInputHandler (unsigned char *buf, int len)

  // InputHandler* addInputHandler (InputHandler *handlers, int fd, InputHandlerProc handler, int activity)

  // InputHandler* getInputHandler (InputHandler *handlers, int fd)

  // int removeInputHandler (InputHandler **handlers, InputHandler *it)

  // InputHandler* getSelectedHandler (InputHandler *handlers, fd_set *mask)

  // fd_set* R_checkActivity (int usec, int ignore_stdin)

  // fd_set* R_checkActivityEx (int usec, int ignore_stdin, void(*intr)(void))

  // void R_runHandlers (InputHandler *handlers, fd_set *mask)

  // int R_SelectEx (int n, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout, void(*intr)(void))
}
