package com.imagepicker.media;

import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;

import java.io.File;

/**
 * Created by rusfearuth on 16.03.17.
 */

public class VideoConfig
{
    public @Nullable final File original;
    public @Nullable final File resized;
    public final int quality;
    public final int rotation;
    public final boolean saveToCameraRoll;

    public VideoConfig(@Nullable final File original,
                       @Nullable File resized, final int quality,
                       final int rotation, boolean saveToCameraRoll)
    {
        this.original = original;
        this.resized = resized;
        this.quality = quality;
        this.rotation = rotation;
        this.saveToCameraRoll = saveToCameraRoll;
    }
    public @NonNull
    VideoConfig withQuality(final int quality)
    {
        return new VideoConfig(
                this.original, this.resized,
                quality, this.rotation,
                this.saveToCameraRoll);
    }

    public @NonNull VideoConfig withRotation(final int rotation)
    {
        return new VideoConfig(
                this.original, this.resized,
                this.quality, rotation,
                this.saveToCameraRoll);
    }

    public @NonNull VideoConfig withOriginalFile(@Nullable final File original)
    {
        if (original != null) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(original.getAbsolutePath());
            int quality = this.quality;
        }

        return new VideoConfig(
                original, this.resized,
                quality, this.rotation,
                this.saveToCameraRoll);
    }
    public @NonNull VideoConfig withResizedFile(@Nullable final File resized)
    {
        return new VideoConfig(
                this.original, resized,
                this.quality, this.rotation,
                this.saveToCameraRoll
        );
    }

    public @NonNull VideoConfig withSaveToCameraRoll(@Nullable final boolean saveToCameraRoll)
    {
        return new VideoConfig(
                this.original, this.resized,
                this.quality, this.rotation,
                this.saveToCameraRoll
        );
    }
    public @NonNull VideoConfig updateFromOptions(@NonNull final ReadableMap options)
    {
        int quality = 100;
        if (options.hasKey("quality"))
        {
            quality = (int) (options.getDouble("quality") * 100);
        }
        int rotation = 0;
        if (options.hasKey("rotation"))
        {
            rotation = (int) options.getDouble("rotation");
        }
        boolean saveToCameraRoll = false;
        if (options.hasKey("storageOptions"))
        {
            final ReadableMap storageOptions = options.getMap("storageOptions");
            if (storageOptions.hasKey("cameraRoll"))
            {
                saveToCameraRoll = storageOptions.getBoolean("cameraRoll");
            }
        }
        return new VideoConfig(this.original, this.resized, quality, rotation, saveToCameraRoll);
    }
    public boolean useOriginal(int currentRotation)
    {
        return quality == 100 && (rotation == 0 || currentRotation == rotation);
    }

    public File getActualFile()
    {
        return resized != null ? resized: original;
    }
}
