package com.powaclient.module.modules;

import com.powaclient.Blur;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

/**
 * Blurs the world behind any GUI (post-processing shader).
 */
public class MenuBlurModule extends Module
{
    public MenuBlurModule()
    {
        super("Menu Blur", Category.MISC);
        this.setEnabled(true);
    }

    @Override
    protected void onDisable()
    {
        Blur.stop();
    }
}
