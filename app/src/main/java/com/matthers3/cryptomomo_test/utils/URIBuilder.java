package com.matthers3.cryptomomo_test.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class URIBuilder {
    private URI uri;

    public URIBuilder(String url) throws URISyntaxException {
        uri = new URI(url);
    }

    public String getResult() {
        return uri.toString();
    }

    public void setParams(List<URIParameter> params) throws URISyntaxException {
        if (params != null) {
            for (URIParameter param: params) {
                addParameter(param.name, param.value);
            }
        }
    }

    private void addParameter(String key, String value) throws URISyntaxException {
        String newParam = key + "=" + value;
        uri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(),
                uri.getQuery() == null ? newParam : uri.getQuery() + "&" +
                        newParam, uri.getFragment());
    }
}
