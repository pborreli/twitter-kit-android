package com.twitter.sdk.android.core.internal;

import android.net.Uri;
import android.os.Build;

public class TwitterApi {

    // TODO: Define common urls, resources here
    public static final String BASE_HOST = "api.twitter.com";
    public static final String BASE_HOST_URL = "https://" + BASE_HOST;

    private final String baseHostUrl;

    public TwitterApi() {
        this(BASE_HOST_URL);
    }

    public TwitterApi(String baseHostUrl) {
        this.baseHostUrl = baseHostUrl;
    }

    public String getBaseHostUrl() {
        return baseHostUrl;
    }

    /**
     * Builds upon the base host url by appending paths to the url.
     *
     * @param paths the paths to append
     * @return {@link android.net.Uri.Builder} that can be used to further build the url.
     */
    public Uri.Builder buildUponBaseHostUrl(String... paths) {
        final Uri.Builder builder = Uri.parse(getBaseHostUrl()).buildUpon();
        if (paths != null) {
            for (String p : paths) {
                builder.appendPath(p);
            }
        }
        return builder;
    }

    /**
     * @return User-Agent string that looks like:
     * client_name/client_version (client_version_code) model/os_version (manufacturer;device;brand;product;client_source;preload;on_wifi)
     * <p/>
     * Example: TwitterAndroidSDK/1.1.0.dev HTC One/4.1.2 (HTC;HTC One;tmous;m7)
     * <p/>
     * See go/ooua for more information.
     */
    public static String buildUserAgent(String clientName, String version) {
        final StringBuilder ua = new StringBuilder(clientName)
                .append('/').append(version)
                        // NOTE: We currently do not provide client_version_code information.
                .append(' ')
                .append(Build.MODEL).append('/').append(Build.VERSION.RELEASE)
                .append(" (")
                .append(Build.MANUFACTURER).append(';')
                .append(Build.MODEL).append(';')
                .append(Build.BRAND).append(';')
                .append(Build.PRODUCT)
                        // NOTE: We do not add client_source, preload, or wifi information.
                .append(')');
        return ua.toString();
    }
}