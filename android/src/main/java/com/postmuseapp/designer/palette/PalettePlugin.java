package com.postmuseapp.designer.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.support.v4.content.FileProvider;
import android.support.v7.graphics.Palette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PalettePlugin
 */
public class PalettePlugin implements MethodCallHandler {

    private static final String PALETTE_CHANNEL = "channel:com.postmuseapp.designer/palette";

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), PALETTE_CHANNEL);
        channel.setMethodCallHandler(new PalettePlugin());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPalette")) {
            getPalette(
                    (String) call.argument("path"),
                    result);
        }
    }

    public void getPalette(final String path, final MethodChannel.Result result) {
        getPalette(path, result, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Map<String, Object> paletteMap = new HashMap<>();
                paletteMap.put("vibrant", convertSwatch(palette.getVibrantSwatch()));
                paletteMap.put("darkVibrant", convertSwatch(palette.getDarkVibrantSwatch()));
                paletteMap.put("lightVibrant", convertSwatch(palette.getLightVibrantSwatch()));
                paletteMap.put("muted", convertSwatch(palette.getMutedSwatch()));
                paletteMap.put("darkMuted", convertSwatch(palette.getDarkMutedSwatch()));
                paletteMap.put("lightMuted", convertSwatch(palette.getLightMutedSwatch()));

                List<Object> swatches = new ArrayList<>();
                for (Palette.Swatch swatch : palette.getSwatches()) {
                    swatches.add(convertSwatch(swatch));
                }
                paletteMap.put("swatches", swatches);
                result.success(paletteMap);
            }
        });

    }

    private void getPalette(final String path, final MethodChannel.Result result, final Palette.PaletteAsyncListener onPalette) {
        new AsyncTask<String, Void, Palette>() {
            @Override
            protected Palette doInBackground(String... params) {
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(params[0], options);
                    options.inSampleSize = calculateInSampleSize(options, 200, 200); //112 is the default palette resizes to anyway
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeFile(params[0], options);

                    if (bitmap == null) {
                        result.error("bitmap", "Bitmap Null", null);
                    } else if (bitmap.isRecycled()) {
                        result.error("bitmap", "Bitmap Recycled", null);
                    }
                    return Palette.from(bitmap).generate();
                } catch (Exception e) {
                    result.error("bitmap", "Exception thrown during async generate", null);
                    Log.e("Palette Plugin", "Exception thrown during async generate", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Palette palette) {
                onPalette.onGenerated(palette);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, path);
    }

    private Map<String, Object> convertSwatch(Palette.Swatch swatch) {
        if (swatch == null) {
            return null;
        }
        Map<String, Object> swatchMap = new HashMap<>();
        swatchMap.put("color", swatch.getRgb());
        swatchMap.put("population", swatch.getPopulation());
        swatchMap.put("titleTextColor", swatch.getTitleTextColor());
        swatchMap.put("bodyTextColor", swatch.getBodyTextColor());
        swatchMap.put("swatchInfo", swatch.toString());
        return swatchMap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
