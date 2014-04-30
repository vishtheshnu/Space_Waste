package com.shnu.spacewaste.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.shnu.spacewaste.SpaceWaste;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(400, 600);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new SpaceWaste();
        }
}