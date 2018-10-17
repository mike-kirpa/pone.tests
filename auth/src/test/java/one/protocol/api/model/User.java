package one.protocol.api.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("emailConfirmed")
    @Expose
    private Boolean emailConfirmed;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("jwtToken")
    @Expose
    private String jwtToken;
    @SerializedName("ips")
    @Expose
    private List<Ip> ips = null;
    @SerializedName("lastIp")
    @Expose
    private String lastIp;
    @SerializedName("lastLoginAt")
    @Expose
    private String lastLoginAt;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("serviceLogin")
    @Expose
    private String serviceLogin;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("readEula")
    @Expose
    private Boolean readEula;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("responce")
    @Expose
    private String responce;
    @SerializedName("captcha")
    @Expose
    private String captcha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public List<Ip> getIps() {
        return ips;
    }

    public void setIps(List<Ip> ips) {
        this.ips = ips;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getServiceLogin() {
        return serviceLogin;
    }

    public void setServiceLogin(String serviceLogin) {
        this.serviceLogin = serviceLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getReadEula() {
        return readEula;
    }

    public void setReadEula(Boolean readEula) {
        this.readEula = readEula;
    }

    @Override
    public String toString() {
        return "User{" +
                "testName='" + testName + '\'' +
                '}';
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

}