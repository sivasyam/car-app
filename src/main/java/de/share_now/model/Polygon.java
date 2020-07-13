package de.share_now.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Polygon {
    //@JsonIgnore
    private Set vin;

    private String cityId;

    private String type;

    private String version;

    private String createdAt;

    private String __v;

    private String name;
    private String _id;

    private String updatedAt;

    private String legacyId;

    private Options options;

    private Geometry3 geometry;

    private $computed $computed;

    private GeoFeatures[] geoFeatures;

    private TimedOptions[] timedOptions;

    public void setVin(Set vin) {
        this.vin = vin;
    }

    public Set getVin() {
        return vin;
    }

    public TimedOptions[] getTimedOptions() {
        return timedOptions;
    }

    public void setTimedOptions(TimedOptions[] timedOptions) {
        this.timedOptions = timedOptions;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public $computed get$computed() {
        return $computed;
    }

    public void set$computed($computed $computed) {
        this.$computed = $computed;
    }

    public String getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(String legacyId) {
        this.legacyId = legacyId;
    }

    public Geometry3 getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry3 geometry) {
        this.geometry = geometry;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public GeoFeatures[] getGeoFeatures() {
        return geoFeatures;
    }

    public void setGeoFeatures(GeoFeatures[] geoFeatures) {
        this.geoFeatures = geoFeatures;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ClassPojo [timedOptions = " + timedOptions + ", cityId = " + cityId + ", type = " + type + ", version = " + version + ", createdAt = " + createdAt + ", __v = " + __v + ", name = " + name + ", options = " + options + ", $computed = " + $computed + ", legacyId = " + legacyId + ", geometry = " + geometry + ", _id = " + _id + ", geoFeatures = " + geoFeatures + ", updatedAt = " + updatedAt + "]";
    }
}


