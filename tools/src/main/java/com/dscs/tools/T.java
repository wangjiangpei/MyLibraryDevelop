package com.dscs.tools;

import com.dscs.tools.cropwindow.edge.CorpParams;
import com.dscs.tools.view.MultiImageSelector;

/**
 *
 */

public class T {
   private MultiImageSelector selector;
    public static MultiImageSelector image() {
        return MultiImageSelector.create();
    }
    public static CorpParams corp(){
        return CorpParams.create();
    }
}
