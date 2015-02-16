package com.paulina.gridimagesearch.models;

/**
 * Created by pramos on 2/15/15.
 */

import java.io.Serializable;

public class Filters implements Serializable {
    private static final long serialVersionUID = -7217618237591791796L;
    public String imageSize = null;
    public String colorFilter = null;
    public String imageType = null;
    public String siteFilter = "";
}