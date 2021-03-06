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

library(hamcrest)
library(utils)
library(org.renjin.test.dataTest)

data(wiod05)
assertThat(ls(), identicalTo(c("countries", "final05", "industries", "inter05", "output05")))
assertThat(countries, identicalTo(
        c("AUS", "AUT", "BEL", "BGR", "BRA", "CAN", "CHN", "CYP", "CZE",  "DEU", "DNK", 
          "ESP", "EST", "FIN", "FRA", "GBR", "GRC", "HUN",  "IDN", "IND", "IRL", "ITA", 
          "JPN", "KOR", "LTU", "LUX", "LVA",  "MEX", "MLT", "NLD", "POL", "PRT", "ROM", 
          "RUS", "SVK", "SVN",  "SWE", "TUR", "TWN", "USA", "RoW")))
          
assertThat(output05[1:5], identicalTo(c(37935L, 83639L, 54522L, 4718L, 1217L)))

