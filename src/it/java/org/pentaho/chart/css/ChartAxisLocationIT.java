/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.css;

import junit.framework.TestCase;

import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartAxisLocationType;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ChartAxisLocationIT extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testChartAxisType() throws IllegalStateException, ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartAxisLocationTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValue[] passValues = new CSSValue[] {
        ChartAxisLocationType.PRIMARY, 
        ChartAxisLocationType.SECONDARY,
        ChartAxisLocationType.PRIMARY, 
        ChartAxisLocationType.PRIMARY, 
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.print("Counter#: " + counter + " Expecting: " + passValues[counter]); //$NON-NLS-1$ //$NON-NLS-2$ 
      System.out.println(" -- Got: " + layoutStyle.getValue(ChartStyleKeys.AXIS_LOCATION)); //$NON-NLS-1$  
      assertEquals("Counter# " + counter + " Location: ", passValues[counter], layoutStyle.getValue(ChartStyleKeys.AXIS_LOCATION)); //$NON-NLS-1$ //$NON-NLS-2$ 
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}