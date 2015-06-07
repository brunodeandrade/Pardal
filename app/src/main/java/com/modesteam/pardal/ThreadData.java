package com.modesteam.pardal;

import com.modesteam.pardal.brand.BrandContent;
import com.modesteam.pardal.city.CityContent;
import com.modesteam.pardal.highwayStretch.HighwayStretchContent;
import com.modesteam.pardal.model.ModelContent;
import com.modesteam.pardal.state.StateContent;
import com.modesteam.pardal.type.TypeContent;

/**
 * Created by luisresende on 03/06/15.
 */
public class ThreadData implements Runnable {
    @Override
    public void run() {
        new TypeContent();
        new BrandContent();
        new StateContent();
        new CityContent();
        new HighwayStretchContent();
        new ModelContent();
    }
}
