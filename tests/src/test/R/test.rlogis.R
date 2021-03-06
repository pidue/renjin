#
# Renjin : JVM-based interpreter for the R language for the statistical analysis
# Copyright © 2010-2019 BeDataDriven Groep B.V. and contributors
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, a copy is available at
# https://www.gnu.org/licenses/gpl-2.0.txt
#

# Generated by gen-dist-tests.R using GNU R version 3.3.1 (2016-06-21)
library(hamcrest)
library(stats)
set.seed(1)
test.rlogis.1 <- function() assertThat({set.seed(1);rlogis(n = 0x1p+0, location = c(0x0p+0, 0x1p-1, 0x1.4p+2), scale = c(0x0p+0, 0x1p+0, 0x1p-1, 0x1.4p+2))}, identicalTo(0x0p+0))
test.rlogis.2 <- function() assertThat({set.seed(1);rlogis(n = 1:5, location = c(0x0p+0, 0x1p-1, 0x1.4p+2), scale = c(0x0p+0, 0x1p+0, 0x1p-1, 0x1.4p+2))}, identicalTo(c(0x0p+0, -0x1.08f9c9bac12dp-1, 0x1.2f42a2402ba03p+2, 0x1.77aede7947abep+0, 0x1p-1), tol = 0.000100))
test.rlogis.3 <- function() assertThat({set.seed(1);rlogis(n = 0x1.ep+3, location = c(0x0p+0, 0x1p-1, 0x1.4p+2),     scale = c(0x0p+0, 0x1p+0, 0x1p-1, 0x1.4p+2))}, identicalTo(c(0x0p+0, -0x1.08f9c9bac12dp-1, 0x1.2f42a2402ba03p+2, 0x1.77aede7947abep+0, 0x1p-1, 0x1.d2af3d453550dp+2, -0x1.60356d8277cf8p-1, 0x1.6cb6a29902f76p+3, 0x1.4p+2, 0x1.6b372bf4136f5p+1, 0x1.aab6c72562db2p-1, 0x1.e917ff33d8538p+2, 0x0p+0, -0x1.1c32add3912ep+1, 0x1.14d205390dbeap+2), tol = 0.000100))
test.rlogis.4 <- function() assertThat({set.seed(1);rlogis(n = numeric(0), location = c(0x0p+0, 0x1p-1, 0x1.4p+2),     scale = c(0x0p+0, 0x1p+0, 0x1p-1, 0x1.4p+2))}, identicalTo(numeric(0)))
test.rlogis.5 <- function() assertThat({set.seed(1);rlogis(n = 0x1.8p+1, location = c(NA, 0x1p-1, 0x1.4p+2), scale = c(0x0p+0, 0x1p+0, 0x1p-1, 0x1.4p+2))}, identicalTo(c(NaN, -0x1.08f9c9bac12dp-1, 0x1.2f42a2402ba03p+2)))
