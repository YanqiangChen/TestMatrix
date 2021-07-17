package com.test.testmatrix;

import android.app.Application;

import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Matrix.Builder builder = new Matrix.Builder(this); // build matrix
        builder.patchListener(new TestPluginListener(this)); // add general pluginListener
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo(); // dynamic config

        // init plugin
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build());
        TracePlugin tracePlugin = new TracePlugin(new TraceConfig.Builder()
                .build());

        //add to matrix
        builder.plugin(ioCanaryPlugin);
        builder.plugin(tracePlugin);

        //init matrix
        Matrix.init(builder.build());

        // start plugin
        ioCanaryPlugin.start();

    }
}
