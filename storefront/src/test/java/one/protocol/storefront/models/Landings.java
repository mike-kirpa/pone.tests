package one.protocol.storefront.models;

public class Landings {
    private String LandingUrl;
    private String LandingName;
    private String ModeView;
    private String HeaderSelector;

    public String getHeaderSelector() {
        return HeaderSelector;
    }

    public void setHeaderSelector(String headerSelector) {
        HeaderSelector = headerSelector;
    }

    public String getModeView() {
        return ModeView;
    }

    public void setModeView(String modeView) {
        ModeView = modeView;
    }

    public String getLandingName() {
        return LandingName;
    }

    public void setLandingName(String landingName) {
        LandingName = landingName;
    }

    public String getLandingUrl() {
        return LandingUrl;
    }

    public void setLandingUrl(String landingUrl) {
        LandingUrl = landingUrl;
    }
}
